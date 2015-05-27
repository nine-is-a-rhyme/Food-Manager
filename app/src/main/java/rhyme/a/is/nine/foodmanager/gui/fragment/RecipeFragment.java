package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.app.Activity;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.MainActivity;
import rhyme.a.is.nine.foodmanager.gui.ProductActivity;
import rhyme.a.is.nine.foodmanager.gui.RecipeActivity;
import rhyme.a.is.nine.foodmanager.gui.adapter.FridgeAdapter;
import rhyme.a.is.nine.foodmanager.gui.adapter.RecipeAdapter;
import rhyme.a.is.nine.foodmanager.product.Product;
import rhyme.a.is.nine.foodmanager.recipe.Recipe;

import static android.view.View.OnClickListener;


public class RecipeFragment extends ListFragment implements OnClickListener {


    private RecipeAdapter recipeAdapter;

    public RecipeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_recipe, container, false);

        return fragmentView;
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), RecipeActivity.class);
        Recipe rec = (Recipe) recipeAdapter.getItem(position);
        intent.putExtra("recipe", rec);
        getActivity().startActivityForResult(intent, 0);
    }

    @Override
    public void onStart() {
        super.onStart();

        recipeAdapter = new RecipeAdapter(getActivity().getBaseContext());
        setListAdapter(recipeAdapter);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        getActivity().startActivityForResult(intent, 0);
    }
}
