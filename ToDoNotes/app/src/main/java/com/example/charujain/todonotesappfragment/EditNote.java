package com.example.charujain.todonotesappfragment;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.List;

public class EditNote extends AppCompatActivity {

    EditText title;
    EditText description;
    EditText priority;
    DatePicker datePicker;
    List<Note> noteList;
    Integer currentPosition;
    SQLHandler sqlHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sqlHandler = new SQLHandler(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        title = (EditText) findViewById(R.id.titleText);
        description = (EditText) findViewById(R.id.descriptionText);
        priority = (EditText) findViewById(R.id.priorityText);
        datePicker = (DatePicker) findViewById(R.id.datePicker2);

        Intent launchingIntent = getIntent();
        noteList = launchingIntent.getParcelableArrayListExtra("NOTES");
        currentPosition = launchingIntent.getIntExtra("POSITION", 0);
        System.out.print("priority==");
        Note note = noteList.get(currentPosition);
        System.out.println(note.priority);
        title.setText(note.title);
        description.setText(note.description);
        priority.setText("" + note.priority);

        int month = Integer.parseInt(note.date.split("-")[0]) - 1;
        int day = Integer.parseInt(note.date.split("-")[1]);
        int year = Integer.parseInt(note.date.split("-")[2]);
        datePicker.updateDate(year, month, day);

    }

    public void deleteNote(View view) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        String date = new StringBuilder()
                // Month is 0 based, so you have to add 1
                .append(month + 1).append("-")
                .append(day).append("-")
                .append(year).toString();
        int is_deleted = getIsDeleted();
        String query = "DELETE FROM notes WHERE title=" + "\"" + noteList.get(currentPosition).title + "\"";
        sqlHandler.executeQuery(query);
        // deleting first time, else delete permanently
        if (is_deleted == 0) {
            query = "INSERT INTO notes(title, description, date, priority, is_deleted) values ('"
                    + reformatString(title.getText().toString()) + "','" + reformatString(description.getText().toString()) + "','" + date + "'," + Integer.parseInt(priority.getText().toString()) + ", 1)";
            sqlHandler.executeQuery(query);
        }
        finish();
    }

    private int getIsDeleted() {
            String query = "SELECT is_deleted FROM notes where title=" + "\"" + noteList.get(currentPosition).title + "\"";
            Cursor c1 = sqlHandler.selectQuery(query);
            int is_deleted = 0;
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {
                        is_deleted = c1.getInt(c1.getColumnIndex("is_deleted"));
                    } while (c1.moveToNext());
                }
                c1.close();
            }
        return is_deleted;
    }

    public void updateNote(View view) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        StringBuilder date = new StringBuilder()
                // Month is 0 based, so you have to add 1
                .append(month + 1).append("-")
                .append(day).append("-")
                .append(year);

        String titleString = reformatString(title.getText().toString());
        String descriptionString = reformatString(description.getText().toString());
        String query = "UPDATE notes set title = " + "\"" + titleString + "\"" + ", description = " + "\"" + descriptionString + "\"" + ", priority = "
                + Integer.parseInt(priority.getText().toString()) + ", date = " + "\"" + date.toString() + "\"" + ", is_deleted=0 "
                +"where title=" + "\"" + reformatString(noteList.get(currentPosition).title) + "\"";
        sqlHandler.executeQuery(query);
        finish();
    }

    private String reformatString(String input) {
        String output = input.replaceAll("'","''");
        output = output.replaceAll("\"","\\\"");
        return output;
    }

    public void cancel(View view) {
        finish();
    }
}
