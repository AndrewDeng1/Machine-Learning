/*
 * Class: NeuralNetworkLayer
 * Description: The template class used to represent a single 
 * neural network layer. Contains the activated and unactivated 
 * layers within this nnl as matrices, as well as the weight and 
 * bias matrices between this layer and the previous layer.
 */

// In Model package
package Model;

// NeuralNetworkLayer class
// Represents a neural network, its activated and unactivated 
// layer, and the weight and bias matrices between this and the 
// previous layer.
public class NeuralNetworkLayer {

    // Matrices to represent the weights, biases, unactivated, and activated layers
    Matrix weights;
    Matrix biases;
    Matrix unactivated;
    Matrix activated;

    // The activation function that will be applied to the unactivated layer to get
    // to the activated layer.
    String activation_function;

    // Constructor for NeuralNetworkLayer class
    // Takes in the dimensions that the weight matrix should be
    // Bias matrix will automatically have same number of rows but only one column
    public NeuralNetworkLayer(int n, int m, String activation_function) {

        // Initialize weight and bias matrices
        setWeights(new Matrix(n, m));
        setBiases(new Matrix(n, 1));

        // Sets the activation function
        setActivation_function(activation_function);
    }

    // Empty constructor for when instantiating the JSON version of this class
    public NeuralNetworkLayer() {

    }

    // Applies the activation function to a matrix
    public Matrix activation_function(Matrix matrix) {

        // Detects which activation function this layer is supposed to have
        switch (getActivation_function()) {
            case "ReLU":
                return matrix.ReLU();
            case "Softmax":
                return matrix.softmax();
        }

        // Invalid activation function
        System.out.println("\n\nACTIVATION FUNCTION IN NEURAL NETWORK LAYER ISN'T VALID\n\n");
        return new Matrix();
    }

    // Applies the differentiated activation function to a matrix
    // Used for backpropagation
    public Matrix differentiated_activation_function(Matrix m) {

        // Gets the activation function
        switch (getActivation_function()) {
            case "ReLU":
                return m.differentiated_ReLU();

            // Note:
            // Purposely no differentiated_softmax because it's only used for output layer,
            // and when generating delta_unactivated for output layer, relies on loss
            // function between prediction (i.e. activated layer, activated using softmax
            // function) and label rather than derivative of activation function
        }

        // Invalid activation function
        System.out.println("\n\nACTIVATION FUNCTION IN NEURAL NETWORK LAYER ISN'T VALID\n\n");
        return new Matrix();
    }

    // Getter and setter methods
    public Matrix getUnactivated() {
        return unactivated;
    }

    public void setUnactivated(Matrix unactivated) {
        this.unactivated = unactivated;
    }

    public Matrix getActivated() {
        return activated;
    }

    public void setActivated(Matrix activated) {
        this.activated = activated;
    }

    public Matrix getWeights() {
        return weights;
    }

    public void setWeights(Matrix weights) {
        this.weights = weights;
    }

    public Matrix getBiases() {
        return biases;
    }

    public void setBiases(Matrix biases) {
        this.biases = biases;
    }

    public String getActivation_function() {
        return activation_function;
    }

    public void setActivation_function(String activation_function) {
        this.activation_function = activation_function;
    }

    // Displays a neural network layer
    @Override
    public String toString() {
        String ret = "";
        ret += "Weights:\n";
        ret += getWeights() + "\n";
        ret += "Biases:\n";
        ret += getBiases() + "\n";
        return ret;
    }
}
