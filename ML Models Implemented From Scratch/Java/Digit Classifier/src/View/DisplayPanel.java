/*
 * Class: DisplayPanel
 * Description: Template class that other classes that will be large panels on 
 * the GUI can inherit from. Requires parent class to pass coordinates and width 
 * and height. Also contains utility methods and variables for easy access.
 */

// Package and imports
package View;

import javax.swing.*;
import java.awt.*;
import java.io.*;

// Panel class
// A template class which other classes that are large panels on the GUI can 
// inherit to require View class to pass coords and dimensions.
public class DisplayPanel extends JPanel {

    // Font size in the predicted label
    public static final int LABEL_FONT_SIZE = 75;
    public static final int BUT_FONT_SIZE = 50;

    // Constructor for DisplayPanel class
    public DisplayPanel(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
    
        setLayout(null);
        setOpaque(false);
    }

    // Meant to be overriden
    // Handles initialization tasks for all GUI components for each panel
    public void init(){

    }
}
