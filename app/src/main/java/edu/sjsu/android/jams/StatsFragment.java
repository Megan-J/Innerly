package edu.sjsu.android.jams;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class StatsFragment extends Fragment {
    private ImageView backButton;
    private BarChart barChart;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        backButton = view.findViewById(R.id.back_arrow_in_pomodoro_stats);
        backButton.setOnClickListener(this::navigateToPomodoro);

        barChart = view.findViewById(R.id.bar_chart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 3f));
        entries.add(new BarEntry(1, 1f));
        entries.add(new BarEntry(2, 2f));
        entries.add(new BarEntry(3, 1.5f));
        entries.add(new BarEntry(4, 2.5f));
        entries.add(new BarEntry(5, 0.5f));
        entries.add(new BarEntry(6, 1f));

        BarDataSet barDataSet = new BarDataSet(entries, "Pomodoro Sessions");
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);

        barChart.setData(barData);
        barChart.setFitBars(true);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setLabelCount(entries.size());
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final String[] days = {"M", "T", "W", "T", "F", "S", "S"};
            @Override
            public String getFormattedValue(float value) {
                return days[(int) value];
            }
        });

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setGranularity(1f);
        yAxis.setTextSize(12f);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawGridLines(false);
        yAxis.setGridColor(Color.parseColor("#273de3"));

        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.invalidate();

        return view;
    }

    private void navigateToPomodoro(View view) {
        Log.d("test", "clicked back button in stats fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_statsFragment_to_pomodoroFragment);
    }
}