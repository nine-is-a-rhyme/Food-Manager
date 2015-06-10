package rhyme.a.is.nine.foodmanager.unit;

import android.app.Application;
import android.test.ApplicationTestCase;

import rhyme.a.is.nine.foodmanager.product.ConnectionTask;

/**
 * Created by martinmaritsch on 20/05/15.
 */
public class ConnectionTaskTest extends ApplicationTestCase<Application>{
    public ConnectionTaskTest() { super(Application.class); }

    public void testGetFoodApiResponse() {
        ConnectionTask ct = new ConnectionTask();
        String response = "";
        try {
            response = ct.execute("http://www.codecheck.info/product.search?q=90129025").get();
        } catch (Exception e) {
            fail();
        }

        if(response == null || response.equals(""))
            fail();

        assertEquals("<!DOCTYPE html>", response.substring(0, 15));
    }

    public void testShouldFail() {
        ConnectionTask ct = new ConnectionTask();
        String response = "";
        try {
            response = ct.execute("http://www.thisisjustafakeurlwhichshouldoptimallynotexist.qwertz").get();
        } catch (Exception e) {
            fail();
        }
        assertNull(response);
    }

}
