package hr.foi.cookie.core.exceptions;

@SuppressWarnings("serial")
public class InternetConnectivityException extends Exception {
	
	public InternetConnectivityException() { }
	
	public InternetConnectivityException(String error) {
		super(error);
	}
	
}
