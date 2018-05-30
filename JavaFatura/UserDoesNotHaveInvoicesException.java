/**
 * Throw this exception when tries to get invoices from a user who has no invoices
 *
 * @author Miguel, Luís e Zé
 * @version 1.0
 */

public class UserDoesNotHaveInvoicesException extends Exception {
  public UserDoesNotHaveInvoicesException(String msg) {
    super("NIF: " + msg);
  }

  public UserDoesNotHaveInvoicesException() {
    super();
  }

}
