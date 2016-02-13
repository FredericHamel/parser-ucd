import java.util.*;

public class Model {
	
	private String name;
	private Set<Classe> classes;
	private Set<Relation> relation;
	private Set<Generalization> generalizations;
	
	public Model(){
		this.name="";
		this.classes = new TreeSet<>();
		this.generalizations = new TreeSet<>();
	}
	
	public Model(String name){
		this.name = name;
		this.classes = new TreeSet<>();
		this.generalizations = new TreeSet<>();
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
	
	public boolean addClasse(Classe c) {
		return classes.add(c);
	}
	
	public void addGeneralization(Generalization g) {
		generalizations.add(g);
	}
	
	public Classe findClasse(String name, boolean create) {
		Classe c = null;
		Iterator<Classe> it = this.getClasseIterator();
		while(it.hasNext()) {
			c = it.next();
			if(c.getName().equals(name))
				return c;
		}
                if(create) {
                    c = new Classe(name);
                    this.addClasse(c);
                }
                else
                    c = null;
                    
		return c;
	}
	
	public Iterator<Classe> getClasseIterator() {
		return this.classes.iterator();
	}
	
	public Generalization findGeneralization(String name, boolean create) {
		Generalization g = null;
		Iterator<Generalization> it = this.getGeneralizationIterator();
		while(it.hasNext()) {
			g = it.next();
			if(g.getName().equals(name))
				return g;
		}
		return create ? new Generalization(name) : null;
	}
	
	public Iterator<Generalization> getGeneralizationIterator() {
		return this.generalizations.iterator();
	}

}
