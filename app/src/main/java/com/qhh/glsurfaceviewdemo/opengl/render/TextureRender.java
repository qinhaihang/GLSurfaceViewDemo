package com.qhh.glsurfaceviewdemo.opengl.render;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glClearColor;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/7/24 15:05
 * @des
 * @packgename com.qhh.glsurfaceviewdemo.opengl.render
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class TextureRender implements GLSurfaceView.Renderer {

    private Context mContext;

    public TextureRender(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
