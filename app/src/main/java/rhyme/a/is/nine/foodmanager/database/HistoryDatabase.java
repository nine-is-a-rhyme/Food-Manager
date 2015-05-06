package rhyme.a.is.nine.foodmanager.database;

import android.content.Context;

import java.util.List;

import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 06/05/15.
 */
public class HistoryDatabase extends ProductDatabase {

    public static Product getProductByBarcode(String barcode) {
        if (products.isEmpty())
            return null;

        for(Product i : products)
        {
            if (i.getBarcode().equals(barcode))
                return i;
        }
        return null;
    }

    public static void writeToFile(Context context) {
        writeFile("history.dbase", context, products);
    }

    public static void readFromFile(Context context) {
        Object products_object = readFile("history.dbase", context);

        if(products_object != null)
            products = (List<Product>) products_object;
    }
}
