

package octopus.repl;

import octopus.*;

/**
 * Scale is used to instantiate a group of notes based on a particular intervals formation.
 * A Scale is to Notes what a HarmonicProgression is to Chords. 
 * It can be a Diatonic(7 notes) or Pentatonic(5 note) scale in both Major and Minor, in any mode or degree.
 *
 */
public class ScaleREPL{ 

	public  Scale diatonic(Note key, int mode) {
		return Scale.getDiatonicScale(key, mode);
	}

	public Scale pentatonic(Note key, int mode) throws
	NoteException {
		return Scale.getPentatonicScale(key, mode);
	}
	
	public Scale cromatic(Note key) throws NoteException{
		return Scale.getCromaticScale(key);
	}

	public Scale mode(Scale scale,int mode) throws NoteException{
		 scale.setMode(mode);
		 return scale;
	}






}




