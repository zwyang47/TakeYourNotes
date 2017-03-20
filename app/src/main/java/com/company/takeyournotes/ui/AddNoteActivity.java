package com.company.takeyournotes.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.company.takeyournotes.R;
import com.company.takeyournotes.data.Note;
import com.company.takeyournotes.data.NotesDataSource;

public class AddNoteActivity extends AppCompatActivity {
    private NotesDataSource notesDataSource;

    // These two elements refer to corresponding database attribute
    private EditText titleEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        notesDataSource = new NotesDataSource(this);
        titleEditText = (EditText) findViewById(R.id.activity_add_note_title);
        contentEditText = (EditText) findViewById(R.id.activity_add_note_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_note, menu);
        // Return true for this menu to be displayed
        return true;
    }

    // Handle a click event
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_note_done:
                saveNote();
                setResult(RESULT_OK);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Get title and content from private elements, then create a Note object, and save it to our database
    private void saveNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        Note note = new Note(title, content);
        notesDataSource.saveNote(note);
    }
}
