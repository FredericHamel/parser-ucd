import java.io.IOException;
import java.util.*;

/**
 * 
 * @author Frederic Hamel et Sabrina Ouaret
 * La classe Admin permet de faire le lien entre la base de donnees qui
 * compose un fichier.ucd( un model ) et l'interface graphique.
 * Cette classe est un singleton.
 *
 */
public class Admin {
	
    private Model model;
    private static Admin instance;
    private final ParserUCD parser;
    private Map<String, String> relations;
    
    private VisiteurUCD v;
    
    private static final Object lock = new Object();

    /**
     * Constructeur
     */
    public Admin(){
        this.model = null;
        this.parser = ParserUCD.getInstance();
        this.relations = new HashMap<>();
        this.v = null;
    }

    /**
     * getInstance retourne l'instance ou la creer si elle n'existe pas deja.
     * Singleton.
     */
    public static Admin getInstance(){
        if(instance == null){
            synchronized(lock) {
                if(instance == null)
                    instance = new Admin();
            }
        }
        return instance;
    }

    /**
     * Parser le contenu du fichier, appele model.
     * @param filename, le nom du fichier ucd a parser.
     */
    public void parseModel(String filename) throws IOException {

    	/**
    	 * Appel a la fonction parse de la classe ParseUCD
    	 * On passe en parametre le nom du fichier et on retourne
    	 * le model parse.
    	 */
        this.model = parser.parse(filename);
        
        Association assoc;
        //Iterateur des relation d'associations du model
        Iterator<Association> assocIt = this.model.getAssociationIterator();
        while(assocIt.hasNext()) {
            assoc = assocIt.next();
            this.relations.put("(R) " +assoc.getName(), assoc.toString());
        }
        
        Aggregation o;
        //Iterator des relation d'aggregations du model
        Iterator<Aggregation> aggreg = this.model.getAggregationIterator();
        while(aggreg.hasNext()) {
            o = aggreg.next();
            this.relations.put(o.getSerializeName(),o.toString());
        }
    }

    /**
     * Retourne les noms des classes du model
     * @return classes de type ArrayList<String>
     */
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
    
    /**
     * Search cree un visiteur v;
     * v visite le model.
     * @param name, le nom de la classe que l'on selectionne dans le GUI
     */
    public void search(String name) {
        this.v = new VisiteurUCD(name);
        this.v.visit(this.model);
    }
    
    /**
     * @return liste des methodes de la classe courante selectionnee
     */
    public ArrayList<String> getMethodsOfCurrentClass() {
        return this.v == null ? null : this.v.getMethods();
    }
    
    /**
     * @return liste d'attributs de la classe courante selectionnee
     */
    public ArrayList<String> getAttributesOfCurrentClass() {
        return this.v == null ? null : this.v.getAttributs();
    }
    
    /**
     * @return liste des sous-classes de la classe courante selectionnee
     */
    public ArrayList<String> getSubClasses() {
        return this.v == null ? null : this.v.getSubClasses();
    }
    
    /**
     * @return un map<nom relation, details> de la classe courante selectionnee
     */
    public Map<String,String> getRelations() {
        return this.relations;
    }
}
