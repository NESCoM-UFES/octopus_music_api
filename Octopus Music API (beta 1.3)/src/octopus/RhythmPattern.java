package octopus;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import octopus.communication.MusicalEventSequence;
/**
 * A Rhythm Pattern is the rhythmic line of a MusicalComponent. It is composed of bars, marks and returning points to
 * these marks. Note that, in the Octopus Music API, the rhythmic line is defined independently from the melodic and harmonic lines.
 * <p> The image bellow illustrates the internal structure of RhythmPattern.
 * <center><img src="doc-files/RhythmPattern.jpg"></center>
 *
 * @see Arpeggio
 * @see Bar
 */
public class RhythmPattern  implements Serializable, Playable,RhythmConstants{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name = "";
	protected Vector<Serializable> bars = new Vector<Serializable>();
	//float bpm = 120;
	protected Hashtable<String, Integer> repetitionMarks = new Hashtable<String, Integer>();

	Note pitchRhythmNote = Notes.getC();   

  // Working, but not according to the new project. Bust me adpated to create a
  // RhythmPAttern from a MusicalEventSequence.
  /*public RhythmPattern(Sequence sequence) {
    OctopusReceiver r = new OctopusReceiver();
    float duration = 0;
    Track[]  t = sequence.getTracks();
     if(t.length >0){
       Bar bar = new Bar(); //verificar relacao resolution = beats or refNote
       float nTempo = r.convertTempo(sequence.getMicrosecondLength()); //errado
     //  bpm = (float) (Math.round(nTempo*100)/100.0f);
       int i=0;
       while (i< t[0].size()){
         MidiEvent midiEvent = t[0].get(i);
         MidiMessage midiMessage = midiEvent.getMessage();

         if(midiMessage instanceof ShortMessage){
           MidiEvent nextEvent = t[0].get(i+1);
           float durationTicks =nextEvent.getTick() - midiEvent.getTick();
           duration = (durationTicks/sequence.getResolution())/4;

          if(midiMessage.getStatus()==ShortMessage.NOTE_ON){
           int status = ((ShortMessage)midiMessage).getData2()>0?1:0;
            bar.addRhythmEvent(duration, status);

          }
         }

         i++;
       }
       insertBar(bar);
     }

  }*/
  public RhythmPattern(){
	  this.name = name;
  }

  public RhythmPattern(String name){
	  this.name = name;
  }

  /**
   * Quick way to create a constant RhythmPattern. For example
   * One Bar 4/4 (nUnit/nMeasurement) repeated 3 times (nRepetition).
   * @param nUnit Metre numerator;
   * @param nMeasurement Meter Denominator
   * @param nRepetition Number of timer the RythmPattern repeats.
   */
  public RhythmPattern(int nUnit, int nMeasurement, int nRepetition){
	 
	  Bar bM = new Bar(nUnit,nMeasurement);
      for (int i=0; i<nMeasurement; i++){
    	  bM.addRhythmEvent((double)(1.0/nMeasurement),1);
      }
	 
	 insertMark("beginning");
	 insertBar(bM);
	 insertReturn("beginning",nRepetition);
	 
  }
	
	
  public void insertBar(Bar bar){
    bars.add(bar);
  }


  /**
   * The bars are inserted sequentially  even when an array is passed. This means
   * that the order of input is related to the order of the bars in the
   * array.
   * @param bars Array of bars
   *
   * @see Bar
 */
  public void insertBar(Bar[] bars){
    this.bars.addAll(Arrays.asList(bars));
  }

  /**
   * 
   * @param returnPointsToBar
   * @return
   */
  /**
   * Return all the bars that belongs to the rhythm pattern.
   * @param computeRepetition If true, the repetition marks will be taken in consideration and the
   * number of bars returned. If false, ignores the repetition marks and returns
   * the same number of bars as inputed by the user. The default is true;
   */
   public Bar[] getBars(boolean computeRepetition){
      
	  if (computeRepetition){
		  return getBars();
	  }
	   Vector<Serializable> barsTemp = new Vector<Serializable>();
            
        for (int e = 0; e < bars.size(); e++) {
           if(bars.get(e) instanceof Bar){
        	   barsTemp.add(bars.get(e));
           }
          }
        
      return (Bar[])barsTemp.toArray(new Bar[0]);
    }
  
  
/**
 * Return all the bars that belongs to the rhythm pattern. 
 * The bars will be repeated acconding to the marks as return points. 
 *  
 */
 public Bar[]  getBars() {
	   Vector<Serializable> barsTemp = new Vector<Serializable>();
	    Hashtable<String,Integer> marks = new Hashtable<String,Integer>(); 
    	
	       for (int e = 0; e<bars.size();e++){
	         if(!(bars.get(e) instanceof Bar)){
	           if(bars.get(e) instanceof String){
	             
	               String markName = (String)bars.get(e);
	        	   marks.put(markName, new Integer(e));
	           }
	           if (bars.get(e) instanceof RhythmPattern.ReturnPoint) {
	        	   RhythmPattern.ReturnPoint returnPoint = (RhythmPattern.ReturnPoint) bars.get(e);
	        	   if ( returnPoint.counter > 0) {
	        		
	        		   returnPoint.decrease();
	                   e = (Integer)marks.get(returnPoint.name).intValue();
	               
	                 }
	             else {
	               ( (RhythmPattern.ReturnPoint) bars.get(e)).reset();
	             }
	           }

	         }else{
	        	 barsTemp.add(bars.get(e));

	         }
	     }
	       return (Bar[])barsTemp.toArray(new Bar[0]);
		}
 
 /**
  * Return all the Bar.RythmEvents inside the RhythmPattern in order of execution. 
  * @param mergeTieRhythmEvents if true, merges the tied notes. The default is true.
  */
 public Bar.RhythmEvent[] getRhythmEvents(boolean mergeTieRhythmEvents){
	 if(mergeTieRhythmEvents){
		 return getRhythmEvents();
	 }
	 
	 Vector<Bar.RhythmEvent>  rhythmEvents = new Vector<Bar.RhythmEvent>();
	 Bar[] bars = this.getBars();
	 for (int i = 0; i < bars.length; i++) {
		 rhythmEvents.addAll(Arrays.asList(bars[i].getRhythmEvents()));
	}
	return (Bar.RhythmEvent[])rhythmEvents.toArray(new Bar.RhythmEvent[0]);
	 	  
  }
 
 
 /**
  * Return an array of  Bar.RhythmEvent in which the tied Bar.RhythmEvent have been merged into
  * a single Bar.RhythmEvent if mergeTieRhythmEvents = true.
  * 
  * 
  */
 public Bar.RhythmEvent[] getRhythmEvents(){   
	 
 Vector<Bar.RhythmEvent> newRhythmEvents = new Vector<Bar.RhythmEvent>();
  Bar.RhythmEvent[] rhythmEvents = getRhythmEvents(false);
 //Merge the duration of tied RhythmEvents into a new RhythmEvents;
	   for (int i = 0; i < rhythmEvents.length; i++) {
	    	 Bar.RhythmEvent rhythmEvent =(Bar.RhythmEvent) rhythmEvents[i];
	    	 
	    	 if ((rhythmEvent.tie) && (i < rhythmEvents.length-1)) {	    		
	    		 rhythmEvents[i].duration += rhythmEvents[i+1].duration;	
	    		 newRhythmEvents.add(rhythmEvents[i]);
	    		 i++;
	    	 }else{
	    		 newRhythmEvents.add(rhythmEvents[i]);
	    	 }
	   }
	
	    	 
	return (Bar.RhythmEvent[])newRhythmEvents.toArray(new Bar.RhythmEvent[0]);
          
 }



 
 
  public void replaceBar(Bar oldBar, Bar newBar){
    int index = bars.indexOf(oldBar);
    bars.remove(oldBar);
    bars.add(index,newBar);
  }



  public double getDuration(){
    int se = 0;
    double duration = 0.0;
    for (int e = 0; e < bars.size(); e++) {
      if (! (bars.get(e) instanceof Bar)) {
        if (bars.get(e) instanceof String) {
          se = e;
        }
        if (bars.get(e) instanceof RhythmPattern.ReturnPoint) {
          if ( ( (RhythmPattern.ReturnPoint) bars.get(e)).counter > 0) {
            ( (RhythmPattern.ReturnPoint) bars.get(e)).decrease();
            e = se;
          }else{
               ( (RhythmPattern.ReturnPoint) bars.get(e)).reset();
             }

        }

      }
      else {
        Bar bar = (Bar) bars.get(e);
        duration += bar.getDuration();
      }
    }
    return duration;
  }


  /*
   * @see Bar.getSmallestNote()
   */
  public double getSmallestNote(){
   double smallestNote = 0.0;
   int i =0;
    while(i<bars.size()){
      if(bars.get(i)instanceof Bar){
          smallestNote = ((Bar)bars.get(i)).getSmallestNote();
      }
      i++;
    }
    return smallestNote;
 }


 /**
  * The repetition mechanism is composed by two parts: a mark and a repetition point.
  * The mark is placed like a bar in a certain point of the rhythm bar and every time
  * the return point is reached, the pointer goes back to the mark. This loop keeps going
  * while the number of repetitions specified in the return point is bigger than zero.
  * The mark is addressed but its name.
  *
  * @param name Name that address the mark.
  *
  */
  public void insertMark(String name){
    bars.add(name);
    repetitionMarks.put(name, new Integer(bars.size()));
  }

/**
 * @param mark Name of the mark where the it should return.
 * @param nRepetitions Number of repetitions
 */
  public void insertReturn(String mark, int nRepetitions){
    if(repetitionMarks.containsKey(mark)){
      bars.add(new ReturnPoint(mark, nRepetitions));
    }
  }

  public String toString(){
    int i =0;
    String retorno = "";
    while(i<bars.size()){
      if(bars.get(i)instanceof Bar){
    	  retorno+=((bars.get(i)+"\n"));
      }else{
        if(bars.get(i)instanceof ReturnPoint){
          int r = ((ReturnPoint)bars.get(i)).repetitions;
          String m = ((ReturnPoint)bars.get(i)).name;
          retorno+=("Go to mark:" + m + " " +r + " time(s) \n");
        }else{
        	retorno+=(bars.get(i)+"\n");
        }
      }
      i++;
    }
   return retorno;
  }

  public static RhythmPattern getDemoRhythmPattern(){
      RhythmPattern rhythmpattern = new RhythmPattern();

      Bar bM = new Bar(4,4);


      //bM.addRhythmEvent(bM.EIGHT_NOTE,Bar.RHYTHM_EVENT_NOTE);
      bM.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
     // bM.addRhythmEvent(bM.EIGHT_NOTE,Bar.RHYTHM_EVENT_NOTE);
      bM.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
      bM.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
      bM.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);



      rhythmpattern.insertMark("beginning");
      rhythmpattern.insertBar(bM);
      rhythmpattern.insertReturn("beginning",3);

      return rhythmpattern;
    }


  public void store( File file) throws
        FileNotFoundException, IOException {

    if (!file.getName().endsWith(".rtp")) {
      file = new File(file.getPath() + ".rtp");
    }

      if (file != null) {
        if (!file.getName().endsWith("rtp")) {
          file = new File(file.getPath() + ".rpt");
        }
        FileOutputStream fOut = new FileOutputStream(file);
        ObjectOutput out = new ObjectOutputStream(fOut);
        out.writeObject(this);
        out.flush();
        out.close();
      }


  }

  public static RhythmPattern load(File file) throws FileNotFoundException,
        IOException, ClassNotFoundException {
    RhythmPattern rhythmPattern = null;;
    if (file != null) {
      FileInputStream fIn = new FileInputStream(file);
      ObjectInputStream in = new ObjectInputStream(fIn);
      rhythmPattern = (RhythmPattern) in.readObject();

      in.close();

    }

    return rhythmPattern;

}


  /**
    *
   */
  protected class ReturnPoint implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
    int repetitions;
    int counter =0;

    ReturnPoint(String name, int repetitions){
      this.name = name;
      this.repetitions = repetitions;
      counter = repetitions;
    }

    void reset(){
      counter = repetitions;
    }

    void decrease(){
      counter--;
    }

  }


/*public MusicalEventSequence getRhythmMusicalEvents() {
	MusicalEventSequence p =new MusicalEventSequence();    
    Bar[] bars = getBars();	
 
    double time= 0; 
       for (int i = 0; i<bars.length;i++){

           MusicalEventSequence oneBarSequence = bars[i].getRhythmMusicalEvents();
           oneBarSequence.delayEvents(time);                   
                      
           p.addMusicalEventSequence(oneBarSequence);
           
           time+=bars[i].getDuration();
         }
     
       return p;
	}*/

/*
 	 


  }
*/
public static RhythmPattern getRandomRhythmPattern(int nNotes){
	  RhythmPattern rhythmpattern = new RhythmPattern();
		 
	  double[] accentuation = new double[nNotes];
	  for (int i = 0; i < accentuation.length; i++) {
	      accentuation[i]=RhythmConstants.ACCENT_STRONG;
	  }
	  
	  Bar bM = new Bar(4,4);
	  for(int i=0;i<nNotes;i++){
	      int type = Math.random()<0.9?1:0;
	      double duration = (1*Math.random());
		  bM.addRhythmEvent(duration,type);
	      
	  }
	  
	  rhythmpattern.insertBar(bM);
	  return rhythmpattern;
}

public static RhythmPattern getConstantRhythmPattern(double rhythmPattenDuration, double noteDuration ){
	  RhythmPattern rhythmpattern = new RhythmPattern();
	 
	  
	  Bar bM = new Bar(4,4);
	  double countDutation = 0.0;
	  while (countDutation <rhythmPattenDuration){	 
	      bM.addRhythmEvent(noteDuration,1);
	      countDutation+=noteDuration;	      
	  }
	  
	  rhythmpattern.insertBar(bM);
	  return rhythmpattern;
	  
}

public static RhythmPattern getConstantRhythmPattern(int nNotes, double noteDuration){
	  RhythmPattern rhythmpattern = new RhythmPattern();
	 
	  double[] accentuation = new double[nNotes];
	  for (int i = 0; i < accentuation.length; i++) {
	      accentuation[i]=RhythmConstants.ACCENT_STRONG;
	  }
	  
	  Bar bM = new Bar(nNotes,((int)(1/noteDuration)),accentuation);
	  for(int i=0;i<nNotes;i++){
	      bM.addRhythmEvent(noteDuration,1);
	      
	  }
	  
	  rhythmpattern.insertBar(bM);
	  return rhythmpattern;
	  }

/*public MusicalEventSequence getMusicalEventSequence() {
	 MusicalEventSequence rhythmPatternSequence = new MusicalEventSequence();	
	  Bar.RhythmEvent[] rhythmEvents = getRhythmEvents();
	  
	  double time= 0;

	  for (int i = 0; i < rhythmEvents.length; i++) {
		  double duration =  rhythmEvents[i].duration;

		  if ( (rhythmEvents[i].tie) && (i < rhythmEvents.length - 1)) {
			  i++;
			  duration += rhythmEvents[i].duration;
		  }


		  if(rhythmEvents[i].type==1){ // Note rest
			  MusicalEvent meOct = new MusicalEvent(i, time, pitchRhythmNote, duration,
					                               rhythmEvents[i].velocity);
		  MusicalEvent meOctOff = new MusicalEvent(i, time + duration, pitchRhythmNote,0, 0);
		  
		  rhythmPatternSequence.addMusicalEvent(meOct);
		  rhythmPatternSequence.addMusicalEvent(meOctOff);
		  }
		  time += duration;
	  }

	  return rhythmPatternSequence;	
}*/
public MusicalEventSequence getMusicalEventSequence() {

Note[] notes = {this.pitchRhythmNote};

Melody melody = new Melody(notes, this);
melody.setCircularListNotes(true);

return melody.getMusicalEventSequence();

}

}
