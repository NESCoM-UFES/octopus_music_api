package octopus.instrument.fretted;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import octopus.Note;
import octopus.instrument.InstrumentGraphicalInterface;
import octopus.instrument.InstrumentNotePosition;

public class GuitarGraphicalInterface extends InstrumentGraphicalInterface{

  private static final long serialVersionUID = 1L;

  protected Guitar guitar;

  protected JButton bt;
  protected JLabel lbFret;

  protected boolean holdKey = false;


  protected int pressedKeys = 0;
  protected int lastFretPressed = -1;

  protected JToggleButton[][] bts;// = new JToggleButton[6][12];
  protected ButtonString[] stringsButtons;// = new ButtonString[6];
  protected Thread[] stringsButtonsThreads;// = new Thread[6];


  public GuitarGraphicalInterface(Guitar guitar) {
    super(guitar);
     this.guitar = guitar;

     int nStrings = guitar.getStrings().length;
     int nFrets = guitar.getNumberOfFrets();

     bts = new JToggleButton[nStrings][nFrets];
     stringsButtons = new ButtonString[nStrings];
     stringsButtonsThreads = new Thread[nStrings];

     configLayout();

  }

  public void setGuitar(Guitar guitar){
    this.guitar = guitar;
  }




  public void turnOn(GuitarChordShape chordShape){
	 InstrumentNotePosition[] notePositions =chordShape.getInstrumentNotePositions();
	 for(int i=0; i< notePositions.length;i++){
		 this.turnOn((GuitarNotePosition)notePositions[i]);
	   	 }
	  
  }


 protected void turnOnString(int s, int velocity){

     if (stringsButtonsThreads[s-1] != null) {
       if (stringsButtonsThreads[s-1].getState() == Thread.State.TIMED_WAITING) {
        // System.out.println("esperando");
         stringsButtonsThreads[s-1].interrupt();
       }
     }
     this.stringsButtons[s-1].velocity = velocity;
     stringsButtonsThreads[s-1] = new Thread(stringsButtons[s-1]);
     stringsButtonsThreads[s-1].start();

 }


 private void configLayout(){
    //JPanel pnExternal = new JPanel(new BorderLayout());
    this.setLayout(new BorderLayout());

    JPanel pnFretLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    lbFret = new JLabel("1st");
    lbFret.setForeground(Color.blue);
    pnFretLabel.add(lbFret);
    /*JToggleButton btHoldKey = new JToggleButton("Hold Keys Pressed");
    btHoldKey.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           holdKey = ((JToggleButton)e.getSource()).isSelected();
           resetGUI();
         }
    });

    pnFretLabel.add(btHoldKey);*/

    int nStrings = guitar.getStrings().length;
    int nFrets = guitar.getNumberOfFrets();

    JPanel pnFretBoard = new JPanel();
    pnFretBoard.setLayout(new GridLayout(nStrings,nFrets,0,0));


    for (int s=0; s<nStrings;s++){
       for (int f=0; f<nFrets;f++){
         bts[s][f] = new JToggleButton();
        // bts[s][f] = new JToggleButton("kn"+ String.valueOf(s) + String.valueOf(f));
         bts[s][f].setForeground(Color.BLUE);
         bts[s][f].setBackground(Color.black);
         bts[s][f].setActionCommand("kn"+ String.valueOf(s) + String.valueOf(f));
         bts[s][f].addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {

         String ac = e.getActionCommand();
         int s = Integer.parseInt(ac.substring(2,3));
         int f = Integer.parseInt(ac.substring(3));

         Note note = guitar.getNote(new GuitarNotePosition(f+1, s+1));
         String noteName = note.getSymbol();

         if(((JToggleButton)e.getSource()).isSelected()){

           bts[s][f].setText(noteName);
           //guitar.playNote(note,127);
         }else{

           bts[s][f].setText("");
         }
       }
       });
         pnFretBoard.add(bts[s][f]);
       }
    }

    //Generation of the Strings Panel
    JPanel pnStrings = new JPanel();
    pnStrings.setLayout(new GridLayout(nStrings,1,0,0));
    for(int s=0;s<nStrings;s++){
      //stringsButtons[s] = new JToggleButton("st " + s+1);
      Note note = guitar.getString(s+1).getOpenStringNote();
      stringsButtons[s] = new ButtonString(note.getSymbol());
      stringsButtons[s].setActionCommand("Str"+ String.valueOf(s));
      stringsButtons[s].setBackground(Color.YELLOW);
      //Not sure if I should comment..eclipse suggestion to remove.
      /*stringsButtons[s].addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(ActionEvent e) {
                 if(((JToggleButton)e.getSource()).isSelected()){
                   String ac = e.getActionCommand();
                   int s = Integer.parseInt(ac.substring(3));
                 }
               }
          });*/


      pnStrings.add( stringsButtons[s]);
    }

    this.add(pnFretLabel,BorderLayout.NORTH);
    this.add(pnFretBoard,BorderLayout.CENTER);
    this.add(pnStrings,BorderLayout.EAST);

  }


 public void resetGUI(){
   for(int i=0;i< bts.length;i++){
     for(int j=0; j<bts[i].length;j++){
       bts[i][j].setSelected(false);
       bts[i][j].setText("");
     }
     this.stringsButtons[i].setSelected(false);
   }
   this.repaint();
 }

  public void turnOff(GuitarChordShape chordShape){

  }


  @Override
public void turnOn(InstrumentNotePosition instrumentNotePosition) {
    int fret = ((GuitarNotePosition)instrumentNotePosition).getFret();
    int string = ((GuitarNotePosition)instrumentNotePosition).getString();

   if( fret>0){
    bts[string-1][fret-1].setSelected(true);
    bts[string-1][fret-1].setText(guitar.getNote(new GuitarNotePosition(fret, string)).getSymbol());
   }
   turnOnString(string,127); //Mudar o parametro do velocity
  }

  @Override
public void turnOff(InstrumentNotePosition instrumentNotePosition) {
    int fret = ( (GuitarNotePosition) instrumentNotePosition).getFret();
    int string = ( (GuitarNotePosition) instrumentNotePosition).getString();

    if (fret > 0) {
      bts[string-1][fret-1].setSelected(false);
      bts[string-1][fret-1].setText("");
    }
     stringsButtons[string-1].setSelected(false);

  }

  class ButtonString extends JToggleButton implements Runnable{
   
   private static final long serialVersionUID = 1L;
   Color originalColor;
   int velocity = 0;

   ButtonString(String label){
     super(label);
     originalColor = Color.yellow;
     
   }
   public void run() {
   try{
     int xalpha = originalColor.getAlpha();
     int xgreen = originalColor.getGreen();
     int xblue = originalColor.getBlue();
     int xred = originalColor.getRed();
     int contGreen = (int)(255- (velocity*2.015748));
     //System.out.println(contGreen);
     int flash = 5;
     this.setForeground(Color.green);

     while (true) {
       //int currentRed = this.getBackground().getRed();
       if((contGreen == xgreen)) {
         Thread.interrupted();
         this.setBackground(new Color(xred , contGreen, xblue,xalpha));
         break;
       }else{
         contGreen = contGreen +1;
         this.setBackground(new Color(xred , contGreen, xblue,xalpha));
         if(flash==0){
            this.setForeground(Color.black);
         }else{
           flash-=1;
         }
         int sleepTime = 30;
         Thread.sleep(sleepTime);
       }
     }
   }catch(InterruptedException e){
     //nothing
   }catch(Exception ex){
     ex.printStackTrace();
   }
   }
 }
}
