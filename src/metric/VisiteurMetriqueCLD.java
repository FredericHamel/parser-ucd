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

/**
 *
 * @author frederic
 */
public class VisiteurMetriqueCLD implements IVisiteur {
    private int cpt, max;
    private final String name;
    private Metrique metrique;
    
    public VisiteurMetriqueCLD(String name) {
        this.name = name;
        this.cpt = 0;
        this.max = 0;
    }
    
    public Metrique getMetrique() {
        return this.metrique;
    }

    @Override
    public void visit(Model m) {
        Classe c = m.findClasse(this.name, false);
        c.accept(this);
        this.metrique = new Metrique("CLD", max);
    }

    @Override
    public void visit(Classe c) {
        Iterator<Classe> it = c.getSubClasseIterator();
        if(!it.hasNext()) {
            if(cpt > max)
                max = cpt;
        } else {
            cpt++;
            while(it.hasNext()) {
                Classe subclasse = it.next();
                subclasse.accept(this);
            }
            cpt--;
        }
    }

    @Override
    public void visit(Attribut a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Operation o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
