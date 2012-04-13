package octopus.instrument.fretted;

import octopus.*;

import javax.sound.midi.*;

import java.util.*;
import octopus.communication.*;
import octopus.communication.midi.*;

public class GuitarMidiSynthesizerController
    extends MidiSynthesizerController {
    GuitarMidiReceiver guitarReceiver;


  public GuitarMidiSynthesizerController()throws MidiUnavailableException  {
    super();
  }

  public GuitarMidiSynthesizerController(String midiOut) throws MidiUnavailableException {
    super(midiOut);
  }

  public GuitarMidiSynthesizerController(GuitarMidiReceiver guitarReceiver)throws MidiUnavailableException  {
    super();
    receivers.add(guitarReceiver);
    this.guitarReceiver = guitarReceiver;
  }

  public GuitarMidiSynthesizerController(String midiOut, GuitarMidiReceiver guitarReceiver)throws MidiUnavailableException  {
     super(midiOut);
     this.guitarReceiver = guitarReceiver;
     receivers.add(guitarReceiver);
  }

  @SuppressWarnings("unchecked")
@Override
public void play(MusicalEventSequence musicalStructure) throws MusicPerformanceException
       {
         Sequence s = null;

        try {

          int resolution = 96;

          s = new Sequence(Sequence.PPQ, resolution);

          Track track = s.createTrack();
          MusicalEvent[] musicalEvents = musicalStructure.getMusicalEvents();
          // track.add(new ShortMessage().setMessage(ShortMessage.PROGRAM_CHANGE);)
          for (int i = 0; i < musicalEvents.length; i++) {

            String fret = (String)musicalEvents[i].get("fret");
            String instrumentString = (String)musicalEvents[i].get("string");

            SysexMessage sysexMessage=null;
            ShortMessage msg = new ShortMessage();


            // MetaMessage metaMsg = new MetaMessage(
            Note note = musicalEvents[i].note;
            if (musicalEvents[i].velocity > 0) {
              int velocity = (int) (MAX_PARAMETER_VALUE * musicalEvents[i].velocity);
              msg.setMessage(ShortMessage.NOTE_ON, note.getMidiValue(), velocity);
              if(fret!=null||(instrumentString!=null)){
                sysexMessage = guitarReceiver.getSysexMessageTurnOn(Integer.
                    parseInt(instrumentString),
                    Integer.parseInt(fret));
              }
            }
            else {
              msg.setMessage(ShortMessage.NOTE_OFF, note.getMidiValue(), 0);
              if(fret!=null||(instrumentString!=null)){
                sysexMessage = guitarReceiver.getSysexMessageTurnOff(Integer.
                    parseInt(instrumentString),
                    Integer.parseInt(fret));
              }

            }
            double timing = (musicalEvents[i].timing * 4.0) * resolution;
            MidiEvent me = new MidiEvent(msg, (int) timing);
            if(sysexMessage!=null){
              MidiEvent meSysex = new MidiEvent(sysexMessage, (int) timing);
              track.add(meSysex);
            }
            track.add(me);

          }

          //== ATTENTION == DONT TRY TO PLACE THIS CODE INTO A PRIVATE METHOD!!
          // IT WILL NOT WORK. NEED TO RUN IMEDIATELLY THE Sequencer.start()
          //Getting the virtual resources. Dont need to get the external
          // midi port (midiOut) because it was created on the super.
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
            seqTransmitter.setReceiver( (Receiver) receivers.get(i));
          }

          //Playing inicialization
          sequencer.setSequence(s);
          sequencer.setTempoInBPM( (float) ( musicalStructure).getBpm());
          sequencer.start();

          //Release resourses after end of playing
          while(sequencer.isRunning()){}
            closeDevices();
            //System.out.println("devices closed");


        }
        catch (MidiUnavailableException ex) {
          throw new octopus.MusicPerformanceException(ex.getMessage(),ex);
        }
        catch (InvalidMidiDataException ex1) {
          throw new octopus.MusicPerformanceException(ex1.getMessage(),ex1);
        } catch (NoteException e) {
			// TODO Auto-generated catch block
        	 throw new octopus.MusicPerformanceException(e.getMessage(),e);
		}

      }


}
