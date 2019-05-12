package com.example.werwoelfle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private GameStateConnection conn = new GameStateConnection();

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
        Log.d(LOG_TAG, "MainActivity: onResume()");
        super.onResume();
        bindService(new Intent(this, MainActivity.class), conn, 0);
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "MainActivity: onPause()");
        super.onPause();
        unbindService(conn);
    }

    private void initPlayers() {
        Spinner playerDropdown = (Spinner) findViewById(R.id.playerCount);
        conn.getApi().setPlayers(Integer.parseInt(playerDropdown.getSelectedItem().toString()) - 1);
    }

    public void next(View v) {
        Log.d(LOG_TAG, "MainActivity: next()");

        // Get player count and init service
        initPlayers();

        // Create Group SignUp Intent
        Intent groupSignUpActivityIntent = new Intent(this, GroupSignUpActivity.class);
        startActivity(groupSignUpActivityIntent);
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
