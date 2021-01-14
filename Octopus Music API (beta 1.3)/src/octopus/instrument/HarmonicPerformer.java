package octopus.instrument;

import octopus.Harmony;
import octopus.MusicPerformanceException;
import octopus.NoteException;
import octopus.communication.SynthesizerController;

/**
 * HarmonicPerformer are Performer that play chord shapes and harmonies in a specific
 * polyphonic instrument.
 */
public abstract class HarmonicPerformer extends Performer {
 protected ChordShapeBuilder chordShapeBuilder;

  public HarmonicPerformer(Instrument instrument) throws MusicPerformanceException {
    super(instrument);
  }

  public HarmonicPerformer(Instrument instrument, SynthesizerController player)  {
    super(instrument,player);
}


  public abstract void play(ChordShape chordShape, double duration) throws
      MusicPerformanceException,NoteException;;
  public abstract PerformableHarmony learn(Harmony harmony)throws
      MusicPerformanceException, NoteException;
  public abstract void play(PerformableHarmony pHarmony)throws
      MusicPerformanceException, NoteException;


  /**
   * Show a configuration dialog to change the parameter of the chord shape
   * computation.
   */
  public void showChordDesignProperties(boolean modal){
    chordShapeBuilder.showChordDesignProperties(modal);
  }

}
