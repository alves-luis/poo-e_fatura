/**
 * Throw this exception when tries to get User who does not exist
 *
 * @author Miguel, Luís e Zé
 * @version 1.0
 */

public class UserDoesNotExistInDatabaseException extends Exception {
  public UserDoesNotExistInDatabaseException(String msg) {
    super("NIF: " + msg);
  }

  public UserDoesNotExistInDatabaseException() {
    super();
  }

}
