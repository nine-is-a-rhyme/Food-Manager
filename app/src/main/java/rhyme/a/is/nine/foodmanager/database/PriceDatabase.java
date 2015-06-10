package rhyme.a.is.nine.foodmanager.database;

import android.graphics.Color;
import android.util.Pair;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import rhyme.a.is.nine.foodmanager.product.PriceEntity;

/**
 * Created by martinmaritsch on 20/05/15.
 */
public class PriceDatabase extends Database<PriceEntity> {
    public PriceDatabase(String fileName) {
        super(fileName);
        this.list = new ArrayList<>();
    }

    public List<PriceEntity> getAllPriceEntities() {
        return list;
    }


    private int numberOfMonthsInDatabase() {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(list.get(0).getBuyDate());
        for (PriceEntity p : list) {
            if (startCalendar.getTime().after(p.getBuyDate()))
                startCalendar.setTime(p.getBuyDate());
        }

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(new Date());

        return (endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)) * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    }

    public BarData getMonthBars() {

        if (list == null || list.size() == 0)
            return null;

        int months = numberOfMonthsInDatabase();

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = months; i >= 0; i--) {
            float sum = 0f;
            for (PriceEntity p : getPriceEntitiesForMonth(i)) {
                sum += p.getPrice();
            }
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -i);
            xVals.add(new SimpleDateFormat("MMM yy", Locale.GERMAN).format(cal.getTime()));
            yVals.add(new BarEntry(sum, months - i));
        }

        BarDataSet set1 = new BarDataSet(yVals, "");
        set1.setBarSpacePercent(40f);

        set1.setColor(Color.rgb(148, 212, 212)/*Color.parseColor("#FFBB33")*/);

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

        if (list == null || list.size() == 0)
            return null;

        List<Pair<Date, List<PriceEntity>>> entities = getPriceEntitiesForMonthWeeks(numberOfMonthsInDatabase() - monthIndex);

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> yVals = new ArrayList<>();

        int i = 0;
        for (Pair<Date, List<PriceEntity>> priceEntityPair : entities) {
            float sum = 0;
            for (PriceEntity p : priceEntityPair.second) {
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

        set1.setColor(Color.rgb(179, 48, 80)/*Color.parseColor("#99CC00")*/);

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

        cal.add(Calendar.WEEK_OF_YEAR, 1); // add one week

        List<Date> sundays = new ArrayList<>();
        sundays.add(cal.getTime());
        while (cal.getTime().after(firstDay.getTime())) {
            cal.add(Calendar.DATE, -7);
            sundays.add(cal.getTime());
        }
        Collections.reverse(sundays);

        List<Pair<Date, List<PriceEntity>>> entities = new ArrayList<>();

        for (int i = 0; i < sundays.size() - 1; i++) {

            entities.add(new Pair<>(sundays.get(i), getPriceEntitiesByDateInterval(sundays.get(i), sundays.get(i + 1))));
        }
        return entities;
    }


    public PriceEntity getPriceEntityByPosition(int position) {
        if (list.isEmpty())
            return null;

        return list.get(position);
    }

    public PriceEntity getPriceEntityByName(String name) {
        if (list.isEmpty())
            return null;

        for (PriceEntity i : list) {
            if (i.getName().equals(name))
                return i;
        }
        return null;
    }

    public List<PriceEntity> getPriceEntitiesByDateInterval(Date min, Date max) {
        if (list.isEmpty())
            return null;

        List<PriceEntity> values = new ArrayList<>();

        for (PriceEntity i : list) {
            if (i.getBuyDate().after(min) && i.getBuyDate().before(max))
                values.add(i);
        }
        return values;
    }

    public void deleteAll() {
        list.clear();
    }

    public void addPriceEntity(PriceEntity priceEntity) {
        list.add(priceEntity);
    }
}
