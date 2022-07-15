package octopus.communication.midi;




import java.util.*;

import javax.sound.midi.*;
import octopus.*;
import octopus.communication.*;
/**
 *
 * @author Leandro Costalonga
 * @version 1.0
 */
public class LiveMidiSynthesizerController  implements SynthesizerController {
	public MidiDevice outputDevice=null;
	protected String outputDeviceName;
	protected Receiver synthReceiver=null;
	protected Sequencer sequencer=null; 
	protected Transmitter seqTransmitter=null;

	protected int midiProgram = 1;
	protected float bpm = 120;
	
	public static int defaultChannel = 1; //para teste

	//protected boolean isLoop = false;

	protected Vector<Receiver> receivers;

	protected static final int MAX_PARAMETER_VALUE = 127;
	
	/**the END_OF_BLOCK MetaMessage is necessary as a placeholder for silence.
	 Whenever the the SynthController (this) finds a Octopus.MusivalEvent without
	 a note associate to it, the controller inserts a MetaMessage to mark the 
	 END_OF_BLOCK (or the MusicalSequence to be more precise)*/	
	protected static final int END_OF_BLOCK = 0;

	/*public LiveMidiSynthesizerController() throws MidiUnavailableException {

		//outputDevice = MidiSystem.getSynthesizer();
		//sequencer = MidiSystem.getSequencer();
		receivers = new Vector<Receiver>();

		//===
		sequencer = MidiSystem.getSequencer();
		sequencer.open();
		if(outputDevice==null){ //
			outputDevice = MidiSystem.getSynthesizer();
		}else{//Using external MIDI Port
			//Remove the vitual java synthesizer (bug of Java Sound)
			List list = sequencer.getTransmitters();
			((Transmitter)list.get(0)).close();
		}

		outputDevice.open();
		synthReceiver = outputDevice.getReceiver();

		//Setting up the connections
		seqTransmitter = sequencer.getTransmitter();
		seqTransmitter.setReceiver(synthReceiver);
		for (int i = 0; i < receivers.size(); i++) {
			seqTransmitter = sequencer.getTransmitter();
			seqTransmitter.setReceiver( receivers.get(i));
		}

	}*/


	/*public LiveMidiSynthesizerController(String midiOut) throws MidiUnavailableException {

		outputDevice = OctopusMidiSystem.getMidiDevice(midiOut);
		outputDeviceName = midiOut;

		receivers = new Vector<Receiver>();

		//===
		sequencer = MidiSystem.getSequencer(); //very slow method.
		sequencer.open();
		if(outputDevice==null){ //
			outputDevice = MidiSystem.getSynthesizer();
		}else{//Using external MIDI Port
			//Remove the vitual java synthesizer (bug of Java Sound)
			List list = sequencer.getTransmitters();
			((Transmitter)list.get(0)).close();
		}

		outputDevice.open();
		synthReceiver = outputDevice.getReceiver();

		//Setting up the connections
		seqTransmitter = sequencer.getTransmitter();
		seqTransmitter.setReceiver(synthReceiver);
		for (int i = 0; i < receivers.size(); i++) {
			seqTransmitter = sequencer.getTransmitter();
			seqTransmitter.setReceiver( receivers.get(i));
		}

	}*/
	

	public LiveMidiSynthesizerController() throws MidiUnavailableException {
		outputDevice = MidiSystem.getSynthesizer();
		outputDeviceName = outputDevice.getDeviceInfo().getName();
		sequencer = MidiSystem.getSequencer(); //very slow method.
		sequencer.open();
		rewireSequencer(sequencer, outputDeviceName);

		
	}
	
	public LiveMidiSynthesizerController(String midiOut) throws MidiUnavailableException {
		outputDevice = OctopusMidiSystem.getMidiDevice(midiOut);
		outputDeviceName = midiOut;
		sequencer = MidiSystem.getSequencer(); //very slow method.
		sequencer.open();
		rewireSequencer(sequencer, midiOut);

		
	}
	
	//Change the transmitters/receivers connection without creating a new sequencer...wich is pretty slow and
	//memory intensive.
	public void setMidiOut(String midiOut) throws MidiUnavailableException {
		outputDevice = OctopusMidiSystem.getMidiDevice(midiOut);
		outputDeviceName = midiOut;
		
		if (sequencer != null) {
			if (sequencer.isRunning()) sequencer.stop();			
		}
		
		rewireSequencer(sequencer, midiOut);
	}
	

	protected final void rewireSequencer(Sequencer sequencer, String midiOut) throws MidiUnavailableException {
	 	
		
		/*Transmitter mainSequencerTransmitter = this.sequencer.getTransmitter();
		mainSequencerTransmitter.setReceiver(sequencer.getReceiver());
		sequencer.setSlaveSyncMode(Sequencer.SyncMode.MIDI_SYNC);*/
		
		
		//MidiDevice outputDevice= OctopusMidiSystem.getMidiDevice(midiOut);		
		
		if(outputDevice==null){ //
			outputDevice = MidiSystem.getSynthesizer();
		}else{//Using external MIDI Port
			//Remove the virtual java synthesizer (bug of Java Sound)
			List list = sequencer.getTransmitters();
			((Transmitter)list.get(0)).close();
		}
	    if (!outputDevice.isOpen())	outputDevice.open();
	   
	   Receiver synthReceiver = outputDevice.getReceiver();

	   //Setting up the connections
	   Transmitter seqTransmitter = sequencer.getTransmitter();
	   seqTransmitter.setReceiver(synthReceiver);
	   
	  /* for (int i = 0; i < receivers.size(); i++) {
		   seqTransmitter = sequencer.getTransmitter();
		   seqTransmitter.setReceiver( receivers.get(i));
	   }*/
		 
	
	}


	public void closeDevices() throws MidiUnavailableException {

		//closing in the reverse order of openning
		seqTransmitter.close();
		sequencer.close();
		synthReceiver.close();
		outputDevice.close();
	}

	/*Esse método parece bem defasado. Será que alguem usa? Vou*/
	@Override
	public void play(MusicalEvent musicalEvent) throws MusicPerformanceException{
		try {

			int midiProgram = 1;
			if (musicalEvent.get("midiProgram") != null) {
				midiProgram = Integer.parseInt( ( (String) musicalEvent.get(
						"midiProgram")));
			}

			int channel = 0;
			if (musicalEvent.get("channel") != null) {
				channel = Integer.parseInt( ( (String) musicalEvent.get("channel")));
			}
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.getChannels()[channel].programChange(midiProgram);
			int velocity = (int) (MAX_PARAMETER_VALUE * musicalEvent.velocity);
			synth.getChannels()[channel].noteOn(musicalEvent.note.getMidiValue(), velocity);
			//o noteOff do synth não pode ser escalonado para a duração.
		}
		catch (MidiUnavailableException ex) {
			throw new octopus.MusicPerformanceException(ex.getMessage(), ex);
		}

	}

	public void play(MusicalEventSequence musicalStructure) throws MusicPerformanceException
	{

		try {
			//int resolution = 4;
			int resolution = 96;

			Sequence s = new Sequence(Sequence.PPQ, resolution);

			Track track = s.createTrack();
			MusicalEvent[] musicalEvents = musicalStructure.getMusicalEvents();

			for (int i = 0; i < musicalEvents.length; i++) {	        	 	        	  
				Note note = musicalEvents[i].note;
				MidiMessage msg;
				if(note !=null){ 		            	            	
					msg = new ShortMessage();
					if (musicalEvents[i].velocity > 0) {		                       		            	
						int velocity = (int) (MAX_PARAMETER_VALUE * musicalEvents[i].velocity);
						((ShortMessage)msg).setMessage(ShortMessage.NOTE_ON, defaultChannel, note.getMidiValue(), velocity);
					}else {
						((ShortMessage)msg).setMessage(ShortMessage.NOTE_OFF, defaultChannel,note.getMidiValue(), 0);
					}

				}else {
					msg = new MetaMessage(this.END_OF_BLOCK,null,0);	        	
				}
				double timing = (musicalEvents[i].timing * 4.0) * resolution;
				MidiEvent me = new MidiEvent(msg, (int) timing);
				track.add(me);
			}

			//== ATTENTION == DONT TRY TO PLACE THIS CODE INTO A PRIVATE METHOD!!
			// IT WILL NOT WORK. NEED TO RUN IMEDIATELLY THE Sequencer.start()


			//Playing inicialization
			sequencer.setSequence(s);
			sequencer.setTempoInBPM(bpm);                    		
			sequencer.start();


		}
		catch (InvalidMidiDataException ex1) {
			throw new octopus.MusicPerformanceException(ex1.getMessage(),ex1);
		}

	}




	@SuppressWarnings("unchecked")
	/*
	 * public void play(MusicalEventSequence musicalStructure) throws
	 * MusicPerformanceException {
	 * 
	 * try {
	 * 
	 * //int resolution = 4; int resolution = 96;
	 * 
	 * Sequence s = new Sequence(Sequence.PPQ, resolution);
	 * 
	 * Track track = s.createTrack(); MusicalEvent[] musicalEvents =
	 * musicalStructure.getMusicalEvents(); // track.add(new
	 * ShortMessage().setMessage(ShortMessage.PROGRAM_CHANGE);) for (int i = 0; i <
	 * musicalEvents.length; i++) { ShortMessage msg = new ShortMessage(); //
	 * MetaMessage metaMsg = new MetaMessage( Note note = musicalEvents[i].note;
	 * if(note ==null){ //Pauses are not inserted..instead, notes as escalonated in
	 * time. throw new
	 * MusicPerformanceException("Number of rhythmic events must be equal to number of notes."
	 * ); } if (musicalEvents[i].velocity > 0) {
	 * 
	 * 
	 * int velocity = (int) (MAX_PARAMETER_VALUE * musicalEvents[i].velocity);
	 * msg.setMessage(ShortMessage.NOTE_ON, note.getMidiValue(), velocity); } else {
	 * msg.setMessage(ShortMessage.NOTE_OFF, note.getMidiValue(), 0); }
	 * 
	 * double timing = (musicalEvents[i].timing * 4.0) * resolution; MidiEvent me =
	 * new MidiEvent(msg, (int) timing); track.add(me); }
	 * 
	 * //== ATTENTION == DONT TRY TO PLACE THIS CODE INTO A PRIVATE METHOD!! // IT
	 * WILL NOT WORK. NEED TO RUN IMEDIATELLY THE Sequencer.start() //Getting the
	 * virtual resources. Dont need to get the external // midi port (midiOut)
	 * because it was created on the super. // sequencer =
	 * MidiSystem.getSequencer(); // sequencer.open(); // if(outputDevice==null){ //
	 * // outputDevice = MidiSystem.getSynthesizer(); // }else{//Using external MIDI
	 * Port // //Remove the vitual java synthesizer (bug of Java Sound) // List list
	 * = sequencer.getTransmitters(); // ((Transmitter)list.get(0)).close(); // } //
	 * // outputDevice.open(); // synthReceiver = outputDevice.getReceiver(); // //
	 * //Setting up the connections // seqTransmitter = sequencer.getTransmitter();
	 * // seqTransmitter.setReceiver(synthReceiver); // for (int i = 0; i <
	 * receivers.size(); i++) { // seqTransmitter = sequencer.getTransmitter(); //
	 * seqTransmitter.setReceiver( (Receiver) receivers.get(i)); // }
	 * 
	 * //Playing inicialization sequencer.setSequence(s);
	 * sequencer.setTempoInBPM(bpm); if(this.isLoop) {
	 * sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY); }
	 * 
	 * sequencer.start();
	 * 
	 * 
	 * 
	 * //Release resourses after end of playing // while(sequencer.isRunning()){} //
	 * closeDevices(); //System.out.println("devices closed");
	 * 
	 * } catch (InvalidMidiDataException ex1) { throw new
	 * octopus.MusicPerformanceException(ex1.getMessage(),ex1); }
	 * 
	 * }
	 * 
	 */


	@Override
	public void stop(){
		try {
			if(sequencer!=null){
				if( sequencer.isRunning()){
					sequencer.stop();
				}
			}
			//closeDevices();

		}catch (Exception ex) {
			//nothing: At this point is not important recognize the hardware;
		}
	}

	/*public static void main(String[] args) {
    try {
      MidiSynthesizerController p = new MidiSynthesizerController();
      Musician m = new Musician();

      MusicalEvent me = new MusicalEvent(0, 0, Notes.getA(), RhythmConstants.QUARTER_NOTE,
                                         127);

      // p.play(me);
      Chord chord = new Chord("D#m7(9)");
      //m.play(chord, 90, 30, 10);
     // PlayableMusic music = m.generate(chord, 90, 10, 10);
      //m.play(NoteCollection.getA(), 127);
     // p.play(music);

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }*/

	public void setBPM(float bpm) {
		this.bpm = bpm;
		sequencer.setTempoInBPM(bpm);
	}


	
	/*public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;

	}*/
}
