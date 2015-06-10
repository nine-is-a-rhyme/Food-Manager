package rhyme.a.is.nine.foodmanager.gui.activity;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.database.CategoryDatabase;
import rhyme.a.is.nine.foodmanager.gui.fragment.DatePickerDialogFragment;
import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter;
import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter.ScannedProduct;
import rhyme.a.is.nine.foodmanager.product.Category;
import rhyme.a.is.nine.foodmanager.product.PriceEntity;
import rhyme.a.is.nine.foodmanager.product.Product;

public class ProductActivity extends ActionBarActivity implements View.OnClickListener {

    private Product product;
    private PriceEntity pos;
    public static Product editProduct = null;

    private TextView bestBeforeView;
    public static Spinner category;
    public static String[] suggestedCategories;
    public static CategoryDatabase cat_db;
    private String startedBy = "Fridge";
    private List<Category> cat_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        Intent myIntent = getIntent(); // gets the previously created intent
        startedBy = myIntent.getStringExtra("startedBy");
        if(startedBy == null) {
            startedBy = "Fridge";
        }

        pos = new PriceEntity();


        Button button = (Button) findViewById(R.id.button_save);
        category = (Spinner) findViewById(R.id.et_category);
        cat_db = new CategoryDatabase("category.db");
        cat_db.readFromFile(getBaseContext());
        cat_list = cat_db.getAllCategories();

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, cat_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        Button add_button = (Button) findViewById(R.id.button_add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, CategoryActivity.class);
                ProductActivity.this.startActivity(intent);
            }
        });

        bestBeforeView = (TextView) findViewById(R.id.et_bestbefore);
        bestBeforeView.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        bestBeforeView.setOnClickListener(this);

        if(editProduct == null)
            product = new Product();
        else {
            product = editProduct;
            addProduct(editProduct);
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean fail = false;
                EditText name = (EditText) findViewById(R.id.et_name);
                if (name.getText().toString().length() > 0) {
                    product.setName(name.getText().toString());
                    if(!startedBy.equals("List")) {
                        pos.setName(name.getText().toString());
                    }

                } else {
                    fail = true;
                    //name.setBackgroundColor(Color.RED);
                    name.setError("Bitte Bezeichnung eingeben!");
                }

                if(category.getSelectedItem().toString().length() > 0)
                {
                    product.setCategory(category.getSelectedItem().toString());

                } else {
                    fail = true;
                    //category.setBackgroundColor(Color.RED);
                    TextView category_view = (TextView) category.getSelectedView();
                    category_view.setError("Bitte Kategorie eingeben!");
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

                } catch (Exception e) {
                    product.setCount(1);
                    count.setText("1");
                }

                if(!startedBy.equals("List")) {
                EditText price = (EditText) findViewById(R.id.et_price);
                try {
                    pos.setPrice(Float.parseFloat(price.getText().toString()) * Float.parseFloat(count.getText().toString()));

                } catch (Exception e) {
                    pos.setPrice(0);
                    price.setText("0.00");
                }

                    TextView bestbefore = (TextView) findViewById(R.id.et_bestbefore);
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        Date date = sdf.parse(bestbefore.getText().toString());
                        product.setBestBeforeDate(date);

                    } catch (Exception ex) {
                        fail = true;
                    bestbefore.setError("Bitte Ablaufdatum eingeben!");
                    }
                }

                if (!fail) {
                    if(startedBy.equals("List")) {
                        MainActivity.shoppingListDatabase.addProduct(product);
                    } else
                    {
                        MainActivity.fridgeDatabase.addProduct(product);
                        MainActivity.recipeDatabase.getRecipes();
                        pos.setBuyDate(new Date());
                        MainActivity.priceDatabase.addPriceEntity(pos);
                    }

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Bitte überprüfe deine Eingaben.", Toast.LENGTH_LONG).show();
                }
            }
        });

        if(startedBy.equals("List")) {
            bestBeforeView.setVisibility(View.GONE);
            TextView tw_bestbefore = (TextView) findViewById(R.id.textView3);
            tw_bestbefore.setVisibility(View.GONE);
            TextView tw_category = (TextView) findViewById(R.id.textView4);
            tw_category.setVisibility(View.GONE);
            LinearLayout et_dropdown = (LinearLayout) findViewById(R.id.et_dropdown);
            et_dropdown.setVisibility(View.GONE);
            TextView tw_price = (TextView) findViewById(R.id.textView5);
            tw_price.setVisibility(View.GONE);
            LinearLayout ll_price = (LinearLayout) findViewById(R.id.ll_price);
            ll_price.setVisibility(View.GONE);
        }

        if(ProductActivity.editProduct != null)
        {
            TextView tw_price = (TextView) findViewById(R.id.textView5);
            tw_price.setVisibility(View.GONE);
            LinearLayout ll_price = (LinearLayout) findViewById(R.id.ll_price);
            ll_price.setVisibility(View.GONE);
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
        category.setSelection(0);
        EditText count = (EditText) findViewById(R.id.et_count);
        count.setText(String.valueOf(product.getCount()));
        if(editProduct != null) {
            for(int i = 0; i<cat_list.size();++i)
                if(cat_list.get(i).getName().equals(product.getCategory())) {
                    category.setSelection(i);
                    break;
                }
            TextView best_before = (TextView) findViewById(R.id.et_bestbefore);
            best_before.setText(new SimpleDateFormat("dd.MM.yyyy").format(product.getBestBeforeDate()));
        }


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
