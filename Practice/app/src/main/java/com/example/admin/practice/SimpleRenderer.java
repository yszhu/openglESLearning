package com.example.admin.practice;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.practice.common.ESTransform;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SimpleRenderer implements GLSurfaceView.Renderer {
    private MainActivity activity;

    private int width;

    private int height;

    private FloatBuffer vertexBuffer;

    private FloatBuffer colorBuffer;

    private ShortBuffer indexBuffer;

    private int locationIndex=0;

    private int colorIndex=1;

    private int[] bufferIds=new int[3];

    private int programObject;

    private int vertexShader;

    private int fragmentShader;

    ESTransform matrix;


    String vShaderStr =
            "#version 300 es                            \n" +
                    "uniform mat4 u_mvMatrix;           \n" +
                    "layout(location = 0) in vec4 a_position;   \n" +
                    "layout(location = 1) in vec4 a_color;      \n" +
                    "out vec4 v_color;                          \n" +
                    "void main()                                \n" +
                    "{                                          \n" +
                    "    v_color = a_color;                     \n" +
                    "    gl_Position = u_mvMatrix*a_position;   \n" +
                    "}";


    String fShaderStr =
            "#version 300 es            \n" +
                    "precision mediump float;   \n" +
                    "in vec4 v_color;           \n" +
                    "out vec4 o_fragColor;      \n" +
                    "void main()                \n" +
                    "{                          \n" +
                    "    o_fragColor = v_color; \n" +
                    "}" ;


    private final float[] vertices={
            -1.0f, -1.0f, 1.0f,
            -1.0f,  1.0f, 1.0f,
             1.0f,  1.0f, 1.0f,
             1.0f, -1.0f, 1.0f,

            -1.0f, -1.0f, -1.0f,
            -1.0f,  1.0f, -1.0f,
             1.0f,  1.0f, -1.0f,
             1.0f, -1.0f, -1.0f
    };

    private final float[] colors={
            /*1.0f,1.0f,1.0f,0.0f,
            1.0f,0.0f,1.0f,0.0f,
            0.0f,1.0f,1.0f,0.0f,
            1.0f,1.0f,0.0f,0.0f,

            1.0f,0.0f,1.0f,0.0f,
            0.0f,1.0f,1.0f,0.0f,
            1.0f,1.0f,0.0f,0.0f,
            1.0f,0.0f,1.0f,0.0f,*/

            1.0f,1.0f,0.0f,0.0f,
            1.0f,1.0f,0.0f,0.0f,
            1.0f,1.0f,0.0f,0.0f,
            1.0f,1.0f,0.0f,0.0f,

            0.0f,1.0f,1.0f,0.0f,
            0.0f,1.0f,1.0f,0.0f,
            0.0f,1.0f,1.0f,0.0f,
            0.0f,1.0f,1.0f,0.0f,
    };

    private final short[] indices={
            0,1,2,
            0,2,3,
            4,5,6,
            4,6,7,
            4,5,1,
            4,1,0,
            3,2,6,
            3,6,7,
            1,5,6,
            1,6,2,
            0,4,7,
            0,7,3
    };



    public SimpleRenderer(MainActivity activity){
        this.activity=activity;
        setSize(0.5f);
        vertexBuffer=ByteBuffer.allocateDirect(4*vertices.length).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertices).position(0);

        colorBuffer=ByteBuffer.allocateDirect(4*colors.length).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(colors).position(0);

        indexBuffer=ByteBuffer.allocateDirect(2*indices.length).order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(indices).position(0);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        matrix=new ESTransform();

        matrix.matrixLoadIdentity();
        initProgram();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width=width;
        this.height=height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glViewport(0,0,width,height);

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glUseProgram(programObject);

        draw();
    }

    private void draw(){
        if((bufferIds[0]==0)||(bufferIds[1]==0)||(bufferIds[2]==0)){
            GLES30.glGenBuffers(3,bufferIds,0);

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,bufferIds[0]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*vertices.length,vertexBuffer,GLES30.GL_STATIC_DRAW);

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,bufferIds[1]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*colors.length,colorBuffer,GLES30.GL_STATIC_DRAW);

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0);

            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,bufferIds[2]);
            GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER,2*indices.length,indexBuffer,GLES30.GL_STATIC_DRAW);

            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,0);
        }

        vertexBuffer.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,bufferIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*vertices.length,vertexBuffer,GLES30.GL_STATIC_DRAW);

        GLES30.glEnableVertexAttribArray(locationIndex);
        GLES30.glVertexAttribPointer(locationIndex,3,GLES30.GL_FLOAT,false,4*3,0);

        colorBuffer.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,bufferIds[1]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*colors.length,colorBuffer,GLES30.GL_STATIC_DRAW);

        GLES30.glEnableVertexAttribArray(colorIndex);
        GLES30.glVertexAttribPointer(colorIndex,4,GLES30.GL_FLOAT,false,4*4,0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0);

        indexBuffer.position(0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,bufferIds[2]);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER,2*indices.length,indexBuffer,GLES30.GL_STATIC_DRAW);

        int a_mvMatrix=0;
        a_mvMatrix=GLES30.glGetUniformLocation(programObject,"u_mvMatrix");

        matrix.rotate(1.0f,1,1,1);
        GLES30.glUniformMatrix4fv(a_mvMatrix,1,false,matrix.getAsFloatBuffer());

        GLES30.glDrawElements(GLES30.GL_TRIANGLES,indices.length,GLES30.GL_UNSIGNED_SHORT,0);

        GLES30.glDisableVertexAttribArray(locationIndex);
        GLES30.glDisableVertexAttribArray(colorIndex);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,0);
    }

    private void initProgram(){
        vertexShader=loadShader(GLES30.GL_VERTEX_SHADER,vShaderStr);
        fragmentShader=loadShader(GLES30.GL_FRAGMENT_SHADER,fShaderStr);

        programObject=GLES30.glCreateProgram();

        if(programObject==0){
            Log.i("programObject","failed to create program object");
            GLES30.glDeleteProgram(programObject);
            return;
        }

        GLES30.glAttachShader(programObject,vertexShader);
        GLES30.glAttachShader(programObject,fragmentShader);

        GLES30.glLinkProgram(programObject);
        int[] linked=new int[1];
        GLES30.glGetProgramiv ( programObject, GLES30.GL_LINK_STATUS, linked, 0 );

        if(linked[0]==0){
            Log.i("linked","failed link program");
            Log.i ( "linkedInfo", GLES30.glGetProgramInfoLog ( programObject )+"" );
            GLES30.glDeleteProgram(programObject);
            return;
        }


        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f );

    }

    private int loadShader(int type,String source){

        int[] compiled=new int[1];
        int shader=GLES30.glCreateShader(type);

        GLES30.glShaderSource(shader,source);

        GLES30.glCompileShader(shader);

        GLES30.glGetShaderiv(shader,GLES30.GL_COMPILE_STATUS,compiled,0);

        if(compiled[0]==0){
            GLES30.glDeleteShader(shader);
            Log.i("shader","failed to compile shader");
            return 0;
        }

        return shader;
    }

    private void setSize(float size){
        for(int i=0;i<vertices.length;++i){
            vertices[i]*=size;
        }
    }
}
