package rhyme.a.is.nine.foodmanager.gui.adapter;

/**
 * Created by Fabio on 5/27/2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import rhyme.a.is.nine.foodmanager.R;

public class DrawerAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] items;
    private final Integer[] imageId;

    public DrawerAdapter(Activity context,
                         String[] items, Integer[] imageId) {
        super(context, R.layout.activity_navigation_list_item, items);
        this.context = context;
        this.items = items;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_navigation_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.drawer_item_text);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.drawer_item_icon);
        txtTitle.setText(items[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}