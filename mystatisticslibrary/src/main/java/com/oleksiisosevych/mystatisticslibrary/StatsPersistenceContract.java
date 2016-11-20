
package com.oleksiisosevych.mystatisticslibrary;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the stats locally.
 */
final class StatsPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private StatsPersistenceContract() {
    }

    /* Inner class that defines the table contents */
    static abstract class StatEntry implements BaseColumns {
        static final String TABLE_NAME = "categories";
        static final String COLUMN_NAME_EVENT_ID = "eventId";
        static final String COLUMN_NAME_COUNT = "count";
    }
}
