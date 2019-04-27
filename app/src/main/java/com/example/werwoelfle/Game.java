package com.example.werwoelfle;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private static final String TAG = "Werewolf";
    final private ArrayList<Player> players = new ArrayList<>();
    final private Night night = new Night();

    public Game(int players, ArrayList<String> names, ArrayList<Roles> roles) {
        randomizeRoles(roles);
        for (int i = 0; i <= players; i++) {
            this.players.add(new Player(i, names.get(i), roles.get(i)));
        }
        Log.d(TAG, "Game initialized with " + players + " players");
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Night getNight() {
        return night;
    }

    private void randomizeRoles(ArrayList<Roles> roles) {
        Collections.shuffle(roles);
    }
}
