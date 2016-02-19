import java.util.*;

public class Model implements IVisitable {

    private String name;
    private Set<Classe> classes;
    private Set<Association> assoc;
    private Set<Aggregation> aggregation;
    
    private Classe lastClasse;

    public Model(){
        this("");
    }
    
    public Model(String name){
        this.name = name;
        this.classes = new TreeSet<>();
        this.assoc = new TreeSet<>();
        this.aggregation = new TreeSet<>();
        this.lastClasse = null;
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

    public boolean addAggregation(Aggregation a) {
        return this.aggregation.add(a);
    }

    public Classe findClasse(String name, boolean create) {
        Classe c = null;
        Iterator<Classe> it = this.getClasseIterator();
        
        if(lastClasse != null && lastClasse.getName().equals(name)) {
            return lastClasse;
        }
        
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
        this.lastClasse = c;
        return c;
    }

    public Iterator<Classe> getClasseIterator() {
        return this.classes.iterator();
    }

    public Iterator<Association> getAssociationIterator() {
        return this.assoc.iterator();
    }

    public Iterator<Aggregation> getAggregationIterator() {
        return this.aggregation.iterator();
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
    
    @Override
    public void accept(IVisiteur v) {
        v.visit(this);
    }
}
