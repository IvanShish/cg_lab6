import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import javax.swing.*;
import java.awt.*;

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
        gljpanel.setSize(750, 750);
        grid.add(gljpanel);

        //panel with buttons
        JPanel buttonsPanel = new JPanel();
        BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS);
        buttonsPanel.setLayout(buttonsLayout);
        grid.add(buttonsPanel);

        JSlider xTurnSlider = new JSlider(-180, 180, 0);
        JSlider yTurnSlider = new JSlider(-180, 180, 0);
        JSlider zTurnSlider = new JSlider(-180, 180, 0);
        xTurnSlider.addChangeListener((e) -> {
            eventsListener.setXTurnCur(xTurnSlider.getValue());
            gljpanel.display();
        });
        yTurnSlider.addChangeListener((e) -> {
            eventsListener.setYTurnCur(yTurnSlider.getValue());
            gljpanel.display();
        });
        zTurnSlider.addChangeListener((e) -> {
            eventsListener.setZTurnCur(zTurnSlider.getValue());
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("Вращение по оси x"));
        buttonsPanel.add(xTurnSlider);
        buttonsPanel.add(new JLabel("Вращение по оси y"));
        buttonsPanel.add(yTurnSlider);
        buttonsPanel.add(new JLabel("Вращение по оси z"));
        buttonsPanel.add(zTurnSlider);

        JCheckBox isVisibleCheckBox = new JCheckBox("Прорисовка невидимых граней");
        isVisibleCheckBox.setSelected(false);
        isVisibleCheckBox.addChangeListener((e) -> {
            eventsListener.setDepthTestOn(!isVisibleCheckBox.isSelected());
            gljpanel.display();
        });
        buttonsPanel.add(isVisibleCheckBox);

        JSlider stepSizeSlider = new JSlider(1, 1000, 1);
        stepSizeSlider.addChangeListener((e)-> {
            eventsListener.setStep(stepSizeSlider.getValue() / 10000f);
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("Мелкость разбиения"));
        buttonsPanel.add(stepSizeSlider);

        JSlider xPosSlider = new JSlider(-100, 100, 0);
        JSlider yPosSlider = new JSlider(-100, 100, 0);
        JSlider zPosSlider = new JSlider(-100, 100, 0);
        xPosSlider.addChangeListener((e) -> {
            eventsListener.setxPoz(xPosSlider.getValue()/100f);
            gljpanel.display();
        });
        yPosSlider.addChangeListener((e) -> {
            eventsListener.setyPos(yPosSlider.getValue()/100f);
            gljpanel.display();
        });
        zPosSlider.addChangeListener((e) -> {
            eventsListener.setzPos(zPosSlider.getValue()/100f);
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("Позиция по x"));
        buttonsPanel.add(xPosSlider);
        buttonsPanel.add(new JLabel("Позиция по y"));
        buttonsPanel.add(yPosSlider);
        buttonsPanel.add(new JLabel("Позиция по z"));
        buttonsPanel.add(zPosSlider);


        JSlider xScaleSlider = new JSlider(1, 500, 100);
        JSlider yScaleSlider = new JSlider(1, 500, 100);
        JSlider zScaleSlider = new JSlider(1, 500, 100);
        xScaleSlider.addChangeListener((e) -> {
            eventsListener.setxScale(xScaleSlider.getValue()/100f);
            gljpanel.display();
        });
        yScaleSlider.addChangeListener((e) -> {
            eventsListener.setyScale(yScaleSlider.getValue()/100f);
            gljpanel.display();
        });
        zScaleSlider.addChangeListener((e) -> {
            eventsListener.setzScale(zScaleSlider.getValue()/100f);
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("Масштаб по x"));
        buttonsPanel.add(xScaleSlider);
        buttonsPanel.add(new JLabel("Масштаб по y"));
        buttonsPanel.add(yScaleSlider);
        buttonsPanel.add(new JLabel("Масштаб по z"));
        buttonsPanel.add(zScaleSlider);

        JCheckBox needCoordsCheckBox = new JCheckBox("Включить координатную сетку");
        needCoordsCheckBox.addChangeListener((e) -> {
            eventsListener.setNeedCoords(needCoordsCheckBox.isSelected());
            gljpanel.display();
        });
        buttonsPanel.add(needCoordsCheckBox);

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

        JSlider lightCoeffSlider = new JSlider(0, 50, 0);
        lightCoeffSlider.addChangeListener(e -> {
            eventsListener.setLightCoeff(lightCoeffSlider.getValue());
            gljpanel.display();
        });
        buttonsPanel.add(new JLabel("Интенсивность освещения"));
        buttonsPanel.add(lightCoeffSlider);

        buttonsPanel.add(Box.createHorizontalStrut(5));

        this.getContentPane().add(grid);
        this.setSize(1500, 750);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
