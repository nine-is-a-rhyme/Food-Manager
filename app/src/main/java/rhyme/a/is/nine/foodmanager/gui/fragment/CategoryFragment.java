package rhyme.a.is.nine.foodmanager.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.CategoryActivity;
import rhyme.a.is.nine.foodmanager.gui.adapter.CategoryAdapter;
import rhyme.a.is.nine.foodmanager.util.SwipeDismissListViewTouchListener;

public class CategoryFragment extends ListFragment implements View.OnClickListener {

    private static CategoryAdapter categoryListAdapter = new CategoryAdapter();

    private AddFloatingActionButton fabAdd;

    public static CategoryAdapter getAdapter() {
        return categoryListAdapter;
    }

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Setting the array adapter to the listview
        categoryListAdapter.setContext(getActivity().getBaseContext());
        categoryListAdapter.setListView(getListView());
        setListAdapter(categoryListAdapter);

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
                                    categoryListAdapter.removeItem(position);
                                }
                                categoryListAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                //intent.putExtra("startedBy","List");
                getActivity().startActivityForResult(intent, 0);
                break;
        }
    }
}
