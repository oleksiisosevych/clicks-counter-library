package com.oleksiisosevych.flickrimagesbrowsermvp.data.local;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.StatisticsDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.EventStat;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of statistics data source based on local database storage
 */
public class DummyStatisticsDataSource implements StatisticsDataSource {
    private static DummyStatisticsDataSource INSTANCE;


    // Prevent direct instantiation.
    private DummyStatisticsDataSource() {
    }

    public static DummyStatisticsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DummyStatisticsDataSource();
        }
        return INSTANCE;
    }

    @Override public void logEvent(String eventId) {

    }

    @Override
    public void getAllEventsStats(LoadEventStatisticsCallback callback) {
        List<EventStat> res = new ArrayList<>();
        res.add(new EventStat("1", 1));
        callback.onStatsLoaded(res);
    }
}
