package com.oleksiisosevych.flickrimagesbrowsermvp.data;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.EventStat;

import java.util.List;

/**
 * Interface for recording events and get overall statistics
 */
public interface StatisticsDataSource {
    void logEvent(String eventId);

    void getAllEventsStats(LoadEventStatisticsCallback callback);

    interface LoadEventStatisticsCallback {

        void onStatsLoaded(List<EventStat> stats);

        void onDataNotAvailable();
    }
}
