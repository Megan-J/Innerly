package edu.sjsu.android.jams.questions;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class QuestionCategory implements Parcelable{

    private int category;
    private List<String> questions;

    public QuestionCategory(int category, List<String> questions) {
        this.category = category;
        this.questions = questions;
    }

    public int getCategory(){
        return category;
    }

    public List<String> getQuestions() {
        return questions;
    }

    // Parcelable methods for passing this object in a bundle (if necessary)
    protected QuestionCategory(Parcel in) {
        category = in.readInt();
        questions = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category);
        dest.writeStringList(questions);
    }

    public static final Creator<QuestionCategory> CREATOR = new Creator<QuestionCategory>() {
        @Override
        public QuestionCategory createFromParcel(Parcel in) {
            return new QuestionCategory(in);
        }

        @Override
        public QuestionCategory[] newArray(int size) {
            return new QuestionCategory[size];
        }
    };

}
