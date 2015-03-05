package hr.foi.cookie;

import hr.foi.cookie.core.LoginHelper;
import hr.foi.cookie.core.LoginTask;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;

public class LoginActivity extends Activity {

	protected Intent returnIntent = null;

	//private static final String TAG = "CookieLoginActivity";
	private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
	public static final String EXTRA_ACCOUNTNAME = "extra_accountname";
	//public static final String EXTRA_FIRSTNAME = "extra_firstname";
	//public static final String EXTRA_SURNAME = "extra_surname";
	
	
	private AccountManager mAccountManager;

	private Spinner mAccountTypesSpinner;

	static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
	static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;

	private String[] mNamesArray;
    private String mEmail;
    //private String firstName;
    //private String lastName;

	public static String TYPE_KEY = "type_key";

	public static enum Type {
		FOREGROUND, BACKGROUND, BACKGROUND_WITH_SYNC
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// dohvati imena raèuna na ureðaju
		mNamesArray = getAccountNames();
        mAccountTypesSpinner = initializeSpinner(R.id.login_accounts_spinner, mNamesArray);

		//SharedPreferences sp = getSharedPreferences("cookie_login", MODE_PRIVATE);

		// dohvati podatke o iduæoj aktivnosti
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Class<?> returnClass = (Class<?>) extras.get("returnclass");
			Bundle returnExtras = (Bundle) extras.get("returnextras");
			
			if (returnClass == null) {
				returnClass = MainActivity.class;
			}
			returnIntent = new Intent(this, returnClass);

			if (returnExtras != null)
				returnIntent.putExtras(returnExtras);
			
			if (extras.containsKey(EXTRA_ACCOUNTNAME)) {
				
	            mEmail = extras.getString(EXTRA_ACCOUNTNAME);
	            mAccountTypesSpinner.setSelection(getIndex(mNamesArray, mEmail));
	            
	            LoginHelper lh = new LoginHelper(this);
	            
	            if (lh.isLoggedIn()) {	            	
	            	startActivity(returnIntent);
	            }
	            
	            //LoginTask lt = new LoginTask(this, mEmail, SCOPE, REQUEST_CODE_RECOVER_FROM_AUTH_ERROR);
	            //lt.execute();
	            
	            //Toast.makeText(this, mEmail, Toast.LENGTH_LONG).show();
	            //sp.edit().putString("login_name", t)
	        }
		}
		
		// u sluèaju da nema google raèuna, ispiši smislenu poruku
		Button getToken = (Button) findViewById(R.id.login_login_button);
        getToken.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int accountIndex = mAccountTypesSpinner.getSelectedItemPosition();
                
                if (accountIndex < 0) {
                    // this happens when the sample is run in an emulator which has no google account
                    // added yet.
                    Toast.makeText(
                    	getApplicationContext(),
                    	"Nemate dodanih Google raèuna. Molimo da dodate raèun na Vašem ureðaju prije logina.",
                    	Toast.LENGTH_LONG
                    ).show();
                    return;
                }
                mEmail = mNamesArray[accountIndex];
                LoginTask lt = new LoginTask(getApplicationContext(), mEmail, SCOPE, REQUEST_CODE_RECOVER_FROM_AUTH_ERROR);
                lt.execute();
            }
        });
        
	}

	private Spinner initializeSpinner(int id, String[] values) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			this,
			android.R.layout.simple_spinner_item,
			values
		);
		
		Spinner spinner = (Spinner) findViewById(id);
		spinner.setAdapter(adapter);
		
		return spinner;
	}

	private String[] getAccountNames() {
		mAccountManager = AccountManager.get(this);

		Account[] accounts = mAccountManager
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];

		for (int i = 0; i < names.length; i++) {
			names[i] = accounts[i].name;
		}
		return names;
	}

	/**
	 * Nastavi do aktivnosti nakon logina.
	 */
	protected void proceed() {
		if (this.returnIntent == null)
			this.returnIntent = new Intent(this, MainActivity.class);

		startActivity(this.returnIntent);
	}

	private int getIndex(String[] array, String element) {
		for (int i = 0; i < array.length; i++) {
			if (element.equals(array[i])) {
				return i;
			}
		}
		return 0; // default to first element.
	}
}
