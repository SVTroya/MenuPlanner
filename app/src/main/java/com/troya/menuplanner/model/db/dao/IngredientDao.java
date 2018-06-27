package com.troya.menuplanner.model.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.troya.menuplanner.model.db.entity.IngredientEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredients")
    LiveData<List<IngredientEntity>> getAll();

    @Query("SELECT name FROM ingredients")
    List<String> getAllNames();

    @Query("SELECT _id FROM ingredients WHERE group_id = :groupId")
    LiveData<List<Long>> getIngredientsIdByGroupId(Integer groupId);

    @Query("SELECT * FROM ingredients WHERE _id = :id")
    LiveData<IngredientEntity> getOneById(int id);

    @Query("SELECT _id FROM ingredients WHERE name = :name")
    int getIdByName(String name);

    @Insert(onConflict = REPLACE)
    long addItem(IngredientEntity ingredient);

    //TODO: replace (needed for dummy data)
    @Insert(onConflict = REPLACE)
    List<Long> addItems(List<IngredientEntity> ingredient);

    @Delete
    void deleteItem(IngredientEntity ingredient);
}
