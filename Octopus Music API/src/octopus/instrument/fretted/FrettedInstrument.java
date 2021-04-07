package octopus.instrument.fretted;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

import octopus.Note;
import octopus.NoteException;
import octopus.Notes;
import octopus.instrument.Instrument;

/**
 * Represents string fretted instruments like guitar, bass, bandolin etc. Piano,
 * violin, cello are string instrument NOT fretted.
 */

public class FrettedInstrument extends Instrument implements Serializable {

	private static final long serialVersionUID = 1L;

 protected int nFrets;
  protected InstumentString[] strings; // the tunning of the instrument


  public FrettedInstrument(String name){
    super(name);
  }

/**
 * Return an array of InstrumentString sorted by it's number. This means, in the
 * position 0 of the array there is the top string(higher pitch).
 */
  public InstumentString[] getStrings() {
    Arrays.sort(this.strings);
    return strings;
  }


  public InstumentString getTopString(){
    return strings[0];
  }

  public InstumentString getBottonString(){
    return strings[strings.length -1];
  }



  public void setStrings(InstumentString[] strings){
    this.strings = strings;
    Arrays.sort(this.strings);
  }

  public InstumentString getString(int stringNumber){
    return strings[stringNumber - 1];
  }


  public void setNumberOfFrets(int nFrets){
    this.nFrets = nFrets;
  }

  public int getNumberOfFrets(){return nFrets;}


  @Override
public String toString(){
    return this.name;
  }



 public GuitarNotePosition getNotePosition(InstumentString string, Note note, int startingFret) throws NoteException{
  return getNotePosition(string, note, startingFret, nFrets,false);
 }

 /**Returns the note position if exists. If not, returns null. 
 * @throws NoteException */
 public GuitarNotePosition getNotePosition (InstumentString string, Note note) throws NoteException{
  return getNotePosition(string, note,0,nFrets,false);
 }

 /**Returns the note position within a fret range. If not exists, returns null. 
 * @throws NoteException */
 public GuitarNotePosition getNotePosition(InstumentString string, Note note, int startingFret, int endingFret, boolean octavesDiscern) throws NoteException{
   GuitarNotePosition guitarNotePosition = null;
   int fret = -1;
   Note openStringNote = string.getOpenStringNote();

        fret = Notes.getDistance(openStringNote, note, octavesDiscern);
        if ((fret>= startingFret)&& (fret<= endingFret)){
          guitarNotePosition = new GuitarNotePosition(fret,string.getStringNumber());
        }

     return guitarNotePosition;
 }
 /** Returns and array of note positions of the specified note.
  * @param note note searched.
  * @param octavesDiscern  if true, do not return notes in a different pitch
  * octave
 * @throws NoteException */
 public GuitarNotePosition[] geNotePositions(Note note, boolean octavesDiscern) throws NoteException{
   Vector<GuitarNotePosition> guitarNotePositions = new Vector<GuitarNotePosition>();
   for (int i = 0; i < strings.length; i++) {
     Note openStringNote = strings[i].getOpenStringNote();
     int fret = Notes.getDistance(openStringNote, note, octavesDiscern);
     while (fret <= this.nFrets) {
       guitarNotePositions.add(new GuitarNotePosition(fret, strings[i].getStringNumber()));
       fret+=12; // 12 semitons
     }
   }
     return guitarNotePositions.toArray(new GuitarNotePosition[0]);
 }


 /** Returns the note of a particular note positions. */
 public Note getNote(GuitarNotePosition guitarNotePosition){
   try{
     int nGuitarString = guitarNotePosition.getString();
     int nFret = guitarNotePosition.getFret();

     Note openStringNote = strings[nGuitarString-1].getOpenStringNote();
     Note returnNote = Notes.getNote(openStringNote,nFret);

     return returnNote;

   }catch(NoteException ex){
     //Never happens. Parameter under safe range.
       return null;
   }

 }
}

