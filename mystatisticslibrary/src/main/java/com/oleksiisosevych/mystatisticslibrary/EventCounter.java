
package com.oleksiisosevych.mystatisticslibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.oleksiisosevych.mystatisticslibrary.model.EventCount;

import java.util.ArrayList;
import java.util.List;


/**
 * Concrete implementation of a data source as a db.
 */
public class EventCounter {

    private static EventCounter INSTANCE;

    private StatsDbHelper dbHelper;

    // Prevent direct instantiation.
    private EventCounter(@NonNull Context context) {
        dbHelper = new StatsDbHelper(context);
    }

    public static EventCounter getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new EventCounter(context);
        }
        return INSTANCE;
    }

    public void logEvent(String eventId) {
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

    public void getEventsStats(LoadEventStatisticsCallback callback) {
        List<EventCount> eventStats = new ArrayList<>();
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
                EventCount eventStat = new EventCount(eventId, count);
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
