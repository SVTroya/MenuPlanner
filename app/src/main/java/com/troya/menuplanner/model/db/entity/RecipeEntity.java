package com.troya.menuplanner.model.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "recipes")
public class RecipeEntity implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @NonNull
    private String name;

    private byte[] image;

    @ColumnInfo(name = "result_amount")
    private Float resultAmount;

    @ColumnInfo(name = "result_unit_id")
    private Integer resultUnitId;

    private String comment;
    private String source;
    private float rating = 1;

    public RecipeEntity() {
    }

    @Ignore
    public RecipeEntity(int id) {
        this.id = id;
    }

    @Ignore
    public RecipeEntity(@NonNull String name) {
        this.name = name;
    }


    //TODO: remove - needed for Dummy Data
    @Ignore
    public RecipeEntity(@NonNull String name, byte[] image, String source, float rating) {
        this.name = name;
        this.image = image;
        this.source = source;
        this.rating = rating;
    }


    protected RecipeEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.createByteArray();
        if (in.readByte() == 0) {
            resultAmount = null;
        } else {
            resultAmount = in.readFloat();
        }
        if (in.readByte() == 0) {
            resultUnitId = null;
        } else {
            resultUnitId = in.readInt();
        }
        comment = in.readString();
        source = in.readString();
        rating = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByteArray(image);
        if (resultAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(resultAmount);
        }
        if (resultUnitId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(resultUnitId);
        }
        dest.writeString(comment);
        dest.writeString(source);
        dest.writeFloat(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeEntity> CREATOR = new Creator<RecipeEntity>() {
        @Override
        public RecipeEntity createFromParcel(Parcel in) {
            return new RecipeEntity(in);
        }

        @Override
        public RecipeEntity[] newArray(int size) {
            return new RecipeEntity[size];
        }
    };

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Float getResultAmount() {
        return resultAmount;
    }

    public void setResultAmount(Float resultAmount) {
        this.resultAmount = resultAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Integer getResultUnitId() {
        return resultUnitId;
    }

    public void setResultUnitId(Integer resultUnitId) {
        this.resultUnitId = resultUnitId;
    }
}
