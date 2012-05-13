package octopus;
import octopus.*;
import octopus.util.*;
import octopus.communication.*;
import octopus.communication.midi.*;
import octopus.instrument.*;
import octopus.instrument.fretted.*;


public class BarLibrary {
  public BarLibrary() {
  }

  public static Bar getDemoBar(){
    Bar bar = new Bar(4,4);

    bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
    bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_REST);
    bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE,true);
    bar.addRhythmEvent(Bar.EIGHT_NOTE, Bar.RHYTHM_EVENT_NOTE);
    bar.addRhythmEvent(Bar.EIGHT_NOTE, Bar.RHYTHM_EVENT_REST);


    return bar;
  }
  public static Bar getConstantBar(int beats, int resolution){
    Bar bM = new Bar(beats, resolution);

    for (int i = 0; i < beats; i++) {
      bM.addRhythmEvent((double)(1.0/resolution), 1);
    }


  return bM;
}
public void getTupletBar(){
  Bar bar = new Bar(2,4);
    bar.addRhythmEvent(Bar.QUARTER_NOTE,Bar.RHYTHM_EVENT_NOTE);
    double[ ] reDurations = {Bar.EIGHT_NOTE , 
                             Bar.EIGHT_NOTE, 
                             Bar.EIGHT_NOTE };
    
    int   [ ] reTypes = {Bar.RHYTHM_EVENT_NOTE ,
                         Bar.RHYTHM_EVENT_REST,
                         Bar.RHYTHM_EVENT_NOTE};
    
    boolean isTie = true ;
    double tupletDuration = bar.QUARTER_NOTE;
    
    bar.addRhythmEvent(reTypes, tupletDuration , isTie);
    bar.addRhythmEvent(Bar.SIXTEENTH_NOTE, Bar.RHYTHM_EVENT_NOTE);
    bar.addRhythmEvent(bar.getDottedValue(Bar.EIGHT_NOTE), 
                       Bar.RHYTHM_EVENT_REST); 
}

  
  public static void main(String[] args) {
    System.out.println(BarLibrary.getDemoBar());
  }
}
