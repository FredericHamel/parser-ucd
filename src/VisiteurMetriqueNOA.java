
import java.util.Iterator;

import java.util.Set;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class VisiteurMetriqueNOA implements IVisiteur {
    private final String name;
    private Model model;
    private Metrique metrique;
    private final Set<Attribut> attributCounter;
    
    public VisiteurMetriqueNOA(String name) {
        this.name = name;
        this.attributCounter = new TreeSet<>();
    }

    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        this.model = m;
        m.findClasse(this.name, false).accept(this);
        this.metrique = new Metrique("NOA", this.attributCounter.size());
    }

    @Override
    public void visit(Classe c) {
        Iterator<Attribut> it = c.getAttributIterator();
        while(it.hasNext()){
            this.attributCounter.add(it.next());
            if(c.hasParent())
                c.getParent().accept(this);
        }
       
    }

    @Override
    public void visit(Attribut a) {
        throw new UnsupportedOperationException("Not supported"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Operation o) {
        throw new UnsupportedOperationException("Not supported"); //To change body of generated methods, choose Tools | Templates.
    }
    
}
