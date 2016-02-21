
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe Aggregation represente une relation du type aggregation dans notre model.
 * @author Frederic Hamel et Sabrina Ouaret
 */
public class Aggregation implements Comparable<Aggregation> /* extends Relation */ {

    private final Role container;
    private final Set<Role> parts;

    /**
     * Constructeur
     * @param container
     */
    public Aggregation(Role container) {
        this.container = container;
        this.parts = new TreeSet<>();
    }

    /**
     * Ajout d'un role
     * @param part, le role a ajoute
     */
    public boolean addPart(Role part) {
        return this.parts.add(part);
    }

    /**
     * Ajout de plusieurs roles.
     * @param parts, un set de type Role
     */
    public boolean addParts(Set<Role> parts) {
        return this.parts.addAll(parts);
    }

    /**
     * @return le formatage du nom cree pour l'affichage de cette relation d'aggregation.
     */
    public String getSerializeName() {
        StringBuilder sb = new StringBuilder();
        Iterator<Role> iter = iterator();
        sb.append("(A) C_");
        sb.append(container.getClasse().getName());
        return sb.toString();
    }

    /**
     * @return container, la classe conteneur de l'aggregation.
     */
    public Role getContainer() {
        return container;
    }

    /**
     * @return l'iterateur de Role
     */
    public Iterator<Role> iterator() {
        return this.parts.iterator();
    }

    /**
     * Redefinition de compareTo selon les roles de l'aggregation
     * @return l'entier determinant la finalite de comparaison
     */
    @Override
    public int compareTo(Aggregation o) {
        return this.container.compareTo(o.container) + (this.parts.hashCode() - o.parts.hashCode());
    }

    /**
     * Override de toString, permettant le debuggage
     * du parsing du fichier seulement.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AGGREGATION\n\tCONTAINER\n\t\t");
        sb.append(container.toString());
        sb.append("\n\tPARTS\n");
        Iterator<Role> iter = iterator();
        while(iter.hasNext()) {
            sb.append(String.format("\t\t%s", iter.next().toString()));
            if(iter.hasNext())
                sb.append(",\n");
        }
        sb.append(";\n");
        return sb.toString();
    }
}
