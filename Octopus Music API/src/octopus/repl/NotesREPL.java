package octopus.repl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import octopus.Note;
import octopus.Notes;

public class NotesREPL {
	
	public Note[] suffle(Note[] notes, int noNotes){
		return Notes.suffle(notes,noNotes);
	}
	
	public Note[] suffle(Note[] notes){						
		return Notes.suffle(notes);
	}
	
	public Note[] transpose(Note[] notes, int semitones) throws Exception {
		return Notes.transpose(notes, semitones);
	}
		

}
