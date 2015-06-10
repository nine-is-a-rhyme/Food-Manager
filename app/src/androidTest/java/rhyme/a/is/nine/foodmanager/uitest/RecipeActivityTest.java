package rhyme.a.is.nine.foodmanager.uitest;

import rhyme.a.is.nine.foodmanager.gui.activity.RecipeActivity;
import junit.framework.Assert;
import com.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

public class RecipeActivityTest extends
        ActivityInstrumentationTestCase2<RecipeActivity> {

    private Solo solo;

    public RecipeActivityTest() {
        super(RecipeActivity.class);
        solo.assertCurrentActivity("MainActivity", RecipeActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }


    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


}
