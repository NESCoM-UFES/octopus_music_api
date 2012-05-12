

package octopus;

import java.util.Vector;

import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;

/**
 * Scale is used to instantiate a group of notes based on a particular intervals formation.
 * A Scale is to Notes what a HarmonicProgression is to Chords. 
 * It can be a Diatonic(7 notes) or Pentatonic(5 note) scale in both Major and Minor, in any mode or degree.
 *
 */
public class Scale implements Playable{ //Diatonic

	Note key;
	int mode;

	public static final int DEGREE_TONIC = 1;
	public static final int DEGREE_SUPERTONIC = 2;
	public static final int DEGREE_MEDIANT = 3;
	public static final int DEGREE_SUBDOMINANT = 4;
	public static final int DEGREE_DOMINANT = 5;
	public static final int DEGREE_SUBMEDIANT = 6;
	public static final int DEGREE_LEADING_NOTE = 7;

	public static final int MODE_IONIAN = 0;
	public static final int MODE_DORIAN = 1;
	public static final int MODE_PHRYGIAN = 2;
	public static final int MODE_LYDIAN = 3;
	public static final int MODE_MIXOLYDIAN = 4;
	public static final int MODE_AEOLIAN = 5;
	public static final int MODE_LOCRIAN = 6;

	public static final int MODE_MAJOR = 0;
	public static final int MODE_MINOR = 5;
	public static final int MODE_PENTA_MAJOR = 1;
	public static final int MODE_PENTA_MINOR = 3;

	// protected static int[] majorScale = {2,2,1,2,2,2,1};
	protected static Interval[] majorScaleI = {
		IntervalFactory.getMajorSecond(), IntervalFactory.getMajorSecond(),
		IntervalFactory.getMinorSecond(), IntervalFactory.getMajorSecond(),
		IntervalFactory.getMajorSecond(),
		IntervalFactory.getMajorSecond(), IntervalFactory.getMinorSecond()};


	// protected static int[] minorScale = {2,1,2,2,1,2,2};
	protected static Interval[] minorScaleI = {
		IntervalFactory.getMajorSecond(), IntervalFactory.getMinorSecond(),
		IntervalFactory.getMajorSecond(), IntervalFactory.getMajorSecond(),
		IntervalFactory.getMinorSecond(),
		IntervalFactory.getMajorSecond(), IntervalFactory.getMajorSecond()};

	// protected static int[] pentatonicMinorScale = {3,2,3,2,2};
	protected static Interval[] pentatonicMinorScale = {
		IntervalFactory.getMinorThird(), IntervalFactory.getMajorSecond(),
		IntervalFactory.getMajorSecond(), IntervalFactory.getMinorThird(),
		IntervalFactory.getMajorSecond()};

	Note[] notes;

	//  protected static int[] pentatonicScale = {2,2,3,2,3};
	protected static Interval[] pentatonicMajorScale = {
		IntervalFactory.getMajorSecond(), IntervalFactory.getMajorSecond(),
		IntervalFactory.getMinorThird(), IntervalFactory.getMajorSecond(),
		IntervalFactory.getMinorThird()};

	private static Note noteC = new Note("C", "C",
			IntervalFactory.getMinorSecond(),
			IntervalFactory.getMajorSecond());
	private static Note noteD = new Note("D", "D",
			IntervalFactory.getMajorSecond(),
			IntervalFactory.getMajorSecond());
	private static Note noteE = new Note("E", "E",
			IntervalFactory.getMajorSecond(),
			IntervalFactory.getMinorSecond());
	private static Note noteF = new Note("F", "F",
			IntervalFactory.getMinorSecond(),
			IntervalFactory.getMajorSecond());
	private static Note noteG = new Note("G", "G",
			IntervalFactory.getMajorSecond(),
			IntervalFactory.getMajorSecond());
	private static Note noteA = new Note("A", "A",
			IntervalFactory.getMajorSecond(),
			IntervalFactory.getMajorSecond());
	private static Note noteB = new Note("B", "A",
			IntervalFactory.getMajorSecond(),
			IntervalFactory.getMinorSecond());


	/*
	 * Contains only natural notes;
	 */
	protected static Note CmajDiatonicNotes[] = {
		noteC, noteD, noteE, noteF, noteG, noteA, noteB};
	protected static Note CmajPentaNotes[] = {
		noteC, noteD, noteE, noteG, noteA};

	protected static Note cromaticSharpNotes[] = {
		noteC, WesternMusicNotes.getC(Note.SHARP),
		noteD, WesternMusicNotes.getD(Note.SHARP),
		noteE, 
		noteF, WesternMusicNotes.getF(Note.SHARP),
		noteG, WesternMusicNotes.getG(Note.SHARP),
		noteA, WesternMusicNotes.getA(Note.SHARP),
		noteB};

	protected static Note cromaticFlatNotes[] = {
		WesternMusicNotes.getC(Note.FLAT),noteC, 
		WesternMusicNotes.getD(Note.FLAT),noteD,
		WesternMusicNotes.getE(Note.FLAT),noteE, 
		noteF, 
		WesternMusicNotes.getG(Note.FLAT),noteG,
		WesternMusicNotes.getA(Note.FLAT),noteA, 
		WesternMusicNotes.getB(Note.FLAT),noteB};



	public static Scale getDiatonicScale(Note key, int mode) throws
	NoteException {
		Scale scale = getScale(key, majorScaleI, CmajDiatonicNotes);
		scale.setMode(mode);
		return scale;
	}

	public static Scale getPentatonicScale(Note key, int mode) throws
	NoteException {
		Scale scale = getScale(key, pentatonicMajorScale, CmajPentaNotes);
		scale.setMode(mode);
		return scale;
	}

	public static Scale getScale(Note key, Interval[] intervals, Note notesScale[]) throws
	NoteException {
		Scale scale = new Scale();
		scale.notes = new Note[notesScale.length];
		scale.notes[0] = key;
		int octaveSum = WesternMusicNotes.getDistance(WesternMusicNotes.getC(),scale.notes[0],false);

		for (int i = 1; i < scale.notes.length; i++) {
			scale.notes[i] = WesternMusicNotes.getNote(scale.notes[i - 1],
					intervals[i - 1]);

			// Checks is the a full cycle of the scale waa completed and update the octavePitch to next octave.

			/*  octaveSum+=intervals[i - 1].getDistanceFromRoot();
      if (octaveSum >11){
         scale.notes[i].octavePitch++; 
      }*/

		}
		return scale;

	}

	public static Scale getCromaticScale(Note key) throws NoteException{
		if((key.isDoubleFlat())||(key.isDoubleSharp())){
			throw new NoteException("Double flat or sharp not supported in this method", key.getSymbol());
		}

		Scale scale = new Scale();
		scale.notes = new Note[12];
		Note cromaticNotes[] = key.isFlat()?cromaticFlatNotes:cromaticSharpNotes;


		int cont=0;
		int i = WesternMusicNotes.getCromaticNoteIndex(key);
		while(cont<12){
			if(i>cromaticNotes.length){
				i=0;
			}
			scale.notes[i] = (Note)cromaticNotes[i].clone();
			cont++;
			i++;
		}

		return scale;
	}

	public int getSize(){
		return notes.length + 1;
	}

	//Funciona 100% mas limitado a escala diatonica - Deixar de referencia
	/*private static Scale getDiatonicScale(Note key){
    Scale scale = new Scale();
    scale.notes = new Note[7];
    Note notaRetorno = null;
    boolean accidented = key.isAccidental();
    boolean sharp = key.isSharp();
    boolean flat = key.isFlat();

    int cont = 1;
    Note baseNote = NoteCollection.getNote(key.getNotaBase());
    scale.notes[0] = key;
    int   posNote = NoteCollection.getNoteIndex(baseNote);
    posNote = posNote==6?0:posNote+ 1;
    while(cont<7){
      int distScale = majorScale[cont -1];
      Note note = (Note)NoteCollection.notas[posNote].clone();
   int distNotes = NoteCollection.getDistance(scale.notes[cont-1],note,false);
      if(distNotes==distScale){
        scale.notes[cont] = note;
      }else{
        if (distNotes>distScale){
          scale.notes[cont] = NoteCollection.getFlat(note);
          if(distNotes-distScale>1){
            scale.notes[cont].name+=" 2";
          }
        }else{
          scale.notes[cont] = NoteCollection.getSharp(note);
          if(distScale-distNotes>1){
            scale.notes[cont].name+="2";
          }

        }
      }

      posNote = posNote==6?0:posNote+ 1;
      cont++;
    }


      return scale;
     }*/



	@SuppressWarnings("unused")
	private static int getNoteIndex(Note[] notes, Note note) {
		int r = -1;
		for (int i = 0; i < notes.length; i++) {
			if (notes[i].getSymbol().equals(note.getSymbol())) {
				return i;
			}
		}
		return r;
	}

	public void setMode(int mode) {

		Note[] notesTemp = new Note[notes.length];
		int i2 = 0;
		int i1 = mode;
		int octaveCorretion = 0;

		while ((i2 < notes.length) &&
				(i1 < notes.length)){ // If you choose a mode higher than 4 in a pentatonic scale it will trigger an error.
			notesTemp[i2] = notes[i1];
			notesTemp[i2].octavePitch+=octaveCorretion;
			i2++;
			if(i1 == notes.length - 1){
				i1=0;
				octaveCorretion = 1;
			}else{
				i1 = i1 + 1;
			}

		}
		if(notesTemp[0] != null){
			notes = notesTemp;
		}

	}

	public String toString() {
		String retorno = "";
		retorno+=(
				"------------------------------------------------------------------- \n");
		for (int i = 0; i < notes.length; i++) {
			try {
				retorno+=(notes[i].toString() + " Midi: " + notes[i].getMidiValue() + "\n");
			} catch (NoteException e) {
				e.printStackTrace();
			}
		}
		return retorno;
	}
	public Note[] getNotes() {
		return notes;
	}

	public Note[] getSuffledNotes(int nNotes){
		Vector<Note> returnNotes = new Vector<Note>();

		for (int i = 0; i < nNotes; i++) {
			int randomIndex = (int)(Math.random()* notes.length);		  
			returnNotes.add(notes[randomIndex]);		
		}
		return returnNotes.toArray(new Note[0]);
	}

	public Note getNote(int degree) {
		return notes[degree-1];
	}

	public MusicalEventSequence getMusicalEventSequence() {
		RhythmPattern rhythmPattern = RhythmPattern.getConstantRhythmPattern(notes.length, RhythmConstants.QUARTER_NOTE);  

		Melody melody = new Melody(this.getNotes(), rhythmPattern);
		MusicalEventSequence musicalEventSequence = melody.getMusicalEventSequence();

		return musicalEventSequence;    
	}





}




