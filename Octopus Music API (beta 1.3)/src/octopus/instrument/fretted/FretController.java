package octopus.instrument.fretted;

import java.util.ArrayList;

import octopus.instrument.InstrumentNotePosition;


/*
  Esta classe representa um mecanismo que interage com o RepresentacaoChordInstrumentoCordaMaker para
  restringir a �rea de busca de uma nota em no Instrumento a capacidade de realiza��o do executante.
  Sua fun��o � fiscalizar e, principalmente, dizer ao RepresentacaoChordMaker, de um instrumento
  harmonico que tenha uma abertura m�xima dos dedos, se o usu�rio ser� capaz de executar o
  acorde ou n�o.
 */

class FretController implements Cloneable {
  ArrayList<GuitarNotePosition> notePositions; // armazena as posicoes das notas para calculo da abertura
  FrettedInstrument instrument; // Considera-se somente os instrumentos de corda.
  Guitarist guitarist;

  /** Casa mais a esquerda passada ao controlador.*/
  int initialFret;
  /** Casa mais a direita passada ao controlador.*/
  int finalFret;


  // Os limites s�o calculados a cada nava nota que � acrescentada a representa��o.
  /** Limite esquerdo at� onde � poss�vel procurar nota considerando caracter�sticas do instrumento e do m�sico.*/
  int leftBoundary;
  /** Limite a direita at� onde � poss�vel procurar nota considerando caracter�sticas do instrumento e do m�sico.*/
  int rightBoundary;

  // Usado pelo m�todo clone desta classe.
  private FretController(){};

 /** Construtor usado geralmente no ato da constru��o de uma nova representa��o.
  * @param posNote Posi��o da nota que serve de base inicial para os c�lculos.
  * @instrumento Instrumento que define as restri��es necess�rias ao controle da abertura dos dedos.
  * Ex: Qauntidade de trastes. */
   public FretController(GuitarNotePosition posNote, FrettedInstrument instrument,Guitarist guitarist){
     startup(posNote,  instrument,guitarist);
   }


   /** Construtor usado geralmente quando s�o adionadas novas posi��es a uma representa��o j� criada.
    * @param representacao Representa��o que ir� inicializar o controlador.
    * @instrumento Instrumento que define as restri��es necess�rias ao controle da abertura dos dedos.
  * Ex: Qauntidade de trastes. */
   public FretController(GuitarChordShape chordShape, FrettedInstrument instrument,Guitarist guitarist){
     InstrumentNotePosition[] instrumentNotePositions=chordShape.getInstrumentNotePositions();
     startup((GuitarNotePosition)instrumentNotePositions[0],instrument,guitarist);

     for (int i=1; i < instrumentNotePositions.length ;i++ ) {
       add((GuitarNotePosition)instrumentNotePositions[i]);
     }
   }

  // Rotina de inicializa��o padr�o usada em ambos os construtores;
   private void startup(GuitarNotePosition posNote, FrettedInstrument instrument,Guitarist guitarist){
     notePositions = new ArrayList<GuitarNotePosition>();
     notePositions.add(posNote);
     this.instrument = instrument;
     this.guitarist = guitarist;

     int temp = posNote.getFret() - (guitarist.getMaxFingerStretch() -1);
     leftBoundary = (temp >= 0)? temp: 0;

     initialFret = posNote.getFret(); //
     if(posNote.getFret()==0){
       rightBoundary = instrument.getNumberOfFrets();
       }else{
         temp = posNote.getFret() + (guitarist.getMaxFingerStretch() -1);
         rightBoundary = temp <= instrument.getNumberOfFrets()? temp: instrument.getNumberOfFrets();
       }


     finalFret = posNote.getFret();
   }


/** Para cada representa��o � necess�rio um controladorAberturaDedo. Como o algor�tmo de
 * busca das representa��es � recursivo e o java n�o cria uma instancia nova dos objetos
 * para cada itera��o, � preciso "clonar" o objeto para que seja restaurado o ambiente no
 * momento da chamada recursiva.*/
   @Override
protected Object clone(){
    FretController retorno = new FretController();
    retorno.instrument = instrument;
    retorno.guitarist = guitarist;
    retorno.notePositions = notePositions;
    retorno.initialFret = initialFret;
    retorno.finalFret = finalFret;
    retorno.leftBoundary = leftBoundary;
    retorno.rightBoundary = rightBoundary;

    return retorno;
   }

   /**
    * Adiciona a nova posi��o na representa��o e atualiza os limites m�ximos e m�nimos.
    * @param posNote GuitarNotePosition que deve ser adicionada.
    */
   public void add(GuitarNotePosition posNote){
    notePositions.add(posNote);
    updateBoundaries( posNote.getFret());
   }

 /** Retorna a casa mais a esquerda das posi��es adicionadas.
  *@return Casa(traste) mais a esquerda das posi��es adicionadas.*/
   public int getStartFret() {return initialFret;}

 /** Retorna a casa mais a direita das posi��es adicionadas.
  *@return Casa(traste) mais a direita das posi��es adicionadas.*/
   public int getFinalFret() {return finalFret;}

 /** Retorna o limite esquerdo que o m�sico consegue alcan�ar.
  *@return Limite esquerdo onde o m�sico consegue alcan�ar.*/
   public int getLeftBoundary() {return leftBoundary;}


 /** Retorna o limite a direita que o m�sico consegue alcan�ar.
  *@return Limite direita onde o m�sico consegue alcan�ar.*/
   public int getRightBoundary() {return rightBoundary;}


  // Atualiza os limites poss�veis com a entrada da nova posi��o.
   private void updateBoundaries(int fret){
     int aberturaDedo = guitarist.getMaxFingerStretch();
        if((fret < initialFret)&& (fret!=0)){
            if ( fret >= (finalFret - (aberturaDedo -1))){
                initialFret = fret;
                //Verificar Limite > qtCasas
                rightBoundary = initialFret + (aberturaDedo -1);
            }
        }

        if ((fret > finalFret) &&
           (fret <= instrument.getNumberOfFrets())) {
              if (fret <= (initialFret + (aberturaDedo - 1))){
                 finalFret = fret;
                 leftBoundary = (finalFret - (aberturaDedo -1))>=0?(finalFret - (aberturaDedo -1)):0;
                 //limiteDireita = numCasa;
              }
      }

      if(((finalFret == 0)&&(initialFret==0)) ||
        ((leftBoundary==0)&& (rightBoundary==instrument.getNumberOfFrets()))) {

        finalFret = fret;
        initialFret = fret;

        int temp = fret - (guitarist.getMaxFingerStretch() -1);
        leftBoundary = (temp >= 0)? temp: 0;

        temp = fret + (guitarist.getMaxFingerStretch() -1);
        rightBoundary = temp <= instrument.getNumberOfFrets()? temp: instrument.getNumberOfFrets();

      }
   }



   /** Retorna verdadeiro se for poss�vel a execu��o da posi��o passada na
    * representa��o presente no controlador.
    * @param posNote Posi��o que se deseja saber se � possivel a execu��o na representa�ao vigente.
    * @return True se for poss�vel e False caso contr�rio.*/
   public boolean isPositionAchievable(GuitarNotePosition posNote){
    boolean retorno;
     if (((posNote.getFret() >= leftBoundary) || (posNote.getFret() == 0))
        && (posNote.getFret()<= rightBoundary)){
          retorno = true;
     }else{
        retorno = false;
     }
     return retorno;
   }
}
