import static org.lwjgl.opengl.GL40.*;
public class OpenGLUtils
{
    // for storing debug codes
    static int[] status = new int[1];

    public static int initializeShader(String shaderCode, int shaderType)
    {
        // specify specific OpenGL version and requirements
        String extension = "#extension GL_ARB_shading_language_420pack : require \n";
        shaderCode = "#version 130 \n" + extension + shaderCode;

        // create empty shader object and return reference value
        int shaderRef = glCreateShader(shaderType);

        // stores the source code in the shader
        glShaderSource(shaderRef, shaderCode);

        // compiles source code previously stored in the shader object
        glCompileShader(shaderRef);

        // query whether shader compile was successful
        glGetShaderiv(shaderRef, GL_COMPILE_STATUS, status);
        if (status[0] == GL_FALSE) {
            // retrieve error message
            String errorMessage = glGetShaderInfoLog(shaderRef);
            // free memory used to store shader program
            glDeleteShader(shaderRef);
            // convert byte string to character string
            //        errorMessage = '\n' + errorMessage.decode('utf-8')
            // raise exception:halt program and print error message
            throw new RuntimeException(errorMessage);
        }

        // compilation was successful;
        // return shader reference value
        return shaderRef;
    }

    public static int initializeProgram(String vertexShaderCode, String fragmentShaderCode) {

        int vertexShaderRef = OpenGLUtils.initializeShader(vertexShaderCode, GL_VERTEX_SHADER);
        int fragmentShaderRef = OpenGLUtils.initializeShader(fragmentShaderCode, GL_FRAGMENT_SHADER);

        // create empty program object and store reference to it
        int programRef = glCreateProgram();

        // attach previously compiled shader programs
        glAttachShader(programRef, vertexShaderRef);
        glAttachShader(programRef, fragmentShaderRef);

        // link vertex shader to fragment shader
        glLinkProgram(programRef);

        // query whether program link was successful
        glGetProgramiv(programRef, GL_LINK_STATUS, status);
        if (status[0] == GL_FALSE) {
            // retrieve error message
            String errorMessage = glGetProgramInfoLog(programRef);
            // free memory used to store program
            glDeleteProgram(programRef);
            // convert byte string to character string
            // errorMessage = '\n' + errorMessage.decode('utf-8')
            // raise exception:halt application and print error message
            throw new RuntimeException(errorMessage);
        }

        // linking was successful; return program reference value
        return programRef;
    }

    public static void checkVersion()
    {
        System.out.println("Vendor: " + glGetString(GL_VENDOR) );
        System.out.println("Renderer: " + glGetString(GL_RENDERER) );
        System.out.println("OpenGL version supported: " + glGetString(GL_VERSION) );
    }
}



