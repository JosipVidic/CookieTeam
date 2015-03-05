package hr.foi.cookie;

import hr.foi.cookie.core.AsyncTaskHelper;
import hr.foi.cookie.core.MyExpandableListAdapter;
import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.types.Category;
import hr.foi.cookie.types.Recipe;
import hr.foi.cookie.types.RecipePreferred;
import hr.foi.cookie.types.ShowableCategory;
import hr.foi.cookie.types.ShowableCategoryTypes;
import hr.foi.cookie.webservice.JsonWsCategory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.R.integer;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class CategoryExpandableActivity extends ExpandableListActivity {
	TextView tvCategory1;
	
    private MyExpandableListAdapter expListAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(android.R.style.Theme);
        setContentView(R.layout.activity_expandable_category);
        tvCategory1 = (TextView)findViewById(R.id.tvCategory1);
        
        new AsyncTaskHelper<List<ShowableCategory>>() {

			@Override
			protected Context getActivityContext() { return CategoryExpandableActivity.this; }

			@Override
			protected boolean getShowLoadingDialog() { return true; }

			@Override
			protected List<ShowableCategory> doGetData(String... params) throws DataSourceException {
				JsonWsCategory wsCategory = new JsonWsCategory();
				return wsCategory.getAll();
			}

			@Override
			protected void doProcess(List<ShowableCategory> arg) {
				ArrayList<ArrayList<ShowableCategory>> groups = new ArrayList<ArrayList<ShowableCategory>>();
				ArrayList<ShowableCategory> topCategories = new ArrayList<ShowableCategory>();
				ArrayList<Integer> topCategoryIds = new ArrayList<Integer>();
				
				for (int i = 0; i < arg.size(); i++)
				{
					if (arg.get(i).getType().equals(ShowableCategoryTypes.CATEGORY))
            	 	{
						Category topCat = (Category)arg.get(i).getRealType();
						if (topCat.getTopCategoryId() == 0)
						{
							topCategories.add(arg.get(i));
							topCategoryIds.add(topCat.getCategoryId());
            	 		}
            	 	}
				}
        	 	for (int i = 0; i < topCategoryIds.size(); i++)
        	 	{
        	 		groups.add(new ArrayList<ShowableCategory>());
        	 		
        	 		for (int j = 0; j < arg.size(); j++)
        	 		{
        	 			int topCatId = arg.get(j).getCategoryId();
        	 			if (topCatId != 0)
	        	 		{
	        	 			if (topCatId == topCategoryIds.get(i))
	        	 				groups.get(i).add(arg.get(j));
        	 			}
        	 		}
        	 	}
        	 	expListAdapter = new MyExpandableListAdapter(
        	 		CategoryExpandableActivity.this,
        	 		topCategories,
        	 		groups
        	 	);
        	 	setListAdapter(expListAdapter);
			}
		}.execute();
		
		Button btnSearchRecipes = (Button)findViewById(R.id.btn_get_data);
		btnSearchRecipes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AsyncTaskHelper<String>() {

					@Override
					protected Context getActivityContext() {
						return CategoryExpandableActivity.this;
					}

					@Override
					protected boolean getShowLoadingDialog() {
						return true;
					}

					@Override
					protected String doGetData(String... params) {
						
						List<ShowableCategory> selectedCategories = new ArrayList<ShowableCategory>();
						selectedCategories = expListAdapter.getCheckedCategories();
						
						if (!selectedCategories.isEmpty()) {
							String ids = "";
							for (ShowableCategory category : selectedCategories) {
								ids += (category.getType() == ShowableCategoryTypes.CATEGORY ? "c": "i") + category.getId() + ",";
							}
							ids = ids.substring(0, ids.length() - 1);

							return ids;
						}
						return null;
					}

					@Override
					protected void doProcess(String ids) {
						if (ids == null)
						{
							Toast.makeText(getActivityContext(), "Molimo odaberite barem jednu kategoriju!", Toast.LENGTH_LONG).show();
						}
						else
						{
							Intent i = new Intent(getActivityContext(), RecipesActivity.class);
							i.putExtra("ids", ids);
							startActivity(i);
						}
					}
				}.execute();
			}
		});
    }
}

