package rhyme.a.is.nine.foodmanager.gui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.fragment.RecipeFragment;



public class MainActivity extends ActionBarActivity implements
        ActionBar.TabListener {

    private List<String> recipe_search_entries_;
    private String url;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Kühlschrank", "Einkaufsliste", "Rezepte"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }


    public View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch(v.getId()) {
                case R.id.button_web:

                    Intent myIntent = new Intent(MainActivity.this, Recipe.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                    break;


            }
        }
    };


    public String createURL(){
              /* creates something like http://www.chefkoch.de/ms/s0/karotte+kartoffel/Rezepte.html */

        if(!recipe_search_entries_.isEmpty()) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("http://mobile.chefkoch.de/ms/s0/");

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
