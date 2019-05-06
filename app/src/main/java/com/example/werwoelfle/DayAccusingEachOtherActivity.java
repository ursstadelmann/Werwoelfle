package com.example.werwoelfle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class DayAccusingEachOtherActivity extends Activity {
    private GameStateConnection conn = new GameStateConnection();
    private static final String LOG_TAG = PlayerRoleAllocationActivity.class.getName();
    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_accusing_each_other);

        bindService(new Intent(this, GameState.class), conn, 0);

        this.players = conn.getApi().getPlayersAlive();

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
        unbindService(conn);
    }

    // TODO: Accusing logic
    public void next(View v) {
        conn.getApi().newNight();
        Intent nightWerewolfActivity = new Intent(this, NightWerewolfActivity.class);
        startActivity(nightWerewolfActivity);
    }
}
