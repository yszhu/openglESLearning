package com.example.admin.openglesnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.admin.openglesnote.mainLenstener.MainListener;
import com.example.admin.openglesnote.notes.HelloTriangle;
import com.example.admin.openglesnote.notes.MapBufferObject;
import com.example.admin.openglesnote.notes.TriangleWithLoader;
import com.example.admin.openglesnote.notes.VertexArrayObject;
import com.example.admin.openglesnote.notes.VertexBufferObject;

public class MainActivity extends AppCompatActivity {

    private ListView nameList;
    private Button checkDrawing;
    private String[] names={
            "Hello Triangle",
            "Triangle with loader ",
            "vertex buffer object",
            "vertex array object",
            "map buffer object"
    };

    private Class[] activities={
            HelloTriangle.class,
            TriangleWithLoader.class,
            VertexBufferObject.class,
            VertexArrayObject.class,
            MapBufferObject.class,
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
        Intent intent=new Intent(MainActivity.this,DrawingActivity.class);
        startActivity(intent);
    }
}
