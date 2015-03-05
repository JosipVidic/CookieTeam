package hr.foi.cookie.webservice;

import java.util.List;

import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.User;

public class JsonWsUser extends JsonWsBase<User> {

	@Override
	public User getById(int id) throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAll(int offset, int limit) throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String wsUriPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected User registerUser(String email, String username, String name, String surname) {
		User u = null;
		
		
		return u;
	}
}
