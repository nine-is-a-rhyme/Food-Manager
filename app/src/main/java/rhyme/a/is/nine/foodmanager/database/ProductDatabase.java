package rhyme.a.is.nine.foodmanager.database;

import android.util.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    public LinkedHashMap<String, Integer> getAllCategories() {
        if (list.isEmpty())
            return null;

        LinkedHashMap<String, Integer> categories = new LinkedHashMap<>();

        for(Product product : list) {
            if (!categories.containsKey(product.getCategory()))
                categories.put(product.getCategory(), 1);
            else
                categories.put(product.getCategory(), categories.get(product.getCategory()) + 1);
        }

        return categories;
    }

    public Pair<String, Integer> getCategory(int id) {
        if (list.isEmpty())
            return null;

        int i = 0;
        for(String category : getAllCategories().keySet()) {
            if (i == id)
                return new Pair<>(category, getAllCategories().get(category));
            i++;
        }
        return null;
    }

    public List<Product> getProductsForCategory(int id) {
        if (list.isEmpty())
            return null;

        String category = getCategory(id).first;
        List<Product> result = new ArrayList<>();
        for(Product product : list)
        {
            if (product.getCategory().equals(category))
                result.add(product);
        }

        return result;
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
