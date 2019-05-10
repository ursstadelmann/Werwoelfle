package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class DayAccusingEachOtherActivity extends AppCompatActivity {
    private GameStateConnection conn;
    private static final String LOG_TAG = DayAccusingEachOtherActivity.class.getName();
    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_accusing_each_other);

        conn = new GameStateConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                super.onServiceConnected(name, service);

                // TODO: Accusing logic
                players = conn.getApi().getPlayersAlive();
            }
        };

        bindService(new Intent(this, GameState.class), conn, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!conn.isServiceConnected()) {
            bindService(new Intent(this, GameState.class), conn, 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(conn.isServiceConnected()) {
            unbindService(conn);
        }
    }

    // TODO: Accusing logic
    public void next(View v) {
        conn.getApi().endOfDay();
        Intent nightWerewolfActivity = new Intent(this, NightWerewolfActivity.class);
        startActivity(nightWerewolfActivity);
    }
}
