package octopus.communication.midi;




import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import octopus.MusicPerformanceException;
import octopus.Note;
import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;
import octopus.communication.SynthesizerController;

/**
 *
 * @author Leandro Costalonga
 * @version 1.0
 */
public class MidiSynthesizerController  implements SynthesizerController {
  public MidiDevice outputDevice=null;
  protected String outputDeviceName;
  protected Receiver synthReceiver=null;
  protected Sequencer sequencer=null;
  protected Transmitter seqTransmitter=null;

  protected int midiProgram = 1;
  protected Vector<Receiver> receivers;

  protected static final int MAX_PARAMETER_VALUE = 127;

  public MidiSynthesizerController() throws MidiUnavailableException {

     //outputDevice = MidiSystem.getSynthesizer();
     //sequencer = MidiSystem.getSequencer();
     receivers = new Vector<Receiver>();

  }


  public MidiSynthesizerController(MidiDevice midiOut) throws MidiUnavailableException {

	   outputDevice =midiOut;
	   outputDeviceName = midiOut.getDeviceInfo().getName();

	   receivers = new Vector<Receiver>();

	}
  
  public MidiSynthesizerController(String midiOut) throws MidiUnavailableException {

   outputDevice = OctopusMidiSystem.getMidiDevice(midiOut);
   outputDeviceName = midiOut;

   receivers = new Vector<Receiver>();

}



// DO NOT WORK ON SUBCLASSES. JAVA SOUND BUG
/*public void openDevices() throws MidiUnavailableException {

  if(outputDevice==null){
    outputDevice = MidiSystem.getSynthesizer();
  }else{
    outputDevice = OctopusMidiSystem.getMidiDevice(outputDeviceName);
  }

  outputDevice.open();
  synthReceiver = outputDevice.getReceiver();

  sequencer = MidiSystem.getSequencer();
  sequencer.open();
  seqTransmitter = sequencer.getTransmitter();
  seqTransmitter.setReceiver(synthReceiver);

  for (int i = 0; i < receivers.size(); i++) {
    seqTransmitter.setReceiver((Receiver)receivers.get(i));
  }
}*/

public void closeDevices() throws MidiUnavailableException {

   //closing in the reverse order od openning
   seqTransmitter.close();
   sequencer.close();
   synthReceiver.close();
   outputDevice.close();
}

  /*Send to outputDevice*/
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
    }
    catch (MidiUnavailableException ex) {
      throw new octopus.MusicPerformanceException(ex.getMessage(), ex);
    }

  }

  @SuppressWarnings("unchecked")

  private Sequence getSequence(MusicalEventSequence musicalStructure) throws  InvalidMidiDataException, MusicPerformanceException {

	  //int resolution = 4;
	  int resolution = 96;

	  Sequence s = new Sequence(Sequence.PPQ, resolution);

	  Track track = s.createTrack();
	  MusicalEvent[] musicalEvents = musicalStructure.getMusicalEvents();
	  // track.add(new ShortMessage().setMessage(ShortMessage.PROGRAM_CHANGE);)
	  for (int i = 0; i < musicalEvents.length; i++) {
		  ShortMessage msg = new ShortMessage();
		  // MetaMessage metaMsg = new MetaMessage(
		  Note note = musicalEvents[i].note;
		  if(note ==null){ 
			  //Pauses are not inserted..instead, notes as escalonated in time. 
			  throw new 	MusicPerformanceException("Number of rhythmic events must be equal to number of notes.");
		  }
		  if (musicalEvents[i].velocity > 0) {


			  int velocity = (int) (MAX_PARAMETER_VALUE * musicalEvents[i].velocity);
			  msg.setMessage(ShortMessage.NOTE_ON, note.getMidiValue(), velocity);
		  }
		  else {
			  msg.setMessage(ShortMessage.NOTE_OFF, note.getMidiValue(), 0);
		  }

		  double timing = (musicalEvents[i].timing * 4.0) * resolution;
		  MidiEvent me = new MidiEvent(msg, (int) timing);
		  track.add(me);

	  }
	  return s;
  }
  
  @Override
public void play(MusicalEventSequence musicalStructure) throws MusicPerformanceException
       {

        try {

        /*  //int resolution = 4;
          int resolution = 96;

          Sequence s = new Sequence(Sequence.PPQ, resolution);

          Track track = s.createTrack();
          MusicalEvent[] musicalEvents = musicalStructure.getMusicalEvents();
          // track.add(new ShortMessage().setMessage(ShortMessage.PROGRAM_CHANGE);)
          for (int i = 0; i < musicalEvents.length; i++) {
            ShortMessage msg = new ShortMessage();
            // MetaMessage metaMsg = new MetaMessage(
            Note note = musicalEvents[i].note;
            if(note ==null){ 
	            //Pauses are not inserted..instead, notes as escalonated in time. 
            	throw new 	MusicPerformanceException("Number of rhythmic events must be equal to number of notes.");
	            }
	            if (musicalEvents[i].velocity > 0) {
	                       
	            	
	            int velocity = (int) (MAX_PARAMETER_VALUE * musicalEvents[i].velocity);
	              msg.setMessage(ShortMessage.NOTE_ON, note.getMidiValue(), velocity);
	            }
	            else {
	              msg.setMessage(ShortMessage.NOTE_OFF, note.getMidiValue(), 0);
	            }
            
            double timing = (musicalEvents[i].timing * 4.0) * resolution;
            MidiEvent me = new MidiEvent(msg, (int) timing);
            track.add(me);
          }*/

          //== ATTENTION == DONT TRY TO PLACE THIS CODE INTO A PRIVATE METHOD!!
          // IT WILL NOT WORK. NEED TO RUN IMEDIATELLY THE Sequencer.start()
          //Getting the virtual resources. Dont need to get the external
          // midi port (midiOut) because it was created on the super.
        	
        	
          //Despite the warning, I've decided to try again several versions latter and, 
         //	apparently, it worked (2/05/2013)	
           
         Sequence s = getSequence(musicalStructure);
        	
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

          //Playing inicialization
          sequencer.setSequence(s);
          sequencer.setTempoInBPM( (float) ( musicalStructure).getBpm());
          sequencer.start();

          //Release resourses after end of playing
         while(sequencer.isRunning()){
        	//this is a bug..for some reason, Java is just exiting this loop if we write something in it!
        	// works fine when debugs, but not when run. 02/05/13 
        	// System.out.println("still running");
         }
            closeDevices();
            System.out.println("devices closed");

        }
        catch (MidiUnavailableException ex) {
          throw new octopus.MusicPerformanceException(ex.getMessage(),ex);
        }
        catch (InvalidMidiDataException ex1) {
          throw new octopus.MusicPerformanceException(ex1.getMessage(),ex1);
        }

      }

  public void writeMidiFile(File outputFile, MusicalEventSequence musicalStructure) throws IOException, InvalidMidiDataException, MusicPerformanceException{
	  MidiSystem.write(getSequence(musicalStructure), 1, outputFile);  
  }
  


 @Override
public void stop(){
 try {
   if(sequencer!=null){
   if( sequencer.isRunning()){
     sequencer.stop();
   }
  }
    closeDevices();
  }
  catch (MidiUnavailableException ex) {
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
}
