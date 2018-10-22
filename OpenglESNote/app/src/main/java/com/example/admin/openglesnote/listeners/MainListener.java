package com.example.admin.openglesnote.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.example.admin.openglesnote.MainActivity;

public class MainListener implements AdapterView.OnItemClickListener , View.OnClickListener {

    private MainActivity context;

    public MainListener(MainActivity context){
        this.context=context;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        context.jumpPage(position);
    }

    @Override
    public void onClick(View v) {
        context.checkDrawing();
    }
}
