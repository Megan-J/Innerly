package edu.sjsu.android.jams.Goals;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sjsu.android.jams.R;
import edu.sjsu.android.jams.Utils.DatabaseHandler;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.ViewHolder> {

    private Fragment parentFragment;
    private Context context;
    private List<Goal> goalList;
    private DatabaseHandler db;

    public GoalAdapter(DatabaseHandler db, Fragment parentFragment){
        this.db = db;
        this.parentFragment = parentFragment;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.goal_layout, parent, false);

        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        Goal item = goalList.get(position);
        holder.task.setText(item.getGoalTitle());
        holder.task.setChecked(toBoolean(item.getGoalStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    db.updateStatus(item.getGoalId(), 1);
                }
                else {
                    db.updateStatus(item.getGoalId(), 0);
                }
            }
        });
    }

    public int getItemCount(){
        return goalList != null ? goalList.size() : 0;
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setGoalList(List<Goal> goalList){
        this.goalList = goalList;
        notifyDataSetChanged();
    }

    public void editItem(int index){
        Goal item = goalList.get(index);

        // Create a bundle to pass data to fragment
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getGoalId());
        bundle.putString("goal", item.getGoalTitle());

        AddNewGoal fragment = new AddNewGoal();
        fragment.setArguments(bundle);

        // Use the parent fragment's child fragment manager
        fragment.show(parentFragment.getChildFragmentManager(), AddNewGoal.TAG);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.checkbox);
        }
    }

}
