package hr.foi.cookie;

import hr.foi.cookie.core.AsyncTaskHelper;
import hr.foi.cookie.core.RecipesAdapter;
import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.database.LocalDbRecipe;
import hr.foi.cookie.types.Recipe;
import hr.foi.cookie.webservice.JsonWsRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class RecipesFilterActivity extends Activity {
	private ListView listView;
	private List<Recipe> recipesList = new ArrayList<Recipe>();
	private RecipesAdapter adapter;
	private Recipe[] recipe_data;
	
	private OnItemClickListener listViewOnItemClick;
	private OnItemSelectedListener spinnerOnItemSelected;
	
    private List<Recipe> recipes = new ArrayList<Recipe>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_filter);
		
		final boolean isLocal = getIntent().getExtras().getBoolean("isLocal");

		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		
		final ArrayAdapter<Recipe> arrayAdapter = new ArrayAdapter<Recipe>(this,
                android.R.layout.simple_dropdown_item_1line, recipes);
        final AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.recipes_auto_complete);
        
        textView.setAdapter(arrayAdapter);
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
			protected Context getActivityContext() { return RecipesFilterActivity.this; }

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
		
		new AsyncTaskHelper<List<Recipe>>() {

			@Override
			protected Context getActivityContext() {
				return RecipesFilterActivity.this;
			}

			@Override
			protected boolean getShowLoadingDialog() {
				return true;
			}

			@Override
			protected List<Recipe> doGetData(String... params)
					throws DataSourceException {
				
				if (isLocal)
				{
					runOnUiThread(new Runnable() {
					     @Override
					     public void run() {
					    	 textView.setVisibility(View.GONE);
					     }
					});
					
					LocalDbRecipe localDbRecipe = new LocalDbRecipe(getActivityContext());

					return localDbRecipe.getAll();
				}
				else
				{
					/*
					runOnUiThread(new Runnable() {
					     @Override
					     public void run() {
					    	 textView.setVisibility(View.GONE);
					     }
					});*/
					
					JsonWsRecipe wsRecipe = new JsonWsRecipe();	
					
					return wsRecipe.getAll();
				}			
			}

			@Override
			protected void doProcess(List<Recipe> recipes) {
				recipe_data = new Recipe[recipes.size()];
				recipes.toArray(recipe_data);	
				recipesList = recipes;
				
				adapter = new RecipesAdapter(
					getActivityContext(),
					R.layout.recipes_item_row,
					recipe_data
				);
				if (isLocal)
				{
					adapter.setUseLocally(true);
				}
				
				listView = (ListView)findViewById(R.id.listView1);
		        View header = (View)getLayoutInflater().inflate(R.layout.recipes_header_row, null);
		        listView.addHeaderView(header);
		        listView.setAdapter(adapter);
		        
		        listView.setOnItemClickListener(listViewOnItemClick);
		        
				/*for (Recipe recipe : recipes)
           	 	{
					recipesList.add(recipe);      	 
           	 	}*/
				//Log.d("Josipa", recipesList.toString());
			}
		}.execute();
		
		spinnerOnItemSelected = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
					switch (arg2)
					{
						case 0:
								Collections.sort(recipesList, new Comparator<Recipe>() {

									@Override
									public int compare(Recipe recipe1, Recipe recipe2) {
										
										return recipe1.getName().compareTo(recipe2.getName());
									}
								});
								if (!recipesList.isEmpty())
								{
									recipesList.toArray(recipe_data);
									//adapter.notifyDataSetChanged();
									listView.setAdapter(adapter);
								}
							break;
						case 1:
								Collections.sort(recipesList, new Comparator<Recipe>() {

									@Override
									public int compare(Recipe recipe1, Recipe recipe2) {
										
										return Integer.valueOf(recipe1.getPreparationTime()).compareTo(recipe2.getPreparationTime());
									}
								});
								
								recipesList.toArray(recipe_data);
								//adapter.notifyDataSetChanged();
								listView.setAdapter(adapter);
							break;
						//TODO
						//case 2:, case 3:
					}

				}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		listViewOnItemClick = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent i = new Intent(getApplicationContext(), RecipeActivity.class);
				i.putExtra("id", ((Recipe)arg0.getItemAtPosition(arg2)).getRecipeId());
				if (isLocal)
				{
					i.putExtra("isLocal", true);
				}
				else
				{
					i.putExtra("isLocal", false);
				}
				startActivity(i);
			}
		};

		spinner.setOnItemSelectedListener(spinnerOnItemSelected);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_recipes_filter, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case R.id.opt_recipes_sync:
				new AsyncTaskHelper<HashMap<Integer, Integer>>() {

					@Override
					protected Context getActivityContext() {
						
						return RecipesFilterActivity.this;
					}

					@Override
					protected boolean getShowLoadingDialog() {
						return false;
					}

					@Override
					protected HashMap<Integer, Integer> doGetData(String... params)
							throws DataSourceException {
						
						LocalDbRecipe localDbRecipe = new LocalDbRecipe(getActivityContext());
						
						List<Recipe> recipes = localDbRecipe.getAll();
						List<Integer> ids = new ArrayList<Integer>();
						for (int i = 0; i < recipes.size(); i++)
						{
							ids.add(recipes.get(i).getRecipeId());
						}

						JsonWsRecipe jsonWsRecipe = new JsonWsRecipe();
						try {
							return jsonWsRecipe.checkTimestamps(ids);
						} catch (JSONException e) {
							e.printStackTrace();
							return null;
						}
					}

					@Override
					protected void doProcess(HashMap<Integer, Integer> recipeHashMap){
						HashMap<Integer, Integer> hashMap = recipeHashMap;
						final LocalDbRecipe localDbRecipe = new LocalDbRecipe(RecipesFilterActivity.this);
						final List<Integer> recipeIds = new ArrayList<Integer>();		
						
						for (Integer key : hashMap.keySet())
						{
							try {
								Recipe recipe = localDbRecipe.getById(key);
								if(recipe.getTimestamp() != hashMap.get(key))
								{
									recipeIds.add(recipe.getRecipeId());
									//Log.d("Anton", "Recipes to sync (timestamp): " + " " + recipe.getTimestamp() + " " + recipe.getName() + " " + hashMap.get(key));
								}
							} catch (DataSourceException e) {
								e.printStackTrace();
							}
						}
						
						if (recipeIds.isEmpty())
						{
							Toast.makeText(RecipesFilterActivity.this, 
										   "Nema promjena u receptima od Vašeg preuzimanja!",
										   Toast.LENGTH_LONG).show();
						}
						else
						{
							new AsyncTaskHelper<List<Recipe>>() {
	
								@Override
								protected Context getActivityContext() {
									return RecipesFilterActivity.this;
								}
	
								@Override
								protected boolean getShowLoadingDialog() {
									return true;
								}
	
								@Override
								protected List<Recipe> doGetData(String... params)
										throws DataSourceException {
									JsonWsRecipe jsonWsRecipe = new JsonWsRecipe();
									
									return jsonWsRecipe.getNonSyncedRecipes(recipeIds);
								}
								long numberOfSyncedRecipes;
								@Override
								protected void doProcess(List<Recipe> recipesList) {								
									for (int i = 0; i < recipesList.size(); i++)
									{
										numberOfSyncedRecipes += localDbRecipe.updateRecipe(recipesList.get(i));
									}
									Toast.makeText(RecipesFilterActivity.this, 
												   "Broj sinkroniziranih recepata: " + numberOfSyncedRecipes,
												   Toast.LENGTH_LONG).show();
								}
							}.execute();
						}
					}
				}.execute();
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
