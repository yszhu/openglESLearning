package com.example.admin.openglesnote.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.example.admin.openglesnote.NoteActivity;
import com.example.admin.openglesnote.NoteListActivity;

public class NoteListListener implements AdapterView.OnItemClickListener {
    private NoteListActivity context;
    public NoteListListener(NoteListActivity context){
        this.context=context;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        context.checkNote(position);
    }
}
