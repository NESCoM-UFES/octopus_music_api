package octopus;

/*
 * Estende a classe InvalidChordException e trata as exceções lançadas para uma
 * nota inválida quando forem identificadas irregularidades quanto a notação utilizada.
 * Exemplo: o símbolo que representa a nota Lá é "A" e o usuário decide representar com "ZZ".
 * Obs: O usuário pode redefinir toda a notação se quiser, mas não pode redefinir
 * símbolos individualmente. Só é possível trocar toda a notação de uma vez.
 * @see InvalidChordException
 * @see Exception
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */

/**
 * Thrown when there is a problem instantiation a Note object.
 */
public class NoteException extends Exception {
  private String note;

/*
 * Constrói uma Exception para Nota Inválida que recebe três argumentos através
 * dos parâmetros, a mensagem a ser exibida ao usuário, a cifra, e a nota.
 * @param mensagem Mensagem a ser exibida ao usuário quando uma exceção ocorrer.
 * @param cifra Cifra que possui uma nota inválida.
 * @param nota Nota inválida.
 */
  public NoteException(String message,  String note) {
    super(message);
    this.note = note;
  }

/*
 * Retorna a nota inválida.
 * @return Nota inválida.
 */
  public String getInvalidNote() {
    return note;
  }

}
