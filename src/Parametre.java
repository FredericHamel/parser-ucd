/**
 * Classe parametre qui represente un parametre d'operation
 * du model. Implemente l'interface Comparable d'element Parametre.
 * @author Frederic Hamel et Sabrina Ouaret
 */
public class Parametre implements Comparable<Parametre> {
    private String name, type;

    /**
     * Constructeur
     * @param name, le nom du parametre.
     * @param type, le type du paramatre.
     */
    public Parametre(String name, String type){
        this.name=name;
        this.type=type;
    }

    /**
     * Retourne le nom du parametre.
     * @return name, le nom du parametre.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retourne le type du paramatre.
     * @return type, le type du parametre.
     */
    public String getType(){
        return this.type;
    }
    
    /**
     * Override de la methode compareTo qui compare deux parametres.
     * Comparaison basee sur le type du parametre et le nom.
     * @return un entier selon le resultat des compaisons.
     */
    @Override
    public int compareTo(Parametre o) {
        return type.compareTo(o.type) + name.compareTo(o.type);
    }
    
    /**
     * Override de la methode toString qui
     * retourne l'affichage necessaire pour le GUI.
     * @return le type du parametre.
     */
    @Override
    public String toString(){
        return getType();
    }
}
