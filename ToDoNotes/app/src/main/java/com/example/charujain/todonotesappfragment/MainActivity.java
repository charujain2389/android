package com.example.charujain.todonotesappfragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private void loadListFragment() {
        FragmentManager manager = getSupportFragmentManager();
        NoteListFragment noteListFragment = (NoteListFragment) manager.findFragmentByTag("NLF");
        if (noteListFragment == null) {
            //create the fragment object
            noteListFragment = new NoteListFragment();
            // begin a fragment transaction for fragment operations
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.mainLayout, noteListFragment, "NLF");
            transaction.commit();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            loadListFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListFragment();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadListFragment();
    }

    public void switchToAddFragment() {
        FragmentManager manager = getSupportFragmentManager();
        NoteListFragment noteListFragment = (NoteListFragment) manager.findFragmentByTag("NLF");
        if (noteListFragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            AddNoteFragment addNoteFragment = new AddNoteFragment();
            addNoteFragment.addNoteDelegate = noteListFragment;
            transaction.remove(noteListFragment);
            transaction.add(R.id.mainLayout, addNoteFragment, "ANF");
            transaction.addToBackStack("ADD"); // this ADD identifier can be anything
            transaction.commit();
        }
    }

    public void switchToRecycledFragment() {
        FragmentManager manager = getSupportFragmentManager();
        NoteListFragment noteListFragment = (NoteListFragment) manager.findFragmentByTag("NLF");
        if (noteListFragment != null) {
            RecycledNotesFragment recycledNotesFragment = new RecycledNotesFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(noteListFragment);
            transaction.add(R.id.mainLayout, recycledNotesFragment, "RLF");
            transaction.addToBackStack("ADD"); // this ADD identifier can be anything
            transaction.commit();
        }
    }
}
