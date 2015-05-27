package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.util.FakeData;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        Button btn_reset = (Button) view.findViewById(R.id.btn_reset);
        Button btn_generateData = (Button) view.findViewById(R.id.btn_generate_data);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.fridgeDatabase.deleteAll();
                MainActivity.shoppingListDatabase.deleteAll();
                MainActivity.priceDatabase.deleteAll();
            }
        });

        btn_generateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FakeData.generateFakeData();
            }
        });

        return view;
    }


}
