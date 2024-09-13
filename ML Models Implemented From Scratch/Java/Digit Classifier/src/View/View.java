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
public class View {

    // Visual components of GUI
    TestModelFrame test_model_frame;
    ModelSelectionFrame model_selection_frame;

    // Constructor for view class
    public View() {

        // Initialize test and model selection frames
        setTest_model_frame(new TestModelFrame());
        setModel_selection_frame(new ModelSelectionFrame());
    }

    // Getter and setter
    public TestModelFrame getTest_model_frame() {
        return test_model_frame;
    }

    public void setTest_model_frame(TestModelFrame test_model_frame) {
        this.test_model_frame = test_model_frame;
    }

    public ModelSelectionFrame getModel_selection_frame() {
        return model_selection_frame;
    }

    public void setModel_selection_frame(ModelSelectionFrame model_selection_frame) {
        this.model_selection_frame = model_selection_frame;
    }

    

    
}