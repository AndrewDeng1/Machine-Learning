/*
 * Class: ModelSelectionFrame
 * Description: This frame is responsible for managing 
 * the selection of neural network models by the user. 
 * Contains scroll pane full of models accessible by 
 * the user, or option to create new model.
 */

// Package and imports
package View;

import Model.*;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.*;

// ModelSelectionFrame class
// Responsible for managing the neural network model selection by the user
public class ModelSelectionFrame extends MainFrame {

    // Width and height settings
    public static final int FRAME_WIDTH = 1500;
    public static final int FRAME_HEIGHT = 800;

    JLabel title;
    JScrollPane scroll_pane;
    ArrayList<JButton> buttons;
    JPanel button_panel;

    // Constructor for ModelSelectionFrame
    public ModelSelectionFrame() {
        initTitle();
        initScrollPane();
        initButtons();
    }

    public void initFrame() {

        // Give it a title
        setFrame(new JFrame("Model Selection Frame"));

        // Set layout, add main panel to JFrame
        getFrame().setLayout(null);

        // Initialize main panel
        getMain_panel().setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        getMain_panel().setLayout(null);
        getMain_panel().setBackground(Util.C1);
        getFrame().add(getMain_panel());

        // Set settings for main frame
        getFrame().setResizable(false);
        getFrame().setSize(FRAME_WIDTH, FRAME_HEIGHT);
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setVisible(true);
    }

    // Initializes the title
    public void initTitle(){

        // Visual settings for title
        final int HORI_PAD = 70;
        final int VERT_PAD = 50;
        final int TITLE_WIDTH = FRAME_WIDTH-3*HORI_PAD;
        final int TITLE_HEIGHT = 50;
        final int TITLE_X = HORI_PAD;
        final int TITLE_Y = VERT_PAD;

        // Initialize title, set position
        setTitle(new JLabel("SELECT A NEURAL NETWORK MODEL", SwingConstants.CENTER));
        getTitle().setBounds(TITLE_X, TITLE_Y, TITLE_WIDTH, TITLE_HEIGHT);

        // Set visual components and add to panel
        getTitle().setFont(Util.getFont(50));
        getTitle().setForeground(Util.C4);
        getMain_panel().add(getTitle());
    }

    // Initialize the scroll pane
    public void initScrollPane() {

        // Settings for scroll pane
        final int SCROLL_PANE_HORI_PAD = 100;
        final int SCROLL_PANE_VERT_PAD = 50;
        final int SCROLL_PANE_WIDTH = FRAME_WIDTH - 3 * SCROLL_PANE_HORI_PAD;
        final int SCROLL_PANE_HEIGHT = FRAME_HEIGHT - 5 * SCROLL_PANE_VERT_PAD;
        final int SCROLL_PANE_X = SCROLL_PANE_HORI_PAD;
        final int SCROLL_PANE_Y = getTitle().getY()+getTitle().getHeight()+SCROLL_PANE_VERT_PAD;

        setButton_panel(new JPanel());
        getButton_panel().setLayout(new GridBagLayout());

        // Initialize set position and add scroll pane
        setScroll_pane(new JScrollPane(getButton_panel()));
        getScroll_pane().setBorder(BorderFactory.createLineBorder(Util.C4));
        getScroll_pane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getScroll_pane().setBounds(SCROLL_PANE_X, SCROLL_PANE_Y, SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT);
        getMain_panel().add(getScroll_pane());
    }

    // Initialize the buttons
    public void initButtons() {

        // Init arraylist of buttons
        setButtons(new ArrayList<>());

        // Gets all files in the serialized models path
        // https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
        File folder = new File(Model.SERIALIZED_MODELS_PATH);
        File[] listOfFiles = folder.listFiles();

        // Layout settings for panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // Iterate through each file
        for (int i = 0; i < listOfFiles.length; i++) {

            // Must be a file
            if (!listOfFiles[i].isFile()) {
                continue;
            }

            String file = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length() - 5);
            JButton but = new JButton(file);

            but.setFont(Util.getFont(50));
            but.setForeground(Util.C1);
            but.setBackground(Util.C4);

            gbc.gridy = i;
            getButton_panel().add(but, gbc);
            getButtons().add(but);
        }
    }

    public static int getFrameWidth() {
        return FRAME_WIDTH;
    }

    public static int getFrameHeight() {
        return FRAME_HEIGHT;
    }

    public JScrollPane getScroll_pane() {
        return scroll_pane;
    }

    public void setScroll_pane(JScrollPane scroll_pane) {
        this.scroll_pane = scroll_pane;
    }

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<JButton> buttons) {
        this.buttons = buttons;
    }

    public JPanel getButton_panel() {
        return button_panel;
    }

    public void setButton_panel(JPanel button_panel) {
        this.button_panel = button_panel;
    }

    public JLabel getTitle() {
        return title;
    }

    public void setTitle(JLabel title) {
        this.title = title;
    }

}
