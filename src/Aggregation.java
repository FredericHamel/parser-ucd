
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe Aggregation represente une relation du type aggregation dans notre model.
 * @author frederic
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

    public boolean addPart(Role part) {
        return this.parts.add(part);
    }

    public boolean addParts(Set<Role> parts) {
        return this.parts.addAll(parts);
    }

    public String getSerializeName() {
        StringBuilder sb = new StringBuilder();
        Iterator<Role> iter = iterator();
        sb.append("(A) P_");
        sb.append(container.getClasse().getName());
        return sb.toString();
    }

    public Role getContainer() {
        return container;
    }

    public Iterator<Role> iterator() {
        return this.parts.iterator();
    }

    @Override
    public int compareTo(Aggregation o) {
        return this.container.compareTo(o.container) + (this.parts.hashCode() - o.parts.hashCode());
    }

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
