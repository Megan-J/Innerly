package edu.sjsu.android.jams.Goals;

public class Goal {

    private int goalId;
    private int goalStatus;
    private String goalTitle;

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getGoalStatus() {
        return goalStatus;
    }

    public void setGoalStatus(int goalStatus) {
        this.goalStatus = goalStatus;
    }

    public String getGoalTitle() {
        return goalTitle;
    }

    public void setGoalTitle(String goalTitle) {
        this.goalTitle = goalTitle;
    }
}
