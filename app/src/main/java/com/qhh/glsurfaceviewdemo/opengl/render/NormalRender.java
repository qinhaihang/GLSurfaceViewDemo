package com.qhh.glsurfaceviewdemo.opengl.render;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.qhh.glsurfaceviewdemo.opengl.glprogram.NormalGLProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static com.qhh.glsurfaceviewdemo.opengl.Constants.BYTES_PER_FLOAT;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/7/24 10:57
 * @des
 * @packgename com.qhh.glsurfaceviewdemo.opengl.render
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class NormalRender implements GLSurfaceView.Renderer {

    protected float[] mVertexArray = new float[] { // OpenGL的坐标是[-1, 1]，这里的Vertex正好定义了一个居中的正方形
            // triangle fan x, y
            0,     0,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f,  0.5f,
            -0.5f,  0.5f,
            -0.5f, -0.5f
    };
    protected FloatBuffer mVertexBuffer;
    protected float[] mProjectionMatrix = new float[16];
    private final NormalGLProgram mNormalGLProgram;

    public NormalRender(Context context) {
        mNormalGLProgram = new NormalGLProgram(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d("qhh_normal","onSurfaceCreated");
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        mVertexBuffer = ByteBuffer.allocateDirect(mVertexArray.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVertexArray);

        mNormalGLProgram.buildProgram();

        // 填充数据
        glUniform4f(mNormalGLProgram.getuColorLocation(), 1, 0.5f, 0, 1);
        mVertexBuffer.position(0);
        glVertexAttribPointer(mNormalGLProgram.getaPositionLocation(), 2, GL_FLOAT, false, 0, mVertexBuffer);
        //通知openGL使用顶点进行绘制
        glEnableVertexAttribArray(mNormalGLProgram.getaPositionLocation());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d("qhh_normal","onSurfaceChanged");
        glViewport(0, 0, width, height);
        // 正交变换，只考虑竖屏的情况
        float rate = height * 1.0f / width;
        Matrix.orthoM(mProjectionMatrix, 0, -1, 1, -rate, rate, -1, 1); // 正交变换，防止界面拉伸
        glUniformMatrix4fv(mNormalGLProgram.getuMatrixLocation(), 1, false, mProjectionMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d("qhh_normal","onDrawFrame");
        glClear(GL_COLOR_BUFFER_BIT);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
}
