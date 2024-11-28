package edu.sjsu.android.jams.Goals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.android.jams.R;


public class GoalFragment extends Fragment {

    private RecyclerView goalsRecyclerView;
    private GoalAdapter goalsAdapter;

    private List<Goal> goalList;

    public GoalFragment() {
        // Required empty public constructor
    }

    public static GoalFragment newInstance(String param1, String param2) {
        GoalFragment fragment = new GoalFragment();
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
        View view = inflater.inflate(R.layout.fragment_goal_main, container, false);

        goalList = new ArrayList<>();

        Goal goal = new Goal();
        goal.setGoalStatus(0);
        goal.setGoalTitle("This is a test goal");
        goal.setGoalId(1);

        goalList.add(goal);
        goalList.add(goal);
        goalList.add(goal);
        goalList.add(goal);

        goalsRecyclerView = view.findViewById(R.id.goalsRecyclerView);
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        goalsAdapter = new GoalAdapter(view.getContext());
        goalsRecyclerView.setAdapter(goalsAdapter);


        goalsAdapter.setGoalList(goalList);


        return view;
    }
}