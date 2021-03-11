import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


public class MainGLEventsListener implements GLEventListener {

    private Point[] allBodyPoints;
    private Point[] topBodyPoints;
    private Point[] bottomBodyPoints;
    private Point[] neckPoints;
    private Point[] headPoints;
    private Point[] doorstepPoints;
    private Point[][] stringsPoints;

    private int xTurnPrev;
    private int xTurnCur;
    private int yTurnPrev;
    private int yTurnCur;
    private int zTurnPrev;
    private int zTurnCur;

    private boolean isDepthTestOn = true;

    private double step = 0.0001;

    public void init(GLAutoDrawable glAutoDrawable) {
        allBodyPoints = new Point[]{new Point(-0.3, -1, 0), new Point(0.3, -1, 0), new Point(0.4, -0.8, 0), new Point(0.4, -0.6, 0),
                new Point(0.3, -0.4, 0), new Point(0.2, -0.3, 0), new Point(0.25, -0.2, 0), new Point(0.25, -0.1, 0), new Point(0.2, 0, 0),
                new Point(-0.2, 0, 0), new Point(-0.25, -0.1, 0), new Point(-0.25, -0.2, 0), new Point(-0.2, -0.3, 0),
                new Point(-0.3, -0.4, 0), new Point(-0.4, -0.6, 0), new Point(-0.4, -0.8, 0)/*, new Point(-0.3, -1, 0)*/};

        topBodyPoints = new Point[]{new Point(0.2, -0.3, 0), new Point(0.25, -0.2, 0), new Point(0.25, -0.1, 0), new Point(0.2, 0, 0),
                new Point(-0.2, 0, 0), new Point(-0.25, -0.1, 0), new Point(-0.25, -0.2, 0), new Point(-0.2, -0.3, 0)};

        bottomBodyPoints = new Point[]{new Point(-0.3, -1, 0), new Point(0.3, -1, 0), new Point(0.4, -0.8, 0), new Point(0.4, -0.6, 0),
                new Point(0.3, -0.4, 0), new Point(0.2, -0.3, 0), new Point(-0.2, -0.3, 0), new Point(-0.3, -0.4, 0),
                new Point(-0.4, -0.6, 0), new Point(-0.4, -0.8, 0)/*, new Point(-0.3, -1, 0)*/};

        neckPoints = new Point[]{new Point(-0.05, 0, 0), new Point(-0.05, 0.8, 0), new Point(0.05, 0.8, 0), new Point(0.05, 0, 0)};

        headPoints = new Point[]{new Point(-0.05, 0.8, 0), new Point(-0.1, 0.85, 0), new Point(-0.05, 1, 0),
                new Point(0.05, 1, 0), new Point(0.1, 0.85, 0), new Point(0.05, 0.8, 0)};

        doorstepPoints = new Point[]{new Point(-0.1, -0.8, -0.02), new Point(-0.1, -0.825, -0.02), new Point(0.1, -0.825, -0.02), new Point(0.1, -0.8, -0.02)};

        stringsPoints = new Point[][]{new Point[]{new Point(-0.04, 1, 0), new Point(-0.04, -0.8, 0)},
                new Point[]{new Point(-0.02, 1, 0), new Point(-0.02, -0.8, 0)},
                new Point[]{new Point(0, 1, 0), new Point(0, -0.8, 0)},
                new Point[]{new Point(0.02, 1, 0), new Point(0.02, -0.8, 0)},
                new Point[]{new Point(0.04, 1, 0), new Point(0.04, -0.8, 0)}};
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        if (isDepthTestOn) {
            gl.glEnable(GL2.GL_DEPTH_TEST);
        } else {
            gl.glDisable(GL2.GL_DEPTH_TEST);
        }

        gl.glRotated(xTurnCur - xTurnPrev, 1, 0, 0);
        gl.glRotated(yTurnCur - yTurnPrev, 0, 1, 0);
        gl.glRotated(zTurnCur - zTurnPrev, 0, 0, 1);
        xTurnPrev = xTurnCur;
        yTurnPrev = yTurnCur;
        zTurnPrev = zTurnCur;

        drawBody(gl);
        drawNeck(gl);
        drawHead(gl);
        drawDoorStep(gl);
        drawCylinder(gl, new Point(0, -0.6, 0), 0.1, 0.1);
        drawStrings(gl);
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

    private void drawDoorStep(GL2 gl) {
        gl.glColor3d(0.62, 0.32, 0.17);
        drawPoly(gl, doorstepPoints, doorstepPoints[0].z);
        drawSideDoorstep(gl);
    }

    private void drawSideDoorstep(GL2 gl) {
        gl.glColor3d(0.54, 0.27, 0.07);
        drawQuads(gl, doorstepPoints, 0);
    }

    private void drawStrings(GL2 gl) {
        gl.glColor3d(0, 0, 0);
        gl.glLineWidth(2);
        gl.glBegin(GL2.GL_LINES);
        for (Point[] points : stringsPoints) {
            gl.glVertex3d(points[0].x, points[0].y, -0.01);
            gl.glVertex3d(points[1].x, points[1].y, -0.01);
        }
        gl.glEnd();
        gl.glLineWidth(1);
    }

    private void drawCylinder(GL2 gl, Point center, double radius,  double h) {
        gl.glColor3d(0.3, 0.3, 0.3);
        Point topPoint = new Point(center.x, center.y + radius, center.z);
        Point curPoint = topPoint;
        gl.glBegin(GL2.GL_LINES);
        for (double angle = step; angle < Math.PI * 2; angle += step) {
            gl.glVertex3d(curPoint.x, curPoint.y, curPoint.z);
            gl.glVertex3d(curPoint.x, curPoint.y, curPoint.z + h);

            gl.glVertex3d(center.x, center.y, center.z - 0.005);
            gl.glVertex3d(curPoint.x, curPoint.y, curPoint.z - 0.005);

            curPoint = turnPoint(topPoint, center, angle);
        }
        gl.glEnd();
    }

    private void drawPoly(GL2 gl, Point[] points, double z) {
//        gl.glBegin(GL2.GL_POLYGON);
//        for (Point p : points) {
//            gl.glVertex3d(p.x, p.y, z);
//        }
//        gl.glEnd();

        Point p0 = points[0];
        for (int i = 1; i < points.length - 1; i++) {
            int j = i + 1;
            drawPolyPart(gl, p0, points[i], points[j], z);
        }
    }

    private void drawPolyPart(GL2 gl, Point p0, Point p1, Point p2, double z) {
        gl.glBegin(GL2.GL_LINES);
        if (p1.x < p2.x && p1.y != p2.y) {
            for (double x = p1.x; x < p2.x; x+=step) {
                double y = -(x * (p1.y - p2.y) + (p1.x * p2.y - p2.x * p1.y))/(p2.x - p1.x);
                gl.glVertex3d(p0.x, p0.y, z);
                gl.glVertex3d(x, y, z);
            }
        } else if (p1.x > p2.x && p1.y != p2.y) {
            for (double x = p1.x; x > p2.x; x-=step) {
                double y = -(x * (p1.y - p2.y) + (p1.x * p2.y - p2.x * p1.y))/(p2.x - p1.x);
                gl.glVertex3d(p0.x, p0.y, z);
                gl.glVertex3d(x, y, z);
            }
        } else if (p1.x == p2.x && p1.y < p2.y) {
            for (double y = p1.y; y < p2.y; y += step) {
                gl.glVertex3d(p0.x, p0.y, z);
                gl.glVertex3d(p1.x, y, z);
            }
        } else if (p1.x == p2.x && p1.y > p2.y) {
            for (double y = p1.y; y > p2.y; y -= step) {
                gl.glVertex3d(p0.x, p0.y, z);
                gl.glVertex3d(p1.x, y, z);
            }
        } else if (p1.y == p2.y && p1.x < p2.x) {
            for (double x = p1.x; x < p2.x; x += step) {
                gl.glVertex3d(p0.x, p0.y, z);
                gl.glVertex3d(x, p1.y, z);
            }
        } else if (p1.y == p2.y && p1.x > p2.x) {
            for (double x = p1.x; x > p2.x; x -= step) {
                gl.glVertex3d(p0.x, p0.y, z);
                gl.glVertex3d(x, p1.y, z);
            }
        } else {
            throw new RuntimeException("Unexpected value: " + p1.toString() + " " + p2.toString());
        }
        gl.glEnd();
    }

    private void drawQuads(GL2 gl, Point[] points, double z) {
//        gl.glBegin(GL2.GL_QUADS);
////        for(int i = 0; i < points.length; i++) {
////            int j = i + 1;
////            if(j >= points.length) {
////                j = 0;
////            }
////
////            gl.glVertex3d(points[i].x, points[i].y, 0);
////            gl.glVertex3d(points[i].x, points[i].y, z);
////            gl.glVertex3d(points[j].x, points[j].y, z);
////            gl.glVertex3d(points[j].x, points[j].y, 0);
////        }
////        gl.glEnd();


        for(int i = 0; i < points.length; i++) {
            int j = i + 1;
            if (j >= points.length) {
                j = 0;
            }

            drawSingleQuad(gl, points[i], points[j], z);
        }
    }

    private void drawSingleQuad(GL2 gl, Point p1, Point p2, double z) {

        gl.glBegin(GL2.GL_LINES);
        if (p1.x < p2.x && p1.y != p2.y) {
            for (double x = p1.x; x < p2.x; x+=step) {
                double y = -(x * (p1.y - p2.y) + (p1.x * p2.y - p2.x * p1.y))/(p2.x - p1.x);
                gl.glVertex3d(x, y, p1.z);
                gl.glVertex3d(x, y, z);
            }
        } else if (p1.x > p2.x && p1.y != p2.y) {
            for (double x = p1.x; x > p2.x; x-=step) {
                double y = -(x * (p1.y - p2.y) + (p1.x * p2.y - p2.x * p1.y))/(p2.x - p1.x);
                gl.glVertex3d(x, y, p1.z);
                gl.glVertex3d(x, y, z);
            }
        } else if (p1.x == p2.x && p1.y < p2.y) {
            for (double y = p1.y; y < p2.y; y += step) {
                gl.glVertex3d(p1.x, y, p1.z);
                gl.glVertex3d(p1.x, y, z);
            }
        } else if (p1.x == p2.x && p1.y > p2.y) {
            for (double y = p1.y; y > p2.y; y -= step) {
                gl.glVertex3d(p1.x, y, p1.z);
                gl.glVertex3d(p1.x, y, z);
            }
        } else if (p1.y == p2.y && p1.x < p2.x) {
            for (double x = p1.x; x < p2.x; x += step) {
                gl.glVertex3d(x, p1.y, p1.z);
                gl.glVertex3d(x, p1.y, z);
            }
        } else if (p1.y == p2.y && p1.x > p2.x) {
            for (double x = p1.x; x > p2.x; x -= step) {
                gl.glVertex3d(x, p1.y, p1.z);
                gl.glVertex3d(x, p1.y, z);
            }
        } else {
            throw new RuntimeException("Unexpected value: " + p1.toString() + " " + p2.toString());
        }
        gl.glEnd();
    }

    private Point turnPoint(Point point, Point center, double angle) {
        Point res = new Point(0, 0, 0);
        res.x = center.x + (point.x - center.x) * Math.cos(angle) - (point.y - center.y) * Math.sin(angle);
        res.y = center.y + (point.x - center.x) * Math.sin(angle) + (point.y - center.y) * Math.cos(angle);
        res.z = point.z;
        return res;
    }

    public void setXTurnCur(int xTurnCur) {
        this.xTurnCur = xTurnCur;
    }

    public void setYTurnCur(int yTurnCur) {
        this.yTurnCur = yTurnCur;
    }

    public void setZTurnCur(int zTurnCur) {
        this.zTurnCur = zTurnCur;
    }

    public void setDepthTestOn(boolean depthTestOn) {
        isDepthTestOn = depthTestOn;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
