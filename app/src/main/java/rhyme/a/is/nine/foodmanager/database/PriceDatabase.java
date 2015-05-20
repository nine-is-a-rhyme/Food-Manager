package rhyme.a.is.nine.foodmanager.database;

import android.content.Context;
import android.graphics.Color;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import rhyme.a.is.nine.foodmanager.gui.graph.Bar;
import rhyme.a.is.nine.foodmanager.product.PriceEntity;

/**
 * Created by martinmaritsch on 20/05/15.
 */
public class PriceDatabase implements Serializable {


    private List<PriceEntity> priceEntities;

    private String fileName;


    public PriceDatabase(String fileName) {
        this.fileName = fileName;
        this.priceEntities = new ArrayList<PriceEntity>();
    }

    public List<PriceEntity> getAllPriceEntities() {
        return priceEntities;
    }

    public ArrayList<Bar> getBars() {

        if(priceEntities == null || priceEntities.size() == 0)
            return null;

        ArrayList<Bar> points = new ArrayList<>();

        Calendar cal = Calendar.getInstance();

        System.out.println("Date = " + cal.getTime());

        for (int i = 0; i < 4; i++) {
            Bar bar = new Bar();
            Date max = cal.getTime();
            cal.add(Calendar.DATE, -7);
            Date min = cal.getTime();

            float sum = 0;
            for (PriceEntity p : getPriceEntitiesByDateInterval(min, max)) {
                sum += p.getPrice();
            }

            bar.setValue(sum);
            bar.setName(new SimpleDateFormat("dd MMM").format(min).toString() + " - " + new SimpleDateFormat("dd MMM").format(max).toString());
            bar.setColor((i % 2 == 0) ? Color.parseColor("#99CC00") : Color.parseColor("#FFBB33"));

            points.add(bar);
        }
        Collections.reverse(points);
        return points;
    }

    public float getLastMonthValue() {

        if(priceEntities == null || priceEntities.size() == 0)
            return 0f;

        float sum = 0f;
        Calendar cal = Calendar.getInstance();
        Date max = cal.getTime();
        cal.add(Calendar.MONTH, -1);
        Date min = cal.getTime();
        for (PriceEntity p : getPriceEntitiesByDateInterval(min, max)) {
            sum += p.getPrice();
        }
        return sum;
    }

    public PriceEntity getPriceEntityByPosition(int position) {
        if (priceEntities.isEmpty())
            return null;

        return priceEntities.get(position);
    }

    public PriceEntity getPriceEntityByName(String name) {
        if (priceEntities.isEmpty())
            return null;

        for (PriceEntity i : priceEntities) {
            if (i.getName().equals(name))
                return i;
        }
        return null;
    }

    public List<PriceEntity> getPriceEntitiesByDateInterval(Date min, Date max) {
        if (priceEntities.isEmpty())
            return null;

        List<PriceEntity> values = new ArrayList<>();

        for (PriceEntity i : priceEntities) {
            if (i.getBuyDate().after(min) && i.getBuyDate().before(max))
                values.add(i);
        }
        return values;
    }

    public void addPriceEntity(PriceEntity priceEntity) {
        priceEntities.add(priceEntity);
    }

    public void removePriceEntityByPosition(int position) {
        priceEntities.remove(position);
    }

    public void writeToFile(Context context) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(this.fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(this.priceEntities);
            outputStream.close();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(Context context) {
        Object object = null;
        FileInputStream outputStream;
        try {
            outputStream = context.openFileInput(this.fileName);
            ObjectInputStream ois = new ObjectInputStream(outputStream);
            object = ois.readObject();
            outputStream.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (object != null)
            this.priceEntities = (List<PriceEntity>) object;
    }


}
