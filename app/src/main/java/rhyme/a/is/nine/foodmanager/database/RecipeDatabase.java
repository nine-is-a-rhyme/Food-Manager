package rhyme.a.is.nine.foodmanager.database;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.recipe.Ingredients;
import rhyme.a.is.nine.foodmanager.recipe.Recipe;


/**
 * Created by lalinda on 5/27/15.
 */
public class RecipeDatabase {


    private List<Recipe> recipes;
    private String fileName;

    public RecipeDatabase(String fileName) {
        this.fileName = fileName;
        this.recipes = new ArrayList<Recipe>();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void readFromFile(Context context) {

        try {
            JSONObject data = new JSONObject(loadJSONFromAsset(context));
            JSONArray recipes_json = data.getJSONArray("Rezepte");
            for(int i = 0; i < recipes_json.length(); i++){
                Recipe recipe_data = new Recipe();

                JSONObject recipe = recipes_json.getJSONObject(i);
                JSONArray ingredients = recipe.getJSONArray("Zutaten");
                List<Ingredients> ing_list = new ArrayList<Ingredients>();
                for(int j = 0; j < ingredients.length(); j++) {
                    Ingredients ingredients_data = new Ingredients();
                    JSONObject ing = ingredients.getJSONObject(j);
                    ingredients_data.setAmount(ing.getString("Menge"));
                    ingredients_data.setName(ing.getString("Zutat"));
                    ing_list.add(ingredients_data);
                }
                recipe_data.setIngredients(ing_list);
                recipe_data.setName(recipe.getString("Rezeptname"));
                if(recipe.has("Bild")){
                    recipe_data.setImageURL(recipe.getString("Bild"));
                }

                recipe_data.setDuration(recipe.getString("Kochdauer"));
                recipe_data.setPreparation(recipe.getString("Zubereitung"));
                recipes.add(recipe_data);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String loadJSONFromAsset(Context context) {
        String json = null;
        try{
            InputStream is = context.getAssets().open("ichkoche.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
