package edu.sjsu.android.jams.questions;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class QuestionCategory {

    private int category;
    private int questions;

    public QuestionCategory(int category) {
        this.category = category;
        this.questions = questions;
    }

//    protected QuestionCategory(Parcel in) {
//        questions = in.readInt();
//    }

    public int getCategory() {
        return category;
    }

    public int getQuestions() {
        return questions;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel parcel, int i) {
//
//    }

}
