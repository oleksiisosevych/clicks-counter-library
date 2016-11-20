package com.oleksiisosevych.flickrimagesbrowsermvp.categorydetails;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.ImagesDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.Category;

import java.util.List;

/**
 * Listens to user actions from the UI ({@link CategoryDetailsFragment}), retrieves the data and updates the
 * UI as required.
 */
public class CategoryDetailsPresenter implements CategoryDetailsContract.Presenter {

    private ImagesDataSource imagesDataSource;

    private CategoryDetailsContract.View view;

    private Category category;

    public CategoryDetailsPresenter(@NonNull ImagesDataSource imagesDataSource,
                                    @NonNull CategoryDetailsContract.View view,
                                    @NonNull Category category) {
        this.imagesDataSource = imagesDataSource;
        this.view = view;
        this.category = category;
        view.setPresenter(this);
    }

    @Override public void start() {
        loadPictures();
    }

    @Override public void loadPictures() {
        imagesDataSource.getImages(category.getName(), new ImagesDataSource.LoadImagesCallback() {
            @Override public void onImagesLoaded(List<String> imagesUrls) {
                view.showPictures(imagesUrls);
            }

            @Override public void onDataNotAvailable() {
                // TODO: Left out of scope for now, implement when real service will be used

            }
        });
    }

    @Override public void openPictureDetails(@NonNull String imageUrl) {
        view.showPictureDetails(imageUrl);
    }
}
