package examples;
import octopus.Chord;
import octopus.ChordNotationException;
import octopus.HarmonicProgression;
import octopus.IntervalFactory;
import octopus.Note;
import octopus.NoteException;


public class HarmonicProgressionExample {

	public static void main(String[] args) {
		try{	
            // Instantiate a Harmonic Progression;
			HarmonicProgression harmonicprogression = new HarmonicProgression("12-bar-blues");

            // Define the degrees ;
			harmonicprogression.addScaleDegree("I");
			harmonicprogression.addScaleDegree("IV",IntervalFactory.getMajorSeventh()); //IV7
			harmonicprogression.addScaleDegree("I");	
			harmonicprogression.addScaleDegree("I",IntervalFactory.getMajorSeventh());		
			harmonicprogression.addScaleDegree("IV",IntervalFactory.getMajorSeventh()); //IV7
			harmonicprogression.addScaleDegree("IV",IntervalFactory.getMajorSeventh()); //IV7
			harmonicprogression.addScaleDegree("I");
			harmonicprogression.addScaleDegree("I");
			harmonicprogression.addScaleDegree("II",IntervalFactory.getMinorSeventh());
			harmonicprogression.addScaleDegree("IV",IntervalFactory.getMajorSeventh()); //IV7
			harmonicprogression.addScaleDegree("I");	
			harmonicprogression.addScaleDegree("II",IntervalFactory.getMinorSeventh()); //half bar
			harmonicprogression.addScaleDegree("V",IntervalFactory.getMajorSeventh()); //half bar*/
			
			// Print ;
			System.out.println(harmonicprogression);
             
			//Get the chord in a particular key (C Major);
			Chord[] chords =  harmonicprogression.getChords(Note.getNote("C", 4));
			
			//Print the Chords;
			for (int i = 0; i < chords.length -1; i++) {
				System.out.print(chords[i].getChordName() + " - " );				
			}
			System.out.print(chords[chords.length -1].getChordName());

		} catch (ChordNotationException e) {
			e.printStackTrace();
		} catch (NoteException e) {
			e.printStackTrace();
		}	


	}
}
