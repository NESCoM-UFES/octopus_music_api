package octopus.instrument.fretted;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;

import octopus.Bar;
import octopus.util.Fraction;

/**
 * A GuitarBar extends a Bar with information about the way to generate the
 * RhythmEvents. In a guitar context, the rhythm is recreated by pulling the
 * strings with the pluck hand. The string's attack can be performed in several
 * different ways varying : strength, contact surface(pick, nail, finger),
 * direction (up and down) and region( near sound hole, bridge or fret board).
 * All this parameter can affect the sound and are used as expressiveness
 * parameters by the Guitarist.
 */
public class GuitarBar extends Bar implements Serializable {
 
 private static final long serialVersionUID = 1L;

// protected Vector effects = new Vector(); // maybe for version 2.0;
  protected Hashtable<RhythmEvent, Stroke> strokes = new Hashtable<RhythmEvent, Stroke>();

  public static final int DIRECTION_UP_STROKE = 1;
  public static final int DIRECTION_DOWN_STROKE = 0;

  public static final double REGION_NEAR_BRIDGE = 1.0;
  public static final double REGION_NEAR_MOUTH = 0.5;
  public static final double REGION_NEAR_NOTE = 0;

  public static final double REGION_THUMB_FINGER = 0.5;
  public static final double REGION_INDEX_FINGER = 0.55;
  public static final double REGION_MIDDLE_FINGER = 0.6;
  public static final double REGION_RING_FINGER = 0.65;
  public static final double REGION_LITTLE_FINGER = 0.7;

  public static final String FINGERPICKING_THUMB_FINGER = "P";
  public static final String FINGERPICKING_INDEX_FINGER = "I";
  public static final String FINGERPICKING_MIDDLE_FINGER = "M";
  public static final String FINGERPICKING_RING_FINGER = "A";
  public static final String FINGERPICKING_LITTLE_FINGER = "X";

  public static final int BODY_SLAP_TOP_SOUNDBOARD = 0;
  public static final int BODY_SLAP_BOTTOM_SOUNDBOARD = 1;
  public static final int BODY_SLAP_BRIDGE = 2;

  public static final double PLECTRUM_HARDNESS_FLEXIBLE= 0.1;
  public static final double PLECTRUM_HARDNESS_NORMAL= 0.5;
  public static final double PLECTRUM_HARDNESS_RIGID = 1.0;

  public static final double PLECTRUM_ROUNDNESS_ANGULAR= 0.1;
  public static final double PLECTRUM_ROUNDNESS_REGULAR= 0.6;
  public static final double PLECTRUM_ROUNDNESS_CIRCULAR= 1.0;

  public static final double PLECTRUM_ATTACK_ANGLE_sTRING_PARALLELL= 0.5;

  public static final double PERCUSSIVE_MUTING_FREE = 0.0;
  public static final double PERCUSSIVE_MUTING_MUTED= 1.0;

  public static final double STRING_SLAP_INTENSITY_LOW = 0.0;
  public static final double STRING_SLAP_INTENSITY_HIGH= 1.0;


  public GuitarBar(int nUnits, int measurementUnit) {
    super(nUnits, measurementUnit);
  }


  public void addRhythmEvent(double duration, int type, int StrokeDirection, double StrokeRegion){
  RhythmEvent re = new RhythmEvent(duration, type);
  rhythmEvents.add(re);
  strokes.put(re,new Stroke(StrokeDirection, StrokeRegion));
  }

  public void addSingleRhythmEvent(double duration, int type, int beat, int StrokeDirection, double StrokeRegion){
     addSingleRhythmEvent( duration,  type,  beat,  StrokeDirection,  StrokeRegion,  "n/a");
  }

  public void addSingleRhythmEvent(double duration, int type, int beat,  int StrokeDirection, double StrokeRegion, String finger){
    super.addSingleRhythmEvent( duration,  type,  beat);
    RhythmEvent re = (RhythmEvent)this.rhythmEvents.get(rhythmEvents.size() -1);
    strokes.put(re,new Stroke(StrokeDirection, StrokeRegion, finger));
  }

  public void addRhythmEvent(double duration, int type,  int StrokeDirection, double StrokeRegion, String finger){
    RhythmEvent re = new RhythmEvent(duration, type);
    rhythmEvents.add(re);
    strokes.put(re,new Stroke(StrokeDirection, StrokeRegion,finger));
  }


  public void addRhythmEvent(double duration, int type, Stroke stroke){
    RhythmEvent re = new RhythmEvent(duration, type);
    rhythmEvents.add(re);
    strokes.put(re,stroke);
  }

  public void addRhythmEvent(double duration, int type, boolean tie,Stroke stroke){
    RhythmEvent re = new RhythmEvent(duration, type,tie);
    rhythmEvents.add(re);
    strokes.put(re,stroke);
  }



  public void addBodySlap(int bodySlapPlace){
    //@todo: workaround: define in the stroke
  }


  public void addPercusiveMuting(int event){
    //@todo
  }


  public void setStroke(Bar.RhythmEvent rhythmEvent, GuitarBar.Stroke stroke){
    strokes.put(rhythmEvent,stroke);
  }

  public GuitarBar.Stroke getStroke(Bar.RhythmEvent rhythmEvent){
    if (strokes.containsKey(rhythmEvent)){
      return (GuitarBar.Stroke)strokes.get(rhythmEvent);
    }
    return null;
  }

  public void setSwing(int event, int variation, int type){
    //type 0=fixed, 1 = random
  }
  @Override
public String toString(){
   /* if(autoSignature){
      updateSignature();
    }*/
    // String retorno = "(" +beats+"//"+refNote+") ";
    String retorno = metre.toString();
    retorno+=" ( ";
    for(int i=0;i <this.rhythmEvents.size(); i++){
      RhythmEvent rEv = (RhythmEvent)rhythmEvents.get(i);
      Fraction f =  rEv.getFractionValue();
      retorno+=f.toString();
      if(rEv.type == 0){
        retorno+="p";
      }

      if(rEv.tie){
        retorno+="L";
      }

      if(strokes.containsKey(rEv)){
        Stroke s = (Stroke)strokes.get(rEv);
         retorno+= s.toString();
      }
       retorno+= " ";
    }
        retorno+= ")" + this.getSignatureDistance();
    return retorno;
}
  public static void main(String[] args) {
    //GuitarBar guitarbar = new GuitarBar();
  }

  public static class Stroke extends Properties implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	public int direction; //0 downstroke, upStroke
    public String finger = "n/a"; //pmia
    public double region; // 0 = near bridge 127 - near note
    public double plectrumHardness; //0 = softfinger - 127 hard pick
    public double plectrumShape; //0 = round - 127 triango //verto possiveis formas
    public int bodySlap = -1;
    public double percussiveMuting = 0; //talvez boolean
    public double stringSlapIntensity = 0;
    public double plectrumAttackAngle = 0.5; //Parallel

    public Stroke(int direction,double region){
         this.direction=direction; //0 downstroke, upStroke
         this.region=region; // 0 = near bridge 127 - near note
         //this.pickingType=region; //0 = softfinger - 127 hard pick
         //this.pickForm=region
    }

    public Stroke(int direction,double region,String finger){
     this.finger=finger;
     this.direction=direction; //0 downstroke, upStroke
     this.region=region; // 0 = near bridge 127 - near note
     //this.pickingType=region; //0 = softfinger - 127 hard pick
     //this.pickForm=region
    }

    @Override
	public String toString(){
      String retorno = "[";

      retorno+=direction==1?"\u005e":"v";

        if (region<42){
          retorno+=" x==";
        }else{
          if (region>84){
            retorno+=" ==x";
          }else{
            retorno+=" =x=";
          }
        }

      retorno+= " " + finger;
      return retorno+="]";
    }
}


}
