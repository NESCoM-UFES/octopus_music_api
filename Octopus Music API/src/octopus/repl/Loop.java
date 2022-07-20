package octopus.repl;

import octopus.Playable;
import octopus.communication.MusicalEventSequence;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import octopus.Musician;
import octopus.Note;
import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;
import octopus.communication.SynthesizerController;
import octopus.communication.midi.LiveMidiSynthesizerController;
import octopus.communication.midi.OctopusMidiSystem;

public class Loop   {//implements Playable {
   
	static final int STATUS_CREATED = 0; // created, open, and ready to be used. No sequence has been assigned to it yet.
	static final int STATUS_STOPPED = 1; // already played but now is free to play another loop.
	static final int STATUS_PLAYING = 2; // is active
	static final int STATUS_PAUSED = 3; // is paused...can be resumed at any time so not available.
	static final int STATUS_WAITING = 4;// is on the eminence to start playing..waiting some other sequence to change its state.
	
	
	
	
	private final Sequencer sequencer;
	private final MusicalEventSequence musicalEventSequence; // ou deveria ja ser um MusicalEventSequence?
	private int status;
	public final int controllerLoopID;
	
	
	private PropertyChangeSupport support;


    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

   
	public Loop(int controllerLoopID, Sequencer sequencer, MusicalEventSequence musicalEventSequence) {
		this.sequencer = sequencer;
		this.status = this.STATUS_CREATED;
		this.musicalEventSequence = musicalEventSequence;
		this.controllerLoopID = controllerLoopID;
		
		support = new PropertyChangeSupport(this);
	}
	
	
	public void start() {
		sequencer.start();
		status = Loop.STATUS_PLAYING;
		support.firePropertyChange("status",this.STATUS_WAITING,this.STATUS_PLAYING);
	}
	
	public void stop() {
		sequencer.stop();
		status = Loop.STATUS_STOPPED;
		support.firePropertyChange("status",this.STATUS_PLAYING,this.STATUS_STOPPED);
	}
	
	
	public void setBpm(float bpm) {
		float tempoFactor = bpm/120;		
		sequencer.setTempoFactor(tempoFactor);
		
	}
	

		
	
	 //Não funcionou legal com loops em sincronia. Eles saem de sincronia.
	/*public void mute(boolean isMute) {
		sequencer.setTrackMute(STATUS_CREATED, isMute);
	}*/
			
	//method used to indicate that the loop set to start soon (next loop)
	public void arm() {
		status = Loop.STATUS_WAITING;
		support.firePropertyChange("status",this.STATUS_CREATED,this.STATUS_WAITING);
	}
	
	public int getStatus() {
		return status;
	}
	
	/*public MusicalEventSequence getMusicalEventSequence() {
		
		return musicalEventSequence;
	}*/

}
