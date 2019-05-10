package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class PlayerRoleAllocationActivity extends AppCompatActivity {
    private GameStateConnection conn;
    private static final String LOG_TAG = PlayerRoleAllocationActivity.class.getName();

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_role_allocation);

        conn = new GameStateConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                super.onServiceConnected(name, service);

                Bundle extras = getIntent().getExtras();

                // Get Player from Service
                player = conn.getApi().getPlayers().get(extras.getInt("player"));
                setPlayerName(player);
                setPlayerRole(player.getRole());
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

    private void setPlayerName(Player player) {
        String playerText;
        if (GivePhoneToPlayerActivity.isNullOrEmpty(player.getName())) {
            playerText = getApplicationContext().getString(R.string.give_phone_to_player, Integer.toString(player.getId()));
        } else {
            playerText = player.getName();
        }

        TextView textView = findViewById(R.id.give_phone_to_player);
        textView.setText(playerText);
    }

    private void setPlayerRole(Roles role) {
        //getRole from service
        String playerRole = role.getLabel(getApplicationContext());
        TextView playerRoleTV = findViewById(R.id.player_role);
        playerRoleTV.setText(playerRole);
    }

    public void next(View v) {
        if (conn == null) return;

        if (this.player.getId() + 1 < conn.getApi().getPlayers().size()) {
            Intent givePhoneToPlayerActivity = new Intent(this, GivePhoneToPlayerActivity.class);
            givePhoneToPlayerActivity.putExtra("player", this.player.getId() + 1);
            startActivity(givePhoneToPlayerActivity);
        } else {
            // Game start
            conn.getApi().newNight();
            Intent nightGoingToSleep = new Intent(this, NightGoingToSleepActivity.class);
            startActivity(nightGoingToSleep);
        }
    }
}
