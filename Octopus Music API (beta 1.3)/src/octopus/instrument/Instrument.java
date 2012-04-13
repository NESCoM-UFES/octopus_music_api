package octopus.instrument;

import java.io.*;


/*
Esta classe abstrata é a superclasse de todo instrumento representado por um plug-in.
O plug-in nada mais é do que uma instanciação de um objeto do tipo instrumento.

Revisada: 18/04 AS 17:20
*/

public abstract class Instrument implements Serializable{

  protected String name; // nome e modelo
  protected int patchNumber; // numero midi que mais se aproxima
  protected InstrumentGraphicalInterface instrumentInterface; // Interface  com usuário
  protected int polyphony;

  protected Instrument(String name){
    this.name = name;
    patchNumber = 1;
    polyphony = 1;
  }

  protected Instrument(String name, InstrumentGraphicalInterface instrumentInterface){
    this.name = name;
    patchNumber = 1;
    polyphony = 1;
    this.instrumentInterface = instrumentInterface;
  }

  protected Instrument(String name, int patchNo, InstrumentGraphicalInterface instrumentInterface){
    this.name = name;
    this.patchNumber = patchNo;
    this.instrumentInterface = instrumentInterface;
  }

  public void setInstrumentoInterface(InstrumentGraphicalInterface intrumentInterface){
    this.instrumentInterface = intrumentInterface;
  }


  public InstrumentGraphicalInterface getInstrumentInterface(){return instrumentInterface;}

  public String getName(){return name;}


  public void setPatchNumber(int patchNumber){
    this.patchNumber = patchNumber;
  }

	public  int getPatchNumber(){return patchNumber;}

}
