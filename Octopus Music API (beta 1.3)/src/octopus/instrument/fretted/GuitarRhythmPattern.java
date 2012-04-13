package octopus.instrument.fretted;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Leandro Costalonga
 * @version 1.0
 */
import java.io.*;

import octopus.*;

public class GuitarRhythmPattern extends Arpeggio implements Serializable{

	private static final long serialVersionUID = 1L;

	int minimumPolyphony = 1;
  int arpeggiationMode = 0;

  int fingers; // talvez tirar



  final static int FINGER_STYLE = 0;
  final static int PLECTRUM_PICKING = 1;
  private int strokeMode=0;

  //Vector arpeggio;

  public GuitarRhythmPattern( int polyphony) {
    super(polyphony);
    minimumPolyphony = 1;
  }

  public GuitarRhythmPattern(int polyphony, double duration){
   super(polyphony);
    for (int i=0;i<polyphony;i++){
      RhythmPattern rhythmPattern =  this.getVoice(i);
      GuitarBar bar = new GuitarBar(1,4);
      bar.addRhythmEvent(duration,RhythmConstants.RHYTHM_EVENT_NOTE);
      rhythmPattern.insertBar(bar);
 }

  }

  @Override
public void insertBar(Bar bar, int string){
    ((RhythmPattern)voices.get(string-1)).insertBar(bar);
  }

  @Override
public void insertMark(String name, int notePich){
    //bars.add(name);
    //repetitionMarks.put(name, new StringReturnPoint(notePich, bars.size()));
    ((RhythmPattern)voices.get(notePich-1)).insertMark(name);
  }

 /* public void insertReturn(String mark, int repetitions){
    if(repetitionMarks.containsKey(mark)){
      bars.add(new ReturnPoint(mark, repetitions));
    }
  }*/

/* public void print(){
   for(int s=0; s<this.polyphony;s++){
      System.out.println(s + "==================");
      RhythmPattern rp = ((RhythmPattern)voices.get(s));
      if(rp.bars.size() ==0){

      }else{
        ( (RhythmPattern) voices.get(s)).print();
      }*/




 /* public static void main(String[] args) {
    GuitarRhythmPattern gpr = new GuitarRhythmPattern(4);

    //Bar bs0 = new Bar(2,4);
  //  bs0.addRhythmEvent(bs0.HALF_NOTE,1);
  //  bs0.addRhythmEvent(bs0.QUARTER,1);
  //  bs0.addRhythmEvent(bs0.QUARTER,1);
   // bs0.addRhythmEvent(bs0.QUARTER,0);

  //  gpr.insertBar(bs0, arpeggio);


    GuitarBar bs1 = new GuitarBar(2,4);
    bs1.addRhythmEvent(bs1.QUARTER_NOTE,1,0,127,bs1.THUMB);
    bs1.addRhythmEvent(bs1.QUARTER_NOTE,1);
    bs1.addRhythmEvent(bs1.QUARTER_NOTE,0);
    bs1.addRhythmEvent(bs1.QUARTER_NOTE,0);

   gpr.insertBar(bs1,0);

   Bar bs2 = new Bar(6,4);
   bs2.addRhythmEvent(bs1.QUARTER_NOTE,0);
   bs2.addRhythmEvent(bs1.QUARTER_NOTE, 1);
   bs2.addRhythmEvent(bs1.QUARTER_NOTE, 0);
   bs2.addRhythmEvent(bs1.QUARTER_NOTE, 0);
   //System.out.println(bs2.getSignatureDistance());
   gpr.insertBar(bs2, 1);

   Bar bs3 = new Bar(4,4);
  // bs3.addRhythmEvent(bs1.QUARTER,0);
   bs3.addSingleRhythmEvent(bs1.QUARTER_NOTE, 1,2);
  // bs3.addRhythmEvent(bs1.QUARTER, 1);
  // bs3.addRhythmEvent(bs1.QUARTER, 0);

   gpr.insertBar(bs3, 2);

   Bar bs4 = new Bar(2,4);
   bs4.addRhythmEvent(bs1.QUARTER_NOTE, 0);
   bs4.addRhythmEvent(bs1.QUARTER_NOTE, 0);
   bs4.addRhythmEvent(bs1.QUARTER_NOTE, 0);
   bs4.addRhythmEvent(bs1.QUARTER_NOTE, 1);

   gpr.insertBar(bs4, 3);

   gpr = LibraryGRP.getSimpleArpeggio();
   gpr.print();
   Musician p = null;
       //Player p = null;
       try {
         p = new Musician();
       }catch(Exception ex){

       }

       p.play(gpr);
  }*/



  public boolean isStrumming(int nPluckHandFingers){
   if(getSimultaneousAttacks() > nPluckHandFingers){
     return true;
   }
  return false;
}

public boolean isFullArpeggio(){ // the maximum polyphony is will be reached in a certain time.
  if(getSimultaneousAttacks() == this.getPolyphony()){
  return true;
}
return false;

}

  public void setMinimumPolyphony(int minPolyphony) {
     minimumPolyphony = minPolyphony;
  }

  public void setStrokeMode(int strokeMode) {
    this.strokeMode = strokeMode;
  }

  public int getMinimumPolyphony() {
    return minimumPolyphony;
  }

  public int getStrokeMode() {
    return strokeMode;
  }

  /*class  StringReturnPoint {
   int notePitch;
   int bar;

   StringReturnPoint( int notePitch,int bar){
     this.notePitch = notePitch;
     this.bar = bar;
   }

 }*/
}
