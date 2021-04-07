package  octopus.gui;


import java.awt.Color;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/* Classe que representa o evento musical definido pelo usuário.
    * Um evento musical é uma nota (ainda não definida no padrão ritmico) indicada através
    * de um sua altura (da grave até aguda - incialmente 6 níveis) e sua duração, medida
    * pelos tempos em que ocupa.
    * A representação visual contigua de "Evento Musical" é na verdade uma lista
    * encadeada de eventos; Ex: A nota mais grave deve iniciar a execução no tempo 1 do
    * 1 compasso e terminar no fim do mesmo, logo, isto vai ser repesentado por 4 eventos
    * musicais que iniciam na coluna 0 e termina na 4. O que diferencia um envento do outro
    * é o quadro (um indexador) que tambéma uxilia na detecção de erros.    */
    class MusicalEventFrame implements CellBorder,Comparable, Serializable{
      int linha; // Qual a nota em função da altura (grava/aguda)
      int colunaInicial; // Onde deve começar o evento
      int colunaFinal; // Onde deve terminar o envento
      int quadroAtual; // Qual é indexador
      boolean isEmpty; /* Indica se é um evento criado simplesmente para inicializar a tabela,
                          sem função musical.*/

      Color cor; //Cada evento está em uma trilha diferenciada visualmente pela cor
      transient Border border = new LinesBorder(Color.black,0);
      boolean isSelected = false;

      /* Usado no ato da  inicialização da tabela */
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

      //Teste de Renderização
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

     /* Indica se o envento começa e termina em um único tempo do compasso. */
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
