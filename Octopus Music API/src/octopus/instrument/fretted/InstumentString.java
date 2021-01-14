package octopus.instrument.fretted;

import java.io.Serializable;

import octopus.Note;


/*
  Representa a corda de um insntrumento de corda. O conjunto destes objetos determinam
  a afinação do instrumento;
 */
public class InstumentString implements Serializable,Comparable<Object>{
  
  private static final long serialVersionUID = 1L;
  int stringNo;
  Note openStringNote;

/*
    @param numero Número identificador da corda.
    @param nota Note da corda quando solta.
 */
  public InstumentString(int stringNo, Note openStringNote) {
    this.stringNo= stringNo;
    this.openStringNote = openStringNote;
  }

  /** @return Note da corda quando solta.*/
  public Note getOpenStringNote() { return openStringNote;}

  /** @return Número identificador da corda.*/
  public int getStringNumber(){ return stringNo;}

//Testar
  public int compareTo(Object o) {
    if(((InstumentString)o).getStringNumber()>stringNo){
      return -1;
    }else{
      if(((InstumentString)o).getStringNumber()<stringNo){
        return 1;
      }
    }
    return 0;
  }
}
