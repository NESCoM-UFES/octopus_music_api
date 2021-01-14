package octopus.communication.midi;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

import octopus.instrument.fretted.GuitarGraphicalInterface;
import octopus.instrument.fretted.GuitarNotePosition;




public class GraphicalGuitarMidiReceiver
    extends GuitarMidiReceiver {
  GuitarGraphicalInterface guitarLayout;

  public GraphicalGuitarMidiReceiver(GuitarGraphicalInterface guitarGraphicalInterface) {
    super();
    guitarLayout = guitarGraphicalInterface;
  }

  @Override
public void performAction(MidiMessage message, long lTimeStamp, String strMidiMessage){
    //System.out.println(strMidiMessage );
    if(message instanceof SysexMessage){

      int fret = this.getFret(strMidiMessage);
      int string = this.getString(strMidiMessage);

      if(isTurnOn(strMidiMessage)){
        guitarLayout.turnOn(new GuitarNotePosition(fret, string));
      }else{
        guitarLayout.turnOff(new GuitarNotePosition(fret, string));
      }
    }

 }


 @Override
public SysexMessage getSysexMessageTurnOn(int string, int fret ) throws
      InvalidMidiDataException {
   //System.out.println("ON:"+ string + ":"+ fret);
    String fretHexa = "0" + Integer.toHexString(fret);
    String strMessage = "F0437F0000030"+ (string) + fretHexa +"F7";
       int	nLengthInBytes = strMessage.length() / 2;
       byte[]	abMessage = new byte[nLengthInBytes];
       for (int i = 0; i < nLengthInBytes; i++)
       {
         String strTemp = strMessage.substring(i * 2, i * 2 + 2);
         abMessage[i] = (byte) Integer.parseInt(strTemp, 16);

       }

          SysexMessage sysexMessage = new SysexMessage();
          sysexMessage.setMessage(abMessage, abMessage.length);

          return sysexMessage;

     }


     @Override
	public SysexMessage getSysexMessageTurnOff(int string, int fret ) throws
          InvalidMidiDataException {
       //System.out.println("ON:"+ string + ":"+ fret);
        String fretHexa = "0" + Integer.toHexString(fret);
        String strMessage = "F0437F0000040"+ (string) + fretHexa +"F7";
           int	nLengthInBytes = strMessage.length() / 2;
           byte[]	abMessage = new byte[nLengthInBytes];
           for (int i = 0; i < nLengthInBytes; i++)
           {
             String strTemp = strMessage.substring(i * 2, i * 2 + 2);
             abMessage[i] = (byte) Integer.parseInt(strTemp, 16);

           }

              SysexMessage sysexMessage = new SysexMessage();
              sysexMessage.setMessage(abMessage, abMessage.length);

              return sysexMessage;

     }




}
