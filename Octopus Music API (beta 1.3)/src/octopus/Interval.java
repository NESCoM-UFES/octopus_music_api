package octopus;


import java.io.*;


/**
 * A musical Interval. For example,  3rd minor.
 * <p> Instead of instantiate the intervals directly,prefer use the IntervalFactory static methods. 
 * For example: </p>
 * <code> WesternMusicNotes.getMinorThird()</code> 
 * 
 * @see java.lang.Object
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
public class Interval implements Serializable{

  
private static final long serialVersionUID = 1L;
	
private String symbol; // Exemplo para um intervalo Segunda Aumentada: "#2"
  private int numericInterval; // Exemplo para um intervalo Segunda Aumentada: 2
  private String name; // Exemplo para um intervalo Segunda Aumentada: "Segunda Aumentada"
  private int distanceFromRoot; // Exemplo para um intervalo Segunda Aumentada (medido em semiton): 3
  private int classification; // Menor, maior....etc...
  
  
  public static final int DIMINISHED = -2;
  public static final int MINOR = -1;
  public static final int  PERFECT= 0;
  public static final int  MAJOR= 1;
  public static final int  AUGMENTED= 2;

/*
 * Instancia as propriedades do intervalo.
 * @param simbolo Símbolo Identificador do intervalo. Exemplo para um intervalo
 * Segunda Aumentada: "#2".
 * @param intNumerico Parte numérica do intervalo.
 * @param nome Nome por extenso do intervalo.
 * @param distancia Número de semitons de um intervalo.
 */
  public Interval(String simbolo,int intNumerico, String nome, int distancia) {
    symbol = simbolo;
    numericInterval = intNumerico;
    name = nome;
    distanceFromRoot = distancia;
  }

   public Interval(String symbol,int numericInterval, String name, int rootDistance, int classification) {
    this.symbol = symbol;
    this.numericInterval = numericInterval;
    this.name = name;
    this.distanceFromRoot = rootDistance;
    this.classification = classification;
  }
/**
 * @return Symbol of the interval specified in the notation; i.e "9b" for 9th diminished interval. 
 */
  public String getSymbol() {return symbol;}

/**
 * @return The internal number without the accidents (aug, add, dim etc.);
 */
  public int getNumericInterval() {return numericInterval;}

/**
 * 
 * @return Interval full name. 
 */
  public String getIntervalName() {return name;}

/**
 * @return Semitons counting  from the root note.
 */
  public int getDistanceFromRoot() {return distanceFromRoot;}

  public int getClassification(){
      return classification;
  }
  
  public boolean isDiminished(){
      if(classification ==Interval.DIMINISHED){
          return true;
      }
    return false;      
  }
  
    public boolean isMinor(){
      if(classification ==Interval.MINOR){
          return true;
      }
    return false;      
  }
  public boolean isPerfect(){
      if(classification ==Interval.PERFECT){
          return true;
      }
    return false;      
  }
  
    public boolean isMajor(){
      if(classification ==Interval.MAJOR){
          return true;
      }
    return false;      
  }
    
  public boolean isAugmented(){
      if(classification ==Interval.AUGMENTED){
          return true;
      }
    return false;      
  }    
  
  public String toString(){
        return this.name + "(" +this.symbol +")";
  }
  
}
