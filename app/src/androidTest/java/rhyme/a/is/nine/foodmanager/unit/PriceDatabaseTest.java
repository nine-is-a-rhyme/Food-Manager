package rhyme.a.is.nine.foodmanager.unit;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.Calendar;
import java.util.Date;

import rhyme.a.is.nine.foodmanager.database.PriceDatabase;
import rhyme.a.is.nine.foodmanager.product.PriceEntity;
import rhyme.a.is.nine.foodmanager.util.FakeData;


/**
 * Created by martinmaritsch on 29/04/15.
 */
public class PriceDatabaseTest extends ApplicationTestCase<Application> {
    public PriceDatabaseTest() { super(Application.class); }

    public void testGetMonthsInDb() {
        PriceDatabase db = new PriceDatabase(null);
        assertEquals(0, db.numberOfMonthsInDatabase());
    }

    public void testGetMonthsInDb2() {
        PriceDatabase db = new PriceDatabase(null);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -40);
        db.addPriceEntity(new PriceEntity("", 10.23f, cal.getTime()));
        assertEquals(1, db.numberOfMonthsInDatabase());
    }

    public void testGetMonthsInDb3() {
        PriceDatabase db = new PriceDatabase(null);
        generateFakeData(db);
        if(db.numberOfMonthsInDatabase() > 6 || db.numberOfMonthsInDatabase() < 4)
            fail();
    }

    public void testGetPriceEntitiesForMonth() {
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("", 10.23f, Calendar.getInstance().getTime()));
        assertEquals(1, db.getPriceEntitiesForMonth(0).size());
    }

    public void testGetPriceEntitiesByDateInterval() {
        PriceDatabase db = new PriceDatabase(null);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date min = cal.getTime();
        cal.add(Calendar.DATE, 2);
        Date max = cal.getTime();
        db.addPriceEntity(new PriceEntity("", 10.23f, Calendar.getInstance().getTime()));
        assertEquals(1, db.getPriceEntitiesByDateInterval(min, max).size());
    }

    public void testDelete() {
        PriceDatabase db = new PriceDatabase(null);
        db.addPriceEntity(new PriceEntity("name", 1.23f, Calendar.getInstance().getTime()));
        db.addPriceEntity(new PriceEntity("name2", 1.23f, Calendar.getInstance().getTime()));
        db.deleteAll();
        assertEquals(0, db.getAllPriceEntities().size());
    }

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

    public void testSaveAndReadDb() {
        PriceDatabase db = new PriceDatabase("pricetest.db");
        db.addPriceEntity(new PriceEntity("name", 123.45f, Calendar.getInstance().getTime()));
        db.writeToFile(getSystemContext());

        PriceDatabase newDb = new PriceDatabase("pricetest.db");
        newDb.readFromFile(getSystemContext());
        assertNotNull(newDb.getPriceEntityByName("name"));
        assertEquals(123.45f, newDb.getPriceEntityByName("name").getPrice());
    }


    private void generateFakeData(PriceDatabase priceDatabase) {
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
    }

}
