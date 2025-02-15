import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import java.nio.FloatBuffer;


public class MainGLEventsListener implements GLEventListener {

    private Point[] allBodyPoints;
    private Point[] topBodyPoints;
    private Point[] bottomBodyPoints;
    private Point[] neckPoints;
    private Point[] headPoints;
    private Point[] doorstepPoints;
    private Point[][] stringsPoints;
    private Point[] surfacePoints;

    private double step = 0.0001;
    private boolean isDepthTestOn = true;
    private float lightCoeff;

    private int xTurnCur;
    private int yTurnCur;
    private int zTurnCur;
    private double xPoz;
    private double yPos;
    private double zPos;
    private double xScale = 1;
    private double yScale = 1;
    private double zScale = 1;

    private double camXPos;
    private double camYPos;
    private double camZPos;
    private double camXTurn;
    private double camYTurn;
    private double camZTurn;

    private boolean needCoords = false;
    private boolean isLightOn = false;
    private int projection = 0;
    // LIGHT
    private int light = GL2.GL_LIGHT0;
    private int lightType = 0;
    private float constant = 1;
    private float linear;
    private float quadratic;
    private float brightness = 1;
    private float angle = 90;
    private float exponent = 1;
    // MATERIAL
    private float ambient = 0.2f;
    private float shininess;
    private float specular;
    private float emission;
    private float diffuse;

    //model
    private int isLocalViewer = 0;
    private int isTwoSide = 0;
    private int colorControl = GL2.GL_SINGLE_COLOR;

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

        surfacePoints = new Point[]{new Point(1, -1, 1), new Point(-1, -1, 1), new Point(-1, -1, -1), new Point(1, -1, -1)};
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        if (projection == 0) {
            /* ортографическая проекция */
            gl.glMatrixMode(gl.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glMatrixMode(gl.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glOrtho(-1, -1, -1, 1, 1, 1);
//            gl.glOrtho(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5);
        }
        else {
            gl.glMatrixMode(gl.GL_PROJECTION);
            gl.glLoadIdentity();
            glu.gluPerspective(90, 1, 0.5, 50);

            gl.glMatrixMode(gl.GL_MODELVIEW);
            gl.glLoadIdentity();
            glu.gluLookAt(
                    0, 0, -1, // eye - положение камеры
                    0, 0, 0, // at - положение цели, на которую смотрим
                    0, 1, 0 // up - вектор направления вверх камеры
            );
        }

        if (isDepthTestOn) {
            gl.glEnable(GL2.GL_DEPTH_TEST);
        } else {
            gl.glDisable(GL2.GL_DEPTH_TEST);
        }

        //Поворот и движение камеры
        gl.glRotated(camXTurn, 1, 0, 0);
        gl.glRotated(camYTurn, 0, 1, 0);
        gl.glRotated(camZTurn, 0, 0, 1);
        gl.glTranslated(camXPos, camYPos, camZPos);

        gl.glScaled(0.7, 0.7, 0.7);

        if (isLightOn) {
            gl.glEnable(GL2.GL_LIGHTING);
            gl.glEnable(light);
            gl.glEnable(GL2.GL_COLOR_MATERIAL);
            gl.glLightfv(gl.GL_LIGHT0, gl.GL_POSITION, FloatBuffer.wrap(new float[]{1, 0, 1, brightness}));

            gl.glLightfv(gl.GL_LIGHT1, gl.GL_POSITION, FloatBuffer.wrap(new float[]{1, 0, 1, brightness}));
            gl.glLightfv(gl.GL_LIGHT1, gl.GL_DIFFUSE, FloatBuffer.wrap(new float[]{1, 1, 1, 1}));
            gl.glLightfv(gl.GL_LIGHT1, gl.GL_SPECULAR, FloatBuffer.wrap(new float[]{1, 1, 1, 1}));
            gl.glLightf(gl.GL_LIGHT1, gl.GL_CONSTANT_ATTENUATION, constant);
            gl.glLightf(gl.GL_LIGHT1, gl.GL_LINEAR_ATTENUATION, linear);
            gl.glLightf(gl.GL_LIGHT1, gl.GL_QUADRATIC_ATTENUATION, quadratic);

            gl.glLightfv(gl.GL_LIGHT2, gl.GL_DIFFUSE, FloatBuffer.wrap(new float[]{1, 1, 1, 1}));
            gl.glLightfv(gl.GL_LIGHT2, gl.GL_SPECULAR, FloatBuffer.wrap(new float[]{1, 1, 1, 1}));
            gl.glLightf(gl.GL_LIGHT2, gl.GL_SPOT_CUTOFF, angle);// угол пропускания
            gl.glLightf(gl.GL_LIGHT2, gl.GL_SPOT_EXPONENT, exponent);// значение Е
            gl.glLightfv(gl.GL_LIGHT2, gl.GL_SPOT_DIRECTION, FloatBuffer.wrap(new float[]{0, 0, -1}));

            gl.glColorMaterial(gl.GL_FRONT, gl.GL_DIFFUSE);
//            gl.glColorMaterial(gl.GL_FRONT_AND_BACK, gl.GL_SHININESS);
//            gl.glColorMaterial(gl.GL_FRONT, gl.GL_EMISSION);
//            gl.glColorMaterial(gl.GL_FRONT, gl.GL_AMBIENT);
            gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_SHININESS, FloatBuffer.wrap(new float[]{shininess})); // от 0 до 128, степень зеркального отражения материала
            gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_SPECULAR, FloatBuffer.wrap(new float[]{specular, specular, specular, 1})); // цвет зеркального отражения
//            gl.glMaterialfv(gl.GL_FRONT, gl.GL_DIFFUSE, FloatBuffer.wrap(new float[]{diffuse, diffuse, diffuse, 1})); // цвет диффузного отражения
            gl.glMaterialfv(gl.GL_FRONT, gl.GL_AMBIENT, FloatBuffer.wrap(new float[]{ambient, ambient, ambient, 1})); // цвет материала в тени
            gl.glMaterialfv(gl.GL_FRONT, gl.GL_EMISSION, FloatBuffer.wrap(new float[]{emission, emission, emission, 1}));


            //модель освещения
            gl.glEnable(GL2.GL_NORMALIZE);
            gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, isLocalViewer);
            gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, isTwoSide);
            gl.glLightModeli(GL2.GL_LIGHT_MODEL_COLOR_CONTROL, colorControl);

        }

        //Поворот, передвижение и масштабирование гитары
        gl.glPushMatrix();


//        gl.glRotated(xTurnCur, 1, 0, 0);
//        gl.glRotated(yTurnCur, 0, 1, 0);
//        gl.glRotated(zTurnCur, 0, 0, 1);
//
//        gl.glTranslated(xPoz, yPos, zPos);

        gl.glScaled(xScale, yScale, zScale);

        drawBody(gl);
        drawNeck(gl);
        drawHead(gl);
        drawDoorStep(gl);
        drawCylinder(gl, new Point(0, -0.6, 0), 0.1, 0.1);
        drawStrings(gl);

        drawSurface(gl);
        gl.glPopMatrix();
//
//        if (needCoords) {
//            drawCoords(gl);
//        }


        if (isLightOn) {
            gl.glDisable(GL2.GL_LIGHTING);
            gl.glDisable(light);
        }

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

    private void drawCoords(GL2 gl) {
        gl.glColor3d(1, 0, 0);
        gl.glLineWidth(2);
        gl.glBegin(GL2.GL_LINES);

        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(1, 0, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 1, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, 1);

        gl.glEnd();
        gl.glLineWidth(1);
    }

    private void drawSurface(GL2 gl) {
        gl.glColor3d(0.2, 0.2, 0.2);

        gl.glBegin(gl.GL_POLYGON);
        for (Point p : surfacePoints){
            gl.glVertex3d(p.x, p.y, p.z);
        }

        gl.glEnd();
    }

    private void drawPoly(GL2 gl, Point[] points, double z) {
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

    public void setxPoz(double xPoz) {
        this.xPoz = xPoz;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public void setzPos(double zPos) {
        this.zPos = zPos;
    }

    public void setxScale(double xScale) {
        this.xScale = xScale;
    }

    public void setyScale(double yScale) {
        this.yScale = yScale;
    }

    public void setzScale(double zScale) {
        this.zScale = zScale;
    }

    public void setNeedCoords(boolean needCoords) {
        this.needCoords = needCoords;
    }

    public void setCamXPos(double camXPos) {
        this.camXPos = camXPos;
    }

    public void setCamYPos(double camYPos) {
        this.camYPos = camYPos;
    }

    public void setCamZPos(double camZPos) {
        this.camZPos = camZPos;
    }

    public void setCamXTurn(double camXTurn) {
        this.camXTurn = camXTurn;
    }

    public void setCamYTurn(double camYTurn) {
        this.camYTurn = camYTurn;
    }

    public void setCamZTurn(double camZTurn) {
        this.camZTurn = camZTurn;
    }

    public void setLightCoeff(float lightCoeff) {
        this.lightCoeff = lightCoeff;
    }

    public void setLightOn(boolean lightOn) {
        isLightOn = lightOn;
    }

    public void setProjection(int projection) {
        this.projection = projection;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public void setLightType(int lightT) {
        lightType = lightT;
    }

    public void setConstant(float c) {
        constant = c;
    }

    public void setLinear(float l) {
        linear = l;
    }

    public void setQuadratic(float q) {
        quadratic = q;
    }

    public void setBrightness(float b) {
        brightness = b;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setExponent(float ex) {
        this.exponent = ex;
    }

    public void setAmbient(float ambient) {
        this.ambient = ambient;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public void setSpecular(float specular) {
        this.specular = specular;
    }

    public void setEmission(float emission) {
        this.emission = emission;
    }

    public void setIsLocalViewer(int isLocalViewer) {
        this.isLocalViewer = isLocalViewer;
    }

    public void setIsTwoSide(int isTwoSide) {
        this.isTwoSide = isTwoSide;
    }

    public void setColorControl(int colorControl) {
        this.colorControl = colorControl;
    }

    public void setDiffuse(float diffuse) {
        this.diffuse = diffuse;
    }
}