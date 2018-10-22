package com.example.admin.openglesnote.data;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.admin.openglesnote.renderers.HelloTriangleRenderer;
import com.example.admin.openglesnote.renderers.HelloTriangleWithLoader;
import com.example.admin.openglesnote.renderers.MapBuffersRenderer;
import com.example.admin.openglesnote.renderers.VAORenderer;
import com.example.admin.openglesnote.renderers.VBORenderer;

public class Renderers {

    public static final String  RENDERER_HELLO_TRIANGLE="HelloTriangleRenderer";
    public static final String  RENDERER_TRIANGLE_WITH_LOADER="HelloTriangleWithLoader";
    public static final String  RENDERER_VBO="vertexBufferObject";
    public static final String  RENDERER_VAO="vertexArrayObject";
    public static final String  RENDERER_MAP_BUFFER_OBJECT="mapBufferObject";

    private static String[] renderers={
            RENDERER_HELLO_TRIANGLE,
            RENDERER_TRIANGLE_WITH_LOADER,
            RENDERER_VBO,
            RENDERER_VAO,
            RENDERER_MAP_BUFFER_OBJECT
    };
    public static String currentRenderer=renderers[0];
//    public static Renderers getInstance(){
//        if(instance==null){
//            instance=new Renderers();
//        }
//        return instance;
//    }

    public static void setIndex(int index){
        currentRenderer=renderers[index];
    }
    public static GLSurfaceView.Renderer getCurrentRenderer(Context context){
        GLSurfaceView.Renderer renderer=null;
        switch (currentRenderer){
            case RENDERER_HELLO_TRIANGLE:
                renderer=new HelloTriangleRenderer(context);
                break;
            case RENDERER_TRIANGLE_WITH_LOADER:
                renderer=new HelloTriangleWithLoader(context);
                break;
            case RENDERER_VBO:
                renderer=new VBORenderer(context);
                break;
            case RENDERER_VAO:
                renderer=new VAORenderer(context);
                break;
            case RENDERER_MAP_BUFFER_OBJECT:
                renderer=new MapBuffersRenderer(context);
                break;
        }

        return  renderer;
    }
}
