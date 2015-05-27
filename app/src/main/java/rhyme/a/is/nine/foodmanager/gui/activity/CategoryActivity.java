package rhyme.a.is.nine.foodmanager.gui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.product.Category;
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
            final Spinner sp = (Spinner) findViewById(R.id.et_vorschläge);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Arrays.asList(suggestedCategories));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adapter);

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    EditText name = (EditText) findViewById(R.id.et_name);
                    name.setText((String) sp.getSelectedItem());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

        Button add_button = (Button) findViewById(R.id.button_save);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc_name = ((EditText) findViewById(R.id.et_name)).getText().toString();
                String string_date = ((EditText) findViewById(R.id.et_bestbefore)).getText().toString();
                if (string_date.length() == 0)
                    string_date = "0";
                int bbf_date = Integer.parseInt(string_date);
                if (desc_name.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Kein Kategoriename gewählt", Toast.LENGTH_LONG).show();
                    return;
                }
                ArrayAdapter<Category> adapter = (ArrayAdapter) ProductActivity.category.getAdapter();
                if (isInList(desc_name))
                {
                    Toast.makeText(getApplicationContext(), "Kategorie schon vorhanden", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
                Category cat = new Category(desc_name, bbf_date);
                adapter.add(cat);
                //ProductActivity.cat_db.addCategory(cat);
                ProductActivity.category.setSelection(adapter.getCount());
                finish();
            }});
    }

    public boolean isInList(String category)
    {
        ArrayAdapter<Category> adapter = (ArrayAdapter) ProductActivity.category.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++)
        {
            if (adapter.getItem(i).toString().equals(category)) {
                return true;
            }
        }
        return false;
    }
}