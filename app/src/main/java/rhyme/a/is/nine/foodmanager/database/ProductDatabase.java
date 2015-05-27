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
public class ProductDatabase extends Database<Product> {

    public ProductDatabase(String fileName) {
        super(fileName);
        this.list = new ArrayList<Product>();
    }

    public List<Product> getAllProducts() {
        return list;
    }

    public Product getProductByPosition(int position)
    {
        if (list.isEmpty())
            return null;

        return list.get(position);
    }


    public Product getProductByName(String name)
    {
        if (list.isEmpty())
            return null;

        for(Product i : list)
        {
            if (i.getName().equals(name))
                return i;
        }
        return null;
    }

    public Product getProductByBarcode(String barcode)
    {
        if (list.isEmpty())
            return null;

        for(Product i : list)
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
        for(Product i : list)
        {
            if (i.equals(product)) {
                i.increaseCount();
                isNew = false;
            }
        }
        if(isNew) {
            if(product.getBestBeforeDate() != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getBestBeforeDate() != null && product.getBestBeforeDate().before(list.get(i).getBestBeforeDate())) {
                        list.add(i, product);
                        return;
                    }
                }
            }
            list.add(product);
        }
    }

    public void removeProductByPosition(int position, boolean removeCompletely)
    {
        if (list.isEmpty())
            return;

        if(removeCompletely)
            list.remove(position);
        else {
            list.get(position).decreaseCount();

            if(list.get(position).getCount() <= 0)
                list.remove(position);
        }
    }

    public void deleteAll()
    {
        list.clear();
    }

}
