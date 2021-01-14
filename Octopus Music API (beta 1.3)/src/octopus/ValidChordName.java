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
   * Instancia a relação de intervalos na chordName.
   */
  protected ValidChordName() {
    intervals = new ArrayList<Interval>();
  }

/*
   * Cria uma nova chordName válida inicializando sua nota fundamental e sua nota
   * baixo, instancia ainda a relação de intervalos da chordName. Neste construtor a
   * nota fundamental e a nota baixo são inicializadas com o mesmo valor passado
   * como argumento.
   * @param fundamental É a nota que servirá de base para a formação do acorde.
   */
  protected ValidChordName(NoteSymbol fundamental) {
    rootNome = fundamental;
    bassNote = fundamental;
    intervals = new ArrayList<Interval>();
  }

/*
   * Cria uma nova chordName válida inicializando sua nota fundamental e sua nota
   * baixo, instancia ainda a relação de intervalos da chordName. Neste construtor a
   * nota fundamental e a nota baixo são inicializadas com os valores
   * passados como argumentos.
   * @param fundamental É a nota que servirá de base para a formação do acorde.
   * @param baixo É a nota que será a mais grave quando o acorde for formado.
   */
  protected ValidChordName(NoteSymbol fundamental, NoteSymbol bass) {
    rootNome = fundamental;
    bassNote = bass;
    intervals = new ArrayList<Interval>();
  }

/*
   * Obtém a relação de intervalos da chordName. Esses intervalos junto com a fundamental
   * definirão as notas do acorde.
   * @return A relação de intervalos da chordName.
   */
  protected Interval[] getIntervals() {
    return (Interval[]) intervals.toArray(new Interval[0]);
  }

/*
   * Obtém o intervalo, da relação de intervalos da chordName, correspondente a uma pos
   * especificada no parâmetro.
   * @param index pos de onde se deseja obter o intervalo.
   * @return Interval da pos especificada no parâmetro.
   */
  protected Interval getInterval(int index) {
    return ((Interval)intervals.get(index));
  }

/*
   * Obtém a quantidade de intervalos da relação de intervalos da chordName.
   * Teoricamente a quantidade mínima de intervalos para uma chordName válida é três.
   * Exemplo1: C = [fundamental, terça maior, quinta justa].
   * E a quantidade máxima é limitada pela representação da chordName.
   * Exemplo2: C7(9^b11^13)/D = [fundamental, terça maior, quinta justa, sétima menor,
   * nona maior, décima primeira menor, décima terceira justa].
   * Note que o baixo não interfere na quantidade de intervalos da chordName.
   * Note também que o intervalo fundamental é o intervalo de primeira justa,
   * que serve neste caso só para definir a nota fundamental.
   * @return Quantidade de intervalos.
   */
  protected int getIntervalsSize () {
    return intervals.size();
  }

/*
 * Inicializa a nota fundamental.
 * @param nota Menor unidade funcional na música utilizada para formação do
 * Acorde.
 */
  protected void setRootNote(NoteSymbol note) {
    rootNome = note;
  }

/*
 * Obtém a nota fundamental.
 * @return A nota que serve como base para a construção da relação de notas do Acorde.
 */
  protected NoteSymbol getRootNote() {return rootNome;}

/*
 * Inicializa o baixo.
 * @param nota Menor unidade funcional na música utilizada para formação do
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
 * Obtém a nota baixo.
 * @return Nota que representa o baixo do acorde.
 */
  protected NoteSymbol getBassNote() {return bassNote;}

// Interval Genérico
// Os dois métodos seguintes: addInterval(Interval)
// e addInterval(IntervalSymbol) não estão sendo usados. Foram substituídos
// satisfatoriamente por addInterval(int, IntervalSymbol).

/*
 * Adiciona um intervalo na relação de intervalos da cifra.
 * Deverão ser adicionados apenas os intervalos instanciados na relação de intervalos da cifra.
 * @param intervalo Intervalo a ser adicionado.
 */
//  protected void addInterval(Interval intervalo) {
//    intervalos.add(intervalo);
//  }

/*
 * Adiciona um intervalo na relação de intervalos da cifra.
 * @param intervalo Intervalo a ser adicionado.
 */
//  protected void addInterval(IntervalSymbol intervalo) {
//    intervalos.add(intervalo);
//  }

/*
   * Adiciona um intervalo na relação de intervalos da chordName em uma pos específica.
   * Para adicionar no final da relação o argumento para pos deve ser -1.
   * @param pos pos na relação de intervalos da chordName onde o intervalo será adicionado.
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
   * Inicializa um intervalo na pos especificada pelo parâmetro, substituindo o valor
   * antigo, na relação de intervalos da chordName.
   * @param pos pos na relação de intervalos da chordName onde o intervalo será armazenado.
   * @param intervalo Interval a ser armazenado.
   */
  protected void setInterval(int pos, Interval interval) {
    intervals.set(pos, interval);
  }

/*
   * Remove a primeira ocorrência do argumento passado da relação de intervalos da chordName.
   * @param intervalo Interval a ser removido.
   */
  protected void removeInterval(IntervalSymbol interval) {
    intervals.remove(intervals.indexOf(interval));
  }

/*
   * Remove o intervalo, na pos especificada no parâmetro, da relação de intervalos da chordName.
   * @param pos pos do intervalo a ser removido.
   */
  protected void removeInterval(int pos) {
    intervals.remove(pos);
  }

// Comentário -  Não é necessário o intervalo fundamental (primeira justa) neste contexto (ValidChordName).
// O intervalo fundamental (primeira justa) é usado para gerar a nota fundamental, na classe AcordeMaker.
// A relação de intervalos da chordName é montada para que a classe AcordeMaker possa
// gerar as notas do acorde através da nota fundamental e dos intervalos que serão
// instanciados em CifraValidaMaker. Como os parâmetros de ValidChordName são
// Nota fundamental, Nota Baixo e Relação de intervalos, a relação de intervalos
// não necessita de um intervalo para gerar a nota fundamental, pois a própria
// (a nota fundamental) já é um atributo da classe.
// Exemplo:
// C/E => nota fundamental      = dó
//        nota baixo            = mi
//        relação de intervalos = [fundamental(primeira justa), terça maior, quinta justa]
// Para que a classe AcordeMaker gere as notas do acorde só é preciso a
// nota fundamental e os intervalos terça maior e quinta justa.
//

// Fundamental
/*
   * Adiciona o intervalo fundamental (primeira justa) na relação de intervalos da chordName.
   */
  protected void addRoot() {
    intervals.add(IntervalFactory.getRoot());
  }

/*
   * Remove o intervalo fundamental (primeira justa) da relação de intervalos da chordName.
   */
  protected void removeRoot(){
    intervals.remove(intervals.indexOf(IntervalFactory.getRoot()));
  }

// Segunda Menor
/*
   * Adiciona o intervalo Segunda Menor na relação de intervalos da chordName.
   */
  protected void addMinorSecond(){
    intervals.add(IntervalFactory.getMinorSecond());
  }

/*
   * Adiciona o intervalo Segunda Menor, na pos especificada pelo parâmetro,
   * na relação de intervalos da chordName.
   * @param indexVet Especifica a pos, na relação de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addMinorSecond(int indexVet){
    intervals.add(indexVet, IntervalFactory.getMinorSecond());
  }

/*
   * Remove o intervalo Segunda Menor da relação de intervalos da chordName.
   */
  protected void removeMinorSecond() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorSecond()));
  }

// Segunda Maior
/*
   * Adiciona o intervalo Segunda Maior na relação de intervalos da chordName.
   */
  protected void addMajorSecond() {
    intervals.add(IntervalFactory.getMajorSecond());
  }

/*
   * Remove o intervalo Segunda Maior da relação de intervalos da chordName.
   */
  protected void removeMajorSecond() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajorSecond()));
  }

// Segunda Aumentada
/*
   * Adiciona o intervalo Segunda Aumentada na relação de intervalos da chordName.
   */
  protected void addAugmentedSecond() {
    intervals.add(IntervalFactory.getAugSecond());
  }

/*
   * Adiciona o intervalo Segunda Aumentada, na pos especificada pelo parâmetro,
   * na relação de intervalos da chordName.
   * @param indexVet Especifica a pos, na relação de intervalos da chordName, onde
   * o intervalo deve ser adicionado.
   */
  protected void addAugmentedSecond(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getAugSecond());
  }

/*
   * Remove o intervalo Segunda Aumentada da relação de intervalos da chordName.
   */
  protected void removeAugmentedSecond() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAugSecond()));
  }

// Terça Menor
/*
   * Adiciona o intervalo Terça Menor na relação de intervalos da chordName.
   */
  protected void addMinorThird() {
    intervals.add(IntervalFactory.getMinorThird());
  }

/*
   * Remove o intervalo Terça Menor da relação de intervalos da chordName.
   */
  protected void removeMinorThird() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorThird()));
  }

// Terça Maior
/*
   * Adiciona o intervalo Terça Maior na relação de intervalos da chordName.
   */
  protected void addMajorThird() {
    intervals.add(IntervalFactory.getMajorThird());
  }

/*
   * Remove o intervalo Terça Maior da relação de intervalos da chordName.
   */
  protected void removeMajorThird() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajorThird()));
  }

/*
   * Substitui o intervalo Terça Maior pelo intervalo Segunda Maior
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   *
*  @todo verificar necessidade*/
/*  protected void changeMajoThirdrToMajorSecond (int pos) {
    intervals.set(pos, IntervalCollection.getMajorSecond());
  }*/

/*
   * Substitui o intervalo Terça Maior pelo intervalo Terça Menor
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
  protected void changeMajorThirdToMinor(int pos) {
    intervals.set(pos, IntervalFactory.getMinorThird());
  }

/*
   * Substitui o intervalo Terça Maior pelo intervalo Quarta Justa
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
/*  protected void change3MajorTo4Perfect (int pos) {
    intervals.set(pos, IntervalCollection.getPerfectFourth());
  }*/

/*
   * Substitui o intervalo Terça Maior pelo intervalo Quinta Justa
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
/*  protected void change3MajorTo5Perfect (int pos) {
    intervals.set(pos, IntervalCollection.getPerfectFifth());
  }*/

/*
   * Substitui o intervalo Terça Maior pelo intervalo Nona Maior
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
/* protected void change3MajorTo9Major (int pos) {
    intervals.set(pos, IntervalCollection.getMajorNinth());
  }*/

/**
   * Substitui o intervalo Terça Maior pelo intervalo Décima Primeira Justa
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
/*  protected void change3MajorTo11Perfect (int pos) {
    intervals.set(pos, IntervalCollection.getPerfect11th());
  }*/

/*
   * Substitui o intervalo Terça Maior pelo intervalo Décima Segunda Justa
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
  protected void changeMajorThirdToPerfect12th (int pos) {
    intervals.set(pos, IntervalFactory.getPerfect12th());
  }

// Quarta Diminuta
/*
   * Adiciona o intervalo Quarta Diminuta, na pos especificada pelo parâmetro,
   * na relação de intervalos da chordName.
   * @param indexVet Especifica a pos, na relação de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addDiminishedFourth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getDimFourth());
  }

// Quarta Justa
/*
   * Adiciona o intervalo Quarta Justa na relação de intervalos da chordName.
   */
  protected void addPerfectFourth() {
    intervals.add(IntervalFactory.getPerfectFourth());
  }

/*
   * Remove o intervalo Quarta Justa da relação de intervalos da chordName.
   */
  protected void removePerfectFourth(){
    intervals.remove(intervals.indexOf(IntervalFactory.getPerfectFourth()));
  }

// Quarta Aumentada
/*
   * Adiciona o intervalo Quarta Aumentada na relação de intervalos da chordName.
   */
  protected void addAugmentedFourth(){
    intervals.add(IntervalFactory.getAugFourth());
  }

/*
   * Adiciona o intervalo Quarta Aumentada, na pos especificada pelo parâmetro,
   * na relação de intervalos da chordName.
   * @param indexVet Especifica a pos, na relação de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addAugmentedFourth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getAugFourth());
  }

/*
   * Remove o intervalo Quarta Aumentada da relação de intervalos da chordName.
   */
  protected void removeAugmentedFourth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAugFourth()));
  }

// Quinta Diminuta
/*
   * Adiciona o intervalo Quinta Diminuta na relação de intervalos da chordName.
   */
  protected void addDiminishedFifth() {
    intervals.add(IntervalFactory.getDimFifth());
  }

/*
   * Remove o intervalo Quinta Diminuta da relação de intervalos da chordName.
   */
  protected void removeDiminishedFifth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getDimFifth()));
  }

// Quinta Justa
/*
   * Adiciona o intervalo Quinta Justa na relação de intervalos da chordName.
   */
  protected void addPerfectFifth() {
    intervals.add(IntervalFactory.getPerfectFifth());
  }

/*
   * Remove o intervalo Quinta Justa da relação de intervalos da chordName.
   */
  protected void removePerfectFifth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getPerfectFifth()));;
  }

/*
   * Substitui o intervalo Quinta Justa pelo intervalo Quinta Diminuta
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
  protected void changePerfectFifthToDiminished (int pos) {
    intervals.set(pos, IntervalFactory.getDimFifth());
  }

/*
   * Substitui o intervalo Quinta Justa pelo intervalo Quinta Aumentada
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
  protected void changePerfectFifthToAugmented (int pos) {
    intervals.set(pos, IntervalFactory.getAugFifth());
  }

/*
   * Substitui o intervalo Quinta Justa pelo intervalo Nona Maior
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
/*  protected void change5PerfectTo9Major(int pos) {
    intervals.set(pos, IntervalCollection.getMajorNinth());
  }*/

/*
   * Substitui o intervalo Quinta Justa pelo intervalo Décima Primeira Justa
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
/*  protected void change5PerfectTo11Perfect (int pos) {
    intervals.set(pos, IntervalCollection.getPerfect11th());
  }*/

// Quinta Aumentada
/*
   * Adiciona o intervalo Quinta Aumentada na relação de intervalos da chordName.
   */
  protected void addAugmentedFifth() {
    intervals.add(IntervalFactory.getAugFifth());
  }

/*
   * Remove o intervalo Quinta Aumentada da relação de intervalos da chordName.
   */
  protected void removeAugmentedFifth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAugFifth()));;
  }

// Sexta Menor
/*
   * Adiciona o intervalo Sexta Menor na relação de intervalos da chordName.
   */
  protected void addMinorSixth() {
    intervals.add(IntervalFactory.getMinorSixth());
  }

/*
   * Adiciona o intervalo Sexta Menor, na pos especificada pelo parâmetro,
   * na relação de intervalos da chordName.
   * @param indexVet Especifica a pos, na relação de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addMinorSixth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getMinorSixth());
  }

/*
   * Remove o intervalo Sexta Menor da relação de intervalos da chordName.
   */
  protected void removeMinorSixth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorSixth()));
  }

// Sexta Maior
/*
   * Adiciona o intervalo Sexta Maior na relação de intervalos da chordName.
   */
  protected void addMajorSixth() {
    intervals.add(IntervalFactory.getMajorSixth());
  }


// Sexta Aumentada
/*
   * Adiciona o intervalo Sexta Aumentada, na pos especificada pelo parâmetro,
   * na relação de intervalos da chordName.
   * @param indexVet Especifica a pos, na relação de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addAugmentedSixth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getAugSixth());
  }

// Setima Diminuta
/*
   * Adiciona o intervalo Sétima Diminuta na relação de intervalos da chordName.
   */
  protected void addDiminishedSeventh() {
    intervals.add(IntervalFactory.getDimSeventh());
  }

/*
   * Remove o intervalo Sétima Diminuta da relação de intervalos da chordName.
   */
  protected void removeDiminishedSeventh() {
    intervals.remove(intervals.indexOf(IntervalFactory.getDimSeventh()));;
  }

// Setima Menor
/*
   * Adiciona o intervalo Sétima Menor na relação de intervalos da chordName.
   */
  protected void addMinorSeventh() {
    intervals.add(IntervalFactory.getMinorSeventh());
  }

/*
   * Remove o intervalo Sétima Menor da relação de intervalos da chordName.
   */
  protected void removeMajorSeventh() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorSeventh()));;
  }

// Setima Maior
/*
 * Adiciona o intervalo Sétima Maior na relação de intervalos.
 */
  protected void addMajorSeventh() {
    intervals.add(IntervalFactory.getMajorSeventh());
  }

/*
   * Adiciona o intervalo Sétima Maior, na pos especificada pelo parâmetro,
   * na relação de intervalos da chordName.
   * @param indexVet Especifica a pos, na relação de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addMajorSeventh(int indexVet) {
    intervals.set(indexVet, IntervalFactory.getMajorSeventh());
  }

/*
   * Remove o intervalo Sétima Maior da relação de intervalos da chordName.
   */
  protected void removerSetimaMaior() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajorSeventh()));;
  }

// Nona Menor
/*
   * Adiciona o intervalo Nona Menor na relação de intervalos da chordName.
   */
  protected void addMinorNinth() {
    intervals.add(IntervalFactory.getMinorNinth());
  }

/*
   * Remove o intervalo Nona Menor da relação de intervalos da chordName.
   */
  protected void removeMinorNinth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinorNinth()));;
  }

// Nona Maior
/*
   * Adiciona o intervalo Nona Maior na pos especificada pelo parâmetro
   * na relação de intervalos da chordName.
   * @param indexVet Especifica a pos, na relação de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  protected void addMajorNinth(int indexVet) {
    intervals.add(indexVet, IntervalFactory.getMajorNinth());
  }

/*
   * Adiciona o intervalo Nona Maior na relação de intervalos da chordName.
   */
  protected void addMajorNinth() {
    intervals.add(IntervalFactory.getMajorNinth());
  }

/*
   * Remove o intervalo Nona Maior da relação de intervalos da chordName.
   */
  protected void removeMajorNinth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajorNinth()));;
  }

// Nona Aumentada
/*
   * Adiciona o intervalo Nona Aumentada na relação de intervalos da chordName.
   */
  protected void addAugmentedNinth() {
    intervals.add(IntervalFactory.getAugNinth());
  }

/*
   * Remove o intervalo Nona Aumentada da relação de intervalos da chordName.
   */
  protected void removeAugmentedNinth() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAugNinth()));;
  }

// DecimaPrimeira Diminuta
/*
   * Adiciona o intervalo Décima Primeira Diminuta na relação de intervalos da chordName.
   */
  protected void addDiminished11th() {
    intervals.add(IntervalFactory.getDim11th());
  }

/*
   * Remove o intervalo Décima Primeira Diminuta da relação de intervalos da chordName.
   */
  protected void removeDiminished11th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getDim11th()));
  }

// DecimaPrimeira Justa
/*
   * Adiciona o intervalo Décima Primeira Justa na relação de intervalos da chordName.
   */
  protected void addPerfect11th() {
    intervals.add(IntervalFactory.getPerfect11th());
  }

/*
   * Adiciona o intervalo Décima Primeira Justa na pos especificada pelo parâmetro
   * na relação de intervalos da chordName.
   * @param indexVet Especifica a pos, na relação de intervalos da chordName,
   * onde o intervalo deve ser adicionado.
   */
  /*protected void add11Perfect(int indexVet) {
    intervalos.add(indexVet, IntervalCollection.getDecimaPrimeiraJusta());
  }*/

/*
   * Remove o intervalo Décima Primeira Justa da relação de intervalos da chordName.
   */
  protected void removePerfect11th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getPerfect11th()));;
  }

// DecimaPrimeira Aumentada
/*
   * Adiciona o intervalo Décima Primeira Aumentada na relação de intervalos da chordName.
   */
  protected void addAugmented11th() {
    intervals.add(IntervalFactory.getAug11th());
  }

/*
   * Remove o intervalo Décima Primeira Aumentada da relação de intervalos da chordName.
   */
  protected void removeAugmented11th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getAug11th()));;
  }

// DecimaSegunda Justa
/*
   * Substitui o intervalo Décima Segunda Justa pelo intervalo Décima Segunda Diminuta
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
  protected void changePerfect12thToDiminished(int pos) {
    intervals.set(pos, IntervalFactory.getDim12th());
  }

/*
   * Substitui o intervalo Décima Segunda Justa pelo intervalo Décima Segunda Aumentada
   * na relação de intervalos da chordName.
   * @param pos pos, na relação de intervalos da chordName, do intervalo a ser substituído.
   */
  protected void changePerfec12thtToAugmented(int pos) {
    intervals.set(pos, IntervalFactory.getAug12th());
  }

// DecimaTerceira Menor
/*
   * Adiciona o intervalo Décima Terceira Menor na relação de intervalos da chordName.
   */
  protected void addMinor13th() {
    intervals.add(IntervalFactory.getMinor13th());
  }

/*
   * Remove o intervalo Décima Terceira Menor da relação de intervalos da chordName.
   */
  protected void removeMinor13th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMinor13th()));;
  }

// DecimaTerceira Maior
/*
   * Adiciona o intervalo Décima Terceira Maior na relação de intervalos da chordName.
   */
  protected void addMajor13th() {
    intervals.add(IntervalFactory.getMajor13th());
  }

/*
   * Remove o intervalo Décima Terceira Maior da relação de intervalos da chordName.
   */
  protected void removeMajor13th() {
    intervals.remove(intervals.indexOf(IntervalFactory.getMajor13th()));;
  }

// DecimaTerceira Aumentada
/*
   * Adiciona o intervalo Décima Terceira Aumentada na relação de intervalos da chordName.
   */
  protected void addAugmented13th() {
    intervals.add(IntervalFactory.getAug13th());
  }

/*
   * Ordena a relação de intervalos da chordName.
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
   * Verifica se o intervalo pertence a relação de intervalos da chordName.
   * @param intervaloCifraValida Interval a ser verificado.
   * @return True quando o intervalo for encontrado, False caso contrário.
   */
  protected boolean containsInterval(Interval interval){
    return intervals.contains(interval);
  }

/*
   * Verifica se existem intervalos iguais na chordName, ou seja, intervalos com
   * mesma quantidade de semitons.
   * @return True quando existir intervalos iguais, False caso contrário.
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
