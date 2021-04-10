package octopus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class specifies the alphabet used to describe the Chords.
 * The default alphabet used is based on the Brazilian Bossa Nova, but it can be customised 
 * or completely replace. For example, some musical styles use the "M" to represent a major
 * interval (F7M), other prefer to write the word "major" in the end (F7 Major).
 * <p> The alphabet used by the defaultChordNotation is given by the table bellow:</p>
 * <center><img src="doc-files/DefaultChordNotation.jpg"></center>
 * <p><a href="doc-files/DefaultChordNotation.txt"> File example that can be used to construct or modify a ChordNotation. </a></p>
 *
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */


 public class ChordNotation {

// Declaração das notas
/*
 * Representa a nota LA da notação.
 */
  protected NoteSymbol noteA;
/*
 * Representa a nota SI da notação.
 */
  protected NoteSymbol noteB;
/*
 * Representa a nota DO da notação.
 */
  protected NoteSymbol noteC;
/*
 * Representa a nota RE da notação.
 */
  protected NoteSymbol noteD;
/*
 * Representa a nota MI da notação.
 */
  protected NoteSymbol noteE;
/*
 * Representa a nota FA da notação.
 */
  protected NoteSymbol noteF;
/*
 * Representa a nota SOL da notação.
 */
  protected NoteSymbol noteG;
/*
 * Relação da notação utilizada para as notas.
 */
  protected String strNotes[];
/*
 * Relação dos símbolos que configuram as notas da notação.
 */
  protected NoteSymbol notes [];
// Declaração dos símbolos das alterações das notas
/*
 * Representa o símbolo para a alteração Sustenido em uma cifra.
 */
  protected NotationalSymbol sharp;
/*
 * Representa o símbolo para a alteração Bemol em uma cifra.
 */
  protected NotationalSymbol flat;
/*
   * Relação da notação utilizada para as alterações (sustenido e flat).
   */
  protected String strAlterations[];
/*
 * Relação de símbolos que configuram as alterações da nota na notação.
 */
  protected NotationalSymbol accidentals[];
// Declação dos símbolos dos intervalos
/*
 * Representa o intervalo da Fundamental.
 */
  protected IntervalSymbol fundamentalInterval;
/*
 * Representa o intervalo da Segunda Menor.
 */
  protected IntervalSymbol minorSecondInterval;
/*
 * Representa o intervalo da Segunda Maior.
 */
  protected IntervalSymbol majorSecondInterval;
/*
 * Representa o intervalo da Segunda Aumentada.
 */
  protected IntervalSymbol augmentedSecondInterval;
/*
 * Representa o intervalo da Terça Menor.
 */
  protected IntervalSymbol minorThirdInterval;
/*
 * Representa o intervalo da Terça Maior.
 */
  protected IntervalSymbol majorThirdInterval;
/*
 * Representa o intervalo da Quarta Diminuta.
 */
  protected IntervalSymbol diminishedFourthInterval;
/*
 * Representa o intervalo da Quarta Justa.
 */
  protected IntervalSymbol perfectFourthInterval;
/*
 * Representa o intervalo da Quarta Aumentada.
 */
  protected IntervalSymbol augmentedFourthInterval;
/*
 * Representa o intervalo da Quinta Diminuta.
 */
  protected IntervalSymbol diminishedFifthInterval;
/*
 * Representa o intervalo da Quinta Justa.
 */
  protected IntervalSymbol perfectFifthInterval;
/*
 * Representa o intervalo da Quinta Aumentada.
 */
  protected IntervalSymbol augmentedFifthInterval;
/*
 * Representa o intervalo da Sexta Menor.
 */
  protected IntervalSymbol minorSixthInterval;
/*
 * Representa o intervalo da Sexta Maior.
 */
  protected IntervalSymbol majorSixthInterval;
/*
 * Representa o intervalo da Sexta Aumentada.
 */
  protected IntervalSymbol augmentedSixthInterval;
/*
 * Representa o intervalo da Sétima Diminuta.
 */
  protected IntervalSymbol diminishedSeventhInterval;
/*
 * Representa o intervalo da Sétima Menor.
 */
  protected IntervalSymbol minorSeventhInterval;
/*
 * Representa o intervalo da Sétima Maior.
 */
  protected IntervalSymbol majorSeventhInterval;
/*
 * Representa o intervalo da Nona Menor.
 */
  protected IntervalSymbol minorNinthInterval;
/*
 * Representa o intervalo da Nona Maior.
 */
  protected IntervalSymbol majorNinthInterval;
/*
 * Representa o intervalo da Nona Aumentada.
 */
  protected IntervalSymbol augmentedNinthInterval;
/*
 * Representa o intervalo da Décima Primeira Diminuta.
 */
  protected IntervalSymbol diminishedEleventhInterval; //foi acrescentado por Alberico 26/01
/*
 * Representa o intervalo da Décima Primeira Justa.
 */
  protected IntervalSymbol perfectEleventhInterval;
/*
 * Representa o intervalo da Décima Primeira Aumentada.
 */
  protected IntervalSymbol augmentedEleventhInterval;
/*
 * Representa o intervalo da Décima Segunda Justa.
 */
  protected IntervalSymbol perfectTwefthlnterval;
/*
 * Representa o intervalo da Décima Terceira Menor.
 */
  protected IntervalSymbol minorThirteenthInterval;
/*
 * Representa o intervalo da Décima Terceira Maior.
 */
  protected IntervalSymbol majorThirteenthInterval;
/*
 * Representa o intervalo da Décima Terceira Aumentada.
 */
  protected IntervalSymbol augmentedThirteenthInterval; //foi acrescentado por Alberico 26/01
/*
 * Relação da notação utilizada para os intervalos.
 */
  protected String strIntervals[];
// Definicao dos vetores de numeros inteiros representando intervalos especificos
/*
 * Relação com os possíveis intervalos que podem ser utilizados para montar uma cifra {2, 4, 5, 6, 7, 9, 11, 13}.
 */
  protected ArrayList numericIntervals;
/*
 * Relação com um subconjunto de intervalos que podem ser utilizados para montar uma cifra {2, 4, 6}.
 */
  protected ArrayList intervalosNumA;
/*
 * Relacao com um subconjunto de intervalos que podem ser utilizados para montar uma cifra {9, 11, 13}.
 */
  protected ArrayList intervalosNumB;
/*
 * Relação com um subconjunto de intervalos que podem ser utilizados para montar uma cifra {2, 4, 9, 11}.
 */
  protected ArrayList intervalosNumC;
/*
 * Relação com um subconjunto de intervalos que podem ser utilizados para montar uma cifra {2, 4, 5, 6}.
 */
  protected ArrayList intervalosNumD;
// Declaração dos simbolos identificadores do acorde
/*
 * Representa o símbolo do acorde Diminuto na notação.
 */
  protected NotationalSymbol diminishedChord;
/*
 * Representa o símbolo do acorde Força na notação.
 */
  protected NotationalSymbol powerChord;
/*
 * Representa a notação utilizada para o acorde Diminuto.
 */
  protected String strDiminishedChord;
/*
 * Representa a notação utilizada para o acorde Força.
 */
  protected String strPowerChord;
// Definição de outros símbolos
/*
 * Representa o símbolo da nota Adicionada na notação.
 */
  protected NotationalSymbol addNote;
/*
 * Representa símbolo de início alteração em uma cifra.
 */
  protected NotationalSymbol accidentalBeginning;
/*
 * Representa símbolo de fim alteração em uma cifra.
 */
  protected NotationalSymbol accidentalEnding;
/*
 * Representa o símbolo de junção entre intervalos em uma cifra.
 */
  protected NotationalSymbol intervalJunction;
/*
 * Representa o símbolo de inversão em uma cifra.
 */
  protected NotationalSymbol invertion;
/*
 * Representa o símbolo de suspensão em uma cifra.
 */
  protected NotationalSymbol suspension;
/*
 * Representa a notação utilizada para nota Adicionada.
 */
  protected String strAddNote;
/*
 * Representa a notação utilizada para início de alteração.
 */
  protected String strAlterationBeginning;
/*
 * Representa a notação utilizada para fim de alteração.
 */
  protected String strlAterationEnding;
/*
 * Representa a notação utilizada para junção de intervalo.
 */
  protected String strIntervalJunction;
/*
 * Representa a notação utilizada para inversão (baixo).
 */
  protected String strInversion;
/*
 * Representa a notação utilizada para suspensão.
 */
  protected String strSuspension;

/**
 *  Loads the Chordnotation from a file.
 * @param file textual file contating the grammas of the notation.
 */
  public ChordNotation(File file) throws FileNotFoundException, IOException {
    ChordNotationReader reader = new ChordNotationReader(file);
    strNotes = reader.getNoteSymbols();
    strAlterations = reader.getAlterationSymbols();
    strIntervals = reader.getIntervalSymbols();
    strAddNote = reader.getAddNoteSymbol();
    strAlterationBeginning = reader.getAlterationBeginningSymbol();
    strlAterationEnding = reader.getAlterationEndingSymbol();
    strIntervalJunction = reader.getIntervalJunctionSymbol();
    strInversion = reader.getInvertionSymbol();
    strSuspension = reader.getSuspensionSymbol();
    this.configSymbols();
  }

/**
 * Creates a default ChordNotation; 
 */
  public ChordNotation(){
  // Inicializando vetores
    strNotes = new String[7];
    strAlterations = new String[2];
    strIntervals = new String[28];  //modificado o tamanho do vetor p. aceitar
                                     //novos 6 intervalos - Alberico Junior
    numericIntervals = new ArrayList(8);
    intervalosNumA = new ArrayList(3);
    intervalosNumB = new ArrayList(3);
    intervalosNumC = new ArrayList(4);
    intervalosNumD = new ArrayList(4);
  // Simbolos default de notas;
    strNotes[0] = "A";
    strNotes[1] = "B";
    strNotes[2] = "C";
    strNotes[3] = "D";
    strNotes[4] = "E";
    strNotes[5] = "F";
    strNotes[6] = "G";
  // Simbolos default para alterações
    strAlterations[0] = "#";
    strAlterations[1] = "b";
  // Simbolos default para intervalos
    strIntervals[0] = "";
    strIntervals[1] = "b2";
    strIntervals[2] = "2";
    strIntervals[3] = "#2";
    strIntervals[4] = "m";
    strIntervals[5] = "";
    strIntervals[6] = "b4"; //Alberico acrescentou
    strIntervals[7] = "4";
    strIntervals[8] = "#4";
    strIntervals[9] = "b5";
    strIntervals[10] = "5";
    strIntervals[11] = "#5";
    strIntervals[12] = "b6";
    strIntervals[13] = "6";
    strIntervals[14] = "#6";  //Alberico acrescentou
    strIntervals[15] = "";
    strIntervals[16] = "7";
    strIntervals[17] = "7M";
    strIntervals[18] = "b9";
    strIntervals[19] = "9";
    strIntervals[20] = "#9";
    strIntervals[21] = "b11"; //Alberico acrescentou
    strIntervals[22] = "11";
    strIntervals[23] = "#11";
    strIntervals[24] = "12";  //Alberico acrescentou so para acorde forca 25/02/02
    strIntervals[25] = "b13";
    strIntervals[26] = "13";
    strIntervals[27] = "#13"; //Alberico acrescentou
  // Declaração dos simbolos identificadores do acorde
    strDiminishedChord = "dim";
    strPowerChord = "5";
  // Outros simbolos default
    strAddNote = "add";
    strAlterationBeginning = "(";
    strlAterationEnding = ")";
    strIntervalJunction = "^" ;
    strInversion = "/";
    strSuspension = "sus";
    this.configSymbols();
  }

/*
 * Save the notation into a file.
 * @param filePath folder where the file will be saved.
 */
  /*protected void saveAs(String filePath) {
    //todo implement
  }*/

  
/*
 * Inicializa as notas DO, RE, MI, FA, SOL, LA e SI com os símbolos lidos a partir
 * de um arquivo ou com o valor default já existente no sistema.
 */
  private void configSymbols(){
    // Notas
    noteA = new NoteSymbol(strNotes[0], Notes.getA());
    noteB = new NoteSymbol(strNotes[1], Notes.getB());
    noteC = new NoteSymbol(strNotes[2], Notes.getC());
    noteD = new NoteSymbol(strNotes[3], Notes.getD());
    noteE = new NoteSymbol(strNotes[4], Notes.getE());
    noteF = new NoteSymbol(strNotes[5], Notes.getF());
    noteG = new NoteSymbol(strNotes[6],Notes.getG());
    // Alimentando lista de notas com os objetos instanciados
    NoteSymbol auxNotes [] = {noteA, noteB, noteC, noteD, noteE, noteF, noteG};
    notes = auxNotes;
    auxNotes = null;
    // Alterações
    sharp = new NotationalSymbol(strAlterations[0],"#","Sharp");;
    flat = new NotationalSymbol(strAlterations[1],"b","Flat");
    // Alimentando lista de accidentals com os objetos instanciados
    NotationalSymbol alteracoesTemp [] = {sharp, flat };
    accidentals = alteracoesTemp;
    alteracoesTemp = null;
    //Intervalos
    fundamentalInterval = new IntervalSymbol(strIntervals[0], Intervals.getRoot());
    minorSecondInterval = new IntervalSymbol(strIntervals[1], Intervals.getMinorSecond());
    majorSecondInterval = new IntervalSymbol(strIntervals[2], Intervals.getMajorSecond());
    augmentedSecondInterval = new IntervalSymbol(strIntervals[3], Intervals.getAugSecond());
    minorThirdInterval = new IntervalSymbol(strIntervals[4], Intervals.getMinorThird());
    majorThirdInterval = new IntervalSymbol(strIntervals[5], Intervals.getMajorThird());
    perfectFourthInterval = new IntervalSymbol(strIntervals[6], Intervals.getDimFourth());
    perfectFourthInterval = new IntervalSymbol(strIntervals[7], Intervals.getPerfectFourth());
    augmentedFourthInterval = new IntervalSymbol(strIntervals[8], Intervals.getAugFourth());
    diminishedFifthInterval = new IntervalSymbol(strIntervals[9], Intervals.getDimFifth());
    perfectFifthInterval = new IntervalSymbol(strIntervals[10], Intervals.getPerfectFifth());
    augmentedFifthInterval = new IntervalSymbol(strIntervals[11], Intervals.getAugFifth());
    minorSixthInterval = new IntervalSymbol(strIntervals[12], Intervals.getMinorSixth());
    majorSixthInterval = new IntervalSymbol(strIntervals[13], Intervals.getMajorSixth());
    perfectFourthInterval = new IntervalSymbol(strIntervals[14], Intervals.getAugSixth());
    diminishedSeventhInterval = new IntervalSymbol(strIntervals[15], Intervals.getDimSeventh());
    minorSeventhInterval = new IntervalSymbol(strIntervals[16], Intervals.getMinorSeventh());
    majorSeventhInterval = new IntervalSymbol(strIntervals[17], Intervals.getMajorSeventh());
    minorNinthInterval = new IntervalSymbol(strIntervals[18], Intervals.getMinorNinth());
    majorNinthInterval = new IntervalSymbol(strIntervals[19], Intervals.getMajorNinth());
    augmentedNinthInterval = new IntervalSymbol(strIntervals[20], Intervals.getAugNinth());
    diminishedEleventhInterval = new IntervalSymbol(strIntervals[21],Intervals.getDim11th());
    perfectEleventhInterval = new IntervalSymbol(strIntervals[22], Intervals.getPerfect11th());
    augmentedEleventhInterval = new IntervalSymbol(strIntervals[23], Intervals.getAug11th());
    perfectTwefthlnterval = new IntervalSymbol(strIntervals[24], Intervals.getPerfect12th());
    minorThirteenthInterval = new IntervalSymbol(strIntervals[25], Intervals.getMinor13th());
    majorThirteenthInterval = new IntervalSymbol(strIntervals[26], Intervals.getMajor13th());
    augmentedThirteenthInterval = new IntervalSymbol(strIntervals[27], Intervals.getAug13th());

    // Identificadores dos intervalos num, numA, numB, mumC e numD - AE
    numericIntervals.add(new IntervalSymbol(strIntervals[2], Intervals.getMajorSecond()));
    numericIntervals.add(new IntervalSymbol(strIntervals[7], Intervals.getPerfectFourth()));
    numericIntervals.add(new IntervalSymbol(strIntervals[10], Intervals.getPerfectFifth()));
    numericIntervals.add(new IntervalSymbol(strIntervals[13], Intervals.getMajorSixth()));
    numericIntervals.add(new IntervalSymbol(strIntervals[16], Intervals.getMinorSeventh()));
    numericIntervals.add(new IntervalSymbol(strIntervals[19], Intervals.getMajorNinth()));
    numericIntervals.add(new IntervalSymbol(strIntervals[22], Intervals.getPerfect11th()));
    numericIntervals.add(new IntervalSymbol(strIntervals[26], Intervals.getMajor13th()));

    intervalosNumA.add(new IntervalSymbol(strIntervals[2], Intervals.getMajorSecond()));
    intervalosNumA.add(new IntervalSymbol(strIntervals[7], Intervals.getPerfectFourth()));
    intervalosNumA.add(new IntervalSymbol(strIntervals[13], Intervals.getMajorSixth()));

    intervalosNumB.add(new IntervalSymbol(strIntervals[19], Intervals.getMajorNinth()));
    intervalosNumB.add(new IntervalSymbol(strIntervals[22], Intervals.getPerfect11th()));
    intervalosNumB.add(new IntervalSymbol(strIntervals[26], Intervals.getMajor13th()));

    intervalosNumC.add(new IntervalSymbol(strIntervals[2], Intervals.getMajorSecond()));
    intervalosNumC.add(new IntervalSymbol(strIntervals[7], Intervals.getPerfectFourth()));
    intervalosNumC.add(new IntervalSymbol(strIntervals[19], Intervals.getMajorNinth()));
    intervalosNumC.add(new IntervalSymbol(strIntervals[22], Intervals.getPerfect11th()));

    intervalosNumD.add(new IntervalSymbol(strIntervals[2], Intervals.getMajorSecond()));
    intervalosNumD.add(new IntervalSymbol(strIntervals[7], Intervals.getPerfectFourth()));
    intervalosNumD.add(new IntervalSymbol(strIntervals[10], Intervals.getPerfectFifth()));
    intervalosNumD.add(new IntervalSymbol(strIntervals[13], Intervals.getMajorSixth()));
    // Identificadores do acorde
    diminishedChord = new NotationalSymbol(strDiminishedChord, "Diminished Symbol");
    powerChord = new NotationalSymbol(strPowerChord,"Power Chord");
    // Outros simbolos
    addNote = new NotationalSymbol(strAddNote, "add interval without add lower intervals") ;
    accidentalBeginning = new NotationalSymbol(strAlterationBeginning, "Beginning of an alteration or inteval adding.");

    accidentalEnding = new NotationalSymbol(strlAterationEnding, "End of interval alteration os adding");
    intervalJunction = new NotationalSymbol(strIntervalJunction,"Connect intervals");
    invertion = new NotationalSymbol(strInversion, "Invertion");
    //faltava instanciar suspensao
    suspension = new NotationalSymbol(strSuspension, "Suspension");
  }

/*
 * 
 * Obtém o símbolo da nota dada a posição inicial de sua representação
 * dentro da cifra. Este métoto percorre todas as notas da notação atual
 * até encontrar o símbolo da nota cuja representação apareça a partir da
 * posição informada dentro da cifra passada como argumento.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição inicial a ser utilizada na pesquisa
 * @return Símbolo da nota procurado ou <b>null</b> caso não encontre
 * nenhuma nota correspondente.
 */
  protected NoteSymbol getNoteSymbol(String chordName, int startPos) {
    NoteSymbol retorno = null;
    String simboloUtilizado = null;
    int posFinal;
    int tamanho = 0;

    for (int i=0; i < notes.length; i++) {
      simboloUtilizado = notes[i].getUsedSymbol();
      tamanho = simboloUtilizado.length();
      posFinal = startPos + tamanho;

      if (posFinal <= chordName.length() && simboloUtilizado.equals(chordName.substring(startPos, posFinal))) {
        retorno = notes[i];
        break;
      }
    }

    return retorno;
  }

/*
 * Obtém o símbolo da alteração dada a posição inicial de sua representação
 * dentro da cifra. Este métoto percorre todas as alterações da notação atual
 * até encontrar o símbolo da alteração cuja representação apareça a partir da
 * posição informada dentro da cifra passado como argumento.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação da alteração inicia dentro da cifra
 * @return O símbolo da alteração procurada ou <b>null</b> caso não encontre
 * nenhuma alteração correspondente.
 */
  protected NotationalSymbol getAlterationSymbol(String chordName, int startPos) {
    NotationalSymbol retorno = null;
    String simboloUtilizado = null;
    int posFinal;
    int tamanho = 0;

    for (int i=0; i < accidentals.length; i++) {
      simboloUtilizado = accidentals[i].getUsedSymbol();
      tamanho = simboloUtilizado.length();
      posFinal = startPos + tamanho;

      if (posFinal <= chordName.length() && simboloUtilizado.equals(chordName.substring(startPos, posFinal))) {
        retorno = accidentals[i];
        break;
      }
    }

    return retorno;
  }

 /*
 * Obtém o símbolo do acorde diminuto dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do acorde diminuto inicia dentro da cifra
 * @return O símbolo do acorde diminuto procurado ou <b>null</b> caso não encontre
 * nenhum acorde diminuto correspondente.
 */
  protected NotationalSymbol getDiminishedChordSymbol(String chordName, int startPos) {
    NotationalSymbol retorno = null;
    String strAcordeDiminuto = diminishedChord.getUsedSymbol();
    int tamanho = strAcordeDiminuto.length();
    int posFinal = startPos + tamanho;

    if (posFinal <= chordName.length() && strAcordeDiminuto.equals(chordName.substring(startPos,posFinal))) {
      retorno = diminishedChord;
    }

    return retorno;
  }

 /*
 * Obtém o símbolo do intervalo terça menor dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do intervalo terça menor inicia dentro da cifra
 * @return O símbolo do intervalo terça menor procurado ou <b>null</b> caso não encontre
 * nenhum intervalo terça menor correspondente.
 */
  protected IntervalSymbol getMinorThirdSymbol(String chordName, int startPos) {
    IntervalSymbol retorno = null;
    String strIntervaloTercaMenor = minorThirdInterval.getUsedSymbol();
    int tamanho = strIntervaloTercaMenor.length();
    int posFinal = startPos + tamanho;

    if (posFinal <= chordName.length() && strIntervaloTercaMenor.equals(chordName.substring(startPos,posFinal))) {
      retorno = minorThirdInterval;
    }

    return retorno;
  }

/*
 * Obtém o símbolo do intervalo quinta justa dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do intervalo quinta justa inicia dentro da cifra
 * @return O símbolo do intervalo quinta justa procurado ou <b>null</b> caso não encontre
 * nenhum intervalo quinta justa correspondente.
 */
  protected IntervalSymbol getPerfectFifthSymbol(String chordName, int startPos) {
    IntervalSymbol retorno = null;
    String strIntervaloQuintaJusta = perfectFifthInterval.getUsedSymbol();
    int tamanho = strIntervaloQuintaJusta.length();
    int posFinal = startPos + tamanho;

    if (posFinal <= chordName.length() && strIntervaloQuintaJusta.equals(chordName.substring(startPos,posFinal))) {
      retorno = perfectFifthInterval;
    }

    return retorno;
  }

/*
 * Obtém o símbolo do intervalo sétima maior dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do intervalo sétima maior inicia dentro da cifra
 * @return O símbolo do intervalo sétima maior procurado ou <b>null</b> caso não encontre
 * nenhum intervalo sétima maior correspondente.
 */
  protected IntervalSymbol getMajorSeventhSymbol(String chordName, int startPos) {
    IntervalSymbol retorno = null;
    String strIntervaloSetimaMaior = majorSeventhInterval.getUsedSymbol();
    int tamanho = strIntervaloSetimaMaior.length();
    int posFinal = startPos + tamanho;

    if (posFinal <= chordName.length() && strIntervaloSetimaMaior.equals(chordName.substring(startPos,posFinal))) {
      retorno = majorSeventhInterval;
    }

    return retorno;
  }

/*
 * Obtém o símbolo do intervalo sétima menor dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do intervalo sétima menor inicia dentro da cifra
 * @return O símbolo do intervalo sétima menor procurado ou <b>null</b> caso não encontre
 * nenhum intervalo sétima menor correspondente.
 */
  protected IntervalSymbol getMinorSeventhSymbol(String chordName, int startPos) {
    IntervalSymbol retorno = null;
    String strIntervaloSetimaMenor = minorSeventhInterval.getUsedSymbol();
    int tamanho = strIntervaloSetimaMenor.length();
    int posFinal = startPos + tamanho;

    if (posFinal <= chordName.length() && strIntervaloSetimaMenor.equals(chordName.substring(startPos,posFinal))) {
      retorno = minorSeventhInterval;
    }

    return retorno;
  }

/*
 * Obtém o símbolo do intervalo nona maior dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do intervalo nona maior inicia dentro da cifra
 * @return O símbolo do intervalo nona maior procurado ou <b>null</b> caso não encontre
 * nenhum intervalo nona maior correspondente.
 */
  protected IntervalSymbol getMajorNinthSymbol(String chordName, int startPos) {
    IntervalSymbol retorno = null;
    String strIntervaloNonaMaior = majorNinthInterval.getUsedSymbol();
    int tamanho = strIntervaloNonaMaior.length();
    int posFinal = startPos + tamanho;

    if (posFinal <= chordName.length() && strIntervaloNonaMaior.equals(chordName.substring(startPos,posFinal))) {
      retorno = majorNinthInterval;
    }

    return retorno;
  }

/*
 * Obtém o símbolo do intervalo décima primeira justa dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do intervalo décima primeira justa inicia dentro da cifra
 * @return O símbolo do intervalo décima primeira justa procurado ou <b>null</b> caso não encontre
 * nenhum intervalo décima primeira justa correspondente.
 */
  protected IntervalSymbol getPerfect11th(String chordName, int posInicial) {
    IntervalSymbol retorno = null;
    String strIntervaloDecimaPrimeiraJusta = perfectEleventhInterval.getUsedSymbol();
    int tamanho = strIntervaloDecimaPrimeiraJusta.length();
    int posFinal = posInicial + tamanho;

    if (posFinal <= chordName.length() && strIntervaloDecimaPrimeiraJusta.equals(chordName.substring(posInicial,posFinal))) {
      retorno = perfectEleventhInterval;
    }

    return retorno;
  }

/*
 * Obtém o símbolo do intervalo décima terceira maior dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do intervalo décima terceira maior inicia dentro da cifra
 * @return O símbolo do intervalo décima terceira maior procurado ou <b>null</b> caso não encontre
 * nenhum intervalo décima terceira maior correspondente.
 */
  protected IntervalSymbol getMajor13thSymbol(String chordName, int posInicial) {
    IntervalSymbol retorno = null;
    String strIntervaloDecimaTerceiraMaior = majorThirteenthInterval.getUsedSymbol();
    int tamanho = strIntervaloDecimaTerceiraMaior.length();
    int posFinal = posInicial + tamanho;

    if (posFinal <= chordName.length() && strIntervaloDecimaTerceiraMaior.equals(chordName.substring(posInicial,posFinal))) {
      retorno = majorThirteenthInterval;
    }

    return retorno;
  }

/*
 * Obtém o símbolo da suspensão dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação da suspensão inicia dentro da cifra
 * @return O símbolo da suspensão procurado ou <b>null</b> caso não encontre
 * nenhum símbolo suspensão correspondente.
 */
  protected NotationalSymbol getSuspensionSymbol(String chordName, int posInicial) {
    NotationalSymbol retorno = null;
    String strSuspensao = suspension.getUsedSymbol();
    int tamanho = strSuspensao.length();
    int posFinal = posInicial + tamanho;

    if (posFinal <= chordName.length() && strSuspensao.equals(chordName.substring(posInicial,posFinal))) {
      retorno = suspension;
    }

    return retorno;
  }

/*
 * Obtém o símbolo da nota adicionada dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação da nota adicionada inicia dentro da cifra
 * @return O símbolo da nota adicionada procurado ou <b>null</b> caso não encontre
 * nenhum símbolo nota adicionada correspondente.
 */
  protected NotationalSymbol getAddNoteSymbol(String chordName, int posInicial) {
    NotationalSymbol retorno = null;
    String strNotaAdicionada = addNote.getUsedSymbol();
    int tamanho = strNotaAdicionada.length();
    int posFinal = posInicial + tamanho;

    if (posFinal <= chordName.length() && strNotaAdicionada.equals(chordName.substring(posInicial,posFinal))) {
      retorno = addNote;
    }

    return retorno;
  }

/*
 * Obtém o símbolo do início alteração dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do início alteração inicia dentro da cifra
 * @return O símbolo do início alteração procurado ou <b>null</b> caso não encontre
 * nenhum símbolo início alteração correspondente.
 */
  protected NotationalSymbol getAlterationBeginningSymbol(String chordName, int posInicial) {
    NotationalSymbol retorno = null;
    String strInicioAlteracao = accidentalBeginning.getUsedSymbol();
    int tamanho = strInicioAlteracao.length();
    int posFinal = posInicial + tamanho;

    if (posFinal <= chordName.length() && strInicioAlteracao.equals(chordName.substring(posInicial,posFinal))) {
      retorno = accidentalBeginning;
    }

    return retorno;
  }

/*
 * Obtém o símbolo do fim alteração dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação do fim alteração inicia dentro da cifra
 * @return O símbolo do fim alteração procurado ou <b>null</b> caso não encontre
 * nenhum símbolo fim alteração correspondente.
 */
  protected NotationalSymbol getAlterationEndingSymbol(String chordName, int posInicial) {
    NotationalSymbol retorno = null;
    String strFimAlteracao = accidentalEnding.getUsedSymbol();
    int tamanho = strFimAlteracao.length();
    int posFinal = posInicial + tamanho;

    if (posFinal <= chordName.length() && strFimAlteracao.equals(chordName.substring(posInicial,posFinal))) {
      retorno = accidentalEnding;
    }

    return retorno;
  }

/*
 * Obtém o símbolo da junção intervalo dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação da junção intervalo inicia dentro da cifra
 * @return O símbolo da junção intervalo procurado ou <b>null</b> caso não encontre
 * nenhum símbolo junção intervalo correspondente.
 */
  protected NotationalSymbol getIntervalJunctionSymbol(String chordName, int posInicial) {
    NotationalSymbol retorno = null;
    String strJuncaoIntervalo = intervalJunction.getUsedSymbol();
    int tamanho = strJuncaoIntervalo.length();
    int posFinal = posInicial + tamanho;

    if (posFinal <= chordName.length() && strJuncaoIntervalo.equals(chordName.substring(posInicial,posFinal))) {
      retorno = intervalJunction;
    }

    return retorno;
  }

/*
 * Obtém o símbolo da inversão dada a posição inicial de sua representação
 * dentro da cifra.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição em que a representação da inversão inicia dentro da cifra
 * @return O símbolo da inversão procurado ou <b>null</b> caso não encontre
 * nenhum símbolo inversão intervalo correspondente.
 */
  protected NotationalSymbol getInvertionSymbol(String chordName, int posInicial) {
    NotationalSymbol retorno = null;
    String strInversao = invertion.getUsedSymbol();
    int tamanho = strInversao.length();
    int posFinal = posInicial + tamanho;

    if (posFinal <= chordName.length() && strInversao.equals(chordName.substring(posInicial,posFinal))) {
      retorno = invertion;
    }

    return retorno;
  }


/**
 * Obtém o símbolo do intervalo NumA dada a posição inicial de sua representação
 * dentro da cifra. Este métoto percorre todas as posições da relação de
 * intervalos NumA até encontrar o símbolo do intervalo NumA cuja representação
 * apareça a partir da posição informada dentro da cifra passada como argumento.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição inicial a ser utilizada na pesquisa
 * @return Símbolo do intervalo NumA procurado ou <b>null</b> caso não encontre
 * nenhuma intervalo NumA correspondente.
 */
  protected IntervalSymbol getSimboloIntervaloNumA(String chordName, int posInicial) {
    IntervalSymbol retorno = null;
    String simboloUtilizado = null;
    int posFinal;
    int tamanho = 0;

    for (int i=0; i < intervalosNumA.size(); i++) {
      IntervalSymbol simbIntAux = (IntervalSymbol) intervalosNumA.get(i);
      simboloUtilizado = simbIntAux.getUsedSymbol();
      tamanho = simboloUtilizado.length();
      posFinal = posInicial + tamanho;

      if (posFinal <= chordName.length() && simboloUtilizado.equals(chordName.substring(posInicial,posFinal))) {
        retorno = simbIntAux;
        break;
      }
    }

    return retorno;
  }

/**
 * Obtém o símbolo do intervalo NumB dada a posição inicial de sua representação
 * dentro da cifra. Este métoto percorre todas as posições da relação de
 * intervalos NumB até encontrar o símbolo do intervalo NumB cuja representação
 * apareça a partir da posição informada dentro da cifra passada como argumento.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição inicial a ser utilizada na pesquisa
 * @return Símbolo do intervalo NumB procurado ou <b>null</b> caso não encontre
 * nenhuma intervalo NumB correspondente.
 */
  protected IntervalSymbol getSimboloIntervaloNumB(String chordName, int posInicial) {
    IntervalSymbol retorno = null;
    String simboloUtilizado = null;
    int posFinal;
    int tamanho = 0;

    for (int i=0; i < intervalosNumB.size(); i++) {
      IntervalSymbol simbIntAux = (IntervalSymbol) intervalosNumB.get(i);
      simboloUtilizado = simbIntAux.getUsedSymbol();
      tamanho = simboloUtilizado.length();
      posFinal = posInicial + tamanho;

      if (posFinal <= chordName.length() && simboloUtilizado.equals(chordName.substring(posInicial,posFinal))) {
        retorno = simbIntAux;
        break;
      }
    }

    return retorno;
  }

/**
 * Obtém o símbolo do intervalo NumC dada a posição inicial de sua representação
 * dentro da cifra. Este métoto percorre todas as posições da relação de
 * intervalos NumC até encontrar o símbolo do intervalo NumC cuja representação
 * apareça a partir da posição informada dentro da cifra passada como argumento.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição inicial a ser utilizada na pesquisa
 * @return Símbolo do intervalo NumC procurado ou <b>null</b> caso não encontre
 * nenhuma intervalo NumC correspondente.
 */
   protected IntervalSymbol getSimboloIntervaloNumC(String chordName, int posInicial) {
    IntervalSymbol retorno = null;
    String simboloUtilizado = null;
    int posFinal;
    int tamanho = 0;

    for (int i=0; i < intervalosNumC.size(); i++) {
      IntervalSymbol simbIntAux = (IntervalSymbol) intervalosNumC.get(i);
      simboloUtilizado = simbIntAux.getUsedSymbol();
      tamanho = simboloUtilizado.length();
      posFinal = posInicial + tamanho;

      if (posFinal <= chordName.length() && simboloUtilizado.equals(chordName.substring(posInicial,posFinal))) {
        retorno = simbIntAux;
        break;
      }
    }

    return retorno;
  }

/**
 * Obtém o símbolo do intervalo NumD dada a posição inicial de sua representação
 * dentro da cifra. Este métoto percorre todas as posições da relação de
 * intervalos NumD até encontrar o símbolo do intervalo NumD cuja representação
 * apareça a partir da posição informada dentro da cifra passada como argumento.
 * @param cifra Representação da cifra a ser pesquisada.
 * @param posInicial Posição inicial a ser utilizada na pesquisa
 * @return Símbolo do intervalo NumD procurado ou <b>null</b> caso não encontre
 * nenhuma intervalo NumD correspondente.
 */
  protected IntervalSymbol getSimboloIntervaloNumD(String chordName, int posInicial) {
    IntervalSymbol retorno = null;
    String simboloUtilizado = null;
    int posFinal;
    int tamanho = 0;

    for (int i=0; i < intervalosNumD.size(); i++) {
      IntervalSymbol simbIntAux = (IntervalSymbol) intervalosNumD.get(i);
      simboloUtilizado = simbIntAux.getUsedSymbol();
      tamanho = simboloUtilizado.length();
      posFinal = posInicial + tamanho;

      if (posFinal <= chordName.length() && simboloUtilizado.equals(chordName.substring(posInicial,posFinal))) {
        retorno = simbIntAux;
        break;
      }
    }

    return retorno;
  }

  public String[] getNotesSymbols(){
      String[] retorno = new String[notes.length];
      for (int i = 0; i < this.notes.length;i++){
          retorno[i] = notes[i].getUsedSymbol();
      }
      return retorno;
  }

  /*
   * Esta classe armazena a configuração da notação da cifra que será utilizada
   * pelo sistema de acordo com a preferência do usuário. Exemplo: o sistema
   * configura o simboloUtilizado para Acorde Suspensão com "sus", mas o usuário
   * pode preferir identificar um Acorde Supensão com a palavra "suspensão".
   * @see java.lang.Object
   * @author Leandro Lesqueves Costalonga
   * @version 1.1
   */

   class ChordNotationReader {
  /*
   * Representa a descrição das notas da cifra.
   */
    protected String strNotes[];
  /*
   * Representa a descrição dos intervalos.
   */
    protected String strIntervals[];
  /*
   * Representa a descrição das alterações.
   */
    protected String strAlterations[];
  /*
   * Representa a descrição do acorde diminuto.
   */
    protected String strDiminishedChord;
  /*
   * Representa a descrição do acorde força.
   */
    protected String strPowerChord;
  /*
   * Representa a descrição da  nota adicionada.
   */
    protected String strAddNote;
  /*
   * Representa a descrição do início da alteração.
   */
    protected String strAlterationBeginning;
  /*
   * Representa a descrição do final da alteração.
   */
    protected String strAlterationEnding;
  /*
   * Representa a descrição da junção do intervalo.
   */
    protected String strIntervalJunction;
  /*
   * Representa a descrição da inversão.
   */
    protected String strInversion;
  /*
   * Representa a descrição da suspensão.
   */
    protected String strSuspension;

  /*
   * Inicializa as propriedades da Notação Cifra com os valores obtidos de um arquivo.
   * @param arquivo Fonte de informações necessárias para a inicialização das propiedades.
   */
    protected ChordNotationReader(File file) throws FileNotFoundException,
        IOException {
//      try {
        FileReader entrada = new FileReader(file);
        BufferedReader bufEntrada = new BufferedReader(entrada);
        String linha;

        // Pegando as notas
        for (int i=0;i<7; i++ ) {
          strNotes[i] = bufEntrada.readLine();
        }

        //Pegando as alterações
        for (int i=0;i<2; i++ ) {
          strAlterations[i] = bufEntrada.readLine();
        }

        //Pegando os intervalos
        for (int i=0;i<23; i++ ) {
                strIntervals[i] = bufEntrada.readLine();
        }

        //Pegando os outros simbolos
      /*  for (int i=0;i<23; i++ ) {
                strIntervalos[i] = bufEntrada.readLine();
        }*/

        // Pegando os tipos de acordes
        strDiminishedChord = bufEntrada.readLine();
        strPowerChord = bufEntrada.readLine();

        // protected SimboloCifra acordeMenor = new SimboloCifra("m", "menor","Acorde Menor");
        // protected SimboloCifra acordeMaior = new SimboloCifra("", "maior", "Acorde Maior");
        // protected SimboloCifra acordeTetrade = new SimboloCifra("7","setima", "Acorde com sétima") ;

        // Pegando os outros símbolos
        strAddNote = bufEntrada.readLine();
        strAlterationBeginning = bufEntrada.readLine();
        strAlterationEnding = bufEntrada.readLine();
        strIntervalJunction = bufEntrada.readLine();
        strInversion = bufEntrada.readLine();
        strSuspension = bufEntrada.readLine();
     // } catch (Exception e) {
      //  e.printStackTrace();
     // }
    }

  /*
   * Obtém uma relação com os símbolos das notas.
   * @return Relação com a notação para as notas.
   */
    protected String[] getNoteSymbols() {return strNotes;};

  /*
   * Obtém uma relação com a notação para os intervalos.
   * @return Relação com a notação para os intervalos.
   */
    protected String[] getIntervalSymbols(){return strIntervals;};

  /*
   * Obtém uma relação com a notação para as alterações.
   * Os símbolos utilizados pelo sistema são " # " e " b ".
   * @return Relação com a notação para as alterações.
   */
    protected String[] getAlterationSymbols(){ return strAlterations;};

  /*
   * Obtém a notação para o acorde diminuto.
   * O simbolo utilizado pelo sistema é " ° ".
   * @return Notação para o acorde diminuto.
   */
    protected String getDiminishedChordSymbol() { return strDiminishedChord;};

  /*
   * Obtém a notação para o acorde força.
   * O símbolo utilizado pelo sistema é " 5 ".
   * @return Notação para o acorde força.
   */
    protected String getPowerChordSymbol() {return strPowerChord;};

  /*
   * Obtém a notação para o acorde com nota Adicionada.
   * O símbolo utilizado pelo sistema é " add ".
   * @return Notação para o acorde com nota Adicionada.
   */
    protected String getAddNoteSymbol() {return strAddNote;};

  /*
   * Obtém a notação para o início de alteração.
   * O símbolo utilizado pelo sistema é " ( ".
   * @return Notação para início de alteração.
   */
    protected String getAlterationBeginningSymbol() { return strAlterationBeginning;};

  /*
   * Obtém a notação para o final de alteração.
   * O símbolo utilizado pelo sistema é " ) ".
   * @return Notação para o final de alteração.
   */
    protected String getAlterationEndingSymbol() { return strAlterationEnding;};

  /*
   * Obtém a notação para a junção de intervalos.
   * O símbolo utilizado pelo sistema é " ^ ".
   * @return Notação para a junção do intervalo.
   */
    protected String getIntervalJunctionSymbol() { return strIntervalJunction;};

  /*
   * Obtém a notação para inversão (baixo).
   * O símbolo utilizado pelo sistema é " / ".
   * @return Notação para o símbolo inversão.
   */
    protected String getInvertionSymbol() { return strInversion;};

  /*
   * Obtém a notação para suspensão.
   * O símbolo utilizado pelo sistema é " sus ".
   * @return Notação para o símbolo suspensão.
   */
    protected String getSuspensionSymbol() { return strSuspension;};
  }


}
