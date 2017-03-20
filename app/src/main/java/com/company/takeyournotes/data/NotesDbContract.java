package com.company.takeyournotes.data;

import android.provider.BaseColumns;

/**
 * Created by msi on 2017/3/18.
 */
// This class describe the schema of our database
public final class NotesDbContract {
    // Make sure no one can extend this class
    private NotesDbContract() {
    }

    public static final class NotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
