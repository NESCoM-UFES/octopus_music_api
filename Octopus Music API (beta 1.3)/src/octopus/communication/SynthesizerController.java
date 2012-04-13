package octopus.communication;


import octopus.*;


public  interface SynthesizerController {

  public  void play(MusicalEvent musicalEvent)throws MusicPerformanceException;

  public  void play(MusicalEventSequence musicalStructure)throws MusicPerformanceException;

  public  void stop();

  //send the messages to receiver
 // public  void setReceiver(OctopusReceiver octopusReceiver)throws PlayException;



}
