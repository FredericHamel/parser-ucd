import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frederic
 */
public class VisiteurUCD implements IVisiteur {

    private String name;
    
    private Model m;
    private Classe c;
    private ArrayList<String> attributs, methods, subClasse;
    
    
    public VisiteurUCD(String classname){
        this.name=classname;
        this.attributs = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.subClasse = new ArrayList<>();
    }
    
    @Override
    public void visit(Model m) {
        this.m = m;
        this.c = this.m.findClasse(name, false);
        this.c.accept(this);
    }

    @Override
    public void visit(Classe c) {
        Iterator<Attribut> attr = c.getAttributIterator();
        while(attr.hasNext())
            attr.next().accept(this);
        Iterator<Operation> oper = c.getOperationsIterator();
        while(oper.hasNext())
            oper.next().accept(this);
        Classe sub;
        Iterator<Classe> it = c.getSubClasseIterator();
        while(it.hasNext()){
            sub = it.next();
            subClasse.add(String.format("CLASS %s", sub.getName()));
        }            
    }

    @Override
    public void visit(Attribut a) {
        this.attributs.add(a.toString());
    }

    @Override
    public void visit(Operation o) {
        this.methods.add(o.toString());
    }

    public ArrayList<String> getMethods() {
        return this.methods;
    }

    public ArrayList<String> getAttributs() {
        return this.attributs;
    }
    public ArrayList<String> getSubClasses() {
        return this.subClasse;
    }
}
