package hr.foi.cookie;

import hr.foi.cookie.core.AsyncTaskHelper;
import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.database.LocalDbImage;
import hr.foi.cookie.database.LocalDbRecipe;
import hr.foi.cookie.types.IngredientQuantified;
import hr.foi.cookie.types.Recipe;
import hr.foi.cookie.webservice.ImageWsRecipe;
import hr.foi.cookie.webservice.JsonWsRecipe;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeActivity extends Activity {
	final Context context = this;
	
	ImageView imgPicture;
	Recipe fRec = null;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);
		
		this.imgPicture = (ImageView)findViewById(R.id.imgRecipePicture);
		
		final int id = getIntent().getExtras().getInt("id");
		final boolean isLocal = getIntent().getExtras().getBoolean("isLocal");
		
		
		new AsyncTaskHelper<Recipe>() {

			@Override
			protected Context getActivityContext() { return RecipeActivity.this; }

			@Override
			protected boolean getShowLoadingDialog() { return false; }

			@Override
			protected Recipe doGetData(String... params) throws DataSourceException {
				
				if (isLocal)
				{
					LocalDbRecipe localDbRrecipe = new LocalDbRecipe(getActivityContext());
					
					return localDbRrecipe.getById(id);
				}
				else
				{
					JsonWsRecipe wsRecipe = new JsonWsRecipe();
				
					return wsRecipe.getById(id);
				}
			}

			@Override
			protected void doProcess(final Recipe recipe) {
				TextView title = (TextView)findViewById(R.id.txtRecipeTitle);
				TextView description = (TextView)findViewById(R.id.txtRecipeDescription);
				TextView preparationTime = (TextView)findViewById(R.id.txtRecipePreparationTime);
				TextView ingredients = (TextView)findViewById(R.id.txtRecipeIngredients);
				
				title.setText(recipe.getName());
				description.setText(recipe.getDescription() == null ? "" : recipe.getDescription());
				
				preparationTime.setText(
					getResources().getString(R.string.preparationTime) + " " +
					recipe.getPreparationTimeString()
				);

				//Log.d("Anton i Josipa:", "Sastojci:" + " " + recipe.getIngredients().get(0).getName());
				
				ingredients.setText("");
				String ingredientList = "";
				for (IngredientQuantified ing : recipe.getIngredients())
				{
					ingredientList += ing.getIngredientQuantifiedFormattedString() + "\n";
				}
				ingredients.setText(ingredientList);

				new AsyncTaskHelper<Bitmap>() {

					@Override
					protected Context getActivityContext() {
						return RecipeActivity.this;
					}

					@Override
					protected boolean getShowLoadingDialog() {
						return true;
					}

					@Override
					protected Bitmap doGetData(String... params)
							throws DataSourceException {
						
						if (isLocal)
						{
							LocalDbImage localDbImage = new LocalDbImage();
							
							return localDbImage.byteArrayToImage(recipe.getImageLarge());
						}
						else
						{
							ImageWsRecipe wsRecipeImage = new ImageWsRecipe();
							
							return wsRecipeImage.getByIdLarge(id, getActivityContext());
						}
						
					}

					@Override
					protected void doProcess(Bitmap bitmap) {
						if (bitmap != null) {
							imgPicture.setImageBitmap(bitmap);
						}
					}
				}.execute();
				fRec = recipe;
				
				/*
				btnDownloadRecipe.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						LocalDbRecipe localDbRecipe = new LocalDbRecipe(context);
						Bitmap recipeImageLarge = ((BitmapDrawable)imgPicture.getDrawable()).getBitmap();
						
						long saveResult = localDbRecipe.saveRecipeLocally(recipe, recipeImageLarge);
						if (saveResult != -1)
						{
							Toast.makeText(context, "Recept uspješno spremljen!", Toast.LENGTH_LONG).show();
						}
						else
						{
							Toast.makeText(context, "Greška kod unosa recepta! Možda ste veæ preuzeli ovaj recept.", Toast.LENGTH_LONG).show();
						}
					}
				});		*/			
			}
			
		}.execute();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_recipe, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case R.id.opt_recipe_download:
			if (fRec != null) {
				LocalDbRecipe localDbRecipe = new LocalDbRecipe(context);
				Bitmap recipeImageLarge = ((BitmapDrawable) imgPicture.getDrawable()).getBitmap();

				long saveResult = localDbRecipe.saveRecipeLocally(fRec, recipeImageLarge);
				if (saveResult != -1) {
					Toast.makeText(context, "Recept uspješno spremljen!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(
							context,
							"Greška kod unosa recepta! Možda ste veæ preuzeli ovaj recept.",
							Toast.LENGTH_LONG).show();
				}
			}
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}
