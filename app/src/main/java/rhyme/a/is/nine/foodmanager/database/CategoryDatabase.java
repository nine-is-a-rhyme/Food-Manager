package rhyme.a.is.nine.foodmanager.database;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.product.Category;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class CategoryDatabase extends Database<Category> {

    public CategoryDatabase(String fileName) {
        super(fileName);
    }

    public List<Category> getAllCategories() {
        if (list.size() == 0)
        {
            list = new ArrayList<Category>();
            list.add(new Category("Lebensmittel", 10));
            list.add(new Category("Getränke", 30));
        }
        return list;
    }

    public Category getCategoryByPosition(int position)
    {
        if (list.isEmpty())
            return null;

        return list.get(position);
    }
    public void addCategory(Category cat)
    {
        list.add(cat);
    }
    public void setList(List<Category> cats)
    {
        list = cats;
    }

    public void setList(ArrayAdapter<Category> cats)
    {
        List<Category> new_list = new ArrayList<Category>();
        for (int counter = 0; counter < cats.getCount(); counter++)
        {
            new_list.add(cats.getItem(counter));
        }
        setList(new_list);
    }

}
