/*
 * Class: Util
 * Description: Class containing utility methods useful for frames and panels 
 * across entire View package such as getting a font.
 */

// Imports and packages
package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.*;
import javax.swing.*;

// Util class
// Utility methods for View package
public class Util {
    
    // Colors used throughout the program
    public static final Color C1 = Color.decode("#D8C99B");
    public static final Color C2 = Color.decode("#D8973C");
    public static final Color C3 = Color.decode("#BD632F");
    public static final Color C4 = Color.decode("#A4243B");
    public static final Color C5 = Color.decode("#D14A63");
    
    // Returns font of specified size, but also bold or italic based on parameters
    public static Font getFont(int size) {

        // https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
        // Change path to font based on whether bolded or italicized
        Font font;

        try {

            // create the font to use. Specify the size!
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src\\View\\Fonts\\MonoLisaBold.otf"))
                    .deriveFont((float) size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            // register the font
            ge.registerFont(font);

            return font;
        } catch (IOException e) {
            System.out.println("\n\nFONT IMPORT ERROR !!!\n\n");
            e.printStackTrace();
        } catch (FontFormatException e) {
            System.out.println("\n\nFONT IMPORT ERROR !!!\n\n");
            e.printStackTrace();
        }

        return null;
    }
}
