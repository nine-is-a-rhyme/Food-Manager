package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.recipe.Recipe;

/**
 * Created by lalinda on 5/27/15.
 */
public class RecipeAdapter extends BaseAdapter {

    private Context context;

    public RecipeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        List<Recipe> recipes = MainActivity.recipeDatabase.getRecipes();
        if(recipes != null)
            return recipes.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.recipeDatabase.getRecipeByPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView recipe_name = new TextView(context);
        Recipe recipe = MainActivity.recipeDatabase.getRecipeByPosition(position);
        recipe_name.setText(recipe.getName());
        recipe_name.setTextColor(Color.BLACK);
        recipe_name.setHeight(125);
        recipe_name.setGravity(Gravity.CENTER_VERTICAL);
        recipe_name.setPadding(50,0,0,0);


        return recipe_name;
    }

}
