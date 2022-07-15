package octopus.repl;

import octopus.Arpeggio;
import octopus.Bar;
import octopus.Chord;
import octopus.Harmony;
import octopus.Melody;
import octopus.MusicPerformanceException;
import octopus.Playable;
import octopus.RhythmPattern;

/**
 * The purpose of this class is basically to test the implementations of the OctopusShell
 * keeping the code there "cleaner".
 * @author llcos
 * @date 11/07/2022
 *
 */
public class TestingOctopusREPL extends OctopusShell{
	//### FOR TESTING PURPOSE ONLY - NOT TO ADD TO THE JSH FILE ###

	//live = new LivePerformer();

	public void testLoopBar() {

		try {


			Bar b1 = bar(4,4,"0+++------------", SIXTEENTH_NOTE);
			Bar b2 = bar(4,4,"----0+++--------", SIXTEENTH_NOTE);
			Bar b3 = bar(4,4,"--------0+++----", SIXTEENTH_NOTE);
			Bar b4 = bar(4,4,"------------0+++", SIXTEENTH_NOTE);

			System.out.println(duration(b1));
			System.out.println(duration(b2));

			play(loop(together(b1,b2)));
			stop(0);
			
			play(loop(together(b1,b2,b3,b4)));
			stop(0);

		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testLoopMelody() {

		try {


			Bar b1 = bar(4,4,"0+++------------", SIXTEENTH_NOTE);
			Bar b2 = bar(4,4,"----0+++--------", SIXTEENTH_NOTE);
			Bar b3 = bar(4,4,"--------0+++----", SIXTEENTH_NOTE);
			Bar b4 = bar(4,4,"------------0+++", SIXTEENTH_NOTE);

			Melody m1 = melody(notes(C),rhythm(b1));	    	
			Melody m2 = melody(notes(E),rhythm(b2));
			Melody m3 = melody(notes(G),rhythm(b3));
			Melody m4 = melody(notes(B),rhythm(b4));

			play(together(m1,m2,m3,m4));


			play(loop(together(m1,m2)));
			stop(0);
			
			play(loop(together(m1,m2,m3,m4)));
			stop(0);

			play(loop(m1));
			play(loop(m2));
			play(loop(m3));
			play(loop(m4));

		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testLoopArpeggio() {

		try {


			Bar b1 = bar(4,4,"0+++------------", SIXTEENTH_NOTE);
			Bar b2 = bar(4,4,"----0+++--------", SIXTEENTH_NOTE);
			Bar b3 = bar(4,4,"--------0+++----", SIXTEENTH_NOTE);
			Bar b4 = bar(4,4,"------------0+++", SIXTEENTH_NOTE);


			Arpeggio a1 = arpeggio(b1,b2,b3);
			//Arpeggio a1 = arpeggio(b1,b2,b3,b4);

			//play(a1);

			play(loop(a1));


		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testLoopChord() {

		try {


			Bar b1 = bar(4,4,"0+++------------", SIXTEENTH_NOTE);
			Bar b2 = bar(4,4,"----0+++--------", SIXTEENTH_NOTE);
			Bar b3 = bar(4,4,"--------0+++----", SIXTEENTH_NOTE);
			Bar b4 = bar(4,4,"------------0+++", SIXTEENTH_NOTE);


			Chord c = chord("C");
			Arpeggio a1 = arpeggio(b1,b2,b3);

			//play(a1);
			play(c,a1);

			play(loop(c));


		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testLoopRhythmPattern() {
		try {



			Bar b1 = bar(4,4,"0+++----0+++++++", SIXTEENTH_NOTE);
			Bar b2 = bar(4,4,"----0+++----0000", SIXTEENTH_NOTE);
			//Bar b3 = bar(4,4,"--------0+++----", SIXTEENTH_NOTE);
			//Bar b4 = bar(4,4,"------------0+++", SIXTEENTH_NOTE);


			RhythmPattern r = rhythm(b1,b2);
			play(r);

			Bar bx = bar(4,4,"0+++----0+++++++----0+++----0000", SIXTEENTH_NOTE);
			RhythmPattern r2 = (rhythm(bx));

			play(loop(r));
			stop(0); //preciso implentar um jeito de parar o loop pelo int.

			play(loop(r2));

		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testLoopHarmony() {

		try {


			Bar rb1 = bar(4,4,"0+++----0+++++++", SIXTEENTH_NOTE);
			Bar rb2 = bar(4,4,"----0+++----0+0+", SIXTEENTH_NOTE);
			//Bar b3 = bar(4,4,"--------0+++----", SIXTEENTH_NOTE);
			//Bar b4 = bar(4,4,"------------0+++", SIXTEENTH_NOTE);


			RhythmPattern r = rhythm(rb1,rb2);


			Chord c = chord("C");
			//Arpeggio a1 = arpeggio(b1,b2,b3);

			Harmony h = harmony(chords("C","F","G"),r);

			play(h);

			//loop(h);
			//stop();

			Bar b1 = bar(4,4,"0+++------------", SIXTEENTH_NOTE);
			Bar b2 = bar(4,4,"----0+++--------", SIXTEENTH_NOTE);
			Bar b3 = bar(4,4,"--------0+++----", SIXTEENTH_NOTE);
			Bar b4 = bar(4,4,"------------0+++", SIXTEENTH_NOTE);


			Arpeggio a1 = arpeggio(b1,b2,b3);
			Harmony h2 = harmony(chords("C","F","G"),r,a1);
			play(h2);
			play(loop(h2));


		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testLoopWhen() {

		try {


			/*
			 * Bar b1 = bar(4,4,"0+++------------", SIXTEENTH_NOTE); Bar b2 =
			 * bar(4,4,"----0+++--------", SIXTEENTH_NOTE); Bar b3 =
			 * bar(4,4,"--------0+++----", SIXTEENTH_NOTE); Bar b4 =
			 * bar(4,4,"------------0+++", SIXTEENTH_NOTE);
			 */
			
			Bar bx = bar(4,4,"0+++----0+++++++----0+++----0000", SIXTEENTH_NOTE);
			RhythmPattern r = (rhythm(bx));

			Melody m1 = melody(notes(C),r);	    	
			Melody m2 = melody(notes(E),r);
			Melody m3 = melody(notes(G),r);
			Melody m4 = melody(notes(B),r);

			play(loop(m1));
			LoopMidiSynthController.defaultChannel = 2;			
			
			when(loop(0),LOOPS,loop(m2));
			LoopMidiSynthController.defaultChannel = 3;		
			
			when(loop(0),LOOPS,loop(m3));
			
			LoopMidiSynthController.defaultChannel = 4;		
			when(loop(0),LOOPS,loop(m4));
			
			stopAll();
		
			play(loop(together(m1,m2,m3,m4)));

		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testBpmChange() {
		
		try {
			
			bpm(120);
			play(loop(A));
			
			when(loop(0),LOOPS,loop(G));
			
			bpm(240);
			
			bpm(60);
			
			bpm(120);
			
			
		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/* protected void finalize throws Exception 
	  {  
	    ((LoopMidiController)this.musician.getSynthesizerController()).closeDevices();
	  }*/  

	public static void main(String[] args) {

		TestingOctopusREPL t = new TestingOctopusREPL();

		try {

			//t.midi();
			t.midi(4);

			
			//t.testLoopBar(); //OK
			//t.testLoopMelody();
			//t.testLoopArpeggio();
			//t.testLoopChord();
			//t.testLoopRhythmPattern();
			//t.testLoopHarmony();
			//t.testLoopWhen();
			
			
			t.testBpmChange();
			
	
			/* Problemas:
			 * 1) t.loop(t.A) está matando o loop em andamento; O problema ocorre quando um loop 
			 *   inicia antes de mudar o midiout. Talvez parar automaticamente quando trocar. Resolvido!
			 * 2)quandro para o programa, preciso fechar os canal...o external seq continua tocando.
			 * 
			 */


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			t.stopAll();
		}

	}

}
