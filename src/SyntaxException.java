/**
 * Classe SyntaxException herite de RuntimeException.
 * @author Frederic Hamel et Sabrina Ouaret
 */

public class SyntaxException extends RuntimeException {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1812533093666009581L;

	public SyntaxException(String msg) {
		super(msg);
	}
}
