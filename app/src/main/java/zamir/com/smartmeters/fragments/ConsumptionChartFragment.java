package zamir.com.smartmeters.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import zamir.com.smartmeters.R;

/**
 * Created by MahmoudSamir on 2/15/2017.
 */

public class ConsumptionChartFragment extends Fragment {
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_consumption_chart, container, false);
        BarChart chart = (BarChart) view.findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Consumption");
        chart.animateXY(2000, 2000);
        chart.invalidate();
        return view;
    }


    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e5 = new BarEntry(20.000f, 0); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 1); // Jun
        valueSet2.add(v2e6);
        BarEntry v2e7 = new BarEntry(90.000f, 2); // July
        valueSet2.add(v2e7);

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("MAY");
        xAxis.add("JUN");
        xAxis.add("July");
        return xAxis;
    }
}
