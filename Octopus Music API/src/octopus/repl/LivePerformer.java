package octopus.repl;

import javax.sound.midi.MidiUnavailableException;

import octopus.MusicPerformanceException;
import octopus.Musician;
import octopus.Playable;
import octopus.communication.MusicalEventSequence;
import octopus.communication.SynthesizerController;
import octopus.communication.midi.MidiSynthesizerController;

public class LivePerformer extends Musician {
	
	public static final int LOOP_LOOPS = 0;
	public static final int LOOP_STOPS= 1;
	//add more in the future.
     

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

		

	public int loop(int primaryLoop, Playable playable)throws MusicPerformanceException{
		MusicalEventSequence p = playable.getMusicalEventSequence();  
		return ((LoopMidiController)player).loop(primaryLoop,p);
	}
	
	public int loop(Playable playable)throws MusicPerformanceException{
		MusicalEventSequence p = playable.getMusicalEventSequence();  
				
		return ((LoopMidiController)player).loop(p);
	}
	
	 /* public int loopWhen(int primaryLoop, int LOOP_EVENT, int secondaryLoop) throws MusicPerformanceException {
		  
	  }*/
	
      //This method will allow statements such as :
	  // when(loop(0), LOOPS, loop(A,B,C));
	  public void loopWhen(int primaryLoop, int LOOP_EVENT, int secondaryLoop) throws MusicPerformanceException {
    	
    	switch (LOOP_EVENT) {
		case LOOP_LOOPS:			
			((LoopMidiController)player).synchLoops(primaryLoop, secondaryLoop);
				
			break;
		case LOOP_STOPS:			
			//loop(primaryLoop,playable);			
			break;

		}
    
     }
	
	public void stop(int loopIndex) {
		((LoopMidiController)player).stop(loopIndex);		
	}
	
	public void stopAll() {
		((LoopMidiController)player).stopAll();		
	}



}
