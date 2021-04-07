package octopus.instrument.fretted;

import javax.sound.midi.MidiUnavailableException;

import octopus.Arpeggio;
import octopus.Bar;
import octopus.Chord;
import octopus.Harmony;
import octopus.Melody;
import octopus.MusicPerformanceException;
import octopus.Note;
import octopus.NoteException;
import octopus.Notes;
import octopus.RhythmPattern;
import octopus.communication.MusicalEvent;
import octopus.communication.MusicalEventSequence;
import octopus.communication.SynthesizerController;
import octopus.communication.midi.GraphicalGuitarMidiReceiver;
import octopus.instrument.ChordShape;
import octopus.instrument.HarmonicPerformer;
import octopus.instrument.InstrumentNotePosition;
import octopus.instrument.PerformableHarmony;
import octopus.instrument.PerformableMelody;
import octopus.util.Log;

/**
 *The guitarist is a HarmonicPerformer that has the knowledge of the guitar
 * (actually, any fretted instrument). To instantiate a guitarist you have to
 * inform him which guitar he will play, this means, how many strings the
 * tuning, number of frets and any other parameter of playability.
 */

public class Guitarist extends HarmonicPerformer {


  private int nPluckHandFingers = 4;
  private int nFretHandFingers = 4;
  private int maxFingerStretch = 4;

  protected Guitar guitar; //just an alias for super.instrument

  protected GuitarChordShapeBuilder guitarChordShapeBuilder;

  private long iMsgId = 0;


  public Guitarist(Guitar guitar) throws MusicPerformanceException{
    super(guitar);
    this.guitar = guitar;
    chordShapeBuilder = new GuitarChordShapeBuilder(this);
    guitarChordShapeBuilder = (GuitarChordShapeBuilder)chordShapeBuilder;
    try {
      GuitarGraphicalInterface guitarLayout =(GuitarGraphicalInterface) guitar.getInstrumentInterface();
      this.setSynthesizerController(new GuitarMidiSynthesizerController(new
          GraphicalGuitarMidiReceiver(guitarLayout)));

      //this.setPlayer(new GuitarMidiSynthesizerController("EDIROL UA-25 MIDI OUT",new YamahaEzAgReceiver()));
    }
    catch (MidiUnavailableException ex) {
      throw new MusicPerformanceException("Midi Devices are not available.",ex);
    }

  }

  public Guitarist(Guitar guitar, SynthesizerController synthesizerController)  {
    super(guitar,synthesizerController);
    this.guitar = guitar;
    chordShapeBuilder = new GuitarChordShapeBuilder(this);
    guitarChordShapeBuilder = (GuitarChordShapeBuilder)chordShapeBuilder;

 }

  @Override
public void play(InstrumentNotePosition instrumentNotePosition) throws MusicPerformanceException{
    Note note = ( (Guitar)this.instrument).getNote( (GuitarNotePosition)
        instrumentNotePosition);
    //this.play( note, 127.0);
    this.play(note);
  }

  @Override
public void play(PerformableMelody pMelody) throws MusicPerformanceException {
    MusicalEventSequence p = new MusicalEventSequence();
    RhythmPattern rp = pMelody.getRythmPattern();
    Note[] notes = pMelody.getNotes();
    double time = 0;
    int spos = 0;
    Bar[] bars = rp.getBars(true);
    for (int e = 0; e < bars.length; e++) {
      Bar bar = bars[e];
      Bar.RhythmEvent[] rhythmEvents = bar.getRhythmEvents();
      for (int i = 0; i < rhythmEvents.length; i++) {
        double duration = rhythmEvents[i].
            duration;

        boolean tie = rhythmEvents[i].tie;
        int type = rhythmEvents[i].type;
        if ( (tie) && (i < rhythmEvents.length - 1)) {
          i++;
          duration += rhythmEvents[i].
              duration;
        }

        double velocity = type == 1 ? bar.getAccentuation(i) : 0;
        Note note = notes[spos];
        GuitarNotePosition guitarNotePosition = (GuitarNotePosition) pMelody.
            getNotePosition(note);

        if (spos >= notes.length - 1) {
          spos = 0;
        }
        else {
          spos++;
        }

         if(velocity>0){
           MusicalEvent meOct = new MusicalEvent(i, time, note, duration,
                                                 velocity);
           MusicalEvent meOctOff = new MusicalEvent(i, time + duration,
                                                    note,
                                                    0, 0);

           String finger = Integer.toString(guitarNotePosition.getFinger());
           String fret = Integer.toString(guitarNotePosition.getFret());
           String string = Integer.toString(guitarNotePosition.getString());

           meOct.put("fret", fret);
           meOct.put("finger", finger);
           meOct.put("string", string);

           meOctOff.put("fret", fret);
           meOctOff.put("finger", finger);
           meOctOff.put("string", string);

           p.addMusicalEvent(meOct);
           p.addMusicalEvent(meOctOff);
         }
        time += duration;
      }
    }
    p.setBpm(this.playingSpeed);
    player.play(p);

  }

  protected MusicalEventSequence getMusicalEventSequence(ChordShape chordShape,double duration) {

    MusicalEventSequence p = new MusicalEventSequence();
    GuitarChordShape guitarCS = (GuitarChordShape) chordShape;
    GuitarNotePosition[] pos = guitarCS.getGuitarNotePositions();
    for (int i = 0; i < pos.length; i++) {
      Note note = ( (Guitar) instrument).getNote(pos[i]);

      double iME = i;
      MusicalEvent me = new MusicalEvent( (long) i * 2, iME, note, duration,
                                         127.0);

      //test if is possible recovery this information
      me.put("fret", Integer.toString(pos[i].fret));
      me.put("string", Integer.toString(pos[i].string));
      me.put("finger", Integer.toString(pos[i].finger));

      p.addMusicalEvent(me);
    }
    p.setBpm(playingSpeed);
    return p;
  }

 
@Override
public void play(ChordShape chordShape, double duration) throws MusicPerformanceException {
    player.play(getMusicalEventSequence(chordShape, duration));
  }


public void play(Harmony harmony) throws MusicPerformanceException, NoteException {
    PerformableHarmony pHarmony = this.learn(harmony);
    System.out.println(pHarmony);
    this.play(pHarmony);
  }

  
public void play(Melody melody) throws MusicPerformanceException, NoteException {
    PerformableMelody pMelody = this.learn(melody);

    this.play(pMelody);
  }

 
@Override
public void play(PerformableHarmony pHarmony) throws MusicPerformanceException, NoteException {
    MusicalEventSequence p = new MusicalEventSequence();

    double time = 0;

    iMsgId = 0;

    RhythmPattern rp = pHarmony.getRythmPattern();
    Bar[] bars = rp.getBars(true);
    Chord[] chords = pHarmony.getChords();
    GuitarChordShape previousChordShape = null;
    //GuitarArpeggio previousGRP = null;

    for (int iBar = 0; iBar < bars.length; iBar++) {
      //Bar bar = bars[iBar];
      Bar.RhythmEvent[] rhythmEvents = bars[iBar].getRhythmEvents();
      int iToken = 0; //Circular list token: Repeat chord while has rhythmEvents

      for (int iRhythmEvent = 0; iRhythmEvent < rhythmEvents.length; iRhythmEvent++) {
        double duration = rhythmEvents[iRhythmEvent].duration;

        boolean tie = rhythmEvents[iRhythmEvent].tie;
        //int type = rhythmEvents[iRhythmEvent].type;
        if ( (tie) && (iRhythmEvent < rhythmEvents.length - 1)) {
          iRhythmEvent++;

          duration += rhythmEvents[iRhythmEvent].duration;
        }

        //double velocity = type == 1 ? bar.getAccentuation(iRhythmEvent) : 0;

        //Verifies is there is an arpeggio designated for the chord; If not,
        //creates one with the polyphony and duration of the chord ;
        
        
        //block not tested YET!!!!!  
        Arpeggio arpeggio =  pHarmony.getArpeggio(chords[iToken]);
        if (arpeggio == null) {
            arpeggio = new GuitarArpeggio(chords[iToken].getPolyphony(), duration);
          }else{
	        if (! (arpeggio instanceof GuitarArpeggio)) {
	            //arpeggio = this.parseToGuitarRhythmPattern(arpeggio);
	            arpeggio = new GuitarArpeggio(arpeggio);
	            this.learnGuitarRhythmPatten( (GuitarArpeggio) arpeggio);
	          }
          }
       
        
       
        //Verifies is there is a chord shape designted for the chord; If not,
        //calculates it. This situation only happens if the user construct the
        //PerformableHarmony for its own.;
        GuitarChordShape chordShape = (GuitarChordShape) pHarmony.getChordShape(
            chords[iToken]);

        if (chordShape == null) {
          chordShape = this.getChordShape(chords[iToken], previousChordShape,(GuitarArpeggio)arpeggio);
        }
        previousChordShape = chordShape;


        MusicalEventSequence chordSequence = this.getMusicalEventSequence(
            chordShape,
            (GuitarArpeggio)arpeggio, duration);
        chordSequence.delayEvents(time);
        p.addMusicalEventSequence(chordSequence);
        //long nextTime = 0;

        //Loop for keep playing the chords until the Rhythm Pattern finishes.
        if (iToken >= chords.length - 1) {
          iToken = 0;
        }
        else {
          iToken++;
        }

        time += duration;
      }

    }
    p.setBpm(this.playingSpeed);
    player.play(p);

  }

 public GuitarChordShape[] fillChordShape(GuitarChordShape guitarChordShape) throws NoteException{
   GuitarChordShapeBuilder csBuilder = guitarChordShapeBuilder;
    return csBuilder.processor.fillChordShape(guitarChordShape);
 }

  /**Generates a chord shape for the specifoed chord observing the parameters.
   * @param previousChordShape The previous chord shape;
   * @param guitarArpeggio the arpeggio
 * @throws NoteException 
   */
  private GuitarChordShape getChordShape(Chord chord,
                                         GuitarChordShape previousChordShape,
                                         GuitarArpeggio guitarArpeggio) throws
                                         MusicPerformanceException, NoteException {
   
	GuitarChordShapeBuilder csBuilder = guitarChordShapeBuilder;
    boolean isStrum = guitarArpeggio.isStrumming(this.nPluckHandFingers);
    boolean isFullArpeggio = guitarArpeggio.isFullArpeggio();
    GuitarChordShape chordShape;
    if (isStrum || isFullArpeggio) {
      if (previousChordShape == null) {
        chordShape = csBuilder.getFirstChordShape(chord, guitarArpeggio, isStrum);
      }
      else {
        chordShape = csBuilder.getSimilarChordShape(previousChordShape, chord,
            guitarArpeggio, isStrum);
      }
    }
    else {
      if (previousChordShape == null) {
        chordShape = csBuilder.getFirstChordShape(chord,
                                                  guitarArpeggio.getPolyphony());
      }
      else {
        chordShape = csBuilder.getSimilarChordShape(previousChordShape,
            chord, guitarArpeggio.getPolyphony());
      }

    }
    previousChordShape = chordShape;
    if (chordShape == null) {
      throw new MusicPerformanceException("The guitarrist does not know how to" +
                                           "play this chord in this instrument",
                                           this,chord);
    }
    else {
      return chordShape;
    }

  }

  protected MusicalEventSequence getMusicalEventSequence(GuitarChordShape
      guitarChordShape, GuitarArpeggio guitarArpeggio, double chordDuration) {
    MusicalEventSequence p = new MusicalEventSequence();
    double timeAdjustiveFactor = 0.0;
    int trackNumber = 0;


    for (int x = 0; x < guitarArpeggio.getPolyphony(); x++) {
      //int se = 0;
      //int si = 0;
      double time = 0;
      RhythmPattern rp = guitarArpeggio.getVoice(x);
      GuitarNotePosition[] notePosition = guitarChordShape.getGuitarNotePositions();
      trackNumber++;
      Bar[] bars = rp.getBars(true);
      for (int e = 0; e < bars.length; e++) {
        Bar bar = bars[e];
        Note note=null;      
        
        if (x < notePosition.length) { //if chord has fewer notes than the arpeggio voices
          note = ( (Guitar) instrument).getNote(notePosition[x]);
        }
        else {// do not attribute any note;
          /*note = ( (Guitar) instrument).getNote(notePosition[
                                                notePosition.length - 1]);*/
        }
        Bar.RhythmEvent[] rhythmEvents = bars[e].getRhythmEvents();

        while (time < chordDuration) {
          for (int i = 0; i < rhythmEvents.length; i++) {
            double duration = rhythmEvents[i].
                duration;
            boolean tie = rhythmEvents[i].tie;
            int type = rhythmEvents[i].type;
            if ( (tie) && (i < rhythmEvents.length - 1)) {
              i++;
              duration += rhythmEvents[i].
                  duration;
            }

            double velocity = type == 1 ? bar.getAccentuation(i) : 0;
            //verifica se é para esticar o arpeggio ou deixar na duracao
            // natural, cortando o que passar.

            if (! (time >= chordDuration)) { // Significa que o evento deveria comecar junto com o termino da duracao do acorde.
              if (guitarArpeggio.isTimeStratch()) {
                if (timeAdjustiveFactor == 0.0) {
                  double arpeggioDuration = guitarArpeggio.getDuration();
                  timeAdjustiveFactor = (chordDuration /
                                         arpeggioDuration);
                }
                duration = duration * timeAdjustiveFactor;
              }
              else {
                double finalDuration = time + duration; //da tempo de acabar?
                if (finalDuration > chordDuration) {
                  duration = finalDuration - chordDuration;
                }
              }


              if((velocity>0)&& (note!=null)){
              MusicalEvent meOct = new MusicalEvent(iMsgId++, time, note,
                  duration,
                  velocity);

              String finger = Integer.toString(notePosition[x].getFinger());
              String fret = Integer.toString(notePosition[x].getFret());
              String string = Integer.toString(notePosition[x].getString());

              meOct.put("fret", fret);
              meOct.put("finger", finger);
              meOct.put("string", string);

              p.addMusicalEvent(meOct);


                MusicalEvent meOctOff = new MusicalEvent(iMsgId++,
                                                         time + duration, note,
                                                         0, 0);

                meOctOff.put("fret", fret);
                meOctOff.put("finger", finger);
                meOctOff.put("string", string);

                p.addMusicalEvent(meOctOff);
              }
              time += duration;
            }

          }
        }
      }
    }
    return (p);
  }

  @Override
public PerformableMelody learn(Melody melody) throws NoteException {
    Note[] notes = melody.getNotes();
    PerformableMelody pMelody = new PerformableMelody(melody.getRythmPattern());
    GuitarNotePosition previousNotePosition = null;

    for (int i = 0; i < notes.length; i++) {
      GuitarNotePosition[] guitarNotePositions = guitar.
          geNotePositions(notes[i], true);
      GuitarNotePosition chosenNotePosition = null;

      if (guitarNotePositions.length > 0) {
        if ( (guitarNotePositions.length == 1) || (i == 0)) {
          chosenNotePosition = guitarNotePositions[0];
          previousNotePosition = chosenNotePosition;
        }
        else {
          chosenNotePosition = choosePosition(previousNotePosition,
                                              guitarNotePositions);
        }
      }
      else {
        //Exeception: nao tem essa nota no instrumento;
      }
      pMelody.addNote(notes[i], chosenNotePosition);
    }

    //Converte para GuitarBar
    RhythmPattern rp = pMelody.getRythmPattern();
    Bar[] bars = rp.getBars(false);
    for (int j = 0; j < bars.length; j++) {
      Bar bar = bars[j];
      GuitarBar guitarBar = new GuitarBar(bar.getNumberOfUnits(),
                                          bar.getMeasurementUnit());
      Bar.RhythmEvent[] rhythmEvent = bar.getRhythmEvents();
      for (int k = 0; k < rhythmEvent.length; k++) {

        guitarBar.addRhythmEvent(rhythmEvent[k].duration,
                                 rhythmEvent[k].type,
                                 rhythmEvent[k].tie);
      }
      rp.replaceBar(bar, guitarBar);
    }

    return pMelody;
  }

  private GuitarNotePosition choosePosition(GuitarNotePosition notePosition,
                                            GuitarNotePosition[] notePositions) {
    int minDistance = 0;
    GuitarNotePosition chosenPosition = null;
    for (int i = 0; i < notePositions.length; i++) {
      int stringDistance = Math.abs(notePosition.getString() -
                                    notePositions[i].getString());
      int fretDistance = Math.abs(notePosition.getFret() -
                                  notePositions[i].getFret());
      int totalDistance = stringDistance + fretDistance;
      if ( (totalDistance < minDistance) || (i == 0)) {
        minDistance = totalDistance;
        chosenPosition = notePositions[i];
      }
    }
    return chosenPosition;
  }

  private void learnArpeggioPlectrum(GuitarArpeggio guitarArpeggio) {
    Arpeggio.SortableRhythmEvent[] events = guitarArpeggio.getSortableRhythmEvents();
    GuitarBar.Stroke stroke;
    int previousVoice = 0;
    boolean downStroke = true;
    guitarArpeggio.setArpeggioStyle(GuitarArpeggio.PLECTRUM_PICKING_ARPEGGIO);
    for (int i = 0; i < events.length; i++) {

      if (events[i].voice == events[previousVoice].voice) {
        ;
        if (downStroke) {
          stroke = new GuitarBar.Stroke(GuitarBar.DIRECTION_DOWN_STROKE,
                                        GuitarBar.REGION_NEAR_MOUTH);
          downStroke = false;
        }
        else {
          stroke = new GuitarBar.Stroke(GuitarBar.DIRECTION_UP_STROKE,
                                        GuitarBar.REGION_NEAR_MOUTH);
          downStroke = true;
        }
      }
      else {
        if (events[i].voice > events[previousVoice].voice) {
          stroke = new GuitarBar.Stroke(GuitarBar.DIRECTION_DOWN_STROKE,
                                        GuitarBar.REGION_NEAR_MOUTH);
        }
        else {
          stroke = new GuitarBar.Stroke(GuitarBar.DIRECTION_UP_STROKE,
                                        GuitarBar.REGION_NEAR_MOUTH);
        }
      }
      ( (GuitarBar) events[i].bar).setStroke(events[i].rhythmEvent, stroke);
      previousVoice = i;
    }

  }

  private void learnArpeggioFingerStyle(GuitarArpeggio guitarArpeggio) {
    Arpeggio.SortableRhythmEvent[] events = guitarArpeggio.getSortableRhythmEvents();
    GuitarBar.Stroke stroke;
    //int previousVoice = 0;
    guitarArpeggio.setArpeggioStyle(GuitarArpeggio.CLASSICAL_FINGER_STYLE_ARPEGGIO);
    String[] pimax = {
        GuitarBar.FINGERPICKING_THUMB_FINGER,
        GuitarBar.FINGERPICKING_INDEX_FINGER,
        GuitarBar.FINGERPICKING_MIDDLE_FINGER,
        GuitarBar.FINGERPICKING_RING_FINGER};

    //int pimaxAdjustFactor = events.length - pimax.length;
     int pimaxAdjustFactor = guitarArpeggio.getPolyphony() - pimax.length;

    for (int i = 0; i < events.length; i++) {
      int pimaxIndex = events[i].voice - pimaxAdjustFactor - 1;//rever pq 2
      if (pimaxIndex < 0) {
        pimaxIndex = 0;
      }
      String finger = pimax[pimaxIndex];
      double region = GuitarBar.REGION_NEAR_MOUTH;
      int strokeDirection = GuitarBar.DIRECTION_UP_STROKE;
      switch(pimaxIndex){
        case 0:{ region = GuitarBar.REGION_THUMB_FINGER;
                strokeDirection = GuitarBar.DIRECTION_DOWN_STROKE;
                break;}
        case 1: region = GuitarBar.REGION_INDEX_FINGER;break;
        case 2: region = GuitarBar.REGION_MIDDLE_FINGER;break;
        case 3: region = GuitarBar.REGION_RING_FINGER;break;
      }


      stroke = new GuitarBar.Stroke(strokeDirection,region, finger);
      ( (GuitarBar) events[i].bar).setStroke(events[i].rhythmEvent, stroke);

    }
  }

  //Strum need to be reviewed since it depends on the attacks simulataneos directions(up or down)
  private void learnStrumFingerStyle(GuitarArpeggio guitarArpeggio) {
    Arpeggio.SortableRhythmEvent[] events = guitarArpeggio.getSortableRhythmEvents();
    GuitarBar.Stroke stroke;
    guitarArpeggio.setArpeggioStyle(GuitarArpeggio.CLASSICAL_FINGER_STYLE_ARPEGGIO);
    for (int i = 0; i < events.length; i++) {

      stroke = new GuitarBar.Stroke(GuitarBar.DIRECTION_DOWN_STROKE,
                                    GuitarBar.REGION_NEAR_MOUTH,
                                    GuitarBar.FINGERPICKING_THUMB_FINGER);
      ( (GuitarBar) events[i].bar).setStroke(events[i].rhythmEvent, stroke);

    }
  }

  //Strum need to be reviewed since it depends on the attacks simulataneos directions(up or down)
  private void learnStrumPlectrum(GuitarArpeggio guitarArpeggio) {
    Arpeggio.SortableRhythmEvent[] events = guitarArpeggio.getSortableRhythmEvents();
    GuitarBar.Stroke stroke;

    for (int i = 0; i < events.length; i++) {

      stroke = new GuitarBar.Stroke(GuitarBar.DIRECTION_DOWN_STROKE,
                                    GuitarBar.REGION_NEAR_MOUTH);
      ( (GuitarBar) events[i].bar).setStroke(events[i].rhythmEvent, stroke);

    }
  }

  public void learnGuitarRhythmPatten(GuitarArpeggio guitarArpeggio) {
    //Arpeggio.SortableRhythEvent[] events = guitarArpeggio.getSortableRhythmEvents();
    //GuitarBar.Stroke stroke;
    //int previousVoice = 0;

    //Introduzir funcao para inferir o tipo de strike
    if (!guitarArpeggio.isStrumming(this.nPluckHandFingers)) {
      if (guitarArpeggio.getArpeggioStyle() == GuitarArpeggio.PLECTRUM_PICKING_ARPEGGIO) {
        learnArpeggioPlectrum(guitarArpeggio);
      }
      else {
        this.learnArpeggioFingerStyle(guitarArpeggio);
      }
    }
    else {
      if (guitarArpeggio.getArpeggioStyle() == GuitarArpeggio.PLECTRUM_PICKING_ARPEGGIO) {
        learnStrumPlectrum(guitarArpeggio);
      }
      else {
        learnStrumFingerStyle(guitarArpeggio);
      }

    }
    guitarArpeggio.printStrokes();
  }

  @Override
public PerformableHarmony learn(Harmony harmony) throws MusicPerformanceException, NoteException {
    PerformableHarmony performableHarmony = new PerformableHarmony(harmony.
        getRythmPattern());
    Chord[] chords = harmony.getChords();
    GuitarChordShape previousChordShape = null;
    for (int i = 0; i < chords.length; i++) {
      Arpeggio arpeggio = harmony.getArpeggio(chords[i]);
      boolean isArpeggioNull = false;
      
      if(arpeggio==null){
    	/* this is a dummy arpeggio just created for the shake of chordshape computation.
    	 * ATTENTION: It is not to be passed to the PerformableHarmony since the its duration cannot be 
    	 * determined at this stage.
    	 */  
    	  arpeggio = new Arpeggio(chords[i].getPolyphony());
    	  isArpeggioNull = true;
      }
	      if (! (arpeggio instanceof GuitarArpeggio)) {
	        //arpeggio = this.parseToGuitarRhythmPattern(arpeggio);
	        arpeggio = new GuitarArpeggio(arpeggio);
	        this.learnGuitarRhythmPatten( (GuitarArpeggio) arpeggio);
	      }
      
      GuitarChordShape guitarChordShape = getChordShape(chords[i],
          previousChordShape, (GuitarArpeggio) arpeggio);
     
      if(isArpeggioNull){
    	  performableHarmony.addChord(chords[i], guitarChordShape);
      }else{
    	 performableHarmony.addChord(chords[i], guitarChordShape,arpeggio);
      }
    	 
    }

    return performableHarmony;
  }

  public void setMaxFingerStretch(int maxFingerStretch) {
    this.maxFingerStretch = maxFingerStretch;
  }

  /*@todo test if it is necessary recreate the chordshapebuilder*/
  public void setGuitar(Guitar guitar) {
    this.guitar = guitar;
  }

  public void setFretHandFingerNumber(int nFretHandFingers) {
    this.nFretHandFingers = nFretHandFingers;
  }

  public void setPluckHandFingerNumber(int nPluckHandFingers) {
    this.nPluckHandFingers = nPluckHandFingers;
  }

  public int getMaxFingerStretch() {
    return maxFingerStretch;
  }

  public Guitar getGuitar() {
    return guitar;
  }

  public int getFretHandFingerNumber() {
    return nFretHandFingers;
  }

  public int getPluckHandFingersNumber() {
    return nPluckHandFingers;
  }

  public ChordShape[] getChordShapes(Chord chord) throws NoteException{
   return guitarChordShapeBuilder.getChordShapes(chord);
  }

  public GuitarChordShape getChordShape(Chord chord, int polyphony) throws NoteException{
   return guitarChordShapeBuilder.getChordShape(chord, polyphony);
 }

  public ChordShape getChordShape(Chord chord) throws NoteException{
    return guitarChordShapeBuilder.getChordShape(chord, chord.getPolyphony());
  }

  public GuitarChordShape getSimilarChordShape(GuitarChordShape previousChordShape, Chord chord, int polyphony) throws NoteException{
    return guitarChordShapeBuilder.getSimilarChordShape( previousChordShape,  chord,  polyphony);
  }

 /* public ChordShape[] getChordShapes(Chord acorde, GuitarNotePosition notePos){
   // return chordShapeBuilder.getChordShapes( acorde,  notePos);
    return null;
  }*/

 public Log getLog(){
   return guitarChordShapeBuilder.getLog();
 }

 public static void main(String[] args) {
       try {

         Guitar guitar = new Guitar("");
         Guitarist g = new Guitarist(guitar);

          Harmony h = new Harmony(RhythmPattern.getDemoRhythmPattern());
          Arpeggio guitarArpeggio = Arpeggio.getDemoArpeggio(4);
          System.out.println(guitarArpeggio.toString());
          guitarArpeggio.setTimeStratch(false);

          h.addChord(new Chord("C"),guitarArpeggio);
          h.addChord(new Chord("F"),guitarArpeggio);
          h.addChord(new Chord("G"),guitarArpeggio);


           Note[] notes = {
             Notes.getA(),
             Notes.getF(),
             Notes.getB(),
             Notes.getE(),
             Notes.getG(),
             Notes.getE()};

         for (int i = 0; i < notes.length; i++) {
           notes[i].setOctavePitch(4);
         }

         Melody m = new Melody(notes, RhythmPattern.getDemoRhythmPattern());
         PerformableMelody pMelody = g.learn(m);
         System.out.println(pMelody);

         g.showInstrumentLayout();
         g.setPlayingSpeed(60.0);
         g.play(h);

       }
       catch (Exception ex) {
         ex.printStackTrace();
       }
  }

}
