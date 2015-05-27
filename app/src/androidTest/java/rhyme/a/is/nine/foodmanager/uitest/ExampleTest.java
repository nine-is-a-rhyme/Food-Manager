package rhyme.a.is.nine.foodmanager.uitest;

/**
 * Created by Fabio on 5/20/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.regex.Pattern;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.CategoryActivity;
import rhyme.a.is.nine.foodmanager.gui.MainActivity;
import rhyme.a.is.nine.foodmanager.gui.ProductActivity;
import rhyme.a.is.nine.foodmanager.gui.Recipe;
import rhyme.a.is.nine.foodmanager.gui.fragment.FridgeFragment;
import rhyme.a.is.nine.foodmanager.gui.fragment.RecipeFragment;

@SuppressWarnings("rawtypes")
public class ExampleTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "rhyme.a.is.nine.foodmanager.gui.MainActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public ExampleTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testTabs() {
        solo.assertCurrentActivity("Test", MainActivity.class);
        solo.clickOnText("Einkaufsliste");
        solo.clickOnText("Rezepte");
        solo.clickOnText("K.?hlschrank");


    }

    public void testAddProducts() {
        //solo.clickOnView(getActivity().findViewById(R.id.floating_action_button_menu));
        solo.clickOnView(getActivity().findViewById(R.id.floating_action_button_add_manual));
        solo.clickOnView(getActivity().findViewById(R.id.floating_action_button_add_manual));
        solo.assertCurrentActivity("Test", ProductActivity.class);
        solo.enterText(0, "Produkt1");
        solo.clickOnText("Speichern");
        solo.assertCurrentActivity("Test", MainActivity.class);
        solo.waitForText("Produkt1");
        solo.clickOnButton(1);
        solo.clickOnButton(0);
        swipeLeftOnText("Produkt1");
        solo.clickOnText("Einkaufsliste");
        solo.waitForText("Produkt1");
        swipeLeftOnText("Produkt1");
    }

    public void testAddCategory() {
        solo.clickOnView(getActivity().findViewById(R.id.floating_action_button_add_manual));
        solo.clickOnView(getActivity().findViewById(R.id.floating_action_button_add_manual));
        solo.assertCurrentActivity("Test", ProductActivity.class);
        solo.clickOnText("Neu");
        solo.assertCurrentActivity("Test", CategoryActivity.class);
        solo.enterText(0, "Kategorie1");
        solo.enterText(1, "5");
        solo.clickOnText("Speichern");
        View view1 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view1);
        solo.scrollToTop(); // I put this in here so that it always keeps the list at start
        solo.waitForText("Kategorie1");
        solo.clickOnText("Kategorie1");
    }

    public void testBestBeforeDate() {
        solo.clickOnView(getActivity().findViewById(R.id.floating_action_button_add_manual));
        solo.clickOnView(getActivity().findViewById(R.id.floating_action_button_add_manual));
        solo.assertCurrentActivity("Test", ProductActivity.class);
        TextView beforedate = (TextView) getActivity().findViewById(R.id.et_bestbefore);
        solo.clickOnView(beforedate);
        //solo.clickOnText("20[.]5[.]2015");
        solo.waitForText("May");
        solo.clickOnText("22");
        solo.clickOnText("OK");
    }

    public void testRecipe() {
        solo.clickOnText("Rezepte");
        solo.clickOnText("Rezept finden!");
        solo.assertCurrentActivity("Test", Recipe.class);
    }

    protected void swipeLeftOnText(String text) {
        int fromX, toX, fromY, toY;
        int[] location = new int[2];

        View row = solo.getText(text);
        row.getLocationInWindow(location);

        // fail if the view with text cannot be located in the window
        if (location.length == 0) {
            fail("Could not find text: " + text);
        }

        fromX = location[0] + 200;
        fromY = location[1];

        toX = location[0] - 100;
        toY = fromY;

        solo.drag(fromX, toX, fromY, toY, 10);
    }
}