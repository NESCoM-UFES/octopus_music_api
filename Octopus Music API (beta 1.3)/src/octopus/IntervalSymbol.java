package octopus;
/**
 * Armazena os símbolos dos intervalos identificados internamente pelo sistema
 * e os respectivos símbolos atribuidos pelo usuário de acordo com sua preferência.
 * Exemplo: o símbolo identificado internamente pelo sistema para representar o
 * intervalo Segunda Aumentada é "#2", mas o usuário pode representá-la com "+2".
 * @see Intervalo
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
import java.io.*;
 class IntervalSymbol extends NotationalSymbol implements Serializable{
  private Interval interval;

/**
   * Inicializa o símbolo utilizado e o intervalo.
   * @param simbUtilizado Símbolo utilizado para representar o intervalo.
   * @param intervalo Interval (distância entre duas notas).
   */
  public IntervalSymbol(String symbol, Interval interval) {
    usedSymbol = symbol;
    this.interval = interval;
  }

/**
   * Retorna o intervalo.
   * @return Interval (distância entre duas notas).
   */
  public Interval getInterval() {return interval;}

/* @parapensar Existe um método similar a este, chamado "containsIntervalo" da classe CifraValida.
 * Em "containsIntervalo" é passado um Interval, retornando true se ele existir na CifraValida.
 * Em existeIntervaloCifraValida é passado um vetor qualquer de Intervalos e uma string.
 * Se a string coincidir com a descrição de algum intervalo do vetor retorna true.
 */
/**
   * Retorna "true" caso o intervalo numérico passado por parâmetro pertença a relação
   * de intervalos da cifra, também passado por parâmetro.
   * @param intervalo (distância entre duas notas).
   * @param numero Interval numérico.
   * @return True caso o intervalo esteja contido na relação de intervalos, False caso contrário.
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
