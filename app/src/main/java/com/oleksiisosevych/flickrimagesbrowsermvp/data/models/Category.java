package com.oleksiisosevych.flickrimagesbrowsermvp.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public final class Category implements Parcelable, Comparable<Category> {

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    private String id;
    private String name;
    private String imageUrl;
    private int clicksCount;

    public Category() {
    }

    protected Category(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.clicksCount = in.readInt();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getClicksCount() {
        return clicksCount;
    }

    public void setClicksCount(int clicksCount) {
        this.clicksCount = clicksCount;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.clicksCount);
    }

    @Override public int compareTo(Category other) {
        if (other.getClicksCount() == this.getClicksCount()) {
            return this.getId().compareTo(other.getId());
        }

        return ((Integer) other.clicksCount).compareTo(this.clicksCount);
    }
}
