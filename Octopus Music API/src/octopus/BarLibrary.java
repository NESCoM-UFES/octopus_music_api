package octopus;

public class BarLibrary {
  public BarLibrary() {
  }

  public static Bar getDemoBar(){
    Bar bar = new Bar(4,4);

    bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
    bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_REST);
    bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE,true);
    bar.addRhythmEvent(RhythmConstants.EIGHT_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
    bar.addRhythmEvent(RhythmConstants.EIGHT_NOTE, RhythmConstants.RHYTHM_EVENT_REST);


    return bar;
  }
  public static Bar getConstantBar(int beats, int resolution){
    Bar bM = new Bar(beats, resolution);

    for (int i = 0; i < beats; i++) {
      bM.addRhythmEvent(1.0/resolution, 1);
    }


  return bM;
}
public void getTupletBar(){
  Bar bar = new Bar(2,4);
  
    bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
  
    double[ ] reDurations = {RhythmConstants.EIGHT_NOTE , 
                             RhythmConstants.EIGHT_NOTE, 
                             RhythmConstants.EIGHT_NOTE };
    
    int   [ ] reTypes = {RhythmConstants.RHYTHM_EVENT_NOTE ,
                         RhythmConstants.RHYTHM_EVENT_REST,
                         RhythmConstants.RHYTHM_EVENT_NOTE};
    
    boolean isTie = true ;
    double tupletDuration = RhythmConstants.QUARTER_NOTE;
    
    bar.addRhythmEvent(reTypes, tupletDuration , isTie);
    bar.addRhythmEvent(RhythmConstants.SIXTEENTH_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
    bar.addRhythmEvent(bar.getDottedValue(RhythmConstants.EIGHT_NOTE), 
                       RhythmConstants.RHYTHM_EVENT_REST); 
}

  
  public static void main(String[] args) {
    System.out.println(BarLibrary.getDemoBar());
  }
}
