// a R b 
public abstract class Relation {
    private final char type;
    private Role a;
    private Role b;

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
}