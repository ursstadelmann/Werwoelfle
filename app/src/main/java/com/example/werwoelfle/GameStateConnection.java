package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class GameStateConnection implements ServiceConnection {

    private GameState.IServiceApi api;

    public GameState.IServiceApi getApi() {
        return api;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.api = (GameState.IServiceApi) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        api = null;
    }
}
