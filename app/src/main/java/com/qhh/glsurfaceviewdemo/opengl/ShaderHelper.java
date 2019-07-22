package com.qhh.glsurfaceviewdemo.opengl;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glShaderSource;

/**
 * @author qinhaihang
 * @version $Rev$
 * @time 19-7-22 下午10:17
 * @des
 * @packgename com.qhh.glsurfaceviewdemo.opengl
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class ShaderHelper {

    private static int createShader(int type){
        int shaderId = glCreateShader(type);
        return shaderId;
    }

    private static int compileShader(int type,String vertexStr) {
        int vertextShaderId = createShader(type);
        glShaderSource(vertextShaderId,vertexStr);
        glCompileShader(vertextShaderId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(vertextShaderId, GL_COMPILE_STATUS,
                compileStatus, 0);

        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.
            glDeleteShader(vertextShaderId);
            return 0;
        }

        return vertextShaderId;
    }

    public static int createVertexShader(String vertexStr){
        return compileShader(GL_VERTEX_SHADER,vertexStr);
    }

    public static int createFragmentShader(String fragmentStr){
        return compileShader(GL_FRAGMENT_SHADER,fragmentStr);
    }

}
