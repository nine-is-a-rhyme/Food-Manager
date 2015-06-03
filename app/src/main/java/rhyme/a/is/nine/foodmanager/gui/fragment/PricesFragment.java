package rhyme.a.is.nine.foodmanager.gui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import rhyme.a.is.nine.foodmanager.R;
import rhyme.a.is.nine.foodmanager.gui.activity.MainActivity;

public class PricesFragment extends Fragment {

    private BarChart monthBarChart;
    private BarChart weekBarChart;
    private TextView weekBarChartTitle;

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
        monthBarChart = (BarChart) view.findViewById(R.id.price_graph_month);
        weekBarChart = (BarChart) view.findViewById(R.id.price_graph_week);
        weekBarChartTitle = (TextView) view.findViewById(R.id.week_bar_chart_title);

        monthBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                monthBarChart.setHighlightIndicatorEnabled(false);

                generateWeekChart(e.getXIndex());
            }

            @Override
            public void onNothingSelected() {

            }
        });

        monthBarChart.setDrawBarShadow(false);
        monthBarChart.setDrawValueAboveBar(true);
        monthBarChart.setDescription("");
        monthBarChart.setDrawGridBackground(false);
        monthBarChart.getAxisLeft().setStartAtZero(true);
        monthBarChart.getAxisLeft().setDrawGridLines(false);
        monthBarChart.getAxisLeft().setEnabled(false);
        monthBarChart.getAxisRight().setStartAtZero(true);
        monthBarChart.getAxisRight().setDrawGridLines(false);
        monthBarChart.getAxisRight().setEnabled(false);
        monthBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        monthBarChart.getXAxis().setDrawGridLines(false);
        monthBarChart.getLegend().setEnabled(false);
        monthBarChart.setPinchZoom(false);
        monthBarChart.setDoubleTapToZoomEnabled(false);

        monthBarChart.setData(MainActivity.priceDatabase.getMonthBars());

        monthBarChart.setVisibleXRange(5);
        //monthBarChart.moveViewToX(monthBarChart.getBarData().getXValCount() - 5);
        monthBarChart.invalidate();

        if(monthBarChart.getBarData() != null)
            generateWeekChart(monthBarChart.getBarData().getXValCount() - 1);

        return view;
    }

    private void generateWeekChart(int month) {

        weekBarChartTitle.setText("Wochenübersicht für " + monthBarChart.getBarData().getXVals().get(month));

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
        weekBarChart.setPinchZoom(false);
        weekBarChart.setDoubleTapToZoomEnabled(false);
        weekBarChart.setHighlightEnabled(false);

        weekBarChart.setData(MainActivity.priceDatabase.getWeekBarsForMonth(month));

        weekBarChart.setVisibleXRange(6);
        weekBarChart.invalidate();
    }
}