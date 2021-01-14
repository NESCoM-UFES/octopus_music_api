package octopus.instrument.fretted;

import java.util.ArrayList;

import octopus.instrument.InstrumentNotePosition;


/*
  Esta classe representa um mecanismo que interage com o RepresentacaoChordInstrumentoCordaMaker para
  restringir a área de busca de uma nota em no Instrumento a capacidade de realização do executante.
  Sua função é fiscalizar e, principalmente, dizer ao RepresentacaoChordMaker, de um instrumento
  harmonico que tenha uma abertura máxima dos dedos, se o usuário será capaz de executar o
  acorde ou não.
 */

class FretController implements Cloneable {
  ArrayList<GuitarNotePosition> notePositions; // armazena as posicoes das notas para calculo da abertura
  FrettedInstrument instrument; // Considera-se somente os instrumentos de corda.
  Guitarist guitarist;

  /** Casa mais a esquerda passada ao controlador.*/
  int initialFret;
  /** Casa mais a direita passada ao controlador.*/
  int finalFret;


  // Os limites são calculados a cada nava nota que é acrescentada a representação.
  /** Limite esquerdo até onde é possível procurar nota considerando características do instrumento e do músico.*/
  int leftBoundary;
  /** Limite a direita até onde é possível procurar nota considerando características do instrumento e do músico.*/
  int rightBoundary;

  // Usado pelo método clone desta classe.
  private FretController(){};

 /** Construtor usado geralmente no ato da construção de uma nova representação.
  * @param posNote Posição da nota que serve de base inicial para os cálculos.
  * @instrumento Instrumento que define as restrições necessárias ao controle da abertura dos dedos.
  * Ex: Qauntidade de trastes. */
   public FretController(GuitarNotePosition posNote, FrettedInstrument instrument,Guitarist guitarist){
     startup(posNote,  instrument,guitarist);
   }


   /** Construtor usado geralmente quando são adionadas novas posições a uma representação já criada.
    * @param representacao Representação que irá inicializar o controlador.
    * @instrumento Instrumento que define as restrições necessárias ao controle da abertura dos dedos.
  * Ex: Qauntidade de trastes. */
   public FretController(GuitarChordShape chordShape, FrettedInstrument instrument,Guitarist guitarist){
     InstrumentNotePosition[] instrumentNotePositions=chordShape.getInstrumentNotePositions();
     startup((GuitarNotePosition)instrumentNotePositions[0],instrument,guitarist);

     for (int i=1; i < instrumentNotePositions.length ;i++ ) {
       add((GuitarNotePosition)instrumentNotePositions[i]);
     }
   }

  // Rotina de inicialização padrão usada em ambos os construtores;
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


/** Para cada representação é necessário um controladorAberturaDedo. Como o algorítmo de
 * busca das representações é recursivo e o java não cria uma instancia nova dos objetos
 * para cada iteração, é preciso "clonar" o objeto para que seja restaurado o ambiente no
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
    * Adiciona a nova posição na representação e atualiza os limites máximos e mínimos.
    * @param posNote GuitarNotePosition que deve ser adicionada.
    */
   public void add(GuitarNotePosition posNote){
    notePositions.add(posNote);
    updateBoundaries( posNote.getFret());
   }

 /** Retorna a casa mais a esquerda das posições adicionadas.
  *@return Casa(traste) mais a esquerda das posições adicionadas.*/
   public int getStartFret() {return initialFret;}

 /** Retorna a casa mais a direita das posições adicionadas.
  *@return Casa(traste) mais a direita das posições adicionadas.*/
   public int getFinalFret() {return finalFret;}

 /** Retorna o limite esquerdo que o músico consegue alcançar.
  *@return Limite esquerdo onde o músico consegue alcançar.*/
   public int getLeftBoundary() {return leftBoundary;}


 /** Retorna o limite a direita que o músico consegue alcançar.
  *@return Limite direita onde o músico consegue alcançar.*/
   public int getRightBoundary() {return rightBoundary;}


  // Atualiza os limites possíveis com a entrada da nova posição.
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



   /** Retorna verdadeiro se for possível a execução da posição passada na
    * representação presente no controlador.
    * @param posNote Posição que se deseja saber se é possivel a execução na representaçao vigente.
    * @return True se for possível e False caso contrário.*/
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
