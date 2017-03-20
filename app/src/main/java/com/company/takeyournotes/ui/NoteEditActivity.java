package com.company.takeyournotes.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.company.takeyournotes.R;
import com.company.takeyournotes.data.Note;
import com.company.takeyournotes.data.NotesDataSource;

public class NoteEditActivity extends AppCompatActivity {
    public static final String NOTE_EXTRA = "NOTE_EXTRA";
    public static final String NOTE_RESULT_EXTRA = "NOTE_RESULT_EXTRA";

    private NotesDataSource notesDataSource;
    private EditText titleEditText;
    private EditText contentEditText;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        notesDataSource = new NotesDataSource(this);

        note = getIntent().getParcelableExtra(NOTE_EXTRA);
        titleEditText = (EditText) findViewById(R.id.activity_note_edit_title);
        titleEditText.setText(note.getTitle());
        contentEditText = (EditText) findViewById(R.id.activity_note_edit_content);
        contentEditText.setText(note.getContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_note_edit_done:
                updateNoteAndFinish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateNoteAndFinish() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        note.setTitle(title);
        note.setContent(content);

        notesDataSource.updateNote(note);
        setResult(RESULT_OK, new Intent().putExtra(NOTE_RESULT_EXTRA, note));
        finish();
    }
}
