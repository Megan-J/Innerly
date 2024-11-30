package edu.sjsu.android.jams.Goals;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.sjsu.android.jams.R;
import edu.sjsu.android.jams.Utils.DatabaseHandler;


public class GoalFragment extends Fragment implements DialogCloseListener {

    private RecyclerView goalsRecyclerView;
    private GoalAdapter goalsAdapter;

    private List<Goal> goalList;
    private DatabaseHandler db;

    private FloatingActionButton fab;


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

        // Initialize database handler
        db = new DatabaseHandler(this.getContext());
        db.openDatabase();

        // Set up recyclerView
        goalsRecyclerView = view.findViewById(R.id.goalsRecyclerView);
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Create goalsAdapter and pass current fragment
        goalsAdapter = new GoalAdapter(db, this);

        // Set adapter on recyclerView
        goalsRecyclerView.setAdapter(goalsAdapter);

        fab = view.findViewById(R.id.fab);

        goalList = db.getAllGoals();
        Collections.reverse(goalList);
        goalsAdapter.setGoalList(goalList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewGoal.newInstance().show(getActivity().getSupportFragmentManager(), AddNewGoal.TAG);
            }
        });

        return view;
    }

    @Override
    public void handleDialogClose(DialogInterface dialogInterface){
        goalList = db.getAllGoals();
        Collections.reverse(goalList);
        goalsAdapter.setGoalList(goalList);
        goalsAdapter.notifyDataSetChanged();
    }
}