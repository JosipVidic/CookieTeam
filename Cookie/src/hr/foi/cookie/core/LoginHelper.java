package hr.foi.cookie.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoginHelper {
	private String firstName;
	private String lastName;
	private String email;

	private SharedPreferences sp = null;
	
	public LoginHelper(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		this.sp = prefs;
		getDetails();
	}
	
	public LoginHelper(SharedPreferences sharedPrefs) {
		this.sp = sharedPrefs;
		getDetails();
	}
	
	
	public void saveDetails() {
		SharedPreferences.Editor editor = this.sp.edit();
		
		editor.putString("login_user_firstname", this.firstName);
		editor.putString("login_user_lastname", this.lastName);
		editor.putString("login_user_email", this.email);

		// spremi izmjene
		editor.commit();
	}
	
	public void getDetails() {
		this.firstName = sp.getString("login_user_firstname", null);
		this.lastName = sp.getString("login_user_firstname", null);
		this.email = sp.getString("login_user_email", null);
	}
	
	public boolean isLoggedIn() {
		if (this.firstName != null)
			return true;
		
		getDetails();
		if (this.firstName != null)
			return true;
		return false;
	}
	
	// getters
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
