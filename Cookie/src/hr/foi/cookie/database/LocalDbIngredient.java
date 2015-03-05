package hr.foi.cookie.database;

import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.IngredientQuantified;
import hr.foi.cookie.types.Recipe;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class LocalDbIngredient extends LocalDbBase<IngredientQuantified>{

	public static final String TABLE = "ingredients";
	public static final String KEY = "ingredientid";
	
	public LocalDbIngredient(Context context) {
		super(context);
	}
	
	public long insertIngredient(IngredientQuantified ingredient)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put("ingredientid", ingredient.getId());
		contentValues.put("name", ingredient.getName());
		
		db = dbHelper.getWritableDatabase();
		long result = db.insert(TABLE, null, contentValues);
		dbHelper.close();
		
		return result;
	}
	
	public boolean checkIfExists(int id)
	{
		String[] columns = new String[]{KEY, "name"};
		try
		{
			db = dbHelper.getReadableDatabase();
			Cursor c = db.query(TABLE, columns, KEY + "=" + id, null, null, null, null);
			c.moveToFirst();
		
			boolean exists = (c.getCount() > 0);
		
			return exists;
		}
		catch(NullPointerException e)
		{

		}
		
		return false;
	}
	
	@Override
	public List<IngredientQuantified> getAll(int offset, int limit) throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IngredientQuantified getById(int id) throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public long saveIngredientLocally(Recipe recipe)
	{
		List<IngredientQuantified> ingredientList = recipe.getIngredients();
		for (int i = 0; i < ingredientList.size(); i++)
		{	
			if (checkIfExists(ingredientList.get(i).getId()) == false)
			{
				insertIngredient(new IngredientQuantified(
				ingredientList.get(i).getId(),
				ingredientList.get(i).getName()));
			}
			
			LocalDbRecipeIngredients localDbRecipeIngredients = new LocalDbRecipeIngredients(this.context);
			localDbRecipeIngredients.insertRecipeIngredients(recipe, ingredientList.get(i));
		}
		
		return 0;
	}
	
}
