package com.troya.menuplanner.model.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.troya.menuplanner.model.db.entity.IngredientInRecipeEntity;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IngredientInRecipeDao {

    @Query("SELECT ir._id, ir.amount, ir.instructions as comment, i._id as ingr_id, " +
            "i.name as ingr_name, i.image_source, u._id as unit_id, u.name as unit_name " +
            "FROM ingredients_in_recipes AS ir " +
            "JOIN ingredients AS i ON ir.ingredient_id = i._id " +
            "LEFT JOIN units AS u ON ir.unit_id = u._id " +
            "WHERE ir.recipe_id = :recipeId")
    LiveData<List<IngredientInRecipeInfo>> getIngredientsByRecipeId(int recipeId);

    @Insert(onConflict = REPLACE)
    long addItem(IngredientInRecipeEntity ingredient);

    //TODO: replace (needed for dummy data)
    @Insert(onConflict = REPLACE)
    List<Long> addItems(List<IngredientInRecipeEntity> ingredient);

    @Delete
    void deleteItem(IngredientInRecipeEntity ingredient);

    @Query("DELETE FROM ingredients_in_recipes where _id IN (:ids)")
    void deleteItems(List<Integer> ids);
}
