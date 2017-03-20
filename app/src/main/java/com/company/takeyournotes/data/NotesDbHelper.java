package com.company.takeyournotes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by msi on 2017/3/18.
 */

public class NotesDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Notes.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + NotesDbContract.NotesEntry.TABLE_NAME + " (" +
            NotesDbContract.NotesEntry._ID + " INTEGER PRIMARY KEY," +
            NotesDbContract.NotesEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            NotesDbContract.NotesEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + " )";

    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + NotesDbContract.NotesEntry.TABLE_NAME;

    public NotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
