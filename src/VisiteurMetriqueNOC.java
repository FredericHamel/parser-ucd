
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frederic
 */
public class VisiteurMetriqueNOC implements IVisiteur {

    private final String name;
    private int cpt;
    private Metrique metrique;
    
    public VisiteurMetriqueNOC(String name) {
        this.name = name;
        this.cpt = 0;
    }

    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        Classe c = m.findClasse(name, false);
        c.accept(this);
        this.metrique = new Metrique("NOC", cpt);
    }

    @Override
    public void visit(Classe c) {
        Iterator<Classe> it = c.getSubClasseIterator();
        while(it.hasNext()) {
            cpt++;
            it.next();
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
