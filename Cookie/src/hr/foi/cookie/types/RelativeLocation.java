package hr.foi.cookie.types;

import com.google.android.gms.maps.model.LatLng;

/**
 * Relative distance from user's current location.
 * 
 * @author Marin
 */
public class RelativeLocation extends Location {

	private double distance;

	/**
	 * Create a relative location that is a certain distance away from the current position.
	 * @param Location ID
	 * @param Name of the location
	 * @param Location coordinates
	 * @param Distance
	 */
	public RelativeLocation(int id, String name, LatLng coordinates, double distance) {
		super(id, name, coordinates);
		this.distance = distance;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public String getDistanceText() {
		String distanceText = "";
		
		long meters = Math.abs((long)((((long)this.distance) - this.distance) * 100));
		
		if (this.distance > 1) {
			distanceText += (long)this.distance + " km ";
		
		}
		
		distanceText += meters + " m.";
		return distanceText;
	}
}
