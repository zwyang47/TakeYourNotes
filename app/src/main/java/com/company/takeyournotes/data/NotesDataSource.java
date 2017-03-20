package com.company.takeyournotes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msi on 2017/3/16.
 */

// This is our data source, first from device memory, then make it to database
public class NotesDataSource {
    /* Memory implementation
    private static TreeMap<Long, Note> noteMap = new TreeMap<>();

    public void saveNote(Note note) {
        if (noteMap.size() > 0) {
            Map.Entry<Long, Note> lastEntry = noteMap.lastEntry();
            long id = lastEntry.getKey() + 1;
            note.setId(id);
            noteMap.put(id, note);
        }
        else {
            noteMap.put(0L, note);
            note.setId(0L);
        }
    }

    public List<Note> getNotes() {
        return new ArrayList<>(noteMap.values());
    }

    public void deleteNote(long id) {
        noteMap.remove(id);
    }
    */

    // Database implementation
    private NotesDbHelper notesDbHelper;

    public NotesDataSource(Context context) {
        notesDbHelper = new NotesDbHelper(context);
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = notesDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotesDbContract.NotesEntry._ID, note.getId());
        values.put(NotesDbContract.NotesEntry.COLUMN_NAME_TITLE, note.getTitle());
        values.put(NotesDbContract.NotesEntry.COLUMN_NAME_CONTENT, note.getContent());

        db.update(NotesDbContract.NotesEntry.TABLE_NAME, values, "_id=" + note.getId(), null);
        db.close();
    }

    public void saveNote(Note note) {
        SQLiteDatabase db = notesDbHelper.getWritableDatabase();

        // ContentValues is actually a key-value map
        ContentValues values = new ContentValues();
        values.put(NotesDbContract.NotesEntry.COLUMN_NAME_TITLE, note.getTitle());
        values.put(NotesDbContract.NotesEntry.COLUMN_NAME_CONTENT, note.getContent());

        db.insert(NotesDbContract.NotesEntry.TABLE_NAME, null, values);
        db.close();
    }

    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();

        SQLiteDatabase db = notesDbHelper.getReadableDatabase();

        // Array of fields we want to retrieve from the database
        String[] projection = {
                NotesDbContract.NotesEntry._ID,
                NotesDbContract.NotesEntry.COLUMN_NAME_TITLE,
                NotesDbContract.NotesEntry.COLUMN_NAME_CONTENT,
        };

        // Because we want to have the newest note on the top of the list, we retrieve notes in ID descending order
        // Cursor is an interface that provides access to the result set from SQL query
        Cursor c = db.query(NotesDbContract.NotesEntry.TABLE_NAME, projection, null, null, null, null, "_ID DESC");

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Long id = c.getLong(c.getColumnIndexOrThrow(NotesDbContract.NotesEntry._ID));
                String title = c.getString(c.getColumnIndexOrThrow(NotesDbContract.NotesEntry.COLUMN_NAME_TITLE));
                String content = c.getString(c.getColumnIndexOrThrow(NotesDbContract.NotesEntry.COLUMN_NAME_CONTENT));
                Note note = new Note(id, title, content);
                notes.add(note);
            }
        }

        if (c != null) {
            c.close();
        }
        db.close();

        return notes;
    }

    public void deleteNote(long id) {
        SQLiteDatabase db = notesDbHelper.getWritableDatabase();

        String selection = NotesDbContract.NotesEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        db.delete(NotesDbContract.NotesEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}
