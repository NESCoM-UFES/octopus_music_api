package octopus;

import java.io.*;
import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;

/**
 * Note is the smallest audible element that can be intentionally played or grouped in a musical structure in the API.
 * 
 * 
 * @see Interval
 * @see WesternMusicNotes
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
public class Note implements Cloneable, Playable,Serializable,Comparable<Object> {
  /**{@value} */ 
  public static final int DOUBLE_FLAT = -2;
  /**{@value} */ 
  public static final int FLAT = -1;
  /**{@value} */ 
  public static final int NATURAL= 0;
  /**{@value} */ 
  public static final int SHARP = 1;
  /**{@value} */ 
  public static final int DOUBLE_SHARP = 2;

  
  /** The number of semitones over/under the note; For example, SHARP = 1.
  protected int accident = 0;

 /**
 * For example, C#
 */
  protected String symbol;

/**
 * For example, C sharp;
 */
  protected String name;

/** The interval distance for the previous note in the tempered scale (or any other).*/
 /* Exemplos: C(previous natural note) - D = Second Major Interval
 *           D(previous natural note) - D sustenido = Segunda Menor
 *           Dó(nota natural imediatamente anterior) - Ré bemol = Segunda Menor.
 */
  protected Interval previousInterval;

/**
 * The interval distance for the previous note in the tempered scale (or any other)*/
 /* Exemplos: Sol - Lá(nota natural imediatamente posterior) = Segunda Maior
 *           Sol sustenido - Lá(nota natural imediatamente posterior) = Segunda Menor
 *           Sol bemol - Sol(nota natural imediatamente posterior) = Segunda Menor.
 */
  protected Interval nextInterval;

/**
 * Octave region. For example , A4 = octave 4;
 */
  protected int octavePitch;

 /** Midi value; Automatically calculated.*/
  protected int MidiValue = -1;
  
  /** Note Frequency; Automatically calculated.*/
  protected double frequency = -1;
  
  
 

/*
   * Instancia as propriedades da nota.
   * @param symbol Símbolo que identifica a nota. Exemplo: a nota
   * Mi é identificada através do simbolo "E".
   * @param name Nome da nota. Exemplo: "Sol".
   * @param previousInterval Interval formado com a nota natural imediatamente
   * anterior a uma nota dada. Exemplo: Quinta justa.
   * @param nextInterval Interval formado com a nota natural imediatamente
   * posterior a uma nota dada. Exemplo: sexta menor.
   */
 public Note(String idSymbol,String noteName, Interval previousInterval, Interval nextInterval){
    this.symbol = idSymbol;
    this.name = noteName;
    this.previousInterval = previousInterval;
    this.nextInterval = nextInterval;
    octavePitch = 5; //Why %? So inflexible!
    MidiValue = -1; //Só é calculado quando se deseja o valor;
    
  }

 public Note(String idSymbol,String noteName, Interval previousInterval, Interval nextInterval, int octavePitch, int MidiValue){
	    this.symbol = idSymbol;
	    this.name = noteName;
	    this.previousInterval = previousInterval;
	    this.nextInterval = nextInterval;
	    this.octavePitch =  octavePitch;
	    this.MidiValue = MidiValue; //Só é calculado quando se deseja o valor;
	  }
 
  public Note(String idSymbol,String noteName, Interval previousInterval, Interval nextInterval, int MidiValue){
    this.symbol = idSymbol;
    this.name = noteName;
    this.previousInterval = previousInterval;
    this.nextInterval = nextInterval;
    octavePitch = 5;
    this.MidiValue = MidiValue; //Só é calculado quando se deseja o valor;
  }

 //Usado pelo método clone;
  private Note(){};

  /*
 * Obtém o nome da nota. Exemplo: "Mi".
 * @return Nome da nota.
 */
  public String getNoteName() {return name;}

/*
 * Obtém o símbolo identificador da nota. Exemplo: "E".
 * @return Símbolo identificador de uma nota.
 */
  public String getSymbol() {return symbol;}

/*
   * Obtém o intervalo formado com a nota natural imediatamente anterior a uma nota dada.
   * Exemplos: Dó(nota natural imediatamente anterior) - Ré = Segunda Maior
   *           Ré(nota natural imediatamente anterior) - Ré sustenido = Segunda Menor
   *           Dó(nota natural imediatamente anterior) - Ré bemol = Segunda Menor.
   * @return Interval formado com a nota natural imediatamente anterior a uma nota dada.
   */
  public Interval getPreviousInterval() {return previousInterval;}

/*
   * Obtém o intervalo formado com a nota natural imediatamente posterior a uma nota dada.
   * Exemplos: Sol - Lá(nota natural imediatamente posterior) = Segunda Maior
   *           Sol sustenido - Lá(nota natural imediatamente posterior) = Segunda Menor
   *           Sol bemol - Sol(nota natural imediatamente posterior) = Segunda Menor.
   * @return Interval formado com a nota natural imediatamente posterior a uma nota dada.
   */
  public Interval getNextInterval() {return nextInterval;}


/*
 * Obtém a região onde a nota está localizada.
 * @return Região onde a nota está localizada.
 */
  public int getOctavePitch() {return octavePitch;}

/*
   * Inicializa a região onde a nota está localizada.
   * O - Mais Grave
   * 11 - Mais Aguda
   * @param pitch Região onde a nota está localizada.
   */
  public void setOctavePicth(int octavePitch) throws NoteException{
    /*@todo Verificar range de escalas do MIDI*/
    if ((octavePitch >= 0)&&(octavePitch <= 11)){
      this.octavePitch = octavePitch;
      WesternMusicNotes.calculateMidiValue(this);
      WesternMusicNotes.calculateFrequency(this);
    }else{
      throw new NoteException("Pitch's octave out of range",this.name);
    }
  }

/*
 * Verifica se uma nota possui alteração (sustenido ou bemol).
 * @return Verdadeiro de encontrar a alteração e falso caso contrário.
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
    
/*
   * Obtém o caracter que representa a nota natural, ou seja, nota sem alteração
   * (sustenido ou bemol).
   * @return Note natural.
   */
  public String getBaseNoteSymbol() {
    return symbol.substring(0,1);
  }

  public Note getBaseNote() throws NoteException{
      return WesternMusicNotes.getNote(getBaseNoteSymbol());
  }
  
  public int getMidiValue() throws NoteException {
    WesternMusicNotes.calculateMidiValue(this);    
    return MidiValue;
  }

  public boolean isSharp(){
    String acc = this.getAccident();
    if (acc != null) {
      return this.getAccident().equals("#");
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

  public boolean isDoubleFlat(){
  String acc = this.getAccident();
  if(acc!=null){
    return this.getAccident().equals("bb");
  }
  return false;
}

 //Valor Calculado no NoteCollection a partir da nota e do pitch
   void setMidiValue(int midiValue) {
       this.MidiValue = midiValue;
   }

/*
 * Obtém a alteração (sustenido ou bemol) de uma nota. Caso a nota não possua
 * alteração o retorno é null.
 * @return Alteração (sustenido ou bemol), ou null se não encontrada alteração na
 * nota.
 */
  public String getAccident() {
    String retorno = null;
    if (isAccidental()) {
      retorno = symbol.substring(1);
    }
    return retorno;
  }
  
public String toString(){
     return this.symbol + this.octavePitch;
 }
  
  @Override
public Object clone(){
    Note retorno = new Note();
    retorno.octavePitch = octavePitch;
    retorno.previousInterval = previousInterval;
    retorno.nextInterval = nextInterval;
    retorno.name = name;
    retorno.symbol = symbol;
    retorno.MidiValue = MidiValue;
    return retorno;
  }

public double getFrequency() throws NoteException {
	WesternMusicNotes.calculateFrequency(this);
	return frequency;
}

 void setFrequency(double frequency) {
	this.frequency = frequency;
}

public int compareTo(Object o) {
    int midiValue = (((Note)o).MidiValue);
    if(this.MidiValue < midiValue){
      return -1;
    }else{
      if(this.MidiValue > midiValue){
        return 1;
      }
    }
    return 0;
  }

public static Note getNote(String noteSymbol, int octavePitch) throws NoteException{
	return WesternMusicNotes.getNote(noteSymbol,octavePitch);
}

public static Note getNote(String noteSymbol) throws NoteException {

	    return WesternMusicNotes.getNote(noteSymbol);
	 
	  }
public static Note getNote(String simbFundamental, String simbIntervalo)  throws NoteException {
	return WesternMusicNotes.getNote(simbFundamental, simbIntervalo);
}
/**
 * Returns the MusicalEventSequence of a single note. To be used only
 * when the note needs to be heard out of musical context (just to see
 * how it sounds). 
 */
public MusicalEventSequence getMusicalEventSequence() {
	MusicalEventSequence p = new MusicalEventSequence();
	p.addMusicalEvent(new MusicalEvent(0,0,this,RhythmConstants.WHOLE_NOTE,RhythmConstants.DYNAMIC_MEZZO_FORTE));
	
	//If this line is removed the the synth will clip the note's sound.
	p.addMusicalEvent(new MusicalEvent(1,200,this,RhythmConstants.WHOLE_NOTE,0));
	
	return p;
}
}
