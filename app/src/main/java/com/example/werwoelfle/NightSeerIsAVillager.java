package com.example.werwoelfle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class NightSeerIsAVillager extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_seer_is_a_villager);
    }

    public void ok(View v) {
        finish();
    }
}
