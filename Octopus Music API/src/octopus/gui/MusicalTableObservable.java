package octopus.gui;

import java.util.Observable;

public class MusicalTableObservable extends Observable {
   public int operationType;

  public void publicarOperacao(int operationType){
    this.operationType = operationType;
    this.setChanged();
    this.notifyObservers();
  }

}