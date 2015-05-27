package rhyme.a.is.nine.foodmanager.database;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
        this.priceEntities = new ArrayList<>();
    }

    public List<PriceEntity> getAllPriceEntities() {
        return priceEntities;
    }


    private int numberOfMonthsInDatabase() {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(priceEntities.get(0).getBuyDate());
        for (PriceEntity p : priceEntities) {
            if(startCalendar.getTime().after(p.getBuyDate()))
                startCalendar.setTime(p.getBuyDate());
        }

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(new Date());

        return (endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)) * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    }

    public BarData getMonthBars() {

        if(priceEntities == null || priceEntities.size() == 0)
            return null;

        int months = numberOfMonthsInDatabase();

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = months; i >= 0; i--) {
            float sum = 0f;
            for(PriceEntity p : getPriceEntitiesForMonth(i)) {
                sum += p.getPrice();
            }
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -i);
            xVals.add(new SimpleDateFormat("MMM yy", Locale.GERMAN).format(cal.getTime()));
            yVals.add(new BarEntry(sum, months - i));
        }

        BarDataSet set1 = new BarDataSet(yVals, "");
        set1.setBarSpacePercent(40f);

        set1.setColors(new int[] {Color.parseColor("#99CC00"), Color.parseColor("#FFBB33")});

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);

        // data.setValueFormatter(new MyValueFormatter());
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.02f", value) + "€";
            }
        });
        data.setValueTextSize(10f);
        return data;
    }

    public BarData getWeekBarsForMonth(int monthIndex) {

        if(priceEntities == null || priceEntities.size() == 0)
            return null;

        List<Pair<Date, List<PriceEntity>>> entities = getPriceEntitiesForMonthWeeks(numberOfMonthsInDatabase()- monthIndex);

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> yVals = new ArrayList<>();

        int i = 0;
        for(Pair<Date, List<PriceEntity>> priceEntityPair : entities) {
            float sum = 0;
            for(PriceEntity p : priceEntityPair.second) {
                sum += p.getPrice();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(priceEntityPair.first);
            cal.add(Calendar.DAY_OF_MONTH, 1); // now it's monday
            Date begin = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, 6); // now it's sunday again
            Date end = cal.getTime();

            //xVals.add(new SimpleDateFormat("dd. MMM", Locale.GERMAN).format(begin) + " - " + new SimpleDateFormat("dd. MMM", Locale.GERMAN).format(end));
            xVals.add("KW " + new SimpleDateFormat("w", Locale.GERMAN).format(begin));
            yVals.add(new BarEntry(sum, i));
            i++;
        }

        BarDataSet set1 = new BarDataSet(yVals, "");
        set1.setBarSpacePercent(40f);

        set1.setColors(new int[] {Color.parseColor("#99CC00"), Color.parseColor("#FFBB33")});

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);

        // data.setValueFormatter(new MyValueFormatter());
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.02f", value) + "€";
            }
        });
        data.setValueTextSize(10f);
        return data;
    }

    public List<PriceEntity> getPriceEntitiesForMonth(int monthsAgo) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -monthsAgo);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        Date min = cal.getTime();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date max = cal.getTime();
        return getPriceEntitiesByDateInterval(min, max);
    }

    public List<Pair<Date, List<PriceEntity>>> getPriceEntitiesForMonthWeeks(int monthsAgo) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -monthsAgo);
        cal.set(GregorianCalendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, -1); // last sunday in month
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

        Calendar firstDay = Calendar.getInstance();
        firstDay.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);

        List<Date> sundays = new ArrayList<>();
        sundays.add(cal.getTime());
        while(cal.getTime().after(firstDay.getTime())) {
            cal.add(Calendar.DATE, -7);
            sundays.add(cal.getTime());
        }
        Collections.reverse(sundays);

        List<Pair<Date, List<PriceEntity>>> entities = new ArrayList<>();

        for (int i = 0; i < sundays.size() - 1; i++) {

            entities.add(new Pair<>(sundays.get(i), getPriceEntitiesByDateInterval(sundays.get(i), sundays.get(i+1))));
        }
        return entities;
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
