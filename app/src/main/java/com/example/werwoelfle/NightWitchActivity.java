package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class NightWitchActivity extends AppCompatActivity {
    private GameStateConnection conn;
    private static final String LOG_TAG = NightWitchActivity.class.getName();

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_witch);

        conn = new GameStateConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                super.onServiceConnected(name, service);

                players = conn.getApi().getPlayersAlive();
                for (Player player : players) {
                    if (player.getRole() == Roles.WITCH) {
                        setKilledPlayerText(conn.getApi().getKilledByWerewolf());
                        setWitchHeal(conn.getApi().isHealedByWitch());

                        Integer preselected = conn.getApi().getKilledByWitch();
                        setPlayerDropdown(players, R.id.killedByWitch, preselected);
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

    private void setKilledPlayerText(int playerId) {
        Player playerKilled = null;
        for (Player player : this.players) {
            if (player.getId() == playerId) {
                playerKilled = player;
            }
        }

        if (playerKilled != null) {

            String wholePlayerText;

            if (GivePhoneToPlayerActivity.isNullOrEmpty(playerKilled.getName())) {
                String playerText = getApplicationContext().getString(R.string.give_phone_to_player, Integer.toString(playerKilled.getId()));
                wholePlayerText = getApplicationContext().getString(R.string.werGestorbenIst, playerText, playerKilled.getRole().getLabel(getApplicationContext()));
            } else {
                wholePlayerText = getApplicationContext().getString(R.string.werGestorbenIst, playerKilled.getName(), playerKilled.getRole().getLabel(getApplicationContext()));
            }

            TextView textView = findViewById(R.id.diedThisNight);
            textView.setText(wholePlayerText);
        }
    }

    private void setWitchHeal(boolean isHealed) {
        CheckBox healedByWitch = findViewById(R.id.healedByWitch);
        healedByWitch.setChecked(isHealed);
    }

    private boolean getWitchHeal() {
        CheckBox healedByWitch = findViewById(R.id.healedByWitch);
        return healedByWitch.isChecked();
    }

    private void setPlayerDropdown(ArrayList<Player> players, int spinnerId, Integer selectionId) {
        final Spinner spinner = findViewById(spinnerId);

        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            String playerText;
            if (GivePhoneToPlayerActivity.isNullOrEmpty(player.getName())) {
                playerText = getApplicationContext().getString(R.string.player_and_role, Integer.toString(player.getId()), player.getRole().getLabel(getApplicationContext()));
            } else {
                playerText = player.getName() + " / " + player.getRole().getLabel(getApplicationContext());
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

    private Integer getKilledByWitch() {
        CheckBox witchDontKill = findViewById(R.id.witchDontKill);
        if (witchDontKill.isChecked()) {
            return null;
        }
        Spinner killedByWitch = findViewById(R.id.killedByWitch);
        int killedByWitchId = killedByWitch.getSelectedItemPosition();

        return this.players.get(killedByWitchId).getId();
    }

    public void next(View v) {
        conn.getApi().setHealedByWitch(getWitchHeal());
        conn.getApi().setKilledByWitch(getKilledByWitch());
        activity();
    }

    private void activity() {
        Intent nightSeer = new Intent(this, NightSeerActivity.class);
        startActivity(nightSeer);
    }
}
