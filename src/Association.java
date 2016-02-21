/**
 * Classe representant une relation d'association nommee entre
 * deux classes a et b. 
 * @author Frederic Hamel et Sabrina Ouaret
 */
public class Association implements Comparable<Association> {
    private String name;
    private final Role a;
    private final Role b;

    /**
     * Constructeur
     * @param name, le nom de l'association.
     * @param a, @param b les roles des classes.
     */
    public Association(String name, Role a, Role b) {
        this.a = a;
        this.b = b;
        this.name = name;
    }

    /**
     * @return name, le nom de l'association
     */
    public String getName() { 
        return name;
    }
    // class a
    public Role getLeft() { return a; }
    // class b
    public Role getRight() { return b; }
    
    /**
     * Override de toString, permettant le debuggage
     * du parsing du fichier seulement.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RELATION ").append(name).append("\n\tROLES\n\t\t")
            .append(getLeft().toString()).append("\n\t\t").append(getRight())
            .append("\n");
        return sb.toString();
    }
    
    /**
     * Redefinition de compareTo selon les roles de l'association
     */
      @Override
    public int compareTo(Association o) {
        return o.a.compareTo(a) + o.b.compareTo(b);
    }
}
