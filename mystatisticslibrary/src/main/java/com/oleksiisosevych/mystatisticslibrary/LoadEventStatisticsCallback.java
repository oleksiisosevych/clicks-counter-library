package com.oleksiisosevych.mystatisticslibrary;

import com.oleksiisosevych.mystatisticslibrary.model.EventCount;

import java.util.List;

public interface LoadEventStatisticsCallback {
    void onStatsLoaded(List<EventCount> eventStats);

    void onDataNotAvailable();
}
