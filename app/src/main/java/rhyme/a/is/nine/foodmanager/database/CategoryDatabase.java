package rhyme.a.is.nine.foodmanager.database;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.product.Category;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class CategoryDatabase extends Database<Category> {

    public CategoryDatabase(String fileName) {
        super(fileName);
        list = new ArrayList<>();
    }

    public List<Category> getAllCategories() {
        return list;
    }

    public Category getCategoryByPosition(int position) {
        return list.isEmpty() ? null : list.get(position);
    }

    public void addCategory(Category cat) {
        list.add(cat);
    }

    public void setList(List<Category> cats) {
        list = cats;
    }

    public void setList(ArrayAdapter<Category> cats) {
        List<Category> new_list = new ArrayList<>();
        for (int counter = 0; counter < cats.getCount(); counter++) {
            new_list.add(cats.getItem(counter));
        }
        setList(new_list);
    }

    public void removeCategoryByPosition(int position) {
        if(list.isEmpty())
            return;

        list.remove(position);
    }

    public void deleteAll() {
        list.clear();
    }

}
