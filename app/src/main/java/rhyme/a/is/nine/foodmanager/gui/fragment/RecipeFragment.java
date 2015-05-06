package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.product.Product;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements View.OnClickListener {

    private List<String> recipe_search_entries_;
    private String url;


    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_recipe, container, false);

        Button button = (Button) fragmentView.findViewById(R.id.button_web);
        button.setOnClickListener(this);

        return fragmentView;
    }




    @Override
    public void onClick(View v) {
        Toast.makeText(this.getActivity(),
                "Button is clicked!", Toast.LENGTH_LONG).show();


        switch (v.getId()) {
            case R.id.button_web:


                break;
        }

    }
    public String createURL(){
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

                               url = stringBuilder.toString();

                           }
              else {
                       url = null;
                   }

       return url;
   }


}
