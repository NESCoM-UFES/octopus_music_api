package examples;
import octopus.*;

public class AppTest {
	public static void main(String[] args) {
		try {
			Note[] notes = Scale.getDiatonicScale(Notes.getC(), Scale.MODE_MAJOR).getNotes();

			for (int i = 0; i < notes.length; i++) {
				System.out.println(notes[i]);
			}

            System.out.println("===================");
			notes = Notes.transpose(notes, -1);
			
			for (int i = 0; i < notes.length; i++) {
				System.out.println(notes[i]);
			}
			
		
		} catch (NoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
}
