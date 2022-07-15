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
	public static final int LOOP_STOPS= 1; //provavelmente vai para a classe Loop
	//add more in the future.
     
	

	public LivePerformer() throws MusicPerformanceException {
		try {
			player = new LoopMidiSynthController();
		}
		catch (MidiUnavailableException ex) {
			throw new MusicPerformanceException("Octopus could not detect any available MIDI device.",ex);
		}
	}


	public LivePerformer(LoopMidiSynthController synthesizerController) {
		super(synthesizerController);
		// TODO Auto-generated constructor stub
	}
	
	public Loop createLoop(Playable playable)throws MusicPerformanceException{
		MusicalEventSequence p = playable.getMusicalEventSequence();  
		return ((LoopMidiSynthController)player).createLoop(p);
	}
	
   /*   @Override
	public void play(Playable playable) throws MusicPerformanceException {
		if(playable instanceof Loop) {
			((Loop)playable).start();
		}else {
			super.play(playable);
		}
	}*/
      
  	public void play(Loop loop) throws MusicPerformanceException {
			((Loop)loop).start();		
	}      


	//This method will allow statements such as :
	  // when(loop(0), LOOPS, loop(A,B,C));
	  public void loopWhen(Loop primaryLoop, int LOOP_EVENT, Loop secondaryLoop) throws MusicPerformanceException {
    	
    	switch (LOOP_EVENT) {
		case LOOP_LOOPS:			
			((LoopMidiSynthController)player).synchLoops(primaryLoop, secondaryLoop);
				
			break;
		case LOOP_STOPS:			
			//loop(primaryLoop,playable);			
			break;

		}
    
     }
	  
	public Loop getLoop(int controllerLoopID) {
		return ((LoopMidiSynthController)player).getLoop(controllerLoopID);
	}
	
	public void stop(Loop loop) {
		loop.stop();				
	}
	
	public void stopAll() {
		((LoopMidiSynthController)player).stopAll();		
	}



}
