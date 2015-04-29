package rhyme.a.is.nine.foodmanager;


import android.content.DialogInterface;
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
import android.widget.TextView;
import java.net.*;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import static rhyme.a.is.nine.foodmanager.R.layout.fragment_recipe;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {

    private List<String> recipe_search_entries_;
    private URL url;
    private View myFragmentView;


    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(fragment_recipe, container, false);

        EditText ingredients = (EditText) myFragmentView.findViewById(R.id.ingredients);
        Button goButton = (Button) myFragmentView.findViewById(R.id.create);

        // Setup event handlers
        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    // TODO: parse input into recipe_search_entries_ list
                    showWebsite();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        ingredients.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    try {
                        // TODO: parse input into recipe_search_entries_ list
                        showWebsite();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        return inflater.inflate(fragment_recipe, container, false);
    }

    public URL createURL() throws MalformedURLException {
        /* creates something like http://www.chefkoch.de/rs/s0e1z1/karotte+kartoffel/Rezepte.html */

        if(!recipe_search_entries_.isEmpty()) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("http://www.chefkoch.de/rs/s0e1z1/");

            for(Iterator<String> entry = recipe_search_entries_.iterator(); entry.hasNext(); ) {
                stringBuilder.append(entry);
                if(entry.hasNext()) {
                    stringBuilder.append("+");
                }
            }

            stringBuilder.append("/Rezepte.html");

            try {
                url = new URL(stringBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        else {
            url = null;
        }


        return url;

    }

    public void showWebsite() throws MalformedURLException {

        try {
            this.createURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(this.url == null){
            return;
        }

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecipeWebView fragment;
        //fragment = new RecipeWebView(this.url); // TDO: this should change some day
        fragment = new RecipeWebView(new URL("http://www.chefkoch.de/rs/s0e1z1/karotte+kartoffel/Rezepte.html"));
        fragmentTransaction.replace(R.id.webview, fragment, "RECIPE_FRAGMENT");
        fragmentTransaction.commit();


    }


}
