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

public class NightSeerActivity extends Activity {
    private GameStateConnection conn;
    private static final String LOG_TAG = NightSeerActivity.class.getName();

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_seer);

        conn = new GameStateConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                super.onServiceConnected(name, service);

                players = conn.getApi().getPlayersAlive();
                for (Player player : players) {
                    if (player.getRole() == Roles.SEER) {
                        Integer preselected = conn.getApi().getSeerWatched();
                        setPlayerDropdown(players, R.id.seerDropdown, preselected);
                        return;
                    }
                }
                activity();
            }
        };

        bindService(new Intent(this, GameState.class), conn, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(new Intent(this, GameState.class), conn, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(conn);
    }

    private void setPlayerDropdown(ArrayList<Player> players, int spinnerId, Integer selectionId) {
        final Spinner spinner = findViewById(spinnerId);

        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            String playerText;
            if (GivePhoneToPlayerActivity.isNullOrEmpty(player.getName())) {
                playerText = getApplicationContext().getString(R.string.give_phone_to_player, Integer.toString(player.getId()));
            } else {
                playerText = player.getName();
            }

            playerNames.add(playerText);
        }

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
            this, android.R.layout.simple_spinner_item, playerNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);
        if (selectionId == null) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(selectionId);
        }
    }

    private Integer getSeerWatched() {
        Spinner watchSeer = findViewById(R.id.seerDropdown);
        int watchSeerId = watchSeer.getSelectedItemPosition();

        return this.players.get(watchSeerId).getId();
    }

    public void getRole(View v) {
        int playerId = getSeerWatched();
        for (Player player : this.players) {
            if (player.getId() == playerId) {
                if (player.getRole() == Roles.WEREWOLF) {
                    Intent showWerewolf = new Intent(this, NightSeerIsAWerewolf.class);
                    startActivity(showWerewolf);
                } else {
                    Intent showWerewolf = new Intent(this, NightSeerIsAVillager.class);
                    startActivity(showWerewolf);
                }
            }
        }
    }

    public void next(View v) {
        conn.getApi().setSeerWatched(getSeerWatched());
        activity();
    }

    private void activity() {
        Intent dayPeopleWakingUp = new Intent(this, DayPeopleWakingUpActivity.class);
        startActivity(dayPeopleWakingUp);
    }
}
