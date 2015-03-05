package hr.foi.cookie.webservice;

import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.interfaces.IDataSource;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Base class for implementing Web service methods.
 * @author Marin
 *
 */
public abstract class JsonWsBase<Entity> extends WsBase implements IDataSource<Entity> {
	
	protected JsonParser jsonParser = new JsonParser();
	
	@Override
	public abstract Entity getById(int id)  throws DataSourceException;
	
	
	@Override
	public List<Entity> getAll() throws DataSourceException {
		return getAll(-1, -1);
	}
	
	@Override
	public abstract List<Entity> getAll(int offset, int limit)  throws DataSourceException;
	
	protected JSONArray getJsonArray() throws DataSourceException {
		return getJsonArray(null);
	}
	
	protected JSONArray getJsonArray(String queryString) throws DataSourceException {
		String qs = "";
		if (queryString != null) {
			qs = queryString;
		}
		return jsonParser.getJSONArrayFromUrl(getWsUri() + qs);
	}
	
	protected JSONObject getJsonObject() throws DataSourceException {
		return getJsonObject(null);
	}
	
	protected JSONObject getJsonObject(String queryString) throws DataSourceException {
		String qs = "";
		if (queryString != null) {
			qs = queryString;
		}
		return jsonParser.getJSONObjectFromUrl(getWsUri() + qs);
	}
}
