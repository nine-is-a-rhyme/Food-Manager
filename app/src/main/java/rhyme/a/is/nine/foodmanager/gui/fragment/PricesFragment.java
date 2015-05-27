package rhyme.a.is.nine.foodmanager.gui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;

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
        final View view = inflater.inflate(R.layout.fragment_prices, container, false);
        BarChart barChart = (BarChart) view.findViewById(R.id.price_graph_month);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                BarChart weekBarChart = (BarChart) view.findViewById(R.id.price_graph_week);

                weekBarChart.setDrawBarShadow(false);
                weekBarChart.setDrawValueAboveBar(true);
                weekBarChart.setDescription("");
                weekBarChart.setDrawGridBackground(false);
                weekBarChart.getAxisLeft().setStartAtZero(true);
                weekBarChart.getAxisLeft().setDrawGridLines(false);
                weekBarChart.getAxisLeft().setEnabled(false);
                weekBarChart.getAxisRight().setStartAtZero(true);
                weekBarChart.getAxisRight().setDrawGridLines(false);
                weekBarChart.getAxisRight().setEnabled(false);
                weekBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                weekBarChart.getXAxis().setDrawGridLines(false);
                weekBarChart.getLegend().setEnabled(false);

                weekBarChart.setData(MainActivity.priceDatabase.getWeekBarsForMonth(e.getXIndex()));

                weekBarChart.setVisibleXRange(5);
                weekBarChart.moveViewToX(weekBarChart.getBarData().getXValCount());
                weekBarChart.invalidate();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("");
        barChart.setDrawGridBackground(false);
        barChart.getAxisLeft().setStartAtZero(true);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setStartAtZero(true);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getLegend().setEnabled(false);

        barChart.setData(MainActivity.priceDatabase.getMonthBars());

        barChart.setVisibleXRange(5);
        barChart.moveViewToX(barChart.getBarData().getXValCount());
        barChart.invalidate();

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
