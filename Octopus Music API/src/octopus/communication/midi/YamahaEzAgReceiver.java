package octopus.communication.midi;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.SysexMessage;






public class YamahaEzAgReceiver extends GuitarMidiReceiver{
  //public MidiDevice outputDevice=null;
  //Receiver receiver=null;



//  boolean[][] knobsLights = new boolean[6][12];
  String[][] knobs = {{"4D","4E","4F","50","51","52","53","54","55","56","57","58"},
      {"48","49","4A","4B","4C","4D","4E","4F","50","51","52","53"},
      {"44","45","46","47","48","49","4A","4B","4C","4D","4E","4F"},
      {"3F","40","41","42","43","44","45","46","47","48","49","4A"},
      {"3A","3B","3C","3D","3E","3F","40","41","42","43","44","45"},
      {"35","36","37","38","39","3A","3B","3C","3D","3E","3F","40"}};




  public YamahaEzAgReceiver()throws
      MidiUnavailableException {
    super();
  }

  @Override
public void performAction(MidiMessage message, long lTimeStamp, String strMidiMessage){
    System.out.println(strMidiMessage );
  }
    /* public YamahaEzAgReceiver(String midiOut) throws
      MidiUnavailableException {


    if(outputDevice!=null){outputDevice.close();}
    outputDevice = OctopusMidiSystem.openMidiDevice(midiOut);
    receiver = outputDevice.getReceiver();


  }*/




  /*public void turnOn(int string, int fret ){
   //System.out.println("ON:"+ string + ":"+ fret);
    try{
     if(receiver!=null){
      String strMessage = "F0437F0000030"+ (string+1) + knobs[string][fret] +"F7";
       int	nLengthInBytes = strMessage.length() / 2;
       byte[]	abMessage = new byte[nLengthInBytes];
       for (int i = 0; i < nLengthInBytes; i++)
       {
         String strTemp = strMessage.substring(i * 2, i * 2 + 2);
         abMessage[i] = (byte) Integer.parseInt(strTemp, 16);

       }

          SysexMessage sysexMessage = new SysexMessage();
          sysexMessage.setMessage(abMessage, abMessage.length);
          receiver.send(sysexMessage,-1);

          //knobsLights[string][fret] = true;

     }
   }catch(Exception ex){
     ex.printStackTrace();
   }
  }*/

 /* public void turnOff(int string, int fret ){
   try{

      String strMessage = "F0437F0000040"+ (string+1) + knobs[string][fret] +"F7";
       int	nLengthInBytes = strMessage.length() / 2;
       byte[]	abMessage = new byte[nLengthInBytes];
       for (int i = 0; i < nLengthInBytes; i++)
       {
         abMessage[i] = (byte) Integer.parseInt(strMessage.substring(i * 2, i * 2 + 2), 16);
       }

          SysexMessage sysexMessage = new SysexMessage();
          sysexMessage.setMessage(abMessage, abMessage.length);
          receiver.send(sysexMessage,-1);


   }catch(Exception ex){
     ex.printStackTrace();
   }
  }*/

 /* public void turnOn(GuitarChordShape chordShape){
   try{
    GuitarNotePosition[] pos = chordShape.getGuitarNotePositions();
    for(int p=0; p<pos.length;p++){
      int corda = pos[p].getString() -1 ;
      int fret = pos[p].getFret() -1 ;
      if(fret>=0){
        turnOn(corda , fret);
      }
    }
   }catch(Exception ex){
     ex.printStackTrace();
   }
  }*/


 /*public void performAction(MidiMessage message, long lTimeStamp,String strMidiMessage){
   //System.out.println(strMidiMessage );
   if (message instanceof SysexMessage) {

     int fret = this.getFret(strMidiMessage);
     int string = this.getString(strMidiMessage);

     if (isTurnOn(strMidiMessage)) {
       turnOn(fret, string);
     }
     else {
       turnOff(fret, string);
     }

   }
 }*/


 /*public void turnOffAll(){
   for (int i=0;i<6;i++){
     for(int j=0; j<12;j++){
       turnOff(i,j);
     }
   }
 }*/


 /* public void turnOff(GuitarChordShape chordShape){
    try{
        GuitarNotePosition[] pos = chordShape.getGuitarNotePositions();
        for(int p=0; p<pos.length;p++){
          int corda = pos[p].getString() -1;
          int fret = pos[p].getFret() - 1;
          if(fret>=0){
            turnOff(corda , fret);
          }
        }
       }catch(Exception ex){
         ex.printStackTrace();
   }
  }*/

  @Override
public SysexMessage getSysexMessageTurnOn(int string, int fret) throws
      InvalidMidiDataException {


      String strMessage = "F0437F0000030"+ (string) + knobs[string-1][fret] +"F7";
       int	nLengthInBytes = strMessage.length() / 2;
       byte[]	abMessage = new byte[nLengthInBytes];
       for (int i = 0; i < nLengthInBytes; i++)
       {
         String strTemp = strMessage.substring(i * 2, i * 2 + 2);
         abMessage[i] = (byte) Integer.parseInt(strTemp, 16);

       }

          SysexMessage sysexMessage = new SysexMessage();
          sysexMessage.setMessage(abMessage, abMessage.length);
          //receiver.send(sysexMessage,-1);

          //knobsLights[string][fret] = true;

          return sysexMessage;




  }

  @Override
public SysexMessage getSysexMessageTurnOff(int string, int fret) throws
      InvalidMidiDataException {
    String strMessage = "F0437F0000040" + (string) + knobs[string-1][fret] +
        "F7";
    int nLengthInBytes = strMessage.length() / 2;
    byte[] abMessage = new byte[nLengthInBytes];
    for (int i = 0; i < nLengthInBytes; i++) {
      String strTemp = strMessage.substring(i * 2, i * 2 + 2);
      abMessage[i] = (byte) Integer.parseInt(strTemp, 16);

    }

    SysexMessage sysexMessage = new SysexMessage();
    sysexMessage.setMessage(abMessage, abMessage.length);
    //receiver.send(sysexMessage,-1);

    //knobsLights[string][fret] = true;

    return sysexMessage;

  }

}
