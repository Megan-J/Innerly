package edu.sjsu.android.jams.goals;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Goal implements Parcelable {

    private final int goalID;
    private final int nameID;

    public Goal(int goalID, int name){
        this.goalID = goalID;
        this.nameID = name;
    }


    protected Goal(Parcel in) {
        goalID = in.readInt();
        nameID = in.readInt();
    }

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(goalID);
        parcel.writeInt(nameID);

    }
}
