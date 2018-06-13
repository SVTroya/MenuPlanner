package com.troya.menuplanner.model.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ingredients_in_recipes",
        foreignKeys = {
        @ForeignKey(entity = RecipeEntity.class, parentColumns = "_id", childColumns = "recipe_id"),
        @ForeignKey(entity = IngredientEntity.class, parentColumns = "_id", childColumns = "ingredient_id"),
        @ForeignKey(entity = UnitEntity.class, parentColumns = "_id", childColumns = "unit_id")})
public class IngredientInRecipeEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "recipe_id", index = true)
    private int recipeId;

    @ColumnInfo(name = "ingredient_id", index = true)
    private int ingredientId;

    private float amount;

    @ColumnInfo(name = "unit_id", index = true)
    private Integer unitId;

    private String instructions;

    public IngredientInRecipeEntity() {
    }

    // TODO: remove
    public IngredientInRecipeEntity(int recipeId, int ingredientId, float amount, Integer unitId, String instructions) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.unitId = unitId;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
