in vec3 position;
in vec3 vertexColor;
out vec3 color;
void main()
{
    gl_Position = vec4(position.x, position.y, position.z, 1.0);
    color = vertexColor;
}