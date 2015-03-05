package hr.foi.cookie.help;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.webkit.WebView;

public class WebViewHelper {

	public String getHtmlFromAsset(InputStream is) {
        InputStream inputStream = is;
        StringBuilder builder = new StringBuilder();
        String htmlString = null;
        try {
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                htmlString = builder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlString;
    }
	
	public void loadHtmlPage(WebView webView) {
        String htmlString = getHtmlFromAsset(null);
        WebView wv = webView;
        if (htmlString != null)
            wv.loadDataWithBaseURL("file:///android_asset/", htmlString, "text/html", "UTF-8", null);
    }
}
