package com.example.werwoelfle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Werewolf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "MainActivity: onResume()");
        super.onResume();
    }

    public void next(View v) {
        Log.d(TAG, "MainActivity: next()");
        // Get Player Count
        Spinner playerDropdown = (Spinner) findViewById(R.id.playerCount);
        int players = Integer.parseInt(playerDropdown.getSelectedItem().toString());

        // Create Group SignUp Intent
        Intent groupSignUpActivityIntent = new Intent(this, GroupSignUpActivity.class);
        groupSignUpActivityIntent.putExtra("players", players);

        startActivity(groupSignUpActivityIntent);
    }
}
