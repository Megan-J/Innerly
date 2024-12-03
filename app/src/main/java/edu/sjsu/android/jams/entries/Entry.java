package edu.sjsu.android.jams.entries;

public class Entry {
    private int entryID;
    private int userID;
    private String date;
    private String prompt;
    private String title;
    private String content;

    //for testing entries list
    public Entry(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Entry(int userID, String date, String prompt, String title, String content) {
        this.userID = userID;
        this.date = date;
        this.prompt = prompt;
        this.title = title;
        this.content = content;
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
}
