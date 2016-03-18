package metric;

import java.io.FileWriter;
import java.io.IOException;
import facade.Admin;
import parserucd.Classe;
import java.util.ArrayList;

public class CSVFileCreator {
	private String filename;
	private Admin admin;
	
	public CSVFileCreator(String filename){
		this.filename = filename;
		this.admin = new Admin();
		createFile();
	}
	
	public void createFile(){
		try{
		    FileWriter w = new FileWriter(this.filename);
	 
		    w.append("CLASSE,");
		    w.append(" ANA,");
		    w.append(" NOM,");
		    w.append(" NOA,");
		    w.append(" ITC,");
		    w.append(" ETC,");
		    w.append(" CAC,");
		    w.append(" DIT,");
		    w.append(" CLD,");
		    w.append(" NOC,");
		    w.append(" NOD");
		    w.append("\n");

		    /*
		     *Boucle qui lit les valeurs
		     *pour chaque classe.
		     */
		    ArrayList<String> c = this.admin.getClassesName();
		    ArrayList<Metrique> m = new ArrayList<>();
		    for(String classe : c){
		    	w.append(classe);
		    	this.admin.search(classe);
		    	m = this.admin.getMetriquesOfCurrentClass();
		    	for(Metrique metrique: m){
		    		w.append(metrique.getName());
		    	}
		    	w.append("\n");
		    }
		    				
		    w.flush();
		    w.close();
		    
		}catch(IOException e){
		     e.printStackTrace();
		} 
	}
}
