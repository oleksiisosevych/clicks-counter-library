package com.oleksiisosevych.flickrimagesbrowsermvp.imagedetails;

import android.support.annotation.NonNull;

/**
 * Listens to user actions from the UI ({@link ImageDetailsFragment}), retrieves the data and updates the
 * UI as required.
 */
public class ImageDetailsPresenter implements ImageDetailsContract.Presenter {

    private ImageDetailsContract.View view;

    private String imageUrl;

    public ImageDetailsPresenter(@NonNull ImageDetailsContract.View view,
                                 @NonNull String imageUrl) {
        this.view = view;
        this.imageUrl = imageUrl;
        view.setPresenter(this);
    }

    @Override public void start() {
        view.showPicture(imageUrl);
    }
}

