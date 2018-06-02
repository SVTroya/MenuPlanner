package com.troya.menuplanner.model.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;

@Entity(tableName = "recipes_and_categories",
        primaryKeys = {"recipe_id", "category_id"},
        foreignKeys = {
                @ForeignKey(entity = RecipeEntity.class, parentColumns = "_id", childColumns = "recipe_id"),
                @ForeignKey(entity = CategoryEntity.class, parentColumns = "_id", childColumns = "category_id")})
public class RecipeAndCategoryEntity {

    @ColumnInfo(name = "recipe_id")
    private int recipeId;

    @ColumnInfo(name = "category_id", index = true)
    private int categoryId;

    public RecipeAndCategoryEntity() {
    }

    @Ignore
    public RecipeAndCategoryEntity(int recipeId, int categoryId) {
        this.recipeId = recipeId;
        this.categoryId = categoryId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
