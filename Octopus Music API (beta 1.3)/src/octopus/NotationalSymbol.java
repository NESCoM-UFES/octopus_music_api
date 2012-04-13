package octopus;

/**
 * Armazena os símbolos das Cifras identificados internamente pelo
 * sistema e os respectivos símbolos atribuídos pelo usuário de acordo com sua
 * preferência. Exemplo: o símbolo identificado internamente pelo sistema para
 * representar uma nota diminuta é "°", mas o usuário pode representar uma nota
 * diminuta com "dim" que o sistema vai fazer a correspondência.
 * @see java.lang.Object
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
import java.io.*;
 class NotationalSymbol implements Serializable{
/**
 * Refere-se ao símbolo utilizado pelo usuário para representar uma cifra.
 */
  public String usedSymbol;
/**
 * Refere-se ao símbolo identificado internamente pelo sistema para representar uma cifra.
 */
  public String symbol;
/**
 * Descrição textual do Símbolo.
 */
  public String description;

  protected NotationalSymbol() {};

/**
   * Inicializa o símbolo utilizado e sua descrição.
   * @param simbUtilizado Símbolo utilizado pelo sistema.
   * @param description Descrição do símbolo.
   */
  public NotationalSymbol(String usedSymbol, String description) {
    this.usedSymbol = usedSymbol;
    this.description = description;
  }

/**
   * Inicializa o símbolo utilizado, sua descrição e o símbolo identificador.
   * @param simbUtilizado Símbolo utilizado pelo sistema.
   * @param simbIdentificador Identificação do símbolo.
   * @param description Descrição do símbolo.
   */
  public NotationalSymbol(String usedSymbol,String symbolID, String description) {
    this.usedSymbol = usedSymbol;
    symbol = symbolID;
    this.description = description;
  }

/**
 * Retorna o símbolo utilizado pelo sistema para representar uma cifra.
 * @return Símbolo utilizado pelo sistema para representar uma cifra.
 */
  public String getUsedSymbol() {return usedSymbol;}

/**
 * Retorna a descrição da cifra.
 * @return Descrição da cifra.
 */
  public String getDescription() {return description;}

/**
 * Retorna o símbolo identificador definido internamente pelo sistema
 * para representar uma cifra.
 * @return símbolo identificador definido internamente pelo sistema
 * para representar uma cifra.
 */
  public String getSymbol() {return symbol;}

/**
 * Retorna a posição final do símbolo utilizado em uma cifra começando na posição
 * inicial que é passada por parâmetro.
 * @param posInicial Posição inicial passada por parâmetro.
 * @return Posição final do símbolo utilizado em uma cifra.
 */
  public int getPosition(int posInicial) {
    return (posInicial + usedSymbol.length());
  }
}
