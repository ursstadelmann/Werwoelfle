package com.example.werwoelfle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NightGoingToSleepActivity extends AppCompatActivity {
    private static final String LOG_TAG = NightGoingToSleepActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_going_to_sleep);
    }

    public void next(View v) {
        Intent nightCupid = new Intent(this, NightCupidActivity.class);
        startActivity(nightCupid);
    }
}
