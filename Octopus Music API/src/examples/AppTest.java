package examples;
import java.io.File;

import javax.swing.JFileChooser;

import octopus.Bar;
import octopus.Melody;
import octopus.Musician;
import octopus.Note;
import octopus.Notes;
import octopus.RhythmPattern;
import octopus.Scale;
import octopus.communication.SynthesizerController;
import octopus.communication.midi.LiveMidiSynthesizerController;
import octopus.communication.midi.MidiSynthesizerController;

public class AppTest {
	public static void main(String[] args) {
		try {
			
			//Chord chord = Chord.getChord("X#m7(9)");
			
		    Scale escala = Scale.getPentatonicScale(Notes.getD(), Scale.MODE_MAJOR);
		    
		    Note[] notas = escala.getSuffledNotes(100);
		    
			Bar bar = new Bar(4,4);
			
			bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_NOTE);
			for (int i = 0; i < 4; i++) {
				bar.addRhythmEvent(Bar.SIXTEENTH_NOTE, Bar.RHYTHM_EVENT_NOTE);
			}
			bar.addRhythmEvent(Bar.HALF_NOTE, Bar.RHYTHM_EVENT_NOTE);
			
			RhythmPattern ritmo = new RhythmPattern();
			ritmo.insertMark("inicio");
			ritmo.insertBar(bar);
			ritmo.insertReturn("inicio", 3);
			
			Melody melodia = new Melody(notas,ritmo );
			
			SynthesizerController synth = new LiveMidiSynthesizerController("loopMIDI Port");
				
				
		    //musician = new Musician(synth);
			
			
			Musician musico = new  Musician(synth);
			
			System.out.println(melodia);
			
			musico.play(melodia);
			
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
