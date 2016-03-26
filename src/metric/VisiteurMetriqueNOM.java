package metric;

import parserucd.Attribut;
import parserucd.Classe;
import parserucd.Model;
import parserucd.Operation;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import parserucd.IVisiteur;

public class VisiteurMetriqueNOM implements IVisiteur {
    private final String name;
    private Model model;
    private Metrique metrique;
    private final Set<Operation> operationCounter;
    
    /**
     * Constructeur pour la classe VisiteurMetriqueNOM.
     * @param name le nom de la classe ciblé,
     */
    public VisiteurMetriqueNOM(String name) {
        this.name = name;
        this.operationCounter = new TreeSet<>();
    }

    /**
     * Retourne le résultat du calcule de la métrique.
     * @return this.metrique
     */
    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        this.model = m;
        
        // Visite le mpdel.
        m.findClasse(this.name, false).accept(this);
        
        // Initialise la métrique à la valeur calculé.
        this.metrique = new Metrique("NOM", this.operationCounter.size());
    }

    @Override
    public void visit(Classe c) {
        Iterator<Operation> it = c.getOperationsIterator();
        
        // Calcul les opérateur directe ou hérité en ignorant le masquage.
        while(it.hasNext()){
            this.operationCounter.add(it.next());
            if(c.hasParent())
                c.getParent().accept(this);
        }
    }

    @Override
    public void visit(Attribut a) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void visit(Operation o) {
        throw new UnsupportedOperationException("Not supported");
    }
}
