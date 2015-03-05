package hr.foi.cookie.webservice;

import hr.foi.cookie.core.exceptions.DataSourceException;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.WindowManager;

public class ImageWsRecipe extends ImageWsBase {
	
	@Override
	public Bitmap getById(int id) throws DataSourceException {
        return getImageFromUri(String.format(getWsUri(), id));
	}
	public Bitmap getByIdLarge(int id, Context context) throws DataSourceException {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		
		@SuppressWarnings("deprecation")
		int width = display.getWidth();
		
		return getImageFromUri(String.format(
				wsUriBase + "resize.php?w=" + (width > 800 ? 300 : width) + "&s=/pictures/recipes/%d.png",
			id
		));
	}

	@Override
	protected String wsUriPath() {
		return "resize.php?w=45&h=45&s=/pictures/recipes/%d.png";
	}

}
