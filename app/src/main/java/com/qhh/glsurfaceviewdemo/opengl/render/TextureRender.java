package com.qhh.glsurfaceviewdemo.opengl.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;

import com.qhh.glsurfaceviewdemo.R;
import com.qhh.glsurfaceviewdemo.opengl.glprogram.TextureGLProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLUtils.texImage2D;
import static android.opengl.Matrix.orthoM;
import static com.qhh.glsurfaceviewdemo.opengl.Constants.BYTES_PER_FLOAT;

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

    protected float[] mVertexArray = new float[] { // OpenGL的坐标是[-1, 1]，这里的Vertex正好定义了一个居中的正方形
            // Triangle Fan x, y
            0f,    0f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f,  0.5f,
            -0.5f,  0.5f,
            -0.5f, -0.5f
    };
    protected float[] mTextureArray = new float[] {
            0.5f, 0.5f,
            0f, 1f,
            1f, 1f,
            1f, 0f,
            0f, 0f,
            0f, 1f
    };
    protected FloatBuffer mVertexBuffer;
    protected FloatBuffer mTextureBuffer;
    protected float[] mProjectionMatrix = new float[16];

    private final TextureGLProgram mTextureGLProgram;

    public TextureRender(Context context) {
        mContext = context;
        mTextureGLProgram = new TextureGLProgram(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        mVertexBuffer = ByteBuffer.allocateDirect(mVertexArray.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVertexArray);
        mTextureBuffer = ByteBuffer.allocateDirect(mTextureArray.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mTextureArray);

        mTextureGLProgram.buildProgram();

        final int[] textureObjectIds = new int[1];
        glGenTextures(1, textureObjectIds, 0);
        int textureId = textureObjectIds[0];
        final BitmapFactory.Options options = new BitmapFactory.Options();
        final Bitmap bitmap = BitmapFactory.decodeResource(
                mContext.getResources(), R.drawable.face, options);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        glGenerateMipmap(GL_TEXTURE_2D);
        bitmap.recycle();
        // 使用该Texture
        glActiveTexture(GL_TEXTURE0);
        glUniform1i(mTextureGLProgram.getuTextureUnitLocation(), 0);
        // 填充数据
        mVertexBuffer.position(0);
        glVertexAttribPointer(mTextureGLProgram.getaPositionLocation(), 2, GL_FLOAT, false, 0, mVertexBuffer);
        glEnableVertexAttribArray(mTextureGLProgram.getaPositionLocation());
        mTextureBuffer.position(0);
        glVertexAttribPointer(mTextureGLProgram.getaTextureCoordinatesLocation(), 2, GL_FLOAT, false, 0, mTextureBuffer);
        glEnableVertexAttribArray(mTextureGLProgram.getaTextureCoordinatesLocation());

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        // 正交投影
        float rate = (float) height / width;
        orthoM(mProjectionMatrix, 0, -1, 1, -rate, rate, -1, 1);
        // 赋值
        glUniformMatrix4fv(mTextureGLProgram.getuMatrixLocation(), 1, false, mProjectionMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
}
