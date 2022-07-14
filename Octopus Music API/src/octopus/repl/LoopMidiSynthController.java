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

	private final Sequencer[] sequencers;
	private final GearBox gearBox; // gearBox is the metaphor for the looping (gears) mechanism proposed.
	private final int[] loopStatus;

	// String midiOut;

	public LoopMidiSynthController() throws MidiUnavailableException {
		// this.midiOut = MidiSystem.getSynthesizer().getDeviceInfo().getName();
		sequencers = new Sequencer[DEFAULT_LOOPS_COUNT];
		loopStatus = new int[DEFAULT_LOOPS_COUNT];
		gearBox = new GearBox(DEFAULT_LOOPS_COUNT);
		loadSequencers(DEFAULT_LOOPS_COUNT);
	}

	public LoopMidiSynthController(String midiOut, int numberLoops) throws MidiUnavailableException {
		super(midiOut);
		sequencers = new Sequencer[numberLoops];
		loopStatus = new int[numberLoops];
		gearBox = new GearBox(numberLoops);
		loadSequencers(numberLoops);
	}

	public LoopMidiSynthController(String midiOutPortName) throws MidiUnavailableException {
		super(midiOutPortName);
		sequencers = new Sequencer[DEFAULT_LOOPS_COUNT];
		loopStatus = new int[DEFAULT_LOOPS_COUNT];
		gearBox = new GearBox(DEFAULT_LOOPS_COUNT);
		loadSequencers(DEFAULT_LOOPS_COUNT);
	}

	// Change the transmitters/receivers connection without creating a new
	// sequencer...wich is pretty slow and
	// memory intensive.
	public void setMidiOut(String midiOut) throws MidiUnavailableException {
		super.setMidiOut(midiOut);

		for (int i = 0; i < sequencers.length; i++) {
			if (sequencers[i] != null) {
				if (sequencers[i].isRunning())
					sequencers[i].stop();
				loopStatus[i] = STATUS_STOPPED;
			}
			rewireSequencer(sequencers[i], this.outputDeviceName);

		}
	}

	
	private final void loadSequencers(int numberSequencers) throws MidiUnavailableException {

		for (int i = 0; i < numberSequencers; i++) {
			if (sequencers[i] == null) {
				sequencers[i] = MidiSystem.getSequencer();
				sequencers[i].open();
				sequencers[i].setLoopCount(sequencers[i].LOOP_CONTINUOUSLY);

				sequencers[i].addMetaEventListener(new MetaEventListener() {
					public void meta(MetaMessage m) {
						if (m.getType() == END_OF_LOOP) {
							int loopIdex = m.getData()[0];
							startPendingSequencers(loopIdex);
							return;
						}
					}
				});

			}
			rewireSequencer(sequencers[i], this.outputDeviceName);
			loopStatus[i] = STATUS_CREATED;
		}
	}

	private void startPendingSequencers(int loopIndex) {
		ArrayList<Integer> pendingLoops = gearBox.getEngagedLoops(loopIndex);
		for (Integer pendindLoopIndex : pendingLoops) {
			if (loopStatus[pendindLoopIndex] == STATUS_WAITING) {
				sequencers[pendindLoopIndex].start();
				loopStatus[pendindLoopIndex] = STATUS_PLAYING;
			}
		}
	}

	/**
	 * Return the next avaliable sequencer.
	 * @return
	 */
	private int getIndexFreeSequencer() {
		for (int i = 0; i < loopStatus.length; i++) {
			if ((loopStatus[i] == STATUS_CREATED) || (loopStatus[i] == STATUS_STOPPED)) {
				loopStatus[i] = STATUS_WAITING;
				return i;
			}
		}
		return -1;
	}

	/**this method adds a final MetaMessage to midi Sequences so the sequencer can inform othes of its state change,
	 in this case, a loop.*/
	private Sequence stampMetaMessage(Sequence sequence, int loopIndex) throws InvalidMidiDataException {
		Track track = sequence.getTracks()[0];

		byte[] data = { (byte)loopIndex };
		MetaMessage msg = new MetaMessage(END_OF_LOOP, data, data.length);

		MidiEvent me = new MidiEvent(msg, sequence.getTickLength());
		track.add(me);

		return sequence;
	}

	
	/**
	 * Convert the Octopus MusicEventSequence into a midi sequence used by the sequencers.
	 * @param musicalStructure
	 * @return
	 * @throws InvalidMidiDataException
	 */
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


	/**
	 * 
	 * @param musicalStructure
	 * @return
	 * @throws MusicPerformanceException
	 */
	public int registerLoop(MusicalEventSequence musicalStructure) throws MusicPerformanceException {
		try {
			int i = getIndexFreeSequencer();

			if (i > -1) {
				Sequence s;

				s = getSequence(musicalStructure);

				s = stampMetaMessage(s,  i);

				sequencers[i].setSequence(s);
				sequencers[i].setTempoInBPM(bpm);
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
		sequencers[i].start();
		loopStatus[i] = STATUS_PLAYING;

		return i;

	}


	
	public void closeDevices() throws MidiUnavailableException {

		// closing in the reverse order of openning
		sequencer.stop();
		seqTransmitter.close();
		sequencer.close();

		for (int i = 0; i < sequencers.length; i++) {
			if (sequencers[i].isRunning()) {
				sequencers[i].stop();
				loopStatus[i] = STATUS_STOPPED;
			}

			if (sequencers[i].isOpen())
				sequencer.close();

		}

		synthReceiver.close();
		outputDevice.close();

	}

	
	public void stopAll() {
		try {
			if (sequencer != null) {
				if (sequencer.isRunning()) {
					sequencer.stop();
				}
			}
			for (int i = 0; i < loopStatus.length; i++) {
				if (loopStatus[i] == STATUS_PLAYING) {
					sequencers[i].stop();
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
		if (sequencers[loopIndex] != null) {
			if (sequencers[loopIndex].isRunning()) {
				sequencers[loopIndex].stop();
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
