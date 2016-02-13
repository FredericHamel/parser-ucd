import java.util.*;

public class Operation implements Comparable<Operation> {
    private String methodeName, typeReturn;
    private Set<Parametre> params;

    public Operation(String name, String type){
        this.methodeName = name;
        this.typeReturn = type;
        this.params = new TreeSet<>(); // chaque param unique
    }
    
    public void addParam(Parametre p) {
        this.params.add(p);
    }

    public void addParams(Set<Parametre> params) {
        this.params.addAll(params);
    }
    
    public String getMethodeName(){
        return this.methodeName;
    }

    public String getTypeReturn(){
        return this.typeReturn;
    }

    public Parametre findParametre(String name, String type, boolean create) {
        Parametre p = null;
        Iterator<Parametre> it = this.getParamIterator();
        while(it.hasNext()) {
            p = it.next();
            if(p.getName().equals(name))
                return p;
        }
        return create ? new Parametre(name, type) : null;
    }

    public Iterator<Parametre> getParamIterator() {
        return this.params.iterator();
    }
    
    @Override
    public int compareTo(Operation o) {
        return o.methodeName.compareTo(this.methodeName)
                + o.typeReturn.compareTo(this.typeReturn);
    }
    
    @Override
    public String toString() {
        return String.format("%s() : %s", methodeName, typeReturn);
    }
}

