
/**
 * Throw this exception when you try to set an Invalid Parameter in a User field
 *
 * @author Miguel, Luís e Zé
 * @version 1.0
 */


public class InvalidUserParameterException extends Exception {
  /**
    * Parameterized constructor
    * @param msg
  */
  public InvalidUserParameterException(String msg) {
    super(msg);
  }

  /**
    * Default constructor
  */
  public InvalidUserParameterException() {
    super();
  }

}
