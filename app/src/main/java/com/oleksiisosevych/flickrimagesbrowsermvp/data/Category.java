package com.oleksiisosevych.flickrimagesbrowsermvp.data;

import android.support.annotation.NonNull;

public final class Category {

    private String id;

    private String name;

    private String imageUrl;

    private int clicksCount;

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
}
