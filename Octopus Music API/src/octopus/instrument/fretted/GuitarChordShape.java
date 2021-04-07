package octopus.instrument.fretted;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

import octopus.Chord;
import octopus.MusicPerformanceException;
import octopus.instrument.ChordShape;


public class GuitarChordShape extends ChordShape implements Comparable<Object>,  Cloneable, Serializable { //Removed Playable for the time being.

  
	private static final long serialVersionUID = 1L;

Vector<BarPosition> barPositions = new Vector<BarPosition>();

  protected FrettedInstrument instrument; //rever necessidade
  protected Guitarist guitarist; //rever necessidade

  protected int fingerStretchingRequired;
  public int initialFret;
  public int finalFret;
  //usada pelo Maker para controle
  public int currentString = -1;

  private int nFingersRequired = 0;


  public GuitarChordShape(Chord chord){
    super(chord);
  }

  public GuitarChordShape(Chord chord, Guitarist guitarist) {
    super(chord);
    this.instrument = guitarist.guitar;
    this.guitarist = guitarist;
  }


  /**
   * Create a Chordshape for the chord that containing the
   * note positions informed. There is no guarantee that his is a playable chord
   * shape.
   */
  public GuitarChordShape(Chord chord,GuitarNotePosition[] guitarNotePositions,Guitarist guitarist)  {
    super(chord);

    this.instrument = guitarist.guitar;
    this.guitarist = guitarist;
    for (int i = 0; i < guitarNotePositions.length; i++) {
      try {
        addInstrumentNotePosition( guitarNotePositions[i], true);
      }
      catch (MusicPerformanceException ex) {
        //nothing
      }
    }

  }

  public GuitarChordShape(Chord chord,GuitarNotePosition[] guitarNotePositions) {
    super(chord);
    this.notePositions.addAll(Arrays.asList(guitarNotePositions));
  }


  public boolean containsNotePosition(GuitarNotePosition posNote){
   for(int i=0;i<notePositions.size();i++){
     GuitarNotePosition pos1 = (GuitarNotePosition)notePositions.get(i);
     if((pos1.fret ==posNote.fret)&&(pos1.string ==posNote.string)){
       return true;
     }
   }
    return false;
 }

 public GuitarNotePosition[] getGuitarNotePositions(){
    GuitarNotePosition[] instrumentNotePositions = notePositions.toArray(new GuitarNotePosition[0]);
    Arrays.sort(instrumentNotePositions);
    return instrumentNotePositions;
  }


   public BarPosition[] getBarsPostions(){
    return barPositions.toArray(new BarPosition[0]);
  }


  @Override
public String toString(){
    String retorno = "";
    GuitarNotePosition[] posicaoArray = this.getGuitarNotePositions();
    Arrays.sort(posicaoArray);
    for (int i = 0; i < posicaoArray.length; i++) {
      GuitarNotePosition posicao = posicaoArray[i];
      retorno += String.valueOf(posicao.getString()) +
          String.valueOf(posicao.getFret()) + "; ";
      retorno += "(" + getRequiredFingersOpening() + ")";
      if (barPositions.size() > 0) {
        retorno += "- " + barPositions.get(barPositions.size() - 1);
      }
    }
      return retorno;
    }

  /*
     //Formato <53-40-21-10>
   public PosicaoNotaInstrumentoCorda[] getPosicoes(String repAcorde){
     Vector posicoes = new Vector();
     int i=1;
     while (i<repAcorde.length()){
       int f = repAcorde.indexOf("-", i)>-1?repAcorde.indexOf("-",i):repAcorde.indexOf(">");
       String posT = repAcorde.substring(i,f);
       //repAcorde = repAcorde.substring(f+1,repAcorde.length());
       int corda = Integer.parseInt(posT.substring(0,1));
       int casa = Integer.parseInt(posT.substring(1,posT.length()));
       PosicaoNotaInstrumentoCorda pos = new PosicaoNotaInstrumentoCorda(casa, corda);
       posicoes.add(pos);
       i = f+1;
     }
     return (PosicaoNotaInstrumentoCorda[])posicoes.toArray(new PosicaoNotaInstrumentoCorda[0]);
   }

  */

  public void addInstrumentNotePosition(GuitarNotePosition guitarNotePosition, boolean computeBarPostion) throws MusicPerformanceException{
    currentString = guitarNotePosition.getString();
    BarPosition pestana=null;
    if((guitarNotePosition.getFret()!=0)){
      if(nFingersRequired >= guitarist.getFretHandFingerNumber()){
        if(computeBarPostion){
	 pestana = isBarPositionPossible(guitarNotePosition);
        }
        if(pestana!=null){
          barPositions.add(pestana);
        }else{
          throw new MusicPerformanceException("Performer not able to perform this chord shape" );
        }
      }
    }
     super.addInstrumentNotePosition(guitarNotePosition);
     if((guitarNotePosition.getFret() !=0) && (pestana==null)){
       initialFret = guitarNotePosition.getFret() < initialFret? guitarNotePosition.getFret(): initialFret;
       finalFret = guitarNotePosition.getFret() > finalFret? guitarNotePosition.getFret(): finalFret;
       nFingersRequired++;
     }

  }

  /*private void addInstrumentNotePosition(GuitarNotePosition[] guitarNotePositions){
   
    this.notePositions.addAll(Arrays.asList(guitarNotePositions));
  }*/

 public int getNumberRequiredFingers(){
   return nFingersRequired;
 }


 /**
  * @return True if the notes positions are sequentially placed without
  * open strings between them.
  */
 public boolean isAdjacentStrings(){
    GuitarNotePosition[] posNotes = this.getGuitarNotePositions();
    for(int i=0;i<posNotes.length-1; i++){
      if(posNotes[i].string -1 != posNotes[i+1].string){
	return false;
	}
    }
    return true;
 }
  public boolean isAdjacentStrings(boolean includeBassNote){
    GuitarNotePosition[] posNotes = this.getGuitarNotePositions();
    int start = includeBassNote?0:1;
    for(int i=start;i<posNotes.length-1; i++){
      if(posNotes[i].string -1 != posNotes[i+1].string){
	return false;
	}
    }
    return true;
 }

 public BarPosition isBarPositionPossible(GuitarNotePosition guitarNotePosition){
   BarPosition barPosition=null;

    //if(posNota.casa == trasteInicial){
     GuitarNotePosition[] guitarNotePositions = getInstrumentNotesPositions(initialFret);
    // GuitarNotePosition[] posNotesPestana=null;


      GuitarNotePosition bottomGuitarNotePosition=null;
      GuitarNotePosition topGuitarNotePosition=null;
      //Acha até onde deve ir a pestana
     if(guitarNotePosition.fret == initialFret){
       topGuitarNotePosition = guitarNotePositions[0];
       bottomGuitarNotePosition =guitarNotePositions[guitarNotePositions.length -1];
      if(guitarNotePosition.getString()<guitarNotePositions[guitarNotePositions.length -1].getString()){
          bottomGuitarNotePosition = guitarNotePosition;
          //posMaior = posNotas[0];
        }
       if(guitarNotePosition.getString()>guitarNotePositions[0].getString()){
          topGuitarNotePosition = guitarNotePosition;
          //posMenor =posNotas[posNotas.length -1];
          }
     }else{
       if((this.nFingersRequired - (guitarNotePositions.length-1)<guitarist.getFretHandFingerNumber()) &&
          (barPositions.size()==0)){
         topGuitarNotePosition = guitarNotePositions[0];
         bottomGuitarNotePosition = guitarNotePositions[guitarNotePositions.length -1];
       }else{
         return null;
       }
     }

     GuitarNotePosition[] openStringPositions= getOpenStrings(1,topGuitarNotePosition.getString());
      InstumentString[] availableStrings = this.getAvailableStrings(topGuitarNotePosition.getString(), bottomGuitarNotePosition.getString());


       boolean pestanaOK = false;
       if ((openStringPositions.length==0)&& (availableStrings.length==0)){
         pestanaOK=true;
       }else{
         if((availableStrings.length==1)&&(openStringPositions.length==0)){
           if (guitarNotePosition.getString()==availableStrings[0].getStringNumber()){
             pestanaOK=true;
           }
         }
       }
       if(pestanaOK){
            barPosition = new BarPosition(bottomGuitarNotePosition.getString(),topGuitarNotePosition.getString(),bottomGuitarNotePosition.getString());
        }


   return barPosition;

  }

 //Calcula somente uma pestana no traste mais a esquerda da represnetacao.
/*  public BarChord isPestanaPossible(GuitarNotePosition posNote){

    BarChord pestana=null;

    if(posNote.fret == trasteInicial){
      GuitarNotePosition[] posNotes = getPosicoesNotes(posNote.getCasa());
      GuitarNotePosition[] posNotesPestana=null;

      GuitarNotePosition posMenor;
      GuitarNotePosition posMaior;

        if(posNote.getCorda()<posNotes[0].getCorda()){
          posMenor = posNote;
          posMaior = posNotes[posNotes.length -1];
        }else{
            posMenor =posNotes[0];
            posMaior = posNotes[posNotes.length -1];
            if(posNote.getCorda()>posNotes[posNotes.length -1].getCorda()){
              posMaior = posNote;
            }
          }
        GuitarNotePosition[] posCordasSoltas= getCordasSoltas(1,posMaior.getCorda());
        if (posCordasSoltas.length==0){
          posNotesPestana = getPosicoesNotesPestana(posMenor,posMaior);
          if (posNotesPestana != null){
            pestana = new BarChord(posMenor.getCorda(),posMaior.getCorda(),posMenor.getCasa());
            pestana.setPosicoesNotesPestana(posNotesPestana);
          }
        }
    }


   return pestana;
  }*/

 public GuitarNotePosition[] getOpenStrings(int initialString, int finalString){
   Vector<GuitarNotePosition> posNotesRetorno=new Vector<GuitarNotePosition>();

   for (int i=0; i<notePositions.size();i++){
     //if(((GuitarNotePosition)posicoes.get(i)).getCasa()!= 0){
     if((((GuitarNotePosition)notePositions.get(i)).getFret()==0) &&
         (((GuitarNotePosition)notePositions.get(i)).getString()>=initialString) &&
         (((GuitarNotePosition)notePositions.get(i)).getString()<=finalString)){

       posNotesRetorno.add((GuitarNotePosition) notePositions.get(i));

     }

   }
   GuitarNotePosition[] retorno = posNotesRetorno.toArray(new GuitarNotePosition[posNotesRetorno.size()]);
   Arrays.sort(retorno);

    return retorno;
 }
 public GuitarNotePosition getInstrumentNotePosition(int string){
   //GuitarNotePosition[] posNotesRetorno=null;
   GuitarNotePosition guitarNotePosition=null;

   for (int i=0; i<notePositions.size();i++){
     if(((GuitarNotePosition)notePositions.get(i)).getString()==string){
       guitarNotePosition = ((GuitarNotePosition)notePositions.get(i));
       }
   }

   return guitarNotePosition;
  }

  public GuitarNotePosition[] getInstrumentNotesPositions(int fret){
    //GuitarNotePosition[] posNotesRetorno=null;
    Vector<GuitarNotePosition> guitarNotePositions=new Vector<GuitarNotePosition>();

    for (int i=0; i<notePositions.size();i++){
     //if(((GuitarNotePosition)posicoes.get(i)).getCasa()!= 0){
      if(((GuitarNotePosition)notePositions.get(i)).getFret()==fret){
        guitarNotePositions.add((GuitarNotePosition) notePositions.get(i));
       }

    }
    GuitarNotePosition[] returnArray = guitarNotePositions.toArray(new GuitarNotePosition[guitarNotePositions.size()]);
    Arrays.sort(returnArray);

    return returnArray;
  }

  //testar
  /*public GuitarNotePosition[] getBarNotesPosition(GuitarNotePosition initialGuitarNotePosition,
                                                  GuitarNotePosition finalGuitarNotePosition){
    boolean isBarPositionPossible = true;
    Vector barGuitarNotePositions = new Vector();

    int fret = initialGuitarNotePosition.getFret();
    int initialString = initialGuitarNotePosition.getString()+1;
    int finalString = finalGuitarNotePosition.getString()-1;

    ChordShapeProperties chordShapeProperties = new ChordShapeProperties();
    int cont = initialString;
    while((cont<=finalString)&&(isBarPositionPossible)){//while
      int i = cont -initialString;
      //Note nota = instrumento.getNote(posNoteInicio);
      //olha se tem algum nota posiçao na frente da pestana, ou seja, corda não usada!
      if(this.getInstrumentNotePosition(cont)==null){

        GuitarNotePosition posNoteAdd = new GuitarNotePosition(fret,cont);
        Note nota = instrument.getNote(posNoteAdd);
        //ChordNote notaAcorde = new ChordNote(nota,IntervaloCollection.getFundamental());
        ChordNote notaAcorde = chord.getChordNote(nota);
        if(notaAcorde!=null){
          int intervaloNumerico = notaAcorde.getRole().getNumericInterval();
          switch (intervaloNumerico){
            case 1:
              if(!((Boolean)chordShapeProperties.get("duplicateFundamental")).booleanValue()){
                isBarPositionPossible = false;
                break;
              }
              break;

            case 3:
              if(!((Boolean)chordShapeProperties.get("doubleThird")).booleanValue()){
                isBarPositionPossible = false;
                break;
              }
              break;

            case 5:
              if(!((Boolean)chordShapeProperties.get("doublePerfectFifth")).booleanValue()){
                isBarPositionPossible = false;
                break;
              }
              break;

          }
        }
        //posicoesNotesPestana[i] = posNoteAdd;
        barGuitarNotePositions.add(posNoteAdd);
        //posicoesNotesPestana[i] => Descriçao
      }
      cont++;
    }
    if (!isBarPositionPossible){
      barGuitarNotePositions = null;
    }
    return ((GuitarNotePosition[])barGuitarNotePositions.toArray(new GuitarNotePosition[0]));
  }*/


  public int getRequiredFingersOpening(){
    return finalFret - initialFret;
  }

  public int[] getAvailableStrings(){
    return getAvailableStrings(false);
  }

  public int getFretAverage(boolean isOpenStringComputed){
    double media1 = 0;
    int div = 0;
    GuitarNotePosition[] guitarNotePositions = this.getGuitarNotePositions();
    for (int i=0;i<guitarNotePositions.length;i++){
      if(isOpenStringComputed){
        media1 += guitarNotePositions[i].getFret();
        div++;
      }else{
        if(guitarNotePositions[i].getFret()!= 0){
           media1 += guitarNotePositions[i].getFret();
           div++;
          }
      }
    }
    media1 = media1/div;
    return (int)Math.round(media1);
  }
  public int[] getAvailableStrings(boolean isUpperBassNoteStringScanned){
    int nStrings =instrument.getStrings().length;
    int polyphony = notePositions.size() ;
    HashSet<Integer> availableStrings=new HashSet<Integer>();

    int[] arrayReturn = new int[0];

    //verifica se existe cordas em náo usadas
    if(polyphony < nStrings){

      //inicializa vetor de cordas
      //      cordasDisponiveis = new int[cordasDisponiveis];
      if(!isUpperBassNoteStringScanned){
	GuitarNotePosition posicaoNote = (GuitarNotePosition)notePositions.get(0);
	nStrings = posicaoNote.string;
      }

      for(int i=1;i<=nStrings;i++){
	availableStrings.add(new Integer(instrument.getString(i).getStringNumber()));
      }

      for(int i=0; i<polyphony; i++){
        availableStrings.remove(new Integer(((GuitarNotePosition)notePositions.get(i)).getString()));
      }

      Integer[] cordasRestantes = availableStrings.toArray(new Integer[availableStrings.size()]);
      arrayReturn = new int[cordasRestantes.length];

      for(int i=0;i<cordasRestantes.length;i++){
        arrayReturn[i]= cordasRestantes[i].intValue();
      }
    }
    return arrayReturn;
  }


  /**
   * Search string that has not been used in the ranger between bottomString and
   * upperString.
   */
  public InstumentString[] getAvailableStrings(int upperString, int bottomString){
    Vector<InstumentString> strings = new Vector<InstumentString>();
    for(int i = upperString; i>=bottomString;i--){
      GuitarNotePosition posNaoUsada = this.getInstrumentNotePosition(i);
      if(posNaoUsada == null){
	strings.add(instrument.getString(i));
      }
    }
    return strings.toArray(new InstumentString[0]);
  }


 /**
  * If this method is changed, then the fingering suggestion should be checked too.
  */
  @Override
public int compareTo(Object obj2){
    GuitarChordShape rep2 = (GuitarChordShape) obj2;
    int retorno = 0;
    int qtComparacao;

    qtComparacao = rep2.getInstrumentNotePositions().length < notePositions.size()? rep2.getInstrumentNotePositions().length: notePositions.size();
    if(rep2.fingerStretchingRequired == fingerStretchingRequired){
//      if(rep2.getPosicoesNotes().length == posicoes.size()){
      if(rep2.getNumberRequiredFingers()== nFingersRequired){
        for (int i=0; i< qtComparacao;i++){
          GuitarNotePosition pos1 = (GuitarNotePosition)rep2.notePositions.get(i);
          GuitarNotePosition pos2 = (GuitarNotePosition)notePositions.get(i);

          if (pos1.compareTo(pos2) == 1){
            retorno = 1;
            break;
          }else{
            if(pos1.compareTo(pos2) == -1){
              retorno = -1;
              break;
            }
          }
        }
      }else{
        if(rep2.getNumberRequiredFingers()< nFingersRequired){
          retorno = 1;
        }else{
            retorno = -1;
          }
      }

    }else{
        if(rep2.fingerStretchingRequired < fingerStretchingRequired){
          retorno = 1;
        }else{
            retorno = -1;
          }
      }

    return retorno;
  }


  @SuppressWarnings("unchecked")
@Override
public Object clone() {//throws CloneNotSupportedException{
    GuitarChordShape clone = new GuitarChordShape(chord);
    //clone.posicoes = (PosicaoNoteInstrumento[])posicoes.clone();
    clone.guitarist = guitarist;
    clone.notePositions = (Vector)notePositions.clone();
    clone.finalFret = finalFret;
    clone.initialFret = initialFret;
    clone.fingerStretchingRequired = fingerStretchingRequired;
    clone.description = description;
    clone.barPositions = (Vector)barPositions.clone();
    clone.chord = chord;
    clone.instrument = instrument;
    clone.nFingersRequired = nFingersRequired;
    return clone;

  }

class BarPosition implements Serializable{
 
	private static final long serialVersionUID = 1L;
int initialString;
 int finalString;
 int fret;
 int finger;

 GuitarNotePosition[] guitarNotePositions;

 public BarPosition( int initialString,int finalString, int fret) {
   this.initialString = initialString;
   this.finalString = finalString;
   this.fret = fret;
  }

  public void setGuitarNotePositions(GuitarNotePosition[] barChordPositions){
    this.guitarNotePositions = barChordPositions;
  }

  public GuitarNotePosition[] getGuitarNotePositions(){
    return guitarNotePositions;
  }

@Override
public String toString(){
     return "Bar on fret "  + fret +" starting on string " + initialString + " and ending on string " +finalString;
   }

}


}

