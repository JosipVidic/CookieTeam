package hr.foi.cookie.types;

import hr.foi.cookie.interfaces.ILocation;

import com.google.android.gms.maps.model.LatLng;

/**
 * Object that represents a Location.
 * @author Marin
 *
 */
public class Location implements ILocation {
	protected int id;
	protected String name;
	protected LatLng coordinates;
	
	protected City city = null;
	
	public Location(int id, String name, LatLng coordinates) {
		this.id = id;
		this.name = name;
		this.coordinates = coordinates;
	}
	
	public Location(int id, String name, double latitude, double longitude) {
		this.id = id;
		this.name = name;
		this.coordinates = new LatLng(latitude, longitude);
	}
	
	public Location(int id, String name, LatLng coordinates, City city) {
		this.id = id;
		this.name = name;
		this.coordinates = coordinates;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LatLng getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
