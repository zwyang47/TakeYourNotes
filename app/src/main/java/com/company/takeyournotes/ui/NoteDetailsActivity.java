package com.company.takeyournotes.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.company.takeyournotes.R;
import com.company.takeyournotes.data.Note;
import com.company.takeyournotes.data.NotesDataSource;

public class NoteDetailsActivity extends AppCompatActivity {
    // The key passed to this activity
    public static final String NOTE_EXTRA = "NOTE_EXTRA";

    private static final int NOTE_EDIT_ACTIVITY_REQUEST_CODE = 1;

    private NotesDataSource notesDataSource;
    private Note note;
    private TextView titleTextView;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        notesDataSource = new NotesDataSource(this);

        titleTextView = (TextView) findViewById(R.id.activity_note_details_title);
        contentTextView = (TextView) findViewById(R.id.activity_note_details_content);

        note = getIntent().getParcelableExtra(NOTE_EXTRA);

        setNoteFields();
    }

    private void setNoteFields() {
        titleTextView.setText(note.getTitle());
        contentTextView.setText(note.getContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_note_details_delete:
                showDeleteDialog();
                return true;
            case R.id.menu_note_details_edit:
                Intent intent = new Intent(getApplicationContext(), NoteEditActivity.class);
                intent.putExtra(NoteEditActivity.NOTE_EXTRA, note);
                startActivityForResult(intent, NOTE_EDIT_ACTIVITY_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDeleteDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.note_delete_dialog_title)
                .setMessage(R.string.note_delete_dialog_message)
                .setPositiveButton(R.string.note_delete_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notesDataSource.deleteNote(note.getId());
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton(R.string.note_delete_dialog_negative_button, null)
                .create();
        alertDialog.show();
    }
}
