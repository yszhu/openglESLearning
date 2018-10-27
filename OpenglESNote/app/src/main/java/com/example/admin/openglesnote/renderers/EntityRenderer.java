package com.example.admin.openglesnote.renderers;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.admin.openglesnote.DrawingActivity;
import com.example.admin.openglesnote.common.ESShader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EntityRenderer extends BasicRenderer {

    private String TAG="Entity Activity";

    private DrawingActivity context;

    private String vertexCode=
            "#version 300 es 			               \n"
                    +   "in vec4 vPosition;           \n"
                    +"   in vec4 vColor;              \n"
                    +"   out vec4 fColor;             \n"
                    + "void main()                    \n"
                    + "{                              \n"
                    + "   gl_Position = vPosition;    \n"
                    +"    fColor=vColor;              \n"
                    + "}                              \n";

    private String fragmentCode=
            "#version 300 es		 			          	         \n"
                    + "precision mediump float;					  	 \n"
                    +" in vec4 fColor;                              \n"
                    + "out vec4 fragColor;	 			 		  	 \n"
                    + "void main()                                  \n"
                    + "{                                            \n"
                    + "  fragColor = fColor;	                     \n"
                    + "}                                            \n";

    private int vertexShader;
    private int fragmentShader;
    private int programObject;


    private int width;
    private int height;
    private FloatBuffer floatBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer indicesBuffer;
    private ShortBuffer indiecsRestartBuffer;
    private ShortBuffer indicesGegenerateTriangelBuffer;

    private final float[] vertexData={
            -1.0f, -0.25f, 0.0f,
            -0.75f, 0.25f, 0.0f,
            -0.5f, -0.25f, 0.0f,

            -0.25f, 0.25f, 0.0f,

            0.0f, -0.25f, 0.0f,

            0.25f, 0.25f, 0.0f,

            0.5f, -0.25f, 0.0f,

            0.75f, 0.25f, 0.0f,

            1.0f, -0.25f, 0.0f,


    };

    private final float[] colorData={
            0.0f,1.0f,1.0f,1.0f,
            1.0f,0.0f,1.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,

            0.0f,1.0f,1.0f,1.0f,
            1.0f,0.0f,1.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,

            0.0f,1.0f,1.0f,1.0f,
            1.0f,0.0f,1.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,
    };

    private final short[] indiecs={
            0,1,2,3,4,5,6,7,8
    };

    private final short[] indiecsResart= {
            0, 1, 2, 3, (short) 65535, 5, 6, 7, 8
    };

    private final short[] indicesDegenerateTriangle={
            0,1,2,3,3,6,6,7,8
    };


    private final String DRAW_ARRAY="drawArray";
    private final String DRAW_ELEMENT="drawElement";
    private final String DRAW_RANG_ELEMENT="drawRangElement";

    private final String DRAW_RESART_ENTITY="restartEntity";
    private final String DRAW_DEGENERATE_TRIANGLE="degenerateTriangle";

    private String[] drawApis={
            DRAW_ARRAY,
            DRAW_ELEMENT,
            DRAW_RANG_ELEMENT,

            DRAW_RESART_ENTITY,
            DRAW_DEGENERATE_TRIANGLE
    };
    private int apiIndex=0;
    private String drawAPI=drawApis[0];


    public EntityRenderer(DrawingActivity context){
        this.context=context;
        floatBuffer=ByteBuffer.allocateDirect(vertexData.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatBuffer.put(vertexData).position(0);

        colorBuffer=ByteBuffer.allocateDirect(colorData.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(colorData).position(0);

        indicesBuffer=ByteBuffer.allocateDirect(indiecs.length*2).order(ByteOrder.nativeOrder()).asShortBuffer();
        indicesBuffer.put(indiecs).position(0);

        indiecsRestartBuffer=ByteBuffer.allocateDirect(indiecsResart.length*2).order(ByteOrder.nativeOrder()).asShortBuffer();
        indiecsRestartBuffer.put(indiecsResart);
        indiecsRestartBuffer.position(0);

        indicesGegenerateTriangelBuffer=ByteBuffer.allocateDirect(indicesDegenerateTriangle.length*2).
                order(ByteOrder.nativeOrder()).asShortBuffer();
        indicesGegenerateTriangelBuffer.put(indicesDegenerateTriangle);
        indicesGegenerateTriangelBuffer.position(0);
    }

    public String getDrawAPI(){
        return drawAPI;
    }

    public void setDrawAPI(){
        apiIndex++;
        apiIndex%=drawApis.length;
        drawAPI=drawApis[apiIndex];
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        vertexShader=ESShader.loadShader(GLES30.GL_VERTEX_SHADER,vertexCode);
        fragmentShader=ESShader.loadShader(GLES30.GL_FRAGMENT_SHADER,fragmentCode);

        programObject=GLES30.glCreateProgram();

        if (programObject==0){
            return ;
        }

        GLES30.glAttachShader(programObject,vertexShader);
        GLES30.glAttachShader(programObject,fragmentShader);

        GLES30.glBindAttribLocation(programObject,0,"vPosition");
        GLES30.glBindAttribLocation(programObject,1,"vColor");

        GLES30.glLinkProgram(programObject);


        int[] linked=new int[1];
        GLES30.glGetProgramiv(programObject,GLES30.GL_LINK_STATUS,linked,0);

        if(linked[0]==0){
            Log.e(TAG,"Error linking program");
            Log.e(TAG,GLES30.glGetProgramInfoLog(programObject));
            GLES30.glDeleteProgram(programObject);
            return;
        }

        GLES30.glClearColor(1.0f,1.0f,1.0f,0.0f);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width=width;
        this.height=height;
    }


    private void drawWithDrawArray(){
        GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,0,floatBuffer);
        GLES30.glVertexAttribPointer(1,4,GLES30.GL_FLOAT,false,0,colorBuffer);

        //maybe there are some buffers (not VBO) to store vertex attribute (variable and array) exist in GPU ,
        // so that data can be transport before
        // enable vertex attribute array.
        //by yszhu
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,9);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    private void drawWithElements(){

        GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,0,floatBuffer);
        GLES30.glVertexAttribPointer(1,4,GLES30.GL_FLOAT,false,0,colorBuffer);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP,9, GLES30.GL_UNSIGNED_SHORT,indicesBuffer.position(0));

        //GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP,5, GLES30.GL_UNSIGNED_SHORT,indicesBuffer.position(4));
        //in C,the last param is the Start position of index Array,so it can be used like indicesBuffer+4 to change
        // first element of index .in Java used like indicesBuffer.position(4)
        //by yszhu

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void drawWithRangElements(){
        GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,0,floatBuffer);
        GLES30.glVertexAttribPointer(1,4,GLES30.GL_FLOAT,false,0,colorBuffer);

        //maybe there are some buffers (not VBO) exist in GPU ,so that data can be transport before
        // enable vertex attribute array.
        //by yszhu
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);


        GLES30.glDrawRangeElements(GLES30.GL_TRIANGLE_STRIP,2,9,6, GLES30.GL_UNSIGNED_SHORT,indicesBuffer.position(2));
        //when Specifies the start position ,the start position of indiecsBuffer(the last param) must be changed just like change
        // start pointer of array in C
        //by yszhu

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    private void drawRestartEntity(){
        GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,0,floatBuffer);
        GLES30.glVertexAttribPointer(1,4,GLES30.GL_FLOAT,false,0,colorBuffer);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glEnable(GLES30.GL_PRIMITIVE_RESTART_FIXED_INDEX);


        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP,9, GLES30.GL_UNSIGNED_SHORT,indiecsRestartBuffer.position(0));

        //GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP,5, GLES30.GL_UNSIGNED_SHORT,indicesBuffer.position(4));
        //in C,the last param is the Start position of index Array,so it can be used like indicesBuffer+4 to change
        // first element of index .in Java used like indicesBuffer.position(4)
        //by yszhu

        GLES30.glDisable(GLES30.GL_PRIMITIVE_RESTART_FIXED_INDEX);
        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    private void drawDegenerateTriangle(){
        GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,0,floatBuffer);
        GLES30.glVertexAttribPointer(1,4,GLES30.GL_FLOAT,false,0,colorBuffer);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);



        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP,9, GLES30.GL_UNSIGNED_SHORT,indicesGegenerateTriangelBuffer.position(0));

        //GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP,5, GLES30.GL_UNSIGNED_SHORT,indicesBuffer.position(4));
        //in C,the last param is the Start position of index Array,so it can be used like indicesBuffer+4 to change
        // first element of index .in Java used like indicesBuffer.position(4)
        //by yszhu

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glViewport(0,0,width,height);

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glUseProgram(programObject);

        switch (drawAPI){
            case DRAW_ARRAY:
                drawWithDrawArray();
                break;
            case DRAW_ELEMENT:
                drawWithElements();
                break;
            case DRAW_RANG_ELEMENT:
                drawWithRangElements();
                break;
            case DRAW_RESART_ENTITY:
                drawRestartEntity();
                break;
            case DRAW_DEGENERATE_TRIANGLE:
                drawDegenerateTriangle();
                break;
        }

    }
}
