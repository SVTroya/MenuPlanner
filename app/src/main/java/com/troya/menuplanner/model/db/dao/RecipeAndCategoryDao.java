package com.troya.menuplanner.model.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeAndCategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;

import java.util.List;

@Dao
public interface RecipeAndCategoryDao {

    @Query("SELECT _id, name, image, result_amount, result_unit_id, comment, source, rating FROM recipes " +
            "INNER JOIN recipes_and_categories ON recipes_and_categories.recipe_id = recipes._id " +
            "WHERE recipes_and_categories.category_id = :categoryId")
    LiveData<List<RecipeEntity>> getRecipesByCategoryId(int categoryId);

    @Query("SELECT _id, name, color FROM categories, recipes_and_categories " +
            "WHERE recipes_and_categories.recipe_id = categories._id AND recipe_id = :recipeId")
    LiveData<List<CategoryEntity>> getCategoriesByRecipeId(int recipeId);

    @Insert
    long addItems(RecipeAndCategoryEntity binding);

    //TODO: replace (needed for dummy data)
    @Insert
    List<Long> addItems(List<RecipeAndCategoryEntity> bindings);
}
