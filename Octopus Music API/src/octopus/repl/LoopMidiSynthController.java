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
	



	/*There are 16 midi channels per track. We are using just one track...channel 1 for  
	 * 
	 */
	private final int DEFAULT_LOOPS_COUNT = 15; 
	
	private final int TICK_OFFSET = 0;

	private final int END_OF_LOOP = 1;
	

	private boolean loopArmed = false;
	
	private final Loop[] loops;//avaliar necessidade
	private final Sequencer[] sequencers;
	private final GearBox gearBox; // gearBox is the metaphor for the looping (gears) mechanism proposed.
	//private final int[] loopStatus;

	// String midiOut;

	public LoopMidiSynthController() throws MidiUnavailableException {
		// this.midiOut = MidiSystem.getSynthesizer().getDeviceInfo().getName();
		sequencers = new Sequencer[DEFAULT_LOOPS_COUNT];
		loops = new Loop[DEFAULT_LOOPS_COUNT]; 
		//loopStatus = new int[DEFAULT_LOOPS_COUNT];
		gearBox = new GearBox(DEFAULT_LOOPS_COUNT);
		loadSequencers(DEFAULT_LOOPS_COUNT);
	}

	public LoopMidiSynthController(String midiOut, int numberLoops) throws MidiUnavailableException {
		super(midiOut);
		sequencers = new Sequencer[numberLoops];
		loops = new Loop[numberLoops]; 
		//loopStatus = new int[numberLoops];
		gearBox = new GearBox(numberLoops);
		loadSequencers(numberLoops);
	}

	public LoopMidiSynthController(String midiOutPortName) throws MidiUnavailableException {
		super(midiOutPortName);
		sequencers = new Sequencer[DEFAULT_LOOPS_COUNT];
		loops = new Loop[DEFAULT_LOOPS_COUNT]; 
		//loopStatus = new int[DEFAULT_LOOPS_COUNT];
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
					loops[i].stop();													
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
						if(loopArmed) {
							if (m.getType() == END_OF_LOOP) {
								int loopID = m.getData()[0];
								startPendingSequencers(loopID);
								return;
							}
						}
					}
				});

			}
			rewireSequencer(sequencers[i], this.outputDeviceName);
			//loopStatus[i] = STATUS_CREATED; // Avaliar se é necessário saber se o sequencer foi criado
		}
	}

	private void startPendingSequencers(int loopID) {
		ArrayList<Loop> pendingLoops = gearBox.getEngagedLoops(loopID);
		for (Loop pendindLoop : pendingLoops) {
			if (pendindLoop.getStatus() == Loop.STATUS_WAITING) {
				pendindLoop.start();				
			}
		}
		loopArmed = false;
	}
	
	
	
	public void setBPM(float bpm) {		
		super.setBPM(bpm);
		
		for (int i = 0; i < loops.length; i++) {
			if(loops[i] != null) {
				loops[i].setBpm(bpm);
			}
		}
	}
	
	/**
	 * Return the next avaliable sequencer.
	 * @return
	 */
	private int getAvaiableSequencer() {
		for (int i = 0; i < loops.length; i++) {
			if ((loops[i] == null) || (loops[i].getStatus() == Loop.STATUS_STOPPED)) {
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
		

		MidiEvent me = new MidiEvent(msg, sequence.getTickLength() - TICK_OFFSET);
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
					((ShortMessage) msg).setMessage(ShortMessage.NOTE_ON, defaultChannel,note.getMidiValue(), velocity);
				} else {
					((ShortMessage) msg).setMessage(ShortMessage.NOTE_OFF, defaultChannel, note.getMidiValue(), 0);
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

	public Loop createLoop(MusicalEventSequence musicalStructure) throws MusicPerformanceException {
		try {
			
			//Loop loop;
			int i =getAvaiableSequencer();

			if (i > -1) {
											
				Sequence s;

				s = getSequence(musicalStructure);
				s = stampMetaMessage(s,  i); // this is just true for loops! regular playable use the super.sequencer (not looping).

				sequencers[i].setSequence(s);
				sequencers[i].setTempoInBPM(bpm);

				
				loops[i] = new Loop(i,sequencers[i],musicalStructure);
				//loopArmed = true;
				System.out.println("loopID = " + i );
				
				
				return loops[i];

			} else {
				throw new octopus.MusicPerformanceException("No sequencer available to loop. Try create a new LoopMidiSynthController");
			}
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Loop getLoop(int controllerLoopID) {
		return loops[controllerLoopID];
	}
	
	
	public void synchLoops(Loop primaryLoop, Loop secondaryLoop) {
		gearBox.engage(primaryLoop,  secondaryLoop);
		secondaryLoop.arm();
		loopArmed = true;
		
	}
	
	
	public void closeDevices() throws MidiUnavailableException {

		// closing in the reverse order of openning
		sequencer.stop();
		seqTransmitter.close();
		sequencer.close();

		for (int i = 0; i < sequencers.length; i++) {
			if (sequencers[i].isRunning()) {
				loops[i].stop();
				loops[i]=null;
			}

			if (sequencers[i].isOpen())
				sequencer.close();

		}

		synthReceiver.close();
		outputDevice.close();

	}


	public void stopAll() {

		if (sequencer != null) {
			if (sequencer.isRunning()) {
				sequencer.stop();
			}
		}
		for (int i = 0; i < loops.length; i++) {
			if(loops[i] != null) {
				if((loops[i].getStatus() == Loop.STATUS_PLAYING) ||
						(loops[i].getStatus() == Loop.STATUS_WAITING)){ //testar a segunda condição.
					loops[i].stop();
				}
			}
		}

	}


}

class GearBox {
	ArrayList gearBox[];

	public GearBox(int loopCount) {
		gearBox = new ArrayList[loopCount];
	}

	public void engage(Loop primaryLoop, Loop secondaryLoop) {
		int loopIndex = primaryLoop.controllerLoopID;
		if (gearBox[loopIndex] == null) {
			gearBox[loopIndex] = new ArrayList<Loop>();
		}
		gearBox[loopIndex].add(secondaryLoop);
	}

	public void disengage(Loop primaryLoop, Loop secondaryLoop) {
		int loopIndex = primaryLoop.controllerLoopID;
		if (gearBox[loopIndex] != null) {
			gearBox[loopIndex].remove(secondaryLoop);
		}

	}

	public ArrayList getEngagedLoops(int primaryLoopIndex) {
		return gearBox[primaryLoopIndex];
	}
	public ArrayList getEngagedLoops(Loop primaryLoop) {		
		return gearBox[primaryLoop.controllerLoopID];
	}
}
