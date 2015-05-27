package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;


import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
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

        Product product = MainActivity.fridgeDatabase.getProductByPosition(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fridge_element, null);
        TextView productName = (TextView) rowView.findViewById(R.id.fridge_product_name);
        TextView productBestBefore = (TextView) rowView.findViewById(R.id.fridge_product_bestbefore);
        TextView productCount = (TextView) rowView.findViewById(R.id.fridge_product_count);
        Button minusButton = (Button) rowView.findViewById(R.id.fridge_minus_button);
        Button plusButton = (Button) rowView.findViewById(R.id.fridge_plus_button);
        minusButton.setTag(position);
        plusButton.setTag(position);

        if(product.getCount() <= 1)
            minusButton.setEnabled(false);

        productName.setText(product.getName());
        productBestBefore.setText("Haltbar bis: " + (product.getBestBeforeDate() == null ? "-": new SimpleDateFormat("dd.MM.yyyy").format(product.getBestBeforeDate())));
        productCount.setText("Anzahl: " + product.getCount());

        if(product.getBestBeforeDate() == null)
            rowView.setBackgroundColor(Color.LTGRAY);
        else if(product.getBestBeforeDate().getTime() - System.currentTimeMillis() < -1000/* milliseconds */ * 60/* seconds */ * 60/* minutes */ * 24/* hours */ * 1/* days */)
            rowView.setBackgroundColor(Color.parseColor("#F7977A"));
        else if(product.getBestBeforeDate().getTime() - System.currentTimeMillis() < 1000/* milliseconds */ * 60/* seconds */ * 60/* minutes */ * 24/* hours */ * 2/* days */)
            rowView.setBackgroundColor(Color.parseColor("#FFF79A"));
        else
            rowView.setBackgroundColor(Color.parseColor("#82CA9D"));

        return rowView;
    }

    public void removeItem(int position, boolean completely) {
        Product product = MainActivity.fridgeDatabase.getProductByPosition(position);
        MainActivity.shoppingListDatabase.addProduct(new Product(product.getName(), product.getCategory(), product.getBarcode(), product.getSize(), 1));
        MainActivity.fridgeDatabase.removeProductByPosition(position, completely);
    }

    public void deleteAll()
    {
        MainActivity.fridgeDatabase.deleteAll();
    }

    public void decreaseProductCount(int position) {
        MainActivity.fridgeDatabase.removeProductByPosition(position, false);
    }

    public void increaseProductCount(int position) {
        MainActivity.fridgeDatabase.getProductByPosition(position).increaseCount();
    }
}