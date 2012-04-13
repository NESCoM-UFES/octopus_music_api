package examples;
import octopus.*;
import octopus.util.*;
import octopus.communication.*;
import octopus.communication.midi.*;
import octopus.instrument.*;
import octopus.instrument.fretted.*;


public class MusicianTest {

	public static void main(String[] args) {
		try {
			Musician musician = new Musician();

			/*Note to Testers: uncomment each of the blocks below (one-by-one) to test if the musician is able
			 * to play the several playable components implemented in the PlayableThings Class.
			 * You may have to also uncomment some of the Exceptions.
			 */


		/*	 ====  BLOCK 1: Testing Note Playing ====
		  Note note = PlayableThings.getNoteToPlay();
		  System.out.println(note);
		  musician.play(PlayableThings.getNoteToPlay());*/
			 

			// ====  BLOCK 2: Testing Bar Playing ==== 
		/* Bar bar = PlayableThings.getBarToPlay();
		 System.out.println(bar);
		 musician.play(PlayableThings.getBarToPlay());*/
			 

		//====  BLOCK 3: Testing RhythmPattern Playing ==== 
		  /*RhythmPattern rhythmPattern =  PlayableThings.getRhythmPatternToPlay();
		  System.out.println(rhythmPattern);
		  musician.play(PlayableThings.getRhythmPatternToPlay());*/
			 

			//====  BLOCK 4: Testing Arpeggio Playing ==== 
		/*	Arpeggio arpeggio = PlayableThings.getArpeggiotoPlay();
			System.out.println(arpeggio);  
			musician.play(arpeggio);*/
			


			// ====  BLOCK 5: Testing Melody Playing ==== 
			/*try{
				Melody melody = PlayableThings.getMelodyToPlay();
				System.out.println(melody); // NAO ESTA FUNCIONANDO - IMPLEMENTAR TOSTRING()
				musician.play(melody);
			}catch (NoteException e) {
				System.out.println("Ops...it seems that your musician is having troubles with the notes of the Melody: ");
				e.printStackTrace();
			}*/
			 

			// ====  BLOCK 6: Testing HarmonicProgression Playing ==== 
/*			try{

				HarmonicProgression harmonicProgression = PlayableThings.getHarmonicProgressionToPlay();
				System.out.println(harmonicProgression);
				musician.play(harmonicProgression);
			} catch (ChordNotationException e) {
				System.out.println("Ops...it seems that your musician is having troubles with the Chords of the Harmonic Progression: ");
				e.printStackTrace();
			}*/
			
			/* ====  BLOCK 7: Testing Scale Playing ==== */
			
			/*try{
				Scale scale = PlayableThings.getScaleToPlay();
				System.out.println(scale); 
				musician.play(scale);
			}catch (NoteException e) {
				System.out.println("Ops...it seems that your musician is having troubles with the notes of the Scale: ");
				e.printStackTrace();
			}*/
			
           /* ====  BLOCK 8: Testing Chord Playing ==== */
			
			/*try{
				Chord chord;
				try {
					chord = PlayableThings.getChordToPlay();
					System.out.println(chord);
					musician.play(chord);
					
				} catch (ChordException e) {
					System.out.println("It seems that we had some issues creating this chord.");

				} catch (ChordNotationException e) {
					System.out.println("Did you know that Octopus allows you to choose the chord's notation? Try that!");
				}

			}catch (NoteException e) {
				System.out.println("Ops...it seems that your musician is having troubles with the notes of the Scale: ");
				e.printStackTrace();
			}*/

         /* ====  BLOCK 9: Testing Harmony Playing ==== */

			try{
				Harmony harmony;

				harmony = PlayableThings.getHarmonyToPlay();
				System.out.println(harmony); 
				musician.play(harmony);
			} catch (ChordNotationException e) {
				System.out.println("Ops...it seems that your musician is having troubles with the notes of the harmony: ");
			}

		}
		catch (MusicPerformanceException ex) {
			System.out.println("Ops...it seems that your musician had troubles: " +  ex.getMessage());
		}finally{
			//Runtime.getRuntime().exit(0);
		}
		//System.exit(0);

	}


}
