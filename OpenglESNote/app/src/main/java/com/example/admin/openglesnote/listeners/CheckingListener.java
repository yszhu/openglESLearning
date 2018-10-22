package com.example.admin.openglesnote.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.example.admin.openglesnote.CheckingActivity;
import com.example.admin.openglesnote.data.Renderers;

public class CheckingListener implements AdapterView.OnItemClickListener {
    private CheckingActivity context;
    public CheckingListener(CheckingActivity context){
        this.context=context;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        context.checking(position);
    }
}
