package rhyme.a.is.nine.foodmanager.database;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public abstract class ProductDatabase implements Serializable {

    protected static List<Product> products = new ArrayList<Product>();

    public static List<Product> getAllProducts() {
        return products;
    }

    public static Product getProductByPosition(int position)
    {
        if (products.isEmpty())
            return null;

        return products.get(position);
    }


    public static Product getProductByName(String name)
    {
        if (products.isEmpty())
            return null;

        for(Product i : products)
        {
            if (i.getName().equals(name))
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
            if (i.getName().equals(product.getName())) {
                i.increaseCount();
                isNew = false;
            }
        }
        if(isNew)
            products.add(product);
    }

    public static void removeProductByPosition(int position, boolean removeCompletely)
    {
        if (products.isEmpty())
            return;

        if(removeCompletely)
            products.remove(position);
        else {
            products.get(position).decreaseCount();

            if(products.get(position).getCount() <= 0)
                products.remove(position);
        }
    }

    protected static void writeFile(String fileName, Context context, List<Product> products) {
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

    protected static Object readFile(String fileName, Context context) {
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
