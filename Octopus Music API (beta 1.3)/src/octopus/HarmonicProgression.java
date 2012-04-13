package octopus;


import java.util.*;

import octopus.communication.MusicalEventSequence;

/**
 * <code>HarmonicProgression</code> is used instantiate a group of Chords based on the scale degrees and key.
 *  The HarmonicProgression is to Chords what the Scale is to Notes.
 *  <p> The scale degrees symbols that can be used are: I, II, III, IV, V, VI, VII.  Uppercase for Major and
 *   lowercase for  minor chords."</p>
 *  
 *<p><a href="doc-files/HarmonicProgressionExample.java"> Code example: A 12-bar blues HarmonicProgression.</a></p>
 * @see Harmony
 *
 */
public class HarmonicProgression implements Playable{
 String name;
 Vector<ScaleDegree> degrees = new Vector<ScaleDegree>();

  public HarmonicProgression() {
  
  }

  /**
   * @param name An identification for the HarmonicProgression. for example: "12-Bar-Blues"
   */
  public HarmonicProgression(String name) {
    this.name = name;
  }

  /**
   *
   * @param scaleDegree I, II, III, IV, V, VI, VII( Uppercase = Major, lowcase = minor)
   */
  public void addScaleDegree(String scaleDegree) throws ChordNotationException{
   addScaleDegree(scaleDegree, new Interval[0]);
 }
 /**
  *
  * @param scaleDegree I, II, III, IV, V, VI, VII( Uppercase = Major, lowercase = minor)
  * @param intervals Interval[] Set of intervals to enhance the scale degree.
  * e.g HarmonyProgression.addScaleDegree("V", IntervalCollection.setimaMaior) = V7
  */
 public void addScaleDegree(String scaleDegree, Interval[] intervals)throws ChordNotationException{
    ScaleDegree sd = new ScaleDegree(scaleDegree,intervals);
    degrees.add(sd);
  }

  public void addScaleDegree(String scaleDegree, Interval interval)throws ChordNotationException{
  Interval[] intervals = new Interval[1];
  intervals[0] = interval;
  ScaleDegree sd = new ScaleDegree(scaleDegree,intervals);
  degrees.add(sd);
}
/**
 *
 * @param key Note from where the scale degrees will start be counted.
 * @return Harmony Chords designated by the scales degrees and the ket note.
 */
public Chord[] getChords(Note key){
  Vector<Chord> chords = new Vector<Chord>();

  for(int i =0;i<degrees.size();i++){
    ScaleDegree sd = (ScaleDegree)degrees.get(i);
    Chord chord = Chord.getChord(key,sd);
    chords.add(chord);
  }
  return (Chord[])chords.toArray(new Chord[0]);
}

  @Override
public String toString(){
    String retorno = "";
    for (int i=0;i<degrees.size()-1;i++){
      retorno +=((ScaleDegree)degrees.get(i)).toString() + " - ";
    }
    retorno+=((ScaleDegree)degrees.get(degrees.size()-1));
    return retorno;
  }

  /**
   * Represents one of the degrees in a Harmonic Prossession. E.g I or V7
   */
  class ScaleDegree {
    Interval[] intervals;
    String scaleDegree;
    int numericValue;
    int mode; //1 Major; 0 Minor

    ScaleDegree(String scaleDegree, Interval[] intervals)throws ChordNotationException{
      this.scaleDegree = scaleDegree;
      this.intervals = intervals;
      this.numericValue = getNumericValue(scaleDegree);
      if(numericValue == -1){
    	  throw new ChordNotationException("Invalid scale degree symbol: " + scaleDegree,scaleDegree );
      }
      
      if(isUpperCase(scaleDegree)){
        mode = 1;
      }else{
        mode = 0;
      }
    }

    public int getNumericValue(String romanValue){
      String[] roman = {"I", "II", "III", "IV", "V", "VI", "VII"};
      for (int i=0; i<roman.length;i++){
        if(romanValue.equalsIgnoreCase(roman[i])){
          return i+1;
        }
      }
      return -1;
    }

    @Override
	public String toString(){
      String retorno = scaleDegree;
      if (intervals!=null){
        for(int i=0;i<intervals.length;i++){
          retorno+=intervals[0].getSymbol()+" ";
        }
      }
      return retorno;
    }
    public boolean isUpperCase(String text){
      String copyU = text.toString();
      copyU = copyU.toUpperCase();
      if(text.equals(copyU)){
        return true;
      }
      return false;
    }

  }

/**
 * It will play the Harmonic Progression using the C note as the key.
 * @see getChord; 
 * @return
 */
public MusicalEventSequence getMusicalEventSequence() {

    Chord[] chords =  this.getChords(WesternMusicNotes.getC());


    RhythmPattern rhythmPattern = RhythmPattern.getDemoRhythmPattern();
    Harmony harmony = new Harmony(chords,rhythmPattern) ;
    
    return harmony.getMusicalEventSequence();
}
}
