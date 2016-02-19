/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frederic
 */
public interface IVisitable {

    /**
     * Accept le visiteur.
     * @param obj le visiteur.
     */
    void accept(IVisiteur obj);
}
