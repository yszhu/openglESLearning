package com.example.admin.openglesnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.openglesnote.data.Renderers;
import com.example.admin.openglesnote.listeners.CheckingListener;

public class CheckingActivity extends Activity {
    private ListView shader_list;
    private CheckingListener checkingListener;
    private String[] shadersName={
            "Hello triangle",
            "Hello triangle with loader",
            "vertex buffer object",
            "vertex array object",
            "map buffer object",
            "drwa apis",
            "simpleShader",
            "simpleTexture",
            "mipmap texture",
            "wrap map",
            "cube map"

    };
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);
        initView();
        bindListener();
    }

    private void initView(){
        shader_list=findViewById(R.id.shader_list);
        shader_list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,shadersName));
    }

    private void bindListener(){
        checkingListener=new CheckingListener(this);
        shader_list.setOnItemClickListener(checkingListener);

    }

    public void checking(int index){
        Renderers.setIndex(index);
        Intent intent=new Intent(CheckingActivity.this,DrawingActivity.class);
        startActivity(intent);
    }
}
