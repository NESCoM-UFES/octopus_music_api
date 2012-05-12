package octopus;

/**
 * The WesternMusicNotes class creates and performs computations over Note objects.   
 *
 * @see Interval
 * @see Note
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
public class WesternMusicNotes {

/* public static final int DOUBLE_FLAT = -2;
   public static final int FLAT = -1;
   public static final int NATURAL= 0;
   public static final int SHARP = 1;
   public static final int DOUBLE_SHARP = 2;*/


  private static Note C = new Note("C","DO",IntervalFactory.getMinorSecond(),IntervalFactory.getMajorSecond(), 60);
  private static Note D = new Note("D","Ré",IntervalFactory.getMajorSecond(),IntervalFactory.getMajorSecond(), 62);
  private static Note E = new Note("E","Mi",IntervalFactory.getMajorSecond(),IntervalFactory.getMinorSecond(),64);
  private static Note F = new Note("F","Fá",IntervalFactory.getMinorSecond(),IntervalFactory.getMajorSecond(),65);
  private static Note G = new Note("G","Sol",IntervalFactory.getMajorSecond(),IntervalFactory.getMajorSecond(),67);
  private static Note A = new Note("A","L",IntervalFactory.getMajorSecond(),IntervalFactory.getMajorSecond(),69);
  private static Note B = new Note("B","Si",IntervalFactory.getMajorSecond(),IntervalFactory.getMinorSecond(),71);

  // contém apenas notas naturais
/*
 * Relação de notas naturais.
 */
  static Note notes[] = {C, D, E, F, G, A, B};

  // contém tons e semitons
/*
 * Relação de notas cromáticas, ou seja, todos os doze sons da escala cromática.
 */
  static Object cromaticNotes[] = {C,"accidented note ", D,"accidented note ", E, F,
	                  "accidented note ", G, "accidented note ", A, "accidented note ",
			   B};

  public WesternMusicNotes () {}

/*
 * Obtém a nota Dó.
 * @return A nota Dó.
 */
  public static Note getC() { return (Note)C.clone();}
  public static Note getC(int accident) {
   Note retorno = (Note)C.clone();
      switch (accident)    {
       case -1: retorno = getFlat(retorno); break;
       case 1: retorno = getSharp(retorno); break;
   }

   return retorno;

  }

/*
 * Retorna a nota Ré.
 * @return A nota Ré.
 */
  public static Note getD() {return (Note)D.clone();}
  public static Note getD(int accident) {
   Note retorno = (Note)D.clone();
      switch (accident)    {
       case -1: retorno = getFlat(retorno); break;
       case 1: retorno = getSharp(retorno); break;
   }
      return retorno;

  }
/*
 * Retorna a nota Mi.
 * @return A nota Mi.
 */
  public static Note getE() {return (Note)E.clone();}
  public static Note getE(int accident) {
   Note retorno = (Note)E.clone();
      switch (accident)    {
       case -1: retorno = getFlat(retorno); break;
       case 1: retorno = getSharp(retorno); break;
   }
      return retorno;

  }
/*
 * Retorna a nota Fá.
 * @return A nota Fá.
 */
  public static Note getF() {return (Note)F.clone();}
  public static Note getF(int accident) {
   Note retorno = (Note)F.clone();
      switch (accident)    {
       case -1: retorno = getFlat(retorno); break;
       case 1: retorno = getSharp(retorno); break;
   }
      return retorno;

  }
/*
 * Retorna a nota Sol.
 * @return A nota Sol.
 */
  public static Note getG() {return (Note)G.clone();}
  public static Note getG(int accident) {
   Note retorno = (Note)G.clone();
      switch (accident)    {
       case -1: retorno = getFlat(retorno); break;
       case 1: retorno = getSharp(retorno); break;
   }
      return retorno;

  }
/*
 * Retorna a nota Lá.
 * @return A nota Lá.
 */
  public static Note getA() {return (Note)A.clone();}
  public static Note getA(int accident) {
   Note retorno = (Note)A.clone();
      switch (accident)    {
       case -1: retorno = getFlat(retorno); break;
       case 1: retorno = getSharp(retorno); break;
   }
      return retorno;

  }
/*
 * Retorna a nota Si.
 * @return A nota Si.
 */
  public static Note getB() {return (Note)B.clone();}
  public static Note getB(int accident) {
   Note retorno = (Note)B.clone();
      switch (accident)    {
       case -1: retorno = getFlat(retorno); break;
       case 1: retorno = getSharp(retorno); break;
   }
      return retorno;

  }
/*
   * Verifica se a nota é alterada (Dó#, Mib etc.).
   * Ou seja verifica se na String passada como argumento existe o caracter '#' (Sustenido)
   * ou 'b'(Bemol), indicando uma alteração na nota.
   * @param simbNota Note que pode ser alterada ou não.
   * @return True se a nota for alterada, False caso contrário.
   */
  private static boolean isAccidentalNote(String simbNota) {
    boolean retorno;
    if ((simbNota.indexOf('#')!= -1)||(simbNota.indexOf('b')!= -1)) {
      retorno = true;
    }else{
       retorno= false;
    }
   return retorno;
  }

// Para transformar a Note em Note com sustenido é necessário acrescentar o "#"
// ao symbol da notaBase, acrescentar a String "Sustenido" ao nomeNota
// e criar a notaAlteradaRetorno passando a Note, o nome da Note e os intervalos
// anterior e posterior.
/*
   * Retorna uma Note com sustenido.
   * @param notaBase A Note que será transformada em Note com sustenido.
   * @return Note com sustenido.
   */
  public static Note getSharp(Note notaBase) {
    Note notaAlteradaRetorno;
    String simbNovaNota = notaBase.getSymbol() + "#";
    String nomeNovaNota = notaBase.getNoteName() + " Sharp";
    int midi = notaBase.MidiValue +1;
    notaAlteradaRetorno = new Note(simbNovaNota, nomeNovaNota,IntervalFactory.getMinorSecond(),
                                 IntervalFactory.getMinorSecond(), midi);
    return notaAlteradaRetorno;
  }
  public static Note getDoubleSharp(Note notaBase) {
    Note notaAlteradaRetorno;
    String simbNovaNota = notaBase.getSymbol() + "##";
    String nomeNovaNota = notaBase.getNoteName() + " Double Sharp";
    int midi = notaBase.MidiValue +2;
    notaAlteradaRetorno = new Note(simbNovaNota, nomeNovaNota,IntervalFactory.getMinorSecond(),
                                 IntervalFactory.getMinorSecond(), midi);
    return notaAlteradaRetorno;
  }

// Para transformar a Note em Note com bemol é necessário acrescentar o "b"
// ao symbol da notaBase, acrescentar a String "Bemol" ao nomeNota
// e criar a notaAlteradaRetorno passando a Note, o nome da Note e os intervalos
// anterior e posterior.
/*
   * Retorna uma Note com bemol.
   * @param notaBase A Note que será transformada em Note com bemol.
   * @return Note com bemol.
   */

  public static Note getFlat(Note notaBase) {
    Note notaAlteradaRetorno;
    String simbNovaNota = notaBase.getSymbol() + "b";
    String nomeNovaNota = notaBase.getNoteName() + " Flat";
    int midi = notaBase.MidiValue -1;
    notaAlteradaRetorno = new Note(simbNovaNota, nomeNovaNota, IntervalFactory.getMinorSecond(),IntervalFactory.getMinorSecond(), midi);
    
    return notaAlteradaRetorno;
  }

  public static Note getDoubleFlat(Note notaBase) {
    Note notaAlteradaRetorno;
    String simbNovaNota = notaBase.getSymbol() + "bb";
    String nomeNovaNota = notaBase.getNoteName() + " Double Flat";
    /*@todo errado*/
    int midi = notaBase.MidiValue -2;
    notaAlteradaRetorno = new Note(simbNovaNota, nomeNovaNota, IntervalFactory.getMinorSecond(),IntervalFactory.getMinorSecond(), midi);
    return notaAlteradaRetorno;
}


/*
   * Retorna a Note correspondente ao argumento passado. Ex: "D" -> Note Ré.
   * @param symbol Símbolo que dará origem a uma Note.
   * @return A Note correspondente ao argumento passado.
   */
  public static Note getNote(String noteSymbol) throws NoteException {
   //try{
     return getNote(noteSymbol, 4);
   /*}catch(NoteException noteEx){
     // always pass OctavePitch 4...so no worries.
     return null;
   }*/
  }

  public static Note getNote(String noteSymbol, int octavePitch) throws
      NoteException {
    Note notaRetorno = null;

    int indexNota = -1;
    String nota = noteSymbol;
    String alteracao = null;

    if (isAccidentalNote(noteSymbol)) {
      nota = noteSymbol.substring(0,1);
      alteracao = noteSymbol.substring(1,2);
    }

    for (int i=0;i < notes.length; i++) {
      if (nota.equals(notes[i].getSymbol())) {
        indexNota = i;
        notaRetorno = (Note)notes[i].clone();
        break;
      }
    }

    if (alteracao != null) {
      if (alteracao.equals("#")) {
	String intervalo = notes[indexNota].getNextInterval().getSymbol();
          if (intervalo.equals("2")) {
            notaRetorno = getSharp(notes[indexNota]);
          }else{
            throw new NoteException("This note can not be sharp",notes[indexNota].getSymbol());
            //System.out.println("Esta nota não pode ser Sustenida");
             //erro
          }
      }else{
         if (alteracao.equals("b")) {
           String intervalo = notes[indexNota].getPreviousInterval().getSymbol();
           if (intervalo.equals("2")) {
	     notaRetorno = getFlat(notes[indexNota]);
           }else{
              //System.out.println("Esta nota não pode ser Bemol");
              throw new NoteException("This note can not be flat : " + notes[indexNota].getSymbol(), notes[indexNota].getSymbol());
              //erro
           }
         }
      }
    }

    if(notaRetorno!= null){
    notaRetorno.setOctavePicth(octavePitch);
    calculateMidiValue(notaRetorno);
    calculateFrequency(notaRetorno);
    }

   return notaRetorno;
  }

  public static Note getNote(Note notaFundamental, int semitons)  throws NoteException{
    Interval intervalo = IntervalFactory.getInterval(semitons);
    return getNote(notaFundamental, intervalo);
  }
/*
   * Dada uma nota e dado um intervalo retorna a Note correspondente ao intervalo
   * dado contando a partir da nota dada. Note que a nota e o intervalo são textos.
   * Ex: ("C","b3") -> Note Mi bemol.
   * @param simbFundamental Símbolo que dará origem a uma Note.
   * @param simbIntervalo Símbolo que dará origem a um Interval.
   * @return A Note correspondente ao intervalo dado contando a partir da nota dada.
   */
  public static Note getNote(String simbFundamental, String simbIntervalo)  throws NoteException {
    Interval intervalo = IntervalFactory.getInterval(simbIntervalo);
    Note notaFundamental = getNote(simbFundamental);
    return getNote(notaFundamental, intervalo);
  }


 public static Note getNote(Note fundamentalNote, Interval interval) throws NoteException{

   Note returnNote= null;
   int cont = 1;
   Note baseNote = WesternMusicNotes.getNote(fundamentalNote.getBaseNoteSymbol());
   baseNote.setOctavePicth(fundamentalNote.getOctavePitch());
   int posNote = WesternMusicNotes.getNoteIndex(baseNote);
   posNote+= (interval.getNumericInterval() -1);
   int octave = fundamentalNote.getOctavePitch();
   while (posNote >6){
     posNote-=7;
     octave++;
   }
   returnNote = (Note)notes[posNote].clone();


   int distNotes = WesternMusicNotes.getDistance(fundamentalNote,returnNote,false);
   int dif = interval.getDistanceFromRoot() - distNotes;
   while (dif > 11) {
     dif -= 12; // Diminuo pela quantidade de elementos do vetor!
   }
   while (dif < -11) {
     dif += 12; // Diminuo pela quantidade de elementos do vetor!
   }


 switch(dif){
   case 1: returnNote= WesternMusicNotes.getSharp(returnNote);break;
   case -11: returnNote = WesternMusicNotes.getSharp(returnNote);break;
   case 2: returnNote = WesternMusicNotes.getDoubleSharp(returnNote);break;
   case -10: returnNote = WesternMusicNotes.getDoubleSharp(returnNote);break;
   case -1: returnNote= WesternMusicNotes.getFlat(returnNote);break;
   case 11: returnNote = WesternMusicNotes.getFlat(returnNote);break;
   case -2: returnNote = WesternMusicNotes.getDoubleFlat(returnNote);break;
   case 10: returnNote = WesternMusicNotes.getDoubleFlat(returnNote);break;

     }

   
  if ((dif>2)&& (dif<10)||
	  (dif > -10)&&(dif < -2)){
       throw new NoteException(
           "Is not allowed double sharp or flat for this note and this interval. \n"
           + "Try to use next natural note instead.",fundamentalNote.getNoteName());
     }

   
   returnNote.setOctavePicth(octave);
   calculateMidiValue(returnNote);
   calculateFrequency(returnNote);
   return returnNote;
 }
/*
   * Retorna a quantidade de semitons entre duas notas,
   * podendo considerar as regiões de oitava ou não.
   * @param nota1 Note origem.
   * @param nota2 Note destino.
   * @param considerOctave Considera-se regiões de oitava ou não.
   * @return Quantidade de semitons entre duas notas.
   */
  public static int getDistance(Note note1, Note note2, boolean octaveCounting) throws NoteException {
    int retorno;
    int posNota1 = getCromaticNoteIndex(note1);
    int posNota2 = getCromaticNoteIndex(note2);

    if (octaveCounting) {
      int indiceNota1 = note1.getOctavePitch();
      int indiceNota2 = note2.getOctavePitch();
      int diferenca = 0;

      if (indiceNota1!=indiceNota2) {
        if (indiceNota1 < indiceNota2) {
          diferenca = indiceNota2 - indiceNota1;
          retorno=((diferenca * 12) + (posNota2 - posNota1));
        }else{
           diferenca = indiceNota1 - indiceNota2;
           retorno=((diferenca * -12) + (posNota2 - posNota1));
        }
      }else{
         retorno = posNota2 -posNota1;
      }
    }else{
       retorno = posNota2 -posNota1;
       retorno = (retorno < 0)?retorno+=12: retorno;
    }

   return retorno;
  }

// getCromaticNoteIndex recebe como parâmetro um objeto do tipo
// Note. Retorna o índice (0 - 11) desta Note na escala cromatica.
/*
   * Retorna a posição da Note na escala cromática.
   * @param nota Note que se busca a posição.
   * @return Posição da Note na relação de notas cromáticas.
   */
  public static int getCromaticNoteIndex(Note nota) throws NoteException {
    int valorAlteracao = 0;
    int indexNotaRetorno = -1;

    if (nota.isAccidental()) {
      String acc = nota.getAccident();
      if(acc.equals("#")){
        valorAlteracao = 1;
      }else{
         if(acc.equals("b")){
           valorAlteracao = -1;
         }else{
            if(acc.equals("##")){
              valorAlteracao = 2;
            }else{
               if(acc.equals("bb")){
                 valorAlteracao = -2;
               }
            }
         }
      }
      //valorAlteracao = (nota.getAccident().equals("#"))? (1) :(- 1);

      nota = getNote(nota.getBaseNoteSymbol());
    }

    // Buscando a fundamental
    for (int i=0;i<12 ;i++ ) {
      if (cromaticNotes[i] instanceof Note) {
        if (nota.symbol.equals(((Note) cromaticNotes[i]).symbol)){
	  indexNotaRetorno += (i+1);
	  break;
	}
      }
    }

    indexNotaRetorno = (indexNotaRetorno != -1)? indexNotaRetorno+=valorAlteracao: -1;

    // Tratamento circular para não estourar o vetor;
    while (indexNotaRetorno > 11) {
      indexNotaRetorno -= 12; // Diminuo pela quantidade de elementos do vetor!
       //oitava++;
    }
   return indexNotaRetorno;
  }

/*
   * Retorna a posição da nota na relação de Notas (Notas naturais).
   * @param notaProcurada Note que se busca a posição.
   * @return Posição da Note na relação de notas naturais.
   */
  public  static int getNoteIndex(Note notaProcurada) {
    int indexRetorno = -1;
    for (int i =0; i< notes.length ; i++) {
      if (notaProcurada.symbol == notes[i].getSymbol()) {
        indexRetorno = i;
        break;
      }
    }
   return indexRetorno;
  }

  public static void calculateMidiValue(Note nota) throws NoteException{
    int posEscala = getCromaticNoteIndex(nota);
    if (nota.getOctavePitch() == 4) { //I've changed from 5 to for...not sure why it was 5 before, but must have a good reason!
      nota.setMidiValue(60 + posEscala);
    }
    else {
      if (nota.getOctavePitch() < 4) { //same here
        //Calcula a oitava e soma a posição na escala cromática.
        int fator = 60 - ( (5 - nota.getOctavePitch()) * 12);
        nota.setMidiValue(fator + posEscala);
      }
      else {
        int fator = 60 + ( (nota.getOctavePitch() - 4) * 12); //same here
        nota.setMidiValue(fator + posEscala);
      }
    }
  }
  
 public static void calculateFrequency(Note note) throws NoteException{
	 if (note.MidiValue == -1){
		 calculateMidiValue(note);
	 }
	 double freq = (440 * Math.pow(2,((double)(note.getMidiValue() - 69)/12))); 
	 note.setFrequency(freq);
 }
  
 public static void main(String args[]) {
 Interval[] intervals = IntervalFactory.getIntervals();
  for(int i=0;i< notes.length;i++){
    for(int j=0;j<intervals.length;j++){
      try{
        System.out.println(notes[i].name + " " + intervals[j].getSymbol() +
                           " = " +
                           getNote(notes[i], intervals[j]).symbol + "\n");
      }catch(Exception ex){
        ex.printStackTrace();
      }
    }
  }

 }


}



