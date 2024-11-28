package edu.sjsu.android.jams.Goals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sjsu.android.jams.MainActivity;
import edu.sjsu.android.jams.R;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.ViewHolder> {

    private MainActivity mainActivity;
    private Context context;
    private List<Goal> goalList;

    public GoalAdapter(Context context){
        this.context = context;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.goal_layout, parent, false);

        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        Goal item = goalList.get(position);
        holder.task.setText(item.getGoalTitle());
        holder.task.setChecked(toBoolean(item.getGoalStatus()));
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.checkbox);
        }
    }

}
