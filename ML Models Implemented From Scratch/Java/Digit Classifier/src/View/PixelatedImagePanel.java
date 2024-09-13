/*
 * Class: PixelatedImagePanel
 * Description: This panel will display the scaled down image that the neural 
 * network model will see. Gives the user a sense of what the neural network 
 * model takes in as input.
 */

// Package and imports
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// PixelatedImagePanel
// Displays the pixelated image which is what the neural network model actually 
// sees in the backend when making a prediction
public class PixelatedImagePanel extends DisplayPanel {

    // GUI components for this panel
    JLabel title;
    JLabel pixelated_image;

    // Constructor for pixelated display panel
    public PixelatedImagePanel(int x, int y, int width, int height){
        super(x, y, width, height);

        // Initialize visual components
        init();
    }

    // Initializes panel
    @Override
    public void init(){
        initTitle();
        initPixelatedImage();
    }

    // Initializes the title
    public void initTitle(){

        // Visual settings for title
        final int VERT_PAD = 70;
        final int TITLE_WIDTH = getWidth();
        final int TITLE_HEIGHT = 50;
        final int TITLE_X = 0;
        final int TITLE_Y = VERT_PAD;

        // Initialize title, set position
        setTitle(new JLabel("Neural Network Perspective", SwingConstants.CENTER));
        getTitle().setBounds(TITLE_X, TITLE_Y, TITLE_WIDTH, TITLE_HEIGHT);

        // Set visual components and add to panel
        getTitle().setFont(Util.getFont(20));
        // getTitle().setOpaque(true);
        // getTitle().setBackground(C2);
        getTitle().setForeground(Util.C1);
        add(getTitle());
    }

    // Initialize the image
    public void initPixelatedImage(){

        // Visual settintgs for image
        final int PIXELATED_IMAGE_VERT_PAD = 20;
        final int PIXELATED_IMAGE_WIDTH = getTitle().getWidth();
        final int PIXELATED_IMAGE_HEIGHT = getTitle().getWidth();
        final int PIXELATED_IMAGE_X = 0;
        final int PIXELATED_IMAGE_Y = getTitle().getY()+getTitle().getHeight()+PIXELATED_IMAGE_VERT_PAD;

        // Initialize image, set position
        setPixelated_image(new JLabel());
        getPixelated_image().setBounds(PIXELATED_IMAGE_X, PIXELATED_IMAGE_Y, PIXELATED_IMAGE_WIDTH, PIXELATED_IMAGE_HEIGHT);

        // Set visual components, add to panel
        getPixelated_image().setOpaque(true);
        getPixelated_image().setBorder(BorderFactory.createLineBorder(Util.C1));
        getPixelated_image().setBackground(Color.black);
        add(getPixelated_image());
    }

    // Getter and setter methods
    public JLabel getTitle() {
        return title;
    }

    public void setTitle(JLabel title) {
        this.title = title;
    }

    public JLabel getPixelated_image() {
        return pixelated_image;
    }

    public void setPixelated_image(JLabel pixelated_image) {
        this.pixelated_image = pixelated_image;
    }

    

}
