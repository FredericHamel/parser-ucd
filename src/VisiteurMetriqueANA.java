import java.util.ArrayList;
import java.util.Iterator;


public class VisiteurMetriqueANA implements IVisiteur{

    private String name;
    private Model m;
    private Classe c;
    private Metrique metrique;
    private Set<Operation> operationCounter;

    public VisiteurMetriqueANA(String classname){
        this.name = classname;
        this.metrique = null;
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
        this.c = this.m.findClasse(name, false);
        this.c.accept(this);
    }

    /**
     * Visite la classe c.
     * @param c, une classe.
     */
    public void visit(Classe c){
        int counterOp=0, counterParam =0;
        Iterator<Operation> oper = c.getOperationsIterator();
        while(oper.hasNext()){
            Iterator<Parametre> p = oper.next().getParamIterator();
            while(p.hasNext())
                counterParam++;
            counterOp++;
        } 
        double value = counterParam/counterOp;
        this.metrique = new Metrique("ANA", value);
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

    }

}
