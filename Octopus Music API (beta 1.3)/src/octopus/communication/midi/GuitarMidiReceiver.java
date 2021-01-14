package octopus.communication.midi;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

public abstract class GuitarMidiReceiver
    extends OctopusMidiReceiver {


  public GuitarMidiReceiver() {
    super();
  }

  @Override
public void performAction(MidiMessage message, long lTimeStamp, String strMidiMessage){
    //System.out.println(strMidiMessage );
    if(message instanceof SysexMessage){

      int fret = this.getFret(strMidiMessage);
      int string = this.getString(strMidiMessage);

      if(isTurnOn(strMidiMessage)){
        System.out.println("Pressed - "+"String: " + string + "Fret: " + fret  );
      }else{
        System.out.println("Released - "+"String: " + string + "Fret: " + fret  );
      }
    }

 }

 protected int getFret(String strMidiMessage){
   String txtFret = strMidiMessage.substring(strMidiMessage.length() - 2);
   int fret = Integer.parseInt(txtFret, 16);
   return fret;
 }

 protected int getString(String strMidiMessage){
   String txtString = strMidiMessage.substring(strMidiMessage.length() - 4, strMidiMessage.length() - 3);
   int string = Integer.parseInt(txtString);
  return string;
 }

 protected boolean isTurnOn(String strMidiMessage){
   String sysExType = (strMidiMessage.substring(strMidiMessage.length() - 7, strMidiMessage.length() - 6));
   boolean ON_OFF = sysExType.equalsIgnoreCase("3")?true:false;
   return ON_OFF;
 }


 public abstract SysexMessage getSysexMessageTurnOn(int string, int fret ) throws
      InvalidMidiDataException;



public abstract SysexMessage getSysexMessageTurnOff(int string, int fret ) throws
          InvalidMidiDataException;



}
