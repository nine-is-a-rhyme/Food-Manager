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

    public static List<Product> getAllProducts() {
        return products;
    }

    public static List<Product> getProducts(ProductPlace productPlace) {
        List<Product> temp = new ArrayList<Product>();

        for(Product i : products)
        {
            if (i.getProductPlace() == productPlace)
                temp.add(i);
        }
        return temp;
    }

    public static Product getProductByName(String name, ProductPlace productPlace)
    {
        for(Product i : products)
        {
            if (i.getName().equals(name) && i.getProductPlace() == productPlace)
                return i;
        }
        return null;
    }

    public static void addProduct(Product product)
    {
        products.add(product);
    }

    public static void deleteProductByName(String name, ProductPlace productPlace)
    {
        for(Product i : products)
        {
            if (i.getName().equals(name) && i.getProductPlace() == productPlace)
                products.remove(i);
        }
    }
}
