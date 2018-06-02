package com.troya.menuplanner.model.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.troya.menuplanner.model.IIngredient;

@Entity(tableName = "ingredients",
        foreignKeys = @ForeignKey(entity = IngredientGroupEntity.class,
                                  parentColumns = "_id",
                                  childColumns = "group_id"),
        indices = {@Index(value = "name", unique = true)})
public class IngredientEntity implements IIngredient {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @NonNull
    private String name;

    @ColumnInfo(name = "group_id", index = true)
    private Integer groupId;

    @ColumnInfo(name = "image_source")
    private String imageSource;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}
