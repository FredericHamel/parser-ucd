
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frederic
 */
public class VisiteurMetriqueETC implements IVisiteur {
    private Model model;
    private final String name;
    private Metrique metrique;
    private final Set<Parametre> parametresCounter;
    
    
    public VisiteurMetriqueETC(String name) {
        this.name = name;
        this.parametresCounter = new TreeSet<>();
    }

    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        this.model = m;
        Iterator<Classe> it = m.getClasseIterator();
        while(it.hasNext()) {
            Classe c = it.next();
            if(!c.getName().equals(this.name))
                c.accept(this);
        }
        this.metrique = new Metrique("ETC", parametresCounter.size());
    }

    @Override
    public void visit(Classe c) {
        Iterator<Operation> it = c.getOperationsIterator();
        while(it.hasNext()) {
            it.next().accept(this);
        }
    }

    @Override
    public void visit(Attribut a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Operation o) {
        Iterator<Parametre> it = o.getParamIterator();
        while(it.hasNext()) {
            Parametre p = it.next();
            if(p.getType().equals(this.name)) {
                this.parametresCounter.add(p);
            }
        }
    }
    
    
}
