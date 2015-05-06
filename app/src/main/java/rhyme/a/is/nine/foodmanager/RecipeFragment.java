package rhyme.a.is.nine.foodmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import static rhyme.a.is.nine.foodmanager.R.layout.fragment_recipe;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements OnClickListener, OnKeyListener {

    private static final String RECIPE_URL = "http://www.chefkoch.de/rs/s0e1z1/*/Rezepte.html";

    private List<String> recipe_search_entries_;
    private URL url;


    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myFragmentView = inflater.inflate(fragment_recipe, container, false);

        EditText ingredients = (EditText) myFragmentView.findViewById(R.id.ingredients);
        Button goButton = (Button) myFragmentView.findViewById(R.id.create);

        // Setup event handlers
        goButton.setOnClickListener(this);
        ingredients.setOnKeyListener(this);

        return myFragmentView;
    }

    public String createUrlString(String[] ingredients) {
        /* creates something like http://www.chefkoch.de/rs/s0e1z1/karotte+kartoffel/Rezepte.html */

        StringBuilder sb = new StringBuilder();
        for (String i : ingredients) {

        }

        if (!recipe_search_entries_.isEmpty()) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("http://www.chefkoch.de/rs/s0e1z1/");

            for (Iterator<String> entry = recipe_search_entries_.iterator(); entry.hasNext(); ) {
                stringBuilder.append(entry);
                if (entry.hasNext()) {
                    stringBuilder.append("+");
                }
            }

            stringBuilder.append("/Rezepte.html");

            try {
                url = new URL(stringBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        } else {
            url = null;
        }


        return null;

    }

    public void showWebsite() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        RecipeWebView fragment;

        fragment = new RecipeWebView();
        fragment.setUrlString("http://www.chefkoch.de/rs/s0e1z1/karotte+kartoffel/Rezepte.html");
        fragmentTransaction.replace(R.id.webview, fragment, "RECIPE_FRAGMENT");
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View view) {
        // TODO: parse input into recipe_search_entries_ list
        showWebsite();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER) {
            // TODO: parse input into recipe_search_entries_ list
            showWebsite();
            return true;
        }
        return false;
    }
}
