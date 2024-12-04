package edu.sjsu.android.jams;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import edu.sjsu.android.jams.Utils.DatabaseHandler;

public class StatsFragment extends Fragment {
    private ImageView backButton;
    private BarChart barChart;
    private TextView hoursFocusedTodayValue, hoursFocusedValue, totalPomodoroSessionsValue;
    private DatabaseHandler databaseHandler;
    private int userID;

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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("user_id", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        backButton = view.findViewById(R.id.back_arrow_in_pomodoro_stats);
        backButton.setOnClickListener(this::navigateToPomodoro);

        databaseHandler = new DatabaseHandler(this.getContext());
        databaseHandler.openDatabase();

        barChart = view.findViewById(R.id.bar_chart);
        loadBarChartData();

        /*
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
         */

        hoursFocusedTodayValue = view.findViewById(R.id.hoursFocusedTodayValue);
        hoursFocusedValue = view.findViewById(R.id.hoursFocusedValue);
        totalPomodoroSessionsValue = view.findViewById(R.id.totalPomodoroSessionsValue);

        loadStats();

        return view;
    }

    public void loadStats(){
        String currentDate = getCurrentDate();
        double hoursToday = databaseHandler.getTodayHoursFocused(userID, currentDate);
        double totalHours = databaseHandler.getTotalHoursFocused(userID);
        int totalSessions = databaseHandler.getTotalPomodoroSessions(userID);

        hoursFocusedTodayValue.setText(String.format("%.3f", hoursToday));
        hoursFocusedValue.setText(String.format("%.3f", totalHours));
        totalPomodoroSessionsValue.setText(String.valueOf(totalSessions));
    }

    public void loadBarChartData(){
        Cursor cursor = databaseHandler.getDailyFocusHours(userID);
        ArrayList<BarEntry> entries = new ArrayList<>();

        while (cursor.moveToNext()) {
            int dayOfWeek = cursor.getInt(cursor.getColumnIndexOrThrow("day_of_week"));
            float totalHours = cursor.getFloat(cursor.getColumnIndexOrThrow("total_hours"));
            entries.add(new BarEntry(dayOfWeek, totalHours));
        }
        cursor.close();

        BarDataSet barDataSet = new BarDataSet(entries, "Daily Focus Hours");
        barDataSet.setColor(Color.parseColor("#99BCC0"));
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);

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
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void navigateToPomodoro(View view) {
        Log.d("test", "clicked back button in stats fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_statsFragment_to_pomodoroFragment);
    }
}