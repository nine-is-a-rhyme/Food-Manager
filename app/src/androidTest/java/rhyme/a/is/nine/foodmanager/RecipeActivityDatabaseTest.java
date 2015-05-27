package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.List;

import rhyme.a.is.nine.foodmanager.recipe.Recipe;
import rhyme.a.is.nine.foodmanager.database.RecipeDatabase;

/**
 * Created by lalinda on 5/27/15.
 */
public class RecipeActivityDatabaseTest extends ApplicationTestCase<Application> {
    public RecipeActivityDatabaseTest() { super(Application.class); }


    public void testLoadJSON(){
        RecipeDatabase db = new RecipeDatabase("ichkoche.json");
        db.readFromFile(getContext());
        List<Recipe> recipes = db.getRecipes();
        assert(!recipes.isEmpty());
    }

    public void testRecipeName(){
        RecipeDatabase db = new RecipeDatabase("ichkoche.json");
        db.readFromFile(getContext());
        List<Recipe> recipes = db.getRecipes();
        assert(!recipes.isEmpty());
        Recipe rep = recipes.get(0);
        assertEquals(rep.getName(), "Ribiseltorte");
    }

    public void testRecipeIngredientsNum(){
        RecipeDatabase db = new RecipeDatabase("ichkoche.json");
        db.readFromFile(getContext());
        List<Recipe> recipes = db.getRecipes();
        assert(!recipes.isEmpty());
        Recipe rep = recipes.get(0);
        assertEquals(rep.getIngredients().size(), 12);
    }

    public void testRecipeNum(){
        RecipeDatabase db = new RecipeDatabase("ichkoche.json");
        db.readFromFile(getContext());
        List<Recipe> recipes = db.getRecipes();
        assert(!recipes.isEmpty());
        assertEquals(recipes.size(), 993);
    }



}
