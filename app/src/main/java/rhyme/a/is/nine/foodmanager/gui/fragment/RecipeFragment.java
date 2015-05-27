package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.AddRecipeActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.ProductActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements View.OnClickListener {

    private AddFloatingActionButton fabAdd;


    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_recipe, container, false);

        Button button = (Button) fragmentView.findViewById(R.id.button_web);

        button.setOnClickListener(((MainActivity)getActivity()).mGlobal_OnClickListener);

        fabAdd = (AddFloatingActionButton) fragmentView.findViewById(R.id.floating_action_button_add_1);
        fabAdd.setOnClickListener(this);
        fabAdd.setTag("ADD");
        fabAdd.setStrokeVisible(true);

        return fragmentView;
    }


    @Override
    public void onClick(View v) {
        switch ((String) v.getTag()) {
            case "ADD":
                Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
                getActivity().startActivity(intent);
                break;
    }
}}
