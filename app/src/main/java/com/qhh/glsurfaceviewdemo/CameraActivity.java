package com.qhh.glsurfaceviewdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qhh.glsurfaceviewdemo.opengl.view.CameraGLSurfaceView;
import com.qhh.permission.PermissionHelper;
import com.sensetime.cameralibrary.CameraConfig;

public class CameraActivity extends AppCompatActivity {

    private CameraGLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGlSurfaceView = new CameraGLSurfaceView(this);
        setContentView(mGlSurfaceView);

        PermissionHelper.getInstance().init(this)
                .setmRequestCallback((flag -> initCamera()))
                .checkPermission(Manifest.permission.CAMERA);

        CameraConfig cameraConfig = new CameraConfig.Builder()
                .setPreviewBuffer(false)
                .setCameraType(CameraConfig.FRONT_CAMERA)
                .builer();
    }

    private void initCamera() {

    }
}
