/*
 * Class: NeuralNetwork class
 * Description: Used to represent the entire neural network. 
 * Contains the neural network layers, and all the methods 
 * necessary to training, infering, validating, loading, 
 * serializing the model. Handles all processes related to 
 * the neural network.
 */

// Package and imports
package Model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;

// NeuralNetwork class
// Used to represent a neural network, and contains all methods necessary for training, validation, inference, and serializing a neural network model
// The core math components found in this class can be found here: https://www.youtube.com/watch?v=w8yWXqWQYmU&t=0s
public class NeuralNetwork {

    // NOTATION
    // nn = "Neural Network"
    // nnl = "Neural Network Layer"
    // L = number of neural network layers in the neural network
    // m = number of training data entries
    // n = number of validation data entries
    // X = samples (input data, model makes prediction based on this data)
    // Y = labels (ground truth, the "correct answer" associated with each entry in
    // X)
    // A_i = activated layer (matrix) of ith nn layer
    // Z_i = unactivated layer (matrix) of ith nn layer
    // W_i = weights layer (matrix) of ith nn layer
    // B_i = biases layer (matrix) of ith nn layer
    // f_i = activation function of ith nn layer
    // f_i' = derivative of activation function of ith nn layer
    // dZ_i = delta unactivated layer (matrix) of ith nn layer
    // dW_i = delta weights layer (matrix) of ith nn layer
    // dB_i = delta biases layer (matrix) of ith nn layer

    // Array of neural network layers
    public ArrayList<NeuralNetworkLayer> neural_network_layers;

    // Constructor for NeuralNetwork class
    // Initializes and populates the arraylist of nnls
    public NeuralNetwork() {

    }
    
    // Initializes new neural network
    public void init_new_neural_network(){
        
        // Initialize array list to store neural network layers
        setNeural_network_layers(new ArrayList<>());
        
        // Add neural network layers
        getNeural_network_layers().add(new NeuralNetworkLayer(10, 784, "ReLU"));
        getNeural_network_layers().add(new NeuralNetworkLayer(10, 10, "Softmax"));
    }

    // Trains the neural network
    // X --> training data, excluding the labels
    // Y --> labels for training data
    // learning_rate --> measure for how fast you want neural network to train
    // (affects gradient descent)
    // epochs --> number of times neural network iterates over entire training
    // dataset
    // Inputs:
    // X: Matrix = 785 x m
    // Y: Matrix = 1 x m
    // learning_rate: double = specifies how "fast" gradient descent is performed
    // epochs: int = number of times to iterate over entire training set
    // Return:
    // None
    public void train(Matrix X, Matrix Y, double learning_rate, int epochs) {

        // Iterate over each epoch
        for (int i = 0; i < epochs; i++) {

            // Get prediction matrix from the forward pass
            Matrix preds = forward(X);

            // Pass in samples + labels + learning rate to do backward propagation to train
            // model
            backward(X, Y, learning_rate);

            // Every 10th iteration, display prediction accuracy
            if (i % Model.DISPLAY_ACCURACY_EVERY_THIS_MANY_EPOCHS == 0) {
                System.out.printf("EPOCH: %d --> ACCURACY: %.5f\n", i, accuracy(inverse_one_hot_encode(preds), Y));
            }
        }
    }

    // Validation process
    // Tests how accurate the neural network is on data it hasn't trained on
    // Inputs:
    // val_X: Matrix = 785 x n; the validation samples
    // val_Y: Matrix = 1 x n; the validation labels
    // Return:
    // None
    public void validate(Matrix val_X, Matrix val_Y) {

        // Gets the model's prediction on the validation samples
        Matrix preds = forward(val_X);

        // Outputs accuracy on validation samples
        // Notice how doesn't do backward propagation on validation samples. This is
        // because we don't want to train the nn on the validation samples, so we can
        // test model accuracy on data it hasn't trained for, to see how well it
        // generalized.
        System.out.printf("VALIDATION ACCURACY: %.5f\n", accuracy(inverse_one_hot_encode(preds), val_Y));
    }

    // Inference process
    // Classifies a single sample
    // Inputs:
    // sample: Matrix = 785 x 1
    // Returns:
    // Matrix = 10 x 1, represents the probabilities for each class
    public Matrix inference(Matrix sample) {

        // sample.printAsImage();

        // Inference should only be used on a single sample, i.e. sample should be of
        // shape 784 x 1
        if (sample.numRows() != Model.SAMPLE_WIDTH * Model.SAMPLE_HEIGHT || sample.numCols() != 1) {
            System.out.printf("ATTEMPTED INFERENCE BUT SAMPLE SHAPE IS: %d x %d, when it should be 785 x 1\n",
                    sample.numRows(), sample.numCols());
            return new Matrix();
        }

        // Gets the model's prediction on a single sample
        // 1 x 1
        Matrix preds = forward(sample);
        // preds = inverse_one_hot_encode(preds);

        // System.out.printf("infering, %d %d, %d\n", preds.numRows(), preds.numCols(),
        // (int) Math.round(preds.get(0).get(0)));

        // Gets prediction from model, just single cell, converts to integer
        // return (int) Math.round(preds.get(0).get(0));

        // Returns the output of the model
        return preds;
    }

    // Inverse one hot encode to get actual integer class, not matrix
    public int inverse_one_hot_encode_only_class(Matrix preds){
        return (int)Math.round(inverse_one_hot_encode(preds).get(0).get(0));
    }

    // Returns percentage (in decimal) representing correct predictions
    // Input:
    // predictions: Matrix = m x 1
    // ground_truth: Matrix = 1 x m
    // Return:
    // double = percentage (as decimal) representing percentage of correct
    // predictions
    public double accuracy(Matrix predictions, Matrix ground_truth) {

        // predictions is initially m x 1
        // predictions and ground_truth should both be 1 x m
        predictions = predictions.transpose();

        // Predictions and labels arraylists should be same size
        if (predictions.numCols() != ground_truth.numCols()) {
            System.out.printf(
                    "\n\nCALCULATING ACCURACY BUT NUMBER OF ROWS IN PREDICTION AND GROUND TRUTH MATRIX DON'T MATCH. prediction: %d x %d (cols important), ground_truth: %d x %d\n\n",
                    predictions.numRows(), predictions.numCols(), ground_truth.numRows(), ground_truth.numCols());
            return -1.0;
        }

        // Calculates number of correct predictions
        double num_correct = 0;
        for (int j = 0; j < Math.min(predictions.numCols(), ground_truth.numCols()); j++) {

            // Check if the jth prediction is correct
            num_correct += Double.compare(predictions.get(0).get(j), ground_truth.get(0).get(j)) == 0 ? 1.0 : 0.0;
        }

        // Decimal of predictions gotten correct
        return (double) num_correct / Math.min(predictions.numCols(), ground_truth.numCols());
    }

    // Serializes this neural network as a json file
    // Inputs:
    // path: String = path to directory in which serialized nn should be placed
    // Returns:
    // None
    public void serialize_neural_network(String path, String name) {

        // https://www.youtube.com/watch?v=6aCwGfXVYOo
        try {
            ObjectMapper mapper = new ObjectMapper();

            // https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java
            File json_location = new File(path + "/" + name);
            
            // If file doesn't exist
            if (!json_location.exists()) {
                
                // Try to create new file
                try {
                    Files.createFile(Paths.get(path, name));
                } catch (IOException e) {
                    System.err.println("Error creating the JSON file: " + e.getMessage());
                }
            }

            // Convert the arraylist of nnls into json string, and put it in the file
            mapper.writeValue(json_location, getNeural_network_layers());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Loads the serialized weights and biases from a previously trained neural
    // network
    // Inputs:
    // path: String = path to the serialized neural network
    // Returns:
    // None
    public void load_neural_network(String path, String name) {

        // https://www.youtube.com/watch?v=6aCwGfXVYOo
        try {
            ObjectMapper mapper = new ObjectMapper();

            // https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java
            File json_location = new File(path + "/" + name);
            if (!json_location.exists() || json_location.isDirectory()) {
                System.out.println("\n\nATTEMPTED TO LOAD NN MODEL, BUT " + path + "/" + name + " DOESN'T EXIST");
                return;
            }

            // Read the json file and instantiate arraylist of nnls
            setNeural_network_layers(
                    mapper.readValue(json_location, new TypeReference<ArrayList<NeuralNetworkLayer>>() {
                    }));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Forward propagation
    // Pass an image (represented as a matrix) into the model, and it attempts to
    // categorize the image (i.e. predict what digit is in the matrix)
    // Note:
    // The input sizes say "m", but that's only necessary
    // Inputs:
    // X: Matrix = 785 x m
    // Returns:
    // Matrix = m x 1; represents the predictions of the model
    public Matrix forward(Matrix X) {

        // X is matrix representing grayscale of image, so doubles between 0 and 255
        // inclusive. Doubles must be normalized to the range 0 and 1 inclusive.
        X = X.multiply((double) 1 / 255);

        // Activation layer of the previous nn layer
        // Initially the image itself (i.e. input data) is treated as the "0th"
        // activation layer
        Matrix activated = X;

        // Iterates over nn layers
        for (int i = 0; i < getNeural_network_layers().size(); i++) {

            // Gets current nn layer
            NeuralNetworkLayer curr_layer = getNeural_network_layers().get(i);

            // Do unactivated layer = W_i . A_(i-1) + B_i
            curr_layer.setUnactivated(curr_layer.getWeights().dot_product(activated).add(curr_layer.getBiases()));

            // Do activated layer = activation_function(unactivated layer)
            // unactivated and activated should both be 10 x m
            curr_layer.setActivated(curr_layer.activation_function(curr_layer.getUnactivated()));

            // Store new activated layer of this nn layer as previous activated layer
            activated = curr_layer.getActivated();
        }

        // Returns matrix, which was activated by softmax function
        return activated;
    }

    // Backward propagation
    // Should only be called after "forward" is called
    // After "forward" is called and predictions are made, the final activation
    // layer in the nn represents the models predictions. To train the model, the
    // "loss" is calculated between the model's predictions and the "ground truth",
    // and in combination with the input data, is used to optimize the weight and
    // bias layers of the nn so the next time input data is passed into the nn the
    // prediction is (hopefully) more accurate.
    // Inputs:
    // X: Matrix = 785 x m
    // Y: Matrix = 1 x m
    // Returns:
    // None
    public void backward(Matrix X, Matrix Y, double learning_rate) {

        // X is matrix representing grayscale of image, so doubles between 0 and 255
        // inclusive. Doubles must be normalized to the range 0 and 1 inclusive.
        X = X.multiply((double) 1 / 255);

        // Number of entries in training dataset
        int m = X.numCols();

        // One hot encodes Y (ground truth)
        // one_hot_encoded_labels = 10 x m
        Matrix one_hot_encoded_labels = one_hot_encode(Y);

        // The "delta unactivated" matrix for unactivated layer of next nn
        // A quantification of the error of the unactivated layer of the next layer in
        // the nn
        Matrix next_delta_unactivated = null;

        // Iterate from last nn layer to first
        for (int i = getNeural_network_layers().size() - 1; i >= 0; i--) {

            // Get current nn layer
            NeuralNetworkLayer curr_layer = getNeural_network_layers().get(i);

            // "delta weights" and "delta biases" matrices
            // The changes to be made to the weight and bias layers for gradient descent
            Matrix delta_weights;
            Matrix delta_biases;

            // "delta unactivated" matrix corresponding to the unactivated layer of the
            // current nn layer
            Matrix delta_unactivated;

            // If this is the last neural network layer
            if (i == getNeural_network_layers().size() - 1) {

                // No "next" layer exists, so the error of the unactivated layer of this nn
                // layer is calculated by the difference between this nn layer's activated layer
                // and the (one hot encoded) ground truth
                // dZ_(L-1) = A_(L-1) - Y
                delta_unactivated = curr_layer.getActivated().subtract(one_hot_encoded_labels);
            } else {

                // Weights layer from next neural network layer
                Matrix next_weights = getNeural_network_layers().get(i + 1).getWeights();

                // Quantifies the error in this nn layer's unactivated layer
                // dZ_i = W_(i+1)^T . Z_(i+1) * f_i'(Z_i)
                delta_unactivated = next_weights.transpose().dot_product(next_delta_unactivated)
                        .multiply(curr_layer.differentiated_activation_function(curr_layer.getUnactivated()));
            }

            // If this is the first neural network layer
            if (i == 0) {

                // For first neural network layer, there is no activation layer from the
                // previous nn layer since no previous nn layer exists. Thus we use the inputted
                // training data instead.
                // dW_1 = dZ_1 . X^T * 1/m
                delta_weights = delta_unactivated.dot_product(X.transpose())
                        .multiply((double) 1 / m);
            } else {

                // Get activated layer from previous nn layer
                Matrix prev_activated = getNeural_network_layers().get(i - 1).getActivated();

                // Do dot product with transposed activated layer from previous nn layer
                // dW_i = dZ_i . A_(i-1)^T * 1/m
                delta_weights = delta_unactivated.dot_product(prev_activated.transpose())
                        .multiply((double) 1 / m);
            }

            // Calculate the change in the biases layer
            // dB_i = sum(dZ_i) * 1/m
            delta_biases = new Matrix(curr_layer.getBiases().numRows(), curr_layer.getBiases().numCols(),
                    (double) 1 / m).multiply(delta_unactivated.sum());
            next_delta_unactivated = delta_unactivated;

            // Gradient descent
            // I.e. Updating the weight and bias layers based on the delta weight and biases
            // W_i = W_i - dW_i
            // B_i = B_i - dB_i
            curr_layer.setWeights(curr_layer.getWeights().subtract(delta_weights.multiply(learning_rate)));
            curr_layer.setBiases(curr_layer.getBiases().subtract(delta_biases.multiply(learning_rate)));
        }

    }

    // Does one hot encoding for the labels
    // E.g. If an image is supposed to be "5", then creates array of size 10 (since
    // that's number of classes) and makes the 4th index a 1, and the rest 0s
    // Returns 10 x m matrix
    // Inputs:
    // Y: Matrix = 1 x m
    // Return:
    // Matrix = 1 x m
    public Matrix one_hot_encode(Matrix Y) {

        // Y is initially 1 x m
        // Transpose so m x 1
        Y = Y.transpose();

        // m x 10
        // 10 being total number of classes
        Matrix ret = new Matrix(Y.numRows(), Model.NUM_CLASSES, 0.0);

        // Iterate through all rows of Y
        for (int i = 0; i < Y.numRows(); i++) {

            // Since Y should be m x 1 (thus index i first, then 0), and classes from 0 to 9
            // (so Y.get(i).get(0) returns double between 0.0 and 9.0)
            ret.get(i).set((int) Math.round(Y.get(i).get(0)), 1.0);
        }

        // 10 x m
        return ret.transpose();
    }

    // Performs inverse of one hot encoding
    // Inputs:
    // preds: Matrix = 10 x m; the one hot encoded version of the predictions of the
    // model
    // Returns:
    // Matrix = m x 1; the non-one hot encoded version of the predictions of the
    // model
    public Matrix inverse_one_hot_encode(Matrix preds) {

        // preds is originally 10 x m
        // Transposed becomes m x 10
        preds = preds.transpose();

        // Will be shape m x 1
        // Where the only element in each row is the predicted class
        Matrix ret = new Matrix(preds.numRows(), 1);

        // Iterate through each row
        for (int i = 0; i < preds.numRows(); i++) {

            // Finds index associated with greatest integer in each column
            // Max value seen in this row
            double mx = -Integer.MIN_VALUE;

            // Index associated with max element
            int mx_ind = -1;

            // Iterate through columns of row
            for (int j = 0; j < preds.numCols(); j++) {

                // If a number is greater than the max number seen thus far, update the max
                // number seen, and update the index associated with max number seen
                if (mx < preds.get(i).get(j)) {
                    mx = preds.get(i).get(j);
                    mx_ind = j;
                }
            }

            // Adds that index (i.e. the predicted class for the image corresponding to that
            // row) to the return matrix
            ret.get(i).set(0, (double) mx_ind);
        }

        // m x 1
        return ret;
    }

    // Getter setter methods
    public ArrayList<NeuralNetworkLayer> getNeural_network_layers() {
        return neural_network_layers;
    }

    public void setNeural_network_layers(ArrayList<NeuralNetworkLayer> neural_network_layers) {
        this.neural_network_layers = neural_network_layers;
    }

}
