package rhyme.a.is.nine.foodmanager.gui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.database.PriceDatabase;
import rhyme.a.is.nine.foodmanager.database.ProductDatabase;
import rhyme.a.is.nine.foodmanager.gui.fragment.FridgeFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.ShoppingListFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.RecipeFragment;
import rhyme.a.is.nine.foodmanager.product.PriceEntity;
import rhyme.a.is.nine.foodmanager.product.Product;


public class MainActivity extends ActionBarActivity implements
        ActionBar.TabListener {

    private List<String> recipe_search_entries_;
    private String url;
    public static ProductDatabase fridgeDatabase = null;
    public static ProductDatabase shoppingListDatabase = null;
    public static ProductDatabase historyDatabase = null;
    public static PriceDatabase priceDatabase = null;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Kühlschrank", "Einkaufsliste", "Rezepte", "Preise"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization
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
        priceDatabase = new PriceDatabase("prices.db");

        fridgeDatabase.readFromFile(getBaseContext());
        shoppingListDatabase.readFromFile(getBaseContext());
        historyDatabase.readFromFile(getBaseContext());
        priceDatabase.readFromFile(getBaseContext());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -8);
        priceDatabase.addPriceEntity(new PriceEntity("ad", 1.23f, new Date()));
        priceDatabase.addPriceEntity(new PriceEntity("asfd", 12.23f, cal.getTime()));

    }

    @Override
    public void onDestroy() {
        fridgeDatabase.writeToFile(getBaseContext());
        shoppingListDatabase.writeToFile(getBaseContext());
        historyDatabase.writeToFile(getBaseContext());
        priceDatabase.readFromFile(getBaseContext());

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
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

    public void onMinusButtonFridgeClicked(View v) {
        shoppingListDatabase.addProduct((Product) FridgeFragment.getAdapter().getItem((int) v.getTag()));
        FridgeFragment.getAdapter().decreaseProductCount((int) v.getTag());
        FridgeFragment.getAdapter().notifyDataSetChanged();
        ShoppingListFragment.getAdapter().notifyDataSetChanged();
    }

    public void onPlusButtonFridgeClicked(View v) {
        FridgeFragment.getAdapter().increaseProductCount((int) v.getTag());
        FridgeFragment.getAdapter().notifyDataSetChanged();
    }

    public void onMinusButtonShoppingListClicked(View v) {
        ShoppingListFragment.getAdapter().decreaseProductCount((int) v.getTag());
        ShoppingListFragment.getAdapter().notifyDataSetChanged();
    }

    public void onPlusButtonShoppingListClicked(View v) {
        ShoppingListFragment.getAdapter().increaseProductCount((int) v.getTag());
        ShoppingListFragment.getAdapter().notifyDataSetChanged();
    }

    public View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.button_web:

                    Intent myIntent = new Intent(MainActivity.this, Recipe.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                    break;
            }
        }
    };


    public String createURL() {
              /* creates something like http://www.chefkoch.de/ms/s0/karotte+kartoffel/Rezepte.html */

        if (!recipe_search_entries_.isEmpty()) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("http://mobile.chefkoch.de/ms/s0/");

            for (Iterator<String> entry = recipe_search_entries_.iterator(); entry.hasNext(); ) {
                stringBuilder.append(entry);
                if (entry.hasNext()) {
                    stringBuilder.append("+");
                }
            }

            stringBuilder.append("/Rezepte.html");

            url = stringBuilder.toString();

        } else {
            url = null;
        }

        return url;
    }
}
