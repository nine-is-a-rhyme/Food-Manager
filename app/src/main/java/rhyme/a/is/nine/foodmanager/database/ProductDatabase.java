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
public class ProductDatabase implements Serializable {

    private List<Product> products;

    private String fileName;


    public ProductDatabase(String fileName) {
        this.fileName = fileName;
        this.products = new ArrayList<Product>();
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductByPosition(int position)
    {
        if (products.isEmpty())
            return null;

        return products.get(position);
    }


    public Product getProductByName(String name)
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

    public Product getProductByBarcode(String barcode)
    {
        if (products.isEmpty())
            return null;

        for(Product i : products)
        {
            if (i.getBarcode().equals(barcode))
                return i;
        }
        return null;
    }

    public void addProduct(Product product)
    {
        boolean isNew = true;

        // check if exists
        for(Product i : products)
        {
            if (i.equals(product)) {
                i.increaseCount();
                isNew = false;
            }
        }
        if(isNew) {
            if(product.getBestBeforeDate() != null) {
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getBestBeforeDate() != null && product.getBestBeforeDate().before(products.get(i).getBestBeforeDate())) {
                        products.add(i, product);
                        return;
                    }
                }
            }
            products.add(product);
        }
    }

    public void removeProductByPosition(int position, boolean removeCompletely)
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

    public void writeToFile(Context context) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(this.fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(this.products);
            outputStream.close();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(Context context) {
        Object object = null;
        FileInputStream outputStream;
        try {
            outputStream = context.openFileInput(this.fileName);
            ObjectInputStream ois = new ObjectInputStream(outputStream);
            object = ois.readObject();
            outputStream.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(object != null)
            this.products = (List<Product>) object;
    }
}
