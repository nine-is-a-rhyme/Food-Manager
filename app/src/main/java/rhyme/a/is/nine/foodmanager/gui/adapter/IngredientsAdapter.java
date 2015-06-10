package rhyme.a.is.nine.foodmanager.gui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

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
        ingredient.setText( ( (Ingredients) getItem(position)).getAmount() + " - " + ( (Ingredients) getItem(position)).getName());

        ingredient.setTextColor(Color.BLACK);
        ingredient.setHeight(70);
        ingredient.setGravity(Gravity.CENTER_VERTICAL);
        ingredient.setPadding(50,0,0,0);


        return ingredient;
    }

}
