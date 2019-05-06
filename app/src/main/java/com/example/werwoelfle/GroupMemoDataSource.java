package com.example.werwoelfle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class GroupMemoDataSource {

    private static final String LOG_TAG = GroupMemoDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private GroupMemoDbHelper dbHelper;

    private String[] columns = {
            GroupMemoDbHelper.COLUMN_ID,
            GroupMemoDbHelper.COLUMN_GROUPNAME,
            GroupMemoDbHelper.COLUMN_GROUPMEMBERNAME,
            GroupMemoDbHelper.COLUMN_NUMBEROFPLAYERS,
            GroupMemoDbHelper.COLUMN_BLINZLI
    };

    public GroupMemoDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper");
        dbHelper = new GroupMemoDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public GroupMemo createGroupMemo(String groupname, String groupmembername, int numberOfPlayers, boolean blinzli) {
        ContentValues values = new ContentValues();
        values.put(GroupMemoDbHelper.COLUMN_GROUPNAME, groupname);
        values.put(GroupMemoDbHelper.COLUMN_GROUPMEMBERNAME, groupmembername);
        values.put(GroupMemoDbHelper.COLUMN_NUMBEROFPLAYERS, numberOfPlayers);
        values.put(GroupMemoDbHelper.COLUMN_BLINZLI, blinzli);

        long insertId = database.insert(GroupMemoDbHelper.TABLE_GROUP_LIST, null, values);

        Cursor cursor = database.query(GroupMemoDbHelper.TABLE_GROUP_LIST,
                columns, GroupMemoDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        GroupMemo groupMemo = cursorToGroupMemo(cursor);
        cursor.close();

        return groupMemo;
    }

    private GroupMemo cursorToGroupMemo(Cursor cursor){
        int idIndex = cursor.getColumnIndex(GroupMemoDbHelper.COLUMN_ID);
        int idGroupName = cursor.getColumnIndex(GroupMemoDbHelper.COLUMN_GROUPMEMBERNAME);
        int idGroupMemberName = cursor.getColumnIndex(GroupMemoDbHelper.COLUMN_GROUPMEMBERNAME);
        int idNumberOfPlayers = cursor.getColumnIndex(GroupMemoDbHelper.COLUMN_NUMBEROFPLAYERS);
        int idBlinzli = cursor.getColumnIndex(GroupMemoDbHelper.COLUMN_BLINZLI);

        String groupName = cursor.getString(idGroupName);
        String groupMemberName = cursor.getString(idGroupMemberName);
        int numberOfPlayers = cursor.getInt(idNumberOfPlayers);
        boolean blinzli = cursor.getInt(idBlinzli) > 0;
        long id = cursor.getLong(idIndex);

        GroupMemo groupMemo = new GroupMemo(groupName, numberOfPlayers, groupMemberName, blinzli);
        return groupMemo;
    }

    public List<GroupMemo> getAllGroupMemos() {
        List<GroupMemo> groupMemoList = new ArrayList<>();

        Cursor cursor = database.query(GroupMemoDbHelper.TABLE_GROUP_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        GroupMemo groupMemo;

        while (!cursor.isAfterLast()) {
            groupMemo = cursorToGroupMemo(cursor);
            groupMemoList.add(groupMemo);
            Log.d(LOG_TAG, "id " + groupMemo.getId()+ ", Inhalt: " + groupMemo.toString());
            cursor.moveToNext();
        }

        cursor.close();
        return groupMemoList;
    }
}
