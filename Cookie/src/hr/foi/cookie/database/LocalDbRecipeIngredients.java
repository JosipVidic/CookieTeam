package hr.foi.cookie.database;

import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.IngredientQuantified;
import hr.foi.cookie.types.Recipe;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;

public class LocalDbRecipeIngredients extends LocalDbBase<IngredientQuantified> {
	
	public static final String TABLE = "recipeingredients";
	
	public LocalDbRecipeIngredients(Context context) {
		super(context);
	}
	
	public long insertRecipeIngredients(Recipe recipe, IngredientQuantified ingredient)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put("recipeid", recipe.getRecipeId());
		contentValues.put("ingredientid", ingredient.getId());
		contentValues.put("quantity", ingredient.getQuantity());
		contentValues.put("unitid", ingredient.getUnit().getId());
		
		db = dbHelper.getWritableDatabase();
		long result = db.insert(TABLE, null, contentValues);
		dbHelper.close();
		
		return result;
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

}
