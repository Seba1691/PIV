/*
 * Example code for accessing any JPIV classes or methods from an command line
 * executable. This program does nothing else than launching jpiv and plotting
 * a vector file. The path of the vector file is read from the command line.
 * Modify this file as you like.
 */

package jpiv_cmd_wrapper;

// Import all jpiv core classes.
import jpiv2.*;

/**
 * @author Peter
 */
public class JpivCmdWrapper {

    private static JPiv jpiv;           // handle to main JPIV instance
    private static String filename;     // storing the command line parameter

    /** The main method is called by the java interpreter at startup.
     * It only stores the command line parameter in a global variable and
     * creates a new JpivCmdWrapper opject.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        filename = args[0];
        new JpivCmdWrapper();
    }

    /** Constructor of your wrapper class.
     * The constructor initializes a new JPIV instance and stores it in
     * a global variable.
     */
    public JpivCmdWrapper() {
        jpiv = new JPiv();
        jpiv.setVisible(true);
        showVectorFile( filename );
    }

    /** Example method.
     * Example for accessing an existing object (ListFrame)
     * and for creating a new object (DisplayVecFrame).
     * @param filename
     */
    private void showVectorFile( String filename ) {
        jpiv.getListFrame().appendElement(filename);
        new DisplayVecFrame(jpiv, filename);
    }
    
}
