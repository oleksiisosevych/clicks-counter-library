package com.oleksiisosevych.flickrimagesbrowsermvp.data.local;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.ImagesDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation data source that gets its data without making network calls, just what you
 * need in case you are in the airplane or want to run unit tests
 */
public class ImagesStubDataSource implements ImagesDataSource {

    private static ImagesStubDataSource INSTANCE;

    // Prevent direct instantiation.
    private ImagesStubDataSource() {
    }

    public static ImagesStubDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImagesStubDataSource();
        }
        return INSTANCE;
    }

    @Override public void getImages(
            @NonNull String searchQuery, @NonNull LoadImagesCallback callback) {
        List<String> stubResult = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            // Add a lot of Donald images
            stubResult.add("http://c10.nrostatic.com/sites/default/files/styles/original_image_with_" +
                    "cropping/public/uploaded/donald-trump-grow-up.jpg?itok=n1PW3Myr");
        }
        callback.onImagesLoaded(stubResult);
    }
}
