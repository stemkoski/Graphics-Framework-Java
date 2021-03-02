uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform bool waves;

uniform float time;
in vec3 vertexPosition;
in vec2 vertexUV;
out vec2 UV;

void main() {

    vec4 pos = vec4(vertexPosition, 1.0);
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * pos;
    UV = vertexUV;

    if (waves) {
        float offset = 0.1 * sin(0.5 * vertexPosition.x + time);
        vec3 pos = vertexPosition + vec3(0.0, offset, 0.0);
        gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(pos, 1);
    }

}//end main
