package com.example.werwoelfle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DayPeopleWakingUpActivity extends Activity {
    private GameStateConnection conn = new GameStateConnection();
    private static final String LOG_TAG = PlayerRoleAllocationActivity.class.getName();
    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_people_waking_up);

        bindService(new Intent("service"), conn, 0);

        this.players = conn.getApi().getPlayersAlive();

        ArrayList<Player> playersDied = conn.getApi().getPlayersDiedThisNight();
        if (checkHunter(playersDied)) {
            setHunterDropdown(this.players);
        }
        setDiedText(playersDied);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!conn.isServiceConnected()) {
            bindService(new Intent("service"), conn, 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(conn);
    }

    private void setHunterDropdown(ArrayList<Player> playersAlive) {
        setPlayerDropdown(playersAlive, R.id.hunter_kills, 0);
    }

    private boolean checkHunter(ArrayList<Player> playersDied) {
        for (Player player:playersDied) {
            if (player.getRole() == Roles.HUNTER) {
                return true;
            }
        }
        return false;
    }

    private void setDiedText(ArrayList<Player> playersDied) {
        String playersDiedText = null;
        for (Player playerDied:playersDied) {
            if (playerDied.getName() != null) {
                playersDiedText += playerDied.getName() + ": " + playerDied.getRole().getLabel(getApplicationContext()) + ", ";
            } else {
                playersDiedText += getApplicationContext().getString(R.string.give_phone_to_player, Integer.toString(playerDied.getId())) + ": " + playerDied.getRole().getLabel(getApplicationContext()) + ", ";
            }
        }

        TextView textView = findViewById(R.id.diedThisNight);
        textView.setText(getApplicationContext().getString(R.string.this_night_died, playersDiedText));
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

    private Integer getSpinnerSelected() {
        Integer id = null;
        Spinner killedByWerewolf = findViewById(R.id.hunter_kills);
        String killedByWerewolfName = killedByWerewolf.getSelectedItem().toString();

        for (Player player:this.players) {
            if (player.getName().equals(killedByWerewolfName)) {
                id = player.getId();
            }
        }
        return id;
    }

    public void next(View v) {
        int hunterKilled = getSpinnerSelected();
        Intent dayAccusingEachOther = new Intent(this, DayAccuingEachOtherActivity.class);
    }
}
