package rhyme.a.is.nine.foodmanager.gui;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.database.CategoryDatabase;
import rhyme.a.is.nine.foodmanager.gui.fragment.DatePickerDialogFragment;
import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter;
import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter.ScannedProduct;
import rhyme.a.is.nine.foodmanager.product.Category;
import rhyme.a.is.nine.foodmanager.product.Product;

public class ProductActivity extends ActionBarActivity implements View.OnClickListener {

    private Product product;

    private TextView bestBeforeView;
    public static Spinner category;
    public static String[] suggestedCategories;
    public static CategoryDatabase cat_db;
    private String startedBy = "Fridge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        Intent myIntent = getIntent(); // gets the previously created intent
        startedBy = myIntent.getStringExtra("startedBy");
        if(startedBy == null) {
            startedBy = "Fridge";
        }

        product = new Product();

        Button button = (Button) findViewById(R.id.button_save);
        category = (Spinner) findViewById(R.id.et_category);
        cat_db = new CategoryDatabase("category.db");
        cat_db.readFromFile(getBaseContext());
        List<Category> cat_list = cat_db.getAllCategories();

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, cat_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        Button add_button = (Button) findViewById(R.id.button_add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, CategoryActivity.class);
                ProductActivity.this.startActivityForResult(intent, 0);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean fail = false;
                EditText name = (EditText) findViewById(R.id.et_name);
                if (name.getText().toString().length() > 0) {
                    product.setName(name.getText().toString());
                    name.setBackgroundColor(Color.GREEN);
                } else {
                    fail = true;
                    name.setBackgroundColor(Color.RED);
                }

                if(category.getSelectedItem().toString().length() > 0)
                {
                    product.setCategory(category.getSelectedItem().toString());
                    category.setBackgroundColor(Color.GREEN);
                } else {
                    fail = true;
                    category.setBackgroundColor(Color.RED);
                }

                /*EditText size = (EditText) findViewById(R.id.et_size);
                if(size.getText().toString().length() > 0)
                {
                    product.setSize(size.getText().toString());
                    size.setBackgroundColor(Color.GREEN);
                } else {
                    fail = true;
                    size.setBackgroundColor(Color.RED);
                }*/
                EditText count = (EditText) findViewById(R.id.et_count);
                try {
                    product.setCount(Integer.parseInt(count.getText().toString()));
                    count.setBackgroundColor(Color.GREEN);
                } catch (Exception e) {
                    product.setCount(1);
                    count.setText("1");
                }

                if(!startedBy.equals("List")) {
                    TextView bestbefore = (TextView) findViewById(R.id.et_bestbefore);
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        Date date = sdf.parse(bestbefore.getText().toString());
                        product.setBestBeforeDate(date);
                        bestbefore.setBackgroundColor(Color.GREEN);
                    } catch (Exception ex) {
                        fail = true;
                        bestbefore.setBackgroundColor(Color.RED);
                    }
                }

                if (!fail) {
                    if(startedBy.equals("List")) {
                        MainActivity.shoppingListDatabase.addProduct(product);
                    } else
                    {
                        MainActivity.fridgeDatabase.addProduct(product);
                    }
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Bitte überprüfe deine Eingaben.", Toast.LENGTH_LONG).show();
                }
            }
        });

        bestBeforeView = (TextView) findViewById(R.id.et_bestbefore);
        bestBeforeView.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        bestBeforeView.setOnClickListener(this);

        if(startedBy.equals("List")) {
            bestBeforeView.setVisibility(View.GONE);
            TextView tw_bestbefore = (TextView) findViewById(R.id.textView3);
            tw_bestbefore.setVisibility(View.GONE);
            TextView tw_category = (TextView) findViewById(R.id.textView4);
            tw_category.setVisibility(View.GONE);
            LinearLayout et_dropdown = (LinearLayout) findViewById(R.id.et_dropdown);
            et_dropdown.setVisibility(View.GONE);
        }

        if (getIntent().getBooleanExtra("SCAN", false))
            new IntentIntegrator(this).initiateScan();
    }

    @Override
    public void onStop()
    {
        //ArrayAdapter<Category> adapter = (ArrayAdapter) category.getAdapter();
        //cat_db.setList(adapter);
        cat_db.writeToFile(getBaseContext());
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);

    }

    private void addProduct(Product product) {
        EditText name = (EditText) findViewById(R.id.et_name);
        name.setText(product.getName());
        category.setSelection(/*product.getCategory()*/ 0);
        /*EditText size = (EditText) findViewById(R.id.et_size);
        size.setText(product.getSize());*/
        EditText count = (EditText) findViewById(R.id.et_count);
        count.setText(String.valueOf(product.getCount()));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            if (result.getContents() == null)
                return;

            product = BarcodeToProductConverter.getProductForBarcode(result.getContents());
            if (product == null)
                return;

            suggestedCategories = null;
            if(product instanceof ScannedProduct)
                suggestedCategories = ((ScannedProduct) product).getCategories();

            addProduct(product);

            Toast.makeText(getApplicationContext(), "Produkt gefunden", Toast.LENGTH_LONG).show();
        }
        else
        {

            Toast.makeText(getApplicationContext(), "Kein Produkt gefunden", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View view) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment newFragment = new DatePickerDialogFragment();
        ((DatePickerDialogFragment)newFragment).setBestBeforeDateTextView(bestBeforeView);
        ((DatePickerDialogFragment)newFragment).setActivity(this);
        newFragment.show(ft, "date_dialog");
    }
}
