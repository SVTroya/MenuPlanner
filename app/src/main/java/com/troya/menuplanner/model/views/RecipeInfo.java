package com.troya.menuplanner.model.views;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

import com.troya.menuplanner.model.db.entity.RecipeAndCategoryEntity;

import java.util.List;

public class RecipeInfo {

    @ColumnInfo(name = "_id")
    private int id;
    private String name;
    private byte[] image;
    private String source;
    private int rating;

    @Relation(projection = "category_id", parentColumn = "_id", entityColumn = "recipe_id", entity = RecipeAndCategoryEntity.class)
    private List<Integer> categoriesId;

    public RecipeInfo() {
    }

    @Ignore
    public RecipeInfo(int id, String name, byte[] image, String source, int rating, List<Integer> categoriesId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.source = source;
        this.rating = rating;
        this.categoriesId = categoriesId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Integer> getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(List<Integer> categoriesId) {
        this.categoriesId = categoriesId;
    }
}
