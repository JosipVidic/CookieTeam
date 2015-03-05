package hr.foi.cookie.webservice;

import hr.foi.cookie.core.PatchInputStream;
import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.interfaces.IImageSource;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class ImageWsBase extends WsBase implements IImageSource {

	protected Bitmap getImageFromUri(String uri)
	{
		Bitmap bmp = null;
		/*
        try {
            InputStream in = new java.net.URL(uri).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;*/
		
		try {
			InputStream in = new java.net.URL(uri).openStream();
			bmp = BitmapFactory.decodeStream(new PatchInputStream(in));
		}
		catch (Exception e)
		{
			
		}
		
		return bmp;
	}
	
	@Override
	public abstract Bitmap getById(int id) throws DataSourceException;

	@Override
	protected abstract String wsUriPath();
	
}
