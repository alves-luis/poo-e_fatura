/**
 * Throw this exception when tries to get history from user with no change history
 *
 * @author Miguel, Luís e Zé
 * @version 1.0
 */


public class UserHasNoInvoiceHistoryException extends Exception {
  public UserHasNoInvoiceHistoryException(String msg) {
    super("NIF: " + msg);
  }

  public UserHasNoInvoiceHistoryException() {
    super();
  }

}
