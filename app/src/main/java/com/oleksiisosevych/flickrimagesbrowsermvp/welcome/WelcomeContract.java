package com.oleksiisosevych.flickrimagesbrowsermvp.welcome;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.common.BasePresenter;
import com.oleksiisosevych.flickrimagesbrowsermvp.common.BaseView;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.Category;

/**
 * Interface that describes communication between view and presenter in welcome screen
 */
interface WelcomeContract {
    interface View extends BaseView<Presenter> {
        void showHottestCategory(@NonNull Category category);

        void navigateToCategoryDetails(@NonNull Category category);

        void navigateToCategoryList();
    }

    interface Presenter extends BasePresenter {
        void openCategoryList();

        void openCategory();
    }
}
