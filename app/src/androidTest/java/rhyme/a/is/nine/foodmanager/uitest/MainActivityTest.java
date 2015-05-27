package rhyme.a.is.nine.foodmanager.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.*;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo mySolo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testActionBar() {
        mySolo.clickOnActionBarItem(R.id.floating_action_button_menu);
    }
}