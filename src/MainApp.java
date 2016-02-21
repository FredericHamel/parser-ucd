import javax.swing.JFrame;

/**
 * Methode main qui permet de lancer l'application. 
 * @author Frederic Hamel et Sabrina Ouaret
 */

public class MainApp {

    public static void main(String[] args) {

        JFrame frame = new GUI();
        frame.setTitle("Parser de fichiers .ucd");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
