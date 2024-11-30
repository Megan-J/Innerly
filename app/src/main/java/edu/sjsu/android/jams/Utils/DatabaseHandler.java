package edu.sjsu.android.jams.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.android.jams.Goals.Goal;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "goalDatabase";
    private static final String GOAL_TABLE = "goal";
    private static final String ID = "id";
    private static final String GOAL = "goal";
    private static final String STATUS = "status";
    private static final String CREATE_GOAL_TABLE = "CREATE TABLE " + GOAL_TABLE + "(" + ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + GOAL + " TEXT, " + STATUS + " INTEGER)";

    // reference to sqlite database
    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_GOAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // drop the old table
        db.execSQL("DROP TABLE IF EXISTS " + GOAL_TABLE);

        // create new table
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

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
        try{
            cursor = db.query(GOAL_TABLE, null, null, null, null, null, null, null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        Goal goal = new Goal();
                        if((cursor.getColumnIndex(ID) >= 0) && (cursor.getColumnIndex(GOAL) >=0) && (cursor.getColumnIndex(STATUS) >=0)){
                            goal.setGoalId(cursor.getInt(cursor.getColumnIndex(ID)));
                            goal.setGoalTitle(cursor.getString(cursor.getColumnIndex(GOAL)));
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
}
