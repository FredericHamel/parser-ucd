import java.util.*;

public class Classe implements Comparable<Classe> {
	
	private String name;
	private Set<Attribut> attributes;
	private Set<Operation> operations;
	
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

	@Override
	public int compareTo(Classe o) {
		return name.compareTo(o.getName());
	}
}
