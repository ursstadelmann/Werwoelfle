package com.example.werwoelfle;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "groups")
public class Groups {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupId")
    private String groupId;

    @ColumnInfo(name = "groupName")
    private String groupName;

    @ColumnInfo(name = "numberOfPlayers")
    private Integer numberOfPlayers;

    @ColumnInfo(name = "groupMemberName")
    private String groupMemberName;

    @ColumnInfo(name = "blinzli")
    private Boolean blinzli;

    @Ignore
    public Groups(String groupName, Integer numberOfPlayers, String groupMemberName, Boolean blinzli) {
        this.groupId = UUID.randomUUID().toString();
        this.groupName = groupName;
        this.numberOfPlayers = numberOfPlayers;
        this.groupMemberName = groupMemberName;
        this.blinzli = blinzli;
    }

    public Groups(String groupId, String groupName, Integer numberOfPlayers, String groupMemberName, Boolean blinzli) {
        this.groupMemberName = groupId;
        this.groupName = groupName;
        this.numberOfPlayers = numberOfPlayers;
        this.groupMemberName = groupMemberName;
        this.blinzli = blinzli;
    }

    public String getGroupId(){
        return this.groupId;
    }

    public void setGroupId(String groupId){
        this.groupId=groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getGroupMemberName() {
        return groupMemberName;
    }

    public void setGroupMemberName(String groupMemberName) {
        this.groupMemberName = groupMemberName;
    }

    public Boolean getBlinzli() {
        return blinzli;
    }

    public void setBlinzli(Boolean blinzli) {
        this.blinzli = blinzli;
    }

}
