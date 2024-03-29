package com.example.werwoelfle;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private static final String LOG_TAG = Game.class.getName();
    final private ArrayList<Player> players = new ArrayList<>();
    final private ArrayList<Night> nights = new ArrayList<>();
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
        Log.d(LOG_TAG, "Player initialized");
    }

    public void setPlayerNames(ArrayList<String> names) {
        if (names.size() < this.players.size()) {
            throw new IllegalArgumentException("Insufficient names for players");
        } else {
            for (Player player : this.players) {
                player.setName(names.get(player.getId()));
                this.players.set(player.getId(), player);
            }
        }
        Log.d(LOG_TAG, "Player names set");
    }

    public void setPlayerRoles(ArrayList<Roles> roles) {
        if (roles.size() < this.players.size()) {
            throw new IllegalArgumentException("Insufficient roles for players");
        } else {
            randomizeRoles(roles);
            for (Player player : this.players) {
                player.setRole(roles.get(player.getId()));
                this.players.set(player.getId(), player);
            }
        }
        Log.d(LOG_TAG, "Player roles set");
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getPlayersAlive() {
        ArrayList<Player> playersAlive = new ArrayList<>();
        for (Player player : players) {
            if (player.isAlive()) {
                playersAlive.add(player);
            }
        }
        return playersAlive;
    }

    public ArrayList<Player> getPlayersDiedThisNight() {
        ArrayList<Player> playersDied = new ArrayList<>();
        Night night = getNight(getDayCycle());
        boolean inLove = false;
        for (Player player : getPlayers()) {
            if (!night.isHealedByWitch() && player.getId() == night.getKilledByWerewolf() || night.getKilledByWitch() != null && player.getId() == night.getKilledByWitch()) {
                if (player.isInLove()) {
                    inLove = true;
                } else {
                    playersDied.add(player);
                }
            }
        }

        if (inLove) {
            ArrayList<Integer> loveIds = getLoveIds();
            for (int id : loveIds) {
                playersDied.add(getPlayers().get(id));
            }
        }

        return playersDied;
    }

    public ArrayList<Integer> getLoveIds() {
        ArrayList<Integer> loveIds = new ArrayList<>();
        for (Player player : getPlayers()) {
            if (player.isInLove()) {
                loveIds.add(player.getId());
            }
        }
        return loveIds;
    }

    public Night getNight(int night) {
        return this.getNights().get(night);
    }

    private ArrayList<Night> getNights() {
        return nights;
    }

    public int getDayCycle() {
        return dayCycle;
    }

    public void newNight() {
        //If Night got already created
        for (Night night : nights) {
            if (night.getNightId().equals(this.dayCycle)) {
                return;
            }
        }

        Night night = new Night();
        night.setNightId(this.dayCycle);
        nights.add(night);
        Log.d(LOG_TAG, "Next night");
    }

    public void clearInLove() {
        for (final Player player : players) {
            player.setInLove(false);
        }
    }

    public void setInLove(ArrayList<Player> inLovePlayers) {
        if (this.getInLove().size() + inLovePlayers.size() > 2) {
            this.clearInLove();
        }
        if (inLovePlayers.size() <= 2) {
            for (Player player : inLovePlayers) {
                player.setInLove(true);
                this.getPlayers().set(player.getId(), player);
            }
        }
    }

    public ArrayList<Player> getInLove() {
        ArrayList<Player> inLove = new ArrayList<>();
        for (final Player player : players) {
            if (player.isInLove()) {
                inLove.add(player);
            }
        }
        return inLove;
    }

    public void endOfDay() {
        this.dayCycle++;
        Log.d(LOG_TAG, "End of day");
    }

    private void randomizeRoles(ArrayList<Roles> roles) {
        Collections.shuffle(roles);
    }

    public void killPlayer(Player killPlayer) {
        killPlayer.setAlive(false);
        this.getPlayers().set(killPlayer.getId(), killPlayer);
    }
}
