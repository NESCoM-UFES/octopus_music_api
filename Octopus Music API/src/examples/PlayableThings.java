package examples;

import octopus.Arpeggio;
import octopus.Bar;
import octopus.Chord;
import octopus.ChordException;
import octopus.ChordNotationException;
import octopus.HarmonicProgression;
import octopus.Harmony;
import octopus.Intervals;
import octopus.Melody;
import octopus.Music;
import octopus.Note;
import octopus.NoteException;
import octopus.Notes;
import octopus.OMC;
import octopus.RhythmConstants;
import octopus.RhythmPattern;
import octopus.Scale;

public class PlayableThings implements OMC {

	public static Note getNoteToPlay(){
		return Notes.getA();
	}

	public static Bar getBarToPlay(){ 
		 Bar bar = new Bar(4,4);
		    bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE);
		    bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
		    bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_REST);
		    bar.addRhythmEvent(RhythmConstants.QUARTER_NOTE, RhythmConstants.RHYTHM_EVENT_NOTE);
		  return bar;  
	}
	
	public static RhythmPattern getRhythmPatternToPlay(){

		RhythmPattern rhythmPattern = new RhythmPattern();

		Bar bM = new Bar(5,4);
		bM.addRhythmEvent(RhythmConstants.QUARTER_NOTE,1);
		double[] duration = {RhythmConstants.EIGHT_NOTE , RhythmConstants.EIGHT_NOTE, RhythmConstants.EIGHT_NOTE};
		int[] type = {1 ,0,1 };
		bM.addRhythmEvent(type,RhythmConstants.QUARTER_NOTE, true);

		bM.addRhythmEvent(RhythmConstants.EIGHT_NOTE,1);
		bM.addRhythmEvent(RhythmConstants.QUARTER_NOTE,1);
		bM.addRhythmEvent(RhythmConstants.EIGHT_NOTE,1);
		bM.addRhythmEvent(bM.getDottedValue(RhythmConstants.EIGHT_NOTE),1);
		bM.addRhythmEvent(RhythmConstants.SIXTEENTH_NOTE,1);

		rhythmPattern.insertMark("beginning");
		rhythmPattern.insertBar(bM);
		rhythmPattern.insertReturn("beginning",3);

		return rhythmPattern;

	}
	public static Arpeggio getArpeggiotoPlay(){
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

	public static Melody getMelodyToPlay() throws NoteException{
		String[] freeSoloMelody = {"C", "C", "C","C","B", "A", "G", "C","C", "B", "C", "B", "B", "B"};
		Melody melody = new Melody(freeSoloMelody,getRhythmPatternToPlay());
		return melody;
	}

	public static Scale getScaleToPlay() throws NoteException{

		return Scale.getPentatonicScale(Notes.getC(), Scale.MODE_MINOR);


	}
	public static HarmonicProgression getHarmonicProgressionToPlay() throws ChordNotationException {
		HarmonicProgression harmonicprogression = new HarmonicProgression("Test");
		harmonicprogression.addScaleDegree("I");
		harmonicprogression.addScaleDegree("ii");
		harmonicprogression.addScaleDegree("V", Intervals.getMajorSeventh());

		return harmonicprogression;

	}
	


public static Chord getChordToPlay() throws  NoteException, ChordException, ChordNotationException {	
   Chord chord = Chord.getChord("Cm7(9)");
  return chord;
}


public static Harmony getHarmonyToPlay() throws ChordNotationException  {
	HarmonicProgression progression = getHarmonicProgressionToPlay();
	Chord[] chords = progression.getChords(Notes.getC());
	RhythmPattern rhythmPattern  = RhythmPattern.getConstantRhythmPattern(4, RhythmConstants.QUARTER_NOTE);
	Harmony harmony = new Harmony(chords, rhythmPattern);
	
	
	return harmony;

}

public static Music getMusicToPlay() throws ChordNotationException, NoteException  {
	
	Music music = new Music();
	
	Melody melody = getMelodyToPlay();
	music.insert(melody);
	music.insert(getHarmonyToPlay(), melody.getDuration());
	
	return music;

}



}

