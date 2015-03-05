package hr.foi.cookie.webservice;

import hr.foi.cookie.core.ListHelper;
import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.IngredientQuantified;
import hr.foi.cookie.types.Recipe;
import hr.foi.cookie.types.RecipePreferred;
import hr.foi.cookie.types.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

public class JsonWsRecipe extends JsonWsBase<Recipe> {
	
	protected final String wsUriPath() {
		return "recipes.php";
	}
	
	@Override
	public Recipe getById(int id) throws DataSourceException {
		JSONObject jObject = getJsonObject("?id=" + id);
		Recipe recipe = jsonObjectToRecipe(jObject);
		
		JSONArray jArray = getJsonArray("?get=ingredients&id=" + id);
		List<IngredientQuantified> ingredientList = getIngredientList(jArray);
		recipe.setIngredients(ingredientList);
		
		return recipe;
	}

	@Override
	public List<Recipe> getAll(int offset, int limit) throws DataSourceException {
		JSONArray json = getJsonArray();
		List<Recipe> recipeList = jsonArrayToRecipeList(json);
		return recipeList;
	}
	
	public List<String> getNames() throws DataSourceException {
		 JSONArray json = getJsonArray();
		 
		 List<String> names = new ArrayList<String>();
		 try {
			for (int i = 0; i < json.length(); i++) {
				JSONObject jObject = json.getJSONObject(i);

				String recipeName = jObject.optString("name");
				names.add(recipeName);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return names;
	}
	
	public List<RecipePreferred> searchPreferred(String ids) throws DataSourceException
	{
		List<RecipePreferred> recipes = new ArrayList<RecipePreferred>();
		JSONObject json = getJsonObject("?ids=" + ids);
		
		try {
			JSONArray jsonPreferred = null;
			JSONArray jsonOther = null;
			try {
				jsonPreferred = json.getJSONArray("preferred");
			}
			catch(JSONException e) { }
			try {
				jsonOther = json.getJSONArray("other");
			}
			catch(JSONException e) { }
			
			if (jsonPreferred != null)
			{
				for (int i = 0; i < jsonPreferred.length(); i++)
				{
					JSONObject pref = jsonPreferred.getJSONObject(i);
					
					Integer recipeId = pref.optInt("recipeid");
					String name = pref.optString("name");
					Integer prearationTime = pref.optInt("preparation_time");
					String description = pref.optString("description");
					
					RecipePreferred recipePreferred = new RecipePreferred(
						recipeId,
						name,
						prearationTime,
						description,
						true
					);
					recipes.add(recipePreferred);
				}
			}
			
			if (jsonOther != null)
			{
				for (int i = 0; i < jsonOther.length(); i++)
				{
					JSONObject oth = jsonOther.getJSONObject(i);
					
					Integer recipeId = oth.optInt("recipeid");
					String name = oth.optString("name");
					Integer prearationTime = oth.optInt("preparation_time");
					String description = oth.optString("description");
					
					RecipePreferred recipeOther = new RecipePreferred(
						recipeId,
						name,
						prearationTime,
						description,
						false
					);
					recipes.add(recipeOther);
				}
			}
		}
		catch (JSONException e)
		{
			throw new DataSourceException("Neispravan JSON zapis kod pretrage recepata!");
		}
		return recipes;
	}
	
	protected Recipe jsonObjectToRecipe(JSONObject jObject)
	{
		Integer recipeId = jObject.optInt("recipeid");
		String name = jObject.optString("name");
		Integer prearationTime = jObject.optInt("preparation_time");
		String description = jObject.optString("description");
		Integer timestamp = jObject.optInt("timestamp");
		
		Recipe recipe = new Recipe(
			recipeId,
			name,
			prearationTime,
			description
		);
		recipe.setTimestamp(timestamp);
		
		return recipe;
	}
	
	protected List<Recipe> jsonArrayToRecipeList(JSONArray jArray) throws DataSourceException
	{
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		for (int i = 0; i < jArray.length(); i++)
		{
			try {
				JSONObject obj = jArray.getJSONObject(i);
				
				Recipe recipe = jsonObjectToRecipe(obj);
				recipes.add(recipe);
				
			} catch (JSONException e) {
				throw new DataSourceException();
			}
		}
		return recipes;
	}
	
	public List<IngredientQuantified> getIngredientList(JSONArray jArray) throws DataSourceException {
		List<IngredientQuantified> list = new ArrayList<IngredientQuantified>();
		
		for (int i = 0; i < jArray.length(); i++)
		{
			try {
				JSONObject obj = jArray.getJSONObject(i);
				
				Integer ingredientId = obj.optInt("ingredientid");
				String ingredientName = obj.optString("ingredientname");
				Integer ingredientCategoryId = obj.optInt("ingredientcategoryid");
				Integer quantity = obj.optInt("quantity");
				Integer unitId = obj.optInt("unitid");
				String unitName = obj.optString("name");
				String unitSymbol = obj.optString("symbol");
				
				Unit unit = new Unit(unitId, unitName, unitSymbol);
				IngredientQuantified ingredientQuantified = new IngredientQuantified(
					ingredientId,
					ingredientName,
					ingredientCategoryId,
					unit,
					quantity
				);
				
				list.add(ingredientQuantified);
				
			} catch (JSONException e) {
				throw new DataSourceException();
			}
		}
		
		return list;
	}
	
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, Integer> checkTimestamps(List<Integer> ids) throws DataSourceException, JSONException
	{
		JSONArray jArray = getJsonArray("?get=timestamps&ids=" + ListHelper.implode(ids));
		
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < jArray.length(); i++)
		{
			JSONObject obj = jArray.getJSONObject(i);
			
			Integer recipeId = obj.getInt("recipeid");
			Integer timestamp = obj.getInt("timestamp");
			
			map.put(recipeId, timestamp);
		}
		
		//Log.d("timestamps", " " + ListHelper.implode(ids));
		
		return map;
	}
	
	public List<Recipe> getNonSyncedRecipes(List<Integer> ids) throws DataSourceException
	{
		JSONArray jArray = getJsonArray("?get=sync&ids=" + ListHelper.implode(ids));
		
		return jsonArrayToRecipeList(jArray);
	}
}
