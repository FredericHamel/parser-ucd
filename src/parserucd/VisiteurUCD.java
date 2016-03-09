package parserucd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe VisiteurUCD implemente IVisiteur.
 * On definit ici les facons de parcourir chaque element
 * pour pouvoir detenir par la suite des arraylist d'objets.
 * @author Frederic Hamel et Sabrina Ouaret
 */
public class VisiteurUCD implements IVisiteur {

    private String name;
    private Model m;
    private Classe c;
    private ArrayList<String> attributs, methods, subClasse;
    
    
    /**
     * Constructeur
     * @param classname, le nom de la classe a parcourir.
     */
    public VisiteurUCD(String classname){
        this.name=classname;
        this.attributs = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.subClasse = new ArrayList<>();
    }
    
    /**
     * Override la methode qui permet la visite du model m.
     * On trouve le model associe a la classe et on appelle
     * la methode accept(visiteur) sur cette classe.
     * @param m, le model.
     */
    @Override
    public void visit(Model m) {
        this.m = m;
        this.c = this.m.findClasse(name, false);
        this.c.accept(this);
    }

    /**
     * Override la methode visit(classe).
     * On appelle la methode accept(visiteur) sur les objets
     * attribut, operation et sous-classe de la classe c.
     * @param c, la classe a visiter.
     */
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
            subClasse.add(String.format("%s", sub.getName()));
        }            
    }

    /**
     * Override la methode visit(attribut).
     * On ajoute l'attribut visite dans l'arraylist des attributs du visiteur.
     */
    @Override
    public void visit(Attribut a) {
        this.attributs.add(a.toString());
    }

    /**
     * Override la methode visit(operation).
     * On ajoute l'operation visite dans l'arraylist des operations du visiteur.
     */
    @Override
    public void visit(Operation o) {
        this.methods.add(o.toString());
    }

    /**
     * Retourne l'arraylist contenant toutes les operations visitees.
     * @return un arraylist d'operations visitees.
     */
    public ArrayList<String> getMethods() {
        return this.methods;
    }

    /**
     * Retourne l'arraylist contenant tous les attributs visites.
     * @return un arraylist d'attributs visites.
     */
    public ArrayList<String> getAttributs() {
        return this.attributs;
    }
    
    /**
     * Retourne l'arraylist contenant toutes les sous-classes visitees.
     * @return un arraylist des sous-classes visitees.
     */
    public ArrayList<String> getSubClasses() {
        return this.subClasse;
    }
}
