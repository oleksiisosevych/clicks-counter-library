/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oleksiisosevych.flickrimagesbrowsermvp.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.StatisticsDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.EventStat;
import com.oleksiisosevych.mystatisticslibrary.EventCounter;
import com.oleksiisosevych.mystatisticslibrary.model.EventCount;

import java.util.ArrayList;
import java.util.List;


/**
 * Concrete implementation of a data source as a db.
 */
public class StatsLibraryDataSource implements StatisticsDataSource {
    private static StatsLibraryDataSource INSTANCE;
    private EventCounter eventCounter;

    // Prevent direct instantiation.
    private StatsLibraryDataSource(@NonNull Context context) {
        eventCounter = EventCounter.getInstance(context);
    }

    public static StatsLibraryDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new StatsLibraryDataSource(context);
        }
        return INSTANCE;
    }

    @Override public void logEvent(String eventId) {
        eventCounter.logEvent(eventId);
    }

    @Override
    public void getAllEventsStats(final LoadEventStatisticsCallback callback) {
        eventCounter.getEventsStats(new com.oleksiisosevych.mystatisticslibrary.LoadEventStatisticsCallback() {
            @Override
            public void onStatsLoaded(List<EventCount> eventCounts) {
                List<EventStat> eventStatList = new ArrayList<>();
                for (EventCount aEventCount : eventCounts) {
                    eventStatList.add(new EventStat(aEventCount.getEventId(), aEventCount.getCount()));
                }
                callback.onStatsLoaded(eventStatList);
            }

            @Override public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
}
