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
 * @time 19-7-20 下午8:55
 * @des
 * @packgename com.qhh.glsurfaceviewdemo.opengl
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class NormalGLProgram {

    private Context mContext;
    private int mProgram;

    private int uMatrixLocation;
    private int aPositionLocation;
    private int uColorLocation;

    public NormalGLProgram(Context context) {
        mContext = context;
    }

    public void buildProgram(){

        if(mProgram <= 0){
            String vertexShader = TextureUtils.readShaderCodeFromResource(mContext, R.raw.color_vertex_shader);
            String fragmentShader = TextureUtils.readShaderCodeFromResource(mContext, R.raw.color_fragment_shader);

            //创建并且编译shader
            int vertexShaderId = ShaderHelper.compileVertexShader(vertexShader);
            int fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShader);

            mProgram = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId);
        }

        //启动 program
        glUseProgram(mProgram);
        // 找到需要赋值的变量
        uMatrixLocation = glGetUniformLocation(mProgram, "u_Matrix");
        aPositionLocation = glGetAttribLocation(mProgram, "a_Position");
        uColorLocation = glGetUniformLocation(mProgram, "u_Color");

    }

    public int getuMatrixLocation() {
        return uMatrixLocation;
    }

    public int getaPositionLocation() {
        return aPositionLocation;
    }

    public int getuColorLocation() {
        return uColorLocation;
    }
}
