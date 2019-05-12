package com.example.werwoelfle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class ChooseExistingGroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_groups);
        createExistingGroupsSpinner();
    }

    private void createExistingGroupsSpinner() {
        Spinner existingGroupsSpinner = findViewById(R.id.existingGroups);
        List<Groups> groups = GroupsDatabase.getInstance(this).groupsDao().getAll();

        if (groups == null) {
            setResult(1);
        }

        ArrayAdapter<Groups> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        existingGroupsSpinner.setAdapter(adapter);
    }

    public void getGroupSelected(View v) {
        Spinner existingGroupsSpinner = findViewById(R.id.existingGroups);
        int groupId = existingGroupsSpinner.getSelectedItemPosition() + 1;

        Intent groupIdIntent = new Intent();
        groupIdIntent.putExtra("groupId", groupId);
        setResult(0, groupIdIntent);
        finish();
    }

    public void cancel(View v) {
        setResult(1);
        finish();
    }
}
