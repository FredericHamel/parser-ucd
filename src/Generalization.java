import java.util.*;

public class Generalization implements Comparable<Generalization> {
	
    private final String name;
    private final Set<Classe> subClasses;

    public Generalization(String name){
            this.name = name;
            this.subClasses = new TreeSet<>();
    }

    public boolean addSubClasses(Classe subClass){
            return this.subClasses.add(subClass);
    }

    public String getName(){
            return this.name;
    }

    public Iterator<Classe> getSubClasseIterator(){
    return this.subClasses.iterator();
    }

    @Override  
    public int compareTo(Generalization o) {
        return name.compareTo(o.getName());
    }
}