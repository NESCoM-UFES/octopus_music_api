package octopus.gui;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;


public class MusicalTable extends JTable {

  public MusicalTableObservable observable = new MusicalTableObservable();
  public MusicalTableModel data;

  private int qtNotasSimultaneas, qtCompassos, qtTemposCompasso;

  public MusicalTable(int qtNotasSimultaneas, int qtCompassos, int qtTemposCompasso) {

    this.qtNotasSimultaneas = qtNotasSimultaneas;
    this.qtCompassos = qtCompassos;
    this.qtTemposCompasso = qtTemposCompasso;

    configuraTabela();
  }

 /* public void setPadraoRitmico(PadraoRitmico padraoRitmico){
    this.qtNotasSimultaneas = padraoRitmico.notasSimultaneas;
    this.qtTemposCompasso = padraoRitmico.temposCompasso;
    this.setModel(padraoRitmico);
    padraoRitmico.fireTableStructureChanged();
  }*/
  
  private void configuraTabela(){
   // Definindo qtd. de linhas e colunas;
   int qtTotalColunas = qtCompassos * qtTemposCompasso;
   int qtColunasTempo = qtTemposCompasso;


   // Definindo o Renderer (MusicalEventFrameRenderer) para os objetos do tipo MusicalEventFrame;
   this.setDefaultRenderer(MusicalEventFrame.class, new MusicalEventFrameRenderer());
   this.setCellSelectionEnabled(true);
   this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
   this.setIntercellSpacing(new Dimension(0,2));

   //Definindo modelo de dados;
   data = new MusicalTableModel(qtNotasSimultaneas,qtCompassos, qtTemposCompasso);
   this.setModel(data);

   // Reponsável por perceber o evento do clique.
   MarcacaoListener marcacaoListener = new MarcacaoListener(this);
   this.getSelectionModel().addListSelectionListener(marcacaoListener);


   this.getTableHeader().setReorderingAllowed(false);
   this.getTableHeader().setResizingAllowed(false);

}

public MusicalEvent[] getSelectedMusicalEvents(){
 return data.getSelectedMusicalEvents();
}

// Debug do que está na tabela
public void imprimeNotacao(){
  int qtC = this.getModel().getColumnCount();
  int qtL = this.getModel().getRowCount();
  System.out.println("=============== ## Musica Events Frames ## ===============");
  for (int l=0;l<qtL;l++){
    for(int c=0;c<qtC;c++){
      MusicalEventFrame me = ((MusicalEventFrame)((this.getModel()).getValueAt(l,c)));
     if (!me.isEmpty){
       System.out.println("Linha: " + me.linha +
			 " Coluna Inicio: " + this.getColumnName(me.colunaInicial) +
			 " Coluna Fim: " + this.getColumnName(me.colunaFinal) +
			 " Frame Atual:"  + me.quadroAtual);
     }
    }
  }
  System.out.println("=============== ## Musical Events  ## ===============");
  MusicalTableModel musicalTableModel =(MusicalTableModel)this.getModel();
  MusicalEvent[] meVector = musicalTableModel.getAllMusicalEvents();
   for (int i = 0; i< meVector.length; i++){
     MusicalEvent me = meVector[i];
     System.out.println( i+1 + ": Linha: " + me.getLinha()+
     " Duracao: "+me.getDuracao() + " iniciando no tempo " + me.getTempoDentroCompasso(musicalTableModel.temposCompasso) + " do compasso " + me.getCompassoInicial(musicalTableModel.temposCompasso) );
    }
  //PadraoRitmico pr = new PadraoRitmico((MusicalTableModel)this.getModel());
  //pr.play();
  }
}