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
    public Association(Role a, Role b) {
        super('R', a, b);
    }
}
