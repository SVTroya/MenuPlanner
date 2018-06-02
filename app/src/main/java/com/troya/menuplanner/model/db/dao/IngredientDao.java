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

    @Query("SELECT _id FROM ingredients WHERE group_id = :groupId")
    LiveData<List<Long>> getIngredientsIdByGroupId(Integer groupId);

    @Query("SELECT * FROM ingredients WHERE _id = :id")
    LiveData<IngredientEntity> getOneById(int id);

    @Insert(onConflict = REPLACE)
    long addItem(IngredientEntity ingredient);

    @Delete
    void deleteItem(IngredientEntity ingredient);
}