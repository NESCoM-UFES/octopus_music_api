package octopus.instrument;

import octopus.*;

import java.util.*;
import octopus.instrument.fretted.GuitarNotePosition;

/**
 *
 * @author Leandro Costalonga
 * @version 1.0
 */
public class PerformableMelody
    extends Melody {
  Hashtable<Note, InstrumentNotePosition> notesPositions;


  public PerformableMelody( RhythmPattern rp) {
  super(rp);
  notesPositions = new Hashtable<Note, InstrumentNotePosition>();
 }

  public PerformableMelody(Note[] notes, RhythmPattern rp) {
    super(notes, rp);
    notesPositions = new Hashtable<Note, InstrumentNotePosition>();
  }

 /* public PerformableMelody(Note[] notes,InstrumentNotePosition[] notePositions, RhythmPattern rhythmPattern) {
    super(notes, rhythmPattern);
    this.notesPositions = new Hashtable(Arrays.asList(notePositions));


  }*/

  public void addNote(Note note, InstrumentNotePosition notePosition){
    super.addNote(note);
    notesPositions.put(note,notePosition);
  }

  public void replaceInstrumentNotePosition(Note note, InstrumentNotePosition notePosition){
    notesPositions.put(note,notePosition);
  }

  public InstrumentNotePosition getNotePosition(Note note){
    if(notesPositions.containsKey(note)){
      return (InstrumentNotePosition) notesPositions.get(note);
    }
    return null;
  }

/*  public void setInstrumentNotePosition(InstrumentNotePosition[] notePositions){
    this.notesPositions = new Vector(Arrays.asList(notePositions));
}


  public PerformableMelody(String[] noteNames, RhythmPattern rp) {
    super(noteNames, rp);
  }*/

@Override
public String toString(){
  String pMelody = "";
  for (int i = 0; i < notes.size(); i++) {
    Note note = (Note)notes.get(i);
    GuitarNotePosition notePosition = (GuitarNotePosition)notesPositions.get(note);
    pMelody += note.getSymbol() + " " + notePosition.toString() + "\n";
  }
  return pMelody;
}


}
