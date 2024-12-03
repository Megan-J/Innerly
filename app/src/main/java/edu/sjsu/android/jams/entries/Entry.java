package edu.sjsu.android.jams.entries;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Entry implements Parcelable {
    private int entryID;
    private int userID;
    private String date;
    private String prompt;
    private String title;
    private String content;

    /**
     * Entry object to insert into database
     * @param userID the user id
     * @param date the selected date
     * @param prompt the journal entry prompt
     * @param title the journal entry title
     * @param content the journal entry content
     */
    public Entry(int userID, String date, String prompt, String title, String content) {
        this.userID = userID;
        this.date = date;
        this.prompt = prompt;
        this.title = title;
        this.content = content;
    }

    /**
     * Entry object retrieved from database
     * @param entryID the entry id (primary key)
     * @param userID the user id
     * @param date the selected date
     * @param prompt the journal entry prompt
     * @param title the journal entry title
     * @param content the journal entry content
     */
    public Entry(int entryID, int userID, String date, String prompt, String title, String content) {
        this.entryID = entryID;
        this.userID = userID;
        this.date = date;
        this.prompt = prompt;
        this.title = title;
        this.content = content;
    }

    protected Entry(Parcel in) {
        entryID = in.readInt();
        userID = in.readInt();
        date = in.readString();
        prompt = in.readString();
        title = in.readString();
        content = in.readString();
    }

    public int getEntryID() {
        return entryID;
    }

    public void setEntryID(int entryID) {
        this.entryID = entryID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(entryID);
        parcel.writeInt(userID);
        parcel.writeString(date);
        parcel.writeString(prompt);
        parcel.writeString(title);
        parcel.writeString(content);
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };
}
