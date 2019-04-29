package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Werewolf";
    private MyServiceConnection conn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start Game Service
        Intent gameService = new Intent(this, GameState.class);
        startService(gameService);
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "MainActivity: onResume()");
        super.onResume();

        bindService(new Intent("service"), conn, 0);
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "MainActivity: onPause()");
        super.onPause();

        unbindService(conn);
    }

    public void next(View v) {
        Log.d(LOG_TAG, "MainActivity: next()");

        // Get player count and init service
        initPlayers();

        // Create Group SignUp Intent
        Intent groupSignUpActivityIntent = new Intent(this, GroupSignUpActivity.class);
        startActivity(groupSignUpActivityIntent);
    }

    private void initPlayers() {
        Spinner playerDropdown = (Spinner) findViewById(R.id.playerCount);
        conn.getApi().setPlayers(Integer.parseInt(playerDropdown.getSelectedItem().toString()));
    }

    public static class MyServiceConnection implements ServiceConnection {

        private GameState.IServiceApi api;

        public GameState.IServiceApi getApi() {
            return api;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            this.api = (GameState.IServiceApi) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            api = null;
        }
    }
}
