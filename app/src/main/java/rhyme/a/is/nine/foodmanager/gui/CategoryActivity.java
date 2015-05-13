package rhyme.a.is.nine.foodmanager.gui;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.fragment.DatePickerDialogFragment;
import rhyme.a.is.nine.foodmanager.product.BarcodeToProductConverter;
import rhyme.a.is.nine.foodmanager.product.Product;

public class CategoryActivity extends ActionBarActivity  {

    private Product product;

    private TextView bestBeforeView;
    private Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String[] suggestedCategories = ProductActivity.suggestedCategories;
        if(suggestedCategories != null) {
            EditText name = (EditText) findViewById(R.id.et_vorschläge);

            String suggestedText="";
            String prefix = "";
            for(String suggested : suggestedCategories) {
                suggestedText += prefix + suggested;
                prefix = ", ";
            }
            name.setHint(suggestedText);

        }


        Button add_button = (Button) findViewById(R.id.button_save);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter<String> adapter = (ArrayAdapter) ProductActivity.category.getAdapter();
                adapter.add(((EditText) findViewById(R.id.et_name)).getText().toString());
                ProductActivity.category.setSelection(adapter.getCount());
                finish();
            }});


    }}

