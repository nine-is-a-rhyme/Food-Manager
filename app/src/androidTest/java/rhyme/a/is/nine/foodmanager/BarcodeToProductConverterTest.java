package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter;

/**
 * Created by martinmaritsch on 22/04/15.
 */
public class BarcodeToProductConverterTest extends ApplicationTestCase<Application> {
    public BarcodeToProductConverterTest() { super(Application.class); }

    public void testGetFoodApiResponse() {
        String response = BarcodeToProductConverter.getFoodApiResponse("90129025");

        assertEquals("<!DOCTYPE html>", response.substring(0, 15));
    }
}