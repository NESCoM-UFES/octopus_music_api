package octopus.instrument.fretted;

import octopus.instrument.*;



import java.io.*;



// Considera-se que todos os instrumentosde corda são trasteados, caso contrário não existe
// como representar um acorde.
public class GuitarNotePosition implements InstrumentNotePosition, Serializable{
  private static final long serialVersionUID = 1L;
   protected int fret;
   protected int string;
   protected int finger;

  public GuitarNotePosition(int fret, int string) {
    this.fret = fret;
    this.string = string;
  }

  public GuitarNotePosition(int fret, int string, int finger) {
   this.fret = fret;
   this.string = string;
   this.finger = finger;
  }


  public void setFinger(int finger){
    this.finger = finger;
  }

  public int getFinger() { return finger;}

  public int getFret() {return fret;}

  public int getString() {return string;}

  @Override
public String toString(){
   return String.valueOf(string) + String.valueOf(fret);
  }

  // Implementação do metódo herdado da interface comparable. Usado para ordenar
  //as representacoes: ordem decrecente das corda e cresencente de casa;
  // Ex: 50 < 53
  public int compareTo(Object obj2){
    GuitarNotePosition posNote2 = (GuitarNotePosition) obj2;
    int retorno;

    if (posNote2.getString() == string){
      if(posNote2.getFret() == fret){
	retorno = 0;
      }else{
	if (posNote2.getFret() < fret){
	  retorno = 1;
	}else{
	  retorno =  -1;
	}
      }
    }else{
      if(posNote2.getString() > string){
	retorno = 1;
      }else{
	retorno = - 1;
      }
    }
  return retorno;
  }
}
