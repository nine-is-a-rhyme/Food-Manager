package rhyme.a.is.nine.foodmanager.recipe;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Stefan on 20.05.2015.
 */
public class Recipe implements Serializable {



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
}

