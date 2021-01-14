package octopus.instrument;

import java.io.Serializable;

import octopus.util.PropertiesDisplay;


 public class ChordShapeProperties extends PropertiesDisplay implements Serializable {

 private static final long serialVersionUID = 1L;

public ChordShapeProperties(){
    configuraDisplay();
  }


  private void configuraDisplay(){
    //dobrar = mesma nota em qualquer oitava oitava (dobrar em uníssono).
    addDisplayableProperty("dobramentoFundamental", "Fundamental doubling:",new Boolean(true));
    //duplicar = mema nota e oitava (dobrar oitavado).
    addDisplayableProperty("duplicateFundamental","Fundamental duplication:",new Boolean(true));
    addDisplayableProperty("triplicateFundamental","Fundamental triplication:",new Boolean(true));
    addDisplayableProperty("doubleThird", "3rd Doubling:",new Boolean(true));
    addDisplayableProperty("duplicatePerfectFifth", "Perfect 5th duplication:",new Boolean(true));
    addDisplayableProperty("doublePerfectFifth", "Perfect 5th doubling:",new Boolean(true));
    addDisplayableProperty("supressFifth", "Perfect 5th supression", new Boolean(true));
    addDisplayableProperty("considerOctave", "Diatonic notes in chords:",new Boolean(false));
    addDisplayableProperty("suggestFingering", "Fingering:",new Boolean(true));
    addDisplayableProperty("showBarre", "Bar chords:",new Boolean(true));
    addDisplayableProperty("calculateInversions", "Invertions:",new Boolean(false));
  }



    public String getFirstTrueElement(){
      String[] keys = this.keySet().toArray(new String[this.size()]);
      Boolean[]values = this.values().toArray(new Boolean[this.size()]);
      for(int i=0;i<keys.length;i++){
	if(values[i].booleanValue()){
          if(!keys[i].equalsIgnoreCase("calculateInversions") &&
             !keys[i].equalsIgnoreCase("considerOctave") &&
             !keys[i].equalsIgnoreCase("showBarre") &&
             !keys[i].equalsIgnoreCase("suggestFingering") &&
             !keys[i].equalsIgnoreCase("supressFifth")){

             return keys[i];
           }
	}
      }

      return null;
    }

    public String getNextTrueElement(String previous){
      String[] keys = this.keySet().toArray(new String[this.size()]);
      Boolean[]values = this.values().toArray(new Boolean[this.size()]);
      int valorInicial = getIndexOf(previous) + 1;
      if(valorInicial!= -1){
	for(int i=valorInicial;i<keys.length;i++){
	  if(values[i].booleanValue()){
	    if(!keys[i].equalsIgnoreCase("calculateInversions") &&
	       !keys[i].equalsIgnoreCase("considerOctave") &&
	       !keys[i].equalsIgnoreCase("showBarre") &&
	       !keys[i].equalsIgnoreCase("suggestFingering") &&
	       !keys[i].equalsIgnoreCase("supressFifth") &&
	       !keys[i].equalsIgnoreCase(previous)){

	      return keys[i];
	    }
	  }
	}
      }

      return null;
    }


}


