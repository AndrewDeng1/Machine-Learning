/*
 * Class: PredictionPanel
 * Description: This is the class that contains the visual components where 
 * the user draws their image and a prediction is made by clicking submit. They 
 * can clear the screen by clicking clear.
 */

// Package and imports
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// PredictionPanel class
// The panel containing visual components necessary for user to draw an image 
// and pass it into backend to make a prediction.
public class PredictionPanel extends DisplayPanel {

    JPanel control_panel;

    // Label to display the predicted digit
    JLabel label;

    // Buttons for clearing and submitting the image
    JButton clear_button, submit_button;

    // Area where user can draw
    JPanel draw_panel;
    DrawArea drawArea;

    // Constructor for prediction panel class
    public PredictionPanel(int x, int y, int width, int height){
        super(x, y, width, height);

        // Initialize visual components
        init();
    }

    // Initializes panel
    @Override
    public void init(){
        initControlPanel();
        initButtons();
        initDraw_panel();
        initDrawArea();
        initLabel();
    }

    // Initializes the submit and clear buttons
    public void initButtons() {

        // Settings for buttons
        final int BUT_HORI_PAD = 20;
        final int BUT_VERT_PAD = 20;
        final int BUT_WIDTH = (getControl_panel().getWidth() - 4 * BUT_HORI_PAD) / 2;
        final int BUT_HEIGHT = getControl_panel().getHeight() - BUT_VERT_PAD;
        final int BUT_X = BUT_HORI_PAD;
        final int BUT_Y = BUT_VERT_PAD;

        // Adds action listener to clear the draw area when clicked
        setClear_button(new JButton("Clear"));
        getClear_button().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getDrawArea().clear();
            }
        });

        // Sets position
        getClear_button().setBounds(BUT_X, BUT_Y, BUT_WIDTH, BUT_HEIGHT);

        // Initializes and sets position for submit button
        setSubmit_button(new JButton("Submit"));
        getSubmit_button().setBounds(BUT_X + BUT_HORI_PAD + BUT_WIDTH, BUT_Y, BUT_WIDTH, BUT_HEIGHT);

        // Sets visual components for clear and submit buttons
        getClear_button().setBackground(Util.C1);
        getClear_button().setForeground(Util.C4);
        getClear_button().setFont(Util.getFont(BUT_FONT_SIZE));

        getSubmit_button().setBackground(Util.C1);
        getSubmit_button().setForeground(Util.C4);
        getSubmit_button().setFont(Util.getFont(BUT_FONT_SIZE));

        // Adds them to the control panel
        getControl_panel().add(getClear_button());
        getControl_panel().add(getSubmit_button());
    }

    // Initializes the control panel
    public void initControlPanel() {

        // Visual settings for control panel
        final int CONTROL_PANEL_X = 0;
        final int CONTROL_PANEL_Y = 0;
        final int CONTROL_PANEL_WIDTH = getWidth();
        final int CONTROL_PANEL_HEIGHT = 100;

        // Initializes and sets settings for control panel
        setControl_panel(new JPanel());
        getControl_panel().setBounds(CONTROL_PANEL_X, CONTROL_PANEL_Y, CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT);
        getControl_panel().setOpaque(false);
        getControl_panel().setLayout(null);

        // Add control panel to panel
        add(getControl_panel());
    }

    // Initializes the draw area
    public void initDrawArea() {

        // Visual settings for draw area gui
        final int DRAW_AREA_X = 0;
        final int DRAW_AREA_Y = 0;
        final int DRAW_AREA_WIDTH = getDraw_panel().getWidth();
        final int DRAW_AREA_HEIGHT = getDraw_panel().getHeight();

        // Initializes and sets settings for draw area
        setDrawArea(new DrawArea());
        getDrawArea().setBounds(DRAW_AREA_X, DRAW_AREA_Y, DRAW_AREA_WIDTH, DRAW_AREA_HEIGHT);

        // Adds draw area to draw panel
        getDraw_panel().add(getDrawArea());
    }

    // Initializes the draw panel
    public void initDraw_panel() {

        // Visual settings for draw panel
        final int DRAW_PANEL_HORI_PAD = 15;
        final int DRAW_PANEL_VERT_PAD = 15;
        final int DRAW_PANEL_WIDTH = getWidth() - 3 * DRAW_PANEL_HORI_PAD - 50;
        final int DRAW_PANEL_HEIGHT = DRAW_PANEL_WIDTH;
        final int DRAW_PANEL_X = (getWidth() - DRAW_PANEL_WIDTH) / 2 - 10;
        final int DRAW_PANEL_Y = getControl_panel().getY() + getControl_panel().getHeight() + DRAW_PANEL_VERT_PAD;

        // Initializes and sets settings for draw panel
        setDraw_panel(new JPanel());
        getDraw_panel().setBounds(DRAW_PANEL_X, DRAW_PANEL_Y, DRAW_PANEL_WIDTH, DRAW_PANEL_HEIGHT);
        getDraw_panel().setBorder(BorderFactory.createLineBorder(Util.C1));

        // https://stackoverflow.com/questions/29778456/set-border-thickness-on-jpanels
        getDraw_panel().setBorder(BorderFactory.createStrokeBorder(new BasicStroke(20.0f)));
        getDraw_panel().setLayout(null);

        // Add draw panel to main panel
        add(getDraw_panel());
    }

    // Initializes the prediction label
    public void initLabel() {

        // Visual settings for label
        final int LABEL_VERT_PAD = 10;
        final int LABEL_X = getDraw_panel().getX();
        final int LABEL_Y = getDraw_panel().getY() + getDraw_panel().getHeight() + LABEL_VERT_PAD;
        final int LABEL_WIDTH = getDraw_panel().getWidth();
        final int LABEL_HEIGHT = getHeight() - LABEL_Y - 4 * LABEL_VERT_PAD;

        // Initialize label, set position
        setLabel(new JLabel("", SwingConstants.CENTER));
        getLabel().setBounds(LABEL_X, LABEL_Y, LABEL_WIDTH, LABEL_HEIGHT);

        // Visual GUI settings
        getLabel().setBackground(Util.C2);
        getLabel().setForeground(Util.C1);
        getLabel().setFont(Util.getFont(LABEL_FONT_SIZE));

        // Add to panel
        add(getLabel());
    }

    // Setters and getters
    public JPanel getControl_panel() {
        return control_panel;
    }

    public void setControl_panel(JPanel control_panel) {
        this.control_panel = control_panel;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JButton getClear_button() {
        return clear_button;
    }

    public void setClear_button(JButton clear_button) {
        this.clear_button = clear_button;
    }

    public JButton getSubmit_button() {
        return submit_button;
    }

    public void setSubmit_button(JButton submit_button) {
        this.submit_button = submit_button;
    }

    public JPanel getDraw_panel() {
        return draw_panel;
    }

    public void setDraw_panel(JPanel draw_panel) {
        this.draw_panel = draw_panel;
    }

    public DrawArea getDrawArea() {
        return drawArea;
    }

    public void setDrawArea(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    
}
