/**
 * La classe Role permet de definir une classe et sa multiplicite
 * dans le contexte d'une relation d'association de cette 
 * classe avec une autre classe.
 * @author Frederic Hamel et Sabrina Ouaret
 */
public class Role implements Comparable<Role> {
    private final Classe c;
    private final Multiplicite m;
    
    /**
     * Constructeur
     * @param c, la classe
     * @param m, la multiplicite
     */
    public Role(Classe c, Multiplicite m){
        this.c = c;
        this.m = m;
    }
    
    /**
     * Retourne la classe.
     * @return c, la classe.
     */
    public Classe getClasse() {
        return c;
    }
    
    /**
     * Retourne la multiplicite.
     * @return m, la multiplicite.
     */
    public Multiplicite getMultiplicity() {
        return this.m;
    }
    
    /**
     * Override la methode compareTo de l'interface Comparable.
     * On compare deux roles entre eux par la classe et la multiplicite.
     * @param o, le role.
     * @return un entier indiquant le resultat de la comparaison.
     */
    @Override
    public int compareTo(Role o) {
        return o.c.compareTo(c) + o.m.compareTo(m);
    }
    
    /**
     * Override la methode toString.
     * Permet de debugger lors de l'affichage du parcours
     * de parser.
     * @return string
     */
    @Override
    public String toString() {
        return String.format("CLASS %s %s", this.c.getName(), this.m.toString());
    }
}
