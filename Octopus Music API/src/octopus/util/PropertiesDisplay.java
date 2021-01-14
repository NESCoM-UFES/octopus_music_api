package octopus.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;


public class PropertiesDisplay
    extends Properties {
  
	private static final long serialVersionUID = 1L;

	String columnNames[] = {
      "Property", "Value"};
  String keys[];
     MyTableModel tableModel = new MyTableModel(columnNames);
  @SuppressWarnings("unchecked")
Hashtable display;
  @SuppressWarnings("unchecked")
public PropertiesDisplay() {
    super();
    display = new Hashtable();
  }

  public PropertiesDisplay(Properties defaults) {
    super(defaults);
  }

  //==Begin




  @SuppressWarnings("unchecked")
public PropertiesDisplay(File file) throws Exception {

    display = new Hashtable();
    FileInputStream input = new FileInputStream(file);
    load(input);

  }

  @SuppressWarnings("unchecked")
public void addDisplayableProperty( String internalName,String displayName,
                                     Object value) {
    display.put(internalName, displayName);
    put(internalName, value);
  }

  public String getPropertyDisplayName(String internalName) {
    return display.get(internalName).toString();
  }

  @SuppressWarnings("unchecked")
public Collection getPropertiesDisplayNames() {
    return display.values();
  }

  /* public String getFirstTrueElement(){
   String[] chaves = (String[])this.keySet().toArray(new String[this.size()]);
   Boolean[]valores = (Boolean[])this.values().toArray(new Boolean[this.size()]);
     for(int i=0;i<chaves.length;i++){
       if(valores[i].booleanValue()){
         if(!chaves[i].equalsIgnoreCase("calculateInversions") &&
            !chaves[i].equalsIgnoreCase("considerOctave") &&
            !chaves[i].equalsIgnoreCase("showBarre") &&
            !chaves[i].equalsIgnoreCase("suggestFingering") &&
            !chaves[i].equalsIgnoreCase("supressFifth")){

            return chaves[i];
          }
       }
     }

     return null;
   }

   public String getNextTrueElement(String previous){
   String[] chaves = (String[])this.keySet().toArray(new String[this.size()]);
   Boolean[]valores = (Boolean[])this.values().toArray(new Boolean[this.size()]);
     int valorInicial = getIndexOf(previous) + 1;
     if(valorInicial!= -1){
       for(int i=valorInicial;i<chaves.length;i++){
         if(valores[i].booleanValue()){
           if(!chaves[i].equalsIgnoreCase("calculateInversions") &&
              !chaves[i].equalsIgnoreCase("considerOctave") &&
              !chaves[i].equalsIgnoreCase("showBarre") &&
              !chaves[i].equalsIgnoreCase("suggestFingering") &&
              !chaves[i].equalsIgnoreCase("supressFifth") &&
              !chaves[i].equalsIgnoreCase(previous)){

             return chaves[i];
           }
         }
       }
     }

     return null;
   }*/

  protected int getIndexOf(String internalName) {
    int retorno = -1;
    String[] chaves = this.keySet().toArray(new String[this.size()]);
    for (int i = 0; i < chaves.length; i++) {
      if (chaves[i].equalsIgnoreCase(internalName)) {
        retorno = i;
      }
    }
    return retorno;
  }

  @SuppressWarnings("unchecked")
public Object[][] getData() {

    Object[] vet1 = display.keySet().toArray(new String[0]);
    Object[][] retorno = new Object[vet1.length][3];

    for (int i = 0; i < vet1.length; i++) {
      retorno[i][0] = display.get(vet1[i]);
      retorno[i][1] = this.get(vet1[i]);
      retorno[i][2] = vet1[i];
    }
    return retorno;
  }

  @SuppressWarnings("unchecked")
public String[] getKeys() {
    return (String[]) display.keySet().toArray(new String[0]);
  }

  public JPanel getDisplayTable() {

    JPanel panel = new JPanel();

    JScrollPane jScrollPane1 = new JScrollPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JTable tbParametros = new JTable();

    panel.setLayout(borderLayout1);
    panel.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(tbParametros, null);
    tableModel.addTableModelListener(new javax.swing.event.TableModelListener() {
      public void tableChanged(TableModelEvent e) {
        if (e.getColumn() != -1) {
          //System.out.println(tableModel.getValueAt(e.getFirstRow(),e.getColumn()));

          put(getKeys()[e.getFirstRow()], tableModel.getValueAt(e.getFirstRow(), 1));
        }
      }
    });

    tableModel.setDataVector(getData(), columnNames);
    keys = getKeys();
    tbParametros.setModel(tableModel);
    tbParametros.setPreferredScrollableViewportSize(new Dimension(100, 70));

    return panel;
  }
  public void showProperties(boolean modal){
  PropertiesWindow w = new PropertiesWindow(this, modal);
  w.setVisible(true);
}

}



 class MyTableModel extends DefaultTableModel {
     
	private static final long serialVersionUID = 1L;
	final String[] nomesColunas;

     MyTableModel(String[] nomesColunas){
       super(nomesColunas,0);
       this.nomesColunas = nomesColunas   ;

     }

     @Override
	public int getColumnCount() {
         return nomesColunas.length;
     }


     /*
      * JTable uses this method to determine the default renderer/
      * editor for each cell.  If we didn't implement this method,
      * then the last column would contain text ("true"/"false"),
      * rather than a check box.
      */
     @SuppressWarnings("unchecked")
	@Override
	public Class getColumnClass(int c) {
         return getValueAt(0, c).getClass();
     }

     /*
      * Don't need to implement this method unless your table's
      * editable.
      */
     @Override
	public boolean isCellEditable(int row, int col) {
         //Note that the data/cell address is constant,
         //no matter where the cell appears onscreen.
         if (col < 1) {
             return false;
         } else {
             return true;
         }
     }

     /*
      * Don't need to implement this method unless your table's
      * data can change.
      */
    /* public void setValueAt(Object value, int row, int col) {

         if (data[0][col] instanceof Integer
                 && !(value instanceof Integer)) {
             try {
                 data[row][col] = new Integer(value.toString());
                 fireTableCellUpdated(row, col);
             } catch (NumberFormatException e) {
               System.out.println("erro");
             }
         } else {
             data[row][col] = value;
             fireTableCellUpdated(row, col);
         }


     }*/
// end

}
  
class PropertiesWindow
     extends JDialog {
  
	private static final long serialVersionUID = 1L;
BorderLayout borderLayout1 = new BorderLayout();

   public PropertiesWindow(PropertiesDisplay propertiesDisplay,boolean modal) {
    ;
     getContentPane().setLayout(borderLayout1);
     this.add(propertiesDisplay.getDisplayTable(), BorderLayout.CENTER);
     this.setModal(modal);
          this.setSize(300, 500);


   }

 }

