package metric;


import parserucd.Attribut;
import parserucd.Operation;
import parserucd.Model;
import parserucd.Classe;
import java.util.Iterator;
import parserucd.IVisiteur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class VisiteurMetriqueNOC implements IVisiteur {

    private final String name;
    private int cpt;
    private Metrique metrique;
    
    /**
     * Le constructeur de la classe Visiteur pour la métrique NOC.
     * @param name 
     */
    public VisiteurMetriqueNOC(String name) {
        this.name = name;
        this.cpt = 0;
    }

    /**
     * Retourne la métrique qui à été calculé.
     * @return this.metrique
     */
    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        // Recherche la classe portant le nom voulu dans le model m.
        Classe c = m.findClasse(name, false);
        
        // Visite le model m.
        c.accept(this);
        
        // Initialise la métrique NOC avec la valeur calculé.
        this.metrique = new Metrique("NOC", cpt);
    }

    @Override
    public void visit(Classe c) {
        Iterator<Classe> it = c.getSubClasseIterator();
        
        // Compte le nombre de sous classe directe.
        while(it.hasNext()) {
            cpt++;
            it.next();
        }
    }

    @Override
    public void visit(Attribut a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Operation o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
