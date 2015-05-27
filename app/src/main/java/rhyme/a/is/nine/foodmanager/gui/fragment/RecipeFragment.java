package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.MainActivity;
import rhyme.a.is.nine.foodmanager.gui.adapter.FridgeAdapter;
import rhyme.a.is.nine.foodmanager.gui.adapter.RecipeAdapter;
import rhyme.a.is.nine.foodmanager.product.Product;


public class RecipeFragment extends ListFragment {


    private RecipeAdapter recipeAdapter;

    public RecipeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_recipe, container, false);

        //Button button = (Button) fragmentView.findViewById(R.id.button_web);
        //button.setOnClickListener(((MainActivity)getActivity()).mGlobal_OnClickListener);

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        recipeAdapter = new RecipeAdapter(getActivity().getBaseContext());
        setListAdapter(recipeAdapter);
    }


}
