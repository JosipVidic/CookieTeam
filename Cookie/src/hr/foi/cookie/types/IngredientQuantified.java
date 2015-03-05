package hr.foi.cookie.types;

/**
 * A quantified ingredient (has a quantity and unit)
 * @author Marin
 *
 */
public class IngredientQuantified extends Ingredient {
	
	Unit unit;
	double quantity;
	
	/**
	 * Create a simple ingredient from an ID and a name.
	 * @param Ingredient ID
	 * @param Ingredient name
	 */
	public IngredientQuantified(int id, String name) {
		super(id, name);
	}
	
	/**
	 * Create a simple ingredient with a category.
	 * @param ID
	 * @param Name
	 * @param ID of the category the ingredient belongs to.
	 */
	public IngredientQuantified(int id, String name, int categoryId) {
		super(id, name, categoryId);
	}
	
	/**
	 * Create a quantified ingredient (an ingredient that has a unit and an associated quantity).
	 * @param ID
	 * @param Name
	 * @param Unit associated with this ingredient.
	 * @param Quantity of this ingredient.
	 */
	public IngredientQuantified(int id, String name, Unit unit, double quantity) {
		super(id, name);
		this.unit = unit;
		this.quantity = quantity;
	}
	
	/**
	 * Create a quantified ingredient with a category.
	 * @param ID
	 * @param Name
	 * @param ID of the category the ingredient belongs to.
	 * @param Unit associated with this ingredient.
	 * @param Quantity of this ingredient.
	 */
	public IngredientQuantified(int id, String name, int categoryId, Unit unit, double quantity) {
		super(id, name, categoryId);
		this.unit = unit;
		this.quantity = quantity;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Return a quantified formatted ingredient
	 * Example: 1 kg - Salt
	 * @return A quantified formatted string
	 */
	public String getIngredientQuantifiedFormattedString() {
		
		double quantity = getQuantity();
		int quantityInt = (int) (quantity * 10);
		
		String quantityString = "";
		if (quantityInt % 10 == 0)
		{
			quantityString = Integer.toString(quantityInt / 10);
		}
		else
		{
			quantityString = Double.toString(quantity);
		}
		
		return quantityString + " " +
			getUnit().getSymbol() + " - " +
			getName();
	}
}
