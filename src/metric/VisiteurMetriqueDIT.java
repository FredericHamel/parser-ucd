package metric;

import parserucd.Attribut;
import parserucd.Classe;
import parserucd.IVisiteur;
import parserucd.Operation;
import parserucd.Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class VisiteurMetriqueDIT implements IVisiteur {
    private final String name;
    private int cpt;
    private Metrique metrique;
    
    /**
     * Construteur du Visiteur pour DIT.
     * @param name 
     */
    public VisiteurMetriqueDIT(String name) {
        this.name = name;
        this.cpt = 0;
    }
    
    /**
     * Retourne le résultat du calcul de la métrique.
     * @return 
     */
    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        Classe c = m.findClasse(name, false);
        c.accept(this);
        
        // Génère la métrique calculé.
        this.metrique = new Metrique("DIT", cpt);
    }

    @Override
    public void visit(Classe c) {
        // Incrément le compteur s'il y a un parent. 
        if(c.hasParent()) {
        	cpt++;
            c.getParent().accept(this);
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
