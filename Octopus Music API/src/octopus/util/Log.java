package octopus.util;


public class Log {

public static final int JUST_STRING = 0;
public static final int STRING_AND_SCREEN = 1;
public static final int JUST_SCREEN = 2;

private int outputType;
private String log="";

/* @TODO:
 *  Adicionar um OutputStream*/

public Log() {
this.outputType = 0;
}

public Log(int outputType) {
  this.outputType = outputType;
}

public  void cleanLog(){
  log = "";
 }

 public  void writeLog(String text){
   log = outputType<2?  log + "\n" +  text:"";
   if (outputType >1){ System.out.println("\n" +  text);}
 }

 public  void writeLog(Object[] array){
  for (int i =0;i<array.length;i++){
   log = outputType<2?  log + "\n" + "    Registro: " + (i+1) + ") " + array[i].toString():"";
   if (outputType >1){ System.out.println("\n" + "    Registro: " + (i+1) + ") " + array[i].toString());}
  }
 }
 public String getStringLog(){
   return log;
 }
}
