import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class VisiteurMetriqueNOM implements IVisiteur {
    private final String name;
    private Model model;
    private Metrique metrique;
    private final Set<Operation> operationCounter;
    
    public VisiteurMetriqueNOM(String name) {
        this.name = name;
        this.operationCounter = new TreeSet<>();
    }

    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        this.model = m;
        m.findClasse(this.name, false).accept(this);
        this.metrique = new Metrique("NOM", this.operationCounter.size());
    }

    @Override
    public void visit(Classe c) {
        Iterator<Operation> it = c.getOperationsIterator();
        while(it.hasNext()){
            this.operationCounter.add(it.next());
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
