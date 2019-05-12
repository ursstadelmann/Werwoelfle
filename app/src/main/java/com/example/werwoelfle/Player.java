package com.example.werwoelfle;

import android.support.annotation.NonNull;

public class Player {
    private int id;
    private String name;
    private Roles role;
    private boolean alive = true;
    private boolean inLove = false;

    public Player(int id, String name, Roles role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isInLove() {
        return inLove;
    }

    public void setInLove(boolean inLove) {
        this.inLove = inLove;
    }

    @NonNull
    @Override
    public String toString() {
        // A value you want to be displayed in the spinner item.
        return getName() + " / " + getRole();
    }
}
