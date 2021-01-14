package octopus.instrument;

import javax.swing.JPanel;

public abstract class InstrumentGraphicalInterface
    extends JPanel {

  Instrument instrument;

  public InstrumentGraphicalInterface(Instrument instrument) {
    super();
    this.instrument = instrument;
  }

 public abstract void turnOn(InstrumentNotePosition instrumentNotePosition);
 public abstract void turnOff(InstrumentNotePosition instrumentNotePosition);

}
