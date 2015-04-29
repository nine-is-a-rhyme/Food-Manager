package rhyme.a.is.nine.foodmanager;

import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainTabActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab = actionBar.newTab()
                .setTag(1)
                .setText("Kühlschrank")
                .setTabListener(new TabListener<FridgeFragment>(
                        this, "fridge", FridgeFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setTag(2)
                .setText("Einkaufsliste")
                .setTabListener(new TabListener<ShoppingListFragment>(
                        this, "shoppinglist", ShoppingListFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setTag(3)
                .setText("Rezepte")
                .setTabListener(new TabListener<RecipeFragment>(
                        this, "recipe", RecipeFragment.class));
        actionBar.addTab(tab);

    }

    @Override
    public Resources getResources() {
        return new ResourceFix(super.getResources());
    }

    private class ResourceFix extends Resources {
        private int targetId = 0;

        ResourceFix(Resources resources) {
            super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
            targetId = Resources.getSystem().getIdentifier("split_action_bar_is_narrow", "bool", "android");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean getBoolean(int id) throws Resources.NotFoundException {
            return targetId == id || super.getBoolean(id);
        }
    }


    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        /** Constructor used each time a new tab is created.
         * @param activity  The host Activity, used to instantiate the fragment
         * @param tag  The identifier tag for the fragment
         * @param clz  The fragment's Class, used to instantiate the fragment
         */
        public TabListener(Activity activity, String tag, Class<T> clz) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
        }

    /* The following are each of the ActionBar.TabListener callbacks */

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                // Detach the fragment, because another one is being attached
                ft.detach(mFragment);
            }
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            // Check if the fragment is already initialized
            if (mFragment == null) {
                // If not, instantiate and add it to the activity
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                // If it exists, simply attach it in order to show it
                ft.attach(mFragment);
            }

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        }
    }
}
