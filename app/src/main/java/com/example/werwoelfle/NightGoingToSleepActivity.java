package com.example.werwoelfle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NightGoingToSleepActivity extends Activity {
    private static final String LOG_TAG = PlayerRoleAllocationActivity.class.getName();

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
