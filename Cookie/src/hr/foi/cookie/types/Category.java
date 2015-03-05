package hr.foi.cookie.types;

public class Category {
	
	private int categoryId;
	private String name;
	private int topCategoryId;
	
	public Category(int categoryId, String name, int topCategoryId) {
		this.categoryId = categoryId;
		this.name = name;
		this.topCategoryId = topCategoryId;
	}
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	
	public int getTopCategoryId() {
		return topCategoryId;
	}
	
	public void setTopCategoryId(int topCategoryId) {
		this.topCategoryId = topCategoryId;
	}

	
}