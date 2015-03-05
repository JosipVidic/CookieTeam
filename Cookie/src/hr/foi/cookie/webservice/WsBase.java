package hr.foi.cookie.webservice;


/**
 * Abstract class representing a Web service. Implements IDataSource and is at the
 * bottom of the Web service plugin hierarchy.
 * @author Marin
 *
 * @param <Entity>
 */
public abstract class WsBase {
	
	/**
	 * Web service root URL, all other URLs are relative to it.
	 */
	protected final String wsUriBase = "http://ankvesic.byethost7.com/";
	
	/**
	 * Returns the "WS URI", or the full URL to the resource identified as the result of
	 * wsUriPath().
	 * @return	Full path to service resource.
	 */
	protected String getWsUri() {
		return wsUriBase + wsUriPath();
	}
	
	/**
	 * Implement this method to return the relative path to the service resource.
	 * 
	 * Example: "recipe.php".
	 * @return	String relative path to resource.
	 */
	protected abstract String wsUriPath();
}
