package com.example.admin.openglesnote;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.openglesnote.data.Renderers;
import com.example.admin.openglesnote.renderers.BasicRenderer;
import com.example.admin.openglesnote.renderers.EntityRenderer;
import com.example.admin.openglesnote.renderers.HelloTriangleRenderer;

public class DrawingActivity extends Activity {
    private final int CONTEXT_CLIENT_VERSION = 3;

    private GLSurfaceView mGLSurfaceView;
    private Button changeModeBtn;
    private TextView apiText;

    private BasicRenderer basicRenderer;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );

        setContentView ( R.layout.activity_drawapi );

        mGLSurfaceView = findViewById(R.id.surface_view);
        changeModeBtn=findViewById(R.id.change_mode);
        apiText=findViewById(R.id.function);
        changeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicRenderer.setDrawAPI();
                apiText.setText(basicRenderer.getDrawAPI());
                Toast.makeText(DrawingActivity.this,basicRenderer.getDrawAPI(),Toast.LENGTH_SHORT).show();
            }
        });

        if ( detectOpenGLES30() )
        {
            // Tell the surface view we want to create an OpenGL ES 3.0-compatible
            // context, and set an OpenGL ES 3.0-compatible renderer.
            mGLSurfaceView.setEGLContextClientVersion ( CONTEXT_CLIENT_VERSION );
            basicRenderer= (BasicRenderer) Renderers.getCurrentRenderer(this);
            mGLSurfaceView.setRenderer (basicRenderer);
            if(basicRenderer.getDrawAPI()!=null){
                apiText.setText(basicRenderer.getDrawAPI());
            }
            else {
                changeModeBtn.setVisibility(View.GONE);
            }

        }
        else
        {
            Log.e ( "HelloTriangle", "OpenGL ES 3.0 not supported on device.  Exiting..." );
            finish();

        }


    }

    //check the status of device support version of opengl es,here check the version 3
    //note 1.2 by yszhu
    private boolean detectOpenGLES30()
    {
        ActivityManager am =
                ( ActivityManager ) getSystemService ( Context.ACTIVITY_SERVICE );
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return ( info.reqGlEsVersion >= 0x30000 );
    }

    @Override
    protected void onResume()
    {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }


}
