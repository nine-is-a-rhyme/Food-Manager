package rhyme.a.is.nine.foodmanager.ui;

/**
 * Created by Fabio on 5/20/2015.
 */
import android.test.ActivityInstrumentationTestCase2;
import android.view.Display;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.robotium.solo.Solo;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.CategoryActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.ProductActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.RecipeActivity;

@SuppressWarnings("rawtypes")
public class ExampleTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "rhyme.a.is.nine.foodmanager.gui.activity.MainActivity";

    private static Class<?> launcherActivityClass;

    static {
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

    public void testNavigationDrawer() {
        solo.assertCurrentActivity("Test", MainActivity.class);
        swipeToRight();
        solo.clickOnText("Einkaufsliste");
        swipeToRight();
        solo.clickOnText("Rezepte");
        swipeToRight();
        solo.clickOnText("Kategorien");
        swipeToRight();
        solo.clickOnText("Ausgaben");
        swipeToRight();
        solo.clickOnText("Einstellungen");
        swipeToRight();
        solo.clickOnText(".?ber");
        swipeToRight();
        solo.clickOnText("K.?hlschrank");

    }

    private void swipeToRight() {
        Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        float xStart = 0;
        float xEnd = width / 2;
        solo.drag(xStart, xEnd, height / 2, height / 2, 1);
    }

    public void testAddProducts() {
        solo.clickOnView(getActivity().findViewById(R.id.floating_action_button_add_manual));
        solo.clickOnView(getActivity().findViewById(R.id.floating_action_button_add_manual));
        solo.assertCurrentActivity("Test", ProductActivity.class);
        solo.enterText(0, "Produkt1");
        solo.clickOnText("Speichern");
        solo.assertCurrentActivity("Test", MainActivity.class);
        solo.clickInList(0);
        solo.waitForText("Produkt1");
        solo.clickOnImageButton(1);
        solo.clickOnImageButton(0);
        solo.clickOnImageButton(0);
        swipeToRight();
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
        solo.goBack();
        swipeToRight();
        solo.clickOnText("Kategorie");
        solo.waitForText("Kategorie1");
        swipeLeftOnText("Kategorie1");
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