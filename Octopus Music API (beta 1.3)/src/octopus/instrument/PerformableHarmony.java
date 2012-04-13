package octopus.instrument;

import octopus.*;

import java.util.*;
/**
 *
 * @author Leandro Costalonga
 * @version 1.0
 */

public class PerformableHarmony
    extends Harmony {

  Hashtable<Chord, ChordShape> chordShapes = new Hashtable<Chord, ChordShape>();

  public  PerformableHarmony(RhythmPattern rhythmPattern){
    super(rhythmPattern);
  }

  public  PerformableHarmony(Chord[] chords,RhythmPattern rhythmPattern){
    super(chords, rhythmPattern);
  }

  public void addChord(Chord chord, ChordShape chordShape){
    chords.add(chord);
    chordShapes.put(chord,chordShape);
  }

  public void addChord(Chord chord, ChordShape chordShape, Arpeggio arpeggio){
    chords.add(chord);
    chordShapes.put(chord,chordShape);
    arpeggios.put(chord,arpeggio);
  }

  public ChordShape getChordShape(Chord chord){
    for(int i = 0; i < chords.size(); i++){
      Chord chordArray = (Chord)chords.get(i);
     if(chordArray==chord){
       return (ChordShape)chordShapes.get(chord);
     }
   }
   return null;
 }

 public void replaceChordShape(Chord chord, ChordShape chordShape){
   chordShapes.put(chord,chordShape);
 }
 @Override
protected void removeChord(int index){
   Chord  chord  = (Chord)chords.get(index);
   chordShapes.remove(chord);
   chords.remove(index);
   arpeggios.remove(new Integer(index));

 }

 @Override
public String toString(){
  String retorno = "";
  for(int i=0;i<chords.size();i++){
    retorno += ((Chord)chords.get(i)).toString();
    retorno += "<" + chordShapes.get(chords.get(i)) + ">";
	   if(arpeggios.get(chords.get(i))==null){
		   retorno += "(Arpeggio not specified)";
	   }else{	      
		   retorno +=  "(" + ((Arpeggio)arpeggios.get(chords.get(i))).getName() + ")";
	   }   
  }
  return retorno;
}

/**
  * @return Return true is all the chords have the chord shape specified.
 */
public boolean isComplete(){

  for (int i = 0; i < chords.size(); i++) {
    Chord chord = (Chord)chords.get(i);
    if(!chordShapes.containsKey(chord)){
      return false;
    }
  }
  return true;
}



}
