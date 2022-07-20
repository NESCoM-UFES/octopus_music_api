package octopus.repl;
import octopus.*;
import octopus.repl.*;
import octopus.communication.*;
import octopus.communication.midi.*;
import examples.*;

import java.util.concurrent.TimeUnit;

import javax.sound.midi.MidiUnavailableException;

public class OctopusShell {


	// ==== CONSTANTS ===== //Talvez criar classes staticas específicas
	// para retornar esses valores. Ex dynamics.fortissimo;


	// SCALES
	public  final int MAJOR = 0;
	public  final int MINOR = 5;

	//DYNAMICS VALUES
	/**{@value}*/
	public  final double DYNAMIC_PIANISSIMO = 0.1;
	/**{@value}*/
	public  final double DYNAMIC_PIANO = 0.2;
	/**{@value}*/
	public  final double DYNAMIC_FORTE = 0.8;
	/**{@value}*/
	public  final double DYNAMIC_MEZZO_PIANO = 0.4;
	/**{@value}*/
	public  final double DYNAMIC_MEZZO_FORTE = 0.6;
	/**{@value}*/
	public  final double DYNAMIC_FORTISSIMO = 1.0;


	//NOTE DURATIONS
	/**{@value}*/
	public  final double DOUBLE_WHOLE = 2;
	/**{@value}*/
	public  final double WHOLE_NOTE = 1;
	/**{@value}*/
	public  final double HALF_NOTE = 0.5; //(1/2);4);
	/**{@value}*/
	public  final double QUARTER_NOTE = 0.25; //(1/4);
	/**{@value}*/
	public  final double EIGHT_NOTE = 0.125; //(1/8);
	/**{@value}*/
	public  final double SIXTEENTH_NOTE = 0.0625; //(1/16);
	/**{@value}*/
	public  final double THIRTY_SECOND_NOTE = 0.03125; //(1/32);


	//LOOP EVENTS
	/**{@value}*/
	public  final int LOOPS = 0;
	public  final int STOPS= 1;


	//NOTES
	public  final Note A = Notes.getA();
	public  final Note B = Notes.getB();
	public  final Note C = Notes.getC();
	public  final Note D = Notes.getD();
	public  final Note E = Notes.getE();
	public  final Note F = Notes.getF();
	public  final Note G = Notes.getG();

	final NotesREPL notes = new  NotesREPL();

	//SCALE
	final ScaleREPL scale = new ScaleREPL();


	//MIDI SETUP

	LoopMidiSynthController synthController;
	//synthController = new LoopMidiSynthController();//uncomment this line at jsh file.	
	LivePerformer musician = new LivePerformer(synthController);


	public OctopusShell() { //Remove when copying to jsh file.
		super();
		try {
			synthController = new LoopMidiSynthController();

			musician = new LivePerformer(synthController);
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//============================  MIDI utilities ===============================
	void midi(){
		OctopusMidiSystem.listDevices(true, true, true);
	}

	void midi(boolean midiIn, boolean midiOut){
		OctopusMidiSystem.listDevices(midiIn, midiOut, true);
	}

	/** Set midi out port to the current Musician. */
	void midi(String midiOutPortName) throws Exception{
		synthController.setMidiOut(midiOutPortName);
		
		/*
		 * if(synthController!=null) synthController.closeDevices();
		 * 
		 * synthController = new LoopMidiController(midiOutPortName);
		 * musician.setSynthesizerController(synthController);
		 */
	}


	/** Set midi out port to the current Musician. */
	void midi(int  midiOut) throws Exception{   	
		String midiOutPortName = OctopusMidiSystem.getMidiDeviceInfo(midiOut).getName();
		synthController.setMidiOut(midiOutPortName);
		
		/*
		 * if(synthController!=null) synthController.closeDevices();
		 * 
		 * synthController = new LoopMidiController(midiOutPortName);
		 * musician.setSynthesizerController(synthController);
		 */
	}
	
	/**
	 * Forces the sequencer to use a particular channel from this moment onwards.
	 * Previous sounding playables/loops will carry on within the channel set on
	 * the moment it starts.
	 * 
	 * @param channel midi channel
	 */
	public void channel(int channel) {
		if((channel > 0) && (channel < 17)){
			synthController.defaultChannel = channel - 1;
		}
	}

	//============================  General Utilities ===============================

	double random(){
		return Math.random();		
	}

	int random(int min, int max){
		return (int)((Math.random()*max) + min);
	}



	//============================  Music/Performance Control ===============================

	/**
	 * Pause only the current thread, which is used by the "play()" method.
	 * @todo We should consider other time figure instead of ms.
	 * @param milliseconds
	 * @throws Exception
	 */
	void pause(int milliseconds) throws Exception{
		TimeUnit.MILLISECONDS.sleep(milliseconds);
	}

	void bpm(int bpm) {
		musician.setPlayingSpeed(bpm);

	}

	/*Playable play(Playable p) throws MusicPerformanceException{ //precisa mesmo retornar playable?
		musician.play(p);
		return p;
	}*/
	
	void play(Playable p) throws MusicPerformanceException{ 
		musician.play(p);
		//return p;
	}
	
	void play(Loop loop) throws MusicPerformanceException{
		musician.play(loop);
		//return p;
	}

	void play (Chord chord, Arpeggio arpeggio) throws Exception{
		musician.play(chord.getMusicalEventSequence(arpeggio));    
	}

	void play(Note note, double duration) throws Exception{
		musician.play(note.getMusicalEventSequence(duration));  
	}

	void play(Note note, double duration, double dynamics) throws Exception{
		musician.play(note.getMusicalEventSequence(duration, dynamics));  
	}

	void stop() {
		musician.stop();
	}
	
	void stopAll() {
		musician.stopAll();
	}
	
	void stop(Loop loop) {
		musician.stop(loop);
	}
	
	void stop(int loopID) {
		musician.stop(musician.getLoop(loopID));
	}

	//Not working...but not really a necessity! Check that latter.
	/*void play(Note[] ns)throws Exception{
	     Melody m = Melody(ns, RhythmPattern.getConstantRhythmPattern(notes.length, QUARTER_NOTE));
	     musician.play(m);
	}

	void play(Chord[] cs){
		musician.play(harmony(chords));
	}*/


	/* JShell do not accept.
	 * //Dumb method for syntax purpose 
	 * public Loop loop(Loop loop) throws
	 * Exception{ return loop; }
	 */ 
	
	public Loop loop(Playable playable) throws Exception{
		/*if(playable instanceof Loop) {
			return (Loop)playable;
		}*/		
		return musician.createLoop(playable);
	}
		
	//This is a pretty dumb method just of improve the syntax os the language.
	//i.e when(loop(1), LOOPS, loop(melody(C,D,E))
	public Loop loop(int loopID) { 
		return musician.getLoop(loopID);
	}
	public Loop when(Loop primaryLoop, int LOOP_EVENT, Loop secondaryLoop) throws Exception{
		 musician.loopWhen(primaryLoop,LOOP_EVENT,secondaryLoop);
		 return secondaryLoop;
	}
	
	//pensar em como fazer isso e se realmente é necessário. Perguntar aos músicos.
/*	public void when(Loop primaryLoop, int LOOP_EVENT, Playable playable) throws Exception{
		 musician.loopWhen(primaryLoop,LOOP_EVENT,secondaryLoop);
		 //return secondaryLoop;
	}*/
	

	public Playable together(Playable...playables) {
		Music m = new Music();
		//MusicalEventSequence ms = new MusicalEventSequence();
		for (Playable p: playables){ 
			/*if(p instanceof Loop) {
				
			}else {*/
				m.insert(p);
			//}
			
			
			
		}
		return m;
	}
	
	/* Não funcionou legal...verificar se é mesmo necessário.
	public void unMute(Loop loop) {
		loop.mute(false);
	}
	public void mute(Loop loop) {
		loop.mute(true);
	}	
	public void mute(Loop loop, boolean isMute) {
		loop.mute(isMute);
	}*/
	
	//============================  Information of all the Playables ===============================

	public double duration(Playable playable) {
		return playable.getMusicalEventSequence().getDuration();
	}

	//============================   Note ===============================

	Note sharp(Note note){
		return Notes.getSharp(note);
	}

	Note flat(Note note){
		return Notes.getFlat(note);
	}

	Note pitch(Note note, int octave) throws NoteException{
		Note retorno = (Note)note.clone();
		retorno.setOctavePitch(octave);
		return retorno;
	}

	Note note(String symbol, int octave) throws NoteException{
		return Notes.getNote(symbol, octave);
	}

	Note note(String symbol) throws NoteException{
		return Notes.getNote(symbol, 4);
	}

	//============================   Array of Notes ===============================
	/** Return as array of notes of some playables */
	Note[] notes(Scale scale) throws Exception{
		return scale.getNotes();
	}

	/** Return as array of notes of some playables */
	Note[] notes(Chord chord) throws Exception{
		return chord.getNotes();
	}

	/** Return as array of notes of some playables */
	Note[] notes(Melody melody) throws Exception{
		return melody.getNotes();
	}

	/*Note[] notes(String[] noteSymbols) throws Exception{
		return Notes.getNotes(noteSymbols);
	}*/

	Note[] notes(String... noteSymbols) throws Exception{
		return Notes.getNotes(noteSymbols);
	}

	Note[] notes(Note... notes) throws Exception{
		return notes;
	}


	//============================  Processing an Array of Notes ===============================
	Note[] transpose(Note[] notes, int semitones) throws Exception{
		return Notes.transpose(notes,semitones);
	}

	Note[] suffle(Note[] notes){
		return Notes.suffle(notes);
	}   

	Note[] suffle(Note[] notes, int noNotes){
		return Notes.suffle(notes, noNotes);
	}   

	//============================   Chord ===============================

	Chord chord(String chordName) throws Exception{
		Chord chord = Chord.getChord(chordName);
		return chord;
	}

	Chord chord(Note... notes) throws Exception{
		return new Chord(notes);
	}


	Chord[] chords (Chord...chords) throws Exception{
		return chords;
	}

	Chord[] chords(String...chords) throws Exception{
		Chord[] objChords = new Chord[chords.length];
		for (int i=0; i<chords.length; i++){
			objChords[i] = Chord.getChord(chords[i]);
		}
		return objChords;
	}

	//============================  Array of Chords ===============================
	Chord[] chords (HarmonicProgression harmonicprogression, Note key) throws Exception{
		return harmonicprogression.getChords(key);
	}



	//============================   Bar ===============================

	Bar bar(String textualNotation){
		Bar bar = new Bar(4,4);
		bar.addRhythmEvent(textualNotation,SIXTEENTH_NOTE);
		return bar;
	}

	Bar bar(String textualNotation, double subBeatDuration){
		Bar bar = new Bar(4,4);
		bar.addRhythmEvent(textualNotation,subBeatDuration);
		return bar;
	}

	Bar bar(int nUnits, int measurementUnit, String textualNotation){
		Bar bar = new Bar(nUnits,measurementUnit);
		double subBeatDuration = (double)(1.0/measurementUnit);
		bar.addRhythmEvent(textualNotation,subBeatDuration);
		return bar;
	}

	Bar bar(int nUnits, int measurementUnit, String textualNotation, double subBeatDuration){
		Bar bar = new Bar(nUnits,measurementUnit);
		bar.addRhythmEvent(textualNotation,subBeatDuration);
		return bar;
	}

	//============================   RhythmPattern ===============================

	RhythmPattern rhythm(Bar... bars){
		RhythmPattern rp = new RhythmPattern();
		for (Bar b: bars){
			rp.insertBar(b);
		}
		return rp;	
	}

	RhythmPattern rhythm(String... barsTextualNotation){
		RhythmPattern rp = new RhythmPattern();
		for (String t: barsTextualNotation){
			rp.insertBar(bar(t));
		}
		return rp;	
	}

	RhythmREPL.Mark mark(String name){
		return RhythmREPL.mark(name);
	}

	RhythmREPL.ReturnPoint returnTo(String name, int repetitions){
		return RhythmREPL.returnTo(name,repetitions);
	}

	RhythmPattern rhythm(RhythmPattern.Things... rhythmThings){
		RhythmPattern rp = new RhythmPattern();
		for (RhythmPattern.Things item: rhythmThings){
			if(item instanceof RhythmREPL.Mark) rp.insertMark(((RhythmREPL.Mark)item).name);
			if(item instanceof Bar) rp.insertBar((Bar)item);
			if(item instanceof RhythmREPL.ReturnPoint) rp.insertReturn(
					((RhythmREPL.ReturnPoint)item).markName, 
					((RhythmREPL.ReturnPoint)item).repetitions); 		
		}
		return rp;	
	}

	RhythmPattern rhythm(String barTextualNotation, int repetitions){
		RhythmPattern rp = new RhythmPattern();
		for(int i = 0; i< repetitions; i++){
			rp.insertBar(bar(barTextualNotation));
		}

		return rp;	
	}
	RhythmPattern rhythm(Bar bar, int repetitions){
		RhythmPattern rp = new RhythmPattern();
		rp.insertMark("beginning");
		rp.insertBar(bar);
		rp.insertReturn("beginning",repetitions);
		return rp;	
	}

	RhythmPattern rhythm(Bar[] bars, int repetitions){
		RhythmPattern rp = new RhythmPattern();
		rp.insertMark("beginning");
		rp.insertBar(bars);
		rp.insertReturn("beginning",repetitions);
		return rp;	
	}


	//============================   Arpeggio ===============================

	Arpeggio arpeggio(String... barsTextualNotation){
		int nVoices = barsTextualNotation.length;
		Arpeggio gpr = new Arpeggio(nVoices);	      	     
		for(int i = 0; i<nVoices; i++){
			gpr.insertBar(bar(barsTextualNotation[i]),i);
		}

		return gpr;
	}
	Arpeggio arpeggio(Bar... bars){
		int nVoices = bars.length;
		Arpeggio arpeggio = new Arpeggio(nVoices);	      	     
		for(int i = 0; i<nVoices; i++){
			arpeggio.insertBar(bars[i],i);
		}

		return arpeggio;
	}
	Arpeggio arpeggio(RhythmPattern... patterns){
		return new Arpeggio(patterns);	      	     		 
	}


	//============================   Scale ===============================
	Scale scale(Note key, int mode) throws Exception{
		return Scale.getDiatonicScale(key,mode);
	}

	Scale scale(Note key, int[] semitones) throws Exception{
		return Scale.getScale(key,semitones);
	}


	//============================   Melody ===============================

	Melody melody (Note... notes){
		//System.out.println(notes.length);
		return new Melody(notes, RhythmPattern.getConstantRhythmPattern(notes.length, QUARTER_NOTE));
	}

	/*Melody melody(Note[] notes){     
		     return new Melody(notes, RhythmPattern.getConstantRhythmPattern(notes.length, QUARTER_NOTE));
		 }*/

	Melody melody(Note[] notes, double noteDuration){     
		return new Melody(notes, RhythmPattern.getConstantRhythmPattern(notes.length, noteDuration));
	}

	Melody melody(Note[] notes, RhythmPattern rhythmPattern){     
		return new Melody(notes, rhythmPattern);
	}

	//Fazer depois "C++F---F+F-"
	/*Melody melody (String melodyTextualNotation){

		 }*/


	//============================  Harmony & Harmonic Progression  ===============================
	Harmony harmony(HarmonicProgression harmonicprogression, Note key){
		return new Harmony(harmonicprogression.getChords(key));
	}

	Harmony harmony(Chord... chords){
		return new Harmony(chords);
	}

	Harmony harmony(Chord[] chords, RhythmPattern rhythm){
		return new Harmony(chords,rhythm);
	}

	Harmony harmony(Chord[] chords, RhythmPattern rhythm, Arpeggio arpeggio){
		return new Harmony(chords,rhythm,arpeggio);
	}

	Harmony harmony(String... chordsNames)throws Exception{
		Chord[] objChords = new Chord[chordsNames.length];
		for (int i=0; i<chordsNames.length; i++){
			objChords[i] = Chord.getChord(chordsNames[i]);
		}
		return new Harmony(objChords);
	}


	HarmonicProgression progression(String... scaleDegrees) throws Exception{
		HarmonicProgression harmonicprogression =  new  HarmonicProgression("no name");
		for (String degree: scaleDegrees){
			harmonicprogression.addScaleDegree(degree);
		}
		return harmonicprogression;
	}

	/*HarmonicProgression progression( HarmonicProgression.ScaleDegree... scaleDegrees) throws Exception{
	   HarmonicProgression harmonicprogression =  new  HarmonicProgression("no name");
	   for (HarmonicProgression.ScaleDegree degree: scaleDegrees){
	   	harmonicprogression.addScaleDegree(degree);
	   }
	   return harmonicprogression;
	 }*/

	//============================   Music  ===============================
	public Music music(Playable... playables) {		  
		return new Music(playables);
	}

	
	//============================   Testing - Do not copu to JSH File  ===============================	

	public static void main(String[] args) {
		try {
			//## USE JUST FOR QUICK TESTING...IF NEEDED MORE FANCY STUFF, GO TO TestingOctopusREPL
			OctopusShell s = new OctopusShell();
		
		s.midi(5);	
		s.channel(1);
		Melody m1 = s.melody(s.notes(s.A),s.rhythm("0000"));
		 
		 /*s.play(m1);
		 m1.setVolume(0.5f);
		 s.play(m1);*/
		 
		 Loop l1 = s.loop(m1);
		 s.play(l1);
		 
		 m1.setVolume(0.5f);
		 s.when(l1, s.STOPS, s.loop(m1));
		 
		 s.stop(l1);
		 
		 //l1.volume(1);
		 //s.play(l1);
		 System.out.println();
		 			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
