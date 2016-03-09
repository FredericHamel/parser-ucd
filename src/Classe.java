import java.util.*;

/**
 * 
 * @author Frederic Hamel et Sabrina Ouaret
 */

public class Classe implements Comparable<Classe>, IVisitable {

    private final String name;
    private Classe parent;
    private final Set<Attribut> attributes;
    private final Set<Operation> operations;
    private final Set<Classe> subclasses;

    /**
     * Constructeur
     * @param name, le nom de la classe.
     */
    public Classe(String name){
        this.name = name;
        this.attributes = new TreeSet<>();
        this.operations = new TreeSet<>();
        this.subclasses = new TreeSet<>();
        this.parent = null;
    }

    /**
     * Getter name
     * @return name, le nom de la classe.
     */
    public String getName(){
        return this.name;
    }

    public Classe getParent() {
        return this.parent;
    }
    
    
    public boolean hasParent() {
        return this.parent != null;
    }
    
    private void setParent(Classe c) {
        this.parent = c; // Ajoute le parent;
    }

    /**
     * Ajout d'un attribut dans le TreeSet attributes.
     * @param a, l'attribut a ajouter.
     */
    public void addAttribut(Attribut a) {
        attributes.add(a);
    }

    /**
     * Ajout d'une operation dans le TreeSet operations.
     * @param o, l'operation a ajouter.
     */
    public void addOperation(Operation o) {
        operations.add(o);
    }

    /**
     * Ajout d'une sous-classe dans le TreeSet subclasses.
     * @param c, la sous-classe a ajouter.
     */
    public void addSubclass(Classe c) {
        c.setParent(this);
        this.subclasses.add(c);
    }
    
    /**
     * Retourne l'iterateur de type Attribut
     * @return attributes.iterator.
     */
    public Iterator<Attribut> getAttributIterator(){
        return this.attributes.iterator();
    }

    /**
     * Retourne l'iterateur de type Operation
     * @return operations.iterator.
     */
    public Iterator<Operation> getOperationsIterator(){
        return this.operations.iterator();
    }

    /**
     * Retourne l'iterateur de type SubClasse
     * @return subclasses.iterator.
     */
    public Iterator<Classe> getSubClasseIterator() {
        return this.subclasses.iterator();
    }
    
    /**
     * Retourne l'iterateur de type Attribut
     * @return attributes.iterator.
     */
   /* public Set<Operation> findOperationByName(String name) {
        return null;
    }*/

    /**
     * Cherche une operation d'un type particulier et la retourne.
     * @param name, le nom de l'operation a trouver.
     * @param type, le type de l'operation a trouver.
     * @return op, l'operation.
     */
    public Operation findOperationByTypeName(String name, String type) {
        Operation op = null;
        Iterator<Operation> it = this.getOperationsIterator();
        while(it.hasNext()) {
            op = it.next();
            if(op.getMethodeName().equals(name) && op.getTypeReturn().equals(type)) {
                return op;
            }
        }
        return op;
    }

    /**
     * Override de la methode compareTo de deux classes.
     * Comparaison selon leur nom.
     * @return un entier representant le resultat de la comparaison.
     */
    @Override
    public int compareTo(Classe o) {
        return name.compareTo(o.getName());
    }

    /**
     * Override de la fonction d'affichage toString.
     * Cet affichage nous permet de debugger lors de la lecture 
     * des elements du fichier par le parser.
     * @return sb.toString
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CLASS %s\n", name));
        sb.append("\tATTRIBUTES\n");
        Iterator<Attribut> iterAttr = getAttributIterator();
        while(iterAttr.hasNext()) {
            sb.append(String.format("\t\t%s\n", iterAttr.next()));
        }
        sb.append("\tOPERATIONS\n");
        Iterator<Operation> iterOp = getOperationsIterator();
        while(iterOp.hasNext()) {
            sb.append(String.format("\t\t%s\n", iterOp.next()));
        }
        return sb.toString();
    }
    
    /**
     * Accepte le visiteur v de type IVisiteur.
     * Permet de parcourir tous les objets de cette classe.
     */
    @Override
    public void accept(IVisiteur v) {
        v.visit(this);
    }
}
