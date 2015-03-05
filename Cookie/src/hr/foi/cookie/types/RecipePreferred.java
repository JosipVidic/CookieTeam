package hr.foi.cookie.types;

/**
 * Describes a preferred recipe (has an optional "preferred" state).
 * @author Marin
 *
 */
public class RecipePreferred extends Recipe {
	
	boolean preferred = false;
	
	/**
	 * Create a simple recipe. The recipe will be non-preferred by default.
	 * @param ID
	 * @param Name
	 * @param Preparation time
	 * @param Description
	 */
	public RecipePreferred(
		int recipeId,
		String name,
		int preparationTime,
		String description
	) {
		super(recipeId, name, preparationTime, description);
	}
	
	/**
	 * Create a preferred recipe with a settable "preferred" attribute.
	 * @param ID
	 * @param Name
	 * @param Preparation time
	 * @param Description
	 * @param Whether this recipe is preferred
	 */
	public RecipePreferred(
		int recipeId,
		String name,
		int preparationTime,
		String description,
		boolean preferred
	) {
		super(recipeId, name, preparationTime, description);
		this.preferred = preferred;
	}
	
	public boolean isPreferred() {
		return preferred;
	}
	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}
}
