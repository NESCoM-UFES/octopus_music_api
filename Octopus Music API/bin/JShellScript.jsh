import octopus.*;
import octopus.communication.*;
import octopus.communication.midi.*;
import examples.*;

//MIDI

void midiSystem(){
   OctopusMidiSystem.listDevices(midi_in, midi_in, true);
}

void midiSystem(boolean midi_in, boolean midi_out){
   OctopusMidiSystem.listDevices(midi_in, midi_in, true);
}

LiveMidiSynthesizerController synthController;
synthController = new LiveMidiSynthesizerController("loopMIDI Port");
Musician musician = new Musician(synthController);

 void play(Playable p) throws MusicPerformanceException{
     musician.play(p);
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
 

 
 
 

 




/exit
