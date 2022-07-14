package octopus.repl;

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

public class LoopMidiSynthController extends LiveMidiSynthesizerController {
	private final int DEFAULT_LOOPS_COUNT = 4;

	private final int END_OF_LOOP = 1;

	static final int STATUS_CREATED = 0; // created, open, and ready to be used. No sequence has been assigned to it
	// yet.
	static final int STATUS_STOPPED = 1; // already played but now is free to play another loop.
	static final int STATUS_PLAYING = 2; // is active
	static final int STATUS_PAUSED = 3; // is paused...can be resumed at any time so not available.
	static final int STATUS_WAITING = 4;// is on the eminence to start playing..waiting some other sequence to change
	// its state.

	// private final int END_OF_BLOCK = 0; //Usado para garantir as figuras de
	// silencio após a última nota soar na barra/ritmo.

	private final Sequencer[] loops;
	private final GearBox gearBox; // gearBox is the metaphor for the looping (gears) mechanism proposed.
	private final int[] loopStatus;

	// String midiOut;

	public LoopMidiSynthController() throws MidiUnavailableException {
		// this.midiOut = MidiSystem.getSynthesizer().getDeviceInfo().getName();
		loops = new Sequencer[DEFAULT_LOOPS_COUNT];
		loopStatus = new int[DEFAULT_LOOPS_COUNT];
		gearBox = new GearBox(DEFAULT_LOOPS_COUNT);
		createLoops(DEFAULT_LOOPS_COUNT);
	}

	public LoopMidiSynthController(String midiOut, int numberLoops) throws MidiUnavailableException {
		super(midiOut);
		loops = new Sequencer[numberLoops];
		loopStatus = new int[numberLoops];
		gearBox = new GearBox(numberLoops);
		createLoops(numberLoops);
	}

	public LoopMidiSynthController(String midiOutPortName) throws MidiUnavailableException {
		super(midiOutPortName);
		loops = new Sequencer[DEFAULT_LOOPS_COUNT];
		loopStatus = new int[DEFAULT_LOOPS_COUNT];
		gearBox = new GearBox(DEFAULT_LOOPS_COUNT);
		createLoops(DEFAULT_LOOPS_COUNT);
	}

	// Change the transmitters/receivers connection without creating a new
	// sequencer...wich is pretty slow and
	// memory intensive.
	public void setMidiOut(String midiOut) throws MidiUnavailableException {
		super.setMidiOut(midiOut);

		for (int i = 0; i < loops.length; i++) {
			if (loops[i] != null) {
				if (loops[i].isRunning())
					loops[i].stop();
				loopStatus[i] = STATUS_STOPPED;
			}
			rewireSequencer(loops[i], this.outputDeviceName);

		}
	}

	/**
	 * @todo 1)definir sincronia com main sequencer do pai.
	 * @todo 2)Lançar evento quando lupar e tentar sincronizar
	 * @todo 3) parar em série @todo) tentar o triger ou play
	 * 
	 * 
	 */
	/*
	 * private final void rewireSequencer(Sequencer sequencer, String midiOut)
	 * throws MidiUnavailableException {
	 * 
	 * 
	 * Transmitter mainSequencerTransmitter = this.sequencer.getTransmitter();
	 * mainSequencerTransmitter.setReceiver(sequencer.getReceiver());
	 * sequencer.setSlaveSyncMode(Sequencer.SyncMode.MIDI_SYNC);
	 * 
	 * /// sequencer.addMetaEventListener(new MetaEventListener( ) { public void
	 * meta(MetaMessage m) { if (m.getType( ) == END_OF_TRACK) {
	 * System.out.println("end of track"); return; } } }); ////
	 * 
	 * 
	 * MidiDevice outputDevice= OctopusMidiSystem.getMidiDevice(midiOut);
	 * 
	 * if(outputDevice==null){ // outputDevice = MidiSystem.getSynthesizer();
	 * }else{//Using external MIDI Port //Remove the virtual java synthesizer (bug
	 * of Java Sound) List list = sequencer.getTransmitters();
	 * ((Transmitter)list.get(0)).close(); } if (!outputDevice.isOpen())
	 * outputDevice.open();
	 * 
	 * Receiver synthReceiver = outputDevice.getReceiver();
	 * 
	 * //Setting up the connections Transmitter seqTransmitter =
	 * sequencer.getTransmitter(); seqTransmitter.setReceiver(synthReceiver);
	 * 
	 * for (int i = 0; i < receivers.size(); i++) { seqTransmitter =
	 * sequencer.getTransmitter(); seqTransmitter.setReceiver( receivers.get(i)); }
	 * 
	 * 
	 * }
	 */

	private final void createLoops(int numberSequencers) throws MidiUnavailableException {

		for (int i = 0; i < numberSequencers; i++) {
			if (loops[i] == null) {
				loops[i] = MidiSystem.getSequencer();
				loops[i].open();
				loops[i].setLoopCount(loops[i].LOOP_CONTINUOUSLY);

				loops[i].addMetaEventListener(new MetaEventListener() {
					public void meta(MetaMessage m) {
						if (m.getType() == END_OF_LOOP) {
							int loopIdex = m.getData()[0];
							startPendingSequencers(loopIdex);
							return;
						}
					}
				});

			}
			rewireSequencer(loops[i], this.outputDeviceName);
			loopStatus[i] = STATUS_CREATED;
		}
	}

	private void startPendingSequencers(int loopIndex) {
		ArrayList<Integer> pendingLoops = gearBox.getEngagedLoops(loopIndex);
		for (Integer pendindLoopIndex : pendingLoops) {
			if (loopStatus[pendindLoopIndex] == STATUS_WAITING) {
				loops[pendindLoopIndex].start();
				loopStatus[pendindLoopIndex] = STATUS_PLAYING;
			}
		}
	}

	private int getIndexFreeSequencer() {
		for (int i = 0; i < loopStatus.length; i++) {
			if ((loopStatus[i] == STATUS_CREATED) || (loopStatus[i] == STATUS_STOPPED)) {
				loopStatus[i] = STATUS_WAITING;
				return i;
			}
		}
		return -1;
	}

	private Sequence stampMetaMessage(Sequence sequence, int loopIndex) throws InvalidMidiDataException {
		Track track = sequence.getTracks()[0];

		byte[] data = { (byte)loopIndex };
		MetaMessage msg = new MetaMessage(END_OF_LOOP, data, data.length);

		MidiEvent me = new MidiEvent(msg, sequence.getTickLength());
		track.add(me);

		return sequence;
	}

	protected final Sequence getSequence(MusicalEventSequence musicalStructure) throws InvalidMidiDataException {

		int resolution = 96;

		Sequence s = new Sequence(Sequence.PPQ, resolution);

		Track track = s.createTrack();
		MusicalEvent[] musicalEvents = musicalStructure.getMusicalEvents();

		for (int i = 0; i < musicalEvents.length; i++) {
			Note note = musicalEvents[i].note;
			MidiMessage msg;
			if (note != null) {
				msg = new ShortMessage();
				if (musicalEvents[i].velocity > 0) {
					int velocity = (int) (MAX_PARAMETER_VALUE * musicalEvents[i].velocity);
					((ShortMessage) msg).setMessage(ShortMessage.NOTE_ON, note.getMidiValue(), velocity);
				} else {
					((ShortMessage) msg).setMessage(ShortMessage.NOTE_OFF, note.getMidiValue(), 0);
				}

			} else {
				msg = new MetaMessage(END_OF_BLOCK, null, 0);
			}
			double timing = (musicalEvents[i].timing * 4.0) * resolution;
			MidiEvent me = new MidiEvent(msg, (int) timing);
			track.add(me);
		}

		return s;
	}


	public int registerLoop(MusicalEventSequence musicalStructure) throws MusicPerformanceException {
		try {
			int i = getIndexFreeSequencer();

			if (i > -1) {
				Sequence s;

				s = getSequence(musicalStructure);

				s = stampMetaMessage(s,  i);

				loops[i].setSequence(s);
				loops[i].setTempoInBPM(bpm);
				loopStatus[i] = STATUS_CREATED;
				
				return i;

			} else {
				throw new octopus.MusicPerformanceException("No sequencer available to loop. Try add more.");
			}
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public int loop(int primaryLoopIndex, MusicalEventSequence musicalStructure) throws MusicPerformanceException {
		int i = registerLoop(musicalStructure);		
		synchLoops (primaryLoopIndex,i);

		return i;
		
	}
	
	public void synchLoops(int primaryLoop, int secondaryLoop) {
		gearBox.engage(primaryLoop,  secondaryLoop);
		loopStatus[secondaryLoop] = STATUS_WAITING;				
	}
	

	public int loop(MusicalEventSequence musicalStructure) throws MusicPerformanceException {

		int i = registerLoop(musicalStructure);
		loops[i].start();
		loopStatus[i] = STATUS_PLAYING;

		return i;

	}


	// Immediately start looping
	/*
	 * public int loop(MusicalEventSequence musicalStructure) throws
	 * MusicPerformanceException { try {
	 * 
	 * // int resolution = 4; int resolution = 96;
	 * 
	 * Sequence s = new Sequence(Sequence.PPQ, resolution);
	 * 
	 * Track track = s.createTrack(); MusicalEvent[] musicalEvents =
	 * musicalStructure.getMusicalEvents();
	 * 
	 * for (int i = 0; i < musicalEvents.length; i++) { Note note =
	 * musicalEvents[i].note; MidiMessage msg; if (note != null) { msg = new
	 * ShortMessage(); if (musicalEvents[i].velocity > 0) { int velocity = (int)
	 * (MAX_PARAMETER_VALUE * musicalEvents[i].velocity); ((ShortMessage)
	 * msg).setMessage(ShortMessage.NOTE_ON, note.getMidiValue(), velocity); } else
	 * { ((ShortMessage) msg).setMessage(ShortMessage.NOTE_OFF, note.getMidiValue(),
	 * 0); }
	 * 
	 * } else { msg = new MetaMessage(END_OF_BLOCK, null, 0); } double timing =
	 * (musicalEvents[i].timing * 4.0) * resolution; MidiEvent me = new
	 * MidiEvent(msg, (int) timing); track.add(me); }
	 * 
	 * // == ATTENTION == DONT TRY TO PLACE THIS CODE INTO A PRIVATE METHOD!! // IT
	 * WILL NOT WORK. NEED TO RUN IMEDIATELLY BEFORE THE Sequencer.start() //
	 * Getting the virtual resources. Dont need to get the external // midi port
	 * (midiOut) because it was created on the super. //
	 * 
	 * // Playing inicialization int i = getIndexFreeSequencer();
	 * 
	 * if (i > -1) { Sequence s = getSequence(musicalStructure);
	 * 
	 * s = stampMetaMessage(s,(byte)i);
	 * 
	 * loops[i].setSequence(s); loops[i].setTempoInBPM(bpm);
	 * 
	 * loops[i].start(); loopStatus[i] = STATUS_PLAYING;
	 * 
	 * return i;
	 * 
	 * } else { throw new octopus.
	 * MusicPerformanceException("No sequencer available to loop. Try add more."); }
	 * 
	 * } catch (InvalidMidiDataException ex1) { throw new
	 * octopus.MusicPerformanceException(ex1.getMessage(), ex1); }
	 * 
	 * }
	 */

	public void closeDevices() throws MidiUnavailableException {

		// closing in the reverse order of openning
		sequencer.stop();
		seqTransmitter.close();
		sequencer.close();

		for (int i = 0; i < loops.length; i++) {
			if (loops[i].isRunning()) {
				loops[i].stop();
				loopStatus[i] = STATUS_STOPPED;
			}

			if (loops[i].isOpen())
				sequencer.close();

		}

		synthReceiver.close();
		outputDevice.close();

	}

	/*
	 * public void loop(MusicalEventSequence musicalStructure) throws
	 * MusicPerformanceException { try {
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
	 * if(note ==null){ //Pauses are not inserted..instead, notes are escalonated in
	 * time. throw new
	 * MusicPerformanceException("Number of rhythmic events must be equal to number of notes."
	 * ); } if (musicalEvents[i].velocity > 0) {
	 * 
	 * 
	 * int velocity = (int) (MAX_PARAMETER_VALUE * musicalEvents[i].velocity);
	 * msg.setMessage(ShortMessage.NOTE_ON, note.getMidiValue(), velocity); } else {
	 * msg.setMessage(ShortMessage.NOTE_OFF, note.getMidiValue(), 0); }
	 * 
	 * 
	 * double timing = (musicalEvents[i].timing * 4.0) * resolution; MidiEvent me =
	 * new MidiEvent(msg, (int) timing); track.add(me); }
	 * 
	 * //== ATTENTION == DONT TRY TO PLACE THIS CODE INTO A PRIVATE METHOD!! // IT
	 * WILL NOT WORK. NEED TO RUN IMEDIATELLY THE Sequencer.start() //Getting the
	 * virtual resources. Dont need to get the external // midi port (midiOut)
	 * because it was created on the super. //
	 * 
	 * //Playing inicialization int i = getIndexFreeSequencer(); if(i > -1) {
	 * loops[i].setSequence(s); loops[i].setTempoInBPM(bpm);
	 * 
	 * loops[i].start(); }else { throw new octopus.
	 * MusicPerformanceException("No sequencer available to loop. Try add more."); }
	 * 
	 * } catch (InvalidMidiDataException ex1) { throw new
	 * octopus.MusicPerformanceException(ex1.getMessage(),ex1); }
	 * 
	 * 
	 * }
	 */

	public void stopAll() {
		try {
			if (sequencer != null) {
				if (sequencer.isRunning()) {
					sequencer.stop();
				}
			}
			for (int i = 0; i < loopStatus.length; i++) {
				if (loopStatus[i] == STATUS_PLAYING) {
					loops[i].stop();
					loopStatus[i] = STATUS_STOPPED;

					// percorrer gearbox

				}
				if (loopStatus[i] == STATUS_WAITING) { // tem que testar isso!
					loopStatus[i] = STATUS_STOPPED;
				}

			}
		} catch (Exception ex) {
			// nothing: At this point is not important recognize the hardware;
		}

	}

	// como fazer isso? parar todos ligados a ele de uma vez ou deixar morrer o
	// loop?
	public void stop(int loopIndex) {
		if (loops[loopIndex] != null) {
			if (loops[loopIndex].isRunning()) {
				loops[loopIndex].stop();
				loopStatus[loopIndex] = STATUS_STOPPED;
			}
		}

	}

}

class GearBoxNew {
	ArrayList gearBox[];

	public GearBoxNew(int loopCount) {
		gearBox = new ArrayList[loopCount];
	}

	public void engage(int primaryLoopIndex, int secondaryLoopIndex) {
		if (gearBox[primaryLoopIndex] == null) {
			gearBox[primaryLoopIndex] = new ArrayList<Integer>();
		}
		gearBox[primaryLoopIndex].add(secondaryLoopIndex);
	}

	public void disengage(int primaryLoopIndex, int secondaryLoopIndex) {
		if (gearBox[primaryLoopIndex] != null) {
			gearBox[primaryLoopIndex].remove(secondaryLoopIndex);
		}

	}

	public ArrayList getEngagedLoops(int primaryLoopIndex) {
		return gearBox[primaryLoopIndex];
	}
}
