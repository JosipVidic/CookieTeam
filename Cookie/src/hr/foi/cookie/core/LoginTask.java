package hr.foi.cookie.core;

import hr.foi.cookie.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableNotifiedException;

public class LoginTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "TokenInfoTask";
	private static final String NAME_KEY = "given_name";
	private static final String SURNAME_KEY = "family_name";
	protected Context context;

	protected String mScope;
	protected String mEmail;
	protected int mRequestCode;
	
	protected String firstName;
	protected String surName;
	
	public LoginTask(Context context, String email, String scope, int requestCode) {
		this.context = context;
		this.mScope = scope;
		this.mEmail = email;
		this.mRequestCode = requestCode;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			fetchNameFromProfileServer();
		} catch (IOException ex) {
			onError("Following Error occured, please try again. "
					+ ex.getMessage(), ex);
		} catch (JSONException e) {
			onError("Bad response: " + e.getMessage(), e);
		}
		return null;
	}
	
	protected void onError(String msg, Exception e) {
        if (e != null) {
          Log.e(TAG, "Exception: ", e);
        }
        //Toast.makeText(context, "Greška se dogodila: " + e, Toast.LENGTH_LONG).show();
    }
	
	private Intent makeCallback(String accountName) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(LoginActivity.EXTRA_ACCOUNTNAME, accountName);
		context.startActivity(intent);
		
		return intent;
	}
	
	protected String fetchToken() throws IOException {
		try {
			return GoogleAuthUtil.getTokenWithNotification(
				context,
				mEmail,
				mScope,
				null,
				makeCallback(mEmail)
			);
		} catch (UserRecoverableNotifiedException userRecoverableException) {
			// Unable to authenticate, but the user can fix this.
			// Forward the user to the appropriate activity.
			onError("Could not fetch token.", null);
		} catch (GoogleAuthException fatalException) {
			onError("Unrecoverable error " + fatalException.getMessage(), fatalException);
		}
		return null;
	}
	
	private void fetchNameFromProfileServer() throws IOException, JSONException {
		String token = fetchToken();
		if (token == null) {
			// error has already been handled in fetchToken()
			return;
		}
		URL url = new URL(
			"https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token
		);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		int sc = con.getResponseCode();
		if (sc == 200) {
			InputStream is = con.getInputStream();
			//String name = getFirstName(readResponse(is));
			
			//Toast.makeText(context, "Zdravo, " + name, Toast.LENGTH_LONG).show();
			String json = readResponse(is);
			this.firstName = getFirstName(json);
			this.surName = getLastName(json);
			is.close();
			
			LoginHelper lh = new LoginHelper(this.context);
			lh.setFirstName(firstName);
			lh.setLastName(surName);
			lh.setEmail(mEmail);
			lh.saveDetails();
			
			return;
		} else if (sc == 401) {
			GoogleAuthUtil.invalidateToken(context, token);
			onError("Server auth error, please try again.", null);
			Log.i(TAG,
					"Server auth error: " + readResponse(con.getErrorStream()));
			return;
		} else {
			onError("Server returned the following error code: " + sc, null);
			return;
		}
	}
	
	 /**
     * Reads the response from the input stream and returns it as a string.
     */
    private static String readResponse(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int len = 0;
        while ((len = is.read(data, 0, data.length)) >= 0) {
            bos.write(data, 0, len);
        }
        return new String(bos.toByteArray(), "UTF-8");
    }
    
    
    /**
     * Parses the response and returns the first name of the user.
     * @throws JSONException if the response is not JSON or if first name does not exist in response
     */
    private String getFirstName(String jsonResponse) throws JSONException {
      JSONObject profile = new JSONObject(jsonResponse);
      return profile.getString(NAME_KEY);
    }
    private String getLastName(String jsonResponse) throws JSONException {
    	JSONObject profile = new JSONObject(jsonResponse);
    	return profile.getString(SURNAME_KEY);
    }

}
	  
