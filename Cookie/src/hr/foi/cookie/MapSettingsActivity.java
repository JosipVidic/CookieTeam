package hr.foi.cookie;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MapSettingsActivity extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings_map);
	}
}
