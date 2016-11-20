package com.oleksiisosevych.flickrimagesbrowsermvp.data;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.Category;

import java.util.List;

/**
 * Main entry point for accessing categories.json data
 */
public interface CategoriesDataSource {

    void getCategories(@NonNull LoadCategoriesCallback callback);

    interface LoadCategoriesCallback {

        void onCategoriesLoaded(List<Category> categories);

        void onDataNotAvailable();
    }

}
