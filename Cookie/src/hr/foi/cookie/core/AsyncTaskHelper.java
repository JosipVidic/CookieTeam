package hr.foi.cookie.core;

import hr.foi.cookie.core.exceptions.DataSourceException;
import hr.foi.cookie.core.exceptions.InternetConnectivityException;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Helper class for executing asynchronous data-loading tasks.
 * @author Marin
 *
 * @param <T> Type of data to process. Examples: Object, List<Object>
 */
public abstract class AsyncTaskHelper<T> extends AsyncTask<String, Integer, T> {
	private ProgressDialog pDialog;
	
	protected abstract Context getActivityContext();
	protected abstract boolean getShowLoadingDialog();
	protected Exception innerEx = null;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (getShowLoadingDialog()) {
			pDialog = new ProgressDialog(getActivityContext());
			pDialog.setMessage("Dohvaæam...");
			//pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
	}

	@Override
	protected T doInBackground(String... params)
	{
		try
		{
			T data = doGetData(params);
			if (getShowLoadingDialog())
				pDialog.cancel();
			
			return data;
		}
		catch (Exception e)
		{
			this.innerEx = e;
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
    protected void onPostExecute(T arg)
	{
		// only run doProcess if no errors occurred
		if (this.innerEx == null)
		{
			doProcess(arg);
		}
		// else handle exception
		else
		{
			if (this.innerEx.getClass().equals(DataSourceException.class))
			{
				if (this.innerEx.getCause() != null)
				{
					if (this.innerEx.getCause().getClass().equals(InternetConnectivityException.class))
					{
						Toast.makeText(
							getActivityContext(),
							"Dogodila se greška. Izgleda da niste spojeni na Internet.",
							Toast.LENGTH_LONG
						).show();
					}
				}
				else
				{
					Toast.makeText(
						getActivityContext(),
						"Dogodila se greška pri dohvaæanju podataka: " + this.innerEx.toString(),
						Toast.LENGTH_LONG
					).show();
				}
			}
			else
			{
				Toast.makeText(
					getActivityContext(),
					"Dogodila se pozadinska greška: " + this.innerEx.toString(),
					Toast.LENGTH_LONG
				).show();
				
				Log.d("COOKIE", "Error u AsyncTasku: " + this.innerEx.toString());
			}
			
			// cancel loading dialog
			if (getShowLoadingDialog())
				this.pDialog.cancel();
		}
	}

	/**
	 * Asynchronous data-loading method. This method will be called to load the necessary data.
	 * @param params
	 * @return
	 */
	protected abstract T doGetData(String... params) throws DataSourceException;
	
	/**
	 * Main UI thread processing method. 
	 * @param arg
	 */
	protected abstract void doProcess(T arg);

}
