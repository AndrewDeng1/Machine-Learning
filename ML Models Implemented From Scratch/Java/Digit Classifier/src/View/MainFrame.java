/*
 * Class: MainFrame
 * Description: Template class to represent frames throughout the program. 
 * E.g. test frame or model selection frame or model dashboard frame
 */

// Package and imports
package View;

import javax.swing.*;

// MainFrame class
// Represents the frames throughout the program
public class MainFrame {
    
    // Frame and main panel
    JFrame frame;
    JPanel main_panel;

    // Constructor for main frame
    public MainFrame(){
        setMain_panel(new JPanel());
    }

    public void initFrame(){

    }

    public JFrame getFrame() {
        return frame;
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    public JPanel getMain_panel() {
        return main_panel;
    }
    public void setMain_panel(JPanel main_panel) {
        this.main_panel = main_panel;
    }

    
}
