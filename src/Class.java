import java.util.*;

public class Class {
	
	private String name;
	private Set<Attribute> attributes;
	private Set<Operation> operations;
	
	/**
	 * Constructeur
	 * @param name
	 * @param a
	 * @param o
	 */
	public Class(String name){
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
	
    public void addAttribute(Attribute a) {
        attributes.add(a);
    }

    public void addOperation(Operation o) {
        operations.add(o);
    }

	/**
	 * Getter arraylist of attributes
	 * @return attributes
	 */
	public Iterator<Attribute> getAttributeIterator(){
		return this.attributes.iterator();
	}
	
	/**
	 * Getter arraylist of operations
	 * @return operations
	 */
	public Iterator<Operation> getOperationsIterator(){
		return this.operations.iterator();
	}
}
