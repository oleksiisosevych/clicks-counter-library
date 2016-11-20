package com.oleksiisosevych.flickrimagesbrowsermvp.imagedetails;

import com.oleksiisosevych.flickrimagesbrowsermvp.common.BasePresenter;
import com.oleksiisosevych.flickrimagesbrowsermvp.common.BaseView;

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
