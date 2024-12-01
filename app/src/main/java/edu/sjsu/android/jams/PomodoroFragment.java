package edu.sjsu.android.jams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.sjsu.android.jams.Utils.DatabaseHandler;

public class PomodoroFragment extends Fragment {
    private TextView timeText, sessionLengthText, breakLengthText, sessionStatus;
    private Button startButton, resetButton, sessionIncrement, sessionDecrement, breakIncrement, breakDecrement, myStats;
    private ImageView backArrow;

    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;
    private boolean workSession = true;

    private long workDuration = 1 * 60 * 1000;
    private long breakDuration = 2 * 60 * 1000;
    private long timeRemaining = workDuration;

    private DatabaseHandler databaseHandler;

    private int userID;
    private double totalHoursFocused = 0;
    private int totalSessions = 0;
    private double hoursFocusedToday = 0;

    public PomodoroFragment() {
        // Required empty public constructor
    }

    public static PomodoroFragment newInstance(String param1, String param2) {
        PomodoroFragment fragment = new PomodoroFragment();
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
        View view = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        timeText = view.findViewById(R.id.timeText);
        sessionLengthText = view.findViewById(R.id.sessionLengthText);
        breakLengthText = view.findViewById(R.id.breakLengthText);
        sessionStatus = view.findViewById(R.id.session_status);
        startButton = view.findViewById(R.id.startButton);
        resetButton = view.findViewById(R.id.resetButton);
        sessionIncrement = view.findViewById(R.id.sessionIncrement);
        sessionDecrement = view.findViewById(R.id.sessionDecrement);
        breakIncrement = view.findViewById(R.id.breakIncrement);
        breakDecrement = view.findViewById(R.id.breakDecrement);
        myStats = view.findViewById(R.id.myStatsButton);
        backArrow = view.findViewById(R.id.back_arrow_in_pomodoro_session);

        sessionLengthText.setText(String.valueOf(workDuration / 60 / 1000));
        breakLengthText.setText(String.valueOf(breakDuration / 60 / 1000));

        startButton.setOnClickListener(v -> startOrPauseTimer());
        resetButton.setOnClickListener(v -> resetTimer());
        sessionIncrement.setOnClickListener(v -> adjustSessionTime(1));
        sessionDecrement.setOnClickListener(v -> adjustSessionTime(-1));
        breakIncrement.setOnClickListener(v -> adjustBreakTime(1));
        breakDecrement.setOnClickListener(v -> adjustBreakTime(-1));

        myStats.setOnClickListener(this::navigateToStats);
        backArrow.setOnClickListener(this::backToHome);

        return view;
    }

    private void backToHome(View view) {
        Log.d("test", "clicked backButton in pomodoro fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_pomodoroFragment_to_homepageFragment);
    }

    private void navigateToStats(View view) {
        Log.d("test", "clicked myStatsButton in pomodoro fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_pomodoroFragment_to_statsFragment);
    }

    private void startOrPauseTimer() {
        if(timerRunning){
            pauseTimer();
        }
        else{
            startTimer();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = (int) millisUntilFinished;
//                startButton.setText("Pause");
                updateTimerText();
            }

            @Override
            public void onFinish() {
                if(workSession){
                    timeRemaining = breakDuration;
                    workSession = false;
                    sessionStatus.setText("Break Start!");
                    Log.d("test", "trying to update pomodoro stats now");
                    updatePomodoroStats();
                }
                else{
                    timeRemaining = workDuration;
                    workSession = true;
                    sessionStatus.setText("Session Start!");
                }
//                startTimer();
                updateTimerText();
            }
        }.start();
        timerRunning = true;
//        startButton.setText("Pause");
    }

    public void updatePomodoroStats(){
        Log.d("test", "in updatePomodoroStats method");
        if (userID == -1) {
            Log.e("test", "User ID is -1");
            return;
        }
        else{
            Log.d("test", "User ID is " + userID);
        }

        Log.d("test", "Trying to initialize DatabaseHandler");
        databaseHandler = new DatabaseHandler(this.getContext());
        databaseHandler.openDatabase();
        Log.d("test", "DatabaseHandler initialized successfully");
        String currentDate = getCurrentDate();
        Log.d("test", "currentDate: " + currentDate);
        double sessionHours = workDuration / (1000.0 * 60.0 * 60.0);
        Log.d("test", "sessionHours: " + sessionHours);
        boolean exists = databaseHandler.doesPomodoroStatExist(userID, currentDate);
//        boolean exists = false;
//        try {
//            exists = databaseHandler.doesPomodoroStatExist(userID, currentDate);
//            Log.d("test", "Does Pomodoro stat exist: " + exists);
//        } catch (Exception e) {
//            Log.e("test", "Error in doesPomodoroStatExist: " + e.getMessage());
//        }
//
//        Log.d("test", "Does Pomodoro stat exist: " + exists);
//        Log.d("test", "Today Hours Focused: " + databaseHandler.getTodayHoursFocused(userID, currentDate));
//        Log.d("test", "Total Hours Focused: " + databaseHandler.getTotalHoursFocused(userID));
//        Log.d("test", "Total Pomodoro Sessions: " + databaseHandler.getTotalPomodoroSessions(userID));

        if(exists){ // pomo stats already exist
            double currentHoursToday = sessionHours + databaseHandler.getTodayHoursFocused(userID, currentDate);
            double currentTotalHours = sessionHours + databaseHandler.getTotalHoursFocused(userID);
            int currentTotalSessions = databaseHandler.getTotalPomodoroSessions(userID) + 1;
            boolean updateSuccess = databaseHandler.updatePomodoroStats(userID, currentDate, currentHoursToday, currentTotalHours, currentTotalSessions);
            if(!updateSuccess){
                Log.e("test", "Failed to update pomodoro stats");
            }
        }
        else{ // pomo stats don't exist
            databaseHandler.insertPomodoroStat(userID, currentDate, sessionHours, sessionHours, 1);
        }
        Log.d("test", "Pomodoro stats updated for user ID: " + userID);
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    @SuppressLint("DefaultLocale")
    private void updateTimerText() {
        int mins = (int) (timeRemaining / 1000) / 60;
        int secs = (int) (timeRemaining / 1000) % 60;
        timeText.setText(String.format("%02d:%02d", mins, secs));
    }

    private void pauseTimer() {
        if(countDownTimer != null){
            startButton.setText("Pause");
            countDownTimer.cancel();
        }
        timerRunning = false;
        startButton.setText("Start");
    }

    private void resetTimer() {
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        if(workSession){
            timeRemaining = workDuration;
            sessionStatus.setText("Session Start!");
        }
        else{
            timeRemaining = breakDuration;
            sessionStatus.setText("Break Start!");
        }
        updateTimerText();
        timerRunning = false;
        startButton.setText("Start");
    }

    private void adjustSessionTime(int i) {
        long newSessionDuration = workDuration + (long) i * 60 * 1000;
        if(newSessionDuration > 0){
            workDuration = newSessionDuration;
            sessionLengthText.setText(String.valueOf(workDuration / (60*1000)));
            if(workSession){
                timeRemaining = workDuration;
                updateTimerText();
            }
        }
    }

    private void adjustBreakTime(int i) {
        long newBreakDuration = breakDuration + (long) i * 60 * 1000;
        if(newBreakDuration > 0){
            breakDuration = newBreakDuration;
            breakLengthText.setText(String.valueOf(breakDuration / (60*1000)));
            if(!workSession){
                timeRemaining = breakDuration;
                updateTimerText();
            }
        }
    }



}