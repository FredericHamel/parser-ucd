/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frederic
 */
public class Association implements Comparable<Association> {
    private String name;
    private final Role a;
    private final Role b;

    public Association(String name, Role a, Role b) {
        this.a = a;
        this.b = b;
        this.name = name;
    }

    public String getName() { 
        return name;
    }
    // class a
    public Role getLeft() { return a; }
    // class b
    public Role getRight() { return b; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RELATION ").append(name).append("\n\tROLES\n\t\t")
            .append(getLeft().toString()).append("\n\t\t").append(getRight())
            .append("\n");
        return sb.toString();
    }
      @Override
    public int compareTo(Association o) {
        return o.a.compareTo(a) + o.b.compareTo(b);
    }
}
