package com.troya.menuplanner.model.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.troya.menuplanner.model.db.entity.UnitEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UnitDao {

    @Query("SELECT * FROM units")
    LiveData<List<UnitEntity>> getAll();

    @Query("SELECT name FROM units")
    List<String> getAllNames();

    @Query("SELECT _id FROM units WHERE name = :name")
    int getIdByName(String name);

    @Query("SELECT name FROM units WHERE _id = :id")
    String getNameById(int id);

    @Insert(onConflict = REPLACE)
    long addItem(UnitEntity unit);

    //TODO: replace (needed for dummy data)
    @Insert(onConflict = REPLACE)
    List<Long> addItems(List<UnitEntity> unit);

    @Delete
    void deleteItem(UnitEntity unit);
}
