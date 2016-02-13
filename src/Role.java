
public class Role implements Comparable<Role> {
    private final Classe c;
    private final Multiplicite m;
    
    public Role(Classe c, Multiplicite m){
        this.c = c;
        this.m = m;
    }
    
    public Classe getClasse() {
        return c;
    }
    
    public Multiplicite getMultiplicity() {
        return this.m;
    }
    
    @Override
    public int compareTo(Role o) {
        return o.c.compareTo(c) + o.m.compareTo(m);
    }
    
    @Override
    public String toString() {
        return String.format("CLASS %s %s", this.c.getName(), this.m.toString());
    }
}
