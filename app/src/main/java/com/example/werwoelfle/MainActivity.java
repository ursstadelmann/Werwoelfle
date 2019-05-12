package com.example.werwoelfle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private GameStateConnection conn = new GameStateConnection();

    static final int RESULT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start Game Service
        Intent gameService = new Intent(this, GameState.class);
        startService(gameService);
        bindService(new Intent(this, GameState.class), conn, 0);
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume()");
        super.onResume();
        bindService(new Intent(this, MainActivity.class), conn, 0);
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause()");
        super.onPause();
        unbindService(conn);
    }

    private void initPlayers(Integer players) {
        conn.getApi().setPlayers(players);
    }

    public void next(View v) {
        Log.d(LOG_TAG, "next()");

        // Get player count and init service
        Spinner playerDropdown = findViewById(R.id.playerCount);
        initPlayers(Integer.parseInt(playerDropdown.getSelectedItem().toString()) - 1);

        // Create Group SignUp Intent
        Intent groupSignUpActivityIntent = new Intent(this, GroupSignUpActivity.class);
        startActivity(groupSignUpActivityIntent);
    }

    public void chooseExistingGroups(View v) {
        Log.d(LOG_TAG, "chooseExistingGroups()");

        //Create Exisiting Groups
        Intent existingGroupsIntent = new Intent(this, ChooseExistingGroupsActivity.class);
        startActivityForResult(existingGroupsIntent, RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 0:
                Integer groupSelected = data.getIntExtra("groupId", -1);
                Groups group = GroupsDatabase.getInstance(this).groupsDao().getById(groupSelected);
                initPlayers(group.getNumberOfPlayers());

                // Create Group SignUp Intent
                Intent groupSignUpActivityIntent = new Intent(this, GroupSignUpActivity.class);
                groupSignUpActivityIntent.putExtra("groupId", groupSelected);
                startActivity(groupSignUpActivityIntent);
                break;
            case 1:
                break;
            default:
                break;
        }
    }

    public void process(View v) {
        Log.d(LOG_TAG, "MainActivity: process()");


        Intent processActivityIntent = new Intent(this, processAblaufButtonActivity.class);
        startActivity(processActivityIntent);
    }

    public void figures(View v) {
        Log.d(LOG_TAG, "MainActivity: figures()");


        Intent figuresActivityIntent = new Intent(this, figuresActivity.class);
        startActivity(figuresActivityIntent);
    }
}
