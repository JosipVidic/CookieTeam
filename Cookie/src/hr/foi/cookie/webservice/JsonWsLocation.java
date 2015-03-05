package hr.foi.cookie.webservice;

import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.City;
import hr.foi.cookie.types.Country;
import hr.foi.cookie.types.RelativeLocation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class JsonWsLocation extends JsonWsBase<RelativeLocation> {
	public final static int DEFAULT_RADIUS = 5;
	
	@Override
	protected String wsUriPath() {
		return "locations.php";
	}
	
	@Override
	public RelativeLocation getById(int id) throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelativeLocation> getAll(int offset, int limit) throws DataSourceException {
		return getAll(null, DEFAULT_RADIUS);
	}
	
	public List<RelativeLocation> getAll(LatLng nearTo, int radius) throws DataSourceException {
		List<RelativeLocation> locationList = new ArrayList<RelativeLocation>();
		JSONArray jArray = null;
		
		if (radius <= 0 || radius >= 1000)
			radius = DEFAULT_RADIUS;
		
		if (nearTo == null)
			jArray = getJsonArray();
		else
			jArray = getJsonArray("?nearto=" + nearTo.latitude + "," + nearTo.longitude + "&inradiusof=" + radius);
		
		for (int i = 0; i < jArray.length(); i++)
		{
			try {
				JSONObject obj = jArray.getJSONObject(i);
				
				int locationId = obj.optInt("locationid");
				String locationName = obj.optString("name");
				double latitude = obj.optDouble("latitude");
				double longitude = obj.optDouble("longitude");
				
				double distance = 0;
				if (nearTo != null)
					distance = obj.optDouble("distance");
				
				int cityId = obj.optInt("cityid");
				String cityName = obj.optString("cityname");
				
				int countryId = obj.optInt("countryid");
				String countryName = obj.optString("countryname");
				String countryCode = obj.optString("countrycode");
				
				Country country = new Country(countryId, countryName, countryCode);
				City city = new City(cityId, cityName, country);
				
				RelativeLocation relLoc = new RelativeLocation(
					locationId,
					locationName,
					new LatLng(latitude, longitude),
					distance
				);
				relLoc.setCity(city);
				
				locationList.add(relLoc);
				
			} catch (JSONException e) {
				throw new DataSourceException();
			}
		}
		
		return locationList;
	}

}