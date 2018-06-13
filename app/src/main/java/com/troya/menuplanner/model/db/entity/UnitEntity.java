package com.troya.menuplanner.model.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "units",
        indices = {@Index(value = "name", unique = true)})
public class UnitEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @NonNull
    private String name;

    public UnitEntity() {
    }

    // TODO: remove
    public UnitEntity(@NonNull String name) {
        this.name = name;
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
}
