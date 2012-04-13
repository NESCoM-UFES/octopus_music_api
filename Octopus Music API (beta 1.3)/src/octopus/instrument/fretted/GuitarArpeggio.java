package octopus.instrument.fretted;

/**
 *
 *
 */
import java.io.*;
import java.util.*;
import octopus.*;

public class GuitarArpeggio extends Arpeggio implements Serializable{
  
 private static final long serialVersionUID = 1L;


int minimumPolyphony = 1; //???


  final static int CLASSICAL_FINGER_STYLE_ARPEGGIO = 0;
  final static int PLECTRUM_PICKING_ARPEGGIO = 1;
  final static int PLECTRUM_FINGER_ARPEGGIO = 2;//HYBRID TECHINIC USING FINGER STYLE(MAX FINGERS) AND PICK(PI).
  final static int ANCHOR_FINGER_STYLE_ARPEGGIO = 3; //rare
  final static int MUTING_FINGER_STYLE_ARPEGGIO = 4; //rare
  final static int CUSTOM_STYLE_ARPEGGIO = 5; //rare

  private int arpeggioStyle=0;

  //Vector arpeggio;

  public GuitarArpeggio( int polyphony) {
    super(polyphony);
    minimumPolyphony = 1;
  }


 /**
  * Creates a GuitarArpeggio based on a regular Arpeggio. Considering that
  * GuitarArpeggio is a subclass of Arpeggio, this constructor just downcast the
  * arpeggio object to a guitarArpeggio object.
  */
 protected GuitarArpeggio(Arpeggio arpeggio){
   super();
   this.setName(arpeggio.getName());
   this.setBpm(arpeggio.getBpm()); //Overall bpm
   this.setTimeStratch(arpeggio.isTimeStratch());
   this.setMinimumPolyphony(arpeggio.getPolyphony()); // Modificado pelo desejo do usuario
   this.setTimeStratch(arpeggio.isTimeStratch());
   this.voices =  new Vector<RhythmPattern> (Arrays.asList(arpeggio.getVoices()));
   for (int i = 0; i < arpeggio.getPolyphony(); i++) {
     RhythmPattern rp = arpeggio.getVoice(i);
     Bar[] bars = rp.getBars(false);
     for (int j = 0; j < bars.length; j++) {
         Bar bar =  bars[j];
         GuitarBar guitarBar = new GuitarBar(bar.getNumberOfUnits(),
                                             bar.getMeasurementUnit());
         Bar.RhythmEvent[] rhythmEvents = bar.getRhythmEvents();

         for (int k = 0; k < rhythmEvents.length; k++) {
           guitarBar.addRhythmEvent(rhythmEvents[k].duration,
                                    rhythmEvents[k].type,
                                    rhythmEvents[k].tie);
         }
         rp.replaceBar(bar,guitarBar);
       }
     }
   }

/**
 * Creates a guitar arpeggio with an specified polyphony. Each voice of the
 * arpeggio will have only one RhythmEvent with the duration designated, meaning that
 * the arpeggio will have the same duration.
 */
 public GuitarArpeggio(int polyphony, double duration){
   super(polyphony);
    for (int i=0;i<polyphony;i++){
      RhythmPattern rhythmPattern =  this.getVoice(i);
      GuitarBar bar = new GuitarBar(1,4);
      bar.addRhythmEvent(duration,RhythmConstants.RHYTHM_EVENT_NOTE);
      rhythmPattern.insertBar(bar);
    }

  }


 public void printStrokes(){
     Arpeggio.SortableRhythmEvent[] events= getSortableRhythmEvents();
      for (int i = 0; i < events.length; i++) {
          GuitarBar gBar = ((GuitarBar)events[i].bar);
          GuitarBar.Stroke stroke = gBar.getStroke(events[i].rhythmEvent);
          String direction = stroke.direction ==0?" down": " up";
          System.out.println(events[i] + direction +" " +stroke.finger );
        }
      }


 /**
  * The arpeggio is considerated an strum when the number of simultaneous
  * attacks is bigger than the number of finger to pull the strings at same
  * time.
  */
 public boolean isStrumming(int nPluckHandFingers){
   if(getSimultaneousAttacks() > nPluckHandFingers){
     return true;
   }
  return false;
}

/**
 * The arpeggio is considerated a full arpeggio if the number of simultaneous
 * attacks is equal to its polyphony.
 */
public boolean isFullArpeggio() {
  if (getSimultaneousAttacks() == this.getPolyphony()) {
    return true;
  }
  return false;

}

  public void setMinimumPolyphony(int minPolyphony) {
     minimumPolyphony = minPolyphony;
  }

  /**
   * @param arpeggioStyle int FINGER_STYLE_ARPEGGIO = 0, PLECTRUM_PICKING_ARPEGGIO = 1;
   */
  public void setArpeggioStyle(int arpeggioStyle) {
    this.arpeggioStyle = arpeggioStyle;
  }

  public int getMinimumPolyphony() {
    return minimumPolyphony;
  }

  /**
   *
   * @return int FINGER_STYLE_ARPEGGIO = 0, PLECTRUM_PICKING_ARPEGGIO = 1;
   */
  public int getArpeggioStyle() {
    return arpeggioStyle;
  }


public static GuitarArpeggio getDemoGuitarArpeggio(){

  GuitarArpeggio gpr = new GuitarArpeggio(4);
   gpr.setBpm(240);

   GuitarBar bs1 = new GuitarBar(4,4);
   bs1.addSingleRhythmEvent(RhythmConstants.WHOLE_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE,1,GuitarBar.DIRECTION_UP_STROKE,GuitarBar.REGION_NEAR_MOUTH,GuitarBar.FINGERPICKING_THUMB_FINGER);
   gpr.insertBar(bs1,0);

   GuitarBar bs2 = new GuitarBar(4,4);
   //bs1.addRhythmEvent(bs1.QUARTER,1,0,0,bs1.INDEX_FINGER);
   bs2.addSingleRhythmEvent(RhythmConstants.HALF_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE,2,GuitarBar.DIRECTION_UP_STROKE,GuitarBar.REGION_NEAR_MOUTH,GuitarBar.FINGERPICKING_INDEX_FINGER);
   gpr.insertBar(bs2,1);

   GuitarBar bs3 = new GuitarBar(4,4);
   //bs1.addRhythmEvent(bs1.QUARTER,1,0,0,bs1.INDEX_FINGER);
   bs3.addSingleRhythmEvent(RhythmConstants.HALF_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE,3,GuitarBar.DIRECTION_UP_STROKE,GuitarBar.REGION_NEAR_MOUTH,GuitarBar.FINGERPICKING_MIDDLE_FINGER);
   gpr.insertBar(bs3,2);

   GuitarBar bs4 = new GuitarBar(4,4);
   //bs1.addRhythmEvent(bs1.QUARTER,1,0,0,bs1.INDEX_FINGER);
   bs4.addSingleRhythmEvent(RhythmConstants.QUARTER_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE,4,GuitarBar.DIRECTION_UP_STROKE,GuitarBar.REGION_NEAR_MOUTH,GuitarBar.FINGERPICKING_RING_FINGER);
   gpr.insertBar(bs4,3);

   return gpr;
 }


/*@todo Testar se precisa de redefinir o metodo da superclasse*/
public static Arpeggio load(File file) throws FileNotFoundException,
    IOException, ClassNotFoundException {
  GuitarArpeggio guitarArpeggio = null; ;
  if (file != null) {
    FileInputStream fIn = new FileInputStream(file);
    ObjectInputStream in = new ObjectInputStream(fIn);
    guitarArpeggio = (GuitarArpeggio) in.readObject();

    in.close();

  }

  return guitarArpeggio;

}

}
