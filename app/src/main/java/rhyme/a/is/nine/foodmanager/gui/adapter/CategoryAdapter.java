package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.product.Category;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by Fabio on 6/10/2015.
 */
public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private ListView listView;

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CategoryAdapter() {
    }

    @Override
    public int getCount() {
        List<Category> categories = MainActivity.categoryDatabase.getAllCategories();
        if(categories != null)
            return categories.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.categoryDatabase.getCategoryByPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Category category = MainActivity.categoryDatabase.getCategoryByPosition(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.category_list_element, null);
        TextView categoryName = (TextView) rowView.findViewById(R.id.category_list_name);
        ImageButton editButton = (ImageButton) rowView.findViewById(R.id.category_list_edit);
        editButton.setTag(position);

        categoryName.setText(category.getName());

        return rowView;
    }

    public void removeItem(int position, boolean completely) {
        //MainActivity.categoryDatabase.removeCategoryByPosition(position, completely);
    }

    public void deleteAll()
    {
       // MainActivity.categoryDatabase.deleteAll();
    }
}
