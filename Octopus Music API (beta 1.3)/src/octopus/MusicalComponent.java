package octopus;

import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;

/**
 * MusicalComponents are the structures that can be inserted into a Music. At the present moment, 
 * MusicalComponents are either Melody or Harmony.
 * @author Leandro Costalonga
 * @version 1.0
 */
public abstract class MusicalComponent implements Playable {
  protected RhythmPattern rhythmPattern;

  boolean circularList = true;
  
  public MusicalComponent(RhythmPattern rhythmPattern) {
    this.rhythmPattern = rhythmPattern;
  }

  public double getDuration(){
   return rhythmPattern.getDuration();
  }

  public RhythmPattern getRythmPattern(){
    return rhythmPattern;
  }

  public  void setRythmPattern(RhythmPattern rhythmPattern){
    this.rhythmPattern = rhythmPattern;;
  }
  
  /*public MusicalEventSequence getMusicalEventSequence(){
	  MusicalEventSequence musicalComponentSequence = null;
	  if (this instanceof Melody){
		  musicalComponentSequence =  getMusicalEventSequence (((Melody)this).getNotes());
	  }
	  
	  return musicalComponentSequence;
  }*/
  
  public MusicalEventSequence getMusicalEventSequence(Note note, Bar.RhythmEvent[] rhythmEvents ) {
	  Note[] notes = {note};
	  return getMusicalEventSequence(notes,rhythmEvents, true);
  }
  
  
  public MusicalEventSequence getMusicalEventSequence(Note[] notes, Bar.RhythmEvent[] rhythmEvents ) {
	  return getMusicalEventSequence(notes,rhythmEvents, true);
  }
  
  public MusicalEventSequence getMusicalEventSequence(Note[] notes, Bar.RhythmEvent[] rhythmEvents, boolean circularListNotes ) {
	  MusicalEventSequence musicalEventSequence = new MusicalEventSequence();		  
	  
	  double time= 0;
	  int indexNotes = 0;

	  for (int i = 0; i < rhythmEvents.length; i++) {
		  double duration =  rhythmEvents[i].duration;

		  if(rhythmEvents[i].type==1){ // Note rest
			  MusicalEvent meOct = new MusicalEvent(i, time, notes[indexNotes], duration,
					                                rhythmEvents[i].velocity);
			  MusicalEvent meOctOff = new MusicalEvent(i, time + duration,
					                                   notes[indexNotes], 0, 0);
			  musicalEventSequence.addMusicalEvent(meOct);
			  musicalEventSequence.addMusicalEvent(meOctOff);
		  }
		  time += duration;

		  if (indexNotes >= notes.length - 1) {
			  if (circularListNotes){  
				  indexNotes = 0;
			  }else{
				  break;
			  }
		  }
		  else {
			  indexNotes++;
		  }		  

	  }

	  return musicalEventSequence;	

  }
  
  /**
   * Link the notes in Note[] to the rhythmEvents in Bar.RhythmEvent[]. The Note in position Notes[i] 
   * will the linked with the Bar.RhythmEvents in the position  Bar.RhythmEvent[i]. Ideally Bar.RhythmEvent[] 
   * and Note[] should be the same size (polyphony). However, in case the voice polyphony (Bar.RhythmEvent[]) 
   * is greater than the number of Notes of Note[], then the last note in Note[] will be repeated to match the 
   * polyphoy of the voice.
   * 
   *  This method is used, for example, to match the notes of a Chord to an Arpeggio voices.
   * @param notes Notes of the voice;
   * @param voicesRhythmEvents Bar.RhythmEvent[]of the voice.
   * 
   */
  public MusicalEventSequence getMusicalEventSequence(Note[] notes, Bar.RhythmEvent[][] voicesRhythmEvents){
		MusicalEventSequence chordMusicalEventSequence = new MusicalEventSequence();;

		for (int i = 0; i < voicesRhythmEvents.length; i++) {
			Bar.RhythmEvent[] voiceRhythmEvents = voicesRhythmEvents[i];
			
				Note note; 
				if (i < notes.length) {
					note = notes[i];
				}
				else {//repete a ultima nota caso polifonia do arpeggio seja menor
					// do que a acorde. REPENSAR ISSO!!!
					note = notes[notes.length - 1];
				}
				
				MusicalEventSequence voiceSequence = this.getMusicalEventSequence(note, voiceRhythmEvents);	
				chordMusicalEventSequence.addMusicalEventSequence(voiceSequence);					

		}
		return chordMusicalEventSequence;

	}
 
 // Not yet. Next version 
 // public abstract void transpose(int semitons);



}
