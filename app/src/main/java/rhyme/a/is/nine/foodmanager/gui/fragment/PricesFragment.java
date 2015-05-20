package rhyme.a.is.nine.foodmanager.gui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.WeekOverviewActivity;
import rhyme.a.is.nine.foodmanager.gui.MainActivity;
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

   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

}
