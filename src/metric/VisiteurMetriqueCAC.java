package metric;


import parserucd.Classe;
import parserucd.Aggregation;
import parserucd.Operation;
import parserucd.Attribut;
import parserucd.Model;
import parserucd.Association;
import parserucd.Role;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import parserucd.IVisiteur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class VisiteurMetriqueCAC implements IVisiteur {

    private Model model;
    private Metrique metrique;
    private final String name;
    private final Set<Association> associationCounter;
    private final Set<Aggregation> aggregationCounter;

    /**
     * Constructeur du calculateur de la métrique CAC.
     * @param name 
     */
    public VisiteurMetriqueCAC(String name) {
        this.name = name;
        this.aggregationCounter = new TreeSet<>();
        this.associationCounter = new TreeSet<>();
    }
    
    /**
     * La métrique calculer par ce visiteur.
     * @return this.metrique 
     */
    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        this.model = m;
        Classe courant = m.findClasse(name, false);
        courant.accept(this);
        this.metrique = new Metrique("CAC", this.aggregationCounter.size()
            +this.associationCounter.size());
    }

    @Override
    public void visit(Classe c) {
        String classname = c.getName();
        Iterator<Association> itAssoc = this.model.getAssociationIterator();
        
        // Compte toutes les associations de la Classe c.
        while(itAssoc.hasNext()) {
            Association assoc = itAssoc.next();
            if(classname.equals(assoc.getLeft().getClasse().getName()) 
                    || classname.equals(assoc.getRight().getClasse().getName())) {
                this.associationCounter.add(assoc);
            }
        }
        
        // Visite le parent.
        if(c.hasParent())
            c.getParent().accept(this);
        
        Iterator<Aggregation> itAggreg = this.model.getAggregationIterator();
        
        // Compte toutes les aggrégations de la classe courante.
        //  en ignorant les duplication.
        while(itAggreg.hasNext()) {
            Aggregation aggreg = itAggreg.next();
            if(aggreg.getContainer().getClasse().getName().equals(classname))
                aggregationCounter.add(aggreg);
            else {
                Iterator<Role> it = aggreg.iterator();
                while(it.hasNext()) {
                    Role role = it.next();
                    if(role.getClasse().getName().equals(classname)){
                        aggregationCounter.add(aggreg);
                        break;
                    }
                        
                }
            }
        }
    }

    @Override
    public void visit(Attribut a) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void visit(Operation o) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    
}
