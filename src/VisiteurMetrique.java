
public class VisiteurMetrique implements IVisiteur{
	
	private String name;
    private Model m;
    private Classe c;
    private ArrayList<Metrique> metriques;
    
   public VisiteurMetrique(String classname){
	   this.name = classname;
	   this.metriques = new ArrayList<>();
   }
    /**
	 * Visite le model m.
	 * @param m, le model.
	 */
    void visit(Model m){
    	this.m = m;
        this.c = this.m.findClasse(name, false);
        this.c.accept(this);
    }
    
    /**
     * Visite la classe c.
     * @param c, une classe.
     */
    void visit(Classe c){
    	int counterOp=0, counterParam =0;
    	Iterator<Operation> oper = c.getOperationsIterator();
        while(oper.hasNext()){
        	Iterator<Parametre> p = oper.next().getParamIterator();
            while(p.hasNext())
            	counterParam++;
            counterOp++;
        }
        double value = counterParam/counterOp;
        Metrique m = new Metrique("ANA", value);
    	metriques.add(m);
    }
    
    /**
     * Visite l'attribut a.
     * @param a, un attribut.
     */
    void visit(Attribut a){
    	
    }
    
    /**
     * Visite l'operation o.
     * @param o, l'operation. 
     */
    void visit(Operation o){
    	
    }

}
