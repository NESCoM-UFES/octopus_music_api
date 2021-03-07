package  octopus.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/*Classe que diz como um MusicalEventFrame dever ser visualmente apresentado ao
     usuário na tabela.*/
     class MusicalEventFrameRenderer extends JLabel  implements TableCellRenderer, CellBorder {

         protected Border noFocusBorder;
         protected Border columnBorder;
         Border border = new LinesBorder(Color.black,0);


         public MusicalEventFrameRenderer() {
           super();
           noFocusBorder = new EmptyBorder(1, 2, 1, 2);
           setOpaque(true);
         }

         public Component getTableCellRendererComponent(JTable table, Object value,
           boolean isSelected, boolean hasFocus, int row, int column) {

           MusicalEventFrame me = (MusicalEventFrame)value;
           MusicalEventFrameRenderer renderer = me.isEmpty? this: me.getObject();
           renderer.setBackground(me.getCor());
           MusicalTableModel model =(MusicalTableModel)table.getModel();
           //permite seleçao visivel

           if (isSelected) {
            // table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
             if(TimeEventPanel.selectedTool == TimeEventPanel.INSERT_TOOL){
             Color cor = ((MusicalTableModel)table.getModel()).coresLinha[row];
             setForeground(cor);
             setBackground(cor);
            } else{
               if(TimeEventPanel.selectedTool != TimeEventPanel.SPLIT_TOOL){
                 model.primeiraColunaSelecionada =  model.primeiraColunaSelecionada== -1?column: model.primeiraColunaSelecionada;
                  if(TimeEventPanel.selectedTool == TimeEventPanel.MOVE_TOOL){
                   //this.setBorder(me.getBorder());
                    setColumnBorder(me.getBorder());
                    setBackground(Color.ORANGE);
                    //setBackground(row==me.linha?Color.ORANGE:table.getBackground());
                 }else{//Select Tool
                   me.select(true);
                   setForeground(Color.cyan);
                   setBackground(Color.cyan);
                 }
               }
             }
           } else {
             //me.select(false);
	     // me.select(true);
             setForeground(table.getForeground());
             setBackground(table.getBackground());
           }


           if (!me.isEmpty){
             if (value instanceof CellBorder) {
               Border border = ((CellBorder)renderer).getBorder();
               renderer.setBorder(border);
             } else {
               if (columnBorder != null) {
                 renderer.setBorder(columnBorder);
               } else {
                 renderer.setBorder(noFocusBorder);
               }

             }
           }

          return renderer;
           //===========


           //return this;
         }

         public void setColumnBorder(Border border) {
           columnBorder = border;
         }

         public Border getColumnBorder() {
           return columnBorder;
         }

         // CellBorder
public void setBorder(Border border) {
  this.border = border;
}
public Border getBorder() {
  return border;
}
public void setBorder(Border border, int row, int col) {}
      public Border getBorder(int row, int col) { return null; }

    }
