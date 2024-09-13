/*
 * Class: Model class
 * Description: The main class in the Model package, used to 
 * handle interactions with the neural network coming from the 
 * controller. Also contains the training + validation pipeline 
 * for the neural network.
 */

// Package and imports
package Model;

import java.io.*;
import java.util.*;

// Model class
// Responsible for handling the neural network, should be used by only Controller class.
public class Model {

    // ---------- NEURAL NETWORK SETTINGS ---------- //
    // Path to training and test csv files
    public static final String TRAIN_CSV_PATH = "src\\Model\\train.csv";

    // Path to directory containing serialized models
    public static final String SERIALIZED_MODELS_PATH = "src\\SerializedNNModels";

    // Settings for each image
    public static final int SAMPLE_WIDTH = 28;
    public static final int SAMPLE_HEIGHT = 28;
    public static final int LABEL_BUFFER = 1;

    // Number of classes an entry can be classified as (0 to 9, so 10 in classes
    // total)
    public static final int NUM_CLASSES = 10;

    // Settings for dataset split
    public static final int DATA_SIZE = 42000;
    public static final int VAL_DATA_SIZE = 100;
    public static final int TRAIN_DATA_SIZE = 10000;

    // Hyperparameters for training
    public static final double LEARNING_RATE = 0.1;
    public static final int EPOCHS = 50;
    public static final int DISPLAY_ACCURACY_EVERY_THIS_MANY_EPOCHS = 1;

    // Whether or not the model should be serialized after it is done training
    public static final boolean SERIALIZE = true;
    // ---------- NEURAL NETWORK SETTINGS ---------- //

    // Data for training and validating
    Matrix data;

    // Neural network itself
    NeuralNetwork neural_network;

    // Constructor for model class
    public Model() {
        setData(read_csv(TRAIN_CSV_PATH));
    }

    // Creates a new neural network
    public void new_neural_network(String name){

        // Initializes new neural network, set default parameters
        setNeural_network(new NeuralNetwork());
        getNeural_network().init_new_neural_network();
    }
    
    // Loads a serialized neural network
    public void load_neural_network(String name){

        // Initializes neural network and loads it
        setNeural_network(new NeuralNetwork());
        getNeural_network().load_neural_network(SERIALIZED_MODELS_PATH, name);
    }

    // Default training neural network settings
    public void train_neural_network(String name, int epochs) {
        train_and_validate_neural_network(1000, 100, true, 0.1, epochs);
    }

    // Trains and validates the neural network
    public void train_and_validate_neural_network(int train_data_size, int val_data_size, boolean shuffle, double learning_rate, int epochs) {

        // Read in training data as matrix
        Matrix data = getData();

        // Shuffle training data to avoid bias
        if(shuffle){
            Collections.shuffle(data.getMatrix());
        }

        // Split into validation and training portions
        // Originally m/n x 785, so transpose into 785 x m/n to get
        // label and samples slices
        Matrix val_data = data.slice(0, val_data_size-1).transpose();
        Matrix train_data = data.slice(val_data_size, val_data_size+train_data_size - 1).transpose();

        // Slice to get validation labels and samples
        Matrix val_Y = val_data.slice(0, 0);
        Matrix val_X = val_data.slice(1, val_data.numRows() - 1);

        // Slice to get training labels and samples
        Matrix train_Y = train_data.slice(0, 0);
        Matrix train_X = train_data.slice(1, train_data.numRows() - 1);

        // Train the neural network
        // getNeural_network().load_neural_network(SERIALIZED_MODELS_PATH, DEFAULT_SERIALIZED_JSON_FILE_NAME);
        getNeural_network().train(train_X, train_Y, learning_rate, epochs);

        // Validate the model
        getNeural_network().validate(val_X, val_Y);
    }

    // Serializes the model
    public void serialize_neural_network(String name){
        getNeural_network().serialize_neural_network(SERIALIZED_MODELS_PATH, name);
    }   

    // Reads the CSV training data
    // Input:
    // path: String = path to data
    // Return:
    // Matrix = dataset as a matrix
    public static Matrix read_csv(String path) {

        // Return matrix
        Matrix ret = new Matrix();

        // Parse CSV file
        // https://howtodoinjava.com/java/io/parse-csv-files-in-java/
        try (Scanner scanner = new Scanner(new File(path))) {

            // To skip first row of column titles
            scanner.nextLine();

            // Count so only reads in DATA_SIZE number of lines
            int count = 0;

            // Read line
            while (scanner.hasNextLine() && count < DATA_SIZE) {

                // Inc the count
                count++;

                // Get the line
                String line = scanner.nextLine();

                // Scan the line for tokens
                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(",");

                    // Array of doubles to represent one entry into the dataset
                    ArrayList<Double> arr = new ArrayList<>();

                    // Since all pixels (785) + the label
                    for (int i = 0; i < SAMPLE_HEIGHT * SAMPLE_WIDTH + 1; i++) {

                        // Populate array with dataset entry
                        arr.add(Double.valueOf(rowScanner.next()));
                    }

                    // Add one dataset entry
                    ret.add(arr);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return ret;
    }

    // Getter and setter methods
    public NeuralNetwork getNeural_network() {
        return neural_network;
    }

    public void setNeural_network(NeuralNetwork neural_network) {
        this.neural_network = neural_network;
    }

    public static String getTrainCsvPath() {
        return TRAIN_CSV_PATH;
    }

    public static String getSerializedModelsPath() {
        return SERIALIZED_MODELS_PATH;
    }

    public static int getSampleWidth() {
        return SAMPLE_WIDTH;
    }

    public static int getSampleHeight() {
        return SAMPLE_HEIGHT;
    }

    public static int getLabelBuffer() {
        return LABEL_BUFFER;
    }

    public static int getNumClasses() {
        return NUM_CLASSES;
    }

    public static int getDataSize() {
        return DATA_SIZE;
    }

    public static int getValDataSize() {
        return VAL_DATA_SIZE;
    }

    public static int getTrainDataSize() {
        return TRAIN_DATA_SIZE;
    }

    public static double getLearningRate() {
        return LEARNING_RATE;
    }

    public static int getEpochs() {
        return EPOCHS;
    }

    public static int getDisplayAccuracyEveryThisManyEpochs() {
        return DISPLAY_ACCURACY_EVERY_THIS_MANY_EPOCHS;
    }

    public static boolean isSerialize() {
        return SERIALIZE;
    }

    public Matrix getData() {
        return data;
    }

    public void setData(Matrix data) {
        this.data = data;
    }

    

}
