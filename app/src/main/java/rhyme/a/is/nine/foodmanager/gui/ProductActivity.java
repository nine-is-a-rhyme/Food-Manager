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

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.database.DatabaseAccess;
import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter;
import rhyme.a.is.nine.foodmanager.product.Product;

public class ProductActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Button button = (Button) findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

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
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        Toast.makeText(getApplicationContext(), "Resultcode: " + requestCode, Toast.LENGTH_LONG).show();
        if(result != null)
        {
            if(result.getContents() == null)
                return;

            Product product = BarcodeToProductConverter.getProductForBarcode(result.getContents());
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
