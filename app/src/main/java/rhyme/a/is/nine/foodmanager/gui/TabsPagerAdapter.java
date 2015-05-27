package rhyme.a.is.nine.foodmanager.gui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rhyme.a.is.nine.foodmanager.gui.fragment.FridgeFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.PricesFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.RecipeFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.ShoppingListFragment;

/**
 * Created by Fabio on 5/6/2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new FridgeFragment();
            case 1:
                // Games fragment activity
                return new ShoppingListFragment();
            case 2:
                // Movies fragment activity
                return new RecipeFragment();
            case 3:
                // Movies fragment activity
                return new PricesFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }

}
