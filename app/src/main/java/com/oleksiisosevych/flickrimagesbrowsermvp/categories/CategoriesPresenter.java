package com.oleksiisosevych.flickrimagesbrowsermvp.categories;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.CategoriesDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.Category;

import java.util.List;

/**
 * Listens to user actions from the UI ({@link CategoriesFragment}), retrieves the data and updates the
 * UI as required.
 */
public class CategoriesPresenter implements CategoriesContract.Presenter {

    private CategoriesDataSource categiesDataSource;

    private CategoriesContract.View view;

    public CategoriesPresenter(@NonNull CategoriesDataSource categiesDataSource,
                               @NonNull CategoriesContract.View view) {
        this.categiesDataSource = categiesDataSource;
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void loadCategories() {
        categiesDataSource.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override public void onCategoriesLoaded(List<Category> categories) {
                view.showCategoriesList(categories);
            }

            @Override public void onDataNotAvailable() {
                // implement case when no data is available, currently left out of scope
                throw new UnsupportedOperationException();
            }
        });
    }

    @Override public void openCategoryDetails(@NonNull Category category) {
        view.showCategoryDetailsUi(category);
    }

    @Override public void start() {
        loadCategories();
    }
}
