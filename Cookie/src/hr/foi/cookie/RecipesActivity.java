package hr.foi.cookie;

import hr.foi.cookie.core.AsyncTaskHelper;
import hr.foi.cookie.core.RecipesAdapter;
import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.Recipe;
import hr.foi.cookie.types.RecipePreferred;
import hr.foi.cookie.webservice.JsonWsRecipe;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecipesActivity extends Activity {
	private ListView listView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes);
		
		final String ids = getIntent().getExtras().getString("ids");
		
		new AsyncTaskHelper<List<RecipePreferred>>() {

			@Override
			protected Context getActivityContext() { return RecipesActivity.this; }

			@Override
			protected boolean getShowLoadingDialog() { return true; }

			@Override
			protected List<RecipePreferred> doGetData(String... params) throws DataSourceException {
				JsonWsRecipe wsRecipe = new JsonWsRecipe();
				List<RecipePreferred> recipes = wsRecipe.searchPreferred(ids);
				
				return recipes;
			}

			@Override
			protected void doProcess(List<RecipePreferred> recipes) {
				RecipePreferred[] recipe_data = new RecipePreferred[recipes.size()];
				recipes.toArray(recipe_data);	
				
				int cutoff = -1;
				
				for (RecipePreferred rec : recipes)
				{
					if (!rec.isPreferred())
					{
						break;
					}
					cutoff = rec.getRecipeId();
				}
				
				final RecipesAdapter adapter = new RecipesAdapter(
					getActivityContext(),
					R.layout.recipes_item_row,
					recipe_data,
					cutoff
				);
				
		        listView1 = (ListView)findViewById(R.id.listView1);
		        listView1.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> l, View v, int position, long id) {
						// TODO Auto-generated method stub
						
					    Recipe rec = adapter.getItem(position - 1);
					    int recipeId = rec.getRecipeId();
					    
					    //Toast.makeText(getActivityContext(), "You have chosen the pen: " + " " + pen, Toast.LENGTH_LONG).show();
					    
					    Intent i = new Intent(getActivityContext(), RecipeActivity.class);
					    i.putExtra("id", recipeId);
					    
					    startActivity(i);
					}
				});
		        
		        View header = (View)getLayoutInflater().inflate(R.layout.recipes_header_row, null);
		        listView1.addHeaderView(header);
		        listView1.setAdapter(adapter);
			}
		}.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
}
