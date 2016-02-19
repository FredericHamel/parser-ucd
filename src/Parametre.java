
public class Parametre implements Comparable<Parametre> {
    private String name, type;

    public Parametre(String name, String type){
        this.name=name;
        this.type=type;
    }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.type;
    }
    
    @Override
    public int compareTo(Parametre o) {
        return type.compareTo(o.type) + name.compareTo(o.type);
    }
    
    @Override
    public String toString(){
        return getType();
    }
}
