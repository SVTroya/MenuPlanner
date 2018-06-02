package com.troya.menuplanner.model.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import com.troya.menuplanner.model.db.entity.IngredientInRecipeEntity;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IngredientInRecipeDao {

    /*@Query("SELECT * FROM IngredientInRecipeEntity WHERE recipeId = :recipeId")
    LiveData<List<IngredientInfo>> getIngredientsByRecipeId(int recipeId);*/

    @Insert(onConflict = REPLACE)
    long addItem(IngredientInRecipeEntity ingredient);

    @Delete
    void deleteItem(IngredientInRecipeEntity ingredient);
}
