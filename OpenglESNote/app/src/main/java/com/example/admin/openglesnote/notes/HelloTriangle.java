package com.example.admin.openglesnote.notes;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.admin.openglesnote.NoteActivity;
import com.example.admin.openglesnote.listeners.NoteListener;
import com.example.admin.openglesnote.R;

public class HelloTriangle extends NoteActivity {
    private RadioGroup checkChosen;
    private NoteListener noteListener;
    private View currentNote=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        initView();
        bindListener();
    }
    private void initView(){
        checkChosen=findViewById(R.id.checking_chosen);
    }

    private void bindListener(){
        noteListener=new NoteListener(this);
        checkChosen.setOnCheckedChangeListener(noteListener);
    }

    public void SwitchNote(int id){
        switch (id){
            case R.id.total_note:
                if(currentNote!=null){
                    findViewById(R.id.activity_note_view).setVisibility(View.VISIBLE);
                    findViewById(R.id.renderer_note_view).setVisibility(View.VISIBLE);
                    currentNote=null;
                }
                break;
            case R.id.activity_note:
                switchToNote(R.id.activity_note_view);
                break;
            case R.id.renderer_note:
                switchToNote(R.id.renderer_note_view);
                break;
        }
    }

    private void switchToNote(int id){
        if(currentNote!=null){
            currentNote.setVisibility(View.GONE);
        }else {
            findViewById(R.id.activity_note_view).setVisibility(View.GONE);
            findViewById(R.id.renderer_note_view).setVisibility(View.GONE);
        }
        currentNote=findViewById(id);
        currentNote.setVisibility(View.VISIBLE);
    }



}
