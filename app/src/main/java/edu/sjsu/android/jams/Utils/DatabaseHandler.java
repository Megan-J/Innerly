package edu.sjsu.android.jams.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.sjsu.android.jams.Goals.Goal;
import edu.sjsu.android.jams.entries.Entry;

public class DatabaseHandler extends SQLiteOpenHelper {

    // reference to sqlite database
    private SQLiteDatabase db;

    // GOALS
    private static final int VERSION = 1;
    private static final String NAME = "appDatabase";
    private static final String GOAL_TABLE = "goal";
    private static final String ID = "id";
    private static final String GOAL = "goal";
    private static final String STATUS = "status";
    private static final String CREATE_GOAL_TABLE = "CREATE TABLE " + GOAL_TABLE + "(" + ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + GOAL + " TEXT, " + STATUS + " INTEGER)";

    // USER ACCOUNT
    private static final String USER_TABLE = "user";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "("
                                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                    + COLUMN_EMAIL + " TEXT UNIQUE, "
                                    + COLUMN_PASSWORD + " TEXT)";

    // POMODORO INFORMATION
    private static final String POMODORO_TABLE = "pomodoro";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_HOURS_FOCUSED_TODAY = "hours_focused_today";
    private static final String COLUMN_TOTAL_HOURS_FOCUSED = "total_hours_focused";
    private static final String COLUMN_TOTAL_SESSIONS = "total_sessions";
    private static final String CREATE_POMODORO_TABLE = "CREATE TABLE " + POMODORO_TABLE + " (" +
                                                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                        COLUMN_USER_ID + " INTEGER, " +
                                                        COLUMN_DATE + " TEXT, " +
                                                        COLUMN_HOURS_FOCUSED_TODAY + " REAL, " +
                                                        COLUMN_TOTAL_HOURS_FOCUSED + " REAL, " +
                                                        COLUMN_TOTAL_SESSIONS + " INTEGER, " +
                                                        "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES user(id))";

    // ENTRIES
    private static final String JOURNAL_TABLE = "journal";
    private static final String COLUMN_ENTRY_DATE = "date";
    private static final String COLUMN_ENTRY_PROMPT = "prompt";
    private static final String COLUMN_ENTRY_TITLE = "title";
    private static final String COLUMN_ENTRY_CONTENT = "content";
    private static final String CREATE_JOURNAL_TABLE = "CREATE TABLE " + JOURNAL_TABLE + " (" +
                                                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                        COLUMN_USER_ID + " INTEGER, " +
                                                        COLUMN_ENTRY_DATE + " DATE, " +
                                                        COLUMN_ENTRY_PROMPT + " TEXT, " +
                                                        COLUMN_ENTRY_TITLE + " TEXT, " +
                                                        COLUMN_ENTRY_CONTENT + " TEXT, " +
                                                        "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES user(id))";

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_GOAL_TABLE);
        db.execSQL(CREATE_USER_TABLE);
//        db.execSQL(CREATE_POMODORO_TABLE);
        db.execSQL(CREATE_JOURNAL_TABLE);
        try {
            db.execSQL(CREATE_POMODORO_TABLE);
            Log.e("test", "Created Pomodoro table");

        } catch (SQLException e) {
            Log.e("test", "Error creating Pomodoro table", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // drop the old table
        db.execSQL("DROP TABLE IF EXISTS " + GOAL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + POMODORO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JOURNAL_TABLE);

        // create new table
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    //********************STATS METHODS******************//
    public boolean insertPomodoroStat(int userId, String date, double hoursFocusedToday, double totalHoursFocused, int totalSessions){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_HOURS_FOCUSED_TODAY, hoursFocusedToday);
        values.put(COLUMN_TOTAL_HOURS_FOCUSED, totalHoursFocused);
        values.put(COLUMN_TOTAL_SESSIONS, totalSessions);

        long result = db.insert(POMODORO_TABLE, null, values);
        return result > 0;
    }

    public boolean updatePomodoroStats(int userId, String date, double hoursFocusedToday, double totalHoursFocused, int totalSessions){
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOURS_FOCUSED_TODAY, hoursFocusedToday);
        values.put(COLUMN_TOTAL_HOURS_FOCUSED, totalHoursFocused);
        values.put(COLUMN_TOTAL_SESSIONS, totalSessions);

        int result = db.update(POMODORO_TABLE, values, COLUMN_USER_ID + "=? AND " + COLUMN_DATE + "=?",
                new String[]{String.valueOf(userId), date});
        return result > 0;
    }

    public boolean doesPomodoroStatExist(int userId, String date) {
        Cursor cursor = db.query(POMODORO_TABLE,
                new String[]{COLUMN_USER_ID},
                COLUMN_USER_ID + "=? AND " + COLUMN_DATE + "=?",
                new String[]{String.valueOf(userId), date},
                null, null, null);

        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }

    public Cursor getPomodoroStats(int userId){
        return db.query(POMODORO_TABLE, null, COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    @SuppressLint("Range")
    public double getTodayHoursFocused(int userId, String date) {
        double hoursFocused = 0;
        Cursor cursor = db.query(POMODORO_TABLE,
                new String[]{COLUMN_HOURS_FOCUSED_TODAY},
                COLUMN_USER_ID + "=? AND " + COLUMN_DATE + "=?",
                new String[]{String.valueOf(userId), date},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            hoursFocused = cursor.getDouble(cursor.getColumnIndex(COLUMN_HOURS_FOCUSED_TODAY));
            cursor.close();
        }
        return hoursFocused;
    }

    public double getTotalHoursFocused(int userId) {
        double totalHours = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_TOTAL_HOURS_FOCUSED + ") FROM " + POMODORO_TABLE
                + " WHERE " + COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            totalHours = cursor.getDouble(0);
            cursor.close();
        }
        return totalHours;
    }

    public int getTotalPomodoroSessions(int userId) {
        int totalSessions = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_TOTAL_SESSIONS + ") FROM " + POMODORO_TABLE
                + " WHERE " + COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            totalSessions = cursor.getInt(0);
            cursor.close();
        }
        return totalSessions;
    }

    //********************USER METHODS******************//
    public boolean insertUser(String email, String password){
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(USER_TABLE, null, values);
        return result > 0; // true if successful, else false
    }

    public int getUserId(String email, String password){
        Cursor cursor = db.query(
                USER_TABLE,
                new String[]{ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex(ID));
            cursor.close();
            return userId;
        }
        return -1;
    }

    public Cursor getUser(String email){
        return db.query(USER_TABLE, null, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
    }

    public boolean validateUser(String email, String password){
        Cursor cursor = db.query(USER_TABLE, new String[]{ID}, COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean isValidUser = (cursor != null && cursor.getCount() > 0);
        if(cursor != null){
            cursor.close();
        }
        return isValidUser; // true if valid, else false
    }

    public int deleteDataById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE, "ID = ?", new String[]{String.valueOf(id)});
    }

    //********************GOAL METHODS******************//
    public void insertGoal(Goal goal){
        ContentValues values = new ContentValues();
        values.put(GOAL, goal.getGoalTitle());
        values.put(STATUS, 0);
        db.insert(GOAL_TABLE, null, values);
    }

    @SuppressLint("Range")  // suppressing the range because it's accounted for inside the if statement
    public List<Goal> getAllGoals(){
        List<Goal> goalList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        Log.d("Database Check: ", "Inside getting all goals method");
        try{
            cursor = db.query(GOAL_TABLE, null, null, null, null, null, null, null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        Goal goal = new Goal();
                        if((cursor.getColumnIndex(ID) >= 0) && (cursor.getColumnIndex(GOAL) >=0) && (cursor.getColumnIndex(STATUS) >=0)){
                            goal.setGoalId(cursor.getInt(cursor.getColumnIndex(ID)));
                            goal.setGoalTitle(cursor.getString(cursor.getColumnIndex(GOAL)));
                            Log.d("Database Check: ", "Retrieved Goals");
                            goal.setGoalStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        }
                        goalList.add(goal);
                    } while(cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return goalList;
    }

    // getting it from the recycling view
    public void updateStatus(int id, int status){
        ContentValues values = new ContentValues();
        values.put(STATUS, status);
        db.update(GOAL_TABLE, values, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateGoal(int id, String goalName){
        ContentValues values = new ContentValues();
        values.put(GOAL, goalName);
        db.update(GOAL_TABLE, values, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteGoal(int id){
        db.delete(GOAL_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }

    //********************JOURNAL ENTRY METHODS******************//
    public boolean insertEntry(Entry entry){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, entry.getUserID());
        values.put(COLUMN_ENTRY_DATE, entry.getDate());
        values.put(COLUMN_ENTRY_PROMPT, entry.getPrompt());
        values.put(COLUMN_ENTRY_TITLE, entry.getTitle());
        values.put(COLUMN_ENTRY_CONTENT, entry.getContent());

        long result = db.insert(JOURNAL_TABLE, null, values);
        return result > 0; // true if successful, else false
    }

    @SuppressLint("Range")
    public List<String> getEntryDatesList(int userID) {
        List<String> datesList = new ArrayList<>();
        try (Cursor c = db.query(true, JOURNAL_TABLE, new String[]{COLUMN_ENTRY_DATE},
                COLUMN_USER_ID + "=" + userID, null, null, null,
                COLUMN_ENTRY_DATE + " DESC", null)) {
            if (c.moveToFirst()) {
                if ((c.getColumnIndex(COLUMN_ENTRY_DATE) >= 0)) {
                    do {
                        datesList.add(c.getString(c.getColumnIndex(COLUMN_ENTRY_DATE)));
                    } while(c.moveToNext());
                }
            }
        }
        return datesList;
    }

    @SuppressLint("Range")
    public List<String> getEntryIDsForDate(int userID, String date) {
        List<String> entryIDList = new ArrayList<>();
        try (Cursor c = db.query(JOURNAL_TABLE, new String[]{ID},
                COLUMN_USER_ID + "=" + userID + " AND " + COLUMN_ENTRY_DATE + "= '" + date + "'",
                null, null, null, ID + " DESC");
        ) {
            if (c.moveToFirst()) {
                if ((c.getColumnIndex(ID) >= 0)) {
                    do {
                        entryIDList.add(c.getString(c.getColumnIndex(ID)));
                    } while(c.moveToNext());
                }
                else{
                    Log.d("get entryIDList db test", "entry id column doesn't exist");
                }
            }
            else{
                Log.d("get entryIDList db test", "cursor is empty");
            }
        }
        return entryIDList;
    }

    @SuppressLint("Range")  // suppressing the range because it's accounted for inside the if statement
    public Entry getEntryByID(String id){
        try (Cursor c = db.query(JOURNAL_TABLE, null, ID + "=?", new String[]{id}, null, null, null)) {
            if (c.moveToFirst()) {
                if ((c.getColumnIndex(COLUMN_USER_ID) >= 0) && (c.getColumnIndex(COLUMN_ENTRY_DATE) >= 0)
                        && (c.getColumnIndex(COLUMN_ENTRY_PROMPT) >= 0) && (c.getColumnIndex(COLUMN_ENTRY_TITLE) >= 0)
                        && (c.getColumnIndex(COLUMN_ENTRY_CONTENT) >= 0)) {
                    int userId = c.getInt(c.getColumnIndex(COLUMN_USER_ID));
                    String date = c.getString(c.getColumnIndex(COLUMN_ENTRY_DATE));
                    String prompt = c.getString(c.getColumnIndex(COLUMN_ENTRY_PROMPT));
                    String title = c.getString(c.getColumnIndex(COLUMN_ENTRY_TITLE));
                    String content = c.getString(c.getColumnIndex(COLUMN_ENTRY_CONTENT));
                    return new Entry(userId, date, prompt, title, content);
                }

            }
        }
        return null;
    }
//
//    public void updateEntry(int id, Date date, String prompt, String title, String content){
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_ENTRY_DATE, date.getDate());
//        values.put(COLUMN_ENTRY_PROMPT, prompt);
//        values.put(COLUMN_ENTRY_TITLE, title);
//        values.put(COLUMN_ENTRY_CONTENT, content);
//        db.update(JOURNAL_TABLE, values, ID + "=?", new String[] {String.valueOf(id)});
//    }
//
//    public void deleteEntry(int id){
//        db.delete(JOURNAL_TABLE, ID + "=?", new String[] {String.valueOf(id)});
//    }
}
