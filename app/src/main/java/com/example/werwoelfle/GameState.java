package com.example.werwoelfle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
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
        return new ServiceApi();
    }

    public interface IServiceApi {
        void setPlayers(int players);
        void setNames(ArrayList<String> names);
        void setRoles(ArrayList<Roles> roles);
    }

    public class ServiceApi extends Binder implements IServiceApi   {

        @Override
        public void setPlayers(int players) {
            GameState.this.getGame().initializePlayers(players);
        }

        @Override
        public void setNames(ArrayList<String> names) {
            GameState.this.getGame().setPlayerNames(names);
        }

        @Override
        public void setRoles(ArrayList<Roles> roles) {
            GameState.this.getGame().setPlayerRoles(roles);
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate(), Service started...");
        super.onCreate();
        this.game = new Game();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy(), Service stopped...");
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG,"onStart(), Service started...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public Game getGame() {
        return this.game;
    }
}
