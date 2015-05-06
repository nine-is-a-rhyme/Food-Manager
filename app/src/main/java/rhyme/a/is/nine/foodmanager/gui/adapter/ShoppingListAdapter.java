package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.database.ShoppingListDatabase;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class ShoppingListAdapter extends BaseAdapter implements View.OnClickListener{

    private Context context;
    private ListView listView;

    public ShoppingListAdapter(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        List<Product> products = ShoppingListDatabase.getAllProducts();
        if(products != null)
            return products.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return ShoppingListDatabase.getProductByPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = ShoppingListDatabase.getProductByPosition(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.shopping_list_element, null);
        TextView productName = (TextView) rowView.findViewById(R.id.shopping_list_product_name);
        TextView productCount = (TextView) rowView.findViewById(R.id.shopping_list_product_count);
        Button minusButton = (Button) rowView.findViewById(R.id.shopping_list_minus_button);
        Button plusButton = (Button) rowView.findViewById(R.id.shopping_list_plus_button);

        minusButton.setOnClickListener(this);
        minusButton.setTag("MINUS_BUTTON");
        plusButton.setOnClickListener(this);
        plusButton.setTag("PLUS_BUTTON");

        productName.setText(product.getName());
        productCount.setText("Anzahl: " + product.getCount());

        return rowView;
    }

    public void removeItem(int position) {
        ShoppingListDatabase.removeProductByPosition(position, false);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, "You pushed a button!", Toast.LENGTH_SHORT).show();

        switch((String)view.getTag()) {
            case "MINUS_BUTTON":
                ShoppingListDatabase.getProductByPosition(listView.getPositionForView((View) view.getParent()));
        }
    }
}