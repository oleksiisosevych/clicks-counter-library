package com.oleksiisosevych.flickrimagesbrowsermvp.router;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oleksiisosevych.flickrimagesbrowsermvp.categories.CategoriesActivity;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.StatisticsDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.local.StatsLibraryDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.EventStat;
import com.oleksiisosevych.flickrimagesbrowsermvp.welcome.WelcomeActivity;

import java.util.List;

/**
 * Starting point for our application that will decide what first activity show to user
 */
public class RouterActivity extends AppCompatActivity {

    private StatisticsDataSource statisticsDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        statisticsDataSource = StatsLibraryDataSource.getInstance(getApplicationContext());
        statisticsDataSource.getAllEventsStats(new StatisticsDataSource.LoadEventStatisticsCallback() {
            @Override public void onStatsLoaded(List<EventStat> stats) {
                Intent intent;
                if (stats != null && stats.size() > 0) {
                    intent = WelcomeActivity.getLaunchIntent(RouterActivity.this);
                } else {
                    intent = CategoriesActivity.getLaunchIntent(RouterActivity.this);
                }
                startActivity(intent);
                finish();
            }

            @Override public void onDataNotAvailable() {
                Intent intent = CategoriesActivity.getLaunchIntent(RouterActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }
}
