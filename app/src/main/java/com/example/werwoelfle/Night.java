package com.example.werwoelfle;

public class Night {
    private Integer nightId = null;
    private Integer killedByWerewolf = null;
    private Integer killedByWitch = null;
    private boolean healedByWitch = false;
    private Integer seerWatched = null;

    public Integer getKilledByWerewolf() {
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

    public Integer getKilledByWitch() {
        return killedByWitch;
    }

    public void setKilledByWitch(Integer killedByWitch) {
        this.killedByWitch = killedByWitch;
    }

    public boolean isHealedByWitch() {
        return healedByWitch;
    }

    public void setHealedByWitch(boolean healedByWitch) {
        this.healedByWitch = healedByWitch;
    }

    public Integer getSeerWatched() {
        return seerWatched;
    }

    public void setSeerWatched(Integer seerWatched) {
        this.seerWatched = seerWatched;
    }
}
