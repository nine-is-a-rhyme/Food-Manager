package rhyme.a.is.nine.foodmanager.util;

import java.util.Calendar;

import rhyme.a.is.nine.foodmanager.database.PriceDatabase;
import rhyme.a.is.nine.foodmanager.database.ProductDatabase;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.product.PriceEntity;
import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 27/05/15.
 */
public class FakeData {

    public static void generateFakeData() {

        PriceDatabase priceDatabase = MainActivity.priceDatabase;
        ProductDatabase fridgeDatabase = MainActivity.fridgeDatabase;

        Calendar cal = Calendar.getInstance();
        priceDatabase.addPriceEntity(new PriceEntity("", 10.23f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 1.23f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 12.23f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 10f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 7.2f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 10.23f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 14.2f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 21.24f, cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        priceDatabase.addPriceEntity(new PriceEntity("", 10.23f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 12.23f, cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        priceDatabase.addPriceEntity(new PriceEntity("", 10.45f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 20.23f, cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        priceDatabase.addPriceEntity(new PriceEntity("", 18.23f, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        priceDatabase.addPriceEntity(new PriceEntity("", 14.23f, cal.getTime()));
        cal.add(Calendar.MONTH, -1);

        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        fridgeDatabase.addProduct(new Product("Milch", "Milchprodukte", "123", "1L", 2, cal.getTime()));
        cal.add(Calendar.DATE, 5);
        fridgeDatabase.addProduct(new Product("Tomaten", "Gemüse", "1234", "", 7, cal.getTime()));
        cal.add(Calendar.DATE, -7);
        fridgeDatabase.addProduct(new Product("Butter", "Milchprodukte", "12345", "", 1, cal.getTime()));
        cal.add(Calendar.DATE, 10);
        fridgeDatabase.addProduct(new Product("Saft", "Getränke", "123456", "", 6, cal.getTime()));
    }
}
