package examples;

import octopus.*;

public class PlayingNotesTutorial {
	
	public static void main(String[] args) {
		
		try {
			Musician musician = new Musician();
			
			String[] noteSymbols = {"G", "A", "B", "D", "D", "B", "C", "C"};
			Note[] notes = Notes.getNotes(noteSymbols);
			
			
			double[] durations = {0.25, 0.25, 0.5, 0.5, 0.5, 0.5, 0.5, 1};
			int[] types = {1,1,1,1,1,1,1,1};
			Bar bar = new Bar(13, 4, durations, types);
			
			/*bar.addRhythmEvent(bar.QUARTER_NOTE, bar.RHYTHM_EVENT_NOTE);
			bar.addRhythmEvent(bar.QUARTER_NOTE, bar.RHYTHM_EVENT_NOTE);
			bar.addRhythmEvent(bar.HALF_NOTE, bar.RHYTHM_EVENT_NOTE);*/
			
		/*	Bar bar2 = new Bar(4,4);
			bar2.addRhythmEvent(bar.HALF_NOTE, bar.RHYTHM_EVENT_NOTE);
			bar2.addRhythmEvent(bar.HALF_NOTE, bar.RHYTHM_EVENT_NOTE);
		
		
			Bar bar3 = new Bar(4,4);
			bar3.addRhythmEvent(bar.HALF_NOTE, bar.RHYTHM_EVENT_NOTE);
			bar3.addRhythmEvent(bar.HALF_NOTE, bar.RHYTHM_EVENT_NOTE);
			
			Bar bar4 = new Bar(4,4);
			bar4.addRhythmEvent(bar.WHOLE_NOTE, bar.RHYTHM_EVENT_NOTE);*/

			
			RhythmPattern rhythmPattern = new RhythmPattern();
			rhythmPattern.insertMark("Beginning");
			rhythmPattern.insertBar(bar);
		/*	rhythmPattern.insertBar(bar2);
			rhythmPattern.insertBar(bar3);
			rhythmPattern.insertBar(bar4);*/
			rhythmPattern.insertReturn("Beginning", 5);
			
		
			
			
			Melody melody = new Melody(notes, rhythmPattern);
            System.out.println(melody);		

            musician.setPlayingSpeed(240);
			musician.play(melody);
			
		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (NoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


}
