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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("GENERALISATION %s\n", name));
        sb.append("\tSUBCLASSES\n");
        Iterator<Classe> iter = getSubClasseIterator();
        while(iter.hasNext()) {
            sb.append(String.format("\t\t%s\n", iter.next().getName()));
        }
        return sb.toString();
    }
}
