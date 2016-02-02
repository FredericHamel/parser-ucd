import java.util.*;

public class Model {
	
	private String name;
	private Set<Classe> classes;
	
	
	public Model(){
		this.name="";
		this.classes = new TreeSet<>();
	}
	
	public Model(String name){
		this.name = name;
		this.classes = new TreeSet<>();
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
	
	public void addClasse(Classe c) {
		classes.add(c);
	}
	
	public Classe findClasse(String name, boolean create) {
		Classe c = null;
		Iterator<Classe> it = this.getClasseIterator();
		while(it.hasNext()) {
			c = it.next();
			if(c.getName().equals(name))
				return c;
		}
		return create ? new Classe(name) : null;
	}
	
	public Iterator<Classe> getClasseIterator() {
		return this.classes.iterator();
	}

}
