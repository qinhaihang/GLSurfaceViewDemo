package com.qhh.glsurfaceviewdemo.opengl;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
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

    public static int compileVertexShader(String vertexStr){
        return compileShader(GL_VERTEX_SHADER,vertexStr);
    }

    public static int compileFragmentShader(String fragmentStr){
        return compileShader(GL_FRAGMENT_SHADER,fragmentStr);
    }

    /**
     * Links a vertex shader and a fragment shader together into an OpenGL
     * program. Returns the OpenGL program object ID, or 0 if linking failed.
     */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {

        // Create a new program object.
        final int programObjectId = glCreateProgram();

        if (programObjectId == 0) {
            return 0;
        }

        // Attach the vertex shader to the program.
        glAttachShader(programObjectId, vertexShaderId);

        // Attach the fragment shader to the program.
        glAttachShader(programObjectId, fragmentShaderId);

        // Link the two shaders together into a program.
        glLinkProgram(programObjectId);

        // Get the link status.
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS,
                linkStatus, 0);

        // Verify the link status.
        if (linkStatus[0] == 0) {
            // If it failed, delete the program object.
            glDeleteProgram(programObjectId);

            return 0;
        }

        // Return the program object ID.
        return programObjectId;
    }

}
