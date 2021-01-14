package octopus;

/**
 * Thrown when there is a problem with the Notation used to describe the Chords. 
 * @see java.lang.Exception
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */

public class ChordNotationException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1681527551283850325L;
private String invalidString;

/* Detalha o motivo da irregularidade na escrita da cifra.
 * @param mensagem Mensagem a ser exibida ao usu�rio quando uma exce��o ocorrer.
 * @param cifra Cifra considerada inv�lida.
 */
  public ChordNotationException(String message, String invalidString) {
    super(message);
    this.invalidString = invalidString;
  }

/**
 * 
 * @return The symbol that does not belong to the currently notation.
 */
  public String getInvalidString() {
    return invalidString;
  }

}
