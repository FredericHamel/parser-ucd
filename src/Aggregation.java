
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frederic
 */
public class Aggregation /* extends Relation */ {

    private final Role container;
    private final Set<Role> parts;
    
    /**
     *
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
        sb.append("(A)");
        while(iter.hasNext()) {
            sb.append(" P_");
            sb.append(iter.next());
        };
        return sb.toString();
    }
    
    public Role getContainer() {
        return container;
    }
    
    public Iterator<Role> iterator() {
        return this.parts.iterator();
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
