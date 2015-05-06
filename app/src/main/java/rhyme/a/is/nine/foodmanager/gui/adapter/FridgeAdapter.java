package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.text.SimpleDateFormat;
import java.util.List;


import rhyme.a.is.nine.foodmanager.gui.MainActivity;
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
        List<Product> products = MainActivity.fridgeDatabase.getAllProducts();
        if(products != null)
            return products.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.fridgeDatabase.getProductByPosition(position);
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

        Product product = MainActivity.fridgeDatabase.getProductByPosition(position);

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setText(product.getName());
        text2.setText("Menge: " + product.getCount() + " | Kategorie: " + product.getCategory() + " | Haltbar bis: " + (product.getBestBeforeDate() == null ? "-": new SimpleDateFormat("dd.MM.yyyy").format(product.getBestBeforeDate()).toString()));

        if(product.getBestBeforeDate() == null)
            twoLineListItem.setBackgroundColor(Color.LTGRAY);
        else if(product.getBestBeforeDate().getTime() - System.currentTimeMillis() < 0)
            twoLineListItem.setBackgroundColor(Color.RED);
        else if(product.getBestBeforeDate().getTime() - System.currentTimeMillis() < 1000/* milliseconds */ * 60/* seconds */ * 60/* minutes */ * 24/* hours */ * 2/* days */)
            twoLineListItem.setBackgroundColor(Color.YELLOW);
        else
            twoLineListItem.setBackgroundColor(Color.GREEN);

        return twoLineListItem;
    }

    public void removeItem(int position) {
        Product product = MainActivity.fridgeDatabase.getProductByPosition(position);
        MainActivity.shoppingListDatabase.addProduct(new Product(product.getName(), product.getCategory(), product.getBarcode(), product.getSize(), 1));
        MainActivity.fridgeDatabase.removeProductByPosition(position, false);
    }
}