package octopus.repl;

import octopus.Playable;
import octopus.communication.MusicalEventSequence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import octopus.MusicPerformanceException;
import octopus.Note;
import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;
import octopus.communication.SynthesizerController;
import octopus.communication.midi.LiveMidiSynthesizerController;
import octopus.communication.midi.OctopusMidiSystem;

public class Loop implements Playable {
   
	static final int STATUS_CREATED = 0; // created, open, and ready to be used. No sequence has been assigned to it
	// yet.
	static final int STATUS_STOPPED = 1; // already played but now is free to play another loop.
	static final int STATUS_PLAYING = 2; // is active
	static final int STATUS_PAUSED = 3; // is paused...can be resumed at any time so not available.
	static final int STATUS_WAITING = 4;// is on the eminence to start playing..waiting some other sequence to change
	// its state.
	
	Sequencer sequencer;
	Playable playable; // ou deveria ja ser um MusicalEventSequence?
	int status;
	
	public Loop(Sequencer sequencer) {
		this.sequencer = sequencer;
		this.status = this.STATUS_CREATED;
		
	}
	
	
	public MusicalEventSequence getMusicalEventSequence() {
		// TODO Auto-generated method stub
		return null;
	}

}