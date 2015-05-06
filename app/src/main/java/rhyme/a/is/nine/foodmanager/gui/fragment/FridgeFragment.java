package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
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
import rhyme.a.is.nine.foodmanager.gui.ProductActivity;
import rhyme.a.is.nine.foodmanager.gui.adapter.FridgeAdapter;
import rhyme.a.is.nine.foodmanager.util.SwipeDismissListViewTouchListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends ListFragment {

    private FragmentActivity myContext;

    private FridgeAdapter productAdapter;


    public FridgeFragment() {
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

        // Setting the array adapter to the listview
        productAdapter = new FridgeAdapter(getActivity().getBaseContext());
        setListAdapter(productAdapter);

        return inflater.inflate(R.layout.fragment_fridge, container, false);
    }

    @Override
    public void onResume() {
        productAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fridge_tab, menu);
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        /** Setting the multiselect choice mode for the listview */
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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
                                    productAdapter.removeItem(position);
                                }
                                productAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                getActivity().startActivityForResult(intent, 0);
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

}
