package octopus;
/**
 * Armazena os s�mbolos dos intervalos identificados internamente pelo sistema
 * e os respectivos s�mbolos atribuidos pelo usu�rio de acordo com sua prefer�ncia.
 * Exemplo: o s�mbolo identificado internamente pelo sistema para representar o
 * intervalo Segunda Aumentada � "#2", mas o usu�rio pode represent�-la com "+2".
 * @see Intervalo
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
import java.io.*;
 class IntervalSymbol extends NotationalSymbol implements Serializable{
  private Interval interval;

/**
   * Inicializa o s�mbolo utilizado e o intervalo.
   * @param simbUtilizado S�mbolo utilizado para representar o intervalo.
   * @param intervalo Interval (dist�ncia entre duas notas).
   */
  public IntervalSymbol(String symbol, Interval interval) {
    usedSymbol = symbol;
    this.interval = interval;
  }

/**
   * Retorna o intervalo.
   * @return Interval (dist�ncia entre duas notas).
   */
  public Interval getInterval() {return interval;}

/* @parapensar Existe um m�todo similar a este, chamado "containsIntervalo" da classe CifraValida.
 * Em "containsIntervalo" � passado um Interval, retornando true se ele existir na CifraValida.
 * Em existeIntervaloCifraValida � passado um vetor qualquer de Intervalos e uma string.
 * Se a string coincidir com a descri��o de algum intervalo do vetor retorna true.
 */
/**
   * Retorna "true" caso o intervalo num�rico passado por par�metro perten�a a rela��o
   * de intervalos da cifra, tamb�m passado por par�metro.
   * @param intervalo (dist�ncia entre duas notas).
   * @param numero Interval num�rico.
   * @return True caso o intervalo esteja contido na rela��o de intervalos, False caso contr�rio.
   */

  public boolean containsInterval(Interval intervals[], String number) {
    boolean retorno = false;
    int tamVet = intervals.length;
    for (int i =0; i < tamVet && !retorno; i++) {
     retorno = number.equals(intervals[i].getSymbol()) ? true : false;
    }
    return retorno;
  }

}
