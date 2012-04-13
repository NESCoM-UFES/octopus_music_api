package octopus.instrument.fretted;

import octopus.*;
import octopus.util.*;
import octopus.instrument.*;

import java.util.*;
import java.io.*;


/*
 * Executa processamentos sobre representacoes de acordes para instrumento de corda.
 * O principal processamento que esta classe executa é preencher uma representação(desenho) de acorde básica
 * com dobramentos, ou seja, notas repetidas.  Dobramento, duplicações e supressões são práticas comums para
 * adequar o acorde e suas representações(desenhos) as restriçoes do instrumento e características da música.
 * @see RepresentacaoChordInstrumentoCordaMaker
 * @see GuitarChordShape
 * @author Leandro Lesqueves Costalonga
 * @version 1.0
 */
 class GuitarChordShapeProcessor implements Serializable{

  	private static final long serialVersionUID = 1L;

ChordShapeProperties chordShapeProperties = new ChordShapeProperties();

  //Instrumento usado no processamento.
  FrettedInstrument instrument; //alias for the guitarrist instrument

  Guitarist guitarist; //alias for the guitarist

  //Onde é totalizada as representacoes processadas pelos algoritmos recursivos.
  Hashtable<String, GuitarChordShape> computedChordShapes;

  // Classe que permite analisar os processamentos efetuados.
   Log log;

  //Ligada quando for desejável registras os eventos no Log.
  boolean logTurnedOn =  false;

  int nOperations =0;
  //String formulaContProcessamentos ="";

/**
  * Cria um GuitarChordShapeProcessor para o StringInstrument especificado.
  * @param instrumento StringInstrument usado como base para os processamentos dos dobramentos.
  */
 public GuitarChordShapeProcessor(GuitarChordShapeBuilder guitarChordShapeBuilder){
   this.instrument = guitarChordShapeBuilder.instrument;
   this.guitarist = guitarChordShapeBuilder.guitarist;
 }

/*
  * Cria um GuitarChordShapeProcessor para o StringInstrument especificado.
  * @param instrumento StringInstrument usado como base para os processamentos dos dobramentos.
  * @param log Log que permite analisar os processamentos efetuados.
  */
/* public GuitarChordShapeProcessor(FrettedInstrument instrument, Log log){
   this.instrument = instrument;
   this.log = log;
 }*/


/**
  * Gera todas as representações possiveis baseado na representação básica e tipos de dobramentos permitidos default.
  * @param repChords Representação básica(sem dobramentos) que deve ser processada.
  * @return Vetor de GuitarChordShape com o resultado do processamento de da representação básica passada.
 * @throws NoteException 
  */
 public  GuitarChordShape[] fillChordShape(GuitarChordShape chordShape) throws NoteException{
   return fillChordShape(chordShape, new ChordShapeProperties());
 }

/**
   * Gera todas as representações possiveis baseado na representação básica e tipos de dobramentos indicados na
   * ChordShapeProperties passada como parametro.
   * @param repChords Representação básica(sem dobramentos) que deve ser processada.
   * @param processamentos ChordShapeProperties com os processamentos que devem ser feitos na representação.
   * @return Vetor de GuitarChordShape com o resultado do processamento de da representação básica passada.
 * @throws NoteException 
   */
  public  GuitarChordShape[] fillChordShape(GuitarChordShape chordShape,
     ChordShapeProperties operations) throws NoteException{

      computedChordShapes = new Hashtable<String, GuitarChordShape>();
      Vector<GuitarChordShape> retorno = new Vector<GuitarChordShape>();

      nOperations =0;
      nOperations++;
      long timeI = System.currentTimeMillis();

      if(log!=null){
        log.cleanLog();
        log.writeLog("Original ChodShape: " + chordShape);
      }

      addChordShape(chordShape);

      performOperation((GuitarChordShape)chordShape.clone(),(ChordShapeProperties)operations.clone(),0);


      retorno.addAll(computedChordShapes.values());
      GuitarChordShape[]retornoTemp = (GuitarChordShape[])computedChordShapes.values().toArray(new GuitarChordShape[0]);
     // GuitarChordShape[]retornoTemp = (GuitarChordShape[])retorno.toArray(new GuitarChordShape[0]);
      Arrays.sort(retornoTemp);

      if(log!=null){
        log.writeLog("Processing output: " + retornoTemp.length);
        log.writeLog(retornoTemp);
        log.writeLog("Number of processes: " +  nOperations );
        log.writeLog("Processing time: " +  String.valueOf(System.currentTimeMillis() - timeI ) + "ms" );
      }

      if(((Boolean)operations.get("suggestFingering")).booleanValue()){
	for(int i=0;i<retornoTemp.length;i++){
	  showFingering(retornoTemp[i]);
	}
      }

      return retornoTemp;
  }


  public  GuitarChordShape getSimilarChordShape(GuitarChordShape basicChordShape, GuitarChordShape[] chordShapes){
    double maxSimilaridade = Double.parseDouble("0.000");
    int indexMaisSimilar = 0;


    Log l2 = new Log(Log.JUST_SCREEN);


    for(int i=0; i < chordShapes.length; i++){
      double s = getSimilarity(basicChordShape,chordShapes[i]);
      l2.writeLog(chordShapes[i].toString() + " - " + s);
      if(s>maxSimilaridade){
	indexMaisSimilar = i;
	maxSimilaridade = s;
	//l2.writeLog(repChords[i].toString() + " - " + s);
      }else {
	if ((s==maxSimilaridade)&&(i>0)){
	/*GuitarNotePosition pR = (GuitarNotePosition)repChordPrevio.posicoes.get(0);
	GuitarNotePosition pC1 = (GuitarNotePosition)repChords[indexMaisSimilar].posicoes.get(0);
	GuitarNotePosition pC2 = (GuitarNotePosition)repChords[i].posicoes.get(0);*/
	//if(Math.abs(pR.getCasa() -pC1.getCasa())>Math.abs(pR.getCasa() -pC2.getCasa())){
        if(chordShapes[indexMaisSimilar].getFretAverage(false) > chordShapes[i].getFretAverage(false))
	  indexMaisSimilar = i;
	 // l2.writeLog(repChords[i].toString() + " - " + s);
	}
      }
    }
    if(chordShapes.length!=0){
      return chordShapes[indexMaisSimilar];
    }else{
      return null;
    }
  }


/**
* Retorna índice de similaridade entre as representações dos acordes.
* @param repReferencia Representação do acorde se servirá de referencia para a comparação de similaridade.
* @param repComparacao Representação do acorde será comparada com a referencia.
* @return Indice de comparaçao. Quanto maior, mais similar é a representaçao de referencia.
*/
  private  double getSimilarity(GuitarChordShape fullChordShape /*completo*/, GuitarChordShape basicChordShape){

    GuitarNotePosition[] basicChordShapePositions = basicChordShape.getGuitarNotePositions();
    int bottomString = instrument.getBottonString().getStringNumber();
    int topString = instrument.getTopString().getStringNumber();

    double ss[][] = new double[basicChordShapePositions.length][instrument.getStrings().length];
    double similarityFactor = 0.0;

    for (int i=0; i<basicChordShapePositions.length;i++){
        GuitarNotePosition pC = basicChordShapePositions[i]; // GuitarNotePosition of the Basic Chord Shape

        // //Check if will not try get a string not specified in the instrument
        int initialString = topString; //Measure distances of the note position one line bellow and one above
        if((pC.string -1)>=topString){
          initialString = pC.string -1;
        }

        //Check if will not try get a string not specified in the instrument
        int finalString = bottomString;
        if((pC.string +1)<=bottomString){
          finalString = pC.string +1;
        }

        int cont = 0;
	double rowAverage = 0.0;

        while(initialString<=finalString){

          GuitarNotePosition pR = fullChordShape.getInstrumentNotePosition(initialString);
          if(pR!=null){
            double fretFactor;
            if((pC.fret==0)||(pR.fret==0)){ //open string
              double media = basicChordShape.getFretAverage(false) - fullChordShape.getFretAverage(false);
	      media = media==0?1:media;
	      media+= (media *0.5);
              fretFactor = Math.abs(media)< guitarist.getMaxFingerStretch()?Math.abs(media):guitarist.getMaxFingerStretch();
            }else{
              fretFactor = Math.abs(pR.getFret() - pC.getFret())<=guitarist.getMaxFingerStretch()?Math.abs(pR.getFret() - pC.getFret()):guitarist.getMaxFingerStretch();
            }

            if(pC.string == pR.string){
              ss[i][initialString-1]= 1.0-((1.0/guitarist.getMaxFingerStretch())*(fretFactor));
            }else{
                ss[i][initialString-1]= 0.5-((0.5/guitarist.getMaxFingerStretch())*(fretFactor));
            }
	    cont++;
	    rowAverage+=ss[i][initialString-1];
          }else{
              ss[i][initialString-1]=0.0;
          }
          initialString++;
        }
    }


    boolean calculoPronto = false;
     double somaParcial = -1.0;
     double maiores[][] = new double[0][0];
     while((!calculoPronto)&&(somaParcial!=0.0)){
       maiores = new double[basicChordShape.notePositions.size()][3];
       for(int i=0;i<maiores.length;i++){
         maiores[i][1] = -1;
       }

       somaParcial = 0.0;
       for (int i=0; i<ss.length;i++){
         //pega o maior da linha
         int contValorUnico = 0;
         for(int j =0; j<ss[i].length;j++){
           if(ss[i][j] > 0){

             if(ss[i][j]>maiores[i][0]){
               maiores[i][0] = ss[i][j];
               somaParcial+=ss[i][j];
               maiores[i][1] = j;
             }
             contValorUnico++;
            }
         }
         if(contValorUnico==1){
           maiores[i][2]=-1.0;
         }else{
           maiores[i][2]=0.0;
         }
       }
       // Verifica intersecao
         for (int i=0; i<ss.length -1;i++){
           if((maiores[i][1]==maiores[i+1][1])&&
               ((maiores[i][1]!= -1.0)&&(maiores[i+1][1]!=-1.0))) {

               if(((maiores[i][2]!=-1)&&(maiores[i+1][2]!=-1))){

                 if(maiores[i][0]>maiores[i+1][0]){
                   ss[i][(int)maiores[i+1][1]]=0.0;
                 }else{
                   ss[i][(int)maiores[i][1]]=0.0;
                   //similaridade = 0.0;
                 }
                 similarityFactor = 0.0;
                 break;
             }else{
               if((maiores[i][2]!=-1)&&(maiores[i+1][2]==-1)){
                 ss[i][(int)maiores[i][1]]=0.0;
               }else{
                 ss[i+1][(int)maiores[i][1]]=0.0;
               }
               break;
             }
           }else{
             similarityFactor+=  maiores[i][0];
             calculoPronto = true;
           }
         }
     }
     similarityFactor += maiores[maiores.length-1][0];
     similarityFactor = similarityFactor/ss.length; //media
     return similarityFactor;
  }


  /**
  * Metodo recursivo que processa sucessivamente as representacoes. O processamento da representação é
  * divido nos 3 metodos de mesmo nome (processa). Este método processa o primeiro nível.
  * @param repChord Representacao a ser processada.
  * @param processamentos Processamentos que devem ser feitos sobre a representação.
  * @param Usado na identação do Log.
 * @throws NoteException 
  */
  private  void performOperation(GuitarChordShape chordShape,
    ChordShapeProperties operations, int t) throws NoteException{

    String processamento = operations.getFirstTrueElement();

    while (processamento !=null){
      GuitarChordShape repChordProcessado = performOperation((GuitarChordShape)chordShape.clone(),processamento);

      String resp;
      if(repChordProcessado!=null){
         resp = addChordShape(repChordProcessado);
      }else{
        repChordProcessado = chordShape;
        resp = "(no alteration)";
      }

      if (log!= null){
        log.writeLog(getIndentation(t)+ chordShape.toString() + " - " + processamento + "(" + resp + ")");
      }

      operations.put(processamento,new Boolean(false));
      performOperation((GuitarChordShape)repChordProcessado.clone(),(ChordShapeProperties)operations.clone(),(t+1));

      nOperations++;

      String processamentoTemp = processamento;
      processamento = operations.getNextTrueElement(processamento);
      operations.put(processamentoTemp,new Boolean(true));

    }

 }


 /**
 * Simplemente verifica duplicidade na lista antes de inserir ou recusar uma representaçao gerada.
 * @return Uma string dizendo se foi inserida ou descartada a representação. Usada no Log.
 */
 private  String addChordShape(GuitarChordShape chordShape){
  String retorno=null;
  if( chordShape !=null){
    if( computedChordShapes.containsKey(chordShape.toString())){
      retorno = ("Refused: Chord Shape Duplicated");
    }else{
      //suggestFingerings(repChord);
      computedChordShapes.put(chordShape.toString(),chordShape);
      retorno  = ("Added");
    }
  }
   return retorno;
 }

 /**
 * Calcula a identaçao correspondente para cada nível para facilitar a leitura do Log.
 * @return Uma string com a identaçao apropriada ao nível.
 */
private  String getIndentation(int level){
  String retorno = "    ";
  for (int i=0; i<level;i++){
    retorno+=retorno;
  }
  return retorno;
 }

 /**
* Metodo de apoio ao método processar do primeiro nível. Neste nível é verificado se o processamento
* do dobramento está configurado para acontecer. Se positivo, então a representaçao é encaminhada para o nivel 3.
* @param repChord Representacao a ser processada.
* @param nomeProcessamento Nome do processamento que será verificado na RepresentacoChordProperties.
* @return A representaçao processada ou null, caso não seja possível processar.
 * @throws NoteException 
  */
 private  GuitarChordShape performOperation(GuitarChordShape chordShape,String operation) throws NoteException{
    GuitarChordShape retorno=null;

   /* if (nomeProcessamento.equalsIgnoreCase("dobramentoFundamental")){
      ChordNote notaChord = repChord.getChord().getRootNote();
      retorno = processar(repChord,notaChord,true);
      return retorno;
    }*/

    if (operation.equalsIgnoreCase("duplicateFundamental")){
      Note notaChord = chordShape.getChord().getRootNote();
      retorno = performOperation(chordShape,notaChord,false,operation);
      return retorno;
    }

    if (operation.equalsIgnoreCase("triplicateFundamental")){
      Note notaChord = chordShape.getChord().getRootNote();
      retorno = performOperation(chordShape,notaChord,false,operation);
      return retorno;
    }

    if (operation.equalsIgnoreCase("doubleThird")){
   //  ChordNote notaChord = searchChordNote(repChord,"3");
      Note notaChord = chordShape.getChord().getNote(3);
      if (notaChord !=null){
        retorno = performOperation(chordShape,notaChord,true,operation);
      }
      return retorno;
    }

    if (operation.equalsIgnoreCase("duplicatePerfectFifth")){
      //ChordNote notaChord = searchChordNote(repChord,"5");
      Note notaChord = chordShape.getChord().getNote(5);
      if (notaChord !=null){
        retorno = performOperation(chordShape,notaChord,false,operation);
      }
      return retorno;
    }

    if (operation.equalsIgnoreCase("doublePerfectFifth")){
     // ChordNote notaChord = searchChordNote(repChord,"5");
      Note notaChord = chordShape.getChord().getNote(3);
      if (notaChord !=null){
        retorno = performOperation(chordShape,notaChord,true,operation);
      }
      return retorno;
    }

    if (operation.equalsIgnoreCase("supressFifth")){
      //setsupressFifth(repChord);
      return retorno;
    }


    if (operation.equalsIgnoreCase("suggestFingering")){
      //**@todo Depois trazer metodo pra cá!*/;
      return retorno;
    }

    return retorno;
  }



  /**
 * Metodo de apoio ao método processar do segundo nível. Neste nível é verificado se nas cordas que não
 * estão sendo usadas na representação é possível incluir um dobramento (processamento). Caso positivo adiciona
 * e descreve na representaçao o papel daquela posiçao adicionada, caso falso retorna null.
 * @param repChord Representacao a ser processada.
 * @param notaChord Note que deve ser procurada e adicionada.
 * @param considerOctave Define se será dobramento ou duplicação.
 * @param nomeProcessamento Nome do processamento para ser na descrição da representação;
 * @return A representaçao processada ou null, caso não seja possível processar.
 * @throws NoteException 
  */
 private  GuitarChordShape performOperation(GuitarChordShape chordShape, Note chordNote, boolean octavesDiscern, String operationName) throws NoteException{
   GuitarChordShape retorno = null;
   FretController controladorAberturaDedo = new FretController(chordShape,instrument,guitarist);

   boolean buscarAcima = true;
   // Nao lembro porque coloquei, mas retirei porque estava adicionando posicao acima do baixo. Ex, em LA 30,22,10 ele adionava o 65.
   /* if(notaChord.getIntervaloRepresentado().getDistanciaFundamental() == 0) {
     buscarAcima = true;
   }else{*/
     buscarAcima = ((Boolean)chordShapeProperties.get("calculateInversions")).booleanValue();
   //}
   int[] cordas = chordShape.getAvailableStrings(buscarAcima);

  //mexi aqui
  for(int i=0;i<cordas.length;i++){
  // for(int i=cordas.length -1; i>=0;i--){
     InstumentString tmpCorda = instrument.getString(cordas[i]);
     Note nota = (chordNote);


     //contProcessamentos++;

        GuitarNotePosition posNote = instrument.getNotePosition(tmpCorda, nota,
                                             controladorAberturaDedo.getLeftBoundary(),
                                            controladorAberturaDedo.getRightBoundary(),
                                            octavesDiscern);
        try{
          if (posNote !=null){
            chordShape.addInstrumentNotePosition(posNote, ((Boolean)chordShapeProperties.get("showBarre")).booleanValue());
            chordShape.setDescription("Added position " + posNote.toString()+" - " + operationName);
            return chordShape;
          }
        }catch(MusicPerformanceException e){
          if(log!=null){
	    log.writeLog(e.getMessage());
          }
         // break;
        }
   }


    return  retorno;
 }

 /**
  * Sugeri os dedos a serem usados pelo músisco na execução da representação do acorde.
  * @param repChord Representacao a ser processada.
  */
 public void showFingering(GuitarChordShape chordShape){
   int nRequiredFingers = 0;
   int fingerTag = 1;
   int initialFret =chordShape.initialFret;
   int fretTag = 1;

   chordShape.setDescription("Fingering:");
   GuitarNotePosition[] pos;

   if(chordShape.getBarsPostions().length>0){
     pos= chordShape.getInstrumentNotesPositions(chordShape.initialFret);
     for(int  i= 0; i< pos.length;i++){
       pos[i].setFinger(fingerTag);
       chordShape.setDescription(pos[i] + " finger: " + fingerTag );
     }
     initialFret++;
     fretTag++;
     nRequiredFingers++;
   }

   for(int  i= initialFret; i<= chordShape.finalFret;i++){
     pos= chordShape.getInstrumentNotesPositions(i);

      for(int  j=0; j< pos.length;j++){
	pos[j].setFinger(fingerTag);
	chordShape.setDescription(pos[j] + " finger: " + fretTag );
	fretTag++;
	nRequiredFingers++;
      }

      if((pos.length ==0)&&
      ((chordShape.getNumberRequiredFingers() -  nRequiredFingers) <=chordShape.finalFret -i)){
	fretTag++;
      }
   }
 }




 /**
 * Permite Ligar/Desligar a opção de monitoração dos processamentos.
 * @param ON_OFF Se verdadeiro, o log irá armazenar as informaçoes relativas aos processamento dos momentoa posteriores.
 * Se falso, irá desligar a função de Log.
*/
 public void setLogOn(boolean ON_OFF){
   if(ON_OFF){
     this.log = new Log();
   }else{
     this.log = null;
   }
 }

 /**
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
 public Log getLog(){
   return log;
 }

}

/**
 * Classe usada para ordenar uma lista de GuitarChordShape por:
 * 1- Casa/Traste (descrescente);
 * 2 - Abertura de dedos requisitada/dificuldade (descrescente);
 * * @author Leandro Lesqueves Costalonga
 * @version 1.0
 */
class SimilarityComparator implements Comparator<Object>{

  public int compare(Object o1, Object o2){
    GuitarChordShape rep1 = (GuitarChordShape) o1;
    GuitarChordShape rep2 = (GuitarChordShape) o2;
    int retorno = 0;
    int qtComparacao;

    qtComparacao = rep2.getInstrumentNotePositions().length < rep1.getInstrumentNotePositions().length? rep2.getInstrumentNotePositions().length: rep1.getInstrumentNotePositions().length;

    for (int i=0; i< qtComparacao;i++){
      GuitarNotePosition pos1 = (GuitarNotePosition)rep2.notePositions.get(i);
      GuitarNotePosition pos2 = (GuitarNotePosition)rep1.notePositions.get(i);

      if (pos1.getFret() > pos2.getFret()){
	retorno = -1;
	break;
      }else{
	if(pos1.getFret() < pos2.getFret()){
	  retorno = 1;
	  break;
	}
      }
    }
    if(retorno ==0){
      if(rep2.fingerStretchingRequired > rep1.fingerStretchingRequired){
	retorno = 1;
      }else{
	 if(rep2.fingerStretchingRequired < rep1.fingerStretchingRequired){
	   retorno = -1;
	 }else{
	   retorno = 0;
	 }
      }
    }
    return retorno;
  }

}

