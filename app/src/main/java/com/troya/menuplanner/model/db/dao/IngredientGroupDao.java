package com.troya.menuplanner.model.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.troya.menuplanner.model.db.entity.IngredientGroupEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IngredientGroupDao {

    @Query("SELECT * FROM ingredient_groups")
    LiveData<List<IngredientGroupEntity>> getAll();

    @Query("SELECT _id FROM ingredient_groups WHERE name = :name")
    long getIdByName(String name);

    @Insert(onConflict = REPLACE)
    long addItem(IngredientGroupEntity group);

    @Delete
    void deleteItem(IngredientGroupEntity group);
}
