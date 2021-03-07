package octopus.gui;

import java.io.Serializable;

public class Arpejo implements Serializable {
  //MusicalEvent[]  musicalEvents;
  public byte direcaoArpejo;
  public byte velocidade;
  public boolean habilitado = true;

  static final byte DIRECAO_DESCENDENTE = 1;
  static final byte DIRECAO_ASCENDENTE = 2;


 /* public Arpejo(MusicalEvent[] musicalEvents, byte direcaoArpejo, int velocidade) {
    this.musicalEvents = musicalEvents;
    this.direcaoArpejo = direcaoArpejo;
    this.velocidade = velocidade;
  }*/

  public Arpejo(byte direcaoArpejo, byte velocidade, boolean habilitado) {
    this.direcaoArpejo = direcaoArpejo;
    this.velocidade = velocidade;
    this.habilitado = habilitado;
  }

 /* public MusicalEvent[] getMusicalEvents(){
    return musicalEvents;
  }

  public void setMusicalEvents(MusicalEvent[] musicalEvents ){
    this.musicalEvents = musicalEvents;
  }

  public int getTempoAtaque(){
    if(musicalEvents != null){
      return musicalEvents[0].getPrimeiroTempo();
    }
    return -1;
  }*/
}