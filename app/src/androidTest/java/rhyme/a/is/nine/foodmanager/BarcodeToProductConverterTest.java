package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import rhyme.a.is.nine.foodmanager.database.ProductDatabase;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
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
    }

    public void testInvalidBarcode() {
        Product response = BarcodeToProductConverter.getProductForBarcode("99999999");
        assertNull(response);
    }

    public void testHistoryDatabase() {
        MainActivity.historyDatabase = new ProductDatabase(null);
        MainActivity.historyDatabase.addProduct(new Product("name", "category", "12345678", "1 L", 1));
        Product response = BarcodeToProductConverter.getProductForBarcode("12345678");
        assertEquals("name", response.getName());
        assertEquals("1 L", response.getSize());
    }

    public void testParseUnicode() {
        assertEquals("ä", BarcodeToProductConverter.parseUnicode("\\u00e4"));
    }

    public void testParseUnicode2() {
        assertEquals("ö", BarcodeToProductConverter.parseUnicode("\\u00f6"));
    }

    public void testParseUnicode3() {
        assertEquals("ü", BarcodeToProductConverter.parseUnicode("\\u00fc"));
    }

    public void testParseUnicode4() {
        assertEquals("Archäologie", BarcodeToProductConverter.parseUnicode("Arch\\u00e4ologie"));
    }
}