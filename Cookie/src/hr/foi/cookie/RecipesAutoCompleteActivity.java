package hr.foi.cookie;

import hr.foi.cookie.core.AsyncTaskHelper;
import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.Recipe;
import hr.foi.cookie.webservice.JsonWsRecipe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class RecipesAutoCompleteActivity extends Activity {
    private List<Recipe> recipes = new ArrayList<Recipe>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setTheme(android.R.style.Theme);
		setContentView(R.layout.activity_recipes_autocomplete);
		
		ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(this,
                android.R.layout.simple_dropdown_item_1line, recipes);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.recipes_auto_complete);
        
        textView.setAdapter(adapter);
        textView.setThreshold(1);
        
        textView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				Intent i = new Intent(getApplicationContext(), RecipeActivity.class);
				i.putExtra("id", ((Recipe)parent.getItemAtPosition(pos)).getRecipeId());
				startActivity(i);
			}
		});
        
        new AsyncTaskHelper<List<Recipe>>() {

			@Override
			protected Context getActivityContext() { return RecipesAutoCompleteActivity.this; }

			@Override
			protected boolean getShowLoadingDialog() { return true; }

			@Override
			protected List<Recipe> doGetData(String... params) throws DataSourceException {
				JsonWsRecipe wsRecipe = new JsonWsRecipe();
				return wsRecipe.getAll();
			}

			@Override
			protected void doProcess(List<Recipe> arg) {
				for (int i = 0; i < arg.size(); i++)
           	 	{
					recipes.add(arg.get(i));
           	 	}
			}
		}.execute();
	}
}
