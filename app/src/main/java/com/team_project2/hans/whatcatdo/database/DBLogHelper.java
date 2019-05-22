package com.team_project2.hans.whatcatdo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.team_project2.hans.whatcatdo.common.Common;

import java.util.ArrayList;

public class DBLogHelper extends SQLiteOpenHelper{
    private final static String TAG = "DB LOG HELPER";

    public DBLogHelper(Context context){
        super(context, Common.DATABASE_NAME, null, Common.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGS_TABLE = "CREATE TABLE " + Common.TABLE_LOGS + "("
                                    + Common.LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                                    + Common.LOG_TIMESTAMP + " INTEGER,"
                                    + Common.LOG_PATH +" TEXT,"
                                    + Common.LOG_COMMENT+ " TEXT"+")";
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
    //comment  추가

    public void addLog(Log log, ArrayList<Emotion> emotions){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valueLog = new ContentValues();


        valueLog.put(Common.LOG_TIMESTAMP, log.getTimestamp());
        valueLog.put(Common.LOG_PATH, log.getPath());
        valueLog.put(Common.LOG_COMMENT, log.getComment());

        db.insert(Common.TABLE_LOGS, null, valueLog);

        for(Emotion emotion : emotions){
            ContentValues valueEmotion = new ContentValues();

            valueEmotion.put(Common.EMOTION_TIMESTAMP, emotion.getTimestamp());
            valueEmotion.put(Common.EMOTION_TITLE, emotion.getTitle());
            valueEmotion.put(Common.EMOTION_PERCENT, emotion.getPercent());
            db.insert(Common.TABLE_EMOTIONS, null, valueEmotion);
        }

        db.close();
    }

    public ArrayList<LogEmotion> getLogEmotion(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<LogEmotion> logs = new ArrayList<>();

        //SELECT logs.timestamp, path, title, percent FROM logs LEFT JOIN emotions ON logs.timestamp == emotions.timestamp
        String SELECT_LOGS_TABLE = "SELECT " + Common.TABLE_LOGS + "." + Common.LOG_TIMESTAMP
                                                + ", " + Common.LOG_PATH
                                                + ", " + Common.EMOTION_TITLE
                                                + ", " + Common.EMOTION_PERCENT
                                                + ", " + Common.LOG_COMMENT
                                                + " FROM " + Common.TABLE_LOGS
                                                + " LEFT JOIN " + Common.TABLE_EMOTIONS
                                                + " ON " + Common.TABLE_LOGS + "." + Common.LOG_TIMESTAMP + " == " + Common.TABLE_EMOTIONS + "." + Common.EMOTION_TIMESTAMP
                                                + " ORDER BY " + Common.TABLE_LOGS + "." + Common.LOG_TIMESTAMP + " DESC;";

        Cursor cursor = db.rawQuery(SELECT_LOGS_TABLE,null);

        while(cursor.moveToNext()){
            boolean exist = false;
            for(LogEmotion logEmotion : logs){
                if(logEmotion.getTimestamp()==cursor.getLong(0)){
                    exist = true;
                }
            }
            if(!exist){
                logs.add(new LogEmotion(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(4)));
            }
        }

        cursor = db.rawQuery(SELECT_LOGS_TABLE,null);

        while(cursor.moveToNext()){
            for(LogEmotion log : logs){
                if(log.getTimestamp()==cursor.getLong(0)){
                    log.addEmotion(new Emotion(log.getTimestamp(),
                            cursor.getString(2),
                            cursor.getFloat(3)));
                }
            }
        }
        return logs;
    }

}