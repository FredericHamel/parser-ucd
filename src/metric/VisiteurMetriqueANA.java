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

    private String name;
    private Model m;
    private Classe c;
    private Metrique metrique;
    private Set<Operation> operationCounter;
    private Set<Parametre> paramCounter;

    public VisiteurMetriqueANA(String classname){
        this.name = classname;
        this.operationCounter = new TreeSet<>();
        this.paramCounter = new TreeSet<>();
    }
    public Metrique getMetrique() {
        return this.metrique;
    }

    /**
     * Visite le model m.
     * @param m, le model.
     */
    public void visit(Model m){
        this.m = m;
        this.c = this.m.findClasse(this.name, false);
        this.c.accept(this);
        this.metrique = new Metrique("ANA", this.operationCounter.size() == 0 ? 0 : (double)(paramCounter.size())/(double)(operationCounter.size()));
    }

    /**
     * Visite la classe c.
     * @param c, une classe.
     */
    public void visit(Classe c){
        Iterator<Operation> oper = c.getOperationsIterator();
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
    public void visit(Attribut a){

    }

    /**
     * Visite l'operation o.
     * @param o, l'operation. 
     */
    public void visit(Operation o){
    	Iterator<Parametre> p = o.getParamIterator();
        while(p.hasNext()){
        	Parametre param = p.next();
            paramCounter.add(param);
        }
    }

}
