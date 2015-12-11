package com.example.charujain.todonotesappfragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by charu.jain on 03/11/15.
 */
public class NoteListFragment extends Fragment implements AddNoteDelegate {

    ListView noteListView;
    NoteAdapter adapter;
    ArrayList<Note> noteList = new ArrayList<>();
    SQLHandler sqlHandler;

    public NoteListFragment() {
    }

    public void addNote(Note c) {
        noteList.add(c);
        addNoteToDB(c);
    }

    private void addNoteToDB(Note c) {
        String query = "INSERT INTO notes(title, description, date, priority) values ('"
                + reformatString(c.title) + "','" + reformatString(c.description) + "','"+ c.date + "'," + c.priority + ")";
        sqlHandler.executeQuery(query);
    }

    private void updateUI() {
        adapter.notifyDataSetChanged();
        showList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        sqlHandler = new SQLHandler(getActivity());
        View fragmentView = inflater.inflate(R.layout.fragment_notelist, container, false);
        noteListView = (ListView) fragmentView.findViewById(R.id.listView);

        showList();

        adapter = new NoteAdapter(getContext(), noteList);
        noteListView.setAdapter(adapter);
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String path = "http://en.wikipedia.org/wiki/" + countryList.get(position).name;
                Intent intent = new Intent(getActivity(), EditNote.class);
                intent.putExtra("NOTES", noteList);
                intent.putExtra("POSITION", position);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        return fragmentView;
    }

    private void showList() {
        noteList.clear();
        String query = "SELECT * FROM notes where is_deleted=0";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Note note = new Note();

                    note.title = c1.getString(c1.getColumnIndex("title"));
                    note.description = c1.getString(c1.getColumnIndex("description"));
                    note.priority = c1.getInt(c1.getColumnIndex("priority"));
                    note.date = c1.getString(c1.getColumnIndex("date"));
                    noteList.add(note);
                } while (c1.moveToNext());
            }
            c1.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //adding a menu
        inflater.inflate(R.menu.content_menu, menu); // -- if doing with xml
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.noteListMenu) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.switchToAddFragment();
        }
        else if(item.getItemId() == R.id.recycledListMenu) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.switchToRecycledFragment();
        }
        return true;
    }

    private String reformatString(String input) {
        System.out.println(input);
        String output = input.replaceAll("'","''");
        output = output.replaceAll("\"","\\\"");
        System.out.println(output);
        return output;
    }
}
