
// a R b 
public abstract class Relation implements Comparable<Relation> {
    private final char type;
    
    private final Role a;
    private final Role b;

    protected Relation(char t, Role a, Role b) {
        this.type = t;
        this.a = a;
        this.b = b;
    }

    public char getRelationSymbol() {
        return type;
    }

    // class a
    public Role getLeft() { return a; }
    // class b
    public Role getRight() { return b; }
    
    @Override
    public int compareTo(Relation o) {
        return o.a.compareTo(a) + o.b.compareTo(b) + o.type - type;
    }
}