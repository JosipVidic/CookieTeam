package hr.foi.cookie;

import hr.foi.cookie.help.WebViewActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
final Context context = this;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(android.R.style.Theme);
        setContentView(R.layout.activity_main);
        
        ImageButton mainActivityButtons[] = new ImageButton[] {
        	(ImageButton)findViewById(R.id.image_button_fridge),
        	(ImageButton)findViewById(R.id.image_button_search),
        	(ImageButton)findViewById(R.id.image_button_world_map),
        	(ImageButton)findViewById(R.id.image_button_my_cookie),
        	(ImageButton)findViewById(R.id.image_button_help),
        	(ImageButton)findViewById(R.id.image_button_login)
        };
        
        for (ImageButton imageButton : mainActivityButtons) {
        	imageButton.setOnClickListener(myOnClickListener);
        }
    }
    
    OnClickListener myOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			switch (v.getId())
			{
				case R.id.image_button_fridge:
					Intent i = new Intent(context, CategoryExpandableActivity.class);
					startActivity(i);
					break;
				case R.id.image_button_search:
					Intent j = new Intent(context, RecipesFilterActivity.class);
					j.putExtra("isLocal", false);
					startActivity(j);
					break;
				case R.id.image_button_world_map:
					Intent k = new Intent(context, MapActivity.class);
					startActivity(k);
					break;
				case R.id.image_button_my_cookie:
					Intent l = new Intent(context, RecipesFilterActivity.class);
					l.putExtra("isLocal", true);
					startActivity(l);
					break;
				case R.id.image_button_help:
					Intent m = new Intent(context, WebViewActivity.class);
					startActivity(m);
					break;
				case R.id.image_button_login:
					Intent n = new Intent(context, LoginActivity.class);
					startActivity(n);
					break;
			}	
		}
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
