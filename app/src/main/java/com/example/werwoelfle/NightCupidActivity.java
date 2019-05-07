package com.example.werwoelfle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class NightCupidActivity extends Activity {
    private GameStateConnection conn;
    private static final String LOG_TAG = NightCupidActivity.class.getName();

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_cupid);

        conn = new GameStateConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                super.onServiceConnected(name, service);

                players = conn.getApi().getPlayersAlive();

                ArrayList<Player> inLovePlayer = conn.getApi().getInLove();

                setPlayerDropdown(players, R.id.inLove_1, inLovePlayer.get(0).getId());
                setPlayerDropdown(players, R.id.inLove_2, inLovePlayer.get(1).getId());
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
        unbindService(conn);
    }

    private void setPlayerDropdown(ArrayList<Player> players, int spinnerId, int selectionId) {
        final Spinner spinner = (Spinner) findViewById(spinnerId);

        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getName());
        }

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, playerNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setSelection(selectionId);
    }

    private ArrayList<Integer> getSpinnerSelected() {
        ArrayList<Integer> inLoveIds = new ArrayList<>();
        Spinner inLove_1 = findViewById(R.id.inLove_1);
        String inLove1 = inLove_1.getSelectedItem().toString();

        Spinner inLove_2 = findViewById(R.id.inLove_2);
        String inLove2 = inLove_2.getSelectedItem().toString();
        for (Player player : this.players) {
            if (player.getName().equals(inLove1) || player.getName().equals(inLove2)) {
                inLoveIds.add(player.getId());
            }
        }
        return inLoveIds;
    }

    public void next(View v) {
        // Get Player ID
        ArrayList<Integer> inLoveIds = getSpinnerSelected();

        conn.getApi().setInLove(inLoveIds);
        Intent nightCupid = new Intent(this, NightWerewolfActivity.class);
        startActivity(nightCupid);
    }
}
