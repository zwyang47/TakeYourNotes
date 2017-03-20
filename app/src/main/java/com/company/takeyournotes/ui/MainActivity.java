package com.company.takeyournotes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.company.takeyournotes.R;
import com.company.takeyournotes.data.Note;
import com.company.takeyournotes.data.NotesDataSource;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private static final int NOTE_DETAILS_ACTIVITY_REQUEST_CODE = 2;

    private RecyclerView recyclerView;
    private NotesListAdapter notesListAdapter;
    private NotesDataSource notesDataSource;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get all stored notes in our database when the app is opened
        notesDataSource = new NotesDataSource(this);
        notes = notesDataSource.getNotes();

        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);

        // LayoutManager presents content vertically in the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        notesListAdapter = new NotesListAdapter(notes, new OnNoteSelectedListener() {
            @Override
            public void onNoteSelected(Note note) {
                // Start note details activity
                Intent intent = new Intent(getApplicationContext(), NoteDetailsActivity.class);
                intent.putExtra(NoteDetailsActivity.NOTE_EXTRA, note);
                startActivityForResult(intent, NOTE_DETAILS_ACTIVITY_REQUEST_CODE);
            }
        });
        recyclerView.setAdapter(notesListAdapter);

        // Divider line between items in the RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_main_add_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivityIntent = new Intent(getApplicationContext(), AddNoteActivity.class);
                // Notify from AddNoteActivity that the user has clicked the done button
                // Request code refer to what kind of operation has been done
                startActivityIntent.putExtra("s", new Intent(getApplicationContext(), NoteDetailsActivity.class));
                startActivityForResult(startActivityIntent, ADD_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check if the decoration is successful, RESULT_OK -> successful, RESULT_CANCEL -> failed
        if (RESULT_OK == resultCode) {
            if (ADD_NOTE_ACTIVITY_REQUEST_CODE == requestCode || NOTE_DETAILS_ACTIVITY_REQUEST_CODE == requestCode) {
                reloadNotesAndScrollUp();
            }
        }
    }

    private void reloadNotesAndScrollUp() {
        notes = notesDataSource.getNotes();
        notesListAdapter.setNoteList(notes);
        recyclerView.smoothScrollToPosition(0);
    }
}
