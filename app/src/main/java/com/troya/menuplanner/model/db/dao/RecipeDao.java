package com.troya.menuplanner.model.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.troya.menuplanner.model.views.RecipeInfo;
import com.troya.menuplanner.model.db.entity.RecipeEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeEntity>> getAll();

    @Transaction
    @Query("SELECT _id, name, source, image, rating FROM recipes")
    LiveData<List<RecipeInfo>> getAllExt();

    @Query("SELECT _id, name, image, result_amount, result_unit_id, comment, source, rating " +
            "FROM recipes " +
            "INNER JOIN recipes_and_categories ON recipes._id = recipes_and_categories.recipe_id " +
            "WHERE category_id IN (:categoryIds)")
    LiveData<List<RecipeEntity>> getRecipesCategories(List<Integer> categoryIds);

    @Query("SELECT * FROM recipes WHERE _id = :id")
    LiveData<RecipeEntity> getOneById(int id);

    @Update
    int updateItem(RecipeEntity recipe);

    @Insert(onConflict = REPLACE)
    long addItem(RecipeEntity recipe);


    //TODO: replace (needed for dummy data)
    @Insert(onConflict = REPLACE)
    List<Long> addItems(List<RecipeEntity> recipes);

    @Delete
    void deleteItem(RecipeEntity recipe);

    @Query("UPDATE recipes SET name = :newName WHERE _id = :id")
    void updateName(int id, String newName);
}
