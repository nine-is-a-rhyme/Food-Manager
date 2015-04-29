package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.ExecutionException;

import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter;
import rhyme.a.is.nine.foodmanager.product.ConnectionTask;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 22/04/15.
 */
public class BarcodeToProductConverterTest extends ApplicationTestCase<Application> {
    public BarcodeToProductConverterTest() { super(Application.class); }

    public void testGetFoodApiResponse() {
        ConnectionTask ct = new ConnectionTask();
        String response = "";
        try {
            response = ct.execute("http://www.codecheck.info/product.search?q=90129025").get();
        } catch (Exception e) {
            fail();
        }

        if(response == null || response == "")
            fail();

        assertEquals("<!DOCTYPE html>", response.substring(0, 15));
    }

    public void testGetProductForBarcode() {
        Product response = BarcodeToProductConverter.getProductForBarcode("90129025");

        assertEquals("Puntigamer, Einzelflasche 0,5l Mehrweg", response.getName());
        assertEquals("0,50 l", response.getSize());
        assertEquals("Bier", response.getCategory());
    }

    public void testInvalidBarcode() {
        Product response = BarcodeToProductConverter.getProductForBarcode("99999999");
        assertNull(response);
    }
}