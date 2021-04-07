package octopus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;


public  class ArpeggioLibrary {


  public static Arpeggio getDemoArpeggio(){
    Arpeggio gpr = new Arpeggio(3);

    Bar bs1 = new Bar(2,4);
    bs1.addRhythmEvent(RhythmConstants.WHOLE_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
    gpr.insertBar(bs1,0);

    Bar bs2 = new Bar(4,4);
    bs2.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RhythmConstants.RHYTHM_EVENT_REST);
    bs2.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
    bs2.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
    bs2.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
    gpr.insertBar(bs2, 1);

    Bar bs3 = new Bar(4,4);

    bs3.addSingleRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE,3);
    gpr.insertBar(bs3, 2);


   return gpr;
  }

  public static Arpeggio getDemoArpeggio2(){
    Arpeggio gpr = new Arpeggio(4);

    Bar bs1 = new Bar(2,4);
    bs1.addRhythmEvent(RhythmConstants.HALF_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
    bs1.addRhythmEvent(RhythmConstants.HALF_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
    gpr.insertBar(bs1,0);

    Bar bs2 = new Bar(4,4);
    bs2.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RhythmConstants.RHYTHM_EVENT_REST);
    bs2.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
    bs2.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
    bs2.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
    gpr.insertBar(bs2, 1);

    Bar bs3 = new Bar(4,4);

    bs3.addSingleRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE,3);
    gpr.insertBar(bs3, 2);

    Bar bs4 = new Bar(4,4);
    for(int i =0; i < 16; i++){
      bs4.addRhythmEvent(RhythmConstants.SIXTEENTH_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
    }
    gpr.insertBar(bs4, 3);

   return gpr;
  }

  public static Arpeggio getFastArpeggio(){
      Arpeggio gpr = new Arpeggio(4);
      gpr.setName("Fast Arpeggio");
      Bar[] bars = new Bar[4];
      for(int i = 0; i<4; i++){
        bars[i] = new Bar(4,16);
        bars[i].addSingleRhythmEvent(RhythmConstants.EIGHT_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE,i);
        gpr.insertBar(bars[i],i);
      }

     return gpr;
  }

  public static Arpeggio getFastArpeggio(int nVoices){
      Arpeggio gpr = new Arpeggio(nVoices);
      gpr.setName("5 Notes Fast Arpeggio");
      Bar[] bars = new Bar[nVoices];
      for(int i = 0; i<nVoices; i++){
        bars[i] = new Bar(nVoices,16);
        bars[i].addSingleRhythmEvent(RhythmConstants.SIXTEENTH_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE,i);
        gpr.insertBar(bars[i],i);
      }

     return gpr;
  }

/**
 * Serialize the arpeggio object in the specified file. In the future this method
 * will probable be replaced by a XML strore one.
 *
 */
  public static void store(Arpeggio prp, File file) throws
      FileNotFoundException, IOException {

  if (!file.getName().endsWith(".prp")) {
    file = new File(file.getPath() + ".prp");
  }

    if (file != null) {
      if (!file.getName().endsWith("prp")) {
        file = new File(file.getPath() + ".prp");
      }
      FileOutputStream fOut = new FileOutputStream(file);
      ObjectOutput out = new ObjectOutputStream(fOut);
      out.writeObject(prp);
      out.flush();
      out.close();
    }


}
/**
* Retore an  arpeggio object from the specified file. In the future this method
* will probable be replaced by a a XML loader.
*/

public static Arpeggio load(File file) throws FileNotFoundException,
      IOException, ClassNotFoundException {
  Arpeggio rhythmPattern = null;;
  if (file != null) {
    FileInputStream fIn = new FileInputStream(file);
    ObjectInputStream in = new ObjectInputStream(fIn);
    rhythmPattern = (Arpeggio) in.readObject();

    in.close();

  }

  return rhythmPattern;

}


}
