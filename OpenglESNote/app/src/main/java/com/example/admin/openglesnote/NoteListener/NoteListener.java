package com.example.admin.openglesnote.NoteListener;

import android.app.Activity;
import android.content.Context;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.admin.openglesnote.NoteActivity;

public class NoteListener implements RadioGroup.OnCheckedChangeListener {
    private NoteActivity context;
    public NoteListener(NoteActivity context){
        this.context=context;
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //Toast.makeText(context,context.findViewById(checkedId).toString(),Toast.LENGTH_SHORT).show();
        context.SwitchNote(checkedId);
    }
}
