package com.example.werwoelfle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class NightWitchActivity extends Activity {
    private GameStateConnection conn = new GameStateConnection();
    private static final String LOG_TAG = PlayerRoleAllocationActivity.class.getName();

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_witch);

        bindService(new Intent(this, GameState.class), conn, 0);
        this.players = conn.getApi().getPlayersAlive();

        setKilledPlayerText(conn.getApi().getKilledByWerewolf());
        setWitchHeal(conn.getApi().isHealedByWitch());

        int preselected = conn.getApi().getKilledByWitch();

        setPlayerDropdown(this.players, R.id.killedByWitch, preselected);
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

    private Integer getKilledByWitch() {
        CheckBox witchDontKill = findViewById(R.id.witchDontKill);
        if (witchDontKill.isChecked()) {
            return null;
        }
        Integer id = null;
        Spinner killedByWitch = findViewById(R.id.killedByWitch);
        String killedByWitchName = killedByWitch.getSelectedItem().toString();

        for (Player player:this.players) {
            if (player.getName().equals(killedByWitchName)) {
                id = player.getId();
            }
        }
        return id;
    }

    public void next(View v) {
        conn.getApi().setHealedByWitch(getWitchHeal());
        conn.getApi().setKilledByWitch(getKilledByWitch());
        Intent nightSeer = new Intent(this, NightSeerActivity.class);
        startActivity(nightSeer);
    }
}
