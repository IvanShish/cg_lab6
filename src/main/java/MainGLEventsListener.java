import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


public class MainGLEventsListener implements GLEventListener {

    private Point[] allBodyPoints;
    private Point[] topBodyPoints;
    private Point[] bottomBodyPoints;
    private Point[] neckPoints;
    private Point[] headPoints;

    private int xTurnPrev;
    private int xTurnCur;
    private int yTurnPrev;
    private int yTurnCur;
    private int zTurnPrev;
    private int zTurnCur;

    public void init(GLAutoDrawable glAutoDrawable) {
        allBodyPoints = new Point[]{new Point(-0.3, -1, 0), new Point(0.3, -1, 0), new Point(0.4, -0.8, 0), new Point(0.4, -0.6, 0),
                new Point(0.3, -0.4, 0), new Point(0.2, -0.3, 0), new Point(0.25, -0.2, 0), new Point(0.25, -0.1, 0), new Point(0.2, 0, 0),
                new Point(-0.2, 0, 0), new Point(-0.25, -0.1, 0), new Point(-0.25, -0.2, 0), new Point(-0.2, -0.3, 0),
                new Point(-0.3, -0.4, 0), new Point(-0.4, -0.6, 0), new Point(-0.4, -0.8, 0), new Point(-0.3, -1, 0)};

        topBodyPoints = new Point[]{new Point(0.2, -0.3, 0), new Point(0.25, -0.2, 0), new Point(0.25, -0.1, 0), new Point(0.2, 0, 0),
                new Point(-0.2, 0, 0), new Point(-0.25, -0.1, 0), new Point(-0.25, -0.2, 0), new Point(-0.2, -0.3, 0)};

        bottomBodyPoints = new Point[]{new Point(-0.3, -1, 0), new Point(0.3, -1, 0), new Point(0.4, -0.8, 0), new Point(0.4, -0.6, 0),
                new Point(0.3, -0.4, 0), new Point(0.2, -0.3, 0), new Point(-0.2, -0.3, 0), new Point(-0.3, -0.4, 0),
                new Point(-0.4, -0.6, 0), new Point(-0.4, -0.8, 0), new Point(-0.3, -1, 0)};

        neckPoints = new Point[]{new Point(-0.05, 0, 0), new Point(-0.05, 0.8, 0), new Point(0.05, 0.8, 0), new Point(0.05, 0, 0)};

        headPoints = new Point[]{new Point(-0.05, 0.8, 0), new Point(-0.1, 0.85, 0), new Point(-0.05, 1, 0),
                new Point(0.05, 1, 0), new Point(0.1, 0.85, 0), new Point(0.05, 0.8, 0)};
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        gl.glRotated(xTurnCur - xTurnPrev, 1, 0, 0);
        gl.glRotated(yTurnCur - yTurnPrev, 0, 1, 0);
        gl.glRotated(zTurnCur - zTurnPrev, 0, 0, 1);
        xTurnPrev = xTurnCur;
        yTurnPrev = yTurnCur;
        zTurnPrev = zTurnCur;

        drawBody(gl);
        drawNeck(gl);
        drawHead(gl);
    }

    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    private void drawBody(GL2 gl) {
        drawBottomBody(gl);
        drawTopBody(gl);
        drawSideBody(gl);
    }

    private void drawBottomBody(GL2 gl) {
        gl.glColor3d(0.85, 0.64, 0.12);
        drawPoly(gl, bottomBodyPoints, 0);
        drawPoly(gl, bottomBodyPoints, 0.1);
    }

    private void drawTopBody(GL2 gl) {
        gl.glColor3d(0.85, 0.64, 0.12);
        drawPoly(gl, topBodyPoints, 0);
        drawPoly(gl, topBodyPoints, 0.1);
    }

    private void drawSideBody(GL2 gl) {
        gl.glColor3d(0.72, 0.52, 0.04);
        drawQuads(gl, allBodyPoints, 0.1);
    }

    private void drawNeck(GL2 gl) {
        gl.glColor3d(0.85, 0.64, 0.12);
        drawPoly(gl, neckPoints, 0);
        drawPoly(gl, neckPoints, 0.1);
        drawSideNeck(gl);
    }

    private void drawSideNeck(GL2 gl) {
        gl.glColor3d(0.72, 0.52, 0.04);
        drawQuads(gl, neckPoints, 0.1);
    }

    private void drawHead(GL2 gl) {
        gl.glColor3d(0.85, 0.64, 0.12);
        drawPoly(gl, headPoints, 0);
        drawPoly(gl, headPoints, 0.1);
        drawSideHead(gl);
    }

    private void drawSideHead(GL2 gl) {
        gl.glColor3d(0.72, 0.52, 0.04);
        drawQuads(gl, headPoints, 0.1);
    }

    private void drawPoly(GL2 gl, Point[] points, double z) {
        gl.glBegin(GL2.GL_POLYGON);
        for (Point p : points) {
            gl.glVertex3d(p.x, p.y, z);
        }
        gl.glEnd();
    }

    private void drawQuads(GL2 gl, Point[] points, double z) {
        gl.glBegin(GL2.GL_QUADS);
        for(int i = 0; i < points.length; i++) {
            int j = i + 1;
            if(j >= points.length) {
                j = 0;
            }

            gl.glVertex3d(points[i].x, points[i].y, 0);
            gl.glVertex3d(points[i].x, points[i].y, z);
            gl.glVertex3d(points[j].x, points[j].y, z);
            gl.glVertex3d(points[j].x, points[j].y, 0);
        }
        gl.glEnd();
    }

    public void setxTurnCur(int xTurnCur) {
        this.xTurnCur = xTurnCur;
    }

    public void setyTurnCur(int yTurnCur) {
        this.yTurnCur = yTurnCur;
    }

    public void setzTurnCur(int zTurnCur) {
        this.zTurnCur = zTurnCur;
    }
}
