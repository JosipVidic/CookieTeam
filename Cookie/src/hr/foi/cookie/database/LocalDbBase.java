package hr.foi.cookie.database;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.interfaces.IDataSource;

public abstract class LocalDbBase<Entity> implements IDataSource<Entity> {
	
	protected DbHelper dbHelper;
	protected SQLiteDatabase db;
	protected Context context;
	
	public LocalDbBase(Context context) {
		this.dbHelper = new DbHelper(context, null);
		this.context = context;
	}
	
	@Override
	public List<Entity> getAll() throws DataSourceException {
		return getAll(-1, -1);
	}
	
	@Override
	public abstract List<Entity> getAll(int offset, int limit) throws DataSourceException;
	
	@Override
	public abstract Entity getById(int id) throws DataSourceException;
}
