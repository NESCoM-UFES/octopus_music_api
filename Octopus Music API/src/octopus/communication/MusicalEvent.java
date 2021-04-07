package octopus.communication;

/**
 * @author Leandro Costalonga
 * @version 1.0
 */
import java.util.Properties;

import octopus.Note;

public class MusicalEvent extends Properties implements Comparable<Object> {

private static final long serialVersionUID = 1L;

public long id;
  public Note note;
  public double duration;
  public double timing;
  public double velocity;

 // Internal used only(Playable Classes)
  public MusicalEvent(long id,double timing, double duration, double velocity) {
	    /*put("id", new Long(id));
	    put("note", note);
	    put("duration", new Double(duration));
	    put("timing", new Double(timing));
	    put("velocity", new Double(velocity));*/

	    this.id = id;
	    this.timing = timing;
	    this.duration = duration;
	    this.velocity = velocity;

	}
  
  public MusicalEvent(long id,double timing, Note note, double duration, double velocity) {
    /*put("id", new Long(id));
    put("note", note);
    put("duration", new Double(duration));
    put("timing", new Double(timing));
    put("velocity", new Double(velocity));*/

    this.id = id;
    this.timing = timing;
    this.note = note;
    this.duration = duration;
    this.velocity = velocity;

}



  public MusicalEvent(long id,double timing, Note note, double duration, double velocity,String[][] propertiesArray) {
 /* put("id",new Long(id));
  put("note",note);
  put("duration",new Double(duration));
  put("timing",new Double(timing));
  put("velocity",new Double(velocity));*/

  this.id = id;
  this.timing = timing;
  this.note = note;
  this.duration = duration;
  this.velocity = velocity;


   for(int i=0; i< propertiesArray.length;i++){
     put(propertiesArray[0],propertiesArray[1]);
   }

  }


  public MusicalEvent(long id, Note note, double duration, double timing, Properties properties) {
    /*put("id", new Long(id));
    put("note", note);
    put("duration", new Double(duration));
    put("timing", new Double(timing));
    put("velocity",new Double(velocity));*/

    this.id = id;
    this.timing = timing;
    this.note = note;
    this.duration = duration;
   // this.velocity = velocity;

    putAll(properties);
}

 /*public void loadFromXML(InputStream inputStream) throws IOException {
   super.loadFromXML(inputStream);

   this.id = ((Long)this.get("id")).longValue();
   this.timing = ((Double)this.get("timing")).doubleValue();
   this.note = ((Note)this.get("timing"));
   this.duration = ((Double)this.get("duration")).doubleValue();;
   this.velocity = ((Double)this.get("velocity")).doubleValue();;;

 }*/

@Override
public String toString(){
 String s = "id: " + id + " Timing:" + timing + " Note: "+ note.getSymbol() + " Duration: " +
     duration +  " Velocity:" + velocity;
 return s;
}

  public static void main(String[] args) {
    //MusicalEvent musicalevent = new MusicalEvent();
  }

  @Override
public int compareTo(Object o) {
    if(((MusicalEvent)o).timing < this.timing){
      return 1;
    }
    if(((MusicalEvent)o).timing > this.timing){
      return -1;
    }
    return 0;
  }
}
