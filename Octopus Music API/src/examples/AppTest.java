package examples;
import octopus.Bar;
import octopus.*;
import octopus.Melody;
import octopus.Musician;
import octopus.Note;
import octopus.Notes;
import octopus.RhythmConstants;
import octopus.RhythmPattern;

public class AppTest {
	public static void main(String[] args) {
		try {
			
			
			Chord chord = new Chord(Notes.getC(),Notes.getE(),Notes.getG());
			
			//Chord chord = Chord.getChord("X#m7(9)");
			
			/*int[] intervals = {2,2,3,2,3,2,3,3,3,1,1};
			Scale escalaX = Scale.getScale(Notes.getC(), intervals);
			
			Note noteTest = Notes.getC(Note.FLAT);
			noteTest.setOctavePitch(6);
			System.out.println(noteTest.getMidiValue());*/
			
			
			/*Scale escalaC = Scale.getCromaticScale(Notes.getC(Note.FLAT));
			System.out.println(escalaC);*/
			
			/*Scale escalaC = Scale.getCromaticScale(Notes.getB(Note.SHARP));
			System.out.println(escalaC);
		    
			Scale escala = Scale.getPentatonicScale(Notes.getD(), Scale.MODE_MAJOR);
		    
		    
		    Note[] notas = escala.getSuffledNotes(100);*/
			
			//Teste: Bar
			//=============================================
			/*String[] notes = {"C", "D", "E", "F", "F", "F"};
			Note[] notas = Notes.getNotes(notes);		   
		    
			Bar bar = new Bar(4,4);
			bar.addRhythmEvent("----0+++0+++0+++0+++0+--0+--0+0+0+0+", RhythmConstants.SIXTEENTH_NOTE);
			System.out.println(bar);*/
			//======================================================
			//Teste Harmonia...problema com rp
			

			RhythmPattern ritmo = new RhythmPattern();
			Bar bar = new Bar(4,4);
			bar.addRhythmEvent("0+++0+--0+++0+--", RhythmConstants.SIXTEENTH_NOTE);
			//ritmo.insertMark("inicio");
			ritmo.insertBar(bar);
			
		//	Harmony harmonia = new Harmony
			
			//=======================================================
			
			/*bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
			for (int i = 0; i < 4; i++) {
				bar.addRhythmEvent(Bar.SIXTEENTH_NOTE, Bar.RHYTHM_EVENT_NOTE);
			}
			bar.addRhythmEvent(Bar.HALF_NOTE, Bar.RHYTHM_EVENT_NOTE);*/
			
		/*	RhythmPattern ritmo = new RhythmPattern();
			Bar bar = new Bar(4,4);
			bar.addRhythmEvent("0+++0+--0+++0+--", RhythmConstants.SIXTEENTH_NOTE);
			
			//ritmo.insertMark("inicio");
			ritmo.insertBar(bar);
			//ritmo.insertReturn("inicio", 3);
			
			Melody melodia = new Melody(notas,ritmo );
			//melodia.setCircularListNotes(false);
			
			//SynthesizerController synth = new LiveMidiSynthesizerController("loopMIDI Port");
				
			Musician musico = new  Musician();	
		    //musico = new Musician(synth);*/
			
			
			
			
			//System.out.println(melodia);
			
			//musico.play(ritmo);
			//musico.play(melodia);
			
			/*musico.play(Notes.getA());
			
			 JFileChooser jc = new JFileChooser();
             jc.showSaveDialog(null);
             File selectedFile = jc.getSelectedFile();
             if(selectedFile!=null){
         
                 if(!selectedFile.getName().endsWith(".mid")){
                   selectedFile = new File(selectedFile.getPath()+".mid");
                 }
             }
              
			((MidiSynthesizerController) musico.getSynthesizerController()).writeMidiFile(selectedFile, melodia.getMusicalEventSequence());*/
			
			
		
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
}
