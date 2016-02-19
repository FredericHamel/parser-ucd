import java.io.IOException;
import java.util.*;

public class Admin {
    private Model model;
    private static Admin instance;
    private final ParserUCD parser;
    private Map<String, String> relations;
    
    private VisiteurUCD v;
    
    private static final Object lock = new Object();

    public Admin(){
        this.model = null;
        this.parser = ParserUCD.getInstance();
        this.relations = new HashMap<>();
        this.v = null;
    }

    public static Admin getInstance(){
        if(instance == null){
            synchronized(lock) {
                if(instance == null)
                    instance = new Admin();
            }
        }
        return instance;
    }

    public void parseModel(String filename) throws IOException {

        this.model = parser.parse(filename);
        Association assoc;
        Iterator<Association> assocIt = this.model.getAssociationIterator();
        while(assocIt.hasNext()) {
            assoc = assocIt.next();
            this.relations.put("(R) " +assoc.getName(), assoc.toString());
        }
        
        Aggregation o;
        Iterator<Aggregation> aggreg = this.model.getAggregationIterator();
        while(aggreg.hasNext()) {
            
            o = aggreg.next();
            this.relations.put(o.getSerializeName(),o.toString());
        }
    }

    public ArrayList<String> getClassesName(){
        Classe c = null;
        ArrayList<String> classes = new ArrayList<>();

        Iterator<Classe> it = model.getClasseIterator();
        while(it.hasNext()) {
            c = it.next();
            classes.add(c.getName());
        }
        return classes;
    }
    public void search(String name) {
        this.v = new VisiteurUCD(name);
        this.v.visit(this.model);
    }
    
    public ArrayList<String> getMethodsOfCurrentClass() {
        return this.v == null ? null : this.v.getMethods();
    }
    
    public ArrayList<String> getAttributesOfCurrentClass() {
        return this.v == null ? null : this.v.getAttributs();
    }
    public ArrayList<String> getSubClasses() {
        return this.v == null ? null : this.v.getSubClasses();
    }
    
    public Map<String,String> getRelations() {
        return this.relations;
    }
}
