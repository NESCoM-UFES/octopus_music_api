package octopus;

import java.io.Serializable;

import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;

/**
 * Note is the smallest audible element that can be intentionally played or grouped in a musical structure in the API.
 * 
 * 
 * @see Interval
 * @see Notes
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
public class Note implements Cloneable, Playable,Serializable,Comparable<Object> {
	/**{@value} */ 
	public static final int DOUBLE_FLAT = -2;
	/**{@value} */ 
	public static final int DOUBLE_SHARP = 2;
	/**{@value} */ 
	public static final int FLAT = -1;
	/**{@value} */ 
	public static final int NATURAL= 0;
	/**{@value} */ 
	public static final int SHARP = 1;


	public static Note getNote(String noteSymbol) throws NoteException {

		return Notes.getNote(noteSymbol);

	}

	public static Note getNote(String noteSymbol, int octavePitch) throws NoteException{
		return Notes.getNote(noteSymbol,octavePitch);
	}


	/**
	 * For example, C sharp;
	 */
	protected String name;

	/**
	 * The interval distance for the previous note in the tempered scale (or any other)*/
	/* Exemplos: Sol - L�(nota natural imediatamente posterior) = Segunda Maior
	 *           Sol sustenido - L�(nota natural imediatamente posterior) = Segunda Menor
	 *           Sol bemol - Sol(nota natural imediatamente posterior) = Segunda Menor.
	 */
	protected Interval nextInterval;

	/**
	 * Octave region. For example , A4 = octave 4;
	 */
	protected int octavePitch;




	/** The interval distance for the previous note in the tempered scale (or any other).*/
	/* Exemplos: C(previous natural note) - D = Second Major Interval
	 *           D(previous natural note) - D sustenido = Segunda Menor
	 *           D�(nota natural imediatamente anterior) - R� bemol = Segunda Menor.
	 */
	protected Interval previousInterval;

	/** The number of semitones over/under the note; For example, SHARP = 1.
  protected int accident = 0;

 /**
	 * For example, C#
	 */
	protected String symbol;

	//Usado pelo m�todo clone;
	private Note(){}

	/*
	 * Instancia as propriedades da nota.
	 * @param symbol S�mbolo que identifica a nota. Exemplo: a nota
	 * Mi � identificada atrav�s do simbolo "E".
	 * @param name Nome da nota. Exemplo: "Sol".
	 * @param previousInterval Interval formado com a nota natural imediatamente
	 * anterior a uma nota dada. Exemplo: Quinta justa.
	 * @param nextInterval Interval formado com a nota natural imediatamente
	 * posterior a uma nota dada. Exemplo: sexta menor.
	 */
	protected Note(String idSymbol,String noteName, Interval previousInterval, Interval nextInterval){
		this.symbol = idSymbol;
		this.name = noteName;
		this.previousInterval = previousInterval;
		this.nextInterval = nextInterval;
		octavePitch = 4; //Why %? So inflexible!
		
	};


	protected Note(String idSymbol,String noteName, Interval previousInterval, Interval nextInterval, int octavePitch){
		this.symbol = idSymbol;
		this.name = noteName;
		this.previousInterval = previousInterval;
		this.nextInterval = nextInterval;
		this.octavePitch =  octavePitch;
	
	}

	@Override
	public Object clone(){
		Note retorno = new Note();
		retorno.octavePitch = octavePitch;
		retorno.previousInterval = previousInterval;
		retorno.nextInterval = nextInterval;
		retorno.name = name;
		retorno.symbol = symbol;

		return retorno;
	}

	public int compareTo(Object o) {
		int midiValue = (((Note)o).getMidiValue());
		if(this.getMidiValue() < midiValue){
			return -1;
		}else{
			if(this.getMidiValue() > midiValue){
				return 1;
			}
		}
		return 0;
	}


	/*
	 * Obt�m a altera��o (sustenido ou bemol) de uma nota. Caso a nota n�o possua
	 * altera��o o retorno � null.
	 * @return Altera��o (sustenido ou bemol), ou null se n�o encontrada altera��o na
	 * nota.
	 */
	public String getAccident() {
		String retorno = null;
		if (isAccidental()) {
			retorno = symbol.substring(1);
		}
		return retorno;
	}

	public  double getFrequency(){
		
		 double freq = (440 * Math.pow(2,((double)(getMidiValue() - 69)/12))); 
		 return freq;
	
	 }

	public int getMidiValue()  {
		 int posEscala = Notes.getCromaticNoteIndex(this);
		    if (this.getOctavePitch() == 4) { //I've changed from 5 to for...not sure why it was 5 before, but must have a good reason!
		      return (60 + posEscala);
		    }
		    else {
		      if (getOctavePitch() < 4) { //same here
		        //Calcula a oitava e soma a posi��o na escala crom�tica.
		        int fator = 60 - ( (5 - getOctavePitch()) * 12);
		        return(fator + posEscala);
		      }
		      else {
		        int fator = 60 + ( (getOctavePitch() - 4) * 12); //same here
		        return(fator + posEscala);
		      }
		    }
	}
 	
	/**
	 * Returns the MusicalEventSequence of a single note. To be used only
	 * when the note needs to be heard out of musical context (just to see
	 * how it sounds). 
	 */
	public MusicalEventSequence getMusicalEventSequence() {
		 /* MusicalEvent meOct = new MusicalEvent(0, 0, notes[indexNotes], duration, rhythmEvents[i].velocity);
		  MusicalEvent meOctOff = new MusicalEvent(i, time + duration,
                     notes[indexNotes], 0, 0);
		  musicalEventSequence.addMusicalEvent(meOct);
		  musicalEventSequence.addMusicalEvent(meOctOff);*/
		
		MusicalEventSequence p = new MusicalEventSequence();
		p.addMusicalEvent(new MusicalEvent(0,0,this,RhythmConstants.WHOLE_NOTE,RhythmConstants.DYNAMIC_MEZZO_FORTE));

		//If this line is removed the the synth will clip the note's sound.
		p.addMusicalEvent(new MusicalEvent(1,RhythmConstants.WHOLE_NOTE,this,RhythmConstants.WHOLE_NOTE,0));

		return p;
	}
	 /*
	 * Obt�m o nome da nota. Exemplo: "Mi".
	 * @return Nome da nota.
	 */
	public String getName() {return name;}	
	/*
	 * Obt�m o intervalo formado com a nota natural imediatamente posterior a uma nota dada.
	 * Exemplos: Sol - L�(nota natural imediatamente posterior) = Segunda Maior
	 *           Sol sustenido - L�(nota natural imediatamente posterior) = Segunda Menor
	 *           Sol bemol - Sol(nota natural imediatamente posterior) = Segunda Menor.
	 * @return Interval formado com a nota natural imediatamente posterior a uma nota dada.
	 */
	protected Interval getNextInterval() {return nextInterval;}

	/*
	 * Obt�m a regi�o onde a nota est� localizada.
	 * @return Regi�o onde a nota est� localizada.
	 */
	public int getOctavePitch() {return octavePitch;}

	/*
	 * Obt�m o intervalo formado com a nota natural imediatamente anterior a uma nota dada.
	 * Exemplos: D�(nota natural imediatamente anterior) - R� = Segunda Maior
	 *           R�(nota natural imediatamente anterior) - R� sustenido = Segunda Menor
	 *           D�(nota natural imediatamente anterior) - R� bemol = Segunda Menor.
	 * @return Interval formado com a nota natural imediatamente anterior a uma nota dada.
	 */
	protected Interval getPreviousInterval() {return previousInterval;}

	/*
	 * Obt�m o s�mbolo identificador da nota. Exemplo: "E".
	 * @return S�mbolo identificador de uma nota.
	 */
	public String getSymbol() {return symbol;}



	/*
	 * Verifica se uma nota possui altera��o (sustenido ou bemol).
	 * @return Verdadeiro de encontrar a altera��o e falso caso contr�rio.
	 */
	public boolean isAccidental() {
		boolean retorno;
		if ((symbol.indexOf('#')!= -1)||(symbol.indexOf('b')!= -1)){
			retorno = true;
		}else{
			retorno= false;
		}
		return retorno;
	}

	public boolean isDoubleFlat(){
		String acc = this.getAccident();
		if(acc!=null){
			return this.getAccident().equals("bb");
		}
		return false;
	}

	public boolean isDoubleSharp(){
		String acc = this.getAccident();
		if (acc != null) {
			return this.getAccident().equals("##");
		}
		return false;

	}




	public boolean isFlat(){
		String acc = this.getAccident();
		if(acc!=null){
			return this.getAccident().equals("b");
		}
		return false;
	}

	public boolean isSharp(){
		String acc = this.getAccident();
		if (acc != null) {
			return this.getAccident().equals("#");
		}
		return false;

	}

	/*
	 * Inicializa a regi�o onde a nota est� localizada.
	 * O - Mais Grave
	 * 11 - Mais Aguda
	 * @param pitch Regi�o onde a nota est� localizada.
	 */
	public void setOctavePitch(int octavePitch) throws NoteException{
		/*@todo Verificar range de escalas do MIDI*/
		if ((octavePitch >= 0)&&(octavePitch <= 11)){
			this.octavePitch = octavePitch;
		}else{
			throw new NoteException("Pitch's octave out of range",this.name);
		}
	}

	public String toString(){
		return this.symbol + this.octavePitch;
	}
}
