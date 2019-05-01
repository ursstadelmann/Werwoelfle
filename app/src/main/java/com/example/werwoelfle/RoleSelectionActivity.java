package com.example.werwoelfle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class RoleSelectionActivity extends AppCompatActivity {

    private GameStateConnection conn = new GameStateConnection();
    private static final String LOG_TAG = RoleSelectionActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        bindService(new Intent("service"), conn, 0);
        //TODO: Wait until service is bound
        initiateRoles(conn.getApi().getPlayers().size());
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

    public void next(View v) {
        // Call RoleSelection Activity
        conn.getApi().setRoles(getRoles());

        Intent givePhoneToPlayer = new Intent(this, GivePhoneToPlayerActivity.class);
        givePhoneToPlayer.putExtra("player", 0);
        startActivity(givePhoneToPlayer);
    }

    private void initiateRoles(int players) {

    }

    private ArrayList<Roles> getRoles() {
        ArrayList<Roles> roles = new ArrayList<>();

        return roles;
    }
}
