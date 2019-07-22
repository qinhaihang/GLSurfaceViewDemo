package com.qhh.glsurfaceviewdemo.opengl;

import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glLinkProgram;

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
public class GLParams {

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
