package com.example.admin.openglesnote.data;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.admin.openglesnote.DrawingActivity;
import com.example.admin.openglesnote.renderers.EntityRenderer;
import com.example.admin.openglesnote.renderers.HelloTriangleRenderer;
import com.example.admin.openglesnote.renderers.HelloTriangleWithLoader;
import com.example.admin.openglesnote.renderers.MapBuffersRenderer;
import com.example.admin.openglesnote.renderers.MipMap2DRenderer;
import com.example.admin.openglesnote.renderers.MultiTextureRenderer;
import com.example.admin.openglesnote.renderers.SimpleTexture2DRenderer;
import com.example.admin.openglesnote.renderers.SimpleTextureCubemapRenderer;
import com.example.admin.openglesnote.renderers.SimpleVertexShaderRenderer;
import com.example.admin.openglesnote.renderers.TextureWrapRenderer;
import com.example.admin.openglesnote.renderers.VAORenderer;
import com.example.admin.openglesnote.renderers.VBORenderer;

public class Renderers {

    public static final String RENDERER_HELLO_TRIANGLE="HelloTriangleRenderer";
    public static final String RENDERER_TRIANGLE_WITH_LOADER="HelloTriangleWithLoader";
    public static final String RENDERER_VBO="vertexBufferObject";
    public static final String RENDERER_VAO="vertexArrayObject";
    public static final String RENDERER_MAP_BUFFER_OBJECT="mapBufferObject";
    public static final String RENDERER_SIMPLE_SHADER="simpleShader";
    public static final String RENDERER_ENTITY="draw apis";
    public static final String RENDERER_SIMPLE_TEXTURE="simple texture ";
    public static final String RENDERER_MIPMAP="mipmap";
    public static final String RENDERER_WRAP="wrap map";
    public static final String RENDERER_CUBE_MAP="cube map";
    public static final String RENDERER_MULTI_TEXTURE="multiTexture";

    private static String[] renderers={
            RENDERER_HELLO_TRIANGLE,
            RENDERER_TRIANGLE_WITH_LOADER,
            RENDERER_VBO,
            RENDERER_VAO,
            RENDERER_MAP_BUFFER_OBJECT,
            RENDERER_ENTITY,
            RENDERER_SIMPLE_SHADER,
            RENDERER_SIMPLE_TEXTURE,
            RENDERER_MIPMAP,
            RENDERER_WRAP,
            RENDERER_CUBE_MAP,
            RENDERER_MULTI_TEXTURE
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
            case RENDERER_ENTITY:
                renderer=new EntityRenderer((DrawingActivity) context);
                break;
            case RENDERER_SIMPLE_SHADER:
                renderer=new SimpleVertexShaderRenderer(context);
                break;
            case RENDERER_SIMPLE_TEXTURE:
                renderer=new SimpleTexture2DRenderer(context);
                break;
            case RENDERER_MIPMAP:
                renderer=new MipMap2DRenderer(context);
                break;
            case RENDERER_WRAP:
                renderer=new TextureWrapRenderer(context);
                break;
            case RENDERER_CUBE_MAP:
                renderer=new SimpleTextureCubemapRenderer(context);
                break;
            case RENDERER_MULTI_TEXTURE:
                renderer=new MultiTextureRenderer(context);
                break;
        }

        return  renderer;
    }
}
