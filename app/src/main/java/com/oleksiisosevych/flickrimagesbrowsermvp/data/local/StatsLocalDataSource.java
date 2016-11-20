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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.StatisticsDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.EventStat;

import java.util.ArrayList;
import java.util.List;


/**
 * Concrete implementation of a data source as a db.
 */
public class StatsLocalDataSource implements StatisticsDataSource {

    private static StatsLocalDataSource INSTANCE;

    private StatsDbHelper dbHelper;

    // Prevent direct instantiation.
    private StatsLocalDataSource(@NonNull Context context) {
        dbHelper = new StatsDbHelper(context);
    }

    public static StatsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new StatsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override public void logEvent(String eventId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                StatsPersistenceContract.StatEntry.COLUMN_NAME_COUNT
        };
        String selection = StatsPersistenceContract.StatEntry.COLUMN_NAME_EVENT_ID + " LIKE ?";
        String[] selectionArgs = {eventId};
        Cursor c = db.query(
                StatsPersistenceContract.StatEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean eventExists = false;
        int currentCount = 0;
        if (c != null && c.getCount() == 1) {
            c.moveToNext();
            currentCount = c.getInt(c.getColumnIndexOrThrow(StatsPersistenceContract.StatEntry.COLUMN_NAME_COUNT));
            eventExists = true;
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (eventExists) {
            updateCount(eventId, currentCount + 1);
        } else {
            insetCount(eventId, 1);
        }
    }

    private void updateCount(String eventId, int count) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StatsPersistenceContract.StatEntry.COLUMN_NAME_COUNT, count);

        String selection = StatsPersistenceContract.StatEntry.COLUMN_NAME_EVENT_ID + " LIKE ?";
        String[] selectionArgs = {eventId};

        db.update(StatsPersistenceContract.StatEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    private void insetCount(String eventId, int count) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StatsPersistenceContract.StatEntry.COLUMN_NAME_EVENT_ID, eventId);
        values.put(StatsPersistenceContract.StatEntry.COLUMN_NAME_COUNT, count);

        db.insert(StatsPersistenceContract.StatEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void getAllEventsStats(LoadEventStatisticsCallback callback) {
        List<EventStat> eventStats = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                StatsPersistenceContract.StatEntry.COLUMN_NAME_EVENT_ID,
                StatsPersistenceContract.StatEntry.COLUMN_NAME_COUNT
        };

        Cursor c = db.query(
                StatsPersistenceContract.StatEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String eventId = c.getString(c.getColumnIndexOrThrow(StatsPersistenceContract.StatEntry.COLUMN_NAME_EVENT_ID));
                int count = c.getInt(c.getColumnIndexOrThrow(StatsPersistenceContract.StatEntry.COLUMN_NAME_COUNT));
                EventStat eventStat = new EventStat(eventId, count);
                eventStats.add(eventStat);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (eventStats.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onStatsLoaded(eventStats);
        }
    }
}
