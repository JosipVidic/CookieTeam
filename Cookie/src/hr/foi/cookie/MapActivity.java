package hr.foi.cookie;

import hr.foi.cookie.core.AsyncTaskHelper;
import hr.foi.cookie.core.LocationHelper;
import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.RelativeLocation;
import hr.foi.cookie.webservice.JsonWsLocation;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements LocationListener {
	private LocationManager locationManager;
	private double[] lastKnownLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, this);
		
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();
		}
		else {
			boolean checkConnection = isNetworkAvailable();
	        if(!checkConnection)
	        {
	            Toast.makeText(getApplicationContext(), "Provjerite da ste spojeni na internet!", Toast.LENGTH_LONG).show();
	        }
	        if (checkConnection)
	        {
	        	//sets current location parameters for the user
	            lastKnownLocation = LocationHelper.getLastKnownLocation(this);
	        }
	
			refreshMap();
		}
	}
	
	private void refreshMap() {
		new AsyncTaskHelper<List<RelativeLocation>>() {
			@Override
			protected Context getActivityContext() { return MapActivity.this; }
			@Override
			protected boolean getShowLoadingDialog() { return false; }
			
			@Override
			protected List<RelativeLocation> doGetData(String... params) throws DataSourceException {
				JsonWsLocation wsLocation = new JsonWsLocation();
				List<RelativeLocation> locations;
				
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivityContext());
				String strRadius = prefs.getString("preference_map_radius", Integer.toString(JsonWsLocation.DEFAULT_RADIUS));
				int radius = Integer.parseInt(strRadius);
				
				if (radius <= 0 || radius >= 1000)
					radius = JsonWsLocation.DEFAULT_RADIUS;
				
				if (lastKnownLocation == null)
				{
					locations = wsLocation.getAll();
				}
				else
				{
					locations = wsLocation.getAll(
						new LatLng(lastKnownLocation[0], lastKnownLocation[1]),
						radius
					);
				}
				
				return locations;
			}

			@Override
			protected void doProcess(List<RelativeLocation> locations) {
				SupportMapFragment smf = (SupportMapFragment)getSupportFragmentManager()
						.findFragmentById(R.id.map_fragment);
				GoogleMap map = smf.getMap();
				LatLngBounds.Builder builder = LatLngBounds.builder();
				
				// oèisti mapu
				map.clear();
				
				for (int i = 0; i < locations.size(); i++)
				{
					builder.include(locations.get(i).getCoordinates());
					Log.d("HOHOHO", locations.get(i).getName());
				
					MarkerOptions options = new MarkerOptions()
						.title(locations.get(i).getName())
						.position(locations.get(i).getCoordinates())
						.snippet(locations.get(i).getDistanceText());
						
						//.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.arrow_down_float));*/
					
					map.addMarker(options);
				}
				if (lastKnownLocation != null)
				{
					LatLng currentCoordinates = new LatLng(lastKnownLocation[0], lastKnownLocation[1]);
					MarkerOptions options = new MarkerOptions()
						.position(currentCoordinates)
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location_marker));
					
					map.addMarker(options);
					builder.include(currentCoordinates);
				}
				
				LatLngBounds bounds = LocationHelper.adjustBoundsForMaxZoomLevel(builder.build());
				map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
			}
		}.execute();
	}
	
	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
			.setCancelable(false)
			.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id) {
						startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					}
				}
			)
			.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id) {
						dialog.cancel();
					}
				}
			);
		
		final AlertDialog alert = builder.create();
		alert.show();
	}
	
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting()) {
			// Log.d("network", "Network available:true");
			return true;
		} else {
			// Log.d("network", "Network available:false");
			return false;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 100, this);
		
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			refreshMap();
		} else {
			buildAlertMessageNoGps();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		this.lastKnownLocation = new double[] { location.getLatitude(), location.getLongitude() };
		refreshMap();
		Log.d("MAP_LOCATION_HOHO", Double.toString(location.getLatitude()));
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_map, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
		case R.id.opt_map_refresh:
			// osvježi mapu
			refreshMap();
			break;
			
		case R.id.opt_map_settings:
			// prikaži postavke
			Intent i = new Intent(this, MapSettingsActivity.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
