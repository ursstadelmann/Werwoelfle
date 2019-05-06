package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class GameStateConnection implements ServiceConnection {

    private GameState.GameStateApiInterface api = null;

    public GameState.GameStateApiInterface getApi() {
        return api;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.api = (GameState.GameStateApiInterface) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        api = null;
    }

    public boolean isServiceConnected() {
        return (api != null);
    }
}
