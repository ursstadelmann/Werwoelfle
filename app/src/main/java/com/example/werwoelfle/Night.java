package com.example.werwoelfle;

public class Night {
    private int killedByWerewolf;
    private int killedByWitch;
    private boolean healedByWitch = false;
    private int seerWatched;

    public int getKilledByWerewolf() {
        return killedByWerewolf;
    }

    public void setKilledByWerewolf(int killedByWerewolf) {
        this.killedByWerewolf = killedByWerewolf;
    }

    public int getKilledByWitch() {
        return killedByWitch;
    }

    public void setKilledByWitch(int killedByWitch) {
        this.killedByWitch = killedByWitch;
    }

    public boolean isHealedByWitch() {
        return healedByWitch;
    }

    public void setHealedByWitch(boolean healedByWitch) {
        this.healedByWitch = healedByWitch;
    }

    public int getSeerWatched() {
        return seerWatched;
    }

    public void setSeerWatched(int seerWatched) {
        this.seerWatched = seerWatched;
    }
}
