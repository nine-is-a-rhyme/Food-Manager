package rhyme.a.is.nine.foodmanager.database;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.product.ProductPlace;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class DatabaseAccess implements Serializable {

    private static List<Product> products = new ArrayList<Product>();
    private static List<Product> products_db = new ArrayList<Product>();

    public static List<Product> getAllProducts() {
        return products;
    }

    public static List<Product> getProducts(ProductPlace productPlace) {
        List<Product> temp = new ArrayList<Product>();

        if (products.isEmpty())
            return null;

        for(Product i : products)
        {
            if (i.getProductPlace() == productPlace)
                temp.add(i);
        }
        return temp;
    }

    public static Product getProductByName(String name, ProductPlace productPlace)
    {
        if (products.isEmpty())
            return null;

        for(Product i : products)
        {
            if (i.getName().equals(name) && i.getProductPlace() == productPlace)
                return i;
        }
        return null;
    }

    public static void addProduct(Product product)
    {
        boolean isNew = true;

        // check if exists
        for(Product i : products)
        {
            if (i.getBarcode().equals(product.getBarcode())) {
                i.increaseCount();
                isNew = false;
            }
        }
        if(isNew)
            products.add(product);

        // construct db
        for(Product i : products_db)
        {
            if (i.getBarcode().equals(product.getBarcode()))
                return;
        }
        products_db.add(product);
    }

    public static void removeProductByPosition(int position)
    {
        if (products.isEmpty())
            return;

        products.remove(position);
    }

    public static Product getDatabaseProductByBarcode(String barcode)
    {
        if (products_db.isEmpty())
            return null;

        for(Product i : products_db)
        {
            if (i.getBarcode().equals(barcode))
                return i;
        }
        return null;
    }

    public static void writeToFile(Context context) {
        writeFile("products.dbase", context, products);
        writeFile("products_db.dbase", context, products_db);
    }

    private static void writeFile(String fileName, Context context, List<Product> products) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(products);
            outputStream.close();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile(Context context) {
        Object products_object = readFile("products.dbase", context);
        Object products_db_object = readFile("products_db.dbase", context);

        if(products_object != null)
            products = (List<Product>) products_object;

        if(products_db_object != null)
            products_db = (List<Product>) products_db_object;
    }

    private static Object readFile(String fileName, Context context) {
        Object object = null;
        FileInputStream outputStream;
        try {
            outputStream = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(outputStream);
            object = ois.readObject();
            outputStream.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }
}
