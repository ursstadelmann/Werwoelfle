package com.example.werwoelfle;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private static final String TAG = "Werewolf";
    final private ArrayList<Player> players = new ArrayList<>();
    private Night night = new Night();
    private int dayCycle = 0;

    public Game(int players, ArrayList<String> names, ArrayList<Roles> roles) {
        randomizeRoles(roles);
        for (int i = 0; i < players; i++) {
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

    public void clearInLove() {
        for (final Player player:players) {
            player.setInLove(false);
        }
    }

    public void setInLove(int playerId) {
        for (final Player player:players) {
            if (player.getId() == playerId) {
                player.setInLove(true);
            }
        }
    }

    public void endOfDay() {
        this.dayCycle++;
    }

    private void randomizeRoles(ArrayList<Roles> roles) {
        Collections.shuffle(roles);
    }
}
