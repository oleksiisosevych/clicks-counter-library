package com.oleksiisosevych.flickrimagesbrowsermvp.categories;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.common.BasePresenter;
import com.oleksiisosevych.flickrimagesbrowsermvp.common.BaseView;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.Category;

import java.util.List;

/**
 * Interface that describes communication between view and presenter in Categories screen
 */
interface CategoriesContract {
    interface View extends BaseView<Presenter> {
        void showCategoriesList(List<Category> categoryList);

        void showCategoryDetailsUi(@NonNull Category category);
    }

    interface Presenter extends BasePresenter {
        void loadCategories();

        void openCategoryDetails(@NonNull Category category);
    }
}
