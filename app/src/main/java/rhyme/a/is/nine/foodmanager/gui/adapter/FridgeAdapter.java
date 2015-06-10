package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.ProductActivity;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class FridgeAdapter extends BaseExpandableListAdapter {

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        try {
            return MainActivity.fridgeDatabase.getAllCategories().size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int group) {
        try {
            return MainActivity.fridgeDatabase.getProductsForCategory(group).size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public Object getGroup(int group) {
        Set<String> keys = MainActivity.fridgeDatabase.getAllCategories().keySet();
        return keys.toArray(new String[keys.size()])[group];
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
            LayoutInflater layoutInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fridge_group_element, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.fridge_group_element_title);
        Pair<String, Integer> group = MainActivity.fridgeDatabase.getCategory(groupId);
        if (group != null) {
            title.setText(group.first + " (" + group.second + " Produkt" + (group.second > 1 ? "e" : "") + ")");
        }

        ImageView light = (ImageView) convertView.findViewById(R.id.fridge_group_element_light);

        Product product = MainActivity.fridgeDatabase.getProductsForCategory(groupId).get(0);
        if (product.getBestBeforeDate() == null)
            light.setColorFilter(Color.LTGRAY);
        else if (product.getBestBeforeDate().getTime() - System.currentTimeMillis() < -1000/* milliseconds */ * 60/* seconds */ * 60/* minutes */ * 24/* hours */ * 1/* days */)
            light.setColorFilter(Color.parseColor("#CC0000"));
        else if (product.getBestBeforeDate().getTime() - System.currentTimeMillis() < 1000/* milliseconds */ * 60/* seconds */ * 60/* minutes */ * 24/* hours */ * 2/* days */)
            light.setColorFilter(Color.parseColor("#FF8800"));
        else
            light.setColorFilter(Color.parseColor("#99CC00"));

        return convertView;
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return super.getCombinedChildId(groupId, childId);
    }

    @Override
    public View getChildView(int groupId, int childId, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fridge_element, null);
        }

        TextView productName = (TextView) convertView.findViewById(R.id.fridge_product_name);
        TextView productBestBefore = (TextView) convertView.findViewById(R.id.fridge_product_bestbefore);
        TextView productCount = (TextView) convertView.findViewById(R.id.fridge_product_count);
        ImageButton minusButton = (ImageButton) convertView.findViewById(R.id.fridge_minus_button);
        ImageButton plusButton = (ImageButton) convertView.findViewById(R.id.fridge_plus_button);
        minusButton.setTag(groupId + "|" + childId);
        plusButton.setTag(groupId + "|" + childId);

        Product product = MainActivity.fridgeDatabase.getProductsForCategory(groupId).get(childId);
        if (product.getCount() <= 1)
            minusButton.setImageResource(R.drawable.ic_action_discard);
        else
            minusButton.setImageResource(R.drawable.ic_action_minus);

        productName.setText(product.getName());
        productBestBefore.setText("Haltbar bis: " + (product.getBestBeforeDate() == null ? "-" : new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(product.getBestBeforeDate())));
        productCount.setText("Anzahl: " + product.getCount());

        ImageView light = (ImageView) convertView.findViewById(R.id.fridge_light);

        if (product.getBestBeforeDate() == null)
            light.setColorFilter(Color.LTGRAY);
        else if (product.getBestBeforeDate().getTime() - System.currentTimeMillis() < -1000/* milliseconds */ * 60/* seconds */ * 60/* minutes */ * 24/* hours */ * 1/* days */)
            light.setColorFilter(Color.parseColor("#CC0000"));
        else if (product.getBestBeforeDate().getTime() - System.currentTimeMillis() < 1000/* milliseconds */ * 60/* seconds */ * 60/* minutes */ * 24/* hours */ * 2/* days */)
            light.setColorFilter(Color.parseColor("#FF8800"));
        else
            light.setColorFilter(Color.parseColor("#99CC00"));

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(activity, ((String) view.getTag()), Toast.LENGTH_SHORT).show();
                String[] token = ((String) view.getTag()).split("|");
                Long groupId = Long.parseLong(token[1]);
                Long childId = Long.parseLong(token[3]);
                long i = getCombinedChildId(groupId, childId);

                ProductActivity.editProduct = MainActivity.fridgeDatabase.getProductByPosition((int) i);
                Intent intent = new Intent(activity, ProductActivity.class);
                ((Activity) activity).startActivityForResult(intent, 0);
                return false;
            }
        });

        convertView.setTag(groupId + "|" + childId);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}