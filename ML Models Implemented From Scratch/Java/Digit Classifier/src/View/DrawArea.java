/*
 * The majority of the code was found online: https://www.youtube.com/watch?v=OOb1eil4PCo&t=0s
 * 
 * Class: DrawArea
 * Description: This is the class that handles the object where 
 * the user can draw their own digit. When the submit button is 
 * clicked, the image they drew will be accessed, converted into 
 * a buffered image, scaled, converted into a matrix, and passed 
 * into the neural network.
 */

// Package and imports
package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// The majority of this code was found here: https://www.youtube.com/watch?v=OOb1eil4PCo&t=0s
// DrawArea class
// Handles the area in which the user can draw a digit between 0 and 9.
// Inherits JComponent class, so can be used like JComponent. It is placed in a JPanel.
public class DrawArea extends JComponent {

  // The thickness of the brush the user uses to draw
  public static final int BRUSH_WIDTH = 50;

  // Image in which we're going to draw
  private Image image;

  // Graphics2D object ==> used to draw on
  private Graphics2D graphic;

  // Mouse coordinates
  private int currentX, currentY, oldX, oldY;

  // Constructor for DrawArea class
  // Adds the action listeners
  public DrawArea() {

    // Set double buffered to false
    setDoubleBuffered(false);

    // Adds mouse listener to detect where user clicked
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {

        // save coord x,y when mouse is pressed
        oldX = e.getX();
        oldY = e.getY();
      }
    });

    // Detects where the mouse moves
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {

        // coord x,y when drag mouse
        currentX = e.getX();
        currentY = e.getY();

        if (graphic != null) {
          // draw line if graphic context not null

          // https://stackoverflow.com/questions/2839508/java2d-increase-the-line-width
          graphic.setStroke(new BasicStroke(BRUSH_WIDTH));

          // Draws the line
          graphic.drawLine(oldX, oldY, currentX, currentY);

          // refresh draw area to repaint
          repaint();

          // store current coords x,y as olds x,y
          oldX = currentX;
          oldY = currentY;
        }
      }
    });
  }

  // Handles when JComponent.paintComponent is called
  @Override
  protected void paintComponent(Graphics g) {

    // Check if image is not instantiated
    if (image == null) {

      // image to draw null ==> we create
      image = createImage(getSize().width, getSize().height);
      graphic = (Graphics2D) image.getGraphics();

      // enable antialiasing
      graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      // clear draw area
      clear();
    }

    // Draw the image onto the graphic
    g.drawImage(image, 0, 0, null);
  }

  // Clears the draing area
  public void clear() {

    // Sets color we paint with to black
    graphic.setPaint(Color.black);

    // Paints entire draw area black
    graphic.fillRect(0, 0, getSize().width, getSize().height);

    // Sets paint color back to white
    graphic.setPaint(Color.white);

    // Repaints graphic to update change
    repaint();
  }

  // Getter and setter methods
  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public Graphics2D getGraphic() {
    return graphic;
  }

  public void setGraphic(Graphics2D graphic) {
    this.graphic = graphic;
  }

  public int getCurrentX() {
    return currentX;
  }

  public void setCurrentX(int currentX) {
    this.currentX = currentX;
  }

  public int getCurrentY() {
    return currentY;
  }

  public void setCurrentY(int currentY) {
    this.currentY = currentY;
  }

  public int getOldX() {
    return oldX;
  }

  public void setOldX(int oldX) {
    this.oldX = oldX;
  }

  public int getOldY() {
    return oldY;
  }

  public void setOldY(int oldY) {
    this.oldY = oldY;
  }

  public static int getBrushWidth() {
    return BRUSH_WIDTH;
  }
}