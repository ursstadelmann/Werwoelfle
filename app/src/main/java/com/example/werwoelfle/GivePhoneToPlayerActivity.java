package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class GivePhoneToPlayerActivity extends AppCompatActivity {
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
                setPlayerName(player);
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

    private void setPlayerName(Player player) {
        String playerText;
        if (GivePhoneToPlayerActivity.isNullOrEmpty(player.getName())) {
            playerText = getApplicationContext().getString(R.string.give_phone_to_player, Integer.toString(player.getId()));
        } else {
            playerText = player.getName();
        }

        TextView textView = findViewById(R.id.give_phone_to_player);
        textView.setText(getApplicationContext().getString(R.string.give_phone_to, playerText));
    }

    public void next(View v) {
        Intent roleAllocation = new Intent(this, PlayerRoleAllocationActivity.class);
        roleAllocation.putExtra("player", this.player.getId());
        startActivity(roleAllocation);
    }

    @org.jetbrains.annotations.Contract("null -> true")
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
