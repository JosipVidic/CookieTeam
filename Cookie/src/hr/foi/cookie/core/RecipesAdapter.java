package hr.foi.cookie.core;

import hr.foi.cookie.R;
import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.database.LocalDbRecipe;
import hr.foi.cookie.types.Recipe;
import hr.foi.cookie.webservice.ImageWsRecipe;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipesAdapter extends ArrayAdapter<Recipe> {
	public final int NO_CUTOFF = -1;
	
	Context context;
	int layoutResourceId;
	Recipe data[] = null;
	int cutoff = -1;
	boolean useLocally = false;

	public boolean isUseLocally() {
		return useLocally;
	}

	public void setUseLocally(boolean useLocally) {
		this.useLocally = useLocally;
	}

	public RecipesAdapter(Context context, int layoutResourceId, Recipe[] data) {
		super(context, layoutResourceId, data);

		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	public RecipesAdapter(Context context, int layoutResourceId, Recipe[] data, int cutoff) {
		super(context, layoutResourceId, data);

		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.cutoff = cutoff;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		WeatherHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new WeatherHolder();

			holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
			holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
			

			row.setTag(holder);
		} else {
			holder = (WeatherHolder) row.getTag();
		}

		Recipe recipe = data[position];
		holder.txtTitle.setText(recipe.getName());
		// holder.imgIcon.setImageResource(weather);

		final int recipeId = recipe.getRecipeId();
		final ImageView bmImage = holder.imgIcon;
		final Context rContext = context;
		new AsyncTaskHelper<Bitmap>() {

			@Override
			protected Context getActivityContext() {
				return rContext;
			}

			@Override
			protected boolean getShowLoadingDialog() {
				return false;
			}

			@Override
			protected Bitmap doGetData(String... params)
					throws DataSourceException {
				
				if (useLocally)
				{
					LocalDbRecipe localDbRecipe = new LocalDbRecipe(getActivityContext());
					return localDbRecipe.getRecipeImage(recipeId);
				}
				else
				{	
					ImageWsRecipe wsRecipeImage = new ImageWsRecipe();
					return wsRecipeImage.getById(recipeId);
				}
			}

			@Override
			protected void doProcess(Bitmap bitmap) {
				if (bitmap != null) {
					bmImage.setImageBitmap(bitmap);
				}
			}
		}.execute();

		return row;
	}
	
	static class WeatherHolder {
		ImageView imgIcon;
		TextView txtTitle;
	}
}