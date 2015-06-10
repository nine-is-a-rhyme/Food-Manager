package rhyme.a.is.nine.foodmanager.recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by Stefan on 20.05.2015.
 */
public class Recipe implements Serializable, Comparable<Recipe> {



    private String imageURL = null;
    private String name = null;
    private String preparation = null;
    private String duration = null;
    private List<Ingredients> ingredients = null;

    public Recipe()
    {

    }
    public Recipe(String name, String imageURL, List<Ingredients> ingredients,
                  String preparation,
                  String duration)
    {
        this.name = name;
        this.imageURL = imageURL;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.duration = duration;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setPreparation(String preparation)
    {
        this.preparation = preparation;
    }
    public void setDuration(String duration)
    {
        this.duration = duration;
    }
    public void setIngredients(List<Ingredients> ingredients)
    {
        this.ingredients = ingredients;
    }
    public String getImageURL() {return imageURL;}
    public String getName() {return name;}
    public String getPreparation() {return preparation;}
    public String getDuration() {return duration;}
    public List<Ingredients> getIngredients() {return ingredients;}

    @Override
    public int compareTo(Recipe another) {
        List<Product> products = MainActivity.fridgeDatabase.getAllProducts();
        List<String> cat_names = new ArrayList<String>();
        List<String> prod_names = new ArrayList<String>();
        for (int i = 0; i < products.size(); i++)
        {
            cat_names.add(products.get(i).getCategory());
            prod_names.add(products.get(i).getName());
        }
        int num_this = countIngredients(this.getIngredients(), cat_names, prod_names);
        int num_another = countIngredients(another.getIngredients(), cat_names, prod_names);

        return  num_another - num_this;
    }

    private int countIngredients(List<Ingredients> ingredients, List<String> categories, List<String> products)
    {
        int count = 0;
        for (int i = 0; i < ingredients.size(); i++)
        {
            if (categories.contains(ingredients.get(i).getName()) || products.contains(ingredients.get(i).getName()))
            {
                count++;
            }
        }
        return count;
    }
}

