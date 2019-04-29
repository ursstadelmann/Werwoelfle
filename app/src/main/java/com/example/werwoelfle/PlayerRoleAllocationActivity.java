package com.example.werwoelfle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlayerRoleAllocationActivity extends Activity {
   int player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_role);

        Bundle extras = getIntent().getExtras();
        this.player = extras.getInt("player");
        setText(this.player);
    }

    private void setText(int player) {
        // Get Name from Service
        //if name.isEmpty()

        String PLAYER_TEXT = getApplicationContext().getString(R.string.give_phone_to_player, Integer.toString(player));

        TextView textView = findViewById(R.id.give_phone_to_player);
        textView.setText(getApplicationContext().getString(R.string.give_phone_to, PLAYER_TEXT));

        //if name not empty set name

        //getRole from service
        String playerRole = Roles.WEREWOLF.getLabel(getApplicationContext());
        TextView playerRoleTV = findViewById(R.id.player_role);
        playerRoleTV.setText(playerRole);
    }

    public void next(View v) {
        //if this.player < Service.getPlayers() {
        Intent givePhoneToPlayerActivity = new Intent(this, GivePhoneToPlayerActivity.class);
        givePhoneToPlayerActivity.putExtra("player", this.player++);
        startActivity(givePhoneToPlayerActivity);
        /*} else {
        going to sleep activity
        */
    }
}
