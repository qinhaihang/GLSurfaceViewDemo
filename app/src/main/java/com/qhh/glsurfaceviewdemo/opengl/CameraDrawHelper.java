package com.qhh.glsurfaceviewdemo.opengl;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.qhh.glsurfaceviewdemo.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.glUseProgram;
import static com.qhh.glsurfaceviewdemo.opengl.Constants.BYTES_PER_FLOAT;
import static com.qhh.glsurfaceviewdemo.opengl.Constants.BYTES_PER_SHORT;

/**
 * @author qinhaihang
 * @version $Rev$
 * @time 19-7-28 下午9:49
 * @des
 * @packgename com.qhh.glsurfaceviewdemo.opengl
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class CameraDrawHelper {

    private int textureId;
    private Context mContext;

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    static float squareCoords[] = {
            -1.0f,  1.0f,
            -1.0f, -1.0f,
            1.0f, -1.0f,
            1.0f,  1.0f,
    };

    static float textureVertices[] = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f,
    };
    private final FloatBuffer mVertexBuffer;
    private final ShortBuffer mDrawListBuffer;
    private final FloatBuffer mTextureVerticesBuffer;
    private final int mProgram;

    private int mPositionHandle;
    private int mTextureCoordHandle;

    // number of coordinates per vertex in this array
    private static final int COORDS_PER_VERTEX = 2;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    public CameraDrawHelper(Context context,int textureId) {
        this.textureId = textureId;
        mContext = context;

        mVertexBuffer = ByteBuffer.allocateDirect(squareCoords.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(squareCoords);

        mVertexBuffer.position(0);

        mDrawListBuffer = ByteBuffer.allocateDirect(drawOrder.length * BYTES_PER_SHORT)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(drawOrder);
        mDrawListBuffer.position(0);

        mTextureVerticesBuffer = ByteBuffer.allocateDirect(textureVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(textureVertices);
        mTextureVerticesBuffer.position(0);

        String vertexShader = TextureUtils.readShaderCodeFromResource(mContext, R.raw.camera_vertex_shader);
        String fragmentShader = TextureUtils.readShaderCodeFromResource(mContext, R.raw.camera_fragment_shader);

        int vertexShaderHandle = ShaderHelper.compileVertexShader(vertexShader);
        int fragmentShaderHandle = ShaderHelper.compileFragmentShader(fragmentShader);

        mProgram = ShaderHelper.linkProgram(vertexShaderHandle, fragmentShaderHandle);

    }

    public void draw(float[] mtx){
        glUseProgram(mProgram);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the <insert shape here> coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, mVertexBuffer);

        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate");
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);

        //textureVerticesBuffer.clear();
        //textureVerticesBuffer.put( transformTextureCoordinates( textureVertices, mtx ));
        //textureVerticesBuffer.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, vertexStride, mTextureVerticesBuffer);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, mDrawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureCoordHandle);
    }

    private float[] transformTextureCoordinates( float[] coords, float[] matrix) {
        float[] result = new float[ coords.length ];
        float[] vt = new float[4];

        for ( int i = 0 ; i < coords.length ; i += 2 ) {
            float[] v = { coords[i], coords[i+1], 0 , 1  };
            Matrix.multiplyMV(vt, 0, matrix, 0, v, 0);
            result[i] = vt[0];
            result[i+1] = vt[1];
        }
        return result;
    }
}
