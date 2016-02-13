/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frederic
 */
public class Association extends Relation {
    private String name;
    public Association(String name, Role a, Role b) {
        super('R', a, b);
        this.name = name;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RELATION ").append(name).append("\n\tROLES\n\t\t")
                .append(getLeft().toString()).append("\n\t\t").append(getRight())
                .append("\n");
        return sb.toString();
    }
}
