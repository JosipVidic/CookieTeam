package hr.foi.cookie.types;

/**
 * A showable category, which is a category that is either selected or not.
 * @author Marin
 *
 */
public class ShowableCategory {
	private int id;
	private String name;
	private ShowableCategoryTypes type;
	private boolean selected = false;
	
	/**
	 * Top category ID in case this is a category, ingredient category ID otherwise.
	 */
	private int categoryId;
	
	/**
	 * Create a new simple category, which will not be selected by default.
	 * @param ID
	 * @param Name
	 * @param Parent category ID
	 * @param Type (CATEGORY or INGREDIENT, a ShowableCategory can be either).
	 */
	public ShowableCategory(int id, String name, int categoryId, ShowableCategoryTypes type) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.categoryId = categoryId;
	}
	
	/**
	 * Create a selected category.
	 * @param ID
	 * @param Name
	 * @param Parent category ID
	 * @param Type (CATEGORY or INGREDIENT, a ShowableCategory can be either).
	 * @param Whether this category is selected.
	 */
	public ShowableCategory(int id, String name, int categoryId, ShowableCategoryTypes type, boolean selected) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.categoryId = categoryId;
		this.selected = selected;
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
	public ShowableCategoryTypes getType() {
		return type;
	}
	public void setType(ShowableCategoryTypes type) {
		this.type = type;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * This will return an Object that is either an Ingredient if this category is actually
	 * a showable ingredient, or a Category if it truly is a category.
	 * 
	 * You should use getType() to determine which of these two classes to cast to.
	 * @return Object representation of the actual type of data behind this category.
	 */
	public Object getRealType() {
		if (this.type == ShowableCategoryTypes.CATEGORY) {
			Category cat = new Category(this.id, this.name, this.categoryId);
			return cat;
		}
		else {
			Ingredient ingredient = new Ingredient(this.id, this.name, this.categoryId);
			return ingredient;
		}
	}
}
