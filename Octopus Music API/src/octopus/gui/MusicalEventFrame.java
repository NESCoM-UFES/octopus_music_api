package  octopus.gui;


import java.awt.Color;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/* Classe que representa o evento musical definido pelo usu�rio.
    * Um evento musical � uma nota (ainda n�o definida no padr�o ritmico) indicada atrav�s
    * de um sua altura (da grave at� aguda - incialmente 6 n�veis) e sua dura��o, medida
    * pelos tempos em que ocupa.
    * A representa��o visual contigua de "Evento Musical" � na verdade uma lista
    * encadeada de eventos; Ex: A nota mais grave deve iniciar a execu��o no tempo 1 do
    * 1 compasso e terminar no fim do mesmo, logo, isto vai ser repesentado por 4 eventos
    * musicais que iniciam na coluna 0 e termina na 4. O que diferencia um envento do outro
    * � o quadro (um indexador) que tamb�ma uxilia na detec��o de erros.    */
    class MusicalEventFrame implements CellBorder,Comparable, Serializable{
      int linha; // Qual a nota em fun��o da altura (grava/aguda)
      int colunaInicial; // Onde deve come�ar o evento
      int colunaFinal; // Onde deve terminar o envento
      int quadroAtual; // Qual � indexador
      boolean isEmpty; /* Indica se � um evento criado simplesmente para inicializar a tabela,
                          sem fun��o musical.*/

      Color cor; //Cada evento est� em uma trilha diferenciada visualmente pela cor
      transient Border border = new LinesBorder(Color.black,0);
      boolean isSelected = false;

      /* Usado no ato da  inicializa��o da tabela */
      MusicalEventFrame(){
        isEmpty = true;
        quadroAtual = -1;
        cor = Color.white;
        setCor(cor);

      }


      MusicalEventFrame(int linha, int colunaInicial, int colunaFinal, int quadroAtual){
        this.linha = linha;
        this.colunaInicial = colunaInicial;
        this.colunaFinal = colunaFinal;
        this.quadroAtual =  quadroAtual;
        isEmpty = false;

      }

      //Teste de Renderiza��o
      public MusicalEventFrameRenderer getObject(){
        Color cor = isSelected? Color.red:Color.BLACK;

        MusicalEventFrameRenderer t = new MusicalEventFrameRenderer();
        if(isSingleTempo()){
          t.setBorder(BorderFactory.createLineBorder(cor,2));
        }else{
          if(quadroAtual==0){
            t.setBorder(BorderFactory.createMatteBorder(2,2,2,0,cor));
          }else{
            if((colunaFinal - colunaInicial) == quadroAtual){
              t.setBorder(BorderFactory.createMatteBorder(2,0,2,2,cor));
            }else{
              t.setBorder(BorderFactory.createMatteBorder(2,0,2,0,cor));
            }
          }
        }
        return t;
      }

      public void select(boolean valor){
        //for(int i =this.colunaInicial; i<= this.colunaFinal;i++){
          isSelected = valor;
        //}

      }

      public void setCor(Color cor){
        this.cor = cor;
      }

      public Color getCor(){
        return cor;
      }
      public int getDuracao(){
        return (colunaFinal - colunaInicial);
      }

     /* Indica se o envento come�a e termina em um �nico tempo do compasso. */
      public boolean isSingleTempo(){
        boolean retorno;
        if (getDuracao()>0){
          retorno = false;
        }else{
          retorno = true;
        }
        return retorno;
      }

      public void setEmpty(){
        isEmpty = true;
        quadroAtual = -1;
        cor = Color.white;
        setCor(cor);
      }

      // CellBorder
      @Override
	public void setBorder(Border border) {
        this.border = border;
      }
      @Override
	public Border getBorder() {
        return border;
      }
      @Override
	public void setBorder(Border border, int row, int col) {}
      @Override
	public Border getBorder(int row, int col) { return null; }

      public String getHashKey(){
	return ( String.valueOf(linha) + String.valueOf(colunaInicial) + String.valueOf(colunaFinal));
      }


      //ordenacao por coluna e lihna (esquerda mais alta)
      @Override
	public int compareTo(Object o){
        MusicalEventFrame musicalEventFrame = (MusicalEventFrame)o;
        int retorno =1;
        if (musicalEventFrame.colunaInicial < this.colunaInicial){
          retorno = -1;
        }else{
            if (musicalEventFrame.colunaInicial == this.colunaInicial){
              retorno = 0;
              if (musicalEventFrame.linha < this.linha){
                retorno = -1;
              }else{
                if (musicalEventFrame.linha > this.linha){
                  retorno = 1;
                }

              }
            }
          }
        return retorno;
      }
    }
