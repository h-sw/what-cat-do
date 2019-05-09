package com.team_project2.hans.whatcatdo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBLogHelper extends SQLiteOpenHelper {
    private final static String TAG = "DB LOG HELPER";

    public DBLogHelper(Context context){
        super(context, Common.DATABASE_NAME, null, Common.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGS_TABLE = "CREATE TABLE " + Common.TABLE_LOGS + "("
                                    + Common.LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                                    + Common.LOG_TIMESTAMP + " INTEGER,"
                                    + Common.LOG_PATH +" TEXT" + ")";
        db.execSQL(CREATE_LOGS_TABLE);

        String CREATE_EMOTIONS_TABLE = "CREATE TABLE " + Common.TABLE_EMOTIONS + "("
                                        + Common.EMOTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                        + Common.EMOTION_TIMESTAMP + " INTEGER,"
                                        + Common.EMOTION_TITLE + " TEXT,"
                                        + Common.EMOTION_PERCENT + " REAL" + ")";

        db.execSQL(CREATE_EMOTIONS_TABLE);
        android.util.Log.d(TAG,"DB create success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Common.TABLE_LOGS);
            db.execSQL("DROP TABLE IF EXISTS " + Common.TABLE_EMOTIONS);
            onCreate(db);
    }

    public void addLog(Log log, ArrayList<Emotion> emotions){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valueLog = new ContentValues();


        valueLog.put(Common.LOG_TIMESTAMP, log.getTimestamp());
        valueLog.put(Common.LOG_PATH, log.getPath());

        db.insert(Common.TABLE_LOGS, null, valueLog);

        for(Emotion emotion : emotions){
            ContentValues valueEmotion = new ContentValues();

            valueEmotion.put(Common.EMOTION_TIMESTAMP, emotion.getTimestamp());
            valueEmotion.put(Common.EMOTION_TITLE, emotion.getTitle());
            valueEmotion.put(Common.EMOTION_PERCENT, emotion.getPercent());

            db.insert(Common.TABLE_EMOTIONS, null, valueEmotion);
        }

        db.close();
        android.util.Log.d(TAG,"db add success");
    }

    public ArrayList<ResultLog> getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_LOGS_TABLE = "SELECT " + Common.TABLE_LOGS + "." + Common.LOG_TIMESTAMP
                                                + ", " + Common.LOG_PATH
                                                + ", " + Common.EMOTION_TITLE
                                                + ", " + Common.EMOTION_PERCENT
                                                + " FROM " + Common.TABLE_LOGS
                                                + " LEFT JOIN " + Common.TABLE_EMOTIONS
                                                + " ON "+ Common.TABLE_LOGS + "." + Common.LOG_TIMESTAMP + " == " + Common.TABLE_EMOTIONS + "." + Common.EMOTION_TIMESTAMP;
        //SELECT time FROM logs LEFT JOIN emotions ON logs.timestamp == emotions.timestamp

        Cursor cursor = db.rawQuery(SELECT_LOGS_TABLE,null);


        ArrayList<ResultLog> logs = new ArrayList<>();

        while(cursor.moveToNext()){
            logs.add(new ResultLog(cursor.getLong(0), cursor.getString(1)));
        }

        cursor = db.rawQuery(SELECT_LOGS_TABLE,null);

        while(cursor.moveToNext()){
            for(ResultLog log : logs){
                android.util.Log.d(TAG,Long.toString(log.getTimestamp()));
                android.util.Log.d(TAG,Long.toString(cursor.getLong(0)));
                if(log.getTimestamp()==cursor.getLong(0)){
                    android.util.Log.d(TAG,"12312312");
                    log.addEmotion(new Emotion(log.getTimestamp(),cursor.getString(2),cursor.getFloat(3)));
                }
            }
        }
        return logs;
    }

}