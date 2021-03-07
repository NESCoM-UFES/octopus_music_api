import javax.sound.midi.MidiUnavailableException;

import octopus.*;
import octopus.communication.*;
import octopus.communication.midi.*;

public class LiveOctopus {

	public static void main(String[] args) {
		try {		
			Note A = Notes.getA();
			Note B = Notes.getB();		

			LiveMidiSynthesizerController synthController;

			synthController = new LiveMidiSynthesizerController("loopMIDI Port");

			Musician musician = new Musician(synthController); 

		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
