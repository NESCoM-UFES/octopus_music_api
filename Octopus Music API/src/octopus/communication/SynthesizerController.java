package octopus.communication;


import octopus.MusicPerformanceException;


public  interface SynthesizerController {
	
 
  public  void play(MusicalEvent musicalEvent)throws MusicPerformanceException;

  public  void play(MusicalEventSequence musicalStructure)throws MusicPerformanceException;
    
  public  void stop();
  
  public void setBPM(float bpm);
  
  //public void setLoop(boolean isLoop);

  //send the messages to receiver
 // public  void setReceiver(OctopusReceiver octopusReceiver)throws PlayException;



}
