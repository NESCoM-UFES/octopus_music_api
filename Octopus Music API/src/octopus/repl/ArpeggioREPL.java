package octopus.repl;

import octopus.*;;


public class ArpeggioREPL {
	
	public  Arpeggio fast() {
		return ArpeggioLibrary.getFastArpeggio();
	}
	
	public  Arpeggio fast(int nVoices) {
		return ArpeggioLibrary.getFastArpeggio(nVoices);
	}
	
/*	public Arpeggio draw() {
		
	}
	
	
	public int save (Arpeggio arpeggio, String fileName) {
	 
		ArpeggioLibrary.store(arpeggio, null);
	}*/

}
