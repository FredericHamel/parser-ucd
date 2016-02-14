import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JFrame;


public class MainApp {

	public static void main(String[] args) {
		
		JFrame frame = new GUI();
		frame.setTitle("Parser de fichiers .ucd");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		String path = frame.getFile().getAbsolutePath();	
		
		ParserUCD p = new ParserUCD();
        try {
            Schema schema = new Schema(path);
            p.parse(schema);
            System.out.println(schema.getModel());
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
        
	}
}
