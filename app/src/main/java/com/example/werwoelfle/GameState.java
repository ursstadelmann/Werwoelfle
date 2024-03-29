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

    public interface GameStateApiInterface {
        void setPlayers(int players);
        void setNames(ArrayList<String> names);
        void setRoles(ArrayList<Roles> roles);
        void setInLove(ArrayList<Player> inLovePlayers);
        void newNight();
        void endOfDay();

        void killPlayer(Player killPlayer);
        ArrayList<Player> getPlayers();
        ArrayList<Player> getPlayersAlive();
        ArrayList<Player> getInLove();
        Integer getKilledByWerewolf();
        Integer getKilledByWitch();
        boolean isHealedByWitch();
        Integer getSeerWatched();
        ArrayList<Player> getPlayersDiedThisNight();
        void setKilledByWerewolf(int killedByWerewolf);
        void setHealedByWitch(boolean isHealed);
        void setKilledByWitch(Integer killedByWitch);
        void setSeerWatched(int seerWatched);
    }

    public class GameStateApi extends Binder implements GameStateApiInterface {

        @Override
        public void setPlayers(int players) {
            GameState.this.getGame().initializePlayers(players);
        }

        @Override
        public ArrayList<Player> getPlayers() {
            return GameState.this.getGame().getPlayers();
        }

        @Override
        public ArrayList<Player> getPlayersAlive() {
            return GameState.this.getGame().getPlayersAlive();
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
        public void setInLove(ArrayList<Player> inLovePlayers){
            GameState.this.getGame().setInLove(inLovePlayers);
        }

        @Override
        public void newNight() {
            GameState.this.getGame().newNight();
        }

        @Override
        public void endOfDay() {
            GameState.this.getGame().endOfDay();
            newNight();
        }

        @Override
        public void killPlayer(Player killPlayer) {
            GameState.this.getGame().killPlayer(killPlayer);
        }

        @Override
        public ArrayList<Player> getInLove() {
            return GameState.this.getGame().getInLove();
        }

        @Override
        public Integer getKilledByWerewolf() {
            return GameState.this.getGame().getNight(GameState.this.getGame().getDayCycle()).getKilledByWerewolf();
        }

        @Override
        public Integer getKilledByWitch() {
            return GameState.this.getGame().getNight(GameState.this.getGame().getDayCycle()).getKilledByWitch();
        }

        @Override
        public boolean isHealedByWitch() {
            return GameState.this.getGame().getNight(GameState.this.getGame().getDayCycle()).isHealedByWitch();
        }

        @Override
        public Integer getSeerWatched() {
            return GameState.this.getGame().getNight(GameState.this.getGame().getDayCycle()).getSeerWatched();
        }

        @Override
        public ArrayList<Player> getPlayersDiedThisNight() {
            return GameState.this.getGame().getPlayersDiedThisNight();
        }

        @Override
        public void setKilledByWerewolf(int killedByWerewolf) {
            GameState.this.getGame().getNight(GameState.this.getGame().getDayCycle()).setKilledByWerewolf(killedByWerewolf);
        }

        @Override
        public void setHealedByWitch(boolean isHealed) {
            GameState.this.getGame().getNight(GameState.this.getGame().getDayCycle()).setHealedByWitch(isHealed);
        }

        @Override
        public void setKilledByWitch(Integer killedByWitch) {
            GameState.this.getGame().getNight(GameState.this.getGame().getDayCycle()).setKilledByWitch(killedByWitch);
        }

        @Override
        public void setSeerWatched(int seerWatched) {
            GameState.this.getGame().getNight(GameState.this.getGame().getDayCycle()).setSeerWatched(seerWatched);
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
