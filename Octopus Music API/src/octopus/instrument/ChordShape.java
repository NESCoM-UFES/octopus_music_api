package octopus.instrument;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

import octopus.Chord;

/* A classe abstrata "Representacao" garante que todas as futuras representações irão conter,
  ao menos, a estrutura básica descrita em seu conteúdo.
  Diferentes instrumentos tem diferentes modos de representar um acorde(fisicamente).
 */

public abstract class ChordShape implements Serializable{ ////Removed Playable for the time being.

  public Vector<InstrumentNotePosition> notePositions = new Vector<InstrumentNotePosition>();
  public Chord chord;
  public String description = "";

  public ChordShape(Chord chord){
    this.chord = chord;
  }


  public void addInstrumentNotePosition(InstrumentNotePosition posNote){
    notePositions.add(posNote);
  }

  /**
   * Return an sorted array of IntrumentNotePositions. Each IntrumentNotePosition
   * subclass must implement the Comparable.compareTo(Object o) methor is order to
   * allow the sorting.
   */
  public InstrumentNotePosition[] getInstrumentNotePositions(){
    InstrumentNotePosition[] instrumentNotePositions =
        notePositions.toArray(new InstrumentNotePosition[0]);
    Arrays.sort(instrumentNotePositions);
    return instrumentNotePositions;
  }

  public int getPolyphony(){
     return notePositions.size();
  }

  public void setChord(Chord acorde){
    this.chord = acorde;
  }

  public Chord getChord(){
    return chord;
  }

  public void setDescription(String description){
    this.description = this.description + this.description + "\n";
  }

  public String getDescription(){
    return description;
  }


}
