package com.example.charujain.todonotesappfragment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by charu.jain on 03/11/15.
 */
public class Note implements Parcelable{
    String title;
    String description;
    int priority;
    String date;

    public static Creator<Note> CREATOR = new Creator<Note>() {

        @Override
        public Note createFromParcel(Parcel source) {
            Note c = new Note();
            c.title = source.readString();
            c.description = source.readString();
            c.priority = source.readInt();
            c.date = source.readString();
            return c;
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(priority);
        dest.writeString(date);
    }


}
