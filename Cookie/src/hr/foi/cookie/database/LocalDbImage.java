package hr.foi.cookie.database;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LocalDbImage {

	public byte[] imageToByteArray(Bitmap bitmap)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] bitmapData = stream.toByteArray();
		
		return bitmapData;
	}
	
	public Bitmap byteArrayToImage(byte[] byteArray )
	{	
		return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	}
}
