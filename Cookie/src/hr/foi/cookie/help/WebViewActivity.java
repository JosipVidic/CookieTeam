package hr.foi.cookie.help;

import hr.foi.cookie.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		WebView wv;
			
		wv = (WebView) findViewById(R.id.webView1);
		
		wv.loadUrl("file:///android_asset/help.html");
		//wv.loadDataWithBaseURL("file:///android_asset/help.html", null, "text/html; charset=utf-8", "UTF-8", null);
		
		
	}
}
