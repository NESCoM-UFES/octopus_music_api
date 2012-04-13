package octopus;

/**
 * Armazena os s�mbolos das Cifras identificados internamente pelo
 * sistema e os respectivos s�mbolos atribu�dos pelo usu�rio de acordo com sua
 * prefer�ncia. Exemplo: o s�mbolo identificado internamente pelo sistema para
 * representar uma nota diminuta � "�", mas o usu�rio pode representar uma nota
 * diminuta com "dim" que o sistema vai fazer a correspond�ncia.
 * @see java.lang.Object
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
import java.io.*;
 class NotationalSymbol implements Serializable{
/**
 * Refere-se ao s�mbolo utilizado pelo usu�rio para representar uma cifra.
 */
  public String usedSymbol;
/**
 * Refere-se ao s�mbolo identificado internamente pelo sistema para representar uma cifra.
 */
  public String symbol;
/**
 * Descri��o textual do S�mbolo.
 */
  public String description;

  protected NotationalSymbol() {};

/**
   * Inicializa o s�mbolo utilizado e sua descri��o.
   * @param simbUtilizado S�mbolo utilizado pelo sistema.
   * @param description Descri��o do s�mbolo.
   */
  public NotationalSymbol(String usedSymbol, String description) {
    this.usedSymbol = usedSymbol;
    this.description = description;
  }

/**
   * Inicializa o s�mbolo utilizado, sua descri��o e o s�mbolo identificador.
   * @param simbUtilizado S�mbolo utilizado pelo sistema.
   * @param simbIdentificador Identifica��o do s�mbolo.
   * @param description Descri��o do s�mbolo.
   */
  public NotationalSymbol(String usedSymbol,String symbolID, String description) {
    this.usedSymbol = usedSymbol;
    symbol = symbolID;
    this.description = description;
  }

/**
 * Retorna o s�mbolo utilizado pelo sistema para representar uma cifra.
 * @return S�mbolo utilizado pelo sistema para representar uma cifra.
 */
  public String getUsedSymbol() {return usedSymbol;}

/**
 * Retorna a descri��o da cifra.
 * @return Descri��o da cifra.
 */
  public String getDescription() {return description;}

/**
 * Retorna o s�mbolo identificador definido internamente pelo sistema
 * para representar uma cifra.
 * @return s�mbolo identificador definido internamente pelo sistema
 * para representar uma cifra.
 */
  public String getSymbol() {return symbol;}

/**
 * Retorna a posi��o final do s�mbolo utilizado em uma cifra come�ando na posi��o
 * inicial que � passada por par�metro.
 * @param posInicial Posi��o inicial passada por par�metro.
 * @return Posi��o final do s�mbolo utilizado em uma cifra.
 */
  public int getPosition(int posInicial) {
    return (posInicial + usedSymbol.length());
  }
}
