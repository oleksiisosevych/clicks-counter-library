package com.oleksiisosevych.flickrimagesbrowsermvp.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Main entry point for searching images given search query
 */
public interface ImagesDataSource {

    void getImages(@NonNull String searchQuery, @NonNull LoadImagesCallback callback);

    interface LoadImagesCallback {

        void onImagesLoaded(List<String> imagesUrls);

        void onDataNotAvailable();
    }

}
