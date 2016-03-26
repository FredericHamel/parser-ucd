package metric;


import parserucd.Model;
import parserucd.Attribut;
import parserucd.Parametre;
import parserucd.Operation;
import parserucd.Classe;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import parserucd.IVisiteur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class VisiteurMetriqueETC implements IVisiteur {
    private Model model;
    private final String name;
    private Metrique metrique;
    private final Set<Parametre> parametresCounter;
    
    /**
     * Constructeur du visiteur ETC.
     * @param name 
     */
    public VisiteurMetriqueETC(String name) {
        this.name = name;
        this.parametresCounter = new TreeSet<>();
    }

    /**
     * Donne la métrique calculé.
     * @return this.metrique
     */
    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        this.model = m;
        Iterator<Classe> it = m.getClasseIterator();
        
        // Visite les autres classes.
        while(it.hasNext()) {
            Classe c = it.next();
            if(!c.getName().equals(this.name))
                c.accept(this);
        }
        
        // Initialise la métrique par la valeur calculé.
        this.metrique = new Metrique("ETC", parametresCounter.size());
    }

    @Override
    public void visit(Classe c) {
        Iterator<Operation> it = c.getOperationsIterator();
        // Visite les opérations de la classe.
        while(it.hasNext()) {
            it.next().accept(this);
        }
    }

    @Override
    public void visit(Attribut a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Operation o) {
        Iterator<Parametre> it = o.getParamIterator();
        // Incremente le nombre de fois que le paramètres
        //  est du type de la classe ciblé.
        while(it.hasNext()) {
            Parametre p = it.next();
            if(p.getType().equals(this.name)) {
                this.parametresCounter.add(p);
            }
        }
    }
    
    
}
