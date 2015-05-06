package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import rhyme.a.is.nine.foodmanager.database.ProductDatabase;
import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.product.ProductPlace;


/**
 * Created by martinmaritsch on 29/04/15.
 */
public class ProductDatabaseTest extends ApplicationTestCase<Application> {
    public ProductDatabaseTest() { super(Application.class); }

    public void testAddProduct() {
        ProductDatabase.addProduct(new Product("name", "category", "barcode", "size", 1));
        assertEquals(1, ProductDatabase.getAllProducts().size());
    }

    public void testAddProductShouldNotReturnProducts() {
        ProductDatabase.addProduct(new Product("name", "category", "barcode", "size", 1));
        assertEquals(0, ProductDatabase.getAllProducts().size());
    }

    public void testGetProductByName() {
        ProductDatabase.addProduct(new Product("name123", "category123", "barcode", "size", 1));
        assertEquals("category123", ProductDatabase.getProductByName("name123").getCategory());
    }

    public void testModifyProduct() {
        ProductDatabase.addProduct(new Product("name456", "category123", "barcode", "size", 1));
        Product product = ProductDatabase.getProductByName("name456");
        product.setSize("12345");
        assertEquals("12345", ProductDatabase.getProductByName("name456").getSize());
    }

}
