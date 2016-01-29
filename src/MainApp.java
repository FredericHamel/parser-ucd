import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class MainApp {

	public static void main(String[] args) {
		
		File fichier = new File("Ligue.ucd");
		BufferedReader buffer = null;
		StringTokenizer strtok;
		
		Model m = new Model();
		
		try{	
			//Lire le fichier
			buffer = new BufferedReader(new FileReader(fichier));
			String ligne = buffer.readLine();
			
			//Lecture ligne par ligne
			while(ligne!=null){
				
				strtok = new StringTokenizer(ligne);
				
				//Decoupage d'une ligne avec espaces
				while(strtok.hasMoreElements()){
					if(strtok.nextElement() == "MODEL"){
						
						m.setName(strtok.nextToken());
						System.out.println("Le nom du model est:"+m.getName());
					
					}
					break;//Pour le moment
				}
				//ligne = nextLine();
				break;//Pour le moment
			}
		}catch(IOException e){
			System.out.println(e);
		}
		
	}

}
