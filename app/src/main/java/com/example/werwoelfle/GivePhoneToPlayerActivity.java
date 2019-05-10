package com.example.werwoelfle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

public class GivePhoneToPlayerActivity extends Activity {
    private GameStateConnection conn;
    private static final String LOG_TAG = GivePhoneToPlayerActivity.class.getName();

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_phone_to_player);

        conn = new GameStateConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                super.onServiceConnected(name, service);
                Bundle extras = getIntent().getExtras();

                // Get Name from Service
                player = conn.getApi().getPlayers().get(extras.getInt("player"));
                setText(player.getName());
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

    private void setText(String playerName) {
        String playerText;
        if (GivePhoneToPlayerActivity.isNullOrEmpty(playerName)) {
            playerText = getApplicationContext().getString(R.string.give_phone_to_player, playerName);
        } else {
            playerText = playerName;
        }

        TextView textView = findViewById(R.id.give_phone_to_player);
        textView.setText(getApplicationContext().getString(R.string.give_phone_to, playerText));
    }

    public void next(View v) {
        Intent roleAllocation = new Intent(this, PlayerRoleAllocationActivity.class);
        roleAllocation.putExtra("player", this.player.getId());
        startActivity(roleAllocation);
    }

    @org.jetbrains.annotations.Contract("null -> false")
    public static boolean isNullOrEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
