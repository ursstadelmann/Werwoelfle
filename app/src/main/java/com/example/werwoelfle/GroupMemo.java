package com.example.werwoelfle;

public class GroupMemo {

    private String groupName;
    private int groupSize;
    private String groupMemberName;
    private boolean wantsBlinlzli;
    private long id;


    public GroupMemo(String groupName, int groupSize, String groupMemberName, boolean wantsBlinlzli) {
        this.groupName = groupName;
        this.groupMemberName = groupMemberName;
        this.groupSize = groupSize;
        this.wantsBlinlzli = wantsBlinlzli;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getGroupMemberName() {
        return groupMemberName;
    }

    public void setGroupMemberName(String groupMemberName) {
        this.groupMemberName = groupMemberName;
    }

    public boolean isWantsBlinlzli() {
        return wantsBlinlzli;
    }

    public void setWantsBlinlzli(boolean wantsBlinlzli) {
        this.wantsBlinlzli = wantsBlinlzli;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String output = groupName + groupSize;
        return output;
    }
}