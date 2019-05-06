package com.example.werwoelfle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class GroupMemoDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = GroupMemoDbHelper.class.getSimpleName();

    public static final String DB_NAME= "group_list.db";
    public static final int DB_VERSION= 1;

    public static final String TABLE_GROUP_LIST = "group_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GROUPNAME = "groupname";
    public static final String COLUMN_GROUPMEMBERNAME = "groupmembername";
    public static final String COLUMN_NUMBEROFPLAYERS = "_number";
    public static final String COLUMN_BLINZLI = "_true";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_GROUP_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GROUPNAME + " TEXT NOT NULL, " +
                    COLUMN_GROUPMEMBERNAME + " TEXT NOT NULL, " +
                    COLUMN_NUMBEROFPLAYERS + " INTEGER NOT NULL, " +
                    COLUMN_BLINZLI + " BOOLEAN NOT NULL);";


    public GroupMemoDbHelper(Context context) {
        //super(context, "PLATZHALTER_DATENBANKNAME", null, 1);
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try{
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt");
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Anlagen der Tabelle " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2){

    }

}
