package com.example.werwoelfle;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;


@Database(entities = {Groups.class}, version = 1, exportSchema = false)
public abstract class GroupsDatabase extends RoomDatabase {

    private static GroupsDatabase INSTANCE;

    public abstract GroupsDao groupsDao();

    public synchronized static GroupsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static GroupsDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context, GroupsDatabase.class, "groups").addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                })
                .build();
    }
}


