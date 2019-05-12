package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class RoleSelectionActivity extends AppCompatActivity {

    private GameStateConnection conn;
    private static final String LOG_TAG = RoleSelectionActivity.class.getName();

    Integer total_players;

    TextView leaderTv, werewolfTv, witchTv, seerTv, cupidTv, villagerTv, blinkTv, hunterTv, totalTv;

    Integer leader_count = 1, werewolf_count, witch_count, seer_count, cupid_count, villager_count, blink_count, hunter_count;

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

        leaderTv = findViewById(R.id.leader_count);
        werewolfTv = findViewById(R.id.werewolf_count);
        witchTv = findViewById(R.id.witch_count);
        seerTv = findViewById(R.id.seer_count);
        cupidTv = findViewById(R.id.cupid_count);
        villagerTv = findViewById(R.id.villager_count);
        blinkTv = findViewById(R.id.blink_count);
        hunterTv = findViewById(R.id.hunter_count);

        totalTv = findViewById(R.id.total_count);
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

    public void next(View v) {
        // Call RoleSelection Activity
        conn.getApi().setRoles(getRoles());

        Intent givePhoneToPlayer = new Intent(this, GivePhoneToPlayerActivity.class);
        givePhoneToPlayer.putExtra("player", 0);
        startActivity(givePhoneToPlayer);
    }

    private void initiateRoles(int players) {
        this.total_players = players;

        this.werewolf_count = players / 4;
        this.witch_count = 1;
        this.seer_count = 1;
        this.cupid_count = 1;
        this.villager_count = this.total_players - werewolf_count - witch_count - seer_count - cupid_count;

        Integer total = this.werewolf_count + this.witch_count + this.seer_count + this.cupid_count + this.villager_count + 1;

        this.leaderTv.setText(this.leader_count.toString());
        this.werewolfTv.setText(this.werewolf_count.toString());
        this.witchTv.setText(this.witch_count.toString());
        this.seerTv.setText(this.seer_count.toString());
        this.cupidTv.setText(this.cupid_count.toString());
        this.villagerTv.setText(this.villager_count.toString());
        this.totalTv.setText(total.toString());
    }

    private ArrayList<Roles> getRoles() {
        // TODO: get Roles and send them to service
        ArrayList<Roles> roles = new ArrayList<>();
        setRoleCount(roles, this.werewolf_count, Roles.WEREWOLF);
        setRoleCount(roles, this.witch_count, Roles.WITCH);
        setRoleCount(roles, this.cupid_count, Roles.CUPID);
        setRoleCount(roles, this.seer_count, Roles.SEER);
        setRoleCount(roles, this.villager_count, Roles.VILLAGER);

        return roles;
    }

    private ArrayList<Roles> setRoleCount(ArrayList<Roles> roles, int count, Roles role) {
        for (int i = 0; i < count; i++) {
            roles.add(role);
        }
        return roles;
    }

    public void werewolf_plus(View v) {

    }

    public void werewolf_minus(View v) {

    }

    public void hunter_plus(View v) {

    }

    public void hunter_minus(View v) {

    }

    public void villager_plus(View v) {

    }

    public void villager_minus(View v) {

    }

    public void blink_plus(View v) {

    }

    public void blink_minus(View v) {

    }
}
