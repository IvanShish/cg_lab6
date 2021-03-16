import com.jogamp.opengl.GL2;

public enum Lights {
    GL_LIGHT0(GL2.GL_LIGHT0) {
        @Override
        public String toString() {
            return "Направленный";
        }
    },
    GL_LIGHT1(GL2.GL_LIGHT1) {
        @Override
        public String toString() {
            return "Точечный";
        }
    },
    GL_LIGHT2(GL2.GL_LIGHT2) {
        @Override
        public String toString() {
            return "Прожектор";
        }
    };
    public int code;

    Lights(int code) {
        this.code = code;
    }
}
