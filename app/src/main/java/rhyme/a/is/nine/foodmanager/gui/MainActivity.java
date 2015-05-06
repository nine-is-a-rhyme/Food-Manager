package rhyme.a.is.nine.foodmanager.gui;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.database.ProductDatabase;
import rhyme.a.is.nine.foodmanager.gui.fragment.ShoppingListFragment;

public class MainActivity extends ActionBarActivity implements
        ActionBar.TabListener {

    public static ProductDatabase fridgeDatabase = null;
    public static ProductDatabase shoppingListDatabase = null;
    public static ProductDatabase historyDatabase = null;

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

        fridgeDatabase = new ProductDatabase("fridge.db");
        shoppingListDatabase = new ProductDatabase("shopping_list.db");
        historyDatabase = new ProductDatabase("history.db");

        fridgeDatabase.readFromFile(getBaseContext());
        shoppingListDatabase.readFromFile(getBaseContext());
        historyDatabase.readFromFile(getBaseContext());
    }

    @Override
    public void onDestroy() {
        fridgeDatabase.writeToFile(getBaseContext());
        shoppingListDatabase.writeToFile(getBaseContext());
        historyDatabase.writeToFile(getBaseContext());
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

    public void onMinusButtonClicked(View v) {
        ShoppingListFragment.getAdapter().decreaseProductCount((int) v.getTag());
        ShoppingListFragment.getAdapter().notifyDataSetChanged();
    }

    public void onPlusButtonClicked(View v) {
        ShoppingListFragment.getAdapter().increaseProductCount((int)v.getTag());
        ShoppingListFragment.getAdapter().notifyDataSetChanged();
    }
}
