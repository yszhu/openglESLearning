package com.example.admin.practice;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private GLSurfaceView.Renderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(detectOpenglES30()){
            initView();
            setContentView(glSurfaceView);
        }
    }

    private boolean detectOpenglES30(){
        ActivityManager activityManager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo=activityManager.getDeviceConfigurationInfo();
        return (configurationInfo.reqGlEsVersion>=0x30000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }


    private void initView(){
        glSurfaceView=new GLSurfaceView(this);
        //须在setRenderer前指定
        glSurfaceView.setEGLContextClientVersion(3);
        renderer=new SimpleRenderer(this);
        glSurfaceView.setRenderer(renderer);


    }
}
