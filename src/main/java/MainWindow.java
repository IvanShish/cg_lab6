import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class MainWindow extends JFrame {

    public MainWindow() throws HeadlessException {
        super();

        //main panel
        JPanel grid = new JPanel();
        GridLayout layout = new GridLayout(1, 2);
        grid.setLayout(layout);

        //panel for drawing
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLJPanel gljpanel = new GLJPanel(capabilities);
        MainGLEventsListener eventsListener = new MainGLEventsListener();
        gljpanel.addGLEventListener(eventsListener);
        gljpanel.setSize(850, 850);
        grid.add(gljpanel);

        //panel with buttons
        JPanel buttonsPanel = new JPanel();
        BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS);
        buttonsPanel.setLayout(buttonsLayout);
        grid.add(buttonsPanel);

//        JSlider xTurnSlider = new JSlider(-180, 180, 0);
//        JSlider yTurnSlider = new JSlider(-180, 180, 0);
//        JSlider zTurnSlider = new JSlider(-180, 180, 0);
//        xTurnSlider.addChangeListener((e) -> {
//            eventsListener.setXTurnCur(xTurnSlider.getValue());
//            gljpanel.display();
//        });
//        yTurnSlider.addChangeListener((e) -> {
//            eventsListener.setYTurnCur(yTurnSlider.getValue());
//            gljpanel.display();
//        });
//        zTurnSlider.addChangeListener((e) -> {
//            eventsListener.setZTurnCur(zTurnSlider.getValue());
//            gljpanel.display();
//        });
//        buttonsPanel.add(new JLabel("Вращение по оси x"));
//        buttonsPanel.add(xTurnSlider);
//        buttonsPanel.add(new JLabel("Вращение по оси y"));
//        buttonsPanel.add(yTurnSlider);
//        buttonsPanel.add(new JLabel("Вращение по оси z"));
//        buttonsPanel.add(zTurnSlider);
//
//        JCheckBox isVisibleCheckBox = new JCheckBox("Прорисовка невидимых граней");
//        isVisibleCheckBox.setSelected(false);
//        isVisibleCheckBox.addChangeListener((e) -> {
//            eventsListener.setDepthTestOn(!isVisibleCheckBox.isSelected());
//            gljpanel.display();
//        });
//        buttonsPanel.add(isVisibleCheckBox);
//
//        JSlider stepSizeSlider = new JSlider(1, 1000, 1);
//        stepSizeSlider.addChangeListener((e)-> {
//            eventsListener.setStep(stepSizeSlider.getValue() / 10000f);
//            gljpanel.display();
//        });
//        buttonsPanel.add(new JLabel("Мелкость разбиения"));
//        buttonsPanel.add(stepSizeSlider);
//
//        JSlider xPosSlider = new JSlider(-100, 100, 0);
//        JSlider yPosSlider = new JSlider(-100, 100, 0);
//        JSlider zPosSlider = new JSlider(-100, 100, 0);
//        xPosSlider.addChangeListener((e) -> {
//            eventsListener.setxPoz(xPosSlider.getValue()/100f);
//            gljpanel.display();
//        });
//        yPosSlider.addChangeListener((e) -> {
//            eventsListener.setyPos(yPosSlider.getValue()/100f);
//            gljpanel.display();
//        });
//        zPosSlider.addChangeListener((e) -> {
//            eventsListener.setzPos(zPosSlider.getValue()/100f);
//            gljpanel.display();
//        });
//        buttonsPanel.add(new JLabel("Позиция по x"));
//        buttonsPanel.add(xPosSlider);
//        buttonsPanel.add(new JLabel("Позиция по y"));
//        buttonsPanel.add(yPosSlider);
//        buttonsPanel.add(new JLabel("Позиция по z"));
//        buttonsPanel.add(zPosSlider);
//
//
//        JSlider xScaleSlider = new JSlider(1, 500, 100);
//        JSlider yScaleSlider = new JSlider(1, 500, 100);
//        JSlider zScaleSlider = new JSlider(1, 500, 100);
//        xScaleSlider.addChangeListener((e) -> {
//            eventsListener.setxScale(xScaleSlider.getValue()/100f);
//            gljpanel.display();
//        });
//        yScaleSlider.addChangeListener((e) -> {
//            eventsListener.setyScale(yScaleSlider.getValue()/100f);
//            gljpanel.display();
//        });
//        zScaleSlider.addChangeListener((e) -> {
//            eventsListener.setzScale(zScaleSlider.getValue()/100f);
//            gljpanel.display();
//        });
//        buttonsPanel.add(new JLabel("Масштаб по x"));
//        buttonsPanel.add(xScaleSlider);
//        buttonsPanel.add(new JLabel("Масштаб по y"));
//        buttonsPanel.add(yScaleSlider);
//        buttonsPanel.add(new JLabel("Масштаб по z"));
//        buttonsPanel.add(zScaleSlider);
//
//        JCheckBox needCoordsCheckBox = new JCheckBox("Включить координатную сетку");
//        needCoordsCheckBox.addChangeListener((e) -> {
//            eventsListener.setNeedCoords(needCoordsCheckBox.isSelected());
//            gljpanel.display();
//        });
//        buttonsPanel.add(needCoordsCheckBox);

        JSlider camXPosSlider = new JSlider(-100, 100, 0);
        JSlider camYPosSlider = new JSlider(-100, 100, 0);
        JSlider camZPosSlider = new JSlider(-100, 100, 0);
        camXPosSlider.addChangeListener((e) -> {
            eventsListener.setCamXPos(camXPosSlider.getValue()/100f);
            gljpanel.display();
        });
        camYPosSlider.addChangeListener((e) -> {
            eventsListener.setCamYPos(camYPosSlider.getValue()/100f);
            gljpanel.display();
        });
        camZPosSlider.addChangeListener((e) -> {
            eventsListener.setCamZPos(camZPosSlider.getValue()/100f);
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("Позиция камеры по x"));
        buttonsPanel.add(camXPosSlider);
        buttonsPanel.add(new JLabel("Позиция камеры по y"));
        buttonsPanel.add(camYPosSlider);
        buttonsPanel.add(new JLabel("Позиция камеры по z"));
        buttonsPanel.add(camZPosSlider);

        JSlider camXTurnSlider = new JSlider(-180, 180, 0);
        JSlider camYTurnSlider = new JSlider(-180, 180, 0);
        JSlider camZTurnSlider = new JSlider(-180, 180, 0);
        camXTurnSlider.addChangeListener((e) -> {
            eventsListener.setCamXTurn(camXTurnSlider.getValue());
            gljpanel.display();
        });
        camYTurnSlider.addChangeListener((e) -> {
            eventsListener.setCamYTurn(camYTurnSlider.getValue());
            gljpanel.display();
        });
        camZTurnSlider.addChangeListener((e) -> {
            eventsListener.setCamZTurn(camZTurnSlider.getValue());
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("Поворот камеры по x"));
        buttonsPanel.add(camXTurnSlider);
        buttonsPanel.add(new JLabel("Поворот камеры по y"));
        buttonsPanel.add(camYTurnSlider);
        buttonsPanel.add(new JLabel("Поворот камеры по z"));
        buttonsPanel.add(camZTurnSlider);

        buttonsPanel.add(new JLabel("Проецирование"));

        JRadioButton orthoProjection = new JRadioButton("Ортографическая проекция");
        JRadioButton perspectiveProjection = new JRadioButton("Перспективная проекция");
        ButtonGroup projectionGroup = new ButtonGroup();
        projectionGroup.add(orthoProjection);
        projectionGroup.add(perspectiveProjection);

        orthoProjection.addActionListener(e ->  {
            eventsListener.setProjection(0);
            gljpanel.display();
        });
        perspectiveProjection.addActionListener(e ->  {
            eventsListener.setProjection(1);
            gljpanel.display();
        });
        orthoProjection.setSelected(true);

        buttonsPanel.add(orthoProjection);
        buttonsPanel.add(perspectiveProjection);

        buttonsPanel.add(new JLabel("Свет"));
        JCheckBox isLightOnCheckBox = new JCheckBox("Использовать освещение");
        isLightOnCheckBox.addChangeListener(e -> {
            eventsListener.setLightOn(isLightOnCheckBox.isSelected());
            gljpanel.display();
        });
        buttonsPanel.add(isLightOnCheckBox);

        buttonsPanel.add(new JLabel("Типы света:"));
        JComboBox <Lights> lights = new JComboBox<>(Lights.values());
        lights.setMaximumSize(new Dimension(lights.getMaximumSize().width, lights.getMinimumSize().height));
        lights.addActionListener(e -> {
            eventsListener.setLight(((Lights) lights.getSelectedItem()).code);
            gljpanel.display();
        });
        buttonsPanel.add(lights);
//        JRadioButton direct = new JRadioButton("Направленный");
//        JRadioButton point = new JRadioButton("Точечный");
//        JRadioButton spotlight = new JRadioButton("Прожектор");
//        ButtonGroup lightGroup = new ButtonGroup();
//        lightGroup.add(direct);
//        lightGroup.add(point);
//        lightGroup.add(spotlight);
//
//        direct.addActionListener(e ->  {
//            eventsListener.setLightType(0);
//            gljpanel.display();
//        });
//        point.addActionListener(e ->  {
//            eventsListener.setLightType(1);
//            gljpanel.display();
//        });
//        spotlight.addActionListener(e ->  {
//            eventsListener.setLightType(2);
//            gljpanel.display();
//        });
//        direct.setSelected(true);
//
//        buttonsPanel.add(direct);
//        buttonsPanel.add(point);
//        buttonsPanel.add(spotlight);

        JSlider bright = new JSlider(0, 200, 100);
        JSlider constant = new JSlider(0, 100, 100);
        JSlider linear = new JSlider(0, 100, 0);
        JSlider quadratic = new JSlider(0, 100, 0);
        JSlider angle = new JSlider(0, 180, 90);
        JSlider exponent = new JSlider(0, 500, 100);
        bright.addChangeListener((e) -> {
            eventsListener.setBrightness(bright.getValue() / 100f);
            gljpanel.display();
        });
        constant.addChangeListener((e) -> {
            eventsListener.setConstant(constant.getValue() / 100f);
            gljpanel.display();
        });
        linear.addChangeListener((e) -> {
            eventsListener.setLinear(linear.getValue()  / 100f);
            gljpanel.display();
        });
        quadratic.addChangeListener((e) -> {
            eventsListener.setQuadratic(quadratic.getValue() / 100f);
            gljpanel.display();
        });
        angle.addChangeListener((e) -> {
            eventsListener.setAngle(angle.getValue());
            gljpanel.display();
        });
        exponent.addChangeListener((e) -> {
            eventsListener.setExponent(exponent.getValue() / 100f);
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("Яркость"));
        buttonsPanel.add(bright);
        buttonsPanel.add(new JLabel("Постоянный коэфф. затухания"));
        buttonsPanel.add(constant);
        buttonsPanel.add(new JLabel("Линейный коэфф. затухания"));
        buttonsPanel.add(linear);
        buttonsPanel.add(new JLabel("Квадратичный коэфф. затухания"));
        buttonsPanel.add(quadratic);
        buttonsPanel.add(new JLabel("Угол пропускания"));
        buttonsPanel.add(angle);
        buttonsPanel.add(new JLabel("Экспонента затухания"));
        buttonsPanel.add(exponent);


        buttonsPanel.add(new JLabel("Материал"));

        JSlider ambient = new JSlider(0, 100, 20);
        JSlider shininess = new JSlider(0, 128, 0);
        JSlider specular = new JSlider(0, 100, 0);
        JSlider emission = new JSlider(0, 100, 0);
        JSlider diffuse = new JSlider(0, 100, 0);
        ambient.addChangeListener((e) -> {
            eventsListener.setAmbient(ambient.getValue() / 100f);
            gljpanel.display();
        });
        shininess.addChangeListener((e) -> {
            eventsListener.setShininess(shininess.getValue());
            gljpanel.display();
        });
        specular.addChangeListener((e) -> {
            eventsListener.setSpecular(specular.getValue() / 100f);
            gljpanel.display();
        });
        emission.addChangeListener((e) -> {
            eventsListener.setEmission(emission.getValue() / 100f);
            gljpanel.display();
        });
        diffuse.addChangeListener(e -> {
            eventsListener.setDiffuse(diffuse.getValue()/100);
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("GL_AMBIENT"));
        buttonsPanel.add(ambient);
        buttonsPanel.add(new JLabel("GL_SHININESS"));
        buttonsPanel.add(shininess);
        buttonsPanel.add(new JLabel("GL_SPECULAR"));
        buttonsPanel.add(specular);
        buttonsPanel.add(new JLabel("GL_EMISSION"));
        buttonsPanel.add(emission);
        buttonsPanel.add(new JLabel("GL_DIFFUSE"));
        buttonsPanel.add(diffuse);


        JCheckBox isLocalViewer = new JCheckBox("GL_LIGHT_MODEL_LOCAL_VIEWER");
        JCheckBox isTwoSide = new JCheckBox("GL_LIGHT_MODEL_TWO_SIDE");
        JCheckBox colorControl = new JCheckBox("GL_LIGHT_MODEL_COLOR_CONTROL");
        isLocalViewer.addActionListener(v -> {
            if (isLocalViewer.isSelected()) {
                eventsListener.setIsLocalViewer(1);
            } else {
                eventsListener.setIsLocalViewer(0);
            }
            gljpanel.display();
        });
        isTwoSide.addActionListener(v -> {
            if (isTwoSide.isSelected()) {
                eventsListener.setIsTwoSide(1);
            } else {
                eventsListener.setIsTwoSide(0);
            }
            gljpanel.display();
        });
        colorControl.addActionListener(e -> {
            if (isTwoSide.isSelected()) {
                eventsListener.setColorControl(GL2.GL_SEPARATE_SPECULAR_COLOR);
            } else {
                eventsListener.setColorControl(GL2.GL_SINGLE_COLOR);
            }
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("Модель"));
        buttonsPanel.add(isLocalViewer);
        buttonsPanel.add(isTwoSide);
//        buttonsPanel.add(colorControl);



        buttonsPanel.add(Box.createHorizontalStrut(5));

        this.getContentPane().add(grid);
        this.setSize(1700, 850);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
