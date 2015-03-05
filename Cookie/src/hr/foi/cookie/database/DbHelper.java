package hr.foi.cookie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	
	public static final String DB_NAME = "cookie.db";
	public static final int DB_VERSION = 1;
	
	public DbHelper(Context context, CursorFactory factory) {
		super(context, DB_NAME, factory, DB_VERSION);
		//context.deleteDatabase(DB_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE recipes " +
				   "(recipeid INTEGER PRIMARY KEY, " +
				   "name TEXT, " +
				   "preparation_time INTEGER, " +
				   "description TEXT, " +
				   "image_large TEXT, " +
				   "timestamp INTEGER);");
		
		db.execSQL("CREATE TABLE ingredients " +
				   "(ingredientid INTEGER PRIMARY KEY, " +
				   "name TEXT);");
		
		db.execSQL("CREATE TABLE recipeingredients " +
				   "(recipeid INTEGER, " +
				   "ingredientid INTEGER, " +
				   "quantity REAL, " +
				   "unitid INTEGER, " +
				   "PRIMARY KEY (recipeid, ingredientid));");
		
		db.execSQL("CREATE TABLE units " +
				   "(unitid INTEGER PRIMARY KEY, " +
				   "name TEXT, " +
				   "symbol TEXT);");
		
		db.execSQL("INSERT INTO units (unitid, name, symbol)" +
				   "VALUES( " +
				   "null, \"Komad\", \"kom\")," +
				   "(null, \"Kilogram\", \"kg\")," +
				   "(null, \"Mililitra\", \"ml\")," +
				   "(null, \"Dekagram\", \"dg\")," +
				   "(null, \"Gram\", \"g\");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
