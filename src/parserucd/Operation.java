package parserucd;

import java.util.*;

/**
 * Classe Operation represente un objet operation d'une classe.
 * Implemente un Comparable et IVisitable.
 * @author Frederic Hamel et Sabrina Ouaret
 */
public class Operation implements Comparable<Operation>, IVisitable {
    private String methodeName, typeReturn;
    private Set<Parametre> params;

    /**
     * Constructeur
     * @param name, le nom de l'operation.
     * @param type, le type de retour de l'operation.
     */
    public Operation(String name, String type){
        this.methodeName = name;
        this.typeReturn = type;
        this.params = new TreeSet<>(); 
    }
    
    /**
     * Ajoute un parametre au set params.
     * @param p, le parametre de l'operation a ajouter.
     */
    public void addParam(Parametre p) {
        this.params.add(p);
    }

    /**
     * Ajoute un set de parametres a ajouter au set params.
     * @param params, un set de type Parametre.
     */
    public void addParams(Set<Parametre> params) {
        this.params.addAll(params);
    }
    
    /**
     * Retourne un nom d'operation.
     * @return methodeName, le nom de l'operation.
     */
    public String getMethodeName(){
        return this.methodeName;
    }

    /**
     * Retourne le type de retour de l'operation.
     * @return typeReturn, le type de retour de l'operation.
     */
    public String getTypeReturn(){
        return this.typeReturn;
    }

    /**
     * Retourne un parametre: si le parametre existe, on le retourne.
     * S'il n'existe pas et que le create est TRUE, on le creer.
     * Sinon, on retourne null.
     */
    public Parametre findParametre(String name, String type, boolean create) {
        Parametre p = null;
        Iterator<Parametre> it = this.getParamIterator();
        while(it.hasNext()) {
            p = it.next();
            if(p.getName().equals(name))
                return p;
        }
        return create ? new Parametre(name, type) : null;
    }

    /**
     * Retourne l'iterateur de params.
     * @return params.iterator
     */
    public Iterator<Parametre> getParamIterator() {
        return this.params.iterator();
    }
    
    /**
     * Override la methode compareTo d'une operation.
     * On compare deux operations par rapport leur nom et leur 
     * type de retour.
     * Retourne un entier indiquant le resultat de cette comparaison.
     * @return un entier resultant de la comparaison.
     */
    @Override
    public int compareTo(Operation o) {
        return o.methodeName.compareTo(this.methodeName)
                + o.typeReturn.compareTo(this.typeReturn);
    }
    
    /**
     * Override la methode toString.
     * Construction d'un StringBuilder.
     * Nous permet d'afficher une operation comme le GUI le demande:
     * type_de_retour nom_operation ( type_du_param )
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("%s %s(", typeReturn, methodeName));
        Iterator<Parametre> iter = params.iterator();
        if(iter.hasNext())
            sb.append(iter.next().toString());
        while(iter.hasNext()) {
            sb.append(String.format(", %s", iter.next().getType()));
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Override la methode accept de IVisiteur.
     * Accepte la visite de l'objet IVisiteur.
     * @param obj, le visiteur.
     */
    @Override
    public void accept(IVisiteur obj) {
        obj.visit(this);
    }
}

