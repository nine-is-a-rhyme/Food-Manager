package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.ProductActivity;
import rhyme.a.is.nine.foodmanager.gui.adapter.ShoppingListAdapter;
import rhyme.a.is.nine.foodmanager.util.SwipeDismissListViewTouchListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends ListFragment implements View.OnClickListener {

    private static ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter();

    private AddFloatingActionButton fabAdd;

    public static ShoppingListAdapter getAdapter() {
        return shoppingListAdapter;
    }

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);

        //return inflater.inflate(R.layout.fragment_shopping_list, container, false);

        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        fabAdd = (AddFloatingActionButton) view.findViewById(R.id.floating_action_button_add);
        fabAdd.setOnClickListener(this);
        fabAdd.setTag("ADD");
        fabAdd.setStrokeVisible(true);

        return view;
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
        shoppingListAdapter.setContext(getActivity().getBaseContext());
        shoppingListAdapter.setListView(getListView());
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
                                    shoppingListAdapter.removeItem(position, true);
                                }
                                shoppingListAdapter.notifyDataSetChanged();
                            }
                        }
                );
        swipeDismissListViewTouchListener.setEnabled(true);
        getListView().setOnTouchListener(swipeDismissListViewTouchListener);
    }

    @Override
    public void onClick(View view) {
        switch ((String) view.getTag()) {
            case "ADD":
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("startedBy","List");
                getActivity().startActivityForResult(intent, 0);
                break;
        }
    }
}
