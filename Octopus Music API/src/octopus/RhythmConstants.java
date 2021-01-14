package octopus;

/**
 * <p> Declares public constants of rhythmic values.</p>
 *
 * <p> Declares all the rhythmic constants used throughout the API. For example, the <code>Bar</code>
 * realises this interface and therefore can use the constants as shown: </p>
 * 
 * <code> bar.addRhythmEvent(Bar.QUARTER_NOTE, Bar.RHYTHM_EVENT_REST)</code>
 * 
 * 
 * @author Leandro Costalonga
 * @version 1.0
 */
public interface RhythmConstants {

  
  //ACCENTUATIONS
  /** {@value}*/
  public static final double ACCENT_STRONG = 1.0;
  /**{@value}*/
  public static final double ACCENT_HALF_STRONG = 0.85;
  /**{@value}*/
  public static final double ACCENT_WEEK = 0.7;


  //DYNAMICS VALUES
  /**{@value}*/
  public static final double DYNAMIC_PIANISSIMO = 0.1;
  /**{@value}*/
  public static final double DYNAMIC_PIANO = 0.2;
  /**{@value}*/
  public static final double DYNAMIC_FORTE = 0.8;
  /**{@value}*/
  public static final double DYNAMIC_MEZZO_PIANO = 0.4;
  /**{@value}*/
  public static final double DYNAMIC_MEZZO_FORTE = 0.6;
  /**{@value}*/
  public static final double DYNAMIC_FORTISSIMO = 1.0;

  
  //NOTE TYPES
  /**{@value}*/
  public static final int RHYTHM_EVENT_NOTE = 1;
  /**{@value}*/
  public static final int RHYTHM_EVENT_REST = 0;
  
  
  //NOTE DURATIONS
  /**{@value}*/
  public static final double DOUBLE_WHOLE = 2;
  /**{@value}*/
  public static final double WHOLE_NOTE = 1;
  /**{@value}*/
  public static final double HALF_NOTE = 0.5; //(1/2);4);
  /**{@value}*/
  public static final double QUARTER_NOTE = 0.25; //(1/4);
  /**{@value}*/
  public static final double EIGHT_NOTE = 0.125; //(1/8);
  /**{@value}*/
  public static final double SIXTEENTH_NOTE = 0.0625; //(1/16);
  /**{@value}*/
  public static final double THIRTY_SECOND_NOTE = 0.03125; //(1/32);


}
