package hr.foi.cookie.webservice;

import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.ShowableCategory;
import hr.foi.cookie.types.ShowableCategoryTypes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonWsCategory extends JsonWsBase<ShowableCategory> {
	
	@Override
	protected String wsUriPath() {
		return "categories.php";
	}
	
	@Override
	public ShowableCategory getById(int id) throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ShowableCategory> getAll(int offset, int limit) throws DataSourceException {
		JSONObject json = getJsonObject();
		List<ShowableCategory> categories = new ArrayList<ShowableCategory>();
		
		try {
			JSONArray jsonCats = null;
			JSONArray jsonIngs = null;
			try {
				jsonCats = json.getJSONArray("categories");
			}
			catch(JSONException e) { }
			try {
				jsonIngs = json.getJSONArray("ingredients");
			}
			catch(JSONException e) { }
			
			if (jsonCats != null)
			{
				for (int j = 0; j < jsonCats.length(); j++)
				{
					JSONObject cat = jsonCats.getJSONObject(j);
					
					Integer topCategoryId = cat.optInt("topcategoryid");
					Integer categoryId = cat.optInt("categoryid");
					String categoryName = cat.optString("name");
					//Log.d("JSON", "" + categoryName);
					
					ShowableCategory category = new ShowableCategory(
						categoryId,
						categoryName,
						topCategoryId,
						ShowableCategoryTypes.CATEGORY
					);
					categories.add(category);
				}
			}
			if (jsonIngs != null)
			{
				for (int j = 0; j < jsonIngs.length(); j++)
				{
					JSONObject ing = jsonIngs.getJSONObject(j);
					
					Integer ingredientId = ing.optInt("ingredientid");
					String ingredientName = ing.optString("name");
					Integer categoryId = ing.optInt("categoryid");
					
					Log.d("JSON", "" + ingredientName);
					
					ShowableCategory category = new ShowableCategory(
						ingredientId,
						ingredientName,
						categoryId,
						ShowableCategoryTypes.INGREDIENT
					);
					categories.add(category);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return categories;
	}
}
