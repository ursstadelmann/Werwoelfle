package com.example.werwoelfle;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "groups")
public class Groups {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "groupId")
    private Integer groupId;

    @ColumnInfo(name = "groupName")
    private String groupName;

    @ColumnInfo(name = "numberOfPlayers")
    private Integer numberOfPlayers;

    @ColumnInfo(name = "groupMemberName")
    private String groupMemberName;

    @Ignore
    public Groups(String groupName, Integer numberOfPlayers, String groupMemberName) {
        this.groupName = groupName;
        this.numberOfPlayers = numberOfPlayers;
        this.groupMemberName = groupMemberName;
    }

    public Groups(Integer groupId, String groupName, Integer numberOfPlayers, String groupMemberName) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.numberOfPlayers = numberOfPlayers;
        this.groupMemberName = groupMemberName;
    }

    public Integer getGroupId(){
        return this.groupId;
    }

    public void setGroupId(Integer groupId){
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

    @NonNull
    @Override
    public String toString() {
        // A value you want to be displayed in the spinner item.
        return getGroupId() + ": " + getGroupName() + ", " + getNumberOfPlayers() + " + 1 Erzähler";
    }
}
