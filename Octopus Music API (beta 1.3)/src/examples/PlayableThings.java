package examples;

import octopus.*;

public class PlayableThings implements OMC {

	public static Note getNoteToPlay(){
		return WesternMusicNotes.getA();
	}

	public static Bar getBarToPlay(){ 
		 Bar bar = new Bar(4,4);
		    bar.addRhythmEvent(Bar.QUARTER_NOTE,Bar.RHYTHM_EVENT_NOTE);
		    bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
		    bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_REST);
		    bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
		  return bar;  
	}
	
	public static RhythmPattern getRhythmPatternToPlay(){

		RhythmPattern rhythmPattern = new RhythmPattern();

		Bar bM = new Bar(5,4);
		bM.addRhythmEvent(bM.QUARTER_NOTE,1);
		double[] duration = {bM.EIGHT_NOTE , bM.EIGHT_NOTE, bM.EIGHT_NOTE};
		int[] type = {1 ,0,1 };
		bM.addRhythmEvent(duration, type,bM.QUARTER_NOTE, true);

		bM.addRhythmEvent(bM.EIGHT_NOTE,1);
		bM.addRhythmEvent(bM.QUARTER_NOTE,1);
		bM.addRhythmEvent(bM.EIGHT_NOTE,1);
		bM.addRhythmEvent(bM.getDottedValue(bM.EIGHT_NOTE),1);
		bM.addRhythmEvent(bM.SIXTEENTH_NOTE,1);

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
	        bars[i].addSingleRhythmEvent(Bar.EIGHT_NOTE,Bar.RHYTHM_EVENT_NOTE,i);
	        gpr.insertBar(bars[i],i);
	      }

	     return gpr;
	}

	public static Melody getMelodyToPlay() throws NoteException{
		String[] freeSoloMelody = {"C", "E", "F", "G", "F", "C","C", "E", "F", "G", "F", "C"};
		Melody melody = new Melody(freeSoloMelody,getRhythmPatternToPlay());
		return melody;
	}

	public static Scale getScaleToPlay() throws NoteException{

		return Scale.getPentatonicScale(WesternMusicNotes.getC(), Scale.MODE_MINOR);


	}
	public static HarmonicProgression getHarmonicProgressionToPlay() throws ChordNotationException {
		HarmonicProgression harmonicprogression = new HarmonicProgression("Test");
		harmonicprogression.addScaleDegree("I");
		harmonicprogression.addScaleDegree("ii");
		harmonicprogression.addScaleDegree("V", IntervalFactory.getMajorSeventh());

		return harmonicprogression;

	}
	


public static Chord getChordToPlay() throws  NoteException, ChordException, ChordNotationException {	
   Chord chord = Chord.getChord("Cm7(9)");
  return chord;
}


public static Harmony getHarmonyToPlay() throws ChordNotationException  {
	HarmonicProgression progression = getHarmonicProgressionToPlay();
	Chord[] chords = progression.getChords(WesternMusicNotes.getC());
	RhythmPattern rhythmPattern  = RhythmPattern.getConstantRhythmPattern(4, RhythmPattern.QUARTER_NOTE);
	Harmony harmony = new Harmony(chords, rhythmPattern);
	
	
	return harmony;

}



}

