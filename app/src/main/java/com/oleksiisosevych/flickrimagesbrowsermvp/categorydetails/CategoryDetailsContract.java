package com.oleksiisosevych.flickrimagesbrowsermvp.categorydetails;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.common.BasePresenter;
import com.oleksiisosevych.flickrimagesbrowsermvp.common.BaseView;

import java.util.List;

/**
 * Interface that describes communication between view and presenter in Category details screen with the
 * grid of 12 images from selected category
 */
interface CategoryDetailsContract {
    interface View extends BaseView<Presenter> {
        void showPictures(List<String> imageUrls);

        void showPictureDetails(@NonNull String imageUrl);
    }

    interface Presenter extends BasePresenter {
        void loadPictures();

        void openPictureDetails(@NonNull String imageUrl);
    }
}
