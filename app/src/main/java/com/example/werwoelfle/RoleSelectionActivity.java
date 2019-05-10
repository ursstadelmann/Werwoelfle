package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class RoleSelectionActivity extends AppCompatActivity {

    private GameStateConnection conn;
    private static final String LOG_TAG = RoleSelectionActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        conn = new GameStateConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                super.onServiceConnected(name, service);
                initiateRoles(conn.getApi().getPlayers().size());
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

    public void next(View v) {
        // Call RoleSelection Activity
        conn.getApi().setRoles(getRoles());

        Intent givePhoneToPlayer = new Intent(this, GivePhoneToPlayerActivity.class);
        givePhoneToPlayer.putExtra("player", 0);
        startActivity(givePhoneToPlayer);
    }

    private void initiateRoles(int players) {
        // TODO: Initiate Roles
    }

    private ArrayList<Roles> getRoles() {
        // TODO: get Roles and send them to service
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(Roles.WEREWOLF);
        roles.add(Roles.WEREWOLF);
        roles.add(Roles.WITCH);
        roles.add(Roles.CUPID);
        roles.add(Roles.SEER);
        roles.add(Roles.VILLAGER);

        return roles;
    }
}
