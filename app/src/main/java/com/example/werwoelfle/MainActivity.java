package com.example.werwoelfle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import java.util.ArrayList;

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

        if (!conn.isServiceConnected()) {
            bindService(new Intent(this, MainActivity.class), conn, 0);
        }
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "MainActivity: onPause()");
        super.onPause();

        unbindService(conn);
    }

    private void initPlayers() {
        Spinner playerDropdown = (Spinner) findViewById(R.id.playerCount);
        conn.getApi().setPlayers(Integer.parseInt(playerDropdown.getSelectedItem().toString()));
        ArrayList<Integer> inLoveIds = new ArrayList<>();
        inLoveIds.add(0);
        inLoveIds.add(1);
        conn.getApi().setInLove(inLoveIds);
    }

    public void next(View v) {
        Log.d(LOG_TAG, "MainActivity: next()");

        // Get player count and init service
        initPlayers();

        // Create Group SignUp Intent
        Intent groupSignUpActivityIntent = new Intent(this, GroupSignUpActivity.class);
        startActivity(groupSignUpActivityIntent);
    }
}
