package hr.foi.cookie.types;

import java.util.List;

import android.graphics.Bitmap;

public class Recipe {
	
	private int recipeId;
	private String name;
	private int preparationTime;
	private String description;
	private List<IngredientQuantified> ingredients;
	private int timestamp;
	
	public byte[] getImageLarge() {
		return imageLarge;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public void setImageLarge(byte[] imageLarge) {
		this.imageLarge = imageLarge;
	}

	public byte[] getImageSmall() {
		return imageSmall;
	}

	public void setImageSmall(byte[] imageSmall) {
		this.imageSmall = imageSmall;
	}

	private Bitmap picture = null;
	
	private byte[] imageLarge;
	private byte[] imageSmall;
	
	public Recipe(int recipeId, String name, int preparationTime, String description) {
		this.recipeId = recipeId;
		this.name = name;
		this.preparationTime = preparationTime;
		this.description = description;
	}
	
	public Recipe(int recipeId, String name, int preparationTime, String description, byte[] imageLarge, int timestamp) {
		this.recipeId = recipeId;
		this.name = name;
		this.preparationTime = preparationTime;
		this.description = description;
		this.imageLarge = imageLarge;
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public int getRecipeId() {
		return recipeId;
	}
	
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPreparationTime() {
		return preparationTime;
	}
	
	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}
	
	public String getPreparationTimeString()
	{
		int preparation = getPreparationTime();
		int hours = 0;
		
		while (preparation > 60)
		{
			hours++;
			preparation /= 60;
		}
		
		if (hours == 0)
			return preparation + " min";
		else
			return hours + "h " + preparation + " min";
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<IngredientQuantified> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientQuantified> ingredients) {
		this.ingredients = ingredients;
	}

	public Bitmap getPicture() {
		if (this.picture == null)
		{
			// dohvat slike sa servisa
			
		}
		
		return this.picture;
	}

	public void setPicture(Bitmap picture) {
		this.picture = picture;
	}

	public RecipePreferred getRecipePreferred() {
		RecipePreferred recipePreferred = new RecipePreferred(this.recipeId, this.name, this.preparationTime, this.description, false);
		return recipePreferred;
	}
}
