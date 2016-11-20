package com.oleksiisosevych.flickrimagesbrowsermvp.welcome;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.CategoriesDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.StatisticsDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.Category;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.EventStat;

import java.util.Collections;
import java.util.List;

/**
 * Listens to user actions from the UI ({@link WelcomeFragment}), retrieves the data and updates the
 * UI as required.
 */
public class WelcomePresenter implements WelcomeContract.Presenter {

    private WelcomeContract.View view;
    private StatisticsDataSource statisticsDataSource;
    private CategoriesDataSource categoriesDataSource;
    private Category hottestCategory;
    private List<Category> categories;

    public WelcomePresenter(@NonNull CategoriesDataSource categoriesDataSource,
                            @NonNull StatisticsDataSource statisticsDataSource,
                            @NonNull WelcomeContract.View view) {
        this.categoriesDataSource = categoriesDataSource;
        this.statisticsDataSource = statisticsDataSource;
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void start() {
        requestCategories();
    }

    private void requestCategories() {
        categoriesDataSource.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override public void onCategoriesLoaded(List<Category> categories) {
                WelcomePresenter.this.categories = categories;
                requestCategoryStats();
            }

            @Override public void onDataNotAvailable() {
                // not implemented in current scope
            }
        });
    }

    private void requestCategoryStats() {
        statisticsDataSource.getAllEventsStats(new StatisticsDataSource.LoadEventStatisticsCallback() {
            @Override public void onStatsLoaded(List<EventStat> stats) {
                onStatsReceived(stats);
            }

            @Override public void onDataNotAvailable() {
                // not implemented in current scope

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
        Collections.sort(categories);
        hottestCategory = categories.get(0);
        view.showHottestCategory(hottestCategory);
    }

    @Override public void openCategoryList() {
        view.navigateToCategoryList();
    }

    @Override public void openCategory() {
        view.navigateToCategoryDetails(hottestCategory);
    }

}

