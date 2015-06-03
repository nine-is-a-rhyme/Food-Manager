package rhyme.a.is.nine.foodmanager.gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.adapter.IngredientsAdapter;
import rhyme.a.is.nine.foodmanager.recipe.Recipe;
import rhyme.a.is.nine.foodmanager.util.DownloadImage;

public class RecipeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Recipe rec = (Recipe) getIntent().getExtras().get("recipe");
        setTitle(rec.getName());

        new DownloadImage((ImageView) findViewById(R.id.imageView)).execute("http://"+rec.getImageURL());

        ListView ingredients = (ListView) findViewById(R.id.ingredientslist);
        IngredientsAdapter adapter = new IngredientsAdapter(getBaseContext(), rec);
        ingredients.setAdapter(adapter);

        TextView duration = (TextView) findViewById(R.id.duration);
        duration.setText("Kochdauer: " + rec.getDuration());

        TextView preparation = (TextView) findViewById(R.id.preparation);
        preparation.setText(rec.getPreparation());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
