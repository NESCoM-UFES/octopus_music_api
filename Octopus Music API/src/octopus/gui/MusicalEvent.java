package  octopus.gui;
import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;

public class MusicalEvent implements Serializable {

  int midiNote;
  //outros paramentros ainda não sabidos de configuracao do envento msuical: Eg. attack, staccato etc...

  MusicalEventFrame[] musicalEventFrames;
  //transient MusicalTableModel model;
  public int volume = 127;
  public Hashtable efeitos = new Hashtable();

  public double swingTiming = 0.0;


  public MusicalEvent(MusicalEventFrame[] meFrames){//,MusicalTableModel model ) {
    musicalEventFrames = meFrames;
    //this.model = model;
  }

  public MusicalEvent(Collection meFrames){//, MusicalTableModel model) {
   musicalEventFrames = (MusicalEventFrame[])meFrames.toArray(new MusicalEventFrame[meFrames.size()]);
   //this.model = model;
  }

  public int getDuracao(){
    int duracao = 0;
    if(musicalEventFrames.length>0){
      duracao = (musicalEventFrames[0].colunaFinal - musicalEventFrames[0].colunaInicial) + 1;
    }
    return duracao;
  }

  public int getLinha(){
    int linha = 0;
    if(musicalEventFrames.length>0){
      linha = musicalEventFrames[0].linha;
    }
    return linha;
  }
  public int getCompassoInicial(int temposCompasso){
    int compasso = 0;
    if(musicalEventFrames.length>0){
      //float v1 = ((float)(musicalEventFrames[0].colunaInicial + 1)/(float)model.temposCompasso);
      float v1 = ((float)(musicalEventFrames[0].colunaInicial + 1)/(float)temposCompasso);
      float x = (v1 - (int)v1);
      compasso = x==0.0?(int)v1:(int)v1+1;
    }
    return compasso;
  }

  public int getTempoDentroCompasso(int temposCompasso){
    int compasso = 0;
    if(musicalEventFrames.length>0){
      //compasso = (musicalEventFrames[0].colunaInicial + 1- ((getCompassoInicial()-1)*model.temposCompasso));
      compasso = (musicalEventFrames[0].colunaInicial + 1- ((getCompassoInicial(temposCompasso)-1)*temposCompasso));
    }
    return compasso;
  }
  public double getStartTime(double referenciaTempo){
    double startTime = 0;
    if(musicalEventFrames.length>0){
      //startTime = swingTiming + (musicalEventFrames[0].colunaInicial)*model.referenciaTempo;
      startTime = swingTiming + (musicalEventFrames[0].colunaInicial)*referenciaTempo;
    }
    return startTime;
  }

  @Override
public String toString(){
    return "Linha:" + musicalEventFrames[0].linha + " Col. inic." + musicalEventFrames[0].colunaInicial+ " Col. final." + musicalEventFrames[0].colunaFinal;
  }

  public int getPrimeiroTempo(){
    if(musicalEventFrames.length>0){
      return musicalEventFrames[0].colunaInicial;
    }
    return -1;
  }

   /*private void writeObject(java.io.ObjectOutputStream out)
       throws IOException{
     out.writeObject(musicalEventFrames);
     //out.writeObject(model);
     out.writeInt(volume);
     out.writeObject(efeitos);
     out.writeDouble(swingTiming);
   }*/

}