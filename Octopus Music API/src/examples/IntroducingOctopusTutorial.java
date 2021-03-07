package examples;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

import octopus.Arpeggio;
import octopus.Bar;
import octopus.Chord;
import octopus.ChordException;
import octopus.ChordNotationException;
import octopus.Harmony;
import octopus.Musician;
import octopus.NoteException;
import octopus.RhythmPattern;
import octopus.communication.midi.MidiSynthesizerController;
import octopus.communication.midi.OctopusMidiSystem;
import octopus.instrument.fretted.Guitar;
import octopus.instrument.fretted.Guitarist;

public class IntroducingOctopusTutorial {

	
	/*
	 * This method will return a Musician connected to an MIDI output port that
	 * a sampler software(Reason) is listening. 
	 * To make it work, you may have to change the device index.
	 */
	public static Musician getMusicianReason() throws MidiUnavailableException{
		/*
		 * Uncomment the following line to see what midi devices are available at your computer.
		 */
		
		//OctopusMidiSystem.listDevicesAndExit(true,  true, true);
		MidiDevice[] devices = OctopusMidiSystem.getDevices(false, true);
		for (int i = 0; i < devices.length; i++) {
			System.out.println(devices[i].getDeviceInfo().getName());
		}
		
		MidiSynthesizerController controller = new MidiSynthesizerController(devices[0]);
		System.out.println("Selected device: " + controller.outputDevice.getDeviceInfo().getName());
		Musician m = new Musician(controller);
		
		m.setPlayingSpeed(120);

		return m;
	}


	public static Arpeggio getNiceArpeggio(){
	    Arpeggio gpr = new Arpeggio(5);
	      gpr.setName("Bossa");
          Bar bass = new Bar(4,4);
        //  bass.addSingleRhythmEvent(1, 1, 0);
	   //   gpr.insertBar(bass,1);
	      Bar[] bars = new Bar[4];
	      for(int i = 0; i<4; i++){
	        bars[i] = new Bar(4,8);
	        if(i%2==0){
		        bars[i].addSingleRhythmEvent(Bar.EIGHT_NOTE,Bar.RHYTHM_EVENT_NOTE,i);
	        }
	        gpr.insertBar(bars[i],(i+1));
	      }

	     return gpr;
	}
	
	
	public static  Harmony getNiceHarmony() throws ChordNotationException, NoteException, ChordException{
		
	
		
		String[] chordNames = { "D7M(9)", "E7(9)", "Em7(9)",  "A7(add13)", "F#m7(b5)", "B7(b9)",
				   "Em7(9)", "F#7(b13)","B7M","B7(b9)", "E7(9)", "Eb7M/G", "Eb7M"};

		Chord[] chords = new Chord[chordNames.length];
		for (int i = 0; i < chordNames.length; i++) {
			chords[i] = Chord.getChord(chordNames[i]);
			System.out.println(chords[i]);
		}
			
		
		RhythmPattern rhythm = new RhythmPattern(4,4,3);
		
		
		
		//return new Harmony(chords, rhythm, IntroducingOctopusTutorial.getNiceArpeggio());
		return new Harmony(chords, rhythm);
		
		
	}
	
	
	public static void main(String[] args) {
		try {

			//OctopusMidiSystem.listDevicesAndExit(true, true, true);
			
			//Musician musician = IntroducingOctopusTutorial.getMusicianReason();

		    // Musician musician = new Musician();
			
		
		//	musician.play(IntroducingOctopusTutorial.getNiceHarmony());
			
			/*MidiDevice[] devices = OctopusMidiSystem.getDevices(false, true);
			MidiDevice selectedDevice = devices[0];
			System.out.println("Selected: " + selectedDevice.getDeviceInfo().getName());
			MidiSynthesizerController controller = new MidiSynthesizerController(selectedDevice);
			Guitarist guitarist = new Guitarist(new Guitar("Nylon"),controller);*/
			
			//===== Block Guitarrist Test ======
			//Musician musician  = IntroducingOctopusTutorial.getMusicianReason();
			//musician.play(PlayableThings.getMusicToPlay());
		
			
			//===== Block Guitarrist Test ======
			Guitarist guitarist = new Guitarist(new Guitar("Nylon"));
			guitarist.showInstrumentLayout();
			
			guitarist.play(IntroducingOctopusTutorial.getNiceHarmony());
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 System.exit(0);

	}

}
