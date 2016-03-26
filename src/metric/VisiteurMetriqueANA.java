package metric;

import parserucd.Classe;
import parserucd.Attribut;
import parserucd.Parametre;
import parserucd.Model;
import parserucd.Operation;
import java.util.Set;
import java.util.Iterator;
import parserucd.IVisiteur;
import java.util.TreeSet;



public class VisiteurMetriqueANA implements IVisiteur {

    private final String name;
    private Model m;
    private Classe c;
    private Metrique metrique;
    private final Set<Operation> operationCounter;
    private final Set<Parametre> paramCounter;

    /**
     * 
     * @param classname le nom de classe.
     */
    public VisiteurMetriqueANA(String classname){
        this.name = classname;
        this.operationCounter = new TreeSet<>();
        this.paramCounter = new TreeSet<>();
    }
    
    /**
     * La métrique calculer par ce visiteur.
     * @return this.metrique 
     */
    public Metrique getMetrique() {
        return this.metrique;
    }

    /**
     * Visite le model m.
     * @param m le model.
     */
    @Override
    public void visit(Model m){
        this.m = m;
        
        // Recherche la classe à cible.
        this.c = this.m.findClasse(this.name, false);
        
        // Demande à d'accepter le visiteur.
        this.c.accept(this);
        
        // Création de la metrique.
        this.metrique = new Metrique("ANA",
                this.operationCounter.isEmpty()? 0:
                        (double)(paramCounter.size())/
                                (double)(operationCounter.size()));
    }

    /**
     * Visite la classe c.
     * @param c, une classe.
     */
    @Override
    public void visit(Classe c){
        Iterator<Operation> oper = c.getOperationsIterator();
        // Itération sur les opérations de la Classe c.
        while(oper.hasNext()){
        	Operation o = oper.next();
        	operationCounter.add(o);
        	o.accept(this);
        } 
    }

    /**
     * Visite l'attribut a.
     * @param a, un attribut.
     */
    @Override
    public void visit(Attribut a){
       // Opération vide pour la métrique.
    }

    /**
     * Visite l'operation o.
     * @param o, l'operation. 
     */
    @Override
    public void visit(Operation o){
    	Iterator<Parametre> p = o.getParamIterator();
        // Compte les parametre distinct.
        while(p.hasNext()){
        	Parametre param = p.next();
            paramCounter.add(param);
        }
    }
}
