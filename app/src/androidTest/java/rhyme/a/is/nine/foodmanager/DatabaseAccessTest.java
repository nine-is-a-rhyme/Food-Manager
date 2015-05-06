package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import rhyme.a.is.nine.foodmanager.database.DatabaseAccess;
import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.product.ProductPlace;


/**
 * Created by martinmaritsch on 29/04/15.
 */
public class DatabaseAccessTest  extends ApplicationTestCase<Application> {
    public DatabaseAccessTest() { super(Application.class); }

    public void testAddProduct() {
        DatabaseAccess.addProduct(new Product("name", "category", "barcode", "size", 1, ProductPlace.FRIDGE));
        assertEquals(1, DatabaseAccess.getAllProducts().size());
    }

    public void testAddProductShouldNotReturnProducts() {
        DatabaseAccess.addProduct(new Product("name", "category", "barcode", "size", 1, ProductPlace.FRIDGE));
        assertEquals(0, DatabaseAccess.getProducts(ProductPlace.SHOPPING_LIST).size());
    }

    public void testGetProductByName() {
        DatabaseAccess.addProduct(new Product("name123", "category123", "barcode", "size", 1, ProductPlace.FRIDGE));
        assertEquals("category123", DatabaseAccess.getProductByName("name123", ProductPlace.FRIDGE).getCategory());
    }

    public void testAddProductTwice() {
        DatabaseAccess.addProduct(new Product("name123", "category123", "barcode", "size", 1, ProductPlace.FRIDGE));
        DatabaseAccess.addProduct(new Product("name123", "category123", "barcode", "size", 1, ProductPlace.FRIDGE));
        assertEquals(2, DatabaseAccess.getProductByName("name123", ProductPlace.FRIDGE).getCount());
    }

    public void testAddProductTwiceToTwoLists() {
        DatabaseAccess.addProduct(new Product("name12345", "category123", "barcode123", "size", 1, ProductPlace.FRIDGE));
        DatabaseAccess.addProduct(new Product("name12345", "category123", "barcode123", "size", 1, ProductPlace.SHOPPING_LIST));
        assertEquals(1, DatabaseAccess.getProductByName("name12345", ProductPlace.FRIDGE).getCount());
    }

    public void testModifyProduct() {
        DatabaseAccess.addProduct(new Product("name456", "category123", "barcode", "size", 1, ProductPlace.FRIDGE));
        Product product = DatabaseAccess.getProductByName("name456", ProductPlace.FRIDGE);
        product.setSize("12345");
        assertEquals("12345", DatabaseAccess.getProductByName("name456",ProductPlace.FRIDGE).getSize());
    }

}
