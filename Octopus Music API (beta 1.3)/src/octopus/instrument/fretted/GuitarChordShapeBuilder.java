package octopus.instrument.fretted;

import java.io.Serializable;

//import br.ufrgs.inf.lcm.database.*;
//import br.ufrgs.inf.lcm.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import octopus.Chord;
import octopus.MusicPerformanceException;
import octopus.Note;
import octopus.NoteException;
import octopus.instrument.ChordShape;
import octopus.instrument.ChordShapeBuilder;
import octopus.instrument.ChordShapeProperties;
import octopus.util.Log;
/**
 * Gera as representações dos acordes relativos ao instrumentos de corda previamente informado.
 * @see StringInstrument
 * @see ChordShapeBuilder
 * @see GuitarChordShape
 * @author Leandro Lesqueves Costalonga
 * @version 1.0
 */

 class GuitarChordShapeBuilder extends ChordShapeBuilder implements Serializable {

 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


protected FrettedInstrument instrument;


 //int qtPestanas;
 protected int startingFret;
 protected int endingFret;
 protected int startingString;
 protected int endingString;

 protected ArrayList<GuitarChordShape> chordShapes;
 protected ArrayList<Object> posTemp;
 protected FretController fretController;
 protected Guitarist guitarist;

 protected GuitarChordShapeProcessor processor;

 protected Log log;


 protected GuitarChordShapeBuilder( Guitarist guitarist){
    super(guitarist.guitar);
    this.instrument =  guitarist.guitar;
    options = new ChordShapeProperties();
    startingFret = 1;
    endingFret = instrument.getNumberOfFrets();
    startingString = 1;
    endingString = instrument.getStrings().length;
    chordShapes = new ArrayList<GuitarChordShape>();
    posTemp = new ArrayList<Object>();
    this.guitarist = guitarist;

    processor = new GuitarChordShapeProcessor(this);
  }

  /**
  * @return the esiest Chord Shape.
 * @throws NoteException 
  */
  public GuitarChordShape getChordShape(Chord chord, int polyphony) throws NoteException{
    GuitarChordShape[] basicChordShapes = (GuitarChordShape[])getChordShapes(chord);

    if(log!=null){
      log.writeLog("Basic chord's shapes of " + chord.getChordName());
      log.writeLog(basicChordShapes);
      log.writeLog("Chosen basic chord's shape: " + basicChordShapes[0]);
    }
    int nPossibleChordShapes =0;
    while (nPossibleChordShapes < basicChordShapes.length){
      GuitarChordShape[] possibleChordShapes = processor.fillChordShape(basicChordShapes[nPossibleChordShapes]);

      if(log!=null){
	log.writeLog("Computed chord's shape: " );
	log.writeLog(possibleChordShapes);
      }

      int i = 0;
      while (i<possibleChordShapes.length){
        if(possibleChordShapes[i].getPolyphony() == polyphony){
          if(log!=null){
	    log.writeLog("Chosen full chord's shape : " + possibleChordShapes[i]);
          }
          return possibleChordShapes[i];
        }
        i++;
      }
      nPossibleChordShapes++;
    }
    return null;
  }

  /*
  * Escolhe a representação de acorde mais similar a representação informada.
  * Para calcular a similaridade é considerado: localização da representacao,
  * quantidade de notas iguais e distancia entre os trastes.
  * @repAnterior Representação de referencia para comparação.
  * @acorde Chord referente as representações.
  * @qtNotesSimultaneas Número de notas que deve conter a representação escolhida.
  */
 /**
  * @return The most similiar chord shape related to the previous.
 * @throws NoteException 
  */
  public GuitarChordShape getSimilarChordShape(GuitarChordShape previousChordShape, Chord chord, int polyphony) throws NoteException{

    GuitarChordShape[] basicChordShapes = (GuitarChordShape[])getChordShapes(chord);
    GuitarChordShape similarChordShape = processor.getSimilarChordShape(previousChordShape,basicChordShapes);

    if(log!=null){
      log.cleanLog();
      log.writeLog("Basics chord's shapes of" + chord.getChordName());
      log.writeLog(basicChordShapes);
      log.writeLog("Chosen basic chord's shape: " + similarChordShape.toString());

    }

    while (true){
      GuitarChordShape[] possibleChordShapes = processor.fillChordShape(similarChordShape);

      if(log!=null){
	log.writeLog("Basic chord's shape computed " );
	log.writeLog(possibleChordShapes);
      }

      int i = 0;
      while (i<possibleChordShapes.length){
        if(possibleChordShapes[i].getPolyphony() == polyphony){
	  if(log!=null){
	    log.writeLog("Chosen full chord's shape: " + possibleChordShapes[i]);
	  }
          return possibleChordShapes[i];
        }
        i++;
      }

      if (basicChordShapes.length >1){
	Vector<GuitarChordShape> repTemp =new Vector<GuitarChordShape>(Arrays.asList(basicChordShapes));
	repTemp.remove(similarChordShape);
	basicChordShapes =(GuitarChordShape[]) repTemp.toArray(new GuitarChordShape[0]);
	similarChordShape = processor.getSimilarChordShape(previousChordShape,basicChordShapes);
      }else{
	break;
      }
    }

    return null;
  }

  public GuitarChordShape getSimilarChordShape(GuitarChordShape previousChordShape, Chord chord, GuitarArpeggio guitarArpeggio, boolean isAdjacentStrings) throws NoteException{

   if(chord.getNotes().length>guitarArpeggio.getPolyphony()){
    chord.remove5th();
   }
  GuitarChordShape[] basicChordShapes = (GuitarChordShape[])this.getChordShapes(chord);
  GuitarChordShape similarChordShape = processor.getSimilarChordShape(previousChordShape,basicChordShapes);

 // boolean cordasContiguas = padraoRitmico.containsArpejoCompleto();

  if(log!=null){
    log.cleanLog();
    log.writeLog("Basics chord's shapes of" + chord.getChordName());
    log.writeLog(basicChordShapes);
    log.writeLog("Chosen basic chord's shape:: " + similarChordShape.toString());
  }

  while (true){
    GuitarChordShape[] possibleChordShapes = processor.fillChordShape(similarChordShape);

    if(log!=null){
      log.writeLog("Basic chord's shape computed " );
      log.writeLog(possibleChordShapes);
    }

    int i = 0;
    while (i<possibleChordShapes.length){
      if (isAdjacentStrings){
        if((possibleChordShapes[i].isAdjacentStrings())&&
                (possibleChordShapes[i].getGuitarNotePositions().length >guitarArpeggio.getMinimumPolyphony()) &&
                (possibleChordShapes[i].getGuitarNotePositions().length <= 5)){
             if(log!=null){
               log.writeLog("Chosen full chord's shape: " + possibleChordShapes[i]);
             }
             return possibleChordShapes[i];
           }
      }else{
        if((possibleChordShapes[i].getPolyphony() == guitarArpeggio.getPolyphony())&&(possibleChordShapes[i].isAdjacentStrings(false))){
          if(log!=null){
            log.writeLog("Chosen full chord's shape: " + possibleChordShapes[i]);
          }
          return possibleChordShapes[i];
          }
      }

      i++;
    }

    if (basicChordShapes.length >1){
      Vector<GuitarChordShape> repTemp =new Vector<GuitarChordShape>(Arrays.asList(basicChordShapes));
      repTemp.remove(similarChordShape);
      basicChordShapes =(GuitarChordShape[]) repTemp.toArray(new GuitarChordShape[0]);
      similarChordShape = processor.getSimilarChordShape(previousChordShape,basicChordShapes);
    }else{
      break;
    }
  }

  return null;
  }


   public GuitarChordShape getFirstChordShape(Chord chord, int polyphony) throws NoteException{
//       GuitarChordShapeProcessor p = new GuitarChordShapeProcessor(instrument);
       if(chord.getNotes().length-1 ==polyphony){
         chord.remove5th();
       }
       GuitarChordShape[] basicChordShapes = (GuitarChordShape[])this.getChordShapes(chord);
       Arrays.sort(basicChordShapes, new FirstChordShapeComparator());

       if(log!=null){
         log.writeLog("Basic chord's shapes " + chord.getChordName());
         log.writeLog(basicChordShapes);
         log.writeLog("Choosen chord shape:: " + basicChordShapes[0]);
       }
       int contRepCandidatos =0;
       while (contRepCandidatos < basicChordShapes.length){
         GuitarChordShape[] possibleChordShapes = processor.fillChordShape(basicChordShapes[contRepCandidatos]);

         if(log!=null){
           log.writeLog("Basic chord shape processed: " );
           log.writeLog(possibleChordShapes);
         }

         int i = 0;

         while (i<possibleChordShapes.length){
           if(possibleChordShapes[i].getPolyphony() == polyphony){
             if(log!=null){
               log.writeLog("Choosen chord shape:: " + possibleChordShapes[i]);
             }
             return possibleChordShapes[i];
           }
           i++;
         }
         contRepCandidatos++;
       }
       return null;
  }

   public GuitarChordShape getEasiestChordShape(Chord chord, GuitarArpeggio guitarArpeggio, boolean isAdjacentStrings) throws NoteException{
//     GuitarChordShapeProcessor p = new GuitarChordShapeProcessor(instrument);
     if(chord.getNotes().length>guitarArpeggio.getPolyphony()){
       chord.remove5th();
     }
     GuitarChordShape[] basicChordShapes = (GuitarChordShape[])this.getChordShapes(chord);


     if(log!=null){
       log.writeLog("Representacoes basicas do acorde " + chord.getChordName());
       log.writeLog(basicChordShapes);
       log.writeLog("Representacao Basica Escolhida: " + basicChordShapes[0]);
     }
     int contRepCandidatos =0;
     while (contRepCandidatos < basicChordShapes.length){
       GuitarChordShape[] possibleChordShapes = processor.fillChordShape(basicChordShapes[contRepCandidatos]);

       if(log!=null){
         log.writeLog("Basic chord's shape computed " );
         log.writeLog(possibleChordShapes);
       }

       int i = 0;
       while (i<possibleChordShapes.length){
         if(possibleChordShapes[i].getPolyphony() == guitarArpeggio.getPolyphony()){
           if (isAdjacentStrings){
              if(possibleChordShapes[i].isAdjacentStrings()){
                if(log!=null){
                  log.writeLog("Chosen full chord's shape: " + possibleChordShapes[i]);
                }
                return possibleChordShapes[i];
              }
           }else{
             if(log!=null){
              log.writeLog("Chosen full chord's shape: " + possibleChordShapes[i]);
             }
             return possibleChordShapes[i];
           }
         }
         i++;
      }
       contRepCandidatos++;
     }
     return null;
  }

 public GuitarChordShape getFirstChordShape(Chord chord, GuitarArpeggio guitarArpeggio, boolean isAdjacentStrings) throws NoteException{
//     GuitarChordShapeProcessor p = new GuitarChordShapeProcessor(instrument);
     if(chord.getNotes().length>guitarArpeggio.getPolyphony()){
       chord.remove5th();
     }
     GuitarChordShape[] basicChordShapes = (GuitarChordShape[])getChordShapes(chord);
     Arrays.sort(basicChordShapes, new FirstChordShapeComparator());

     if(log!=null){
       log.writeLog("Basic chord's shapes " + chord.getChordName());
       log.writeLog(basicChordShapes);
       log.writeLog("Choosen chord shape: " + basicChordShapes[0]);
     }
     int contRepCandidatos =0;
     while (contRepCandidatos < basicChordShapes.length){
       GuitarChordShape[] possibleChordShapes = processor.fillChordShape(basicChordShapes[contRepCandidatos]);

       if(log!=null){
         log.writeLog("Basic chord shape processed: " );
         log.writeLog(possibleChordShapes);
       }

       int i = 0;
       while (i<possibleChordShapes.length){
         if (isAdjacentStrings){
           if((possibleChordShapes[i].isAdjacentStrings()) &&
                   (possibleChordShapes[i].getGuitarNotePositions().length >guitarArpeggio.getMinimumPolyphony()) &&
                    (possibleChordShapes[i].getGuitarNotePositions().length <= 5)){
             if(log!=null){
               log.writeLog("Choosen chord shape: " + possibleChordShapes[i]);
             }
             return possibleChordShapes[i];
           }
         }else{
           if ((possibleChordShapes[i].isAdjacentStrings()) && (possibleChordShapes[i].getPolyphony() == guitarArpeggio.getPolyphony())){
             if(log!=null){
               log.writeLog("Choosen chord shape:: " + possibleChordShapes[i]);
             }
             return possibleChordShapes[i];
           }
        }

         i++;
      }
       contRepCandidatos++;
     }
     return null;
    }


 @Override
public ChordShape[] getChordShapes(Chord chord) throws NoteException{
   return getChordShapes(chord,true);
 }

  @SuppressWarnings("unchecked")
public ChordShape[] getChordShapes(Chord chord, boolean isSupressionComputated) throws NoteException{

   GuitarChordShape possibleChordShape;
   ArrayList<Note> notes = new ArrayList<Note>(Arrays.asList(chord.getNotes()));
   //ArrayList<Note> intervals = new ArrayList<Intervals>(Arrays.asList(chord.get)); getIntervals?
   int nStrings = instrument.getStrings().length;
   chordShapes.clear();

   while (nStrings >= notes.size()) { // Faz enquanto tiver possibilidade de completar o acorde.
      GuitarNotePosition guitarNotePosition = instrument.getNotePosition(instrument.getString(nStrings), chord.getBassNote());

        while (guitarNotePosition!=null){ // Verifica se existe uma nota mais de uma vez em uma corda.

          possibleChordShape = new GuitarChordShape(chord, guitarist);

	  //repChordPrevisto.setDescricao(posNote + " -  Fundamental");
          try{
             possibleChordShape.addInstrumentNotePosition(guitarNotePosition, ((Boolean)options.get("showBarre")).booleanValue());
          }catch(MusicPerformanceException e){
	    if(log!=null){
	      log.writeLog("Phase 1: " + e.getMessage());
	     }
           }

          notes.remove(chord.getBassNote());

          assembleChordShape(chord,(ArrayList)notes.clone(), possibleChordShape, new FretController(guitarNotePosition,instrument,guitarist));
          guitarNotePosition = instrument.getNotePosition(instrument.getString(nStrings),
              chord.getBassNote(),
              (guitarNotePosition.getFret() +1));
        }

    nStrings--;
   }

  GuitarChordShape[] arrayReturn = (GuitarChordShape[])chordShapes.toArray(new GuitarChordShape[0]);
   if((chordShapes.size() ==0)&&((Boolean)options.get("supressFifth")).booleanValue()&& isSupressionComputated){
     chord.remove5th();
     arrayReturn = (GuitarChordShape[])getChordShapes(chord,false);
   }

  Arrays.sort(arrayReturn);


  return arrayReturn;
 }


/*
* Método recursivo privado auxiliar do método getRepresentacoesChords. Responsável
* por achar as notas dos acordes e aloca-las na representação.
*/
 @SuppressWarnings("unchecked")
private void assembleChordShape(Chord chord, ArrayList<Note> notes, GuitarChordShape basicChordShape, FretController fretController) throws NoteException{

       GuitarNotePosition guitarNotePosition;
       int currentString = basicChordShape.currentString;

       while (currentString >1){
         currentString--;

         for (int i=0; i < notes.size();i++ ) {

        InstumentString tmpString = instrument.getString(currentString);
        Note note = (notes.get(i));
        boolean isOctaveDiscern =  Boolean.getBoolean(options.getPropertyDisplayName("considerOctave"));

        guitarNotePosition = instrument.getNotePosition(tmpString, note,
                                            fretController.getLeftBoundary(),
                                            fretController.getRightBoundary(),
                                            isOctaveDiscern);


         if (guitarNotePosition != null){

           try{
           if (!notes.isEmpty()){

                 ArrayList notesClone = (ArrayList)notes.clone();

                 GuitarChordShape chordShapeClone = (GuitarChordShape)basicChordShape.clone();
                 chordShapeClone.addInstrumentNotePosition(guitarNotePosition,((Boolean)options.get("showBarre")).booleanValue());
                 chordShapeClone.setDescription(guitarNotePosition + " - " + chord.getInterval(note).getIntervalName());

                 notesClone.remove(i);
                 FretController fretControllerClone = (FretController)fretController.clone();
                 fretControllerClone.add(guitarNotePosition);

                assembleChordShape (chord, notesClone, chordShapeClone, fretControllerClone); // Chamada recursiva

             }else{
               break;
             }
           }catch(MusicPerformanceException e){
	     if(log!=null){
	       log.writeLog("Phase 1: " + e.getMessage());;
	     }
           }
         }
         }//for
       }// fim while
       if (notes.isEmpty()){

          basicChordShape.setChord(chord);
          int aberturaDedosRepAtual = fretController.finalFret - fretController.initialFret;
          basicChordShape.fingerStretchingRequired = aberturaDedosRepAtual;
          basicChordShape.initialFret = fretController.initialFret;
          basicChordShape.finalFret = fretController.finalFret;
          chordShapes.add(basicChordShape);
       }

  }

  /*
   * Permite Ligar/Desligar a opção de monitoração dos processamentos.
   * @param ON_OFF Se verdadeiro, o log irá armazenar as informaçoes relativas aos processamento dos momentoa posteriores.
   * Se falso, irá desligar a função de Log.
  */
 /**
  * Stores the computations in a log.
  * @param ON_OFF boolean turn log mode on or off.
  * @see octopus.util.Log
  */
 public void setLogOn(boolean ON_OFF){
     if(ON_OFF){
       this.log = new Log();
     }else{
       this.log = null;
     }
   }

   /*
   * Permite Ligar/Desligar a opção de monitoração dos processamentos.
   * @param log Envia as informações dos processamentos para o Log passado.
   * @see Log
  */
   public void setLogOn(Log log){
     this.log = log;
   }
   /**
    * @retrun O Log dos processamentos
    * @see Log
    */
   public Log getLog() {
     return log;
  }


  /**
   * Sort the chord shapes byt their fret average.
   */
  class FirstChordShapeComparator implements Comparator<Object> {
    public int compare(Object o1, Object o2) {
      GuitarChordShape rep1 = (GuitarChordShape) o1;
      GuitarChordShape rep2 = (GuitarChordShape) o2;
      int retorno = 0;
      int media1 = rep1.getFretAverage(true);
      int media2 = rep2.getFretAverage(true);

      if (media1 > media2) {
        retorno = 1;
      }
      else {
        retorno = -1;
      }
      return retorno;
    }
  }

}
