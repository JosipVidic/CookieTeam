package hr.foi.cookie.database;

import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.IngredientQuantified;
import hr.foi.cookie.types.Recipe;
import hr.foi.cookie.types.Unit;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

public class LocalDbRecipe extends LocalDbBase<Recipe> {
	
	public static final String TABLE = "recipes";
	public static final String KEY = "recipeid";
	
	public LocalDbRecipe(Context context) {
		super(context);	
	}
	
	public long insertRecipe(Recipe recipe)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put("recipeid", recipe.getRecipeId());
		contentValues.put("name", recipe.getName());
		contentValues.put("preparation_time", recipe.getPreparationTime());
		contentValues.put("description", recipe.getDescription());
		contentValues.put("image_large", recipe.getImageLarge());
		contentValues.put("timestamp", recipe.getTimestamp());

		db = dbHelper.getWritableDatabase();
		long result = db.insert(TABLE, null, contentValues);
		dbHelper.close();
		
		return result;
	}

	public List<IngredientQuantified> getIngredientList(int id)
	{
		List<IngredientQuantified> ingredients = new ArrayList<IngredientQuantified>();
		
		db = dbHelper.getReadableDatabase();
		
		String query = "SELECT ingredients.ingredientid, ingredients.name as ingredientname, quantity, symbol, units.unitid, units.name as unitname " +
					   "FROM ingredients " +
					   "JOIN recipeingredients ON ingredients.ingredientid = recipeingredients.ingredientid " +
					   "JOIN recipes ON recipes.recipeid = recipeingredients.recipeid " +
					   "JOIN units ON recipeingredients.unitid = units.unitid WHERE recipes.recipeid = " + id;
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		
		while (!(c.isAfterLast()))
		{
			int ingredientId = c.getInt(c.getColumnIndex("ingredientid"));
			int unitId = c.getInt(c.getColumnIndex("unitid"));
			String unitName = c.getString(c.getColumnIndex("unitname"));
			String ingredientName = c.getString(c.getColumnIndex("ingredientname"));
			double quantity = c.getDouble(c.getColumnIndex("quantity"));
			String symbol = c.getString(c.getColumnIndex("symbol"));
			
			Unit unit = new Unit(unitId, unitName, symbol);
			IngredientQuantified ingredient = new IngredientQuantified(ingredientId, ingredientName, unit, quantity);
			ingredients.add(ingredient);
			c.moveToNext();
		}
		dbHelper.close();
		
		return ingredients;
	}
	
	@Override
	public List<Recipe> getAll(int offset, int limit) throws DataSourceException {
		
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		String[] columns = new String[]{KEY, "name", "preparation_time","description", "image_large", "timestamp"};
		db = dbHelper.getReadableDatabase();
		
		Cursor c = db.query(TABLE, columns, null, null, null, null, null);
		c.moveToFirst();
		
		while (!(c.isAfterLast()))
		{
			int recipeId = c.getInt(c.getColumnIndex(KEY));
			String name = c.getString(c.getColumnIndex("name"));
			int preparationTime = c.getInt(c.getColumnIndex("preparation_time"));
			String description = c.getString(c.getColumnIndex("description"));
			byte[] imageLarge = c.getBlob(c.getColumnIndex("image_large"));
			
			Integer timestamp = c.getInt(c.getColumnIndex("timestamp"));
			
			Recipe recipe = new Recipe(recipeId, name, preparationTime, description);
			recipe.setImageLarge(imageLarge);
			recipe.setTimestamp(timestamp);
			
			recipes.add(recipe);
			c.moveToNext();
		}
		dbHelper.close();
		
		return recipes;
	}

	@Override
	public Recipe getById(int id) throws DataSourceException {
		
		Recipe recipe = null;
		
		String[] columns = new String[]{KEY, "name", "preparation_time", "description", "image_large", "timestamp"};
		db = dbHelper.getReadableDatabase();
		Cursor c = db.query(TABLE, columns, KEY + "=" + id, null, null, null, null);
		c.moveToFirst();

		int recipeId = c.getInt(c.getColumnIndex(KEY));
		String name = c.getString(c.getColumnIndex("name"));
		int preparationTime = c.getInt(c.getColumnIndex("preparation_time"));
		String description = c.getString(c.getColumnIndex("description"));
		byte[] imageLarge = c.getBlob(c.getColumnIndex("image_large"));
		recipe = new Recipe(recipeId, name, preparationTime, description);
		recipe.setIngredients(getIngredientList(id));
		recipe.setImageLarge(imageLarge);
		recipe.setTimestamp(c.getInt(c.getColumnIndex("timestamp")));
		
		dbHelper.close();
		
		return recipe;
	}
	
	public long saveRecipeLocally(Recipe recipe, Bitmap recipeImageLarge)
	{
		long saveSuccess;
		
		LocalDbIngredient localDbIngredient = new LocalDbIngredient(this.context);
		LocalDbImage localDbImage = new LocalDbImage();
		
		recipe.setImageLarge(localDbImage.imageToByteArray(recipeImageLarge));									
		
		saveSuccess = insertRecipe(new Recipe(recipe.getRecipeId(), 
												  recipe.getName(), 
												  recipe.getPreparationTime(), 
												  recipe.getDescription(),
												  recipe.getImageLarge(),
												  recipe.getTimestamp()));
		
		localDbIngredient.saveIngredientLocally(recipe);
		
		return saveSuccess;
	}
	
	public Bitmap getRecipeImage(int id)
	{
		String[] columns = new String[]{KEY, "image_large"};
		db = dbHelper.getReadableDatabase();
		Cursor c = db.query(TABLE, columns, KEY + "=" + id, null, null, null, null);
		c.moveToFirst();

		byte[] imageLargeByteArray = c.getBlob(c.getColumnIndex("image_large"));
		
		LocalDbImage localDbImage = new LocalDbImage();
		
		Bitmap recipeImageLarge = localDbImage.byteArrayToImage(imageLargeByteArray);
		Bitmap recipeImageSmall = (Bitmap.createScaledBitmap(recipeImageLarge, 45, 45, false));
		
		dbHelper.close();
		
		return recipeImageSmall;
	}
	
	public long updateRecipe(Recipe recipe)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put("recipeid", recipe.getRecipeId());
		contentValues.put("name", recipe.getName());
		contentValues.put("preparation_time", recipe.getPreparationTime());
		contentValues.put("description", recipe.getDescription());
		contentValues.put("timestamp", recipe.getTimestamp());

		db = dbHelper.getWritableDatabase();
		long result = db.update(TABLE, contentValues, KEY + "=" + recipe.getRecipeId(), null);
		dbHelper.close();
		
		return result;
	}

}
