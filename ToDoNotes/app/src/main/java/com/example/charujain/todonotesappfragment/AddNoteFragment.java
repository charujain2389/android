package com.example.charujain.todonotesappfragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNoteFragment extends Fragment {


    Button addButton;
    Button cancelButton;
    EditText noteTitleEditText;
    EditText noteDescriptionEditText;
    DatePicker datePicker;
    int year;
    int month;
    int day;
    AddNoteDelegate addNoteDelegate;
    View fragmentView;

    public AddNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_add_note, container, false);
        noteTitleEditText = (EditText) fragmentView.findViewById(R.id.editText);
        noteDescriptionEditText = (EditText) fragmentView.findViewById(R.id.editText2);
        addButton = (Button) fragmentView.findViewById(R.id.button2);
        cancelButton = (Button) fragmentView.findViewById(R.id.button3);
        datePicker = (DatePicker) fragmentView.findViewById(R.id.datePicker);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = noteTitleEditText.getText().toString();
                String description = noteDescriptionEditText.getText().toString();
                year = datePicker.getYear();
                month = datePicker.getMonth();
                day = datePicker.getDayOfMonth();
                StringBuilder date = new StringBuilder()
                        .append(month + 1).append("-")
                        .append(day).append("-")
                        .append(year).append(" ");
                Note note = new Note();
                note.title = title;
                note.description = description;
                note.date = date.toString();
                if (addNoteDelegate != null) {
                    addNoteDelegate.addNote(note);
                }
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        return fragmentView;
    }

}
