package examples;

import octopus.*;

public class ArpeggioExample {

	/**
	 * This example demonstrate how to create a simple arpeggio with four voices.
	 * @author lcostalonga
	 */
	public static void main(String[] args) {

		//Creates an Arpeggio with 4 voices.
		Arpeggio arpeggio = new Arpeggio(4);

		arpeggio.setName("Fast Arpeggio");
		Bar[] bars = new Bar[4];

		for(int i = 0; i<4; i++){

			// 4 beats, 16th note (duration)
			bars[i] = new Bar(4,16);

			//Insert one 8th note in the i(th) beat and fill the rest of the Bar with rests.
			bars[i].addSingleRhythmEvent(Bar.EIGHT_NOTE,Bar.RHYTHM_EVENT_NOTE,i+1);
			arpeggio.insertBar(bars[i],i);       	      
		}

		System.out.println(arpeggio);

		try {
			Musician musician = new Musician();
			musician.play(arpeggio);		

		} catch (MusicPerformanceException e) {
			// Error in the performance.
			e.printStackTrace();
		}



	}
}
