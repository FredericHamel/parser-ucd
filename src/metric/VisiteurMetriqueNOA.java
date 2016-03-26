package metric;


import parserucd.Attribut;
import parserucd.Classe;
import parserucd.Model;
import parserucd.Operation;
import java.util.Iterator;

import java.util.Set;
import java.util.TreeSet;
import parserucd.IVisiteur;

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
    
    /**
     * Constructeur pour le visiteur de la métrique NOA.
     * @param name le nom de la classe a visité.
     */
    public VisiteurMetriqueNOA(String name) {
        this.name = name;
        this.attributCounter = new TreeSet<>();
    }

    /**
     * Retourne la métrique calculé.
     * @return this.metrique
     */
    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        this.model = m;
        
        // Visite le model m.
        m.findClasse(this.name, false).accept(this);
        
        // Initialise la métrique par la valeur calculé.
        this.metrique = new Metrique("NOA", this.attributCounter.size());
    }

    @Override
    public void visit(Classe c) {
        Iterator<Attribut> it = c.getAttributIterator();
        
        // Compte les attribut de la classe et de la classe parent
        //  en ignorant le masquage des attribut.
        while(it.hasNext()){
            this.attributCounter.add(it.next());
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
