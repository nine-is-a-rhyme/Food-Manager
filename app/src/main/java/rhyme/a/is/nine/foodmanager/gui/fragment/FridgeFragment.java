package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.ProductActivity;
import rhyme.a.is.nine.foodmanager.gui.adapter.FridgeAdapter;
import rhyme.a.is.nine.foodmanager.product.Product;


/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment implements View.OnClickListener {

    private static FridgeAdapter fridgeAdapter;

    public static FridgeAdapter getAdapter() {
        return fridgeAdapter;
    }

    private static FloatingActionButton fabUndo;
    private static FloatingActionsMenu fabMenu;

    private static Object lastRemoved;

    ExpandableListView expandableListView;


    public FridgeFragment() {
        // Required empty public constructor
    }

    public static void showUndoButton() {
        fabUndo.setVisibility(View.VISIBLE);
    }

    public static void setLastRemoved(Object lastRemoved_) {
        lastRemoved = lastRemoved_;
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

        /*
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (expandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    long packedPos = ((ExpandableListView) adapterView).getExpandableListPosition(position);
                    int childPosition = ExpandableListView.getPackedPositionChild(packedPos);
                    Toast.makeText(getActivity().getBaseContext(), "clicked!!!" + childPosition, Toast.LENGTH_LONG)
                            .show();
                }
                return false;
            }
        });
        */
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
        fridgeAdapter.setActivity(getActivity());
        expandableListView.setAdapter(fridgeAdapter);
        expandableListView.bringToFront();

        fabMenu.bringToFront();
        fabUndo.bringToFront();

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int group) {
                for (int i = 0; i < fridgeAdapter.getGroupCount(); i++)
                    if (i != group)
                        expandableListView.collapseGroup(i);

                if (fabMenu.getVisibility() == View.INVISIBLE && !canScroll()) {
                    final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_move_in);
                    fabMenu.startAnimation(animation);

                    fabMenu.setVisibility(View.VISIBLE);
                }
            }
        });

        expandableListView.setLongClickable(true);

        expandableListView.setOnTouchListener(new View.OnTouchListener() {
            private float initialY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        final float y = event.getY();
                        final float yDiff = y - initialY;
                        if (yDiff > 0.0 && canScroll() && fabMenu.getVisibility() == View.INVISIBLE) {

                            final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_move_in);
                            fabMenu.startAnimation(animation);

                            fabMenu.setVisibility(View.VISIBLE);
                            break;
                        } else if (yDiff < 0.0 && canScroll() && fabMenu.getVisibility() == View.VISIBLE) {

                            final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_move_out);
                            fabMenu.startAnimation(animation);

                            fabMenu.setVisibility(View.INVISIBLE);
                            break;
                        }
                        break;
                }
                return false;
            }
        });
    }

    private boolean canScroll() {
        int pos = expandableListView.getLastVisiblePosition();
        if (expandableListView.getChildAt(pos) == null)
            return false;

        return expandableListView.getChildAt(pos).getBottom() > expandableListView.getHeight();

    }

    @Override
    public void onClick(View view) {
        fabMenu.collapse();
        switch ((String) view.getTag()) {
            case "ADD":
            case "ADDMANUAL":
                ProductActivity.editProduct = null;
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                getActivity().startActivityForResult(intent, 0);
                break;
            case "UNDO":
                ((Product) lastRemoved).increaseCount(); // because we deleted one to much
                MainActivity.fridgeDatabase.addProduct((Product) lastRemoved);

                for (int i = 0; i < ShoppingListFragment.getAdapter().getCount(); i++) {
                    if (((Product) ShoppingListFragment.getAdapter().getItem(i)).getName().equals(((Product) lastRemoved).getName())) {
                        ShoppingListFragment.getAdapter().removeItem(i, false);
                        ShoppingListFragment.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
                fabUndo.setVisibility(View.INVISIBLE);
                fridgeAdapter.notifyDataSetChanged();
                break;
            case "ADDSCAN":
                ProductActivity.editProduct = null;
                Intent scanIntent = new Intent(getActivity(), ProductActivity.class);
                scanIntent.putExtra("SCAN", true);
                getActivity().startActivityForResult(scanIntent, 0);
                break;
            default:
                break;
        }
    }
}
