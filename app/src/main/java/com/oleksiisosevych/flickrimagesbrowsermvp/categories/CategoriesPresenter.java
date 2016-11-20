package com.oleksiisosevych.flickrimagesbrowsermvp.categories;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.CategoriesDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.StatisticsDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.Category;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.EventStat;

import java.util.List;

/**
 * Listens to user actions from the UI ({@link CategoriesFragment}), retrieves the data and updates the
 * UI as required.
 */
public class CategoriesPresenter implements CategoriesContract.Presenter {

    private CategoriesDataSource categiesDataSource;

    private CategoriesContract.View view;

    private List<Category> categories;

    private StatisticsDataSource statisticsDataSource;

    public CategoriesPresenter(@NonNull StatisticsDataSource statisticsDataSource,
                               @NonNull CategoriesDataSource categiesDataSource,
                               @NonNull CategoriesContract.View view) {
        this.categiesDataSource = categiesDataSource;
        this.statisticsDataSource = statisticsDataSource;
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void loadCategories() {
        categiesDataSource.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override public void onCategoriesLoaded(List<Category> categories) {

                CategoriesPresenter.this.categories = categories;
                requestCategoryStats();
            }

            @Override public void onDataNotAvailable() {
                // implement case when no data is available, currently left out of scope
                throw new UnsupportedOperationException();
            }
        });
    }

    private void requestCategoryStats() {
        statisticsDataSource.getAllEventsStats(new StatisticsDataSource.LoadEventStatisticsCallback() {
            @Override public void onStatsLoaded(List<EventStat> stats) {
                onStatsReceived(stats);
            }

            @Override public void onDataNotAvailable() {
                view.showCategoriesList(categories);
            }
        });
    }

    private void onStatsReceived(List<EventStat> stats) {
        for (Category category : categories) {
            for (EventStat stat : stats) {
                if (category.getId().equals(stat.getEventId())) {
                    category.setClicksCount(stat.getCount());
                }
            }
        }
        view.showCategoriesList(categories);
    }

    @Override public void openCategoryDetails(@NonNull Category category) {
        statisticsDataSource.logEvent(category.getId());
        view.showCategoryDetailsUi(category);
    }

    @Override public void start() {
        loadCategories();
    }
}
