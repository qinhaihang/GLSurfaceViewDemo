package com.qhh.glsurfaceviewdemo.opengl.glprogram;

import android.content.Context;

import com.qhh.glsurfaceviewdemo.R;
import com.qhh.glsurfaceviewdemo.opengl.ShaderHelper;
import com.qhh.glsurfaceviewdemo.opengl.TextureUtils;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUseProgram;

/**
 * @author qinhaihang
 * @version $Rev$
 * @time 19-7-24 下午10:57
 * @des
 * @packgename com.qhh.glsurfaceviewdemo.opengl.glprogram
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class TextureGLProgram {

    private Context mContext;

    private int mProgram;

    protected int uMatrixLocation;
    protected int aPositionLocation;
    protected int aTextureCoordinatesLocation;
    protected int uTextureUnitLocation;

    public TextureGLProgram(Context context) {
        mContext = context;
    }

    public void buildProgram(){

        if(mProgram <= 0){
            String vertexShaderStr = TextureUtils.readShaderCodeFromResource(mContext,
                    R.raw.texture_vertex_shader);
            String fragmentShaderStr = TextureUtils.readShaderCodeFromResource(mContext,
                    R.raw.texture_fragment_shader);

            int vertexShaderId = ShaderHelper.compileVertexShader(vertexShaderStr);
            int fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShaderStr);

            mProgram = ShaderHelper.linkProgram(vertexShaderId,fragmentShaderId);
        }

        // 启用这个Program
        glUseProgram(mProgram);
        // 找到需要赋值的变量
        uMatrixLocation = glGetUniformLocation(mProgram, "u_Matrix");
        aPositionLocation = glGetAttribLocation(mProgram, "a_Position");
        aTextureCoordinatesLocation = glGetAttribLocation(mProgram, "a_TextureCoordinates");
        uTextureUnitLocation = glGetUniformLocation(mProgram, "u_TextureUnit");

    }

    public int getuMatrixLocation() {
        return uMatrixLocation;
    }

    public int getaPositionLocation() {
        return aPositionLocation;
    }

    public int getaTextureCoordinatesLocation() {
        return aTextureCoordinatesLocation;
    }

    public int getuTextureUnitLocation() {
        return uTextureUnitLocation;
    }
}
