package rhyme.a.is.nine.foodmanager.gui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.Iterator;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.database.PriceDatabase;
import rhyme.a.is.nine.foodmanager.database.ProductDatabase;
import rhyme.a.is.nine.foodmanager.gui.adapter.DrawerAdapter;
import rhyme.a.is.nine.foodmanager.gui.fragment.AboutFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.FridgeFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.HelpFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.PricesFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.RecipeFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.SettingsFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.ShoppingListFragment;
import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.database.RecipeDatabase;
import rhyme.a.is.nine.foodmanager.gui.fragment.ShoppingListFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.RecipeFragment;

/**
 * Created by Fabio on 5/27/2015.
 */
public class MainActivity extends ActionBarActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] NavigationItems;

    public static ProductDatabase fridgeDatabase = null;
    public static ProductDatabase shoppingListDatabase = null;
    public static RecipeDatabase recipeDatabase = null;
    public static ProductDatabase historyDatabase = null;
    public static PriceDatabase priceDatabase = null;
    private ActionBar actionBar;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Integer[] NavigationItemsPictures = {
                R.drawable.ic_action_fridge,
                R.drawable.ic_action_list,
                R.drawable.ic_action_recipe,
                R.drawable.ic_action_graph,
                R.drawable.ic_action_settings,
                R.drawable.ic_action_help,
                R.drawable.ic_action_about
        };

        mTitle = mDrawerTitle = getTitle();
        NavigationItems = getResources().getStringArray(R.array.navigation_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        actionBar = getSupportActionBar();

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        mDrawerList.setAdapter(new DrawerAdapter(MainActivity.this, NavigationItems, NavigationItemsPictures));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        fridgeDatabase = new ProductDatabase("fridge.db");
        shoppingListDatabase = new ProductDatabase("shopping_list.db");
        historyDatabase = new ProductDatabase("history.db");
        priceDatabase = new PriceDatabase("prices.db");
        recipeDatabase = new RecipeDatabase("ichkoche.json");

        fridgeDatabase.readFromFile(getBaseContext());
        recipeDatabase.readFromFile(getBaseContext());
        shoppingListDatabase.readFromFile(getBaseContext());
        historyDatabase.readFromFile(getBaseContext());
        priceDatabase.readFromFile(getBaseContext());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
       }

    public void onMinusButtonFridgeClicked(View v) {
        String[] ids = ((String)v.getTag()).split("|");
        final int groupId = Integer.parseInt(ids[1]);
        final int childId = Integer.parseInt(ids[3]);
        final Product product = (Product) FridgeFragment.getAdapter().getChild(groupId, childId);
        shoppingListDatabase.addProduct(new Product(product.getName(), product.getCategory(), product.getCategory(), product.getSize(), 1));
        product.decreaseCount();
        FridgeFragment.getAdapter().notifyDataSetChanged();

        if(product.getCount() == 0) {
            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.move_out);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    FridgeFragment.setLastRemoved((Product) FridgeFragment.getAdapter().getChild(groupId, childId));
                    fridgeDatabase.removeProduct((Product) FridgeFragment.getAdapter().getChild(groupId, childId));
                    FridgeFragment.getAdapter().notifyDataSetChanged();
                    FridgeFragment.showUndoButton();
                }
            });
            ((RelativeLayout)v.getParent()).startAnimation(animation);
        }

        if (ShoppingListFragment.getAdapter() != null)
            ShoppingListFragment.getAdapter().notifyDataSetChanged();
    }

    public void onPlusButtonFridgeClicked(View v) {
        String[] ids = ((String)v.getTag()).split("|");
        final int groupId = Integer.parseInt(ids[1]);
        final int childId = Integer.parseInt(ids[3]);
        Product product = (Product) FridgeFragment.getAdapter().getChild(groupId, childId);
        product.increaseCount();

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




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                // Top Rated fragment activity
                fragment = new FridgeFragment();
                break;
            case 1:
                // Games fragment activity
                fragment = new ShoppingListFragment();
                break;
            case 2:
                // Movies fragment activity
                fragment = new RecipeFragment();
                break;
            case 3:
                // Movies fragment activity
                fragment = new PricesFragment();
                break;
            case 4:
                // Movies fragment activity
                fragment = new SettingsFragment();
                break;
            case 5:
                // Movies fragment activity
                fragment = new HelpFragment();
                break;
            case 6:
                // Movies fragment activity
                fragment = new AboutFragment();
                break;
            default:
                fragment = new FridgeFragment();
                break;
        }
        Bundle args = new Bundle();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(NavigationItems[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        actionBar.setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}


