package rhyme.a.is.nine.foodmanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;


public class recipe_external extends ActionBarActivity {

    public List<String> recipe_search_entries_;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_external);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_external, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String createURL(){
        /* creates something like http://www.chefkoch.de/rs/s0e1z1/karotte+kartoffel/Rezepte.html */

        if(!recipe_search_entries_.isEmpty()) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("http://www.chefkoch.de/rs/s0e1z1/");

            for(Iterator<String> entry = recipe_search_entries_.iterator(); entry.hasNext(); ) {
                stringBuilder.append(entry);
                if(entry.hasNext()) {
                    stringBuilder.append("+");
                }
            }

            stringBuilder.append("/Rezepte.html");

            url = stringBuilder.toString();

        }
        else {
            url = null;
        }


        return url;

    }


}
