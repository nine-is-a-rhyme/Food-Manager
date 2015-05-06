package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import rhyme.a.is.nine.foodmanager.database.ProductDatabase;
import rhyme.a.is.nine.foodmanager.product.Product;


/**
 * Created by martinmaritsch on 29/04/15.
 */
public class ProductDatabaseTest extends ApplicationTestCase<Application> {
    public ProductDatabaseTest() { super(Application.class); }

    public void testAddProduct() {
        ProductDatabase db = new ProductDatabase(null);
        db.addProduct(new Product("name", "category", "barcode", "size", 1));
        assertEquals(1, db.getAllProducts().size());
    }

    public void testAddProductShouldNotReturnProducts() {
        ProductDatabase db = new ProductDatabase(null);
        db.addProduct(new Product("name", "category", "barcode123", "size", 1));
        assertEquals("barcode123", db.getProductByPosition(0).getBarcode());
    }

    public void testGetProductByName() {
        ProductDatabase db = new ProductDatabase(null);
        db.addProduct(new Product("name123", "category123", "barcode", "size", 1));
        assertEquals("category123", db.getProductByName("name123").getCategory());
    }

    public void testModifyProduct() {
        ProductDatabase db = new ProductDatabase(null);
        db.addProduct(new Product("name456", "category123", "barcode", "size", 1));
        Product product = db.getProductByName("name456");
        product.setSize("12345");
        assertEquals("12345", db.getProductByName("name456").getSize());
    }

    public void testDecreaseProductByOne() {
        ProductDatabase db = new ProductDatabase(null);
        db.addProduct(new Product("name456", "category123", "barcode", "size", 2));
        Product product = db.getProductByName("name456");
        product.decreaseCount();
        assertEquals(1, db.getProductByName("name456").getCount());
    }

    public void testDecreaseProductShouldDeleteProductCompletely() {
        ProductDatabase db = new ProductDatabase(null);
        db.addProduct(new Product("testname", "category123", "barcode12345", "size", 1));
        db.removeProductByPosition(0, true);
        assertNull(db.getProductByPosition(0));
    }

    public void testSaveAndReadDb() {
        ProductDatabase db = new ProductDatabase("test.db");
        db.addProduct(new Product("name456", "category123", "thisIsAFakeBarcode", "size", 1));
        db.writeToFile(getSystemContext());

        ProductDatabase newDb = new ProductDatabase("test.db");
        newDb.readFromFile(getSystemContext());
        assertEquals("thisIsAFakeBarcode", newDb.getProductByName("name456").getBarcode());
    }


}
