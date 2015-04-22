package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 22/04/15.
 */
public class BarcodeToProductConverterTest extends ApplicationTestCase<Application> {
    public BarcodeToProductConverterTest() { super(Application.class); }

    public void testGetFoodApiResponse() {
        String response = BarcodeToProductConverter.getFoodApiResponse("90129025");

        assertEquals("<!DOCTYPE html>", response.substring(0, 15));
    }

    public void testGetProductForBarcode() {
        Product response = BarcodeToProductConverter.getProductForBarcode("90129025");

        assertEquals("Puntigamer, Einzelflasche 0,5l Mehrweg", response.getName());
        assertEquals("0,50 l", response.getSize());
        assertEquals("Bier", response.getCategory());
    }
}