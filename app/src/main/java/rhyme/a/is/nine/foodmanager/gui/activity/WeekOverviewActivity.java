package rhyme.a.is.nine.foodmanager.gui.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.product.PriceEntity;


public class WeekOverviewActivity extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_overview);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            int week_index = b.getInt("WEEK_ID");

            List<PriceEntity> priceEntities = MainActivity.priceDatabase.getPriceEntitiesForWeek(week_index);

            ArrayList<HashMap<String, String>> list = new ArrayList<>();
            HashMap<String, String> item;
            for(PriceEntity i : priceEntities) {
                item = new HashMap<String,String>();
                item.put( "line1", String.format("%.02f", i.getPrice()) + "€");
                item.put("line2", i.getName() + ", " + new SimpleDateFormat("dd.MM.yyyy").format(i.getBuyDate()).toString());
                list.add(item);
            }

            ListAdapter adapter = new SimpleAdapter(this, list, android.R.layout.two_line_list_item , new String[] { "line1","line2" }, new int[] {android.R.id.text1, android.R.id.text2});
            setListAdapter(adapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_week_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
