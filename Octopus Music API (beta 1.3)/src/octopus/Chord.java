package octopus;

import java.util.*;
import java.io.*;

import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;
/**
 * A Chord is a set of <code> Notes </code> associated with the <code>Interval</code> it represents.
 * The relation between notes of the chords, expressed in musical intervals, 
 * defines the name of the chord. 
 * <p>The chord's name follows a syntax defined in the ChordNotation and  
 * may vary in different musical communities.</p> 
 *        
 * @see ChordNotation  
 * @see Interval
 * @see Note
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */

public class Chord implements Serializable,Playable{

	private static final long serialVersionUID = 1L;

	/** 
	 * The relation of the notes that compose the chord.
	 */
	protected ArrayList<Note> notes;
	protected ArrayList<Interval> intervals;

	/**
	 *  A verified textual name of the chord;  
	 */
	protected ValidChordName validChordName;


	/**
	 * The bass note index.
	 * 
	 */
	private int bassPos = -1;

	/**
	 * The index position of the fundamental note of the chord, if it is not the same as the bass note.
	 */
	private int rootPos = -1;


	private static ChordNotationInterpreter chordNotationInterpreter =  new ChordNotationInterpreter();

	// Just for internal developing use.
	Chord(ValidChordName validChordName) throws ChordNotationException{
		this.validChordName  = validChordName;
		chordInit();
	}



	Chord() {
		notes = new ArrayList<Note>();
		intervals = new ArrayList<Interval>();
	}

	/*
	 * @param rootNote Fundamental/Root note;
	 */
	Chord(Note rootNote) {
		notes = new ArrayList<Note>();
		intervals = new ArrayList<Interval>(); 
		notes.add(rootNote);
		intervals.add(IntervalFactory.fundamental);

		rootPos = bassPos = notes.indexOf(rootNote);
		bassPos = notes.indexOf(rootNote); // Automatic definition of the bass as the root note;

	}

	/**
	 * Creates a chord based on the specified chordName according to the 
	 * default <code> ChordNotation </code>. For example: "Cm7(add11)".
	 * @param chordName the name of the chord according current notation;
	 * @throws ChordNotationException thrown when the chordName can not be resolved using the current notation;
	 * @throws NoteException 
	 * 
	 */
	public Chord(String chordName) throws ChordNotationException, NoteException {
		validChordName  = chordNotationInterpreter.getValidChordName(chordName);
		chordInit();
	}

	/**
	 * Creates a chord based on the specified chordName according to and  
	 * <code> ChordNotation </code>.
	 * @param chordName the name of the chord;
	 * @param chordNotation the ChordNotation that must be used to resolve to chordName;
	 * @throws ChordNotationException hrown when the chordName can not be resolved using the informed notation
	 * @throws NoteException 
	 */
	public Chord(String chordName, ChordNotation chordNotation) throws ChordNotationException, NoteException{
		chordNotationInterpreter = new ChordNotationInterpreter(chordNotation);
		validChordName  = chordNotationInterpreter.getValidChordName(chordName);
		chordInit();
	}


	public String getChordName(){
		String chordName = "";
		if(validChordName!= null){
			chordName =  validChordName.chordName;
		}else{     
			chordName =  notes.get(rootPos).getSymbol() + "(";
			for (int i = 0; i < notes.size(); i++) {
				if (i != rootPos) {
					Interval interval =  intervals.get(i);
					chordName += interval.getSymbol() + " ";
				}
			}
			chordName += ")";
			if (this.bassPos != rootPos) {
				chordName += "//" + ( intervals.get(rootPos).getSymbol());
			}
		}
		return chordName;
	}

	public int getPolyphony(){
		return notes.size();
	}
	@Override
	public String toString(){
		String r ="";
		r+=("Chord: " + this.getChordName() + "\n");
		r+= "======== Notes ======== \n";
		for (int i=0; i<this.notes.size();i++){
			r+=( i + ": " + notes.get(i).toString() + " - " +intervals.get(i).toString()+ "\n");
		}
		return r;	
	}
	/*
	 * Adiciona a note para a relação de notes do Chord.
	 * @param note Note a ser adicionada à relação de notes do Chord.
	 */
	public void addNote(Note note, Interval interval) {
		notes.add(note);
		intervals.add(interval);
		//Define a posição da fundamental e do baixo, caso o mesmo seja igual a funcamental;
		if (interval.getDistanceFromRoot() ==0){
			rootPos = notes.indexOf(note);
			if(bassPos == -1){
				bassPos = rootPos = notes.indexOf(note);
			}
		}

	}

	public void addNote(Note note) throws NoteException{
		Note rootNote = notes.get(rootPos);
		Interval interval = IntervalFactory.getInterval(rootNote, note);
		addNote(note,interval);
	}

	public void removeChordNote(Note note){
		for (int i=0; i<notes.size();i++){
			if(notes.get(i).getSymbol().equalsIgnoreCase(note.getSymbol())){
				if(bassPos>i){
					bassPos--;
				}
				notes.remove(i);
				intervals.remove(i);
			}
		}
	}

	public void remove5th(){
		for (int i=0; i<notes.size();i++){

			if(intervals.get(i).getNumericInterval()==5){
				if(bassPos>i){
					bassPos--;
				}
				notes.remove(i);
				intervals.remove(i);
			}
		}
	}


	public void removeInterval(Interval interval){
		for (int i=0; i<intervals.size();i++){    
			if(intervals.get(i).getNumericInterval()==interval.getNumericInterval()){
				if(bassPos>i){
					bassPos--;
				}
				notes.remove(i);
				intervals.remove(i);
			}
		}
	}

	/*
	 *
	 *  
	 * @return The root note of the chord.
	 */
	 public Note getRootNote() {return (notes.get(0));}

	 /*
	  * @return Notes of the chord;
	  *
	  */
	 public Note[] getNotes() {
		 Note[] tempNotes = notes.toArray(new Note[0]);        
		 Arrays.sort(tempNotes);        
		 return tempNotes;
	 }


	 public Interval getInterval(Note note){
		 for(int i=0;i<notes.size();i++){     
			 if(notes.get(i).getName().equalsIgnoreCase(note.getName())){
				 return intervals.get(i);
			 }
		 }
		 return null;
	 }

	 /*
	  * @param interval
	  * @return The note and its role(interval) encapsulated in a ChordNote object;.
	  */
	 public  Note getNote(Interval interval){
		 Note retorno=null;
		 for(int i=0; i<intervals.size();i++){
			 if (intervals.get(i).getSymbol() == interval.getSymbol()) {
				 return notes.get(i);        
			 }
		 }
		 return retorno;
	 }

	 /*
	  * @param interval
	  * @return The note and its role(interval) encapsulated in a ChordNote object;.
	  */
	 public  Note getNote(int numericInterval){
		 Note retorno=null;
		 for(int i=0; i<intervals.size();i++){
			 if (intervals.get(i).getNumericInterval() == numericInterval) {
				 return notes.get(i);        
			 }
		 }
		 return retorno;	      	   
	 }

	 /*
	  * Sets the bass note index position the the array of notes;
	  * @param posNotaBaixo Bass note index.
	  */
	 protected void setBassNote(int bassNotePos) throws ChordException, NoteException{
		 bassPos = bassNotePos;
		 Note bassNote =  notes.get(bassNotePos);
		 Note rootNote =  notes.get(rootPos);
		 int cromaticIndexBassNote = Notes.getCromaticNoteIndex(bassNote);
		 int cromaticIndexRootNote = Notes.getCromaticNoteIndex(
				 rootNote);

		 if (cromaticIndexBassNote > cromaticIndexRootNote) {
			 int rootPitchOctave = rootNote.getOctavePitch();
			 int bassPitchOctave = bassNote.getOctavePitch();

			 if (rootPitchOctave > 0) {
				 try{
					 bassNote.setOctavePitch(bassPitchOctave - 1);
				 }catch(NoteException ex){
					 throw new ChordException("The ocatve value can not be lower than 0.", this);
				 }

			 }
		 }
	 }

	 /*
	  *  @return The bass note and its interval encapsulated in a ChordNote object;.
	  */
	 public Note getBassNote() {return notes.get(bassPos);}


	 private void writeObject(java.io.ObjectOutputStream out)throws IOException{
		 out.writeObject(notes);
		 out.writeObject(validChordName);
		 out.writeInt(bassPos);
		 out.writeInt(rootPos);

	 }

	 @SuppressWarnings("unchecked")
	 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
		 notes = (ArrayList<Note>)in.readObject();
		 intervals = (ArrayList<Interval>)in.readObject();
		 validChordName = (ValidChordName)in.readObject();
		 bassPos = in.readInt();
		 rootPos = in.readInt();
	 }

	 private void chordInit(){
		 try{
			 Note rootNote = validChordName.getRootNote().getNote();
			 notes = new ArrayList<Note>();
			 intervals = new ArrayList<Interval>();
			 Interval interval;
			 Note note;
			 boolean isBassNoteSet = false;

			 for (int i = 0; i < validChordName.getIntervalsSize(); i++) {
				 interval = validChordName.getInterval(i);
				 note = Notes.getNote(rootNote, interval);       
				 addNote(note, interval);

				 Note bassNote = validChordName.getBassNote().getNote();
				 String bassNoteSymbol = bassNote.getSymbol();
				 String noteSymbol = note.getSymbol();

				 if (bassNoteSymbol.equals(noteSymbol)) {
					 setBassNote(i);
					 isBassNoteSet = true;
				 }

			 }

			 if (!isBassNoteSet) {
				 Note bassNote = validChordName.getBassNote().getNote();
				 int distanceRootBass = Notes.getDistance(validChordName.getRootNote().
						 getNote(),
						 bassNote, false);
				 Interval bassNoteFundamentalInterval = IntervalFactory.getInterval(
						 distanceRootBass);       
				 addNote(bassNote, bassNoteFundamentalInterval);
				 setBassNote(getNotes().length - 1);
			 }
		 }catch(NoteException noteEx){
			 //Will never happen here. Parameters restrict to safe value range;
		 }catch(ChordException chordEx){
			 //Will never happen here. Parameters restrict to safe value range;
		 }
	 }

	 protected static Chord getChord(Note key, HarmonicProgression.ScaleDegree scaleDegree){
		 try {
			 Note note;
			 Interval interval;
			 if (scaleDegree.numericValue == 1) {
				 note = key;
			 }
			 else {
				 note = Notes.getNote(key,IntervalFactory.getInterval(
						 String.valueOf(scaleDegree.numericValue)));

			 }
			 Chord chord = new Chord(note);
			 // 3th addition
			 if (scaleDegree.mode == 0) {
				 interval = IntervalFactory.getMinorThird();
				 chord.addNote(Notes.getNote(note,interval),interval);
			 }
			 else {
				 interval = IntervalFactory.getMajorThird();
				 chord.addNote(Notes.getNote(note,interval),interval);
			 }
			 // 5th addtion
			 interval = IntervalFactory.getPerfectFifth();
			 chord.addNote(Notes.getNote(note,interval),interval);

			 if (scaleDegree.intervals != null) {
				 for (int i = 0; i < scaleDegree.intervals.length; i++) {
					 interval = scaleDegree.intervals[i];
					 chord.addNote(Notes.getNote(note,interval),interval);
				 }
			 }

			 return chord;

		 }catch ( NoteException ex) {
			 //Will never happen here. Parameters restrict to safe value range;
		 }
		 return null; // Should never return null;
	 }


	 public static Chord getChord(String chordName)throws ChordNotationException,
	 NoteException, ChordException {
		 ValidChordName validChordName  = chordNotationInterpreter.getValidChordName(chordName);
		 return getChord(validChordName);
	 }



	 protected static Chord getChord(ValidChordName validChordName) throws ChordNotationException, NoteException, ChordException {
		 Note rootNote = validChordName.getRootNote().getNote();
		 Chord acordeRetorno = new Chord();
		 acordeRetorno.validChordName = validChordName;
		 Interval interval;
		 Note notaSimples;
		 boolean baixoSet = false;

		 // Para cada interval já instanciado de validChordName este bloco captura a
		 // notaSimples passando a rootNote e o interval. Em seguida é instanciado
		 // um objeto do tipo ChordNote que servirá como base para retornar o valor deste
		 // método.

		 for (int i=0; i < validChordName.getIntervalsSize(); i++) {
			 interval = validChordName.getInterval(i);
			 notaSimples = Notes.getNote(rootNote, interval);     
			 acordeRetorno.addNote(notaSimples, interval);

			 /* @todo Considerar a possibilidade de identificar
			  *  as inversões através do baixo. Ex: C/E = "Primeira Inversão".
			  *                                     C/G = "Segunda Inversão".
			  *  */

			 Note bassNote = validChordName.getBassNote().getNote();
			 String strBaixo = bassNote.getSymbol();
			 String strNotaAnalisada = notaSimples.getSymbol();

			 if (strBaixo.equals(strNotaAnalisada)) {
				 acordeRetorno.setBassNote(i);
				 baixoSet = true;
			 }

		 }

		 if(!baixoSet){
			 Note bassNote = validChordName.getBassNote().getNote();
			 int distanciaFundamentalBaixo =    Notes.getDistance(validChordName.getRootNote().getNote(),
					 bassNote, false);
			 Interval bassFundamentalInterval = IntervalFactory.getInterval(distanciaFundamentalBaixo);      
			 acordeRetorno.addNote(bassNote,bassFundamentalInterval);
			 acordeRetorno.setBassNote(acordeRetorno.getNotes().length -1);
		 }
		 return acordeRetorno;
	 }

	 public MusicalEventSequence getMusicalEventSequence() {

		 double velocity=1;
		 double delay=0.2;
		 double duration = 1;

		 return getMusicalEventSequence (velocity,delay,duration);

	 }

	 //All paramenter must be > 0 and < 1;
	 public MusicalEventSequence getMusicalEventSequence(double velocity, double delay, double duration) {
		 Note[] notes = getNotes();     
		 MusicalEventSequence p =new MusicalEventSequence();
		 for (int i=0; i<notes.length;i++){
			 Note note = notes[i];
			 double time =  i * delay;
			 MusicalEvent meOct = new MusicalEvent(i,time,note,duration,velocity);
			 MusicalEvent meOctOff = new MusicalEvent(i,time+duration,note,0,0);
			 p.addMusicalEvent(meOct);
			 p.addMusicalEvent(meOctOff);
		 }
		 return p;
	 }

	 public MusicalEventSequence getMusicalEventSequence(Arpeggio arpeggio){
		 MusicalEventSequence chordMusicalEventSequence = new MusicalEventSequence();;
		 Bar.RhythmEvent[][] arpeggioRhythmEvents = arpeggio.getRhythmEvents(arpeggio.getDuration());
		 Note[] chordNotes = getNotes();

		 for (int i = 0; i < arpeggioRhythmEvents.length; i++) {
			 Bar.RhythmEvent[] voiceRhythmEvents = arpeggioRhythmEvents[i];
			 double time = 0;
			 for (int j = 0; j < voiceRhythmEvents.length; j++) {
				 Note note; 
				 if (i < chordNotes.length) {
					 note = chordNotes[i];
				 }
				 else {//repete a ultima nota caso polifonia do arpeggio seja menor
					 // do que a acorde. REPENSAR ISSO!!!
					 note = chordNotes[chordNotes.length - 1];
				 }
				 if(voiceRhythmEvents[j].type == 1){
					 MusicalEvent meOct = new MusicalEvent(j, time, note,
							 voiceRhythmEvents[j].duration,
							 voiceRhythmEvents[j].velocity);
					 MusicalEvent meOctOff = new MusicalEvent(j,
							 time + voiceRhythmEvents[j].duration,
							 note,0, 0);
					 chordMusicalEventSequence.addMusicalEvent(meOct);
					 chordMusicalEventSequence.addMusicalEvent(meOctOff);
				 }
				 time += voiceRhythmEvents[j].duration;	
			 }

		 }
		 return chordMusicalEventSequence;

	 }


	 /*chordDuration is given by the RythmPattern of the Harmony.
This method was extracted from Musician as it is. It should suffer alterations to 
make use of the other gerMusicalEvents methods. The idea is not wirte the same code in different
methods. The problem we've got at this stage is to adjust the arpeggio duration to chord duration.
I think I've done this before...will check and come back if not.
	  */
	 public MusicalEventSequence getMusicalEventSequence(Arpeggio arpeggio, double chordDuration){
		 MusicalEventSequence p =new MusicalEventSequence();
		 double timeAdjustiveFactor = 0.0;

		 for(int i=0;i<arpeggio.voices.size();i++){ //para cada voz do arpeggio
			 double time = 0;
			 RhythmPattern rhythmPattern = (RhythmPattern) arpeggio.voices.get(i);
			 Note[] chordNotes = getNotes();
			 Bar[] bars = rhythmPattern.getBars();
			 for (int j = 0; j < bars.length; j++) {;
			 Note note;

			 //pega as notas correspondente as vozes
			 if (i < chordNotes.length) {
				 note = chordNotes[i];
			 }
			 else {//repete a ultima nota caso polifonia do acorde seja menor
				 // do que a acorde. REPENSAR ISSO!!!
				 note = chordNotes[chordNotes.length - 1];
			 }

			 Bar.RhythmEvent[] rhythmEvents = bars[j].getRhythmEvents();
			 while(time <chordDuration){
				 for (int k = 0; k < rhythmEvents.length; k++) { // para cara evento

					 double duration = rhythmEvents[k].duration;
					 boolean tie = rhythmEvents[k].tie;
					 int type = rhythmEvents[k].type;
					 if ( (tie) && (k < rhythmEvents.length - 1)) { //verifica se nota está ligada a outra.
						 k++;
						 duration += rhythmEvents[k].duration;
					 }

					 double velocity = type == 1 ? bars[j].getAccentuation(k) : 0;

					 if(!(time >= chordDuration)){ // Significa que o evento deveria comecar junto com o termino da duracao do acorde.
						 if (arpeggio.isTimeStratch()) {
							 if (timeAdjustiveFactor==0.0){
								 double arpeggioDuration = arpeggio.getDuration();
								 timeAdjustiveFactor = (chordDuration/arpeggioDuration);
							 }
							 duration = duration * timeAdjustiveFactor;
						 }
						 else {
							 double finalDuration = time + duration; //da tempo de acabar?
									 if (finalDuration > chordDuration) {
										 duration = finalDuration - chordDuration;
									 }
						 }

						 if(velocity>0){
							 MusicalEvent meOct = new MusicalEvent(k, time, note,
									 duration,
									 velocity);
							 MusicalEvent meOctOff = new MusicalEvent(k,
									 time + duration, note,
									 0, 0);
							 p.addMusicalEvent(meOct);
							 p.addMusicalEvent(meOctOff);
						 }
						 time += duration;

					 }

				 }
			 }
			 }
		 }

		 return (p);
	 }


}


