package com.example.charujain.todonotesappfragment;

/**
 * Created by charu.jain on 09/12/15.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class SQLHandler {

    public static final String DATABASE_NAME = "todo.db";
    public static final int DATABASE_VERSION = 2;
    SQLiteDatabase sqlDatabase;
    DBHelper dbHelper;

    public SQLHandler(Context context) {

        dbHelper = new DBHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        sqlDatabase = dbHelper.getWritableDatabase();
    }

    public void executeQuery(String query) {
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
            }

            sqlDatabase = dbHelper.getWritableDatabase();
            sqlDatabase.execSQL(query);
        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }

    }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {
            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();

            }
            sqlDatabase = dbHelper.getWritableDatabase();
            c1 = sqlDatabase.rawQuery(query, null);

        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c1;
    }
}
