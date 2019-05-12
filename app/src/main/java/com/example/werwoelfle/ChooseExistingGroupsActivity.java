package com.example.werwoelfle;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.intellij.lang.annotations.Language;

import java.util.LinkedList;
import java.util.List;

public class ChooseExistingGroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_groups);

    }

    private Spinner createExistingGroupsSpinner(){
        new Groups("theDudes", 7, "Marco#Sebastian#Paul“Gian#Hans#Martin#Rice", false);
        Spinner existingGroupsSpinner = findViewById(R.id.existingGroups);
        List<Groups> groups = GroupsDatabase.getInstance(this).groupsDao().getAll();
       // List<String> languageStrings = new LinkedList<>();

        //noni
        //for(int i = 0; i < languages.size(); i++){
        //    languageStrings.add(languages.get(i).getLanguage());
        //}

        ArrayAdapter<Groups> adapter = new ArrayAdapter<Groups>(this, android.R.layout.simple_dropdown_item_1line, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        existingGroupsSpinner.setAdapter(adapter);
        return existingGroupsSpinner;
    }
}
