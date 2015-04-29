package rhyme.a.is.nine.foodmanager.database;

import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.product.ProductPlace;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class DatabaseAccess {

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
        product.setCount(1);
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
}
