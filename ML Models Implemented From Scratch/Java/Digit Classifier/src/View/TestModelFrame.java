/*
 * Class: View
 * Description: View class is the main class in the View 
 * package, and handles the visual components of the program. 
 * This includes managing the main frame and all the 
 * components inside of it. Controller instantiates it and 
 * uses its buttons.
 */

// Parent package and imports
package View;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.*;

// Class View
// The main class in the "View" package, manages the visual components of the program
public class TestModelFrame extends MainFrame {

    // Height and width of the JFrame
    public static final int FRAME_WIDTH = 1500;
    public static final int FRAME_HEIGHT = 800;

    // Visual components of GUI
    PredictionPanel prediction_panel;
    PixelatedImagePanel pixelated_image_panel;
    OutputPanel output_panel;

    public TestModelFrame() {

        // Initializes components of GUI
        initPredictionPanel();
        initPixelatedImagePanel();
        initOutputPanel();
    }

    // Initialize main panel
    public void initPredictionPanel() {
        
        // Prediction panel settings
        final int PREDICTION_PANEL_HORI_PAD = 15;
        final int PREDICTION_PANEL_WIDTH = 600;
        final int PREDICTION_PANEL_HEIGHT = FRAME_HEIGHT;
        final int PREDICTION_PANEL_X = PREDICTION_PANEL_HORI_PAD;
        final int PREDICTION_PANEL_Y = 15;

        // Initialize main panel, set settings
        setPrediction_panel(new PredictionPanel(PREDICTION_PANEL_X, PREDICTION_PANEL_Y, PREDICTION_PANEL_WIDTH, PREDICTION_PANEL_HEIGHT));
    }

    // Initialize pixelated image panel
    public void initPixelatedImagePanel() {
        
        // Prediction panel settings
        final int PIXELATED_IMAGE_HORI_PAD = 15;
        final int PIXELATED_IMAGE_WIDTH = 400;
        final int PIXELATED_IMAGE_HEIGHT = FRAME_HEIGHT;
        final int PIXELATED_IMAGE_X = getPrediction_panel().getX()+getPrediction_panel().getWidth()+PIXELATED_IMAGE_HORI_PAD;
        final int PIXELATED_IMAGE_Y = 15;

        // Initialize main panel, set settings
        setPixelated_image_panel(new PixelatedImagePanel(PIXELATED_IMAGE_X, PIXELATED_IMAGE_Y, PIXELATED_IMAGE_WIDTH, PIXELATED_IMAGE_HEIGHT));
    }

    // Initialize pixelated image panel
    public void initOutputPanel() {
        
        // Prediction panel settings
        final int OUTPUT_PANEL_HORI_PAD = 50;
        final int OUTPUT_PANEL_WIDTH = 350;
        final int OUTPUT_PANEL_HEIGHT = FRAME_HEIGHT;
        final int OUTPUT_PANEL_X = getPixelated_image_panel().getX()+getPixelated_image_panel().getWidth()+OUTPUT_PANEL_HORI_PAD;
        final int OUTPUT_PANEL_Y = 15;

        // Initialize main panel, set settings
        setOutput_panel(new OutputPanel(OUTPUT_PANEL_X, OUTPUT_PANEL_Y, OUTPUT_PANEL_WIDTH, OUTPUT_PANEL_HEIGHT));
    }

    // Initialize main frame
    public void initFrame() {

        // Give it a title
        setFrame(new JFrame("Test Frame"));

        // Set layout, add main panel to JFrame
        getFrame().setLayout(null);

        // Initialize main panel
        getMain_panel().setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        getMain_panel().setLayout(null);
        getMain_panel().setBackground(Util.C4);
        getFrame().add(getMain_panel());

        getMain_panel().add(getPrediction_panel());
        getMain_panel().add(getPixelated_image_panel());
        getMain_panel().add(getOutput_panel());

        // Set settings for main frame
        getFrame().setResizable(false);
        getFrame().setSize(FRAME_WIDTH, FRAME_HEIGHT);
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setVisible(true);
    }

    // Getter and setter methods
    public static int getFrameWidth() {
        return FRAME_WIDTH;
    }

    public static int getFrameHeight() {
        return FRAME_HEIGHT;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public PredictionPanel getPrediction_panel() {
        return prediction_panel;
    }

    public void setPrediction_panel(PredictionPanel prediction_panel) {
        this.prediction_panel = prediction_panel;
    }

    public PixelatedImagePanel getPixelated_image_panel() {
        return pixelated_image_panel;
    }

    public void setPixelated_image_panel(PixelatedImagePanel pixelated_image_panel) {
        this.pixelated_image_panel = pixelated_image_panel;
    }

    public JPanel getMain_panel() {
        return main_panel;
    }

    public void setMain_panel(JPanel main_panel) {
        this.main_panel = main_panel;
    }

    public OutputPanel getOutput_panel() {
        return output_panel;
    }

    public void setOutput_panel(OutputPanel output_panel) {
        this.output_panel = output_panel;
    }
}