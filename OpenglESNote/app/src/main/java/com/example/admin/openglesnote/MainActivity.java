package com.example.admin.openglesnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.admin.openglesnote.listeners.MainListener;
import com.example.admin.openglesnote.notes.entity.EntityNoteActivity;

public class MainActivity extends AppCompatActivity {

    private ListView nameList;
    private Button checkDrawing;
    private String[] names={
            "vertex attribute data transport",
            "entity note"
    };

    private Class[] activities={
            NoteListActivity.class,
            EntityNoteActivity.class
    };
    private MainListener mainListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void initView(){

        nameList=findViewById(R.id.name_list);
        ArrayAdapter arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        nameList.setAdapter(arrayAdapter);

        checkDrawing=findViewById(R.id.check_Drawing);
    }

    private void setListener(){
        mainListener =new MainListener(this);

        nameList.setOnItemClickListener(mainListener);
        checkDrawing.setOnClickListener(mainListener);
    }

    public void jumpPage(int index){
        Intent intent=new Intent(MainActivity.this,activities[index]);
        startActivity(intent);
    }
    public void checkDrawing(){
        Intent intent=new Intent(MainActivity.this,CheckingActivity.class);
        startActivity(intent);
    }
}
