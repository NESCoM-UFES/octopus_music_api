package octopus;

/**
 * Thrown when there is a problem at the chord formation.
 * @author lcostalonga
 *
 */
public class ChordException
    extends Exception {
  
   private static final long serialVersionUID = 1L;

  Chord chord;

  public ChordException(String message, Chord chord) {
    super(message);
    this.chord = chord;
  }


}
