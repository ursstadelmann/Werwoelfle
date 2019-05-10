package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DayAccusingEachOtherActivity extends AppCompatActivity {
    private GameStateConnection conn;
    private static final String LOG_TAG = DayAccusingEachOtherActivity.class.getName();
    private ArrayList<Player> players;

    CountDownTimer cTimer = null;
    TextView countdown = null;

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
                setPlayerDropdown(players, R.id.day_killed, 0);
            }
        };

        bindService(new Intent(this, GameState.class), conn, 0);
        countdown = findViewById(R.id.timerCountdown);
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

    public void startTimer(View v) {
        cTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown.setText(millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                countdown.setText("Fertig");
            }
        };
        cTimer.start();
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
        Spinner killedByPollSpinner = findViewById(R.id.day_killed);
        int dayKilledId = killedByPollSpinner.getSelectedItemPosition();

        return this.players.get(dayKilledId);
    }

    public void next(View v) {
        conn.getApi().killPlayer(getSpinnerSelected());
        conn.getApi().endOfDay();
        Intent nightWerewolfActivity = new Intent(this, NightWerewolfActivity.class);
        startActivity(nightWerewolfActivity);
    }
}
