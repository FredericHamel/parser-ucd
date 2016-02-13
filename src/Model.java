import java.util.*;

public class Model {
	
	private String name;
	private Set<Classe> classes;
	private Set<Relation> relation;
        private Set<Association> assoc;
        private Set<Aggregation> aggregation;
	private Set<Generalization> generalizations;
	
	public Model(){
            this("");
	}
	
	public Model(String name){
		this.name = name;
		this.classes = new TreeSet<>();
		this.generalizations = new TreeSet<>();
                this.assoc = new TreeSet<>();
                this.aggregation = new TreeSet<>();
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
	
        public boolean addAssociation(Association a) {
            return this.assoc.add(a);
        }
        
	public boolean addGeneralization(Generalization g) {
            return this.generalizations.add(g);
	}
	
        public boolean addAggregation(Aggregation a) {
            return this.aggregation.add(a);
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
                if(create) {
                    g = new Generalization(name);
                    this.addGeneralization(g);
                }else
                    g = null;
		return g;
	}
	
        public Iterator<Association> getAssociationIterator() {
            return this.assoc.iterator();
        }
        
        public Iterator<Aggregation> getAggregationIterator() {
            return this.aggregation.iterator();
        }
        
	public Iterator<Generalization> getGeneralizationIterator() {
		return this.generalizations.iterator();
	}
        
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("MODEL %s\n", name));
            Iterator<Classe> cIter = getClasseIterator();
            while(cIter.hasNext()) {
                sb.append(cIter.next());
                sb.append("\n");
            }
            
            Iterator<Generalization> genIter = getGeneralizationIterator();
            while(genIter.hasNext()){
                sb.append(genIter.next());
                sb.append("\n");
            }
            
            Iterator<Association> relIter = getAssociationIterator();
            while(relIter.hasNext()){
                sb.append(relIter.next());
                sb.append("\n");
            }
            
            Iterator<Aggregation> aggIter = getAggregationIterator();
            while(aggIter.hasNext()){
                sb.append(aggIter.next());
                sb.append("\n");
            }         
            return sb.toString();
        }
}
