package metric;


import parserucd.Model;
import parserucd.Attribut;
import parserucd.Operation;
import parserucd.Classe;
import java.util.Iterator;
import parserucd.IVisiteur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class VisiteurMetriqueCLD implements IVisiteur {
    private int cpt, max;
    private final String name;
    private Metrique metrique;
    
    /**
     * Contructeur pour le Visiteur.
     * @param name le nom de la classe ciblé.
     */
    public VisiteurMetriqueCLD(String name) {
        this.name = name;
        this.cpt = 0;
        this.max = 0;
    }
    
    /**
     * Donne la métrique calculé.
     * @return this.metrique.
     */
    public Metrique getMetrique() {
        return this.metrique;
    }

    @Override
    public void visit(Model m) {
        // Recherche la classe courrante.
        Classe c = m.findClasse(this.name, false);
        
        // Démarre le calcule de la métrique.
        c.accept(this);
        this.metrique = new Metrique("CLD", max);
    }

    @Override
    public void visit(Classe c) {
        // Parcourt les les sous classes.
        Iterator<Classe> it = c.getSubClasseIterator();
        if(!it.hasNext()) {
            // Ccncerver le chemin maximal.
            if(cpt > max)
                max = cpt;
        } else {
            cpt++;
            // Descendre dans la hiérarchie des classes de 1 niveau.
            while(it.hasNext()) {
                Classe subclasse = it.next();
                subclasse.accept(this);
            }
            cpt--;
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
