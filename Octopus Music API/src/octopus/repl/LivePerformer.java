package octopus.repl;

import javax.sound.midi.MidiUnavailableException;

import octopus.MusicPerformanceException;
import octopus.Musician;
import octopus.Playable;
import octopus.communication.MusicalEventSequence;
import octopus.communication.SynthesizerController;
import octopus.communication.midi.MidiSynthesizerController;

public class LivePerformer extends Musician {


	public LivePerformer() throws MusicPerformanceException {
		try {
			player = new LoopMidiController();
		}
		catch (MidiUnavailableException ex) {
			throw new MusicPerformanceException("Octopus could not detect any available MIDI device.",ex);
		}
	}


	public LivePerformer(LoopMidiController synthesizerController) {
		super(synthesizerController);
		// TODO Auto-generated constructor stub
	}

	public int loop(Playable playable)throws MusicPerformanceException{
		MusicalEventSequence p = playable.getMusicalEventSequence();  
		return ((LoopMidiController)player).loop(p);
	}
	
	public void stop(int loopIndex) {
		((LoopMidiController)player).stop(loopIndex);		
	}
	
	public void stopAll() {
		((LoopMidiController)player).stopAll();		
	}



}
