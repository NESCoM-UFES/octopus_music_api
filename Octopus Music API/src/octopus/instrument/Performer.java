package octopus.instrument;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import octopus.Melody;
import octopus.MusicPerformanceException;
import octopus.Musician;
import octopus.NoteException;
import octopus.communication.SynthesizerController;

/**
 * Performers are musicians that know how to play a musical instrument.
 * So besides the musicians knowledge, their also have particular knowledge of
 * a musical instrument. So all performer has an instrument associated with him.
 *
 * <p>It’s an abstract class, so the performer need to be specialised for a
 * particular instrument implementing the methods to play a particular
 * InstrumentNotePosition is his instrument, which could be a fret in a guitar,
 * a key in the keyboard and any physical position reference in an instrument.
 * All performers are able to play PerformableMelody, which is a melody with
 * note’s position information. A performer that is able to play also harmony is
 *  a HarmonicPerformer. A HarmonicPerformer is able to play ChordShapes and
 * PerformableHarmony.</p>

 * <p>All performers should be able to adapt a melody or a harmony to the
 * limitations to his instrument. For instance, when a performer plays a harmony
 * he will play it respecting the limitation of the instrument what can sound
 * slightly different of the sound “played” by the musician, although a performer
 * is a musician in a higher level. This adaptation is automatic, but if it is
 * necessary to edit the output of this adaptation then the method learn( )
 * should be used. This method returns the musical component with the
 * performable parameters.</p>
 *
 * @see Musician
 * @see HarmonicPerformer
 * @see PerformableMelody
 */
public abstract class Performer
    extends Musician {

  protected Instrument instrument;

  public Performer(Instrument instrument) throws MusicPerformanceException {
      super();
      this.instrument = instrument;
  }

  public Performer(Instrument instrument, SynthesizerController player)  {
   super(player);
   this.instrument = instrument;
 }

  public abstract PerformableMelody learn(Melody melody) throws NoteException;
  public abstract void play(InstrumentNotePosition instrumentNotePosition)throws
      MusicPerformanceException;
  public abstract void play(PerformableMelody pMelody)throws
      MusicPerformanceException;

  public void showInstrumentLayout(){
    JFrame frame = new JFrame();

    frame.setContentPane(instrument.getInstrumentInterface());
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize(800,220);
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);

  }


}
