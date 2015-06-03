package rhyme.a.is.nine.foodmanager.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.WeekOverviewActivity;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;
import rhyme.a.is.nine.foodmanager.gui.graph.BarGraph;
import rhyme.a.is.nine.foodmanager.gui.graph.Bar;


public class PricesFragment extends Fragment {

    public PricesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prices, container, false);

        ArrayList<Bar> bars = MainActivity.priceDatabase.getBars();

        if(bars != null) {
            BarGraph g = (BarGraph)view.findViewById(R.id.price_graph);
            g.setBars(MainActivity.priceDatabase.getBars());

            g.setOnBarClickedListener(new BarGraph.OnBarClickedListener() {
                @Override
                public void onClick(int index) {
                    Intent intent = new Intent(getActivity(), WeekOverviewActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("WEEK_ID", 3 - index);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }
        TextView sum = (TextView) view.findViewById(R.id.price_value);
        sum.setText(String.format("%.02f", MainActivity.priceDatabase.getLastMonthValue()) + "€");

        return view;
    }
}
