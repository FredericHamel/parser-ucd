package metric;


import parserucd.Model;
import parserucd.Operation;
import parserucd.Attribut;
import parserucd.Classe;
import java.util.Iterator;
import parserucd.IVisiteur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class VisiteurMetriqueNOD implements IVisiteur{
    private final String name;
    private int cpt;
    private Metrique metrique;
    
    /**
     * Constructeur pour la classe VisiteurMetriqueNOD.
     * @param name le nom de la classe sur laquel la métrique est calculé,
     */
    public VisiteurMetriqueNOD(String name) {
        this.name = name;
        this.cpt = 0;
    }

    /**
     * Retourne la métrique calculé par ce visiteur.
     * @return this.metrique
     */
    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        // Recherche la classe dans le model.
        Classe c = m.findClasse(name, false);
        
        // Visite la classe.
        c.accept(this);
        
        // Initialise la métrique à la valeur calculé.
        this.metrique = new Metrique("NOD", cpt);
    }

    @Override
    public void visit(Classe c) {
        Iterator<Classe> it = c.getSubClasseIterator();
        
        // Compte le nombre de sous classe directe et indirecte.
        while(it.hasNext()) {
            cpt++;
            it.next().accept(this);
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
