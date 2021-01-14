package octopus.instrument;

import octopus.Chord;
import octopus.NoteException;

 /*
 * A interface ChordShapeBuilder � usada para garantir que todos os futuros
 * "Geradores de Representa��es de Acordes", seja qual for o instrumento, tenham as mesmas funcionalidades
 * b�sicas.
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
 * Retorna as representa��es b�sicas do acorde. Representa��es b�sicas s�o aquelas que possuem
 * somente uma instacia de cada nota que forma o acorde, ou seja, sem dobramentos ou duplica��es.
 * @param acorde Chord que ter� sua representa��o gerada.
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
