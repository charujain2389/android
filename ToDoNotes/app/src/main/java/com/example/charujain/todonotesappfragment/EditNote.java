package com.example.charujain.todonotesappfragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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
        System.out.println(noteList.get(currentPosition).priority);
        title.setText(noteList.get(currentPosition).title);
        description.setText(noteList.get(currentPosition).description);
        priority.setText("" + noteList.get(currentPosition).priority);

    }

    public void deleteNote(View view) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        String date = new StringBuilder()
                // Month is 0 based, so you have to add 1
                .append(month + 1).append("-")
                .append(day).append("-")
                .append(year).append(" ").toString();
        String query = "DELETE FROM notes WHERE title=" + "\"" + noteList.get(currentPosition).title + "\"";
        sqlHandler.executeQuery(query);
        query = "INSERT INTO notes(title, description, date, priority, is_deleted) values ('"
                + title.getText().toString() + "','" + description.getText().toString() + "','"+ date + "'," + Integer.parseInt(priority.getText().toString()) + ", 1)";
        sqlHandler.executeQuery(query);
        finish();
    }

    public void updateNote(View view) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        StringBuilder date = new StringBuilder()
                // Month is 0 based, so you have to add 1
                .append(month + 1).append("-")
                .append(day).append("-")
                .append(year).append(" ");

        String query = "UPDATE notes set title = " + "\"" + title.getText().toString() + "\"" + ", description = " + "\"" + description.getText().toString() + "\"" + ", priority = "
                + Integer.parseInt(priority.getText().toString()) + ", date = " + "\"" + date.toString() + "\""
                +"where title=" + "\"" + noteList.get(currentPosition).title + "\"";
        sqlHandler.executeQuery(query);
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
