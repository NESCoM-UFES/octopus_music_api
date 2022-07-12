package octopus;

/*
 * Esta classe armazena a cole��o dos poss�veis intervals que podem ser
 * encontrados em uma cifra. Quaisquer duas notas definem um intervalo, portanto
 * esta classe deve conter todos os intervals que ser�o usados para forma��o
 * do acorde.
 */

/**
 * A Factory class to instantiate and manipulate Intervals.
 * 
 * @see Interval
 * @author Leandro Lesqueves Costalonga
 * @version 1.2
 */
public class Intervals {

//Defini��o dos s�mbolos dos intervals
/*
 * Representa o intervalo fundamental instanciando-o com as propriedades:
 * simboloIdentificador: "1";
 * intervaloNumerico: 1;
 * nomeIntervalo: "Fundamental";
 * distanciaFundamental: 0
 */
  protected static final Interval fundamental = new Interval("1",1, "Fundamental", 0);
/*
 * Representa o intervalo segunda menor instanciando-o com as propriedades:
 * simboloIdentificador: "b2";
 * intervaloNumerico: 2;
 * nomeIntervalo: "Segunda Menor";
 * distanciaFundamental: 1
 */


  protected static final Interval minorSecond = new Interval("b2",2, "Minor Second", 1, Interval.MINOR);
/*
 * Representa o intervalo segunda maior instanciando-o com as propriedades:
 * simboloIdentificador: "2";
 * intervaloNumerico: 2;
 * nomeIntervalo: "Segunda Maior";
 * distanciaFundamental: 2
 */
  protected static final Interval majorSecond  = new Interval("2",2, "Major Second", 2, Interval.MAJOR);
/*
 * Representa o intervalo segunda aumentada instanciando-o com as propriedades:
 * simboloIdentificador: "#2";
 * intervaloNumerico: 2;
 * nomeIntervalo: "Segunda Aumentada";
 * distanciaFundamental: 3
 */
  protected static final Interval augSecond = new Interval("#2",2, "Augmented Second", 3, Interval.AUGMENTED);
/*
 * Representa o intervalo ter�a menor instanciando-o com as propriedades:
 * simboloIdentificador: "b3";
 * intervaloNumerico: 3;
 * nomeIntervalo: "Terca Menor";
 * distanciaFundamental: 3
 */
  protected static final Interval minorThird = new Interval("b3",3, "Minor Third", 3, Interval.MINOR);
/*
 * Representa o intervalo ter�a maior instanciando-o com as propriedades:
 * simboloIdentificador: "3";
 * intervaloNumerico: 3;
 * nomeIntervalo: "Terca Maior";
 * distanciaFundamental: 4
 */
  protected static final Interval majorThird = new Interval("3",3, "Major Third", 4, Interval.MAJOR);
/*
 * Representa o intervalo quarta diminuta instanciando-o com as propriedades:
 * simboloIdentificador: "b4";
 * intervaloNumerico: 4;
 * nomeIntervalo: "Quarta Diminuta";
 * distanciaFundamental: 4
 */
  protected static final Interval dimFourth = new Interval("b4",4, "Diminished Fourth", 4, Interval.DIMINISHED);
/*
 * Representa o intervalo quarta justa instanciando-o com as propriedades:
 * simboloIdentificador: "4";
 * intervaloNumerico: 4;
 * nomeIntervalo: "Quarta Justa";
 * distanciaFundamental: 5
 */
  protected static final Interval perfectFourth = new Interval("4",4, "Perfect Fourth", 5, Interval.PERFECT);
/*
 * Representa o intervalo quarta aumentada instanciando-o com as propriedades:
 * simboloIdentificador: "#4";
 * intervaloNumerico: 4;
 * nomeIntervalo: "Quarta Aumentada";
 * distanciaFundamental: 6
 */
  protected static final Interval augFourth = new Interval("#4",4, "Augmented Fourth", 6, Interval.AUGMENTED);

/*
 * Representa o intervalo quinta diminuta instanciando-o com as propriedades:
 * simboloIdentificador: "b5";
 * intervaloNumerico: 5;
 * nomeIntervalo: "Quinta Diminuta";
 * distanciaFundamental: 6
 */
  protected static final Interval dimFifth = new Interval("b5",5, "Diminished Fifth", 6, Interval.DIMINISHED);
/*
 * Representa o intervalo quinta justa instanciando-o com as propriedades:
 * simboloIdentificador: "5";
 * intervaloNumerico: 5;
 * nomeIntervalo: "Quinta Justa";
 * distanciaFundamental: 7
 */
  protected static final Interval perfectFifth = new Interval("5",5, "Perfect Fifth", 7, Interval.PERFECT);
/*
 * Representa o intervalo quinta aumentada instanciando-o com as propriedades:
 * simboloIdentificador: "#5";
 * intervaloNumerico: 5;
 * nomeIntervalo: "Quinta Aumentada";
 * distanciaFundamental: 8
 */
  protected static final Interval augFifth = new Interval("#5",5, "Augmented Fifth",8, Interval.AUGMENTED);
/*
 * Representa o intervalo sexta menor instanciando-o com as propriedades:
 * simboloIdentificador: "b6";
 * intervaloNumerico: 6;
 * nomeIntervalo: "Sexta Menor";
 * distanciaFundamental: 8
 */
  protected static final Interval minorSixth = new Interval("b6",6, "Minor Sixth", 8, Interval.MINOR);
/*
 * Representa o intervalo sexta maior instanciando-o com as propriedades:
 * simboloIdentificador: "6";
 * intervaloNumerico: 6;
 * nomeIntervalo: "Sexta Maior";
 * distanciaFundamental: 9
 */
  protected static final Interval majorSixth = new Interval("6",6, "Major Sixth", 9, Interval.MAJOR);
/*
 * Representa o intervalo sexta aumentada instanciando-o com as propriedades:
 * simboloIdentificador: "#6";
 * intervaloNumerico: 6;
 * nomeIntervalo: "Sexta Aumentada";
 * distanciaFundamental: 10
 */
  protected static final Interval augSixth = new Interval("#6",6, "Augmented Sixth", 10, Interval.AUGMENTED);
/*
 * Representa o intervalo s�tima diminuta instanciando-o com as propriedades:
 * simboloIdentificador: "bb7";
 * intervaloNumerico: 7;
 * nomeIntervalo: "S�tima Diminuta";
 * distanciaFundamental: 9
 */
  protected static final Interval dimSeventh = new Interval("bb7",7, "Dimished Seventh", 9, Interval.DIMINISHED);
/*
 * Representa o intervalo s�tima menor instanciando-o com as propriedades:
 * simboloIdentificador: "b7";
 * intervaloNumerico: 7;
 * nomeIntervalo: "S�tima Menor";
 * distanciaFundamental: 10
 */
  protected static final Interval minorSeventh = new Interval("b7",7, "Minor Seventh", 10, Interval.MINOR);
/*
 * Representa o intervalo s�tima maior instanciando-o com as propriedades:
 * simboloIdentificador: "7";
 * intervaloNumerico: 7;
 * nomeIntervalo: "S�tima Maior";
 * distanciaFundamental: 11
 */
  protected static final Interval majorSeventh = new Interval("7",7, "Major Seventh", 11, Interval.MAJOR);
/*
 * Representa o intervalo nona menor instanciando-o com as propriedades:
 * simboloIdentificador: "b9";
 * intervaloNumerico: 9;
 * nomeIntervalo: "Nona Menor";
 * distanciaFundamental: 13
 */
  protected static final Interval minorNinth = new Interval("b9",9, "Minor Ninth", 13, Interval.MINOR);
/*
 * Representa o intervalo nona maior instanciando-o com as propriedades:
 * simboloIdentificador: "9";
 * intervaloNumerico: 9;
 * nomeIntervalo: "Nona Maior";
 * distanciaFundamental: 14
 */
  protected static final Interval majorNinth = new Interval("9",9, "Major Ninth", 14, Interval.MAJOR);
/*
 * Representa o intervalo nona aumentada instanciando-o com as propriedades:
 * simboloIdentificador: "#9";
 * intervaloNumerico: 9;
 * nomeIntervalo: "Nona Aumentada";
 * distanciaFundamental: 15
 */
  protected static final Interval augNinth = new Interval("#9",9, "Augmented Ninth", 15, Interval.AUGMENTED);
/*
 * Representa o intervalo d�cima primeira diminuta instanciando-o com as
 * propriedades:
 * simboloIdentificador: "b11";
 * intervaloNumerico: 11;
 * nomeIntervalo: "D�cima Primeira Diminuta";
 * distanciaFundamental: 16
 */
  protected static final Interval dim11th = new Interval("b11",11, "Diminished 11th", 16, Interval.DIMINISHED);
/*
 * Representa o intervalo d�cima primeira justa instanciando-o com as
 * propriedades:
 * simboloIdentificador: "11";
 * intervaloNumerico: 11;
 * nomeIntervalo: "D�cima Primeira Justa";
 * distanciaFundamental: 17
 */
  protected static final Interval perfect11th = new Interval("11",11, "Perfect 11th", 17, Interval.PERFECT);
/*
 * Representa o intervalo d�cima primeira aumentada instanciando-o com as
 * propriedades:
 * simboloIdentificador: "#11";
 * intervaloNumerico: 11;
 * nomeIntervalo: "D�cima Primeira Aumentada";
 * distanciaFundamental: 18
 */
  protected static final Interval aug11th = new Interval("#11",11, "Augmented 11th", 18, Interval.AUGMENTED);
/*
 * Representa o intervalo d�cima segunda diminuta instanciando-o com as
 * propriedades:
 * simboloIdentificador: "b12";
 * intervaloNumerico: 12;
 * nomeIntervalo: "D�cima Segunda Diminuta";
 * distanciaFundamental: 18
 */
  protected static final Interval dim12th = new Interval("b12",12, "Diminished 12th", 18, Interval.DIMINISHED);
/*
 * Representa o intervalo d�cima segunda justa instanciando-o com as
 * propriedades:
 * simboloIdentificador: "12";
 * intervaloNumerico: 12;
 * nomeIntervalo: "D�cima Segunda Justa";
 * distanciaFundamental: 19
 */
  protected static final Interval perfect12th = new Interval("12",12, "Perfect 12th", 19, Interval.PERFECT);
/*
 * Representa o intervalo d�cima segunda aumentada instanciando-o com as
 * propriedades:
 * simboloIdentificador: "#12";
 * intervaloNumerico: 12;
 * nomeIntervalo: "D�cima Segunda Aumentada";
 * distanciaFundamental: 20
 */
  protected static final Interval aug12th = new Interval("#12",12, "Augmented 12th", 20, Interval.AUGMENTED);
/*
 * Representa o intervalo d�cima terceira menor instanciando-o com as
 * propriedades:
 * simboloIdentificador: "b13";
 * intervaloNumerico: 13;
 * nomeIntervalo: "D�cima Terceira Menor";
 * distanciaFundamental: 20
 */
  protected static final Interval minor13th = new Interval("b13",13, "Minor 13th", 20, Interval.MINOR);
/*
 * Representa o intervalo d�cima terceira maior instanciando-o com as
 * propriedades:
 * simboloIdentificador: "13";
 * intervaloNumerico: 13;
 * nomeIntervalo: "D�cima Terceira Maior";
 * distanciaFundamental: 21
 */
  protected static final Interval major13th = new Interval("13",13, "Major 13th", 21, Interval.MAJOR);
/*
 * Representa o intervalo d�cima terceira aumentada instanciando-o com as
 * propriedades:
 * simboloIdentificador: "#13";
 * intervaloNumerico: 13;
 * nomeIntervalo: "D�cima Terceira Aumentada";
 * distanciaFundamental: 22
 */
  protected static final Interval aug13th = new Interval("#13",13, "Augmented 13th", 22, Interval.AUGMENTED);

/*
   * Representa a rela��o de intervals que podem ser encontrados entre duas
   * notas.
   */
  public static Interval intervals[] = { fundamental, minorSecond, majorSecond, augSecond,
			        minorThird, majorThird, dimFourth, perfectFourth, augFourth,
                                dimFifth,	perfectFifth, augFifth, minorSixth, majorSixth,
                                augSixth, dimSeventh,	minorSeventh, majorSeventh, minorNinth,
                                majorNinth, augNinth, dim11th, perfect11th,
                                aug11th, dim12th, perfect12th,
                                aug12th, minor13th, major13th,
                                aug13th };

/*
 * Esta classe Collection n�o possui construtor, os objetos foram instanciados
 * no momento da sua declara��o.
 */
  public Intervals() {}

/*
   * Obt�m o intervalo dado o s�mbolo que o representa.
   * @param simbIntervalo S�mbolo que representa o intervalo. Exemplo: Sexta
   * Aumentada =  "#6".
   * @return Interval correspondente ao s�mbolo do par�metro.
   */
  public static Interval getInterval(String intervalSymbol) {
    Interval retorno = null;
    for (int i=0; i<intervals.length ;i++ ) {
      if (intervals[i].getSymbol().equals(intervalSymbol)) {
        retorno = intervals[i];
	break;
      }
    }
    return retorno;
  }

 //Retorna o intervalo dando preferencia aos intervalor naturais. Modificado...ver coment�rio abaixo.
  public static Interval getInterval(int semitons) {
    Interval retorno = null;
    //Comentadado para permitir retornar intervalos acima da oitava.
    /*while (semitons>11){
      semitons-=12;
    }*/

    for (int i=0; i<intervals.length ;i++ ) {
      if (intervals[i].getDistanceFromRoot() == semitons) {
       /* if(intervals[i].getNumericInterval() == semitons){ //Mod to support creating o chords with notes. @todo Testing with other calls.
          retorno = intervals[i];
          break;
        }else{*/
           retorno = intervals[i];
           break;
        }

      //}
    }
    return retorno;
  }

  public static Interval getInterval(Note note1, Note note2) throws NoteException {
    int d = Notes.getDistance(note1, note2,false);
    return getInterval(d);
  }

/*
   * Obt�m a rela��o de intervals do acorde.
   * @return Rela��o de intervals do acorde.
   */
  public static Interval[] getIntervals(){ return intervals; }

/*
   * Obt�m o intervalo fundamental.
   * @return Interval fundamental.
   */
  public static Interval getRoot() {return fundamental;}

/*
   * Obt�m o intervalo segunda menor.
   * @return Interval segunda menor.
   */
  public static Interval getMinorSecond() {return minorSecond;}

/*
   * Obt�m o intervalo segunda maior.
   * @return Interval segunda maior.
   */
  public static Interval getMajorSecond() {return majorSecond;}

/*
   * Obt�m o intervalo segunda aumentada.
   * @return Interval segunda aumentada.
   */
  public static Interval getAugSecond() {return augSecond;}

/*
   * Obt�m o intervalo ter�a menor.
   * @return Interval ter�a menor.
   */
  public static Interval getMinorThird() {return minorThird;}

/*
   * Obt�m o intervalo ter�a maior.
   * @return Interval ter�a maior.
   */
  public static Interval getMajorThird() {return majorThird;}

/*
   * Obt�m o intervalo quarta diminuta.
   * @return Interval quarta diminuta.
   */
  public static Interval getDimFourth() {return dimFourth;}

/*
   * Obt�m o intervalo quarta justa.
   * @return Interval quarta justa
   */
  public static Interval getPerfectFourth() {return perfectFourth;}

/*
   * Obt�m o intervalo quarta aumentada.
   * @return Interval quarta aumentada.
   */
  public static Interval getAugFourth() {return augFourth;}

/*
   * Obt�m o intervalo quinta diminuta.
   * @return Interval quinta diminuta.
   */
  public static Interval getDimFifth() {return dimFifth;}

/*
 * Obt�m o interavalo quinta justa.
 * @return Interavalo quinta justa.
 */
  public static Interval getPerfectFifth() {return perfectFifth;}

/*
   * Obt�m o intervalo quinta aumentada.
   * @return Interval quinta aumentada.
   */
  public static Interval getAugFifth() {return augFifth;}

/*
   * Obt�m o intervalo sexta menor.
   * @return Interval sexta menor.
   */
  public static Interval getMinorSixth() {return minorSixth;}

/*
   * Obt�m o intervalo sexta maior.
   * @return Interval sexta maior.
   */
  public static Interval getMajorSixth() {return majorSixth;}

/*
   * Obt�m o intervalo sexta aumentada.
   * @return Interval sexta aumentada.
   */
  public static Interval getAugSixth() {return augSixth;}

/*
   * Obt�m o intervalo s�tima diminuta.
   * @return Interval s�tima diminuta.
   */
  public static Interval getDimSeventh() {return dimSeventh;}

/*
   * Obt�m o intervalo s�tima menor.
   * @return Interval s�tima menor.
   */
  public static Interval getMinorSeventh() {return minorSeventh;}

/*
   * Obt�m o intervalo s�tima maior.
   * @return Interval s�tima maior.
   */
  public static Interval getMajorSeventh() {return majorSeventh;}

/*
   * Obt�m o intervalo nona menor.
   * @return Interval nona menor.
   */
  public static Interval getMinorNinth() {return minorNinth;}

/*
   * Obt�m o intervalo nona maior.
   * @return Interval nona maior.
   */
  public static Interval getMajorNinth() {return majorNinth;}

/*
   * Obt�m o intervalo nona aumentada.
   * @return Interval nona aumentada.
   */
  public static Interval getAugNinth() {return augNinth;}

/*
   * Obt�m o intervalo d�cima primeira diminuta.
   * @return Interval d�cima primeira diminuta.
   */
  public static Interval getDim11th() {return dim11th;}

/*
   * Obt�m o intervalo d�cima primeira justa.
   * @return Interval d�cima primeira justa.
   */
  public static Interval getPerfect11th() {return perfect11th;}

/*
   * Obt�m o intervalo d�cima primeira aumentada.
   * @return Interval d�cima primeira aumentada.
   */
  public static Interval getAug11th() {return aug11th;}

/*
   * Obt�m o intervalo d�cima segunda diminuta.
   * @return Interval d�cima segunda diminuta.
   */
  public static Interval getDim12th() {return dim12th;}

/*
   * Obt�m o intervalo d�cima segunda justa.
   * @return Interval d�cima segunda justa.
   */
  public static Interval getPerfect12th() {return perfect12th;}

/*
   * Obt�m o intervalo d�cima segunda aumentada.
   * @return Interval d�cima segunda aumentada.
   */
  public static Interval getAug12th() {return aug12th;}

/*
   * Obt�m o intervalo d�cima terceira menor.
   * @return Interval d�cima terceira menor.
   */
  public static Interval getMinor13th() {return minor13th;}

/*
   * Obt�m o intervalo d�cima terceira maior.
   * @return Interval d�cima terceira maior.
   */
  public static Interval getMajor13th() {return major13th;}

/*
 * Obt�m o intevalo d�cima terceira aumentada.
 * @return Intevalo d�cima terceira aumentada.
 */
  public static Interval getAug13th() {return aug13th;}

}
