package rhyme.a.is.nine.foodmanager.gui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.database.DatabaseAccess;
import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter;
import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.product.ProductPlace;

public class ProductActivity extends ActionBarActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product = new Product();

        Button button = (Button) findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.et_name);
                product.setName(name.getText().toString());
                EditText category = (EditText) findViewById(R.id.et_category);
                product.setCategory(category.getText().toString());
                EditText size = (EditText) findViewById(R.id.et_size);
                product.setSize(size.getText().toString());
                EditText count = (EditText) findViewById(R.id.et_count);
                try
                {
                    product.setCount(Integer.parseInt(count.getText().toString()));
                }
                catch(Exception e)
                {
                    product.setCount(1);
                }

                EditText bestbefore = (EditText) findViewById(R.id.et_bestbefore);
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    Date date = sdf.parse(bestbefore.getText().toString());
                    product.setBestBeforeDate(date);
                }
                catch (Exception ex){
                    System.out.println(ex);
                }

                product.setProductPlace(ProductPlace.FRIDGE);
                if(product.getBarcode() == null)
                {
                    product.setManual(true);
                }
                DatabaseAccess.addProduct(product);
                finish();
            }
        });
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
        switch (id) {
            case R.id.action_barcode:
                new IntentIntegrator(this).initiateScan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addProduct(Product product)
    {
        EditText name = (EditText) findViewById(R.id.et_name);
        name.setText(product.getName());
        EditText category = (EditText) findViewById(R.id.et_category);
        category.setText(product.getCategory());
        EditText size = (EditText) findViewById(R.id.et_size);
        size.setText(product.getSize());
        EditText count = (EditText) findViewById(R.id.et_count);
        count.setText(product.getCount());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        Toast.makeText(getApplicationContext(), "Resultcode: " + requestCode, Toast.LENGTH_LONG).show();
        if(result != null)
        {
            if(result.getContents() == null)
                return;

            product = BarcodeToProductConverter.getProductForBarcode(result.getContents());
            if(product == null)
                return;

            addProduct(product);

            Toast.makeText(getApplicationContext(), "Product found", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "no product found", Toast.LENGTH_LONG).show();
        }
    }


}
