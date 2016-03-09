
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
public class VisiteurMetriqueITC implements IVisiteur {

    private Model model;
    private Metrique metrique;
    private final Set<Parametre> parametresCounter;
    private final String name;
    
    public VisiteurMetriqueITC(String name) {
        this.name = name;
        this.parametresCounter = new TreeSet<>();
    }
    
    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        this.model = m;
        m.findClasse(this.name, false).accept(this);
        this.metrique = new Metrique("ITC", this.parametresCounter.size());
    }

    @Override
    public void visit(Classe c) {
        Iterator<Operation> it = c.getOperationsIterator();
        while(it.hasNext()){
            Iterator<Parametre> itParam = it.next().getParamIterator();
            while(itParam.hasNext()) {
                Parametre param = itParam.next();
                if(this.model.findClasse(param.getType(), false) != null) {
                    this.parametresCounter.add(param);
                }
            }
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
