package com.qhh.glsurfaceviewdemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qhh.glsurfaceviewdemo.opengl.render.TextureRender;

public class TextureActivity extends AppCompatActivity {

    private GLSurfaceView mGlSurfaceView;
    private TextureRender mTextureRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture);
        mGlSurfaceView = findViewById(R.id.gl_surface);
        mGlSurfaceView.setEGLContextClientVersion(2);
        mTextureRender = new TextureRender(this);
        mGlSurfaceView.setRenderer(mTextureRender);

    }
}
