package com.example.werwoelfle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class GameState extends Service {
    private static final String LOG_TAG = GameState.class.getName();
    private Game game;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG,"onBind(), Service binded...");
        return new GameStateApi();
    }

    public interface IServiceApi {
        void setPlayers(int players);
        void setNames(ArrayList<String> names);
        void setRoles(ArrayList<Roles> roles);
        ArrayList<Player> getPlayers();
    }

    public class GameStateApi extends Binder implements IServiceApi   {

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

        @Override
        public ArrayList<Player> getPlayers() {
            return GameState.this.getGame().getPlayers();
        }
    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG,"onCreate(), Service started...");
        super.onCreate();
        this.game = new Game();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"onDestroy(), Service stopped...");
        super.onDestroy();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onStart(Intent intent, int startId) {
        Log.d(LOG_TAG,"onStart(), Service started...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public Game getGame() {
        return this.game;
    }
}
