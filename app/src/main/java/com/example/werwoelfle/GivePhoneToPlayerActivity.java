package com.example.werwoelfle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GivePhoneToPlayerActivity extends Activity {
    int player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_phone_to_player);

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
    }

    public void next(View v) {
        Intent roleAllocation = new Intent(this, PlayerRoleAllocationActivity.class);
        roleAllocation.putExtra("player", this.player);
        startActivity(roleAllocation);
    }
}
