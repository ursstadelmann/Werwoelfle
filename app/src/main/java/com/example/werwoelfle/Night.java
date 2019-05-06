package com.example.werwoelfle;

public class Night {
    private Integer nightId;
    private Integer killedByWerewolf;
    private Integer killedByWitch;
    private boolean healedByWitch = false;
    private Integer seerWatched;

    public int getKilledByWerewolf() {
        return killedByWerewolf;
    }

    public void setKilledByWerewolf(int killedByWerewolf) {
        this.killedByWerewolf = killedByWerewolf;
    }

    public void clearNight(){
        killedByWerewolf = null;
        killedByWitch = null;
        healedByWitch = false;
        seerWatched = null;
    }

    public Integer getNightId() {
        return nightId;
    }

    public void setNightId(Integer night) {
        this.nightId = night;
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
