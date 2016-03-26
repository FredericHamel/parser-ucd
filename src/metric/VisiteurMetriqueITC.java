package metric;


import parserucd.Classe;
import parserucd.Attribut;
import parserucd.Model;
import parserucd.Operation;
import parserucd.Parametre;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import parserucd.IVisiteur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class VisiteurMetriqueITC implements IVisiteur {

    private Model model;
    private Metrique metrique;
    private final Set<Parametre> parametresCounter;
    private final String name;
    
    /**
     * Constructeur paramétré pour le VisiteurMetriqueITC.
     * @param name le nom de la classe ciblé,
     */
    public VisiteurMetriqueITC(String name) {
        this.name = name;
        this.parametresCounter = new TreeSet<>();
    }
    
    /**
     * Retourne la métrique ITC.
     * @return this.metrique
     */
    public Metrique getMetrique() {
        return this.metrique;
    }
    
    @Override
    public void visit(Model m) {
        this.model = m;
        
        // Visite la classe courante.
        m.findClasse(this.name, false).accept(this);
        
        // Initilise la métrique à la valeur calculé.
        this.metrique = new Metrique("ITC", this.parametresCounter.size());
    }

    @Override
    public void visit(Classe c) {
        Iterator<Operation> it = c.getOperationsIterator();
        
        // Parcours tous les opérateurs de la classe c.
        while(it.hasNext()){
            Iterator<Parametre> itParam = it.next().getParamIterator();
            
            // Parcours tous les paramètre de l'opération. 
            while(itParam.hasNext()) {
                Parametre param = itParam.next();
                // Compte le paramètre si le type fait partie du model.
                if(this.model.findClasse(param.getType(), false) != null) {
                    this.parametresCounter.add(param);
                }
            }
            
            //  Parcours le parent.
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
