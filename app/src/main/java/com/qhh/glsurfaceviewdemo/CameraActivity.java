package com.qhh.glsurfaceviewdemo;

import android.Manifest;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qhh.permission.PermissionHelper;

public class CameraActivity extends AppCompatActivity {

    private GLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGlSurfaceView = new GLSurfaceView(this);
        mGlSurfaceView.setEGLContextClientVersion(2);
        mGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(mGlSurfaceView);

        PermissionHelper.getInstance().init(this)
                .setmRequestCallback((flag -> {
                    initCamera();
                }))
                .checkPermission(Manifest.permission.CAMERA);

    }

    private void initCamera() {

    }
}
