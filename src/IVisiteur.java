
/**
 * IVisiteur la classe du visiteur d'objet.
 * @author Frederic Hamel et Sabrina Ouaret
 */
public interface IVisiteur {
	
	/**
	 * Visite le model m.
	 * @param m, le model.
	 */
    void visit(Model m);
    
    /**
     * Visite la classe c.
     * @param c, une classe.
     */
    void visit(Classe c);
    
    /**
     * Visite l'attribut a.
     * @param a, un attribut.
     */
    void visit(Attribut a);
    
    /**
     * Visite l'operation o.
     * @param o, l'operation. 
     */
    void visit(Operation o);
}
