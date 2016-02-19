/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frederic
 */
public interface IVisiteur {
    void visit(Model m);
    void visit(Classe c);
    void visit(Attribut a);
    void visit(Operation o);
}
