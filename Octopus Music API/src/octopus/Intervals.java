package octopus;

/*
 * Esta classe armazena a coleção dos possíveis intervals que podem ser
 * encontrados em uma cifra. Quaisquer duas notas definem um intervalo, portanto
 * esta classe deve conter todos os intervals que serão usados para formação
 * do acorde.
 */

/**
 * A Factory class to instantiate and manipulate Intervals.
 * 
 * @see Interval
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
public class Intervals {

//Definição dos símbolos dos intervals
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
 * Representa o intervalo terça menor instanciando-o com as propriedades:
 * simboloIdentificador: "b3";
 * intervaloNumerico: 3;
 * nomeIntervalo: "Terca Menor";
 * distanciaFundamental: 3
 */
  protected static final Interval minorThird = new Interval("b3",3, "Minor Third", 3, Interval.MINOR);
/*
 * Representa o intervalo terça maior instanciando-o com as propriedades:
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
 * Representa o intervalo sétima diminuta instanciando-o com as propriedades:
 * simboloIdentificador: "bb7";
 * intervaloNumerico: 7;
 * nomeIntervalo: "Sétima Diminuta";
 * distanciaFundamental: 9
 */
  protected static final Interval dimSeventh = new Interval("bb7",7, "Dimished Seventh", 9, Interval.DIMINISHED);
/*
 * Representa o intervalo sétima menor instanciando-o com as propriedades:
 * simboloIdentificador: "b7";
 * intervaloNumerico: 7;
 * nomeIntervalo: "Sétima Menor";
 * distanciaFundamental: 10
 */
  protected static final Interval minorSeventh = new Interval("b7",7, "Minor Seventh", 10, Interval.MINOR);
/*
 * Representa o intervalo sétima maior instanciando-o com as propriedades:
 * simboloIdentificador: "7";
 * intervaloNumerico: 7;
 * nomeIntervalo: "Sétima Maior";
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
 * Representa o intervalo décima primeira diminuta instanciando-o com as
 * propriedades:
 * simboloIdentificador: "b11";
 * intervaloNumerico: 11;
 * nomeIntervalo: "Décima Primeira Diminuta";
 * distanciaFundamental: 16
 */
  protected static final Interval dim11th = new Interval("b11",11, "Diminished 11th", 16, Interval.DIMINISHED);
/*
 * Representa o intervalo décima primeira justa instanciando-o com as
 * propriedades:
 * simboloIdentificador: "11";
 * intervaloNumerico: 11;
 * nomeIntervalo: "Décima Primeira Justa";
 * distanciaFundamental: 17
 */
  protected static final Interval perfect11th = new Interval("11",11, "Perfect 11th", 17, Interval.PERFECT);
/*
 * Representa o intervalo décima primeira aumentada instanciando-o com as
 * propriedades:
 * simboloIdentificador: "#11";
 * intervaloNumerico: 11;
 * nomeIntervalo: "Décima Primeira Aumentada";
 * distanciaFundamental: 18
 */
  protected static final Interval aug11th = new Interval("#11",11, "Augmented 11th", 18, Interval.AUGMENTED);
/*
 * Representa o intervalo décima segunda diminuta instanciando-o com as
 * propriedades:
 * simboloIdentificador: "b12";
 * intervaloNumerico: 12;
 * nomeIntervalo: "Décima Segunda Diminuta";
 * distanciaFundamental: 18
 */
  protected static final Interval dim12th = new Interval("b12",12, "Diminished 12th", 18, Interval.DIMINISHED);
/*
 * Representa o intervalo décima segunda justa instanciando-o com as
 * propriedades:
 * simboloIdentificador: "12";
 * intervaloNumerico: 12;
 * nomeIntervalo: "Décima Segunda Justa";
 * distanciaFundamental: 19
 */
  protected static final Interval perfect12th = new Interval("12",12, "Perfect 12th", 19, Interval.PERFECT);
/*
 * Representa o intervalo décima segunda aumentada instanciando-o com as
 * propriedades:
 * simboloIdentificador: "#12";
 * intervaloNumerico: 12;
 * nomeIntervalo: "Décima Segunda Aumentada";
 * distanciaFundamental: 20
 */
  protected static final Interval aug12th = new Interval("#12",12, "Augmented 12th", 20, Interval.AUGMENTED);
/*
 * Representa o intervalo décima terceira menor instanciando-o com as
 * propriedades:
 * simboloIdentificador: "b13";
 * intervaloNumerico: 13;
 * nomeIntervalo: "Décima Terceira Menor";
 * distanciaFundamental: 20
 */
  protected static final Interval minor13th = new Interval("b13",13, "Minor 13th", 20, Interval.MINOR);
/*
 * Representa o intervalo décima terceira maior instanciando-o com as
 * propriedades:
 * simboloIdentificador: "13";
 * intervaloNumerico: 13;
 * nomeIntervalo: "Décima Terceira Maior";
 * distanciaFundamental: 21
 */
  protected static final Interval major13th = new Interval("13",13, "Major 13th", 21, Interval.MAJOR);
/*
 * Representa o intervalo décima terceira aumentada instanciando-o com as
 * propriedades:
 * simboloIdentificador: "#13";
 * intervaloNumerico: 13;
 * nomeIntervalo: "Décima Terceira Aumentada";
 * distanciaFundamental: 22
 */
  protected static final Interval aug13th = new Interval("#13",13, "Augmented 13th", 22, Interval.AUGMENTED);

/*
   * Representa a relação de intervals que podem ser encontrados entre duas
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
 * Esta classe Collection não possui construtor, os objetos foram instanciados
 * no momento da sua declaração.
 */
  public Intervals() {}

/*
   * Obtém o intervalo dado o símbolo que o representa.
   * @param simbIntervalo Símbolo que representa o intervalo. Exemplo: Sexta
   * Aumentada =  "#6".
   * @return Interval correspondente ao símbolo do parâmetro.
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

 //Retorna o intervalo dando preferencia aos intervalor naturais. Modificado...ver comentário abaixo.
  public static Interval getInterval(int semitons) {
    Interval retorno = null;
    while (semitons>11){
      semitons-=12;
    }

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
   * Obtém a relação de intervals do acorde.
   * @return Relação de intervals do acorde.
   */
  public static Interval[] getIntervals(){ return intervals; }

/*
   * Obtém o intervalo fundamental.
   * @return Interval fundamental.
   */
  public static Interval getRoot() {return fundamental;}

/*
   * Obtém o intervalo segunda menor.
   * @return Interval segunda menor.
   */
  public static Interval getMinorSecond() {return minorSecond;}

/*
   * Obtém o intervalo segunda maior.
   * @return Interval segunda maior.
   */
  public static Interval getMajorSecond() {return majorSecond;}

/*
   * Obtém o intervalo segunda aumentada.
   * @return Interval segunda aumentada.
   */
  public static Interval getAugSecond() {return augSecond;}

/*
   * Obtém o intervalo terça menor.
   * @return Interval terça menor.
   */
  public static Interval getMinorThird() {return minorThird;}

/*
   * Obtém o intervalo terça maior.
   * @return Interval terça maior.
   */
  public static Interval getMajorThird() {return majorThird;}

/*
   * Obtém o intervalo quarta diminuta.
   * @return Interval quarta diminuta.
   */
  public static Interval getDimFourth() {return dimFourth;}

/*
   * Obtém o intervalo quarta justa.
   * @return Interval quarta justa
   */
  public static Interval getPerfectFourth() {return perfectFourth;}

/*
   * Obtém o intervalo quarta aumentada.
   * @return Interval quarta aumentada.
   */
  public static Interval getAugFourth() {return augFourth;}

/*
   * Obtém o intervalo quinta diminuta.
   * @return Interval quinta diminuta.
   */
  public static Interval getDimFifth() {return dimFifth;}

/*
 * Obtém o interavalo quinta justa.
 * @return Interavalo quinta justa.
 */
  public static Interval getPerfectFifth() {return perfectFifth;}

/*
   * Obtém o intervalo quinta aumentada.
   * @return Interval quinta aumentada.
   */
  public static Interval getAugFifth() {return augFifth;}

/*
   * Obtém o intervalo sexta menor.
   * @return Interval sexta menor.
   */
  public static Interval getMinorSixth() {return minorSixth;}

/*
   * Obtém o intervalo sexta maior.
   * @return Interval sexta maior.
   */
  public static Interval getMajorSixth() {return majorSixth;}

/*
   * Obtém o intervalo sexta aumentada.
   * @return Interval sexta aumentada.
   */
  public static Interval getAugSixth() {return augSixth;}

/*
   * Obtém o intervalo sétima diminuta.
   * @return Interval sétima diminuta.
   */
  public static Interval getDimSeventh() {return dimSeventh;}

/*
   * Obtém o intervalo sétima menor.
   * @return Interval sétima menor.
   */
  public static Interval getMinorSeventh() {return minorSeventh;}

/*
   * Obtém o intervalo sétima maior.
   * @return Interval sétima maior.
   */
  public static Interval getMajorSeventh() {return majorSeventh;}

/*
   * Obtém o intervalo nona menor.
   * @return Interval nona menor.
   */
  public static Interval getMinorNinth() {return minorNinth;}

/*
   * Obtém o intervalo nona maior.
   * @return Interval nona maior.
   */
  public static Interval getMajorNinth() {return majorNinth;}

/*
   * Obtém o intervalo nona aumentada.
   * @return Interval nona aumentada.
   */
  public static Interval getAugNinth() {return augNinth;}

/*
   * Obtém o intervalo décima primeira diminuta.
   * @return Interval décima primeira diminuta.
   */
  public static Interval getDim11th() {return dim11th;}

/*
   * Obtém o intervalo décima primeira justa.
   * @return Interval décima primeira justa.
   */
  public static Interval getPerfect11th() {return perfect11th;}

/*
   * Obtém o intervalo décima primeira aumentada.
   * @return Interval décima primeira aumentada.
   */
  public static Interval getAug11th() {return aug11th;}

/*
   * Obtém o intervalo décima segunda diminuta.
   * @return Interval décima segunda diminuta.
   */
  public static Interval getDim12th() {return dim12th;}

/*
   * Obtém o intervalo décima segunda justa.
   * @return Interval décima segunda justa.
   */
  public static Interval getPerfect12th() {return perfect12th;}

/*
   * Obtém o intervalo décima segunda aumentada.
   * @return Interval décima segunda aumentada.
   */
  public static Interval getAug12th() {return aug12th;}

/*
   * Obtém o intervalo décima terceira menor.
   * @return Interval décima terceira menor.
   */
  public static Interval getMinor13th() {return minor13th;}

/*
   * Obtém o intervalo décima terceira maior.
   * @return Interval décima terceira maior.
   */
  public static Interval getMajor13th() {return major13th;}

/*
 * Obtém o intevalo décima terceira aumentada.
 * @return Intevalo décima terceira aumentada.
 */
  public static Interval getAug13th() {return aug13th;}

}
