import java.util.*;

public class Model {
	
	private String name;
	private ArrayList<Class> classes = new ArrayList<Class>();
	
	public Model(){
		
	}
	
	public Model(String name, ArrayList<Class> c){
		this.name = name;
		this.classes = c;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
	
	public ArrayList<Class> getClasses(){
		return this.classes;
	}

}
