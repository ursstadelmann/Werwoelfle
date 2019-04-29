package com.example.werwoelfle;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class GameState extends Service {
    private static final String TAG = "Werewolf";
    private Game game;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind(), Service binded...");
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy(), Service stopped...");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onStart(Intent intent, int startId) {
        Log.d(TAG,"onStart(), Service started...");
        int players;
        ArrayList<String> names;
        ArrayList<Roles> roles;

        Bundle extras = intent.getExtras();

        if (extras != null) {
            players = extras.getInt("players");
            names = extras.getStringArrayList("names");
            roles = (ArrayList<Roles>) extras.getSerializable("roles");

            this.game = new Game(players, names, roles);
        } else {
            throw new NullPointerException("Extras was null!");
        }
    }

    public Game getGame() {
        return this.game;
    }
}
