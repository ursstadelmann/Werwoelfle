package com.example.werwoelfle;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

@Dao
public interface GroupsDao {

    @Query("SELECT * FROM groups")
    List<Groups> getAll();

    @Query("SELECT * FROM groups WHERE groupId = :groupIds")
    Groups getById(Integer groupIds);

    @Insert
    void insertAll(Groups...groups);

    @Delete
    void delete(Groups oneGroup);

}



