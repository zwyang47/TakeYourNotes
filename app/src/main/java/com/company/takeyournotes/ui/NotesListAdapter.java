package com.company.takeyournotes.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.takeyournotes.R;
import com.company.takeyournotes.data.Note;

import java.util.List;

/**
 * Created by msi on 2017/3/17.
 */

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.VHNoteItem> {
    private List<Note> noteList;
    private OnNoteSelectedListener onNoteSelectedListener;

    public NotesListAdapter(List<Note> noteList, OnNoteSelectedListener onNoteSelectedListener) {
        this.noteList = noteList;
        this.onNoteSelectedListener = onNoteSelectedListener;
    }

    @Override
    public VHNoteItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VHNoteItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_row, parent, false));
    }

    // Called by the RecyclerView to replace the data at a specific position
    @Override
    public void onBindViewHolder(VHNoteItem holder, int position) {
        final Note note = noteList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getContent());

        /**
         * Reason for not start the activity from the adapter:
         * 1. Make our code more maintainable. To start an activity, we need a context, if we start it in the adapter, one need to pass
         * a context to the adapter. That will cause your UI logic spread all over the classes;
         * 2. Make this adapter reusable for other operations, such as a new activity, or show the note detail in a dialog instead of a
         * new screen.
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoteSelectedListener.onNoteSelected(note);
            }
        });
    }

    // Return total number of items in the data set
    @Override
    public int getItemCount() {
        return noteList.size();
    }

    // When delete or create a new Note, call this method to update the noteList
    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        // Notify that any view using the data set should refresh itself
        notifyDataSetChanged();
    }

    static class VHNoteItem extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;

        VHNoteItem(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.notes_list_row_title);
            contentTextView = (TextView) itemView.findViewById(R.id.notes_list_row_content);
        }
    }
}
