package  octopus.gui;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/* Classe responsável por tratar o evento que é disparado quando se clica
  * em uma célula da tabela ou definição de um seleção.
  */
  class MarcacaoListener implements ListSelectionListener {
  MusicalTable table;

   MarcacaoListener(MusicalTable table){
    this.table = table;
   }

    public void valueChanged(ListSelectionEvent e) {
          //Ignore extra messages.
           if (e.getValueIsAdjusting()) return;

           int[] colunas = table.getSelectedColumns();
           int[] linhas = table.getSelectedRows();
	   int colunaInicial = colunas[0];
	   int opType = -1;
	    MusicalTableModel model = (MusicalTableModel)table.getModel();

	   for (int i=0; i<linhas.length;i++){
               int  linha = linhas[i];
               int colunaFinal = colunas.length>0?colunas[colunas.length -1]:0;

               //valores possíveis: 0 - Insert; 1 - Delete; 2 - Split; 3 - ZoomIn; 4 -ZoomOut;
               // 5 - Move; 6- Select
               switch  (TimeEventPanel.selectedTool) {
                 case 0:
		  //model.clearSelection();
		  model.setMusicalEventFrameAt(linha,colunaInicial,colunaFinal,true);
		  model.selectMusicalEventFrames(linha,colunaInicial,colunaFinal,false);
		  opType = 0;
		  //table.observable.publicarOperacao(0);
		   break;
                 case 1:
		  model.clearSelection();
                  for (int c=0; c<colunas.length;c++){
                   model.deleteMusicalEventFrame(linha,colunas[c]);
                  }
                  opType = 1;
		  break;
                 case 2:
		   model.clearSelection();
		   model.splitMusicalEventFrame(linha,colunaInicial);
		   table.observable.publicarOperacao(2);
		   opType = 2;
		   break;
                 case 3:  JOptionPane.showMessageDialog(table,"Funcionalidade ainda não implementada",
                            "Aviso",JOptionPane.INFORMATION_MESSAGE); break;
                 case 4:  JOptionPane.showMessageDialog(table,"Funcionalidade ainda não implementada",
                            "Aviso",JOptionPane.INFORMATION_MESSAGE);; break;
                 case 5:
                     int y1;
                     int y2;
                    /* if(model.getMusicalEventFrame(e.getFirstIndex(),colunas[0]).isEmpty){
                       y1 = colunas[colunas.length -1];
                       y2 = colunas[0];
                     }else{
                       y1 = colunas[0];
                       y2 = colunas[colunas.length -1];
                     }*/
                     if (model.primeiraColunaSelecionada == colunas[0] ){ //esquerda=>direita
                       y1 = colunas[0];
                       y2 = colunas[colunas.length -1];
                      }else{  //direita=>esquerda
                       y1 = colunas[colunas.length -1];
                       y2 = colunas[0];
                       }

                     if((model.selectedEventFrames.size()<=1)){
                       model.clearSelection();
		       model.moveMusicalEventFrame(e.getFirstIndex(),y1,y2);
                      }else{
                        JOptionPane.showMessageDialog(table,"Funcionalidade ainda não implementada",
                            "Aviso",JOptionPane.INFORMATION_MESSAGE);
                        //model.moveSelectedMusicalEventFrame(y2);
                        //model.selectedEventFrames.clear();
			model.clearSelection();
                        model.primeiraColunaSelecionada = -1;
                      }
		      opType = 5;
                     break;
                   case 6:
		    if(i>0){
		      model.selectMusicalEventFrames(linhas[i],colunas[0],colunas[colunas.length-1],true);
	             }else{
		       model.selectMusicalEventFrames(linhas[i],colunas[0],colunas[colunas.length-1],false);
		     }
		     opType = 6;
		     //table.observable.publicarOperacao(6);
                     break;
               }

             }
	     if (opType != -1){
	       table.observable.publicarOperacao(opType);
	     }

	     //}
           table.getSelectionModel().clearSelection();
      }


  }