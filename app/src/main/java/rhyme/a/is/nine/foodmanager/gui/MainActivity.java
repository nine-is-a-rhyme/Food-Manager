package rhyme.a.is.nine.foodmanager.gui;

import android.support.v7.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.fragment.RecipeFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.WebViewFragment;

public class MainActivity extends ActionBarActivity implements
        ActionBar.TabListener {

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

    //Global On click listener for all views
    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch(v.getId()) {
                case R.id.button_web:
                    //Inform the user the button1 has been clicked
                    Toast.makeText(getApplicationContext(), "create recipe button clicked",
                            Toast.LENGTH_SHORT).show();
                    /*final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    WebViewFragment fragment = new WebViewFragment();
                    ft.replace(R.id.webView2, fragment , "WebViewFragment");
                    ft.addToBackStack(null);
                    ft.commit();*/
                    break;

                //http://martin.cubeactive.com/android-onclicklitener-tutorial/

            }
        }
    };


}
