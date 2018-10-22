package com.example.admin.openglesnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.openglesnote.listeners.NoteListListener;
import com.example.admin.openglesnote.notes.HelloTriangle;
import com.example.admin.openglesnote.notes.MapBufferObject;
import com.example.admin.openglesnote.notes.TriangleWithLoader;
import com.example.admin.openglesnote.notes.VertexArrayObject;
import com.example.admin.openglesnote.notes.VertexBufferObject;


public class NoteListActivity extends Activity {
    private ListView noteList;
    private String[] namesList={
            "HelloTriangle",
            "TriangleWithLoader",
            "VertexBufferObject",
            "VertexArrayObject",
            "MapBufferObject"
    };
    private Class[] activities={
            HelloTriangle.class,
            TriangleWithLoader.class,
            VertexBufferObject.class,
            VertexArrayObject.class,
            MapBufferObject.class,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        initView();
        bindListener();
    }
    private void initView(){
        noteList=findViewById(R.id.note_list);
        noteList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,namesList));
    }
    private void bindListener(){
        noteList.setOnItemClickListener(new NoteListListener(this));
    }

    public void checkNote(int index){
        Intent intent=new Intent(NoteListActivity.this,activities[index]);
        startActivity(intent);
    }
}
