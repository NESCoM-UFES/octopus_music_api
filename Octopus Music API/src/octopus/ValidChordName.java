package octopus;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * The chord name verified according the current ChordNotation;
 * 
 * @see IntervalCollection#getIntervals()
 * @see IntervalCollection#getInterval(String)
 * @see IntervalCollection
 * @see IntervalSymbol
 * @see NoteSymbol
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */

 class ValidChordName implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private NoteSymbol rootNome;
  private NoteSymbol bassNote;
  @SuppressWarnings("unused")
private String description;
  private ArrayList<Interval> intervals;
  protected String chordName;

/*
   * Instancia a rela��o de intervalos na chordName.
   */
  protected ValidChordName() {
    intervals = new ArrayList<Interval>();
  }

/*
   * Cria uma nova chordName v�lida inicializando sua nota fundamental e sua nota
   * baixo, instancia ainda a rela��o de intervalos da chordName. Neste construtor a
   * nota fundamental e a nota baixo s�o inicializadas com o mesmo valor passado
   * como argumento.
   * @param fundamental � a nota que servir� de base para a forma��o do acorde.
   */
  protected ValidChordName(NoteSymbol fundamental) {
    rootNome = fundamental;
    bassNote = fundamental;
    intervals = new ArrayList<Interval>();
  }

/*
   * Cria uma nova chordName v�lida inicializando sua nota fundamental e sua nota
   * baixo, instancia ainda a rela��o de intervalos da chordName. Neste construtor a
   * nota fundamental e a nota baixo s�o inicializadas com os valores
   * passados como argumentos.
   * @param fundamental � a nota que servir� de base para a forma��o do acorde.
   * @param baixo � a nota que ser� a mais grave quando o acorde for formado.
   */
  protected ValidChordName(NoteSymbol fundamental, NoteSymbol bass) {
    rootNome = fundamental;
    bassNote = bass;
    intervals = new ArrayList<Interval>();
  }

/*
   * Obt�m a rela��o de intervalos da chordName. Esses intervalos junto com a fundamental
   * definir�o as notas do acorde.
   * @return A rela��o de intervalos da chordName.
   */
  protected Interval[] getIntervals() {
    return (Interval[]) intervals.toArray(new Interval[0]);
  }

/*
   * Obt�m o intervalo, da rela��o de intervalos da chordName, correspondente a uma pos
   * especificada no par�metro.
   * @param index pos de onde se deseja obter o intervalo.
   * @return Interval da pos especificada no par�metro.
   */
  protected Interval getInterval(int index) {
    return ((Interval)intervals.get(index));
  }

/*
   * Obt�m a quantidade de intervalos da rela��o de intervalos da chordName.
   * Teoricamente a quantidade m�nima de intervalos para uma chordName v�lida � tr�s.
   * Exemplo1: C = [fundamental, ter�a maior, quinta justa].
   * E a quantidade m�xima � limitada pela representa��o da chordName.
   * Exemplo2: C7(9^b11^13)/D = [fundamental, ter�a maior, quinta justa, s�tima menor,
   * nona maior, d�cima primeira menor, d�cima terceira justa].
   * Note que o baixo n�o interfere na quantidade de intervalos da chordName.
   * Note tamb�m que o intervalo fundamental � o intervalo de primeira justa,
   * que serve neste caso s� para definir a nota fundamental.
   * @return Quantidade de intervalos.
   */
  protected int getIntervalsSize () {
    return intervals.size();
  }

/*
 * Inicializa a nota fundamental.
 * @param nota Menor unidade funcional na m�sica utilizada para forma��o do
 * Acorde.
 */
  protected void setRootNote(NoteSymbol note) {
    rootNome = note;
  }

/*
 * Obt�m a nota fundamental.
 * @return A nota que serve como base para a constru��o da rela��o de notas do Acorde.
 */
  protected NoteSymbol getRootNote() {return rootNome;}

/*
 * Inicializa o baixo.
 * @param nota Menor unidade funcional na m�sica utilizada para forma��o do
 * Acorde.
 */
  protected void setBassNote(NoteSymbol note) {
    bassNote = note;
  }

/*
   * Determina qual a chordName (String) deu origem a cifraValida.
   * @param chordName String que representa a ValidChordName gerada.
   */
  protected void setChordName(String chordName) {
    this.chordName = chordName;
  }

 /*
   * Determina qual a chordName (texto) deu origem a cifraValida.
   * @return A chordName (texto).
   */
  protected String getChordName() {
    return chordName;
  }


/*
 * Obt�m a nota baixo.
 * @return Nota que representa o baixo do acorde.
 */
  protected NoteSymbol getBassNote() {return bassNote;}

// Interval Gen�rico
// Os dois m�todos seguintes: addInterval(Interval)
// e addInterval(IntervalSymbol) n�o est�o sendo usados. Foram substitu�dos
// satisfatoriamente por addInterval(int, IntervalSymbol).

/*
 * Adiciona um intervalo na rela��o de intervalos da cifra.
 * Dever�o ser adicionados apenas os intervalos instanciados na rela��o de intervalos da cifra.
 * @param intervalo Intervalo a ser adicionado.
 */
//  protected void addInterval(Interval intervalo) {
//    intervalos.add(intervalo);
//  }

/*
 * Adiciona um intervalo na rela��o de intervalos da cifra.
 * @param intervalo Intervalo a ser adicionado.
 */
//  protected void addInterval(IntervalSymbol intervalo) {
//    intervalos.add(intervalo);
//  }

/*
   * Adiciona um intervalo na rela��o de intervalos da chordName em uma pos espec�fica.
   * Para adicionar no final da rela��o o argumento para pos deve ser -1.
   * @param pos pos na rela��o de intervalos da chordName onde o intervalo ser� adicionado.
   * @param intervalo Interval a ser adicionado.
   */
  protected void addInterval(int pos, IntervalSymbol interval) {
    if (pos == -1) {
      intervals.add(interval.getInterval());
    }
    else {
      intervals.add(pos, interval.getInterval());
    }
  }

/*
   * Inicializa um intervalo na pos especificada pelo par�metro, substituindo o valor
   * antigo, na rela��o de intervalos da chordName.
   * @param pos pos na rela��o de intervalos da chordName onde o intervalo ser� armazenado.
   * @param intervalo Interval a ser armazenado.
   */
  protected void setInterval(int pos, Interval interval) {
    intervals.set(pos, interval);
  }

/*
   * Remove a primeira ocorr�ncia do argumento passado da rela��o de intervalos da chordName.
   * @param intervalo Interval a ser removido.
   */
  protected void removeInterval(IntervalSymbol interval) {
    intervals.remove(intervals.indexOf(interval));
  }

/*
   * Remove o intervalo, na pos especificada no par�metro, da rela��o de intervalos da chordName.
   * @param pos pos do intervalo a ser removido.
   */
  protected void removeInterval(int pos) {
    intervals.remove(pos);
  }

// Coment�rio -  N�o � necess�rio o intervalo fundamental (primeira justa) neste contexto (ValidChordName).
// O intervalo fundamental (primeira justa) � usado para gerar a nota fundamental, na classe AcordeMaker.
// A rela��o de intervalos da chordName � montada para que a classe AcordeMaker possa
// gerar as notas do acorde atrav�s da nota fundamental e dos intervalos que ser�o
// instanciados em CifraValidaMaker. Como os par�metros de ValidChordName s�o
// Nota fundamental, Nota Baixo e Rela��o de intervalos, a rela��o de intervalos
// n�o necessita de um intervalo para gerar a nota fundamental, pois a pr�pria
// (a nota fundamental) j� � um atributo da classe.
// Exemplo:
// C/E => nota fundamental      = d�
//        nota baixo            = mi
//        rela��o de intervalos = [fundamental(primeira justa), ter�a maior, quinta justa]
// Para que a classe AcordeMaker gere as notas do acorde s� � preciso a
// nota fundamental e os intervalos ter�a maior e quinta justa.
//

// Fundamental
/*
   * Adiciona o intervalo fundamental (primeira justa) na rela��o de intervalos da chordName.
   */
  protected void addRoot() {
    intervals.add(IntervalFactory.getRoot());
  }

/*
   * Remove o intervalo fundamental (primeira justa) da rela��o de intervalos da chordName.
   */
  protected void removeRoot(){
    intervals.remove(intervals.indexOf(IntervalFactory.getRoot()));
  }

// Segunda Menor
/*
   * Adiciona o intervalo Segunda Menor na rela��o de intervalos da chordName.
   */
  protected void addMinorSecond(){
    intervals.add(IntervalFactory.getMinorSecond());
  }

/*
   * Adiciona o intervalo Segunda Menor, na pos especificada pelo par�metro,
   * na rela��o de intervalos da chordName.
   * @param indexVet Especifica a pos, na rela��o de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addMinorSecond(int indexVet){
    intervals.add(indexVet, IntervalFactory.getMinorSecond());
  }

/*
   * Remove o intervalo Segunda Menor da rela��o de intervalos da chordName.
   */
  protected void removeMinorSecond() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorSecond()));
  }

// Segunda Maior
/*
   * Adiciona o intervalo Segunda Maior na rela��o de intervalos da chordName.
   */
  protected void addMajorSecond() {
    intervals.add(IntervalFactory.getMajorSecond());
  }

/*
   * Remove o intervalo Segunda Maior da rela��o de intervalos da chordName.
   */
  protected void removeMajorSecond() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajorSecond()));
  }

// Segunda Aumentada
/*
   * Adiciona o intervalo Segunda Aumentada na rela��o de intervalos da chordName.
   */
  protected void addAugmentedSecond() {
    intervals.add(IntervalFactory.getAugSecond());
  }

/*
   * Adiciona o intervalo Segunda Aumentada, na pos especificada pelo par�metro,
   * na rela��o de intervalos da chordName.
   * @param indexVet Especifica a pos, na rela��o de intervalos da chordName, onde
   * o intervalo deve ser adicionado.
   */
  protected void addAugmentedSecond(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getAugSecond());
  }

/*
   * Remove o intervalo Segunda Aumentada da rela��o de intervalos da chordName.
   */
  protected void removeAugmentedSecond() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAugSecond()));
  }

// Ter�a Menor
/*
   * Adiciona o intervalo Ter�a Menor na rela��o de intervalos da chordName.
   */
  protected void addMinorThird() {
    intervals.add(IntervalFactory.getMinorThird());
  }

/*
   * Remove o intervalo Ter�a Menor da rela��o de intervalos da chordName.
   */
  protected void removeMinorThird() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorThird()));
  }

// Ter�a Maior
/*
   * Adiciona o intervalo Ter�a Maior na rela��o de intervalos da chordName.
   */
  protected void addMajorThird() {
    intervals.add(IntervalFactory.getMajorThird());
  }

/*
   * Remove o intervalo Ter�a Maior da rela��o de intervalos da chordName.
   */
  protected void removeMajorThird() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajorThird()));
  }

/*
   * Substitui o intervalo Ter�a Maior pelo intervalo Segunda Maior
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   *
*  @todo verificar necessidade*/
/*  protected void changeMajoThirdrToMajorSecond (int pos) {
    intervals.set(pos, IntervalCollection.getMajorSecond());
  }*/

/*
   * Substitui o intervalo Ter�a Maior pelo intervalo Ter�a Menor
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
  protected void changeMajorThirdToMinor(int pos) {
    intervals.set(pos, IntervalFactory.getMinorThird());
  }

/*
   * Substitui o intervalo Ter�a Maior pelo intervalo Quarta Justa
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
/*  protected void change3MajorTo4Perfect (int pos) {
    intervals.set(pos, IntervalCollection.getPerfectFourth());
  }*/

/*
   * Substitui o intervalo Ter�a Maior pelo intervalo Quinta Justa
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
/*  protected void change3MajorTo5Perfect (int pos) {
    intervals.set(pos, IntervalCollection.getPerfectFifth());
  }*/

/*
   * Substitui o intervalo Ter�a Maior pelo intervalo Nona Maior
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
/* protected void change3MajorTo9Major (int pos) {
    intervals.set(pos, IntervalCollection.getMajorNinth());
  }*/

/**
   * Substitui o intervalo Ter�a Maior pelo intervalo D�cima Primeira Justa
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
/*  protected void change3MajorTo11Perfect (int pos) {
    intervals.set(pos, IntervalCollection.getPerfect11th());
  }*/

/*
   * Substitui o intervalo Ter�a Maior pelo intervalo D�cima Segunda Justa
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
  protected void changeMajorThirdToPerfect12th (int pos) {
    intervals.set(pos, IntervalFactory.getPerfect12th());
  }

// Quarta Diminuta
/*
   * Adiciona o intervalo Quarta Diminuta, na pos especificada pelo par�metro,
   * na rela��o de intervalos da chordName.
   * @param indexVet Especifica a pos, na rela��o de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addDiminishedFourth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getDimFourth());
  }

// Quarta Justa
/*
   * Adiciona o intervalo Quarta Justa na rela��o de intervalos da chordName.
   */
  protected void addPerfectFourth() {
    intervals.add(IntervalFactory.getPerfectFourth());
  }

/*
   * Remove o intervalo Quarta Justa da rela��o de intervalos da chordName.
   */
  protected void removePerfectFourth(){
    intervals.remove(intervals.indexOf(IntervalFactory.getPerfectFourth()));
  }

// Quarta Aumentada
/*
   * Adiciona o intervalo Quarta Aumentada na rela��o de intervalos da chordName.
   */
  protected void addAugmentedFourth(){
    intervals.add(IntervalFactory.getAugFourth());
  }

/*
   * Adiciona o intervalo Quarta Aumentada, na pos especificada pelo par�metro,
   * na rela��o de intervalos da chordName.
   * @param indexVet Especifica a pos, na rela��o de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addAugmentedFourth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getAugFourth());
  }

/*
   * Remove o intervalo Quarta Aumentada da rela��o de intervalos da chordName.
   */
  protected void removeAugmentedFourth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAugFourth()));
  }

// Quinta Diminuta
/*
   * Adiciona o intervalo Quinta Diminuta na rela��o de intervalos da chordName.
   */
  protected void addDiminishedFifth() {
    intervals.add(IntervalFactory.getDimFifth());
  }

/*
   * Remove o intervalo Quinta Diminuta da rela��o de intervalos da chordName.
   */
  protected void removeDiminishedFifth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getDimFifth()));
  }

// Quinta Justa
/*
   * Adiciona o intervalo Quinta Justa na rela��o de intervalos da chordName.
   */
  protected void addPerfectFifth() {
    intervals.add(IntervalFactory.getPerfectFifth());
  }

/*
   * Remove o intervalo Quinta Justa da rela��o de intervalos da chordName.
   */
  protected void removePerfectFifth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getPerfectFifth()));;
  }

/*
   * Substitui o intervalo Quinta Justa pelo intervalo Quinta Diminuta
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
  protected void changePerfectFifthToDiminished (int pos) {
    intervals.set(pos, IntervalFactory.getDimFifth());
  }

/*
   * Substitui o intervalo Quinta Justa pelo intervalo Quinta Aumentada
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
  protected void changePerfectFifthToAugmented (int pos) {
    intervals.set(pos, IntervalFactory.getAugFifth());
  }

/*
   * Substitui o intervalo Quinta Justa pelo intervalo Nona Maior
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
/*  protected void change5PerfectTo9Major(int pos) {
    intervals.set(pos, IntervalCollection.getMajorNinth());
  }*/

/*
   * Substitui o intervalo Quinta Justa pelo intervalo D�cima Primeira Justa
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
/*  protected void change5PerfectTo11Perfect (int pos) {
    intervals.set(pos, IntervalCollection.getPerfect11th());
  }*/

// Quinta Aumentada
/*
   * Adiciona o intervalo Quinta Aumentada na rela��o de intervalos da chordName.
   */
  protected void addAugmentedFifth() {
    intervals.add(IntervalFactory.getAugFifth());
  }

/*
   * Remove o intervalo Quinta Aumentada da rela��o de intervalos da chordName.
   */
  protected void removeAugmentedFifth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAugFifth()));;
  }

// Sexta Menor
/*
   * Adiciona o intervalo Sexta Menor na rela��o de intervalos da chordName.
   */
  protected void addMinorSixth() {
    intervals.add(IntervalFactory.getMinorSixth());
  }

/*
   * Adiciona o intervalo Sexta Menor, na pos especificada pelo par�metro,
   * na rela��o de intervalos da chordName.
   * @param indexVet Especifica a pos, na rela��o de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addMinorSixth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getMinorSixth());
  }

/*
   * Remove o intervalo Sexta Menor da rela��o de intervalos da chordName.
   */
  protected void removeMinorSixth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorSixth()));
  }

// Sexta Maior
/*
   * Adiciona o intervalo Sexta Maior na rela��o de intervalos da chordName.
   */
  protected void addMajorSixth() {
    intervals.add(IntervalFactory.getMajorSixth());
  }


// Sexta Aumentada
/*
   * Adiciona o intervalo Sexta Aumentada, na pos especificada pelo par�metro,
   * na rela��o de intervalos da chordName.
   * @param indexVet Especifica a pos, na rela��o de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addAugmentedSixth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getAugSixth());
  }

// Setima Diminuta
/*
   * Adiciona o intervalo S�tima Diminuta na rela��o de intervalos da chordName.
   */
  protected void addDiminishedSeventh() {
    intervals.add(IntervalFactory.getDimSeventh());
  }

/*
   * Remove o intervalo S�tima Diminuta da rela��o de intervalos da chordName.
   */
  protected void removeDiminishedSeventh() {
    intervals.remove(intervals.indexOf(IntervalFactory.getDimSeventh()));;
  }

// Setima Menor
/*
   * Adiciona o intervalo S�tima Menor na rela��o de intervalos da chordName.
   */
  protected void addMinorSeventh() {
    intervals.add(IntervalFactory.getMinorSeventh());
  }

/*
   * Remove o intervalo S�tima Menor da rela��o de intervalos da chordName.
   */
  protected void removeMajorSeventh() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorSeventh()));;
  }

// Setima Maior
/*
 * Adiciona o intervalo S�tima Maior na rela��o de intervalos.
 */
  protected void addMajorSeventh() {
    intervals.add(IntervalFactory.getMajorSeventh());
  }

/*
   * Adiciona o intervalo S�tima Maior, na pos especificada pelo par�metro,
   * na rela��o de intervalos da chordName.
   * @param indexVet Especifica a pos, na rela��o de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addMajorSeventh(int indexVet) {
    intervals.set(indexVet, IntervalFactory.getMajorSeventh());
  }

/*
   * Remove o intervalo S�tima Maior da rela��o de intervalos da chordName.
   */
  protected void removerSetimaMaior() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajorSeventh()));;
  }

// Nona Menor
/*
   * Adiciona o intervalo Nona Menor na rela��o de intervalos da chordName.
   */
  protected void addMinorNinth() {
    intervals.add(IntervalFactory.getMinorNinth());
  }

/*
   * Remove o intervalo Nona Menor da rela��o de intervalos da chordName.
   */
  protected void removeMinorNinth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorNinth()));;
  }

// Nona Maior
/*
   * Adiciona o intervalo Nona Maior na pos especificada pelo par�metro
   * na rela��o de intervalos da chordName.
   * @param indexVet Especifica a pos, na rela��o de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addMajorNinth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getMajorNinth());
  }

/*
   * Adiciona o intervalo Nona Maior na rela��o de intervalos da chordName.
   */
  protected void addMajorNinth() {
    intervals.add(IntervalFactory.getMajorNinth());
  }

/*
   * Remove o intervalo Nona Maior da rela��o de intervalos da chordName.
   */
  protected void removeMajorNinth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajorNinth()));;
  }

// Nona Aumentada
/*
   * Adiciona o intervalo Nona Aumentada na rela��o de intervalos da chordName.
   */
  protected void addAugmentedNinth() {
    intervals.add(IntervalFactory.getAugNinth());
  }

/*
   * Remove o intervalo Nona Aumentada da rela��o de intervalos da chordName.
   */
  protected void removeAugmentedNinth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAugNinth()));;
  }

// DecimaPrimeira Diminuta
/*
   * Adiciona o intervalo D�cima Primeira Diminuta na rela��o de intervalos da chordName.
   */
  protected void addDiminished11th() {
    intervals.add(IntervalFactory.getDim11th());
  }

/*
   * Remove o intervalo D�cima Primeira Diminuta da rela��o de intervalos da chordName.
   */
  protected void removeDiminished11th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getDim11th()));
  }

// DecimaPrimeira Justa
/*
   * Adiciona o intervalo D�cima Primeira Justa na rela��o de intervalos da chordName.
   */
  protected void addPerfect11th() {
    intervals.add(IntervalFactory.getPerfect11th());
  }

/*
   * Adiciona o intervalo D�cima Primeira Justa na pos especificada pelo par�metro
   * na rela��o de intervalos da chordName.
   * @param indexVet Especifica a pos, na rela��o de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  /*protected void add11Perfect(int indexVet) {
    intervalos.add(indexVet, IntervalCollection.getDecimaPrimeiraJusta());
  }*/

/*
   * Remove o intervalo D�cima Primeira Justa da rela��o de intervalos da chordName.
   */
  protected void removePerfect11th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getPerfect11th()));;
  }

// DecimaPrimeira Aumentada
/*
   * Adiciona o intervalo D�cima Primeira Aumentada na rela��o de intervalos da chordName.
   */
  protected void addAugmented11th() {
    intervals.add(IntervalFactory.getAug11th());
  }

/*
   * Remove o intervalo D�cima Primeira Aumentada da rela��o de intervalos da chordName.
   */
  protected void removeAugmented11th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAug11th()));;
  }

// DecimaSegunda Justa
/*
   * Substitui o intervalo D�cima Segunda Justa pelo intervalo D�cima Segunda Diminuta
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
  protected void changePerfect12thToDiminished(int pos) {
    intervals.set(pos, IntervalFactory.getDim12th());
  }

/*
   * Substitui o intervalo D�cima Segunda Justa pelo intervalo D�cima Segunda Aumentada
   * na rela��o de intervalos da chordName.
   * @param pos pos, na rela��o de intervalos da chordName, do intervalo a ser substitu�do.
   */
  protected void changePerfec12thtToAugmented(int pos) {
    intervals.set(pos, IntervalFactory.getAug12th());
  }

// DecimaTerceira Menor
/*
   * Adiciona o intervalo D�cima Terceira Menor na rela��o de intervalos da chordName.
   */
  protected void addMinor13th() {
    intervals.add(IntervalFactory.getMinor13th());
  }

/*
   * Remove o intervalo D�cima Terceira Menor da rela��o de intervalos da chordName.
   */
  protected void removeMinor13th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinor13th()));;
  }

// DecimaTerceira Maior
/*
   * Adiciona o intervalo D�cima Terceira Maior na rela��o de intervalos da chordName.
   */
  protected void addMajor13th() {
    intervals.add(IntervalFactory.getMajor13th());
  }

/*
   * Remove o intervalo D�cima Terceira Maior da rela��o de intervalos da chordName.
   */
  protected void removeMajor13th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajor13th()));;
  }

// DecimaTerceira Aumentada
/*
   * Adiciona o intervalo D�cima Terceira Aumentada na rela��o de intervalos da chordName.
   */
  protected void addAugmented13th() {
    intervals.add(IntervalFactory.getAug13th());
  }

/*
   * Ordena a rela��o de intervalos da chordName.
   */
  protected void sortIntervalsVector() {
    int ind2 = getIntervals().length - 1;
    int ind1 = ind2 - 1;
    Interval intervaloAux;
    while (ind2 > 0 ) {
      while (ind1 >= 0) {
        if(this.getInterval(ind1).getNumericInterval() > this.getInterval(ind2).getNumericInterval()) {
          intervaloAux = getInterval(ind1);
          this.setInterval(ind1,getInterval(ind2));
          this.setInterval(ind2,intervaloAux);
        }
        ind1--;
      }
      ind2--;
      ind1 = ind2 - 1;
    }
  }

/*
   * Verifica se o intervalo pertence a rela��o de intervalos da chordName.
   * @param intervaloCifraValida Interval a ser verificado.
   * @return True quando o intervalo for encontrado, False caso contr�rio.
   */
  protected boolean containsInterval(Interval interval){
    return intervals.contains(interval);
  }

/*
   * Verifica se existem intervalos iguais na chordName, ou seja, intervalos com
   * mesma quantidade de semitons.
   * @return True quando existir intervalos iguais, False caso contr�rio.
   */
  protected boolean containsDuplicatedIntervals () {
    boolean achou = false;
    int quantidadeSemitonsIndex1;
    int quantidadeSemitonsIndex2;
    for (int i = 0; i < intervals.size() && !achou; i++) {
      for (int j = i + 1; j < intervals.size() && !achou; j++) {
        quantidadeSemitonsIndex1 = ((Interval)intervals.get(i)).getDistanceFromRoot();
        quantidadeSemitonsIndex2 = ((Interval)intervals.get(j)).getDistanceFromRoot();
        achou = quantidadeSemitonsIndex1 == quantidadeSemitonsIndex2 ? true : false;
      }
    }
    return achou;
  }

}
