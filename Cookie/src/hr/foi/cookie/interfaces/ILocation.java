package hr.foi.cookie.interfaces;

import com.google.android.gms.maps.model.LatLng;

/**
 * Represents a location.
 * 
 * @author Marin
 */
public interface ILocation {
	/**
	 * Get the name of this location.
	 * @return A name string
	 */
	public String getName();
	
	/**
	 * Get LatLng-formatted coordinates for this location (for display on a map)
	 * @return Coordinates of location.
	 */
	public LatLng getCoordinates();
}
