package  octopus.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.SwingConstants;




public class TimeEventPanel extends JPanel  {

  /*
   * qtNotasSimultaneas: Linhas na Tabela. Default: 6
   * qtCompassos: Marcação a cada qtTemposCompasso. Default: 1
   * qtTemposCompasso: Figura de Tempo. Default: 4 (Quarter Note)
   * Quantidade de Colunas =  qtCompassos * qtTemposCompasso
   */
  private int qtNotasSimultaneas, qtCompassos, qtTemposCompasso;

   //JTable table;
   //TableModel tableModel;
   public MusicalTable table;
   JScrollPane scrollPane;
   JToolBar toolBar;


   //valores possíveis: 0 - Insert; 1 - Delete; 2 - Split; 3 - ZoomIn; 4 -ZoomOut;
   // 5 - Move; 6- Select

  static final int INSERT_TOOL = 0;
  static final int DELETE_TOOL = 1;
  static final int SPLIT_TOOL = 2;
  static final int MOVE_TOOL = 5;
  static final int SELECT_TOOL = 6;




   static int selectedTool= 0;

   //public MusicalTableModel data;



  /** Construtor usado para alterar a configuração básica do violão.*/
   public TimeEventPanel(int qtNotasSimultaneas, int qtCompassos, int qtTemposCompasso) {
    try {

      this.qtNotasSimultaneas = qtNotasSimultaneas;
      this.qtCompassos = qtCompassos;
      this.qtTemposCompasso = qtTemposCompasso;

      configuraComponente();

    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Construtor padrão configurado para necessidades básicas do sistema.*/
  public TimeEventPanel() {
    try {
      this.qtNotasSimultaneas = 4;
      this.qtCompassos = 2;
      this.qtTemposCompasso = 4;

      configuraComponente();
     }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }



 /* public void setPadraoRitmico(PadraoRitmico padraoRitmico){
  
	  
	 this.qtNotasSimultaneas = padraoRitmico.notasSimultaneas;
    this.qtTemposCompasso = padraoRitmico.temposCompasso;
    table.setModel(padraoRitmico);
    padraoRitmico.fireTableStructureChanged();
    dimensionarTabela();
  }*/

  /** Método usado para configurar os parametros iniciais do componente como um todo
   * e não somente da tabela, que pode ser alterada mesmo depois da criação do componente.
   */
  private void configuraComponente(){
		/*try {
	 File file = new File("images/testeLocal.txt");
	
	 FileOutputStream fOut;

		fOut = new FileOutputStream(file);
	
     ObjectOutput out = new ObjectOutputStream(fOut);
     out.writeObject(this);
     out.flush();
     out.close();
     System.out.println(file.getAbsolutePath());
	 JOptionPane.showMessageDialog(this, file.getAbsolutePath());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
	 
	 
	//table = new JTable();
    table = new MusicalTable(qtNotasSimultaneas, qtCompassos,qtTemposCompasso);
    scrollPane = new JScrollPane(table);

    //Painel de Ferramentas
   // toolBar = new JToolBar("Ferramentas",JToolBar.HORIZONTAL);
    toolBar = new JToolBar("Ferramentas",SwingConstants.VERTICAL);
    configuraToolBar();
    //configuraTabela();


   this.addComponentListener( new ComponentAdapter(){
     @Override
	public void componentResized(ComponentEvent e){
      //System.out.println("dimensionei");
      dimensionarTabela();
     }
   });

   this.setLayout(new BorderLayout());
   this.add(scrollPane,BorderLayout.CENTER);
   this.add(toolBar,BorderLayout.WEST);


  }

  private void actionInsert(){
    TimeEventPanel.selectedTool = 0;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Cursor cursor = toolkit.createCustomCursor(new ImageIcon("images/insertCursor.gif").getImage(),new Point(0,0),"MyCur");
    table.setCursor(cursor);
  }
  private void configuraToolBar(){
    ButtonGroup buttonGroup = new ButtonGroup();

    JToggleButton btInsert = new JToggleButton("Insert", new ImageIcon("images/insert.gif"));
    btInsert.addActionListener(new java.awt.event.ActionListener() {
      @Override
	public void actionPerformed(ActionEvent e) {
        actionInsert();
      }
    });
        btInsert.setSelected(true);
        actionInsert();
   // this.setCursor(new Cursor(Cursor.MOVE_CURSOR));

    JToggleButton btDelete = new JToggleButton("Delete", new ImageIcon("images/delete.gif"));
    btDelete.addActionListener(new java.awt.event.ActionListener() {
        @Override
		public void actionPerformed(ActionEvent e) {
          TimeEventPanel.selectedTool = 1;
          Toolkit toolkit = Toolkit.getDefaultToolkit();
          Cursor cursor = toolkit.createCustomCursor(new ImageIcon("images/delete.gif").getImage(),new Point(0,0),"MyCur");
          table.setCursor(cursor);
        }
    });

    JToggleButton btSplit = new JToggleButton("Split", new ImageIcon("images/split.gif"));
    btSplit.addActionListener(new java.awt.event.ActionListener() {
        @Override
		public void actionPerformed(ActionEvent e) {
          TimeEventPanel.selectedTool = 2;
          Toolkit toolkit = Toolkit.getDefaultToolkit();
          Cursor cursor = toolkit.createCustomCursor(new ImageIcon("images/split.gif").getImage(),new Point(0,0),"MyCur");
          table.setCursor(cursor);
        }
    });


    JToggleButton btMove = new JToggleButton("Move", new ImageIcon("images/move.gif"));
    btMove.addActionListener(new java.awt.event.ActionListener() {
        @Override
		public void actionPerformed(ActionEvent e) {
          TimeEventPanel.selectedTool = 5;
          Toolkit toolkit = Toolkit.getDefaultToolkit();
//          Cursor cursor = toolkit.createCustomCursor(new ImageIcon("images/move.gif").getImage(),new Point(0,0),"MyCur");
          Cursor cursor = new Cursor(Cursor.MOVE_CURSOR);
          table.setCursor(cursor);
          MusicalTableModel.primeiraColunaSelecionada = -1;
        }
    });

    JToggleButton btSelect = new JToggleButton("Select", new ImageIcon("images/select.gif"));
    btSelect.addActionListener(new java.awt.event.ActionListener() {
        @Override
		public void actionPerformed(ActionEvent e) {
          TimeEventPanel.selectedTool = 6;
          Toolkit toolkit = Toolkit.getDefaultToolkit();
          Cursor cursor = toolkit.createCustomCursor(new ImageIcon("images/select.gif").getImage(),new Point(0,0),"MyCur");
          table.setCursor(cursor);
        }
    });

    JToggleButton btZoomIn = new JToggleButton("Zoom In", new ImageIcon("images/zoom.gif"));
    btZoomIn.addActionListener(new java.awt.event.ActionListener() {
        @Override
		public void actionPerformed(ActionEvent e) {
          TimeEventPanel.selectedTool = 3;
          Toolkit toolkit = Toolkit.getDefaultToolkit();
          Cursor cursor = toolkit.createCustomCursor(new ImageIcon("images/zoom.gif").getImage(),new Point(0,0),"MyCur");
          table.setCursor(cursor);
        }
    });

    JToggleButton btZoomOut = new JToggleButton("Zoom Out", new ImageIcon("images/zoom.gif"));
    btZoomOut.addActionListener(new java.awt.event.ActionListener() {
        @Override
		public void actionPerformed(ActionEvent e) {
          TimeEventPanel.selectedTool = 4;
          Toolkit toolkit = Toolkit.getDefaultToolkit();
          Cursor cursor = toolkit.createCustomCursor(new ImageIcon("images/zoom.gif").getImage(),new Point(0,0),"MyCur");
          table.setCursor(cursor);
        }
    });

    buttonGroup.add(btInsert);
    buttonGroup.add(btDelete);
    buttonGroup.add(btSplit);
    buttonGroup.add(btMove);
    buttonGroup.add(btSelect);
    buttonGroup.add(btZoomIn);
    buttonGroup.add(btZoomOut);

    //toolBar.setLayout(new VerticalFlowLayout());

    toolBar.add(btInsert);
    toolBar.add(btDelete);
    toolBar.add(btSplit);
    toolBar.add(btMove);
    toolBar.add(btSelect);
    toolBar.add(btZoomIn);
    toolBar.add(btZoomOut);

   
  }

  public JToolBar getToolBar(){
    return toolBar;
  }


  private void dimensionarTabela(){
   // scrollPane.setSize(new Dimension((int)this.getSize().getWidth(),
     //    table.getRowHeight()*this.qtNotasSimultaneas +1));
   // table.setPreferredScrollableViewportSize(new Dimension((int)this.getSize().getWidth(),
       //  table.getRowHeight()*this.qtNotasSimultaneas +1));
   // table.repaint();
    int rowHeight = (int)((this.getSize().getHeight()- table.getTableHeader().getHeight())/table.getRowCount());
    if (rowHeight>0){
      table.setRowHeight(rowHeight);

      ListModel lm = new AbstractListModel() {
        //String headers[] = {"6", "5", "4", "3", "2", "1"}; // Talvez inclua nome das notas
        @Override
		public int getSize() { return qtNotasSimultaneas; }
        @Override
		public Object getElementAt(int index) {
          return  String.valueOf(qtNotasSimultaneas - index);
        }
    };

      JList rowHeader = new JList(lm);
      rowHeader.setFixedCellWidth(20);
      rowHeader.setFixedCellHeight(table.getRowHeight());// + table.getRowMargin());
      rowHeader.setCellRenderer(new RowHeaderRenderer(table));
      scrollPane.setRowHeaderView(rowHeader);

       table.setPreferredScrollableViewportSize(this.getSize());

    }
  }
 public void ZoomIn(){

 }


 public MusicalEvent[] getSelectedMusicalEvents(){
   return ((MusicalTableModel)table.getModel()).getSelectedMusicalEvents();
 }


  public static void main(String[] args) {
    final TimeEventPanel timeEventPanel1 = new TimeEventPanel();
    Frame frame;
    frame = new Frame();
    frame.setTitle("Teste");
    frame.add(timeEventPanel1, BorderLayout.CENTER);
   
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    timeEventPanel1.setSize(d.width/2,d.height/2); // verificar se é correto fazer isso.
    frame.setSize(timeEventPanel1.getSize());
    frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
    frame.setVisible(true);
    frame.addWindowListener(new WindowAdapter() {
            @Override
			public void windowClosing(WindowEvent e) {
                //timeEventPanel1.imprimeNotacao();
                System.exit(0);
            }
        });

  }

}
