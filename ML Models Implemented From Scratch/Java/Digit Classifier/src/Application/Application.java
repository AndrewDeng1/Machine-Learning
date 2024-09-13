// Name: Andrew Deng
// Date: January 19, 2024
// Course Code: ICS4U1
// Title: Digit Classifier
// Description: Allows user to draw a digit. Image is converted into numerical 
// values represented as a matrix, then passed into a neural network trained on 
// recognizing hand written digits, which attempts to classify the digit between 
// 0 and 9.
// Features:
// - No machine-learning related external libraries: Neural network, neural network 
// layers, and the training and serialization processes were all made from scratch. 
// All the math and matrix operations were coded from scratch, and require no use of 
// external libraries.
// - Drawing feature: User is able to draw an image.
// - Classification feature: Image is classified into digit between 0 and 9.
// - Display neural network perspective feature: Displays how the neural network sees the image.
// - Display confidence feature: Displays the probability output of the neural network model.
// - Model selection feature: User is able to select from different models to test out.
// Major skills:
// - Modular programming
// - OOP
// - Use of external libraries (only for convenience for model serialization into JSON)
// - Data structures: ArrayList, 2D ArrayList, String, int, double
// - Iteration
// Areas of Concern:
// - Machine learning model is not very accurate.
// - User doesn't see much of what's happening in the backend math-wise.

/*
 * Class: Application
 * Description: Used to run the program. Contains application status, 
 * determines whether program trains model or displays GUI.
 */

// Package and imports
package Application;

import Controller.Controller;

// Application class
// For running the program
public class Application {

    // Determines what the application should do when running
    // Should it train the nn?
    // Should it run the GUI?
    // Either "TRAIN" or "GUI"
    // public static final String APPLICATION_STATUS = "TRAIN";
    public static final String APPLICATION_STATUS = "GUI";

    // Main method to run program
    public static void main(String[] args) {

        // Instantiating Controller class runs the program
        new Controller();
    }
}
