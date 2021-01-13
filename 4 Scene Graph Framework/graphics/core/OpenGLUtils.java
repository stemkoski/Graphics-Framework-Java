package graphics.core;

import static org.lwjgl.opengl.GL40.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OpenGLUtils
{
    public static int initFromFiles( 
        String vertexShaderFileName, String fragmentShaderFileName )
    {
        return initProgram(
            readFile(vertexShaderFileName), 
            readFile(fragmentShaderFileName) );
    }

    public static String readFile(String fileName) 
    {
        String text = ""; 
        try 
        { 
            text = new String(Files.readAllBytes(Paths.get(fileName))); 
        } 
        catch (Exception ex) 
        { 
            ex.printStackTrace(); 
        } 
        return text; 
    }

    // for storing debug codes
    static int[] status = new int[1];

    public static int initShader(String shaderCode, int shaderType)
    {
        // specify specific OpenGL version
        shaderCode = "#version 330 \n" + shaderCode;

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

    public static int initProgram(String vertexShaderCode, String fragmentShaderCode) {

        int vertexShaderRef   = initShader(vertexShaderCode, GL_VERTEX_SHADER);
        int fragmentShaderRef = initShader(fragmentShaderCode, GL_FRAGMENT_SHADER);

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



