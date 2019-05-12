package com.example.werwoelfle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NightSeerIsAWerewolf extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_seer_is_a_werewolf);
    }

    public void ok(View v) {
        finish();
    }
}
