package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;



import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class FridgeAdapter extends BaseExpandableListAdapter {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return MainActivity.fridgeDatabase.getAllCategories().size();
    }

    @Override
    public int getChildrenCount(int group) {
        return MainActivity.fridgeDatabase.getProductsForCategory(group).size();
    }

    @Override
    public Object getGroup(int group) {
        return MainActivity.fridgeDatabase.getAllCategories().get(group);
    }

    @Override
    public Object getChild(int group, int child) {
        return MainActivity.fridgeDatabase.getProductsForCategory(group).get(child);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i2) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupId, boolean isLastGroup, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fridge_group_element, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.fridge_group_element_title);
        TextView count = (TextView) convertView.findViewById(R.id.fridge_group_element_count);

        Pair<String, Integer> group = MainActivity.fridgeDatabase.getCategory(groupId);
        if(group != null) {
            title.setText(group.first);
            count.setText(group.second + " Produkte");
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupId, int childId, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fridge_element, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.fridge_product_name);
        TextView count = (TextView) convertView.findViewById(R.id.fridge_product_count);
        TextView bestBeforeDate = (TextView) convertView.findViewById(R.id.fridge_product_bestbefore);

        Product product = MainActivity.fridgeDatabase.getProductsForCategory(groupId).get(childId);
        title.setText(product.getName());
        count.setText("Anzahl: " + product.getCount());
        bestBeforeDate.setText(product.getBestBeforeDate().toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}