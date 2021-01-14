package octopus;
/**
 * Armazena os s�mbolos das Notas identificados internamente pelo sistema e os
 * respectivos s�mbolos atribu�dos pelo usu�rio de acordo com sua prefer�ncia.
 * Exemplo: o s�mbolo identificado internamente pelo sistema para representar
 * a nota L� � "A", mas o usu�rio pode represent�-la com "LA".
 * @see Nota
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
import java.io.Serializable;
  class NoteSymbol extends NotationalSymbol implements Serializable{
  private Note note;

/**
 * Inicializa o s�mbolo utilizado e a nota.
 * @param simbUtilizado S�mbolo utilizado pelo usu�rio para representat a nota.
 * @param nota Menor unidade funcional na m�sica utilizada para forma��o do
 * Acorde.
 */
  public NoteSymbol(String symbol, Note note) {
    usedSymbol = symbol;
    this.note = note;
  }

/**
   * Retorna a nota.
   * @return Note (Menor unidade funcional na m�sica utilizada para forma��o do
   * Acorde).
   */
  public Note getNote() {return note;}
}
