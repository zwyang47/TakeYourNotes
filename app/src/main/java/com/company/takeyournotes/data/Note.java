package com.company.takeyournotes.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by msi on 2017/3/16.
 */

// This is the note data model
public class Note implements Parcelable {
    private long id;
    private String title;
    private String content;

    // When we save a note first time, there is no id yet
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // When display a note from our database, all three attributes are fulfilled
    public Note(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    protected Note(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
    }
}
