import java.util.*;

public class Generalization {
	
	private String name;
	private Set<Classe> subClasses;
	
	public Generalization(String name){
		this.name = name;
		this.subClasses = new TreeSet<>();
	}
	
	public void addSubClasses(Classe subClass){
		this.subClasses.add(subClass);
	}
	
	public String getName(){
		return this.name;
	}
	
    public int compareTo(Classe o) {
        return name.compareTo(o.getName());
    }
}