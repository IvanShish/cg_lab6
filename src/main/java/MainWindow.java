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

        buttonsPanel.add(Box.createHorizontalStrut(5));

        this.getContentPane().add(grid);
        this.setSize(1500, 750);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
