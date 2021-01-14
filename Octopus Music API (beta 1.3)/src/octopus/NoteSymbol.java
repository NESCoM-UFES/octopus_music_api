package octopus;
/**
 * Armazena os símbolos das Notas identificados internamente pelo sistema e os
 * respectivos símbolos atribuídos pelo usuário de acordo com sua preferência.
 * Exemplo: o símbolo identificado internamente pelo sistema para representar
 * a nota Lá é "A", mas o usuário pode representá-la com "LA".
 * @see Nota
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
import java.io.Serializable;
  class NoteSymbol extends NotationalSymbol implements Serializable{
  private Note note;

/**
 * Inicializa o símbolo utilizado e a nota.
 * @param simbUtilizado Símbolo utilizado pelo usuário para representat a nota.
 * @param nota Menor unidade funcional na música utilizada para formação do
 * Acorde.
 */
  public NoteSymbol(String symbol, Note note) {
    usedSymbol = symbol;
    this.note = note;
  }

/**
   * Retorna a nota.
   * @return Note (Menor unidade funcional na música utilizada para formação do
   * Acorde).
   */
  public Note getNote() {return note;}
}
