package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.recipe.Ingredients;
import rhyme.a.is.nine.foodmanager.recipe.Recipe;

/**
 * Created by lalinda on 5/27/15.
 */
public class IngredientsAdapter extends BaseAdapter {

    private Context context;
    private Recipe recipe;

    public IngredientsAdapter(Context context, Recipe recipe) {

        this.context = context;
        this.recipe = recipe;
    }

    @Override
    public int getCount() {
        List<Ingredients> ingredients = recipe.getIngredients();
        if(ingredients != null)
            return ingredients.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return recipe.getIngredients().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView ingredient = new TextView(context);
        if(((Ingredients) getItem(position)).getAmount().equals(" ") ){
            ingredient.setText(( (Ingredients) getItem(position)).getName());
        }
        else{
            ingredient.setText( ( (Ingredients) getItem(position)).getAmount() + " - " + ( (Ingredients) getItem(position)).getName());
        }


        if (isInFridge(((Ingredients) getItem(position)).getName()))
        {
            ingredient.setTextColor(Color.rgb(0, 150, 0));
        }
        else
        {
            ingredient.setTextColor(Color.rgb(200, 0, 0));
        }
        ingredient.setHeight(70);
        ingredient.setGravity(Gravity.CENTER_VERTICAL);
        ingredient.setPadding(50,0,0,0);


        return ingredient;
    }

    private boolean isInFridge(String ing_name)
    {
        List<Product> products = MainActivity.fridgeDatabase.getAllProducts();
        List<String> cat_names = new ArrayList<String>();
        List<String> prod_names = new ArrayList<String>();
        for (int i = 0; i < products.size(); i++)
        {
            cat_names.add(products.get(i).getCategory());
            prod_names.add(products.get(i).getName());
        }

        if (cat_names.contains(ing_name) || prod_names.contains(ing_name))
            return true;
        return  false;
    }

}
