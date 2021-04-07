package octopus;

import java.io.Serializable;
import java.util.Vector;

import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;
import octopus.util.Fraction;

  /**
   * A Bar is a container of <code>Bar.RhythmicEvent</code> organised according to the metre. 
   * It represents a simple rhythmic phrase. Only rhythmic events (inner class
   * Bar.RhythmEvent) can be inserted into a Bar. The signature of the bar is
   * represented in form of a fraction, were nUnits is the numerator and
   * measurementUnit is the denominator. 
   *
   *
   * @see             RhythmPattern
   * @see             Arpeggio
   *
     */

public class Bar  implements Serializable,Playable, RhythmConstants, RhythmPattern.Things{

 private static final long serialVersionUID = 1L;

  protected int nUnits;
  protected int measurementUnit;
  protected Fraction metre;
  protected double[] accentuations;

  protected Vector<RhythmEvent> rhythmEvents = new Vector<RhythmEvent>(); //float[]

  protected  boolean autoSignature =true; //not used in version 1.0

    protected Bar(){
	  
    this.nUnits = 0;
    this.measurementUnit = 0;
    metre =  new Fraction(nUnits, measurementUnit, false);
    this.autoAccentuation();
    autoSignature =false;

  }
    /**
     * @param textualNotation Similar as EarSkecth makebeat. 
     * '0' starts playing a clip.
	 * '-' is a rest, meaning silence.
     *  +' extends the duration into the next sub-beat. It always follows a 0 or +.
     *  i.e: "0-00-00-0+++0+0+"
     *
   */
    public void addRhythmEvent(String textualNotation, double subBeatDuration) {
	    int i = 0;
	    double duration = 0;
	    while(i < textualNotation.length())  {	    	
	    	char c = textualNotation.charAt(i);		
			if(c=='0') {
				if(duration > 0) {
					this.addRhythmEvent(duration, RHYTHM_EVENT_NOTE);				
				}
				duration=subBeatDuration;						
			}else {
				if(c== '+') {
					duration+=subBeatDuration;
				}else {
					if(c=='-') {
						if(duration > 0) {
							this.addRhythmEvent(duration, RHYTHM_EVENT_NOTE);							
							duration = 0;
						}
						this.addRhythmEvent(subBeatDuration, RHYTHM_EVENT_REST);	
					}
				}
			}
			i++;
	    }
	    if(duration > 0) this.addRhythmEvent(duration, RHYTHM_EVENT_NOTE);			    
    }
			
    
    
    /**
   * Creates a Bar according to the specified metre. e.g 2/4
   * @param nUnits The number of units in each bar. e.g 2;
   * @param measurementUnit The unit of measurement, relative to the semibreve. e.g 4 (quarter note)
   */
  public Bar(int nUnits, int measurementUnit, double[] durations, int[] types) {
    this.nUnits = nUnits;
    this.measurementUnit = measurementUnit;
    metre =  new Fraction(nUnits, measurementUnit, false);
    this.autoAccentuation();
    autoSignature =false;
    
    this.addRhythmEvent(durations, types);
  }

  /**
   * Creates a Bar according to the specified metre. e.g 2/4
   * @param nUnits The number of units in each bar. e.g 2;
   * @param measurementUnit The unit of measurement, relative to the semibreve. e.g 4 (quarter note)
   */
  public Bar(int nUnits, int measurementUnit) {
    this.nUnits = nUnits;
    this.measurementUnit = measurementUnit;
    metre =  new Fraction(nUnits, measurementUnit, false);
    this.autoAccentuation();
    autoSignature =false;
  }
  

  /**
   * Creates a Bar according to the specified metre and indicates where the beats should be accentuated.
   * @param nUnits The number of units in each bar. e.g 2;
   * @param measurementUnit The unit of measurement, relative to the semibreve. e.g 4 (quarter note)
   * @param accentuations array of doubles indicating the accentuation. e.g. ACCENT_STRONG = 1.0;   */
  public Bar(int nUnits, int measurementUnit, double[] accentuations) {
    this.nUnits = nUnits;
    this.measurementUnit = measurementUnit;
    metre =  new Fraction(nUnits, measurementUnit, false);
    this.accentuations = accentuations;
  }


  /**
   * @param duration Number between 0 and 1 determining  the duration of the note.
   * e.g. WHOLE_NOTE = 1, QUARTER_NOTE = 0.25
   *    *
   * @param type integer specifying whether is a note or a rest. INPUT_NOTe = 1,
   *  RHYTHM_EVENT_NOTE = 0;
   *
   * @see RhythmConstants
   *
 */
  public void addRhythmEvent(double duration, int type){
    rhythmEvents.add(new RhythmEvent(duration, type));

  }


/**
 * @param tie  specifies whether or not the note duration is tied with the next one.
 *
*/
  public void addRhythmEvent(double duration, int type, boolean tie){
    rhythmEvents.add(new RhythmEvent(duration, type, tie));

 }


 /**
  * Insert a single RhythmEvent on the indicated beat and fill the rest of the
  * bar with rests.
  *
  * @param  beat position where the only note of the bar will take place
 */
 public void addSingleRhythmEvent(double duration, int type, int beat){
   int i=1;
   double supposedValue = ((double)nUnits/measurementUnit);
   double reValue = supposedValue/nUnits;
   boolean before = true;
   while(i<=nUnits){
     if(i==beat){
       rhythmEvents.add(new RhythmEvent(duration, type));

       if(duration <reValue){
         rhythmEvents.add(new RhythmEvent(reValue-duration,RhythmConstants.RHYTHM_EVENT_REST));
       }
       before= false;
     }else{
       if(before){
         rhythmEvents.add(new RhythmEvent(reValue,RhythmConstants.RHYTHM_EVENT_REST));
       }else{
         rhythmEvents.add(new RhythmEvent(supposedValue - getDuration(),RhythmConstants.RHYTHM_EVENT_REST));
         break;
       }
     }
     i++;
   }

 }



 public int getMeasurementUnit(){
   return measurementUnit;
 }


 public int getNumberOfUnits(){
   return nUnits;
 }

 
 
 /**
  * Insert tuplets in the bar.
  *
  * @param durations Array of note's duration;
  * @param types Array of the note's type (INPUT_NOTE OR RHYTHM_EVENT_NOTE)
  */
 public void addRhythmEvent(double[] durations, int[] types){
    for (int i=0; i<durations.length;i++){
      rhythmEvents.add(new RhythmEvent(durations[i], types[i]));

    }
}
 

 /**
  * Insert tuplets in the bar.
  *
  * @param durations Array of note's duration;
  * @param types Array of the note's type (INPUT_NOTE OR RHYTHM_EVENT_NOTE)
  * @param tupletValue Duration by which the note's duration will be splitted.
  */
 public void addRhythmEvent(int[] types, double tupletValue){
    double value = (tupletValue/types.length);
    for (int i=0; i<types.length;i++){
      rhythmEvents.add(new RhythmEvent(value, types[i]));

    }
}



/**
 * @param tie Specifies if the last note of the tuplet is tied with tht next note.
 */
  public void addRhythmEvent( int[] types, double tupletValue, boolean tie){
  double value = (tupletValue/types.length);
  
  for (int i=0; i<types.length-1;i++){
   
	  rhythmEvents.add(new RhythmEvent(value, types[i]));

  }
  rhythmEvents.add(new RhythmEvent(value, types[types.length -1], tie));

}



 public Bar.RhythmEvent[] getRhythmEvents(){
    double[] accentuations = this.getAccentuations(); 
    int circularIndex = 0;  
    for (int i = 0; i < this.rhythmEvents.size(); i++) {
    	//if (circularIndex <accentuations.length){
    	 if(rhythmEvents.get(i).type == RHYTHM_EVENT_NOTE){
    	    rhythmEvents.get(i).velocity = accentuations[circularIndex];
    	 }else{
    		 rhythmEvents.get(i).velocity = 0; //REST
    	 }    	 
    	 circularIndex = (circularIndex+1)<accentuations.length?(circularIndex+1):0;
     /*  }else{
    	   circularIndex = 0;
       }*/
	}
        
    return rhythmEvents.toArray(new Bar.RhythmEvent[0]); 
   
 }



/**
 * Returns a dotted rhytmic note(duration of note + 50%)
 *
 * @param  value  original duration;
 * @return      dotted rhytmic note.
 */
  public double getDottedValue(double value){
    return value+=(value/2);
  }



  /**
   * Returns a double dotted rhytmic note(dotted note + 50%)
   *
   * @param  value  original duration;
   * @return      double dotted rhytmic note.
 */
  public double getDoubleDottedValue(double value){
    double valueDot = (value/2);
    double valueDot2 = (valueDot/2);

    return value+= valueDot + valueDot2;
  }



  /**
   * Returns an array of doubles storing values between 0 and 1 related to
   * accentuation of the notes.
   *
   * @return      accentuation value.
   * 
 */
  public double[] getAccentuations(){
    if(this.accentuations.length==0){
      autoAccentuation();
    }
    return this.accentuations;
  }



  /**
   *
   * @param  eventIndex  note index;
   * @return      accentuation value.
   * 
 */
  public double getAccentuation(int eventIndex){
    if(this.accentuations.length==0){
      autoAccentuation();
    }
    int i=0;
    int pos=0;
    while (i<rhythmEvents.size()){
       if(pos >= this.accentuations.length){
         pos =0;
       }
       if(i==eventIndex){
         return this.accentuations[pos];
       }
        i++;
        pos++;
     }
    return RhythmConstants.ACCENT_HALF_STRONG;
  }


  // not fully functional on version 1.0.
  private void autoAccentuation(){
    switch (measurementUnit){
      case 4:    double [] temp = {ACCENT_STRONG,ACCENT_WEEK,ACCENT_HALF_STRONG,ACCENT_WEEK};
            accentuations = temp;break;
      case 3:    double[] temp2 = {ACCENT_STRONG,ACCENT_WEEK,ACCENT_WEEK};
            accentuations = temp2;break;
      case 2:    double[] temp3 = {ACCENT_STRONG,ACCENT_WEEK};
            accentuations = temp3;break;
      default: {
        this.accentuations = new double[rhythmEvents.size()];
        for(int i=0; i< rhythmEvents.size(); i++){
          this.accentuations[i] = ACCENT_HALF_STRONG;
        }
        break;
      }
    }

  }



//Feature estimated for version 1.1. Infer and ypdate based on the events.
/*public void updateSignature(){
    double supposedValue = nUnits/measurementUnit;
    double realValue = 0.0;

    for (int i=0; i<this.rhythmEvents.size(); i++){
      RhythmEvent ev = (RhythmEvent)rhythmEvents.get(i);
      realValue+=ev.value;
    }

  }*/



/**
   *  Returns a positive number if the sum of rhythm events durations exceed
   *  the what time signature estabeleshed . If the sum of duration is less than
   *  the expected, return a negative value with the value remaining to achieve
   *  what the metre estabeleshed. If the rhythm events is  adequate to the metre
   *  returns 0.0;
   *
   *  @return      distance from the signature.
   *
 */
 public double getSignatureDistance(){
   double supposedValue = (double)nUnits/(double)measurementUnit;
    double realValue = getDuration();
    return realValue - supposedValue ;
 }



 /**
  * @return    The sum of the durations of the notes(bar duration).
 */
public double getDuration(){
  double duration = 0.0;

   for (int i=0; i<this.rhythmEvents.size(); i++){
     RhythmEvent ev = rhythmEvents.get(i);
     duration+=ev.duration;
   }
    return duration;
}

/*public Bar.RhythmEvent[] getRhythmEvents(boolean mergeTieRhythmEvents){
	if(!mergeTieRhythmEvents){
		return getRhythmEvents(); //Return RhythmEvents with Tie (ligato) information;
	}else{ //Merge the duration of tied RhythmEvents into a new RhythmEvents;
	   Vector<RhythmEvent> newRhythmEvents = new Vector<RhythmEvent>();
	     for (int i = 0; i < rhythmEvents.size(); i++) {
	    	 RhythmEvent rhythmEvent =(Bar.RhythmEvent) rhythmEvents.get(i);
	    	 if ((rhythmEvent.tie) && (i < rhythmEvents.size()-1)) {
	    		 RhythmEvent newRhythmEvent = new RhythmEvent()
	    		 double duration = ((Bar.RhythmEvent) rhythmEvents.get(i)).value;
            }
             i++;
              duration += ((Bar.RhythmEvent)rhythmEvents.get(i)).value;
             }
             newRhythmEvents.add(new Double(duration));
    }
   double[] arrayReturn = new double[newRhythmEvents.size()]; 
	for (int i = 0; i < newRhythmEvents.size(); i++) {
		arrayReturn[i] = newRhythmEvents.get(i).doubleValue();
	}
   
   return arrayReturn;
}*/



/**
   * @return The duration of smallest rhythm event inside the bat.
   * Attention: If Bar is empty, the smallest note value will be 0.0;
   *
  */
  public double getSmallestNote(){
    double smallestNote = 0.0;

     for (int i=0; i<this.rhythmEvents.size(); i++){
       RhythmEvent ev = rhythmEvents.get(i);
       if(((ev.type==1)&&(ev.duration<smallestNote)) || (i==0)){
         smallestNote = ev.duration;
       }

     }
      return smallestNote;
}


@Override
public String toString(){
 /* if(autoSignature){
    updateSignature();
  }*/
  String retorno = metre.toString();
  retorno+=" ( ";
  for(int i=0;i <this.rhythmEvents.size(); i++){
    RhythmEvent rEv = rhythmEvents.get(i);
    if(rEv.duration >0.0){
	    Fraction f =  rEv.getFractionValue();
	    
	    retorno+=f.toString();
	    if(rEv.type == 0){
	      retorno+="p";
	    }
	
	    if(rEv.tie){
	      retorno+="L";
	    }
	
	    retorno+= " ";
	  }
  }
      retorno+= ") Distance from the signature: " + this.getSignatureDistance();
  return retorno;
}

public void print(){
	System.out.println(this);
}

/**
 * Represents a note or a rest in the bar.
 */
  @SuppressWarnings("serial")
public class RhythmEvent implements Serializable{
  /** duration*/
  public double duration;
  /** velocity/ dynamics/ loudness. Defined by the bar accentuation*/
  public double  velocity;
  /** 0 = rest; 1 if it has to be played*/
  public int type;
  /** true if is linked with the next note*/
  public boolean tie = false;
  
  

 public RhythmEvent(double duration, int type){
    this.duration = duration;
    this.type = type;
  }

 public  RhythmEvent(double duration, int type, boolean tie){
  this.duration = duration;
  this.type = type;
  this.tie = tie;
 }

  public Fraction getFractionValue(){
    //System.out.println(value);
    int den = (int)Math.round((1/duration));
    return new Fraction(1,den, false);
  }

}
/*public static void main(String[] args) {
  Bar bar = new Bar(2,4);
  bar.addRhythmEvent(RhythmConstants.HALF_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
  bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RHYTHM_EVENT_NOTE,true);
  bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RHYTHM_EVENT_NOTE);

  System.out.println(bar);
  }*/

 @Override
public MusicalEventSequence getMusicalEventSequence(){
	MusicalEventSequence p =new MusicalEventSequence();
    double time = 0;
    for (int i = 0; i < rhythmEvents.size(); i++) {
      double duration = rhythmEvents.get(i).duration;

      boolean tie = rhythmEvents.get(i).tie;
      
      if ((tie) && (i < rhythmEvents.size()-1)) {
        i++;        
        duration += rhythmEvents.get(i).duration;
      }
       int type = rhythmEvents.get(i).type;
       double velocity = type == 1 ? getAccentuation(i): 0;
       
       if(type==1){
    	  Note note = Notes.getC();
    	   MusicalEvent meOct = new MusicalEvent(i, time,note, duration, velocity);
          MusicalEvent meOctOff = new MusicalEvent(i, time + duration,note, 0, 0);
          p.addMusicalEvent(meOct);
          p.addMusicalEvent(meOctOff);
        }
      time+=duration;
    }
    return p;
       
    
 }

  
  
}
