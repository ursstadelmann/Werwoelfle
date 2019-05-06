package com.example.werwoelfle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class NightSeerActivity extends Activity {
    private GameStateConnection conn = new GameStateConnection();
    private static final String LOG_TAG = PlayerRoleAllocationActivity.class.getName();

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_seer);

        bindService(new Intent(this, GameState.class), conn, 0);
        this.players = conn.getApi().getPlayersAlive();

        int preselected = conn.getApi().getSeerWatched();

        setPlayerDropdown(this.players, R.id.seerDropdown, preselected);
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
        final Spinner spinner = findViewById(spinnerId);

        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getName());
        }

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
            this, android.R.layout.simple_spinner_item, playerNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setSelection(selectionId);
    }

    private Integer getSeerWatched() {
        Integer id = null;
        Spinner killedByWerewolf = findViewById(R.id.seerDropdown);
        String killedByWerewolfName = killedByWerewolf.getSelectedItem().toString();

        for (Player player:this.players) {
            if (player.getName().equals(killedByWerewolfName)) {
                id = player.getId();
            }
        }
        return id;
    }

    public void getRole(View v) {
        int playerId = getSeerWatched();
        for (Player player:this.players) {
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
        killPlayers(conn.getApi().getPlayersDiedThisNight());

        Intent dayPeopleWakingUp = new Intent(this, DayPeopleWakingUpActivity.class);
        startActivity(dayPeopleWakingUp);
    }

    private void killPlayers(ArrayList<Player> playersDied) {
        for (Player playerDead:playersDied) {
            conn.getApi().killPlayer(playerDead);
        }
    }
}