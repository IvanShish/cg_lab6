import com.jogamp.opengl.GL2;

public enum Lights {
    GL_LIGHT0(GL2.GL_LIGHT0),
    GL_LIGHT1(GL2.GL_LIGHT1),
    GL_LIGHT2(GL2.GL_LIGHT2);
    public int code;

    Lights(int code) {
        this.code = code;
    }
}
