package com.example.charujain.todonotesappfragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class RecycledNotesFragment extends Fragment {

    View fragmentView;
    SQLHandler sqlHandler;
    ArrayList<Note> noteList = new ArrayList<>();
    ListView noteListView;
    NoteAdapter adapter;
    Button cancel;

    public RecycledNotesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_recycled_notes, container, false);
        sqlHandler = new SQLHandler(getActivity());
        noteListView = (ListView) fragmentView.findViewById(R.id.listView2);
        cancel = (Button) fragmentView.findViewById(R.id.button);
        showList();
        adapter = new NoteAdapter(getContext(), noteList);
        noteListView.setAdapter(adapter);
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EditNote.class);
                intent.putExtra("NOTES", noteList);
                intent.putExtra("POSITION", position);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        adapter.notifyDataSetChanged();
        showList();
    }

    private void showList() {
        noteList.clear();
        String query = "SELECT * FROM notes where is_deleted=1";
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
}
