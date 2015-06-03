package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.ProductActivity;
import rhyme.a.is.nine.foodmanager.gui.adapter.FridgeAdapter;
import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.util.SwipeDismissListViewTouchListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment implements View.OnClickListener {

    private static FridgeAdapter fridgeAdapter;

    public static FridgeAdapter getAdapter() {
        return fridgeAdapter;
    }

    private FloatingActionButton fabUndo;
    private FloatingActionsMenu fabMenu;

    private Object lastRemoved;

    ExpandableListView expandableListView;


    public FridgeFragment() {
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

        View view = inflater.inflate(R.layout.fragment_fridge, container, false);
        fabMenu = (FloatingActionsMenu) view.findViewById(R.id.floating_action_button_menu);

        FloatingActionButton fabManual = (FloatingActionButton) view.findViewById(R.id.floating_action_button_add_manual);
        fabManual.setOnClickListener(this);
        fabManual.setTag("ADDMANUAL");
        fabManual.setStrokeVisible(true);
        fabManual.setIcon(R.drawable.ic_action_manual);

        FloatingActionButton fabScan = (FloatingActionButton) view.findViewById(R.id.floating_action_button_add_scan);
        fabScan.setOnClickListener(this);
        fabScan.setTag("ADDSCAN");
        fabScan.setStrokeVisible(true);
        fabScan.setIcon(R.drawable.ic_action_barcode);

        fabUndo = (FloatingActionButton) view.findViewById(R.id.floating_action_button_undo);
        fabUndo.setIcon(R.drawable.ic_action_revert);
        fabUndo.setOnClickListener(this);
        fabUndo.setTag("UNDO");
        fabUndo.setStrokeVisible(true);
        fabUndo.setColorNormal(Color.parseColor("#993300"));
        fabUndo.setColorPressed(Color.parseColor("#EE3300"));


        expandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list);

        return view;
    }

    @Override
    public void onResume() {
        fabUndo.setVisibility(View.INVISIBLE);
        fridgeAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Setting the array adapter to the listview
        fridgeAdapter = new FridgeAdapter();
        fridgeAdapter.setContext(getActivity().getBaseContext());
        expandableListView.setAdapter(fridgeAdapter);
        expandableListView.bringToFront();

        fabMenu.bringToFront();
        fabUndo.bringToFront();
        // enable swipe to delete
        final SwipeDismissListViewTouchListener swipeDismissListViewTouchListener =
                new SwipeDismissListViewTouchListener(
                        expandableListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {

                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    //lastRemoved = fridgeAdapter.getItem(position);
                                    //fridgeAdapter.removeItem(position, true);
                                    fabUndo.setVisibility(View.VISIBLE);
                                }
                                fridgeAdapter.notifyDataSetChanged();
                                if(ShoppingListFragment.getAdapter() != null)
                                    ShoppingListFragment.getAdapter().notifyDataSetChanged();
                            }
                        }
                );
        swipeDismissListViewTouchListener.setEnabled(true);

        expandableListView.setOnTouchListener(new View.OnTouchListener() {
            private float initialY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeDismissListViewTouchListener.onTouch(v, event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        final float y = event.getY();
                        final float yDiff = y - initialY;
                        if (yDiff > 0.0) {
                            fabMenu.setVisibility(View.VISIBLE);
                            break;
                        } else if (yDiff < 0.0) {
                            fabMenu.setVisibility(View.INVISIBLE);
                            break;
                        }
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        fabMenu.collapse();
        switch ((String) view.getTag()) {
            case "ADD":
            case "ADDMANUAL":
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                getActivity().startActivityForResult(intent, 0);
                break;
            case "UNDO":
                MainActivity.fridgeDatabase.addProduct((Product) lastRemoved);

                for(int i = 0; i < ShoppingListFragment.getAdapter().getCount(); i++) {
                    if (((Product)ShoppingListFragment.getAdapter().getItem(i)).getName().equals(((Product)lastRemoved).getName())) {
                        ShoppingListFragment.getAdapter().removeItem(i, false);
                        ShoppingListFragment.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
                fabUndo.setVisibility(View.INVISIBLE);
                fridgeAdapter.notifyDataSetChanged();
                break;
            case "ADDSCAN":
                Intent scanIntent = new Intent(getActivity(), ProductActivity.class);
                scanIntent.putExtra("SCAN", true);
                getActivity().startActivityForResult(scanIntent, 0);
                break;
            default:
                final String selected = (String) ((ExpandableListAdapter)expandableListView.getAdapter()).getChild(
                        Integer.parseInt(((String) view.getTag()).split("|")[0]),
                        Integer.parseInt(((String) view.getTag()).split("|")[1]));
                Toast.makeText(getActivity().getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();
                break;
        }
    }
}
