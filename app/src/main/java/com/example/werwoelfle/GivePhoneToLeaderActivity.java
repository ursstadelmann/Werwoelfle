package com.example.werwoelfle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GivePhoneToLeaderActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_phone_back_to_leader);
        }

        public void next(View v) {
            Intent nightGoingToSleep = new Intent(this, NightGoingToSleepActivity.class);
            startActivity(nightGoingToSleep);
        }
}
