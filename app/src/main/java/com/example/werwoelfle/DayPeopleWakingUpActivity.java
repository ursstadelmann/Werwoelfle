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

                killPlayers(conn.getApi().getPlayersDiedThisNight());


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
        bindService(new Intent(this, GameState.class), conn, 0);
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
        for (Player player : playersDied) {
            if (player.getRole() == Roles.HUNTER) {
                return true;
            }
        }
        return false;
    }

    private void setDiedText(ArrayList<Player> playersDied) {
        String playersDiedText = "";
        for (Player playerDied : playersDied) {
            if (GivePhoneToPlayerActivity.isNullOrEmpty(playerDied.getName())) {
                playersDiedText += getApplicationContext().getString(R.string.give_phone_to_player, Integer.toString(playerDied.getId())) + ": " + playerDied.getRole().getLabel(getApplicationContext()) + ", \n";
            } else {
                playersDiedText += playerDied.getName() + ": " + playerDied.getRole().getLabel(getApplicationContext()) + ", \n";
            }
        }

        TextView textView = findViewById(R.id.diedThisNight);
        textView.setText(getApplicationContext().getString(R.string.this_night_died, playersDiedText));
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

    private Player getSpinnerSelected() {
        Player playerKilled = null;
        Spinner killedByWerewolf = findViewById(R.id.hunter_kills);
        String killedByWerewolfName = killedByWerewolf.getSelectedItem().toString();

        for (Player player : this.players) {
            if (player.getName().equals(killedByWerewolfName)) {
                playerKilled = player;
            }
        }
        return playerKilled;
    }
    
    private void killPlayers(ArrayList<Player> playersDied) {
        for (Player playerDead : playersDied) {
            conn.getApi().killPlayer(playerDead);
        }
    }

    public void next(View v) {
        ArrayList<Player> playersDied = conn.getApi().getPlayersDiedThisNight();
        if (checkHunter(playersDied)) {
            Player hunterKilled = getSpinnerSelected();
            conn.getApi().killPlayer(hunterKilled);
        }

        Intent dayAccusingEachOther = new Intent(this, DayAccusingEachOtherActivity.class);
        startActivity(dayAccusingEachOther);
    }
}
