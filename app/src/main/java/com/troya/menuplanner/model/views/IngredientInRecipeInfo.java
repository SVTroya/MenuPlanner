package com.troya.menuplanner.model.views;

import android.arch.persistence.room.ColumnInfo;

public class IngredientInRecipeInfo {
    @ColumnInfo(name = "_id")
    private int id;
    @ColumnInfo(name = "ingr_name")
    private String ingredientName;
    @ColumnInfo(name = "image_source")
    private String imageSource;
    private Float amount;
    @ColumnInfo(name = "unit_name")
    private String unitName;
    private String comment;

    public int getId() {
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

    public void setId(int id) {
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
}
