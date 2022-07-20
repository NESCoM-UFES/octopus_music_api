package octopus;
import octopus.communication.MusicalEventSequence;

/**
 * The Playable Interface indicates that a class that realises it can be "playable" by a <code> Musician </code>.
 * 
 * @see Musician
 * @author lcostalonga
 *
 */
public interface Playable {
	
  //float volume = 1.0f;	 //value between 0 and 1 used to scale de volume of the musical events.

  public MusicalEventSequence getMusicalEventSequence();
  
 
  //public Map getPlayableAttributes();
	
  @Override
public String toString();	

}
