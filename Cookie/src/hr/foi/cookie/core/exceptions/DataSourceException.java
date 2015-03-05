package hr.foi.cookie.core.exceptions;

@SuppressWarnings("serial")
public class DataSourceException extends Exception {
	
	public DataSourceException() { }
	public DataSourceException(String description) {
		super(description);
	}
	public DataSourceException(Exception innerException) {
		super(innerException);
	}
	
}
