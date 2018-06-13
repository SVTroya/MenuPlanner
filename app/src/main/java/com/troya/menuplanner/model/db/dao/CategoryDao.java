package com.troya.menuplanner.model.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.troya.menuplanner.model.db.entity.CategoryEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntity>> getAll();

    @Query("SELECT * FROM categories WHERE _id = :id")
    LiveData<CategoryEntity> getOneById(int id);

    @Insert(onConflict = REPLACE)
    long addItems(CategoryEntity category);

    //TODO: replace (needed for dummy data)
    @Insert(onConflict = REPLACE)
    List<Long> addItems(List<CategoryEntity> categories);

    @Delete
    void deleteItem(CategoryEntity category);
}
