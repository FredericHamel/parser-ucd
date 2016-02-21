/**
 * Interface IVisitable. Les classes qui seront visitees
 * implementeront celle-ci.
 * @author Frederic Hamel et Sabrina Ouaret
 */

public interface IVisitable {

    /**
     * Accepte le visiteur.
     * @param obj le visiteur.
     */
    void accept(IVisiteur obj);
}
