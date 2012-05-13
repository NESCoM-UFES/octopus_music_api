package octopus;


import java.util.*;
import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;
/**
 * Melody is a set of notes played sequentially according a RhythmPattern.
 * 
 */
public class Melody  extends MusicalComponent implements Playable{

	/**
	 * Repeates the notes to match the full extension of the RhythmPattern.
	 */
	protected boolean circularListNotes = true;

	protected Vector<Note> notes;


	/**
	 * Creates a Melody with the specified Note[] and RhythmPattern;  
	 * @param notes array of notes of the melody;
	 * @param rhythmPattern the rhythmic line of the melody.
	 */
	public Melody(Note[] notes, RhythmPattern rhythmPattern) {
		super(rhythmPattern);
		this.notes = new Vector<Note>(Arrays.asList(notes));
	}

	/**
	 * Creates a Melody with the specified note names and RhythmPattern;  
	 * @param noteNames array of String with the note's names of the melody; for example String ["C", "D", "E"].
	 * @param rhythmPattern the rhythmic line of the melody.
	 * @throws NoteException 
	 */ 
	public Melody(String[] noteNames, RhythmPattern rhythmPattern) throws NoteException {
		super(rhythmPattern);
		setNotes(noteNames);

	}
	/**
	 * Creates a Melody with the specified RhythmPattern; The notes[] must be added separately.  
	 * @param rhythmPattern
	 */
	public Melody(RhythmPattern rhythmPattern) {
		super(rhythmPattern);
		notes = new Vector<Note>();
	}



	public void setNotes(Note[] notes){
		this.notes = new Vector<Note>(Arrays.asList(notes));
	}

	public void setNotes(String[] noteNames) throws NoteException{
		this.notes = new Vector<Note>();
		for (int i=0;i<noteNames.length;i++){
			notes.add(Notes.getNote(noteNames[i]));
		}
	}

	public void addNote(Note note){
		this.notes.add(note);
	}

	public Note[] getNotes(){
		return (Note[])notes.toArray(new Note[0]);
	}

	public void setRhythmPattern(RhythmPattern rp){
		rhythmPattern = rp;
	}


	public MusicalEventSequence getMusicalEventSequence() {
		return this.getMusicalEventSequence(getNotes(), 
				rhythmPattern.getRhythmEvents(true),
				this.isCircularListNotes());
	}



	/**
	 * @return true if list of notes repeats to match the RhythmPattern;
	 */
	 public boolean isCircularListNotes() {
		 return circularListNotes;
	 }

	 /**
	  * Set true to repeat the list of notes according to the RhythmPAttern. If false, the RhythmPattern
	  * will be trimmed to match the number of notes in the list.
	  * @param circularListNotes true if list of notes repeats to match the RhythmPattern.
	  */
	 public void setCircularListNotes(boolean circularListNotes) {
		 this.circularListNotes = circularListNotes;
	 }

}
