import java.util.*;

/**
 * La classe Model represente le model d'un fichier.
 * C'est la classe la plus haute au niveau hierarchique 
 * des objets du fichier.ucd.
 * @author Frederic Hamel et Sabrina Ouaret
 */
public class Model implements IVisitable {

    private String name;
    private Set<Classe> classes;
    private Set<Association> assoc;
    private Set<Aggregation> aggregation;
    
    private Classe lastClasse;
    
    /**
     * Constructeur Model
     * @param name, le nom du model.
     */
    public Model(String name){
        this.name = name;
        this.classes = new TreeSet<>();
        this.assoc = new TreeSet<>();
        this.aggregation = new TreeSet<>();
        this.lastClasse = null;
    }

    /**
     * Retourne le nom du model.
     * @return name, le nom du model.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Modifie le nom du model.
     * @param newName, le nouveau nom du model.
     */
    public void setName(String newName){
        this.name = newName;
    }

    /**
     * Ajoute une classe au TreeSet classes.
     * @param c, la classe a ajouter.
     * @return le TreeSet classes mis a jour.
     */
    public boolean addClasse(Classe c) {
        return this.classes.add(c);
    }

    /**
     * Ajoute une association au TreeSet assoc.
     * @param a, l'association a ajouter.
     * @return le TreeSet assoc mis a jour. 
     */
    public boolean addAssociation(Association a) {
        return this.assoc.add(a);
    }

    /**
     * Ajoute une relation de type aggregation au TreeSet aggregation.
     * @param a, l'aggregation a ajouter.
     * @return le TreeSet aggregation mis a jour.
     */
    public boolean addAggregation(Aggregation a) {
        return this.aggregation.add(a);
    }

    /**
     * Trouve une classe si elle existe dans le TreeSet classes
     * et la retourne. Si elle n'existe pas et que le parametre 
     * create est a TRUE, on la creer, sinon on retourne null.
     * @param name, le nom de la classe a trouver.
     * @param create, le boolean qui indique si on souhaite creer la classe dans le cas où elle n'existe pas dans le TreeSet.
     */
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

    /**
     * Retourne un iterateur de type Classe.
     * @return l'iterateur du set classes.
     */
    public Iterator<Classe> getClasseIterator() {
        return this.classes.iterator();
    }

    /**
     * Retourne un iterateur de type Association.
     * @return l'iterateur du set assoc.
     */
    public Iterator<Association> getAssociationIterator() {
        return this.assoc.iterator();
    }

    /**
     * Retourne un iterateur de type Aggregation.
     * @return l'iterateur du set aggregation.
     */
    public Iterator<Aggregation> getAggregationIterator() {
        return this.aggregation.iterator();
    }

    /**
     * Override de la methode toString.
     * Creer une string de type StringBuilder.
     * Nous permet de debugger la lecture du fichier .ucd.
     * @return string d'affichage.
     */
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
    
    /**
     * Accepte un visiteur de type IVisiteur.
     */
    @Override
    public void accept(IVisiteur v) {
        v.visit(this);
    }
}
