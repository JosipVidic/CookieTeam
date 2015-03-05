package hr.foi.cookie.core;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Helper class for location-related tasks.
 * @author Marin
 *
 */
public class LocationHelper {
	/**
	 * Gets the last known location in array format [lat, lng].
	 * @param activity
	 * @return Last known location
	 */
	public static double[] getLastKnownLocation(Activity activity) {
		
		double lat = 0.0;
		double lng = 0.0;
		
		LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (location == null) {
			lat = 0.0;
			lng = 0.0;
		}
		else {
			// Log.v("Latitude", Double.toString(location.getLatitude()));
			// Log.v("Longitude", Double.toString(location.getLongitude()));

			lat = location.getLatitude();
			lng = location.getLongitude();
		}
		
		return new double[] { lat, lng };
	}
	
	/**
	 * Adjust zoom bounds for a slightly zoomed-out version of a map.
	 * @param Bounds
	 * @return Adjusted bounds
	 */
	public static LatLngBounds adjustBoundsForMaxZoomLevel(LatLngBounds bounds) {
		LatLng sw = bounds.southwest;
		LatLng ne = bounds.northeast;
		
		double deltaLat = Math.abs(sw.latitude - ne.latitude);
		double deltaLon = Math.abs(sw.longitude - ne.longitude);

		final double zoomN = 0.005; // minimum zoom coefficient
		if (deltaLat < zoomN) {
			sw = new LatLng(sw.latitude - (zoomN - deltaLat / 2), sw.longitude);
			ne = new LatLng(ne.latitude + (zoomN - deltaLat / 2), ne.longitude);
			bounds = new LatLngBounds(sw, ne);
		} else if (deltaLon < zoomN) {
			sw = new LatLng(sw.latitude, sw.longitude - (zoomN - deltaLon / 2));
			ne = new LatLng(ne.latitude, ne.longitude + (zoomN - deltaLon / 2));
			bounds = new LatLngBounds(sw, ne);
		}

		return bounds;
	}
}
