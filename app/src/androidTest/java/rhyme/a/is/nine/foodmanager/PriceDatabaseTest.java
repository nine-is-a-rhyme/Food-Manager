package rhyme.a.is.nine.foodmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.Calendar;

import rhyme.a.is.nine.foodmanager.database.PriceDatabase;
import rhyme.a.is.nine.foodmanager.product.PriceEntity;


/**
 * Created by martinmaritsch on 29/04/15.
 */
public class PriceDatabaseTest extends ApplicationTestCase<Application> {
    public PriceDatabaseTest() { super(Application.class); }

    public void testAddPrice() {
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("name", 1.23f, Calendar.getInstance().getTime()));
        assertEquals(1, db.getAllPriceEntities().size());
    }

    public void testGetPrice() {
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("name", 1.23f, Calendar.getInstance().getTime()));
        assertEquals(1.23f, db.getAllPriceEntities().get(0).getPrice());
    }

    public void testGetPriceByName() {
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("name", 1.23f, Calendar.getInstance().getTime()));
        assertEquals(1.23f, db.getPriceEntityByName("name").getPrice());
    }

    public void testGetPriceByPosition() {
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("name", 1.23f, Calendar.getInstance().getTime()));
        assertEquals(1.23f, db.getPriceEntityByPosition(0).getPrice());
    }

    public void testGetLastMonthValue() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -5);
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("name", 1.23f, cal.getTime()));
        db.addPriceEntity(new PriceEntity("name", 1f, cal.getTime()));
        assertEquals(2.23f, db.getLastMonthValue());
    }

    public void testGetBars() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -5);
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("name", 1.23f, cal.getTime()));
        db.addPriceEntity(new PriceEntity("name", 1f, cal.getTime()));
        assertEquals(2.23f, db.getBars().get(3).getValue());
    }

    public void testGetBars2() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -5);
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("name", 1.23f, cal.getTime()));
        db.addPriceEntity(new PriceEntity("name", 1f, cal.getTime()));

        cal.add(Calendar.DATE, -7);
        db.addPriceEntity(new PriceEntity("name", 1.23f, cal.getTime()));

        assertEquals(2.23f, db.getBars().get(3).getValue());
        assertEquals(1.23f, db.getBars().get(2).getValue());
    }

    public void testPriceEntitiesForWeek() {
        PriceDatabase db = new PriceDatabase(null);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        db.addPriceEntity(new PriceEntity("name", 1.23f, cal.getTime()));
        assertEquals(1, db.getPriceEntitiesForWeek(0).size());
    }

    public void testPriceEntitiesForWeek2() {
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("name", 1.23f, Calendar.getInstance().getTime()));
        assertEquals(0, db.getPriceEntitiesForWeek(1).size());
    }

    public void testSaveAndReadDb() {
        PriceDatabase db = new PriceDatabase("price_test.db");
        db.addPriceEntity(new PriceEntity("name", 123.45f, Calendar.getInstance().getTime()));
        db.writeToFile(getSystemContext());

        PriceDatabase newDb = new PriceDatabase("price_test.db");
        newDb.readFromFile(getSystemContext());
        assertNotNull(newDb.getPriceEntityByName("name"));
        assertEquals(123.45f, newDb.getPriceEntityByName("name").getPrice());
    }


}
