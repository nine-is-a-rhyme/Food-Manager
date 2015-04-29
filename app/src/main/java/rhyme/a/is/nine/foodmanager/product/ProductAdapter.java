package rhyme.a.is.nine.foodmanager.product;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.database.DatabaseAccess;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ProductPlace productPlace;

    public ProductAdapter(Context context, ProductPlace productPlace) {
        this.context = context;
        this.productPlace = productPlace;
    }

    @Override
    public int getCount() {
        List<Product> products = DatabaseAccess.getProducts(productPlace);
        if(products != null)
            return products.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return DatabaseAccess.getProducts(productPlace).get(position);
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

        Product product = DatabaseAccess.getProducts(productPlace).get(position);

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
        DatabaseAccess.removeProductByPosition(position);
    }
}