package rhyme.a.is.nine.foodmanager.gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.database.DatabaseAccess;
import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter;
import rhyme.a.is.nine.foodmanager.product.Product;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Context context = getApplicationContext();
        CharSequence text;
        int duration = Toast.LENGTH_SHORT;

        int id = item.getItemId();


        switch (id) {
            case R.id.action_add:
                new IntentIntegrator(this).initiateScan();
                return true;
            case R.id.action_edit:
                text = "Edit clicked!";
                Toast.makeText(context, text, duration).show();
                return true;
            case R.id.action_delete:
                text = "Delete clicked!";
                Toast.makeText(context, text, duration).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(result != null)
        {
            if(result.getContents() == null)
                return;

            Product product = BarcodeToProductConverter.getProductForBarcode(result.getContents());
            if(product == null)
                return;

            DatabaseAccess.addProduct(product);

            DatabaseAccess.addProduct(BarcodeToProductConverter.getProductForBarcode(result.getContents()));
            Toast.makeText(getApplicationContext(), "Product found", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "no product found", Toast.LENGTH_LONG).show();
        }
    }
}
