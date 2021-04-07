import octopus.*;
import octopus.repl.*;
import octopus.communication.*;
import octopus.communication.midi.*;
import examples.*;

import java.util.concurrent.TimeUnit

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



//util
void pause(int milliseconds) throws Exception{
    TimeUnit.MILLISECONDS.sleep(milliseconds);
}

double random(){
   return Math.random();		
}

int random(int min, int max){
    return (int)((Math.random()*max) + min);
}

//Library

Library lib = new Library();

//MIDI

LiveMidiSynthesizerController synthController;
synthController = new LiveMidiSynthesizerController();
Musician musician = new Musician(synthController);


void midi(){
   OctopusMidiSystem.listDevices(true, true, true);
}

void midi(boolean midiIn, boolean midiOut){
   OctopusMidiSystem.listDevices(midiIn, midiOut, true);
}

/** Set midi out port to the current Musician. */
void midi(String midiOutPortName) throws Exception{
   if(synthController!=null)  synthController.closeDevices();
   
   synthController = new LiveMidiSynthesizerController(midiOutPortName);
   musician.setSynthesizerController(synthController);
}

/** Set midi out port to the current Musician. */
void midi(int  midiOut) throws Exception{   
   String midiOutPortName = OctopusMidiSystem.getMidiDeviceInfo(midiOut).getName();
   if(synthController!=null)  synthController.closeDevices();
   
   synthController = new LiveMidiSynthesizerController(midiOutPortName);
   musician.setSynthesizerController(synthController);
}
//Interpreters

 void play(Playable p) throws MusicPerformanceException{
     musician.play(p);
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


//Playables Note

Note A = Notes.getA();
Note B = Notes.getB();
Note C = Notes.getC();
Note D = Notes.getD();
Note E = Notes.getE();
Note F = Notes.getF();
Note G = Notes.getG();

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
 
 
 //chord
 
 Chord chord(String chordName) throws Exception{
   Chord chord = Chord.getChord(chordName);
   return chord;
 }
 
 //Scale
 Scale scale(Note key, int mode) throws Exception{
    return Scale.getDiatonicScale(key,mode);
 }
 
 Scale scale(Note key, int[] semitones) throws Exception{
    return Scale.getScale(key,semitones);
 }
  
  
final ScaleREPL scale = new ScaleREPL();

// notes() method
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

Note[] notes(String[] noteSymbols) throws Exception{
	return Notes.getNotes(noteSymbols);
}

Note[] notes(String... noteSymbols) throws Exception{
	return Notes.getNotes(noteSymbols);
}

Note[] notes(Note... notes) throws Exception{
	return notes;
}
 
/** Utilitarian methods over array of notes**/
NotesREPL notes = new  NotesREPL();
 
Note[] transpose(Note[] notes, int semitones) throws Exception{
    return Notes.transpose(notes,semitones);
 }
  
Note[] suffle(Note[] notes){
  return Notes.suffle(notes);
}   

Note[] suffle(Note[] notes, int noNotes){
  return Notes.suffle(notes, noNotes);
}   
 
 
 //Melody
 
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
 //Rhythm
 
 BarREPL barREL(String textualNotation){
      return BarREPL.bar(textualNotation);
 }

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
 RhythmPattern rhythm(Bar[] bars, int repetitions){
 	RhythmPattern rp = new RhythmPattern();
 	rp.insertMark("beginning");
 	rp.insertBar(bars);
    rp.insertReturn("beginning",repetitions);
 	return rp;	
 }


 
 