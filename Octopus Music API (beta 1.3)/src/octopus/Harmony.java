package octopus;


import java.util.*;

import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;

/**
 * Harmony is a <code>MusicalComponent</code> that encapsulates chords + arpeggios
 * played in sequence according to RhythmPattern and Arpeggio.
 *
 *
 * @see RhythmPattern
 * @see MusicalComponent
 * @see Arpeggio

 */
public class Harmony extends MusicalComponent implements Playable {
	protected Vector<Chord> chords = new Vector<Chord>();
	protected Hashtable<Chord, Arpeggio> arpeggios = new Hashtable<Chord, Arpeggio>();


	/**
	 * Creates a Harmony with the specified chords that will be played with a constant
	 * 4/4 RhythmPattern. The chord's duration is quarter note = 0.25.
	 * @param chords array of chords of the Harmony.
	 */
	public Harmony(Chord[] chords) {/**@todo verificar se esta funcionando*/
		super(new RhythmPattern(4,4,chords.length));
		this.chords = new Vector<Chord>(Arrays.asList(chords));

	}
	/**
	 * Create a Harmony with specified RhythmPattern. The Chords must be added separately. 
	 * @param rhythmPattern rhythmic line of the Harmony.
	 */
	public Harmony(RhythmPattern rhythmPattern) {
		super(rhythmPattern);
	}

	/**
	 * Create a Harmony with specified Chords and RhythmPattern. 
	 * @param chords array of chords of the Harmony.
	 * @param rhythmPattern rhythmic line of the Harmony.
	 */
	public Harmony(Chord[] chords,RhythmPattern rhythmPattern ) {
		super(rhythmPattern);
		this.chords = new Vector<Chord>(Arrays.asList(chords));

	}

	/**
	 * Create a Harmony with specified Chords, RhythmPattern, and Arpeggio.The same Arpeggio will be applied 
	 * to all chords.
	 * @param chords  chords array of chords of the Harmony.
	 * @param rhythmPattern rhythmPatternrhythmic line of the Harmony.
	 * @param arpeggio the arpeggio applied to the chords.
	 */
	public Harmony(Chord[] chords,RhythmPattern rhythmPattern, Arpeggio arpeggio ) {
		super(rhythmPattern);
		this.addChord(chords,arpeggio );
	}
	/**
	 * Create a Harmony with specified Chords, RhythmPattern, and Arpeggios. For every chords[i] an arpeggio[i]
	 * must be passed. One Arpeggio per Chord.
	 * to all chords.
	 * @param chords  chords array of chords of the Harmony.
	 * @param rhythmPattern rhythmPatternrhythmic line of the Harmony.
	 * @param arpeggios the arpeggios applied to the chords. One arpeggio per chord.
	 */
	public Harmony(Chord[] chords,RhythmPattern rhythmPattern,Arpeggio[] arpeggios ) {
		super(rhythmPattern);
		for (int i = 0; i < chords.length; i++) {
			if(i<arpeggios.length){
				addChord(chords[i], arpeggios[i]);
			}else{
				addChord(chords[i]);
			}
		}

	}

	public void setDefaultArpeggio (Arpeggio arpeggio){
		for (int i = 0; i < chords.size(); i++) {
			Chord chord = (Chord)chords.get(i);
			if(!arpeggios.containsKey(chord)){
				arpeggios.put(chord,arpeggio);
			}
		}
	}

	public void addChord(Chord chord){
		chords.add(chord);
	}

	public void addChord(Chord chord, Arpeggio arpeggio){
		chords.add(chord);
		arpeggios.put(chord,arpeggio);
	}

	public void addChord(Chord[] chords, Arpeggio arpeggio){
		for(int i = 0; i < chords.length; i++){
			addChord(chords[i], arpeggio);
		}
	}

	public void addChord(Chord[] chords){
		for(int i = 0; i < chords.length; i++){
			addChord(chords[i]);
		}
	}

	protected void removeChord(int index){
		chords.remove(index);
		arpeggios.remove(new Integer(index));
	}

	public void removeChord(Chord chord){
		for(int i = 0; i < chords.size(); i++){
			Chord chordArray = (Chord)chords.get(i);
			if(chordArray==chord){
				removeChord(i);
				break;
			}
		}
	}


	/*public void transpose(int semitons){
   //implementar depois
 }
// Nao funcionam devido a comparacoa de objetos e nao de valores e talvez seja
// certo deixar assim pra facilitar a interface grafica.
 /*public void removeChord(Chord chord, int index){
   int chordsFound = 0;
   for(int i = 0; i < chords.size(); i++){
    Chord chordArray = (Chord)chords.get(i);
    if(chordArray==chord){
      chordsFound++;
      if(index == chordsFound){
        removeChord(i);
        break;
      }
    }
 }
}
  public void removeAllChords(Chord chord){
  for(int i = 0; i < chords.size(); i++){
    Chord chordArray = (Chord)chords.get(i);
    if(chordArray==chord){
      removeChord(i);
    }
  }
}*/


	public Chord[] getChords(){
		return (Chord[]) chords.toArray(new Chord[0]);
	}

	public Arpeggio getArpeggio(Chord chord){

		return (Arpeggio)arpeggios.get(chord);
	}

	public void setRhythmPattern(RhythmPattern rhythmPattern ){
		this.rhythmPattern = rhythmPattern;
	}

	public HarmonicProgression[] getHarmonicProgression(){
		return null;
	}

	@Override
	public String toString(){
		String retorno = "";
		for(int i=0;i<chords.size();i++){
			retorno += ((Chord)chords.get(i)).getChordName();

			if (arpeggios.get(chords.get(i)) != null){    	 
				retorno +=  "(" + ((Arpeggio)arpeggios.get(chords.get(i))).getName() + ")";
			}
		}
		return retorno;
	}

	public MusicalEventSequence getMusicalEventSequence(){
		MusicalEventSequence harmonyMusicalEventSequence= new MusicalEventSequence();

		Chord[] chordsHarmony = getChords();
		int indexChords = 0;

		Bar.RhythmEvent[] harmonyRhythmEvents = this.rhythmPattern.getRhythmEvents(true);
		double time = 0.0;	  
		for (int i = 0; i < harmonyRhythmEvents.length; i++) {
			Chord chord = chordsHarmony[indexChords];
			Arpeggio arpeggio = getArpeggio(chord);

			if(arpeggio==null){			  
				arpeggio = new Arpeggio(chord.getPolyphony(), harmonyRhythmEvents[i].duration);
			}

			//the the RhythmEvents with the adjusted duration.
			Bar.RhythmEvent[][] arpeggioRhythmEvents = arpeggio.getRhythmEvents(harmonyRhythmEvents[i].duration);
			Note[] notes =  chord.getNotes();

			MusicalEventSequence chordMusicalEventSequence = getMusicalEventSequence(notes,arpeggioRhythmEvents);


			chordMusicalEventSequence.delayEvents(time);
			harmonyMusicalEventSequence.addMusicalEventSequence(chordMusicalEventSequence);

			if (indexChords >= chordsHarmony.length - 1) {
				if (circularList){  
					indexChords = 0;
				}else{
					break;
				}
			}
			else {
				indexChords++;
			}
			time += harmonyRhythmEvents[i].duration;
		}

		return harmonyMusicalEventSequence;


	}



}  





