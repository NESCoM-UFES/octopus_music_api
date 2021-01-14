package octopus.instrument;

import octopus.Chord;
import octopus.NoteException;

 /*
 * A interface ChordShapeBuilder é usada para garantir que todos os futuros
 * "Geradores de Representações de Acordes", seja qual for o instrumento, tenham as mesmas funcionalidades
 * básicas.
 * @see Instrumento
 * @see ChordShape
 * @see RepresentacaoAcordeInstrumentoCordaMaker
 * @author Leandro Lesqueves Costalonga
 * @version 1.0
 */
public abstract class ChordShapeBuilder {
  public ChordShapeProperties options;
  protected Instrument instrument;

  /*
 * Retorna as representações básicas do acorde. Representações básicas são aquelas que possuem
 * somente uma instacia de cada nota que forma o acorde, ou seja, sem dobramentos ou duplicações.
 * @param acorde Chord que terá sua representação gerada.
 * @return Vetor de ChordShape.
 */

 public ChordShapeBuilder(Instrument instrument){
   this.instrument = instrument;
 }

public abstract ChordShape[] getChordShapes(Chord chord) throws NoteException;

public void showChordDesignProperties(boolean modal){
  options.showProperties(modal);
}


}
