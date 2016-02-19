import java.util.*;

public class Classe implements Comparable<Classe>, IVisitable {

    private String name;
    private Set<Attribut> attributes;
    private Set<Operation> operations;
    private Set<Classe> subclasses;

    /**
     * Constructeur
     * @param name
     * @param a
     * @param o
     */
    public Classe(String name){
        this.name = name;
        this.attributes = new TreeSet<>();
        this.operations = new TreeSet<>();
        this.subclasses = new TreeSet<>();
    }

    /**
     * Getter name
     * @return name
     */
    public String getName(){
        return this.name;
    }

    public void addAttribut(Attribut a) {
        attributes.add(a);
    }

    public void addOperation(Operation o) {
        operations.add(o);
    }

    public void addSubclass(Classe c) {
        this.subclasses.add(c);
    }
    
    /**
     * Getter arraylist of attributes
     * @return attributes
     */
    public Iterator<Attribut> getAttributIterator(){
        return this.attributes.iterator();
    }

    /**
     * Getter arraylist of operations
     * @return operations
     */
    public Iterator<Operation> getOperationsIterator(){
        return this.operations.iterator();
    }

    public Iterator<Classe> getSubClasseIterator() {
        return this.subclasses.iterator();
    }
    
    public Set<Operation> findOperationByName(String name) {
        return null;
    }

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

    @Override
    public int compareTo(Classe o) {
        return name.compareTo(o.getName());
    }

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
    
    @Override
    public void accept(IVisiteur v) {
        v.visit(this);
    }
}
