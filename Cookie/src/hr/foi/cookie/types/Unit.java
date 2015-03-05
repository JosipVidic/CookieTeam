package hr.foi.cookie.types;

public class Unit {
	int id;
	String name;
	String symbol;
	
	public Unit(int id, String name, String symbol)
	{
		this.id = id;
		this.name = name;
		this.symbol = symbol;
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
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
