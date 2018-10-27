package com.example.admin.openglesnote.renderers;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import com.example.admin.openglesnote.common.ESShader;
import com.example.admin.openglesnote.common.ESShapes;
import com.example.admin.openglesnote.common.ESTransform;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SimpleVertexShaderRenderer extends BasicRenderer
{

    ///
    // Constructor
    //
    public SimpleVertexShaderRenderer(Context context )
    {

    }

    ///
    // Initialize the shader and program object
    //
    public void onSurfaceCreated (GL10 glUnused, EGLConfig config )
    {
        String vShaderStr =
                "#version 300 es 							 \n" +
                        "uniform mat4 u_mvpMatrix;                   \n" +
                        "layout(location = 0) in vec4 a_position;    \n" +
                        "layout(location = 1) in vec4 a_color;       \n" +
                        "out vec4 v_color;                           \n" +
                        "void main()                                 \n" +
                        "{                                           \n" +
                        "   v_color = a_color;                       \n" +
                        "   gl_Position = u_mvpMatrix * a_position;  \n" +
                        "}                                           \n";

        String fShaderStr =
                "#version 300 es 							 \n" +
                        "precision mediump float;                    \n" +
                        "in vec4 v_color;                            \n" +
                        "layout(location = 0) out vec4 outColor;     \n" +
                        "void main()                                 \n" +
                        "{                                           \n" +
                        "  outColor = v_color;                       \n" +
                        "}                                           \n";

        // Load the shaders and get a linked program object
        mProgramObject = ESShader.loadProgram ( vShaderStr, fShaderStr );

        // Get the uniform locations
        mMVPLoc = GLES30.glGetUniformLocation ( mProgramObject, "u_mvpMatrix" );

        // Generate the vertex data
        mCube.genCube ( 1.0f );

        // Starting rotation angle for the cube
        mAngle = 45.0f;

        GLES30.glClearColor ( 1.0f, 1.0f, 1.0f, 0.0f );
    }

    private void update()
    {
        if ( mLastTime == 0 )
        {
            mLastTime = SystemClock.uptimeMillis();
        }

        long curTime = SystemClock.uptimeMillis();
        long elapsedTime = curTime - mLastTime;
        float deltaTime = elapsedTime / 1000.0f;
        mLastTime = curTime;

        ESTransform perspective = new ESTransform();
        ESTransform modelview = new ESTransform();
        float aspect;

        // Compute a rotation angle based on time to rotate the cube
        mAngle += ( deltaTime * 40.0f );

        if ( mAngle >= 360.0f )
        {
            mAngle -= 360.0f;
        }

        // Compute the window aspect ratio
        aspect = ( float ) mWidth / ( float ) mHeight;

        // Generate a perspective matrix with a 60 degree FOV
        perspective.matrixLoadIdentity();
        perspective.perspective ( 60.0f, aspect, 1.0f, 20.0f );

        // Generate a model view matrix to rotate/translate the cube
        modelview.matrixLoadIdentity();

        // Translate away from the viewer
        modelview.translate ( 0.0f, 0.0f, -2.0f );

        // Rotate the cube
        modelview.rotate ( mAngle, 1.0f, 0.0f, 1.0f );

        // Compute the final MVP by multiplying the
        // modevleiw and perspective matrices together
        mMVPMatrix.matrixMultiply ( modelview.get(), perspective.get() );
    }

    ///
    // Draw a triangle using the shader pair created in onSurfaceCreated()
    //
    public void onDrawFrame ( GL10 glUnused )
    {
        update();

        // Set the viewport
        GLES30.glViewport ( 0, 0, mWidth, mHeight );

        // Clear the color buffer
        GLES30.glClear ( GLES30.GL_COLOR_BUFFER_BIT );

        // Use the program object
        GLES30.glUseProgram ( mProgramObject );

        // Load the vertex data
        GLES30.glVertexAttribPointer ( 0, 3, GLES30.GL_FLOAT, false,
                0, mCube.getVertices() );
        GLES30.glEnableVertexAttribArray ( 0 );

        // Set the vertex color to red
        GLES30.glVertexAttrib4f ( 1, 1.0f, 0.0f, 0.0f, 1.0f );

        // Load the MVP matrix
        GLES30.glUniformMatrix4fv ( mMVPLoc, 1, false,
                mMVPMatrix.getAsFloatBuffer() );

        // Draw the cube
        GLES30.glDrawElements ( GLES30.GL_TRIANGLES, mCube.getNumIndices(),
                GLES30.GL_UNSIGNED_SHORT, mCube.getIndices() );
    }

    ///
    // Handle surface changes
    //
    public void onSurfaceChanged ( GL10 glUnused, int width, int height )
    {
        mWidth = width;
        mHeight = height;
    }

    // Handle to a program object
    private int mProgramObject;

    // Uniform locations
    private int mMVPLoc;

    // Vertex data
    private ESShapes mCube = new ESShapes();

    // Rotation angle
    private float mAngle;

    // MVP matrix
    private ESTransform mMVPMatrix = new ESTransform();

    // Additional Member variables
    private int mWidth;
    private int mHeight;
    private long mLastTime = 0;
}
