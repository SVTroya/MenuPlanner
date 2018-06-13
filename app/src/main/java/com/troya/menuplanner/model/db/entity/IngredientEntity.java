package com.troya.menuplanner.model.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ingredients",
        foreignKeys = @ForeignKey(entity = IngredientGroupEntity.class,
                                  parentColumns = "_id",
                                  childColumns = "group_id"),
        indices = {@Index(value = "name", unique = true)})
public class IngredientEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @NonNull
    private String name;

    @ColumnInfo(name = "group_id", index = true)
    private Integer groupId;

    @ColumnInfo(name = "image_source")
    private String imageSource;

    public IngredientEntity() {
    }

    // TODO: remove
    public IngredientEntity(@NonNull String name, String imageSource) {
        this.name = name;
        this.imageSource = imageSource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}
