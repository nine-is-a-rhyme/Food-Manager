package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.AddRecipeActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.RecipeActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.ProductActivity;
import rhyme.a.is.nine.foodmanager.gui.adapter.RecipeAdapter;
import rhyme.a.is.nine.foodmanager.recipe.Recipe;

import static android.view.View.OnClickListener;

/**
 * A simple {@link Fragment} subclass.
 */

public class RecipeFragment extends ListFragment implements View.OnClickListener {

    private AddFloatingActionButton fabAdd;

    private RecipeAdapter recipeAdapter;

    public RecipeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_recipe, container, false);


        fabAdd = (AddFloatingActionButton) fragmentView.findViewById(R.id.floating_action_button_add_1);
        fabAdd.setOnClickListener(this);
        fabAdd.setTag("ADD");
        fabAdd.setStrokeVisible(true);

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
        switch ((String) v.getTag()){
            case "ADD":
        Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
        //intent.putExtra("started by", "Recipe");
        getActivity().startActivity(intent);
        break;
    }
}

}
