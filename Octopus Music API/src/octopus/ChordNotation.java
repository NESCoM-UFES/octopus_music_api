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

// Declara��o das notas
/*
 * Representa a nota LA da nota��o.
 */
  protected NoteSymbol noteA;
/*
 * Representa a nota SI da nota��o.
 */
  protected NoteSymbol noteB;
/*
 * Representa a nota DO da nota��o.
 */
  protected NoteSymbol noteC;
/*
 * Representa a nota RE da nota��o.
 */
  protected NoteSymbol noteD;
/*
 * Representa a nota MI da nota��o.
 */
  protected NoteSymbol noteE;
/*
 * Representa a nota FA da nota��o.
 */
  protected NoteSymbol noteF;
/*
 * Representa a nota SOL da nota��o.
 */
  protected NoteSymbol noteG;
/*
 * Rela��o da nota��o utilizada para as notas.
 */
  protected String strNotes[];
/*
 * Rela��o dos s�mbolos que configuram as notas da nota��o.
 */
  protected NoteSymbol notes [];
// Declara��o dos s�mbolos das altera��es das notas
/*
 * Representa o s�mbolo para a altera��o Sustenido em uma cifra.
 */
  protected NotationalSymbol sharp;
/*
 * Representa o s�mbolo para a altera��o Bemol em uma cifra.
 */
  protected NotationalSymbol flat;
/*
   * Rela��o da nota��o utilizada para as altera��es (sustenido e flat).
   */
  protected String strAlterations[];
/*
 * Rela��o de s�mbolos que configuram as altera��es da nota na nota��o.
 */
  protected NotationalSymbol accidentals[];
// Decla��o dos s�mbolos dos intervalos
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
 * Representa o intervalo da Ter�a Menor.
 */
  protected IntervalSymbol minorThirdInterval;
/*
 * Representa o intervalo da Ter�a Maior.
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
 * Representa o intervalo da S�tima Diminuta.
 */
  protected IntervalSymbol diminishedSeventhInterval;
/*
 * Representa o intervalo da S�tima Menor.
 */
  protected IntervalSymbol minorSeventhInterval;
/*
 * Representa o intervalo da S�tima Maior.
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
 * Representa o intervalo da D�cima Primeira Diminuta.
 */
  protected IntervalSymbol diminishedEleventhInterval; //foi acrescentado por Alberico 26/01
/*
 * Representa o intervalo da D�cima Primeira Justa.
 */
  protected IntervalSymbol perfectEleventhInterval;
/*
 * Representa o intervalo da D�cima Primeira Aumentada.
 */
  protected IntervalSymbol augmentedEleventhInterval;
/*
 * Representa o intervalo da D�cima Segunda Justa.
 */
  protected IntervalSymbol perfectTwefthlnterval;
/*
 * Representa o intervalo da D�cima Terceira Menor.
 */
  protected IntervalSymbol minorThirteenthInterval;
/*
 * Representa o intervalo da D�cima Terceira Maior.
 */
  protected IntervalSymbol majorThirteenthInterval;
/*
 * Representa o intervalo da D�cima Terceira Aumentada.
 */
  protected IntervalSymbol augmentedThirteenthInterval; //foi acrescentado por Alberico 26/01
/*
 * Rela��o da nota��o utilizada para os intervalos.
 */
  protected String strIntervals[];
// Definicao dos vetores de numeros inteiros representando intervalos especificos
/*
 * Rela��o com os poss�veis intervalos que podem ser utilizados para montar uma cifra {2, 4, 5, 6, 7, 9, 11, 13}.
 */
  protected ArrayList numericIntervals;
/*
 * Rela��o com um subconjunto de intervalos que podem ser utilizados para montar uma cifra {2, 4, 6}.
 */
  protected ArrayList intervalosNumA;
/*
 * Relacao com um subconjunto de intervalos que podem ser utilizados para montar uma cifra {9, 11, 13}.
 */
  protected ArrayList intervalosNumB;
/*
 * Rela��o com um subconjunto de intervalos que podem ser utilizados para montar uma cifra {2, 4, 9, 11}.
 */
  protected ArrayList intervalosNumC;
/*
 * Rela��o com um subconjunto de intervalos que podem ser utilizados para montar uma cifra {2, 4, 5, 6}.
 */
  protected ArrayList intervalosNumD;
// Declara��o dos simbolos identificadores do acorde
/*
 * Representa o s�mbolo do acorde Diminuto na nota��o.
 */
  protected NotationalSymbol diminishedChord;
/*
 * Representa o s�mbolo do acorde For�a na nota��o.
 */
  protected NotationalSymbol powerChord;
/*
 * Representa a nota��o utilizada para o acorde Diminuto.
 */
  protected String strDiminishedChord;
/*
 * Representa a nota��o utilizada para o acorde For�a.
 */
  protected String strPowerChord;
// Defini��o de outros s�mbolos
/*
 * Representa o s�mbolo da nota Adicionada na nota��o.
 */
  protected NotationalSymbol addNote;
/*
 * Representa s�mbolo de in�cio altera��o em uma cifra.
 */
  protected NotationalSymbol accidentalBeginning;
/*
 * Representa s�mbolo de fim altera��o em uma cifra.
 */
  protected NotationalSymbol accidentalEnding;
/*
 * Representa o s�mbolo de jun��o entre intervalos em uma cifra.
 */
  protected NotationalSymbol intervalJunction;
/*
 * Representa o s�mbolo de invers�o em uma cifra.
 */
  protected NotationalSymbol invertion;
/*
 * Representa o s�mbolo de suspens�o em uma cifra.
 */
  protected NotationalSymbol suspension;
/*
 * Representa a nota��o utilizada para nota Adicionada.
 */
  protected String strAddNote;
/*
 * Representa a nota��o utilizada para in�cio de altera��o.
 */
  protected String strAlterationBeginning;
/*
 * Representa a nota��o utilizada para fim de altera��o.
 */
  protected String strlAterationEnding;
/*
 * Representa a nota��o utilizada para jun��o de intervalo.
 */
  protected String strIntervalJunction;
/*
 * Representa a nota��o utilizada para invers�o (baixo).
 */
  protected String strInversion;
/*
 * Representa a nota��o utilizada para suspens�o.
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
  // Simbolos default para altera��es
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
  // Declara��o dos simbolos identificadores do acorde
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
 * Inicializa as notas DO, RE, MI, FA, SOL, LA e SI com os s�mbolos lidos a partir
 * de um arquivo ou com o valor default j� existente no sistema.
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
    // Altera��es
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
 * Obt�m o s�mbolo da nota dada a posi��o inicial de sua representa��o
 * dentro da cifra. Este m�toto percorre todas as notas da nota��o atual
 * at� encontrar o s�mbolo da nota cuja representa��o apare�a a partir da
 * posi��o informada dentro da cifra passada como argumento.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o inicial a ser utilizada na pesquisa
 * @return S�mbolo da nota procurado ou <b>null</b> caso n�o encontre
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
 * Obt�m o s�mbolo da altera��o dada a posi��o inicial de sua representa��o
 * dentro da cifra. Este m�toto percorre todas as altera��es da nota��o atual
 * at� encontrar o s�mbolo da altera��o cuja representa��o apare�a a partir da
 * posi��o informada dentro da cifra passado como argumento.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o da altera��o inicia dentro da cifra
 * @return O s�mbolo da altera��o procurada ou <b>null</b> caso n�o encontre
 * nenhuma altera��o correspondente.
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
 * Obt�m o s�mbolo do acorde diminuto dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do acorde diminuto inicia dentro da cifra
 * @return O s�mbolo do acorde diminuto procurado ou <b>null</b> caso n�o encontre
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
 * Obt�m o s�mbolo do intervalo ter�a menor dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do intervalo ter�a menor inicia dentro da cifra
 * @return O s�mbolo do intervalo ter�a menor procurado ou <b>null</b> caso n�o encontre
 * nenhum intervalo ter�a menor correspondente.
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
 * Obt�m o s�mbolo do intervalo quinta justa dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do intervalo quinta justa inicia dentro da cifra
 * @return O s�mbolo do intervalo quinta justa procurado ou <b>null</b> caso n�o encontre
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
 * Obt�m o s�mbolo do intervalo s�tima maior dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do intervalo s�tima maior inicia dentro da cifra
 * @return O s�mbolo do intervalo s�tima maior procurado ou <b>null</b> caso n�o encontre
 * nenhum intervalo s�tima maior correspondente.
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
 * Obt�m o s�mbolo do intervalo s�tima menor dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do intervalo s�tima menor inicia dentro da cifra
 * @return O s�mbolo do intervalo s�tima menor procurado ou <b>null</b> caso n�o encontre
 * nenhum intervalo s�tima menor correspondente.
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
 * Obt�m o s�mbolo do intervalo nona maior dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do intervalo nona maior inicia dentro da cifra
 * @return O s�mbolo do intervalo nona maior procurado ou <b>null</b> caso n�o encontre
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
 * Obt�m o s�mbolo do intervalo d�cima primeira justa dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do intervalo d�cima primeira justa inicia dentro da cifra
 * @return O s�mbolo do intervalo d�cima primeira justa procurado ou <b>null</b> caso n�o encontre
 * nenhum intervalo d�cima primeira justa correspondente.
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
 * Obt�m o s�mbolo do intervalo d�cima terceira maior dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do intervalo d�cima terceira maior inicia dentro da cifra
 * @return O s�mbolo do intervalo d�cima terceira maior procurado ou <b>null</b> caso n�o encontre
 * nenhum intervalo d�cima terceira maior correspondente.
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
 * Obt�m o s�mbolo da suspens�o dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o da suspens�o inicia dentro da cifra
 * @return O s�mbolo da suspens�o procurado ou <b>null</b> caso n�o encontre
 * nenhum s�mbolo suspens�o correspondente.
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
 * Obt�m o s�mbolo da nota adicionada dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o da nota adicionada inicia dentro da cifra
 * @return O s�mbolo da nota adicionada procurado ou <b>null</b> caso n�o encontre
 * nenhum s�mbolo nota adicionada correspondente.
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
 * Obt�m o s�mbolo do in�cio altera��o dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do in�cio altera��o inicia dentro da cifra
 * @return O s�mbolo do in�cio altera��o procurado ou <b>null</b> caso n�o encontre
 * nenhum s�mbolo in�cio altera��o correspondente.
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
 * Obt�m o s�mbolo do fim altera��o dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o do fim altera��o inicia dentro da cifra
 * @return O s�mbolo do fim altera��o procurado ou <b>null</b> caso n�o encontre
 * nenhum s�mbolo fim altera��o correspondente.
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
 * Obt�m o s�mbolo da jun��o intervalo dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o da jun��o intervalo inicia dentro da cifra
 * @return O s�mbolo da jun��o intervalo procurado ou <b>null</b> caso n�o encontre
 * nenhum s�mbolo jun��o intervalo correspondente.
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
 * Obt�m o s�mbolo da invers�o dada a posi��o inicial de sua representa��o
 * dentro da cifra.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o em que a representa��o da invers�o inicia dentro da cifra
 * @return O s�mbolo da invers�o procurado ou <b>null</b> caso n�o encontre
 * nenhum s�mbolo invers�o intervalo correspondente.
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
 * Obt�m o s�mbolo do intervalo NumA dada a posi��o inicial de sua representa��o
 * dentro da cifra. Este m�toto percorre todas as posi��es da rela��o de
 * intervalos NumA at� encontrar o s�mbolo do intervalo NumA cuja representa��o
 * apare�a a partir da posi��o informada dentro da cifra passada como argumento.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o inicial a ser utilizada na pesquisa
 * @return S�mbolo do intervalo NumA procurado ou <b>null</b> caso n�o encontre
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
 * Obt�m o s�mbolo do intervalo NumB dada a posi��o inicial de sua representa��o
 * dentro da cifra. Este m�toto percorre todas as posi��es da rela��o de
 * intervalos NumB at� encontrar o s�mbolo do intervalo NumB cuja representa��o
 * apare�a a partir da posi��o informada dentro da cifra passada como argumento.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o inicial a ser utilizada na pesquisa
 * @return S�mbolo do intervalo NumB procurado ou <b>null</b> caso n�o encontre
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
 * Obt�m o s�mbolo do intervalo NumC dada a posi��o inicial de sua representa��o
 * dentro da cifra. Este m�toto percorre todas as posi��es da rela��o de
 * intervalos NumC at� encontrar o s�mbolo do intervalo NumC cuja representa��o
 * apare�a a partir da posi��o informada dentro da cifra passada como argumento.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o inicial a ser utilizada na pesquisa
 * @return S�mbolo do intervalo NumC procurado ou <b>null</b> caso n�o encontre
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
 * Obt�m o s�mbolo do intervalo NumD dada a posi��o inicial de sua representa��o
 * dentro da cifra. Este m�toto percorre todas as posi��es da rela��o de
 * intervalos NumD at� encontrar o s�mbolo do intervalo NumD cuja representa��o
 * apare�a a partir da posi��o informada dentro da cifra passada como argumento.
 * @param cifra Representa��o da cifra a ser pesquisada.
 * @param posInicial Posi��o inicial a ser utilizada na pesquisa
 * @return S�mbolo do intervalo NumD procurado ou <b>null</b> caso n�o encontre
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
   * Esta classe armazena a configura��o da nota��o da cifra que ser� utilizada
   * pelo sistema de acordo com a prefer�ncia do usu�rio. Exemplo: o sistema
   * configura o simboloUtilizado para Acorde Suspens�o com "sus", mas o usu�rio
   * pode preferir identificar um Acorde Supens�o com a palavra "suspens�o".
   * @see java.lang.Object
   * @author Leandro Lesqueves Costalonga
   * @version 1.1
   */

   class ChordNotationReader {
  /*
   * Representa a descri��o das notas da cifra.
   */
    protected String strNotes[];
  /*
   * Representa a descri��o dos intervalos.
   */
    protected String strIntervals[];
  /*
   * Representa a descri��o das altera��es.
   */
    protected String strAlterations[];
  /*
   * Representa a descri��o do acorde diminuto.
   */
    protected String strDiminishedChord;
  /*
   * Representa a descri��o do acorde for�a.
   */
    protected String strPowerChord;
  /*
   * Representa a descri��o da  nota adicionada.
   */
    protected String strAddNote;
  /*
   * Representa a descri��o do in�cio da altera��o.
   */
    protected String strAlterationBeginning;
  /*
   * Representa a descri��o do final da altera��o.
   */
    protected String strAlterationEnding;
  /*
   * Representa a descri��o da jun��o do intervalo.
   */
    protected String strIntervalJunction;
  /*
   * Representa a descri��o da invers�o.
   */
    protected String strInversion;
  /*
   * Representa a descri��o da suspens�o.
   */
    protected String strSuspension;

  /*
   * Inicializa as propriedades da Nota��o Cifra com os valores obtidos de um arquivo.
   * @param arquivo Fonte de informa��es necess�rias para a inicializa��o das propiedades.
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

        //Pegando as altera��es
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
        // protected SimboloCifra acordeTetrade = new SimboloCifra("7","setima", "Acorde com s�tima") ;

        // Pegando os outros s�mbolos
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
   * Obt�m uma rela��o com os s�mbolos das notas.
   * @return Rela��o com a nota��o para as notas.
   */
    protected String[] getNoteSymbols() {return strNotes;};

  /*
   * Obt�m uma rela��o com a nota��o para os intervalos.
   * @return Rela��o com a nota��o para os intervalos.
   */
    protected String[] getIntervalSymbols(){return strIntervals;};

  /*
   * Obt�m uma rela��o com a nota��o para as altera��es.
   * Os s�mbolos utilizados pelo sistema s�o " # " e " b ".
   * @return Rela��o com a nota��o para as altera��es.
   */
    protected String[] getAlterationSymbols(){ return strAlterations;};

  /*
   * Obt�m a nota��o para o acorde diminuto.
   * O simbolo utilizado pelo sistema � " � ".
   * @return Nota��o para o acorde diminuto.
   */
    protected String getDiminishedChordSymbol() { return strDiminishedChord;};

  /*
   * Obt�m a nota��o para o acorde for�a.
   * O s�mbolo utilizado pelo sistema � " 5 ".
   * @return Nota��o para o acorde for�a.
   */
    protected String getPowerChordSymbol() {return strPowerChord;};

  /*
   * Obt�m a nota��o para o acorde com nota Adicionada.
   * O s�mbolo utilizado pelo sistema � " add ".
   * @return Nota��o para o acorde com nota Adicionada.
   */
    protected String getAddNoteSymbol() {return strAddNote;};

  /*
   * Obt�m a nota��o para o in�cio de altera��o.
   * O s�mbolo utilizado pelo sistema � " ( ".
   * @return Nota��o para in�cio de altera��o.
   */
    protected String getAlterationBeginningSymbol() { return strAlterationBeginning;};

  /*
   * Obt�m a nota��o para o final de altera��o.
   * O s�mbolo utilizado pelo sistema � " ) ".
   * @return Nota��o para o final de altera��o.
   */
    protected String getAlterationEndingSymbol() { return strAlterationEnding;};

  /*
   * Obt�m a nota��o para a jun��o de intervalos.
   * O s�mbolo utilizado pelo sistema � " ^ ".
   * @return Nota��o para a jun��o do intervalo.
   */
    protected String getIntervalJunctionSymbol() { return strIntervalJunction;};

  /*
   * Obt�m a nota��o para invers�o (baixo).
   * O s�mbolo utilizado pelo sistema � " / ".
   * @return Nota��o para o s�mbolo invers�o.
   */
    protected String getInvertionSymbol() { return strInversion;};

  /*
   * Obt�m a nota��o para suspens�o.
   * O s�mbolo utilizado pelo sistema � " sus ".
   * @return Nota��o para o s�mbolo suspens�o.
   */
    protected String getSuspensionSymbol() { return strSuspension;};
  }


}
