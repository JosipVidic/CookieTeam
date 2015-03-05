package hr.foi.cookie.types;

public class Ingredient {
	int id;
	String name;
	int categoryId;
	
	public Ingredient(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Ingredient(int id, String name, int categoryId) {
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
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
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public IngredientQuantified getIngredientQuantified()
	{
		return new IngredientQuantified(this.id, this.name, this.categoryId);
	}
}
