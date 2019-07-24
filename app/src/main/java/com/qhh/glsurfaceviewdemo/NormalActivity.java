package com.qhh.glsurfaceviewdemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qhh.glsurfaceviewdemo.opengl.render.NormalRender;

public class NormalActivity extends AppCompatActivity {

    private GLSurfaceView mGlSurfaceView;
    private NormalRender mNormalRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        mGlSurfaceView = findViewById(R.id.gl_surface);
        mGlSurfaceView.setEGLContextClientVersion(2);
        mNormalRender = new NormalRender(this);
        mGlSurfaceView.setRenderer(mNormalRender);
    }
}
