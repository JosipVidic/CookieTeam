package hr.foi.cookie.types;

public class Country {
	private int id;
	private String name;
	private String isocode;
	
	public Country(int id, String name, String isoCode) {
		this.id = id;
		this.name = name;
		this.isocode = isoCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsocode() {
		return isocode;
	}

	public void setIsocode(String isocode) {
		this.isocode = isocode;
	}
}
