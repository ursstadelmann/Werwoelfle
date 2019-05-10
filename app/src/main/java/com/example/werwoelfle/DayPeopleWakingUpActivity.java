package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DayPeopleWakingUpActivity extends AppCompatActivity {
    private GameStateConnection conn;
    private static final String LOG_TAG = DayPeopleWakingUpActivity.class.getName();
    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_people_waking_up);

        conn = new GameStateConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                super.onServiceConnected(name, service);

                players = conn.getApi().getPlayersAlive();

                ArrayList<Player> playersDied = conn.getApi().getPlayersDiedThisNight();
                if (checkHunter(playersDied)) {
                    setHunterDropdown(players);
                }
                setDiedText(playersDied);
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

    private Player getSpinnerSelected() {
        Player playerKilled = null;
        Spinner killedByWerewolf = findViewById(R.id.hunter_kills);
        String killedByWerewolfName = killedByWerewolf.getSelectedItem().toString();

        for (Player player:this.players) {
            if (player.getName().equals(killedByWerewolfName)) {
                playerKilled = player;
            }
        }
        return playerKilled;
    }

    public void next(View v) {
        Player hunterKilled = getSpinnerSelected();
        conn.getApi().killPlayer(hunterKilled);

        Intent dayAccusingEachOther = new Intent(this, DayAccusingEachOtherActivity.class);
        startActivity(dayAccusingEachOther);
    }
}
