
package com.oleksiisosevych.flickrimagesbrowsermvp.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class StatsDbHelper extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "Stats.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER_TYPE";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StatsPersistenceContract.StatEntry.TABLE_NAME + " (" +
                    StatsPersistenceContract.StatEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    StatsPersistenceContract.StatEntry.COLUMN_NAME_EVENT_ID + TEXT_TYPE + COMMA_SEP +
                    StatsPersistenceContract.StatEntry.COLUMN_NAME_COUNT + INTEGER_TYPE +
                    " )";

    StatsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
