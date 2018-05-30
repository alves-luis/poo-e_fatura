/**
 * Throw this exception when a wrong instance of user is trying to do something
 * its not supposed to be doing.
 *
 * @author Miguel, Luís e Zé
 * @version 1.0
 */

public class WrongInstanceOfUserException extends Exception {
  public WrongInstanceOfUserException(String msg) {
    super("Expected: " + msg);
  }

  public WrongInstanceOfUserException() {
    super();
  }

}
