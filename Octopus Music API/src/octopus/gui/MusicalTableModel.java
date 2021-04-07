package  octopus.gui;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/* Modelo de dados da tabela. Diz como está organizados os MusicalEventFrame's na
    * tabela, ou seja, a  composioção;*/
   public class MusicalTableModel extends AbstractTableModel implements Serializable {
          public ArrayList columnNames = new ArrayList();
          public Object[][] data;

	  public Hashtable musicalEvents = new Hashtable();
	  public Vector arpejos = new Vector();

          public ArrayList selectedEventFrames = new ArrayList();
          public static int primeiraColunaSelecionada = -1;

          public int qtCompassos;
          public int temposCompasso;
          public int notasSimultaneas;

	  public int notasSimultaneasArpejo = 0;
          public double referenciaTempo = 1.0; //Quarter Note


          Color[] coresLinha = {new Color(204,204,255),new Color(255,255,0),
                                new Color(0,204,51),new Color(0,0,204),
                                new Color(204,0,0),new Color(204,0,204)};

          protected MusicalTableModel(int notasSimultaneas,int qtCompassos, int temposCompasso){
            super();
            this.qtCompassos = qtCompassos;
            this.temposCompasso = temposCompasso;
            this.notasSimultaneas = notasSimultaneas;
            int qtColunas = qtCompassos * temposCompasso;
            data = new Object[notasSimultaneas][qtColunas];

	    inicializaArpejos();

	    rotulaColunas();
            reset();

          }

	  private void inicializaArpejos(){
	    arpejos = new Vector(qtCompassos * temposCompasso);

	    for (int i=0; i<(qtCompassos * temposCompasso);i++){
	      arpejos.add(new Arpejo(Arpejo.DIRECAO_DESCENDENTE,  Byte.parseByte("0"), true));
	    }
	  }
	  public void reset(){
            int qtColunas = qtCompassos * temposCompasso;
            Color corVazio =  Color.white;
            for (int l=0; l< notasSimultaneas;l++){
              for(int c=0;c<qtColunas;c++){
                 data[l][c] =  new MusicalEventFrame();

              }
            }
	    this.fireTableDataChanged();
          }

         /* private void rotulaColunas(){

            for (int i=0; i< qtCompassos; i++){
              for (int j=0; j<temposCompasso;j++){
                 String nomeCol =  String.valueOf(j+1) + "/" + String.valueOf(i+1);
                 columnNames.add(nomeCol);
              }
            }

          }*/
	  private void rotulaColunas(){

	    for (int i=0; i< (qtCompassos*temposCompasso); i++){
		 String nomeCol =   String.valueOf(i+1);
		 columnNames.add(nomeCol);
	    }

          }



          @Override
		public int getColumnCount() {
               return columnNames.size();
           }

           @Override
		public int getRowCount() {
               return data.length;
           }

           @Override
		public String getColumnName(int col) {
               return ((String)columnNames.get(col));
           }

           @Override
		public Object getValueAt(int row, int col) {
               return data[row][col];
           }

           /*
            * JTable uses this method to determine the default renderer/
            * editor for each cell.  If we didn't implement this method,
            * then the last column would contain text ("true"/"false"),
            * rather than a check box.
            */
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

           @Override
		public void setValueAt(Object value, int row, int col) {
               data[row][col] = value;
               fireTableCellUpdated(row, col);
            }

            public MusicalEventFrame getMusicalEventFrame(int row, int col){
               MusicalEventFrame musicalEventFrame = (MusicalEventFrame)data[row][col];
               return musicalEventFrame;
            }

	    public void deleteMusicalEventFrame(int linha, int colunaInicial, int colunaFinal){
		MusicalEventFrame me;
	        while (true){
		  if(colunaInicial <= colunaFinal){
		    me = (MusicalEventFrame)data[linha][colunaInicial];
		    if(me.isEmpty ==false){
		      int temp =me.colunaFinal;
		      deleteMusicalEventFrame(me.linha, me.colunaInicial);
		      colunaInicial = temp;
		    }
		    colunaInicial++;
		  }else{
		    break;
		  }
		}
		 fireTableRowsUpdated(linha,linha);
           }

	    public void deleteMusicalEventFrame(int row, int col){
             if((row>-1) && (!((MusicalEventFrame)data[row][col]).isEmpty)){
              MusicalEventFrame musicalEventFrame = (MusicalEventFrame)data[row][col];

	       String key = String.valueOf(row) + String.valueOf(musicalEventFrame.colunaInicial) + String.valueOf(musicalEventFrame.colunaFinal);
	       musicalEvents.remove(key);

	       /*try{
		 arpejos.remove(musicalEventFrame.colunaInicial);
	       }catch(ArrayIndexOutOfBoundsException ex){};*/


               for(int i= musicalEventFrame.colunaInicial; i<=musicalEventFrame.colunaFinal;i++){
                 ((MusicalEventFrame)data[row][i]).setEmpty();
               }

	       updataRowMusicalEventFrames(row);
               fireTableRowsUpdated(row,row);
             }
            }

            public void setMusicalEventFrameAt(MusicalEventFrame musicalEventFrame) {
              setMusicalEventFrameAt(musicalEventFrame.linha,musicalEventFrame.colunaInicial,musicalEventFrame.colunaFinal,true);
            }

            public void setMusicalEventFrameAt(int row, int colunaInicial, int colunaFinal,boolean updateRow) {
             if(row >= 0){

	       if (updateRow){
		 deleteMusicalEventFrame(row,colunaInicial,colunaFinal);
	       }

	       int frame = 0;
	       Vector meFrames = new Vector();
               for (int i=colunaInicial;i<=colunaFinal;i++){
                 MusicalEventFrame musicalEventFrame = new MusicalEventFrame(row,colunaInicial,colunaFinal,frame++);
                 musicalEventFrame.setCor(coresLinha[musicalEventFrame.linha]);
                 data[row][i] = musicalEventFrame;

		 meFrames.add(musicalEventFrame);
               }

	       String key = String.valueOf(row) + String.valueOf(colunaInicial) + String.valueOf(colunaFinal);
	       musicalEvents.put(key, new MusicalEvent(meFrames));

	       if(updateRow){
		 updataRowMusicalEventFrames(row);
               }
	       fireTableRowsUpdated(row,row);
             }
            }

	    //private void limparMusicalEvents

            public void setArpejo(Arpejo arpejo, int tempoAtaque){
	      arpejos.add(tempoAtaque, arpejo);
	    }

	    public Arpejo getArpejo(int tempoAtaque){
	      return (Arpejo)arpejos.get(tempoAtaque);
	    }

	    // Verifica se existem arpejos(mesmo tempo) com polifonia maxima, ou seja,
	    // se existe necessidade das notas das representaçòes dos acordes estarem contiguas.
	    public boolean containsArpejoCompleto(){
	      for(int i=0; i<this.temposCompasso;i++){
		MusicalEvent[] mes = this.getMusicalEvents(i);
		if(mes.length == this.notasSimultaneas){
		  return true;
		}
	      }
	      return false;
	    }


	    public void splitMusicalEventFrame(int row, int col){
             if (row >-1){
              MusicalEventFrame me = (MusicalEventFrame)data[row][col];

	      String key = String.valueOf(row) + String.valueOf(me.colunaInicial) + String.valueOf(me.colunaFinal);
              musicalEvents.remove(key);

                ((MusicalEventFrame)data[row][me.colunaInicial]).colunaFinal = col;
                updataRowMusicalEventFrames(row);
                fireTableRowsUpdated(row,row);
             // }
             }
            }

           public void moveMusicalEventFrame(int linhaOrigem, int colunaOrigem, int colunaDestino){
             if(linhaOrigem>-1){
             MusicalEventFrame me = (MusicalEventFrame)data[linhaOrigem][colunaOrigem];
             if (!me.isEmpty){
               int duracao = me.colunaFinal - me.colunaInicial;
               int colunaInicio = colunaDestino -  me.quadroAtual;

	       String key = String.valueOf(linhaOrigem) + String.valueOf(me.colunaInicial) + String.valueOf(me.colunaFinal);
	       MusicalEvent meOriginal = (MusicalEvent) musicalEvents.get(key);

               if ((colunaInicio+duracao  < this.getColumnCount()) && (colunaInicio >= 0)){
                 this.deleteMusicalEventFrame(linhaOrigem,colunaOrigem);
                 this.setMusicalEventFrameAt(linhaOrigem,colunaInicio,colunaInicio+duracao,true);

		 key = String.valueOf(linhaOrigem) + String.valueOf(colunaInicio) + String.valueOf(colunaInicio+duracao);
		 MusicalEvent meNovo = (MusicalEvent) musicalEvents.get(key);

		 //Copiando os valores após movido o evento;
		 meNovo.volume = meOriginal.volume;
                 meNovo.efeitos =  meOriginal.efeitos;


		 } else{
                //System.out.println("Não pode - excedeu o limite");
               }
             }
             MusicalTableModel.primeiraColunaSelecionada = -1;
             }
           }

           public void moveSelectedMusicalEventFrame(int colunaDestino){
             int distancia = MusicalTableModel.primeiraColunaSelecionada - colunaDestino;
             MusicalEventFrame me;
             Collections.sort(selectedEventFrames);
             for(int i=0;i<selectedEventFrames.size();i++){
               me = (MusicalEventFrame)selectedEventFrames.get(i);

               moveMusicalEventFrame(me.linha,me.colunaInicial+me.quadroAtual,colunaDestino);
             }
                selectedEventFrames.clear();
                MusicalTableModel.primeiraColunaSelecionada = -1;
              }

/*           public void selectMusicalEventFrames(int linha, int colunaInicial, int colunaFinal){
            MusicalEventFrame me;
             while (true){
               if(colunaInicial <= colunaFinal){
                 me = (MusicalEventFrame)data[linha][colunaInicial];
                 if(me.isEmpty ==false){
                   me.select(true);
                   selectedEventFrames.add(me);
                   colunaInicial =me.colunaFinal;
                 }
                 colunaInicial++;
               }else{
                 break;
               }
             }
              fireTableRowsUpdated(linha,linha);
           }*/

              private void selectWholeMusicalEventFrame(MusicalEventFrame  me, boolean selected){
                for (int i = me.colunaInicial; i<=me.colunaFinal;i++){
                  ((MusicalEventFrame)data[me.linha][i]).select(selected);
                }
              }
              public void clearSelection(){
		for(int i=0;i<selectedEventFrames.size();i++){
		  MusicalEventFrame mf = (MusicalEventFrame)selectedEventFrames.get(i);
		  selectWholeMusicalEventFrame(mf,false);
		}
	        selectedEventFrames.clear();
	      }
	      public void selectMusicalEventFrames(int linha, int colunaInicial, int colunaFinal, boolean append){
		MusicalEventFrame me;
		if(!append) {
		  clearSelection();
		}
                while (true){
                  if(colunaInicial <= colunaFinal){
                    me = (MusicalEventFrame)data[linha][colunaInicial];
                    if(me.isEmpty ==false){
                      //me.select(true);
                      selectWholeMusicalEventFrame(me, true);
                      selectedEventFrames.add(me);
                      colunaInicial =me.colunaFinal;
                    }
                    colunaInicial++;
                  }else{
                    break;
                  }
                }
                 fireTableRowsUpdated(linha,linha);
           }

           public MusicalEvent getMusicalEvent(MusicalEventFrame meFrame){
             MusicalEvent me;
             /*Vector vetorMusicalEventFrames = new Vector();
             for(int i = meFrame.colunaInicial; i<= meFrame.colunaFinal;i++){
               vetorMusicalEventFrames.add((MusicalEventFrame)data[meFrame.linha][i]);
             }

             me = new MusicalEvent(vetorMusicalEventFrames, this);*/

	     String key = String.valueOf(meFrame.linha) + String.valueOf(meFrame.colunaInicial) + String.valueOf(meFrame.colunaFinal);
	     return (MusicalEvent)musicalEvents.get(key);

             //return me;
           }

          public MusicalEvent[] getSelectedMusicalEvents(){
	    Vector selectedEvents = new Vector();
	    for (int i=0; i<selectedEventFrames.size();i++){
	      MusicalEventFrame me = (MusicalEventFrame)selectedEventFrames.get(i);
	      String key = String.valueOf(me.linha) + String.valueOf(me.colunaInicial) + String.valueOf(me.colunaFinal);

	      selectedEvents.add(musicalEvents.get(key));
	    }

	    return (MusicalEvent[])selectedEvents.toArray(new MusicalEvent[0]);
	  }

	 /* public Collection getAllMusicalEvents(){
           Vector vetorMusicalEvents = new Vector();
           int linha= 0;
           int coluna = 0;
           while(true){
             MusicalEventFrame meFrame = ((MusicalEventFrame)data[linha][coluna]);
             if(!meFrame.isEmpty){
               vetorMusicalEvents.add(this.getMusicalEvent(meFrame));
               coluna = meFrame.colunaFinal;
             }
             if(++coluna>=this.getColumnCount()){
               coluna=0;
               if(++linha>=this.getRowCount()){
                 break;
               }
             }
           }
           return vetorMusicalEvents;
         }*/

	  public MusicalEvent[] getAllMusicalEvents(){
	    return (MusicalEvent[]) musicalEvents.values().toArray(new MusicalEvent[0]);
	  }

	  public MusicalEvent[] getMusicalEvents(int tempo){
	    Vector retorno = new Vector();
	    for (int i= (notasSimultaneas -1); i >-1; i--){
	      MusicalEventFrame mf = getMusicalEventFrame(i,tempo);
	      if((!mf.isEmpty) && (mf.quadroAtual==0)){
		retorno.add(musicalEvents.get(mf.getHashKey()));
	      }
	    }
	    return (MusicalEvent[]) retorno.toArray(new MusicalEvent[0]);
	  }

	  public boolean isAtaqueJunto(MusicalEvent[] musicalEvents){
	    if( musicalEvents.length>1){
	      int tempoI = musicalEvents[0].getPrimeiroTempo();
	      for(int i=1; i<musicalEvents.length;i++){
		if (musicalEvents[i].getPrimeiroTempo()!=tempoI){
		  return false;
		}
	      }
	      return true;
	    }else{
	      return false;
	    }
	  }

           /*Atualiza a representação gráfica dos rítmos. */
           private void updataRowMusicalEventFrames(int linha){
             int col = 0;

              while (col<columnNames.size()){

                MusicalEventFrame musicalEventFrame = (MusicalEventFrame)data[linha][col];
                if(!musicalEventFrame.isEmpty){
                  int provavelErro = checkMusicalEventFrame(musicalEventFrame);
                  if(provavelErro != -1){
                    int colunaErro = provavelErro ;

                    if(musicalEventFrame.quadroAtual == 0){
                      //Conserta colunas anteriores ao erro;

                      setMusicalEventFrameAt(linha,musicalEventFrame.colunaInicial,colunaErro - 1,false);

                      //Conserta depois

                      musicalEventFrame = (MusicalEventFrame)data[linha][colunaErro];
                      setMusicalEventFrameAt(linha,colunaErro,musicalEventFrame.colunaFinal,false);
                    }else{
                     if(TimeEventPanel.selectedTool == 2){ //split
                       setMusicalEventFrameAt(linha,musicalEventFrame.colunaInicial,colunaErro - 1,false);
                     }
                     musicalEventFrame = (MusicalEventFrame)data[linha][colunaErro];
                     setMusicalEventFrameAt(linha,colunaErro ,musicalEventFrame.colunaFinal,false);
                    }
                  }
                  col = musicalEventFrame.colunaFinal;
                }
                col++;
              }
            }

           /*Testa a continuidade do MusicalEventFrame e retorno a coluna em que houve a descontinuidade.
            *Se não houver descontinuidade, retorno 0;  */
          private int checkMusicalEventFrame(MusicalEventFrame musicalEventFrame){
             int retorno = -1;
             int frameCheck = 0;
             int row = musicalEventFrame.linha;
             if(musicalEventFrame.quadroAtual==0){
               for(int i=musicalEventFrame.colunaInicial; i<=musicalEventFrame.colunaFinal;i++){
                 MusicalEventFrame me = (MusicalEventFrame)data[row][i];
                 if ((me.quadroAtual != frameCheck)){//||
                       //((me.colunaFinal-me.colunaInicial)>me.quadroAtual)){
                   retorno = i;
                   break;
                 }
                 frameCheck++;
               }
             }else{
               //Problema no primeiro elemento de uma possível sequencia;
               retorno = musicalEventFrame.colunaInicial + musicalEventFrame.quadroAtual;
             }
             return retorno;
           }

          /* private void writeObject(java.io.ObjectOutputStream out)throws IOException{
             out.writeInt(notasSimultaneas);
             out.writeInt(temposCompasso);
             out.writeObject(columnNames);
             out.writeObject(data);
             out.writeObject(musicalEvents);
             out.writeObject(arpejos);
             out.writeObject(selectedEventFrames);
             out.writeInt(primeiraColunaSelecionada);
             out.writeDouble(referenciaTempo);
            }

            private void readObject(java.io.ObjectInputStream in)throws IOException, ClassNotFoundException{
              notasSimultaneas = in.readInt();
              temposCompasso = in.readInt();
              columnNames = (ArrayList)in.readObject();
              data = (Object[][])in.readObject();
              musicalEvents= (Hashtable)in.readObject();
              arpejos = (Vector)in.readObject();
              selectedEventFrames = (ArrayList)in.readObject();
              primeiraColunaSelecionada = in.readInt();
              referenciaTempo = in.readDouble();
            }*/



            /* private void readObject(java.io.ObjectInputStream in)throws IOException, ClassNotFoundException{
              System.out.println("Ok - sr1");
            }*/

       }//fim da classe