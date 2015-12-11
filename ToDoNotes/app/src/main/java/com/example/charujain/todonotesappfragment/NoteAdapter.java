package com.example.charujain.todonotesappfragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by charu.jain on 04/11/15.
 */
public class NoteAdapter extends BaseAdapter {

    Context context;
    List<Note> noteList;

    static class ViewHolder {
        TextView title;
        TextView priority;
        TextView date;
    }

    public NoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mainView;

        if(convertView == null) {
            Log.i("NoteAdapter", "getView("+position+")");

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mainView = inflater.inflate(R.layout.row, null);
            ViewHolder vh = new ViewHolder();
            vh.title = (TextView) mainView.findViewById(R.id.note_title);
            vh.priority = (TextView) mainView.findViewById(R.id.note_priority);
            vh.date = (TextView) mainView.findViewById(R.id.note_dueDate);

            //associate the view holder with the view
            mainView.setTag(vh);
        }
        else {
            mainView = convertView;
        }
        //get the note object at position in the array
        Note note = (Note) getItem(position);

        //get the view holder associated with the view
        ViewHolder vh = (ViewHolder) mainView.getTag();

        //set the note title on the textview
        vh.priority.setText(note.priority+"");
        vh.title.setText(note.title);
        vh.date.setText(note.date);

        return mainView;
    }
}
