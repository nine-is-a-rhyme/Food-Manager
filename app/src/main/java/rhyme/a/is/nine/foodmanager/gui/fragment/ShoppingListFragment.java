package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.AddItemDialog;
import rhyme.a.is.nine.foodmanager.gui.adapter.ShoppingListAdapter;
import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.util.SwipeDismissListViewTouchListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends ListFragment {

    private FragmentActivity myContext;

    private ShoppingListAdapter shoppingListAdapter;


    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    @Override
    public void onResume() {
        shoppingListAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Setting the array adapter to the listview
        shoppingListAdapter = new ShoppingListAdapter(getActivity().getBaseContext(), getListView());
        setListAdapter(shoppingListAdapter);

        // enable swipe to delete
        SwipeDismissListViewTouchListener swipeDismissListViewTouchListener =
                new SwipeDismissListViewTouchListener(
                        getListView(),
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {

                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    shoppingListAdapter.removeItem(position);
                                }
                                shoppingListAdapter.notifyDataSetChanged();
                            }
                        }
                );
        swipeDismissListViewTouchListener.setEnabled(true);
        getListView().setOnTouchListener(swipeDismissListViewTouchListener);
        getListView().setOnScrollListener(swipeDismissListViewTouchListener.makeScrollListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        CharSequence text;

        int id = item.getItemId();


        switch (id) {
            case R.id.action_add:
                DialogFragment scannerDialog = new AddItemDialog();
                scannerDialog.show(myContext.getFragmentManager(), "test");
                return true;
            case R.id.action_edit:
                text = "Edit clicked!";
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                text = "Delete clicked!";
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onMinusButtonClicked(View v) {
        shoppingListAdapter.decreaseProductCount((int)v.getTag());
        shoppingListAdapter.notifyDataSetChanged();
    }

    public void onPlusButtonClicked(View v) {
        shoppingListAdapter.increaseProductCount((int)v.getTag());
        shoppingListAdapter.notifyDataSetChanged();
    }

}
