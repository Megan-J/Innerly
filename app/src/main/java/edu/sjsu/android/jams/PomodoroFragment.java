package edu.sjsu.android.jams;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PomodoroFragment extends Fragment {
    private TextView timeText, sessionLengthText, breakLengthText, sessionStatus;
    private Button startButton, resetButton, sessionIncrement, sessionDecrement, breakIncrement, breakDecrement;

    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;
    private boolean workSession = true;

    private long workDuration = 25 * 60 * 1000;
    private long breakDuration = 2 * 60 * 1000;
    private long timeRemaining = workDuration;

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

        sessionLengthText.setText(String.valueOf(workDuration / 60 / 1000));
        breakLengthText.setText(String.valueOf(breakDuration / 60 / 1000));

        startButton.setOnClickListener(v -> startOrPauseTimer());
        resetButton.setOnClickListener(v -> resetTimer());
        sessionIncrement.setOnClickListener(v -> adjustSessionTime(1));
        sessionDecrement.setOnClickListener(v -> adjustSessionTime(-1));
        breakIncrement.setOnClickListener(v -> adjustBreakTime(1));
        breakDecrement.setOnClickListener(v -> adjustBreakTime(-1));

        return view;
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