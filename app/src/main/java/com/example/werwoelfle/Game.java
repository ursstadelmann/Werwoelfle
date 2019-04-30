package com.example.werwoelfle;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private static final String LOG_TAG = Game.class.getName();
    final private ArrayList<Player> players = new ArrayList<>();
    final private Night night = new Night();
    private int dayCycle = 0;

    public Game() {
        Log.d(LOG_TAG, "Game initialized");
    }

    public void initializePlayers(int players) {
        if (this.players.size() > players) {
            this.players.clear();
        }
        for (int i = this.players.size(); i < players; i++) {
            this.players.add(new Player(i));
        }
    }

    public void setPlayerNames(ArrayList<String> names) {
        if (names.size() < this.players.size()) {
            throw new IllegalArgumentException("Insufficient names for players");
        } else {
            for (Player player:this.players) {
                player.setName(names.get(player.getId()));
                this.players.set(player.getId(),player);
            }
        }
    }

    public void setPlayerRoles(ArrayList<Roles> roles) {
        if (roles.size() < this.players.size()) {
            throw new IllegalArgumentException("Insufficient roles for players");
        } else {
            randomizeRoles(roles);
            for (Player player:this.players) {
                player.setRole(roles.get(player.getId()));
                this.players.set(player.getId(),player);
            }
        }
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
