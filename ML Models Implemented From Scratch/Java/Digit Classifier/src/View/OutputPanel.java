/*
 * Class: OutputPanel
 * Description: This class contains the visual components necessary for displaying 
 * the predicted probabilities by the neural network for each digit between 0 and 
 * 9. It displays this in rows of JLabels.
 */

// Package and imports
package View;

import java.util.*;
import javax.swing.*;

// OutputPanel class
// Class that contains the JLabels that display the probability predicted by the 
// neural network for each digit between 0 and 9.
public class OutputPanel extends DisplayPanel {

    JLabel title;
    ArrayList<JLabel> output_labels;

    // Constructor for OutputPanel object
    public OutputPanel(int x, int y, int width, int height){
        super(x, y, width, height);
    
        // Initialize visual components
        init();
    }
    
    // Initializes panel
    @Override
    public void init(){
        initTitle();
        initOutputLabels();
    }

    // Initializes the title label
    public void initTitle(){
        
        // Settings for visual components for title
        final int TITLE_VERT_PAD = 15;
        final int TITLE_WIDTH = getWidth();
        final int TITLE_HEIGHT = 50;
        final int TITLE_X = 0;
        final int TITLE_Y = TITLE_VERT_PAD;
        
        // Initialize and set position for title
        setTitle(new JLabel("Confidence", SwingConstants.CENTER));
        getTitle().setBounds(TITLE_X, TITLE_Y, TITLE_WIDTH, TITLE_HEIGHT);

        // Other visuals for title
        // Set visual components and add to panel
        getTitle().setFont(Util.getFont(40));
        getTitle().setForeground(Util.C1);
        add(getTitle());
    }

    // Initializes the probability-display labels
    public void initOutputLabels(){

        // Number of possible predictions
        final int NUM_CLASSES = 10;

        // Visual settings for labels
        final int LABEL_VERT_PAD = 20;
        final int LABEL_WIDTH = getWidth();
        final int LABEL_HEIGHT = (getHeight()-getTitle().getY()-getTitle().getHeight()-5*LABEL_VERT_PAD)/NUM_CLASSES;
        final int LABEL_X = 0;
        final int LABEL_Y = getTitle().getY()+getTitle().getHeight()+LABEL_VERT_PAD;

        // Init arraylist
        setOutput_labels(new ArrayList<>());

        // Iterate through arraylist and init labels
        for(int i=0; i<NUM_CLASSES; i++){
            
            // Init output label, set position
            JLabel output_label = new JLabel(String.format("%d: ", i), SwingConstants.CENTER);
            output_label.setBounds(LABEL_X, LABEL_Y+i*LABEL_HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
            output_label.setOpaque(true);

            // Set alternating back and fore ground colors
            if(i%2==0){
                output_label.setBackground(Util.C1);
                output_label.setForeground(Util.C4);
            } else {
                output_label.setBackground(Util.C4);
                output_label.setForeground(Util.C1);
            }

            // Set font size
            output_label.setFont(Util.getFont(20));

            // Add to panel
            add(output_label);

            // Add to arraylist
            getOutput_labels().add(output_label);
        }
    }

    // Setters and getters
    public JLabel getTitle() {
        return title;
    }

    public void setTitle(JLabel title) {
        this.title = title;
    }

    public ArrayList<JLabel> getOutput_labels() {
        return output_labels;
    }

    public void setOutput_labels(ArrayList<JLabel> output_labels) {
        this.output_labels = output_labels;
    }

    
}
