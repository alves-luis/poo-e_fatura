
/**
 * Throw this exception when you try to get an EconomicalField with
 * an invalid code
 *
 * @author Miguel, Luís e Zé
 * @version 1.0
 */


public class InvalidEconomicalFieldCodeException extends Exception {
  /**
    * Parameterized constructor
    * @param msg
  */
  public InvalidEconomicalFieldCodeException(String msg) {
    super(msg);
  }

  /**
    * Default constructor
  */
  public InvalidEconomicalFieldCodeException() {
    super();
  }

}
