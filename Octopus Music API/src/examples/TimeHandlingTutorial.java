package examples;

import octopus.Bar;
import octopus.MusicPerformanceException;
import octopus.Musician;
import octopus.RhythmPattern;

public class TimeHandlingTutorial {

	public static Bar getBar(){
		
		Bar bar = new Bar(4,4);
		
		bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
		bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
		bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_REST);
		bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
		
		
		double[] durations = {0.25, 0.5, 0.125, 0.125};
		int[] type = {1, 1, 0, 1};
		
		bar = new Bar(4, 4, durations, type);
		
		
		return bar;
		
	}
	
	public static Bar getAnotherBar(){
		
		Bar bar = new Bar(4,4);
		
		bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
		bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
		bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_REST);
		bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE );
		
		
		return bar;
		
	}
	
	public static RhythmPattern getRhythmPatten(){
		RhythmPattern rhythmPattern = new RhythmPattern();
		
		rhythmPattern.insertMark("begin");
		
		rhythmPattern.insertMark("begin1stPArt");
		rhythmPattern.insertBar(getBar());
		rhythmPattern.insertReturn("begin1stPArt", 3);
		
		rhythmPattern.insertBar(getAnotherBar());
		
		rhythmPattern.insertReturn("begin", 2);
		
		return rhythmPattern;
	
		
	}
	
	
	public static void main(String[] args) {
		try {
		
			Musician musician = new Musician();
			
			Bar bar = TimeHandlingTutorial.getBar();
			
			System.out.println(TimeHandlingTutorial.getRhythmPatten());
			
			musician.play(TimeHandlingTutorial.getRhythmPatten());
	
		
			
			
		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

		
		
	}	

}


