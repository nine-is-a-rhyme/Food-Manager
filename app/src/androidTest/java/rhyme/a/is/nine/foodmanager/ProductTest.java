package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.Calendar;
import java.util.Date;

import rhyme.a.is.nine.foodmanager.database.ProductDatabase;
import rhyme.a.is.nine.foodmanager.product.Product;


/**
 * Created by martinmaritsch on 29/04/15.
 */
public class ProductTest extends ApplicationTestCase<Application> {
    public ProductTest() { super(Application.class); }

    private Product test = new Product("name", "category", "barcode", "size", 1);

    public void testNewProductName() {
        assertEquals("name", test.getName());
    }

    public void testNewProductCategory() {
        assertEquals("category", test.getCategory());
    }

    public void testNewProductBarcode() {
        assertEquals("barcode", test.getBarcode());
    }

    public void testNewProductSize() {
        assertEquals("size", test.getSize());
    }

    public void testNewProductCount() {
        assertEquals(1, test.getCount());
    }

    public void testIncreaseCount() {
        int count = test.getCount();
        test.increaseCount();
        assertEquals(count + 1, test.getCount());
    }

    public void testDecreaseCount() {
        int count = test.getCount();
        test.decreaseCount();
        assertEquals((count != 0) ? (count-1) : 0, test.getCount());
    }

    public void testDecreaseCount2() {
        while(test.getCount() > 0) {
            test.decreaseCount();
        }
        test.decreaseCount(); // -1 or 0 !? -> should be 0
        assertEquals(0, test.getCount());
    }

    public void testEquals() {
        Product a = new Product("name", "category", "barcode", "size", 1);
        Product b = new Product("name", "category", "barcode", "size", 1);
        assertTrue(a.equals(b));
    }

    public void testEquals2() {
        Product a = new Product("name", "category", "barcode", "size", 1);
        Product b = new Product("name", "c", "barcode", "size", 1);
        assertTrue(a.equals(b));
    }

    public void testEquals3() {
        Product a = new Product("name", "category", "barcode", "size", 1);
        Product b = new Product("name", "category", "b", "size", 1);
        assertTrue(a.equals(b));
    }

    public void testEquals4() {
        Product a = new Product("name", "category", "barcode", "size", 1);
        Product b = new Product("name", "category", "barcode", "s", 1);
        assertTrue(a.equals(b));
    }

    public void testEquals5() {
        Date d = Calendar.getInstance().getTime();
        Product a = new Product("name", "category", "barcode", "size", 1, d);
        Product b = new Product("name", "category", "barcode", "size", 1, d);
        assertTrue(a.equals(b));
    }

    public void testEquals6() {
        Date d = Calendar.getInstance().getTime();
        Product a = new Product("name", "category", "barcode", "size", 1, d);
        Product b = new Product("name", "c", "barcode", "size", 1, d);
        assertTrue(a.equals(b));
    }

    public void testEqualsFail() {
        Product a = new Product("name", "category", "barcode", "size", 1);
        Product b = new Product("nam", "category", "barcode", "size", 1);
        assertFalse(a.equals(b));
    }

    public void testEqualsFail2() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        Product a = new Product("name", "category", "barcode", "size", 1, cal.getTime());
        Product b = new Product("name", "category", "barcode", "size", 1, Calendar.getInstance().getTime());
        assertFalse(a.equals(b));
    }

    public void testEqualsFailWithObject() {
        Product a = new Product("name", "category", "barcode", "size", 1);
        assertFalse(a.equals(new Object()));
    }

}
