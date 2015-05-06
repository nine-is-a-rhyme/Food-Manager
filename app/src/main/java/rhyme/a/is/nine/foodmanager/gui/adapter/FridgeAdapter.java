package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.List;

import rhyme.a.is.nine.foodmanager.database.FridgeDatabase;
import rhyme.a.is.nine.foodmanager.database.ProductDatabase;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class FridgeAdapter extends BaseAdapter {

    private Context context;

    public FridgeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        List<Product> products = FridgeDatabase.getAllProducts();
        if(products != null)
            return products.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return FridgeDatabase.getProductByPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        Product product = FridgeDatabase.getProductByPosition(position);

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setText(product.getName());
        text2.setText("Menge: " + product.getCount() + " | Kategorie: " + product.getCategory() + " | Haltbar bis: " + (product.getBestBeforeDate() == null ? "-": product.getBestBeforeDate().toGMTString()));

        if(product.getBestBeforeDate() == null)
            twoLineListItem.setBackgroundColor(Color.LTGRAY);
        else if(product.getBestBeforeDate().getTime() - System.currentTimeMillis() < 0)
            twoLineListItem.setBackgroundColor(Color.RED);
        else if(product.getBestBeforeDate().getTime() - System.currentTimeMillis() < 3600*24)
            twoLineListItem.setBackgroundColor(Color.YELLOW);
        else
            twoLineListItem.setBackgroundColor(Color.GREEN);

        return twoLineListItem;
    }

    public void removeItem(int position) {
        FridgeDatabase.removeProductByPosition(position, false);
    }
}