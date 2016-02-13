import java.util.*;

public class Generalization {
	
	private String name;
	private Set<Classe> subClasses;
	
	public Generalization(String name){
		this.name = name;
		this.subClasses = new TreeSet<>();
	}
	
	public boolean addSubClasses(Classe subClass){
		return this.subClasses.add(subClass);
	}
	
	public String getName(){
		return this.name;
	}
	
	public Iterator<Classe> getSubClasseIterator(){
        return this.subClasses.iterator();
	}

	
    public int compareTo(Classe o) {
        return name.compareTo(o.getName());
    }
}