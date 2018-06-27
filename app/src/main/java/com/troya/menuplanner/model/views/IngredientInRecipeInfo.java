package com.troya.menuplanner.model.views;

import android.arch.persistence.room.ColumnInfo;
import android.util.Log;

public class IngredientInRecipeInfo {

    private static final String TAG = IngredientInRecipeInfo.class.getSimpleName();

    @ColumnInfo(name = "_id")
    private Integer id;
    @ColumnInfo(name = "ingr_id")
    private Integer ingredientId;
    @ColumnInfo(name = "ingr_name")
    private String ingredientName;
    @ColumnInfo(name = "image_source")
    private String imageSource;
    private Float amount;
    @ColumnInfo(name = "unit_id")
    private Integer unitId;
    @ColumnInfo(name = "unit_name")
    private String unitName;
    private String comment;

    public Integer getId() {
        return id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getImageSource() {
        return imageSource;
    }

    public Float getAmount() {
        return amount;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getComment() {
        return comment;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IngredientInRecipeInfo) {
            IngredientInRecipeInfo ingredient = (IngredientInRecipeInfo) obj;
            if (!this.getId().equals(ingredient.getId())
                    || !this.getComment().equals(ingredient.getComment())
                    || !this.getAmount().equals(ingredient.getAmount())
                    || !this.getIngredientId().equals(ingredient.getIngredientId())
                    || !this.getIngredientName().equals(ingredient.getIngredientName())
                    || !this.getUnitId().equals(ingredient.getUnitId())
                    || !this.getUnitName().equals(ingredient.getUnitName())) {
                return false;
            }
            return true;
        } else {
            Log.e(TAG, "equals: Incorrect type of object: " + obj.getClass().getSimpleName());
            return false;
        }
    }
}
