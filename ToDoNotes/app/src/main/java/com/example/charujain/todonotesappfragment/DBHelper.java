package com.example.charujain.todonotesappfragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by charu.jain on 06/12/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory cursorFactory, int databaseVersion) {
        super(context, databaseName, cursorFactory, databaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table notes(_id integer primary key, title text unique not null, description text null, date text, priority integer default 0, is_deleted integer default 0)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
