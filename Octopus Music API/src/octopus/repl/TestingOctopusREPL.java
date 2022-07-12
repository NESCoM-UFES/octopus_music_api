package octopus.repl;

import octopus.Arpeggio;
import octopus.Bar;
import octopus.Chord;
import octopus.Harmony;
import octopus.Melody;
import octopus.MusicPerformanceException;
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
	    	
	    	loop(together(b1,b2));
	    	//loop(together(b1,b2,b3,b4));
		
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
	    		    	
	    	//play(s.together(m1,m2,m3,m4));// OK
	    	
	    	
	    	//loop(s.together(m1,m2));
	    	//loop(s.together(m1,m2,m3,m4));
	    	
	    	loop(m1);
	    	loop(m2);
	    	loop(m3);
	    	loop(m4);
		
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
			
			loop(a1);
														   	    
		
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
		
			loop(c);
														   	    
		
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
			
			loop(r);
			stop(); 
			
			loop(r2);
		
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
	     	loop(h2);
														   	    
		
		} catch (MusicPerformanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	public static void main(String[] args) {
		
		try {
		TestingOctopusREPL t = new TestingOctopusREPL();
		
		
		//t.midi();
		
		t.midi(4);
		
		
	
		//t.play(t.melody(t.C, t.D));
		
		t.testLoopArpeggio();
		t.loop(t.A);
		
		/* Problemas:
		 * 1) t.loop(t.A) está matando o loop em andamento; O problema ocorre quando um loop 
		 *   inicia antes de mudar o midiout. Talvez parar automaticamente quando trocar.
		 * 2)quandro para o programa, preciso fechar os canal...o external seq continua tocando.
		 * 
		 */
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
