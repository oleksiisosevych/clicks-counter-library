package com.oleksiisosevych.flickrimagesbrowsermvp.imagedetails;

import com.oleksiisosevych.flickrimagesbrowsermvp.BasePresenter;
import com.oleksiisosevych.flickrimagesbrowsermvp.BaseView;

/**
 * Interface that describes communication between view and presenter in image details screen
 */
interface ImageDetailsContract {
    interface View extends BaseView<Presenter> {
        void showPicture(String imageUrls);
    }

    interface Presenter extends BasePresenter {
    }
}
