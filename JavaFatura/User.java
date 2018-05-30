
/**
 * Class that includes the shared information of both types of Users (individuals and enterprises) that pay taxes.
 *
 * @author Miguel, Luís and Zé
 * @version 1.1
 */

import java.io.Serializable;
import java.util.Objects;


public class User implements Serializable {

    private int NIF;
    private String email;
    private String name;
    private String address;
    private String password;

    /**
     * Constructor (default) for objects of the User class.
     *
     */
    public User() {
        this.NIF = 0;
        this.email = "";
        this.name = "";
        this.address = "";
        this.password = "";
    }

    /**
      * Constructor that accepts a NIF
      * @param nif User NIF
    */
    public User(int nif) {
      this.NIF = nif;
    }

    /**
     * Constructor (parameterized) for objects of class User
     *
     * @param  myNIF  User's NIF
     * @param  myEmail  User's E-mail
     * @param  myName  User's designation
     * @param  myAddress  User's address
     * @param  myPassword  User's Password
     */
    public User(int myNIF, String myEmail, String myName, String myAddress, String myPassword) {
        this.NIF = myNIF;
        this.email = myEmail;
        this.name = myName;
        this.address = myAddress;
        this.password = myPassword;
    }

    /**
     * Copy constructor
     * @param u User
     */

    public User(User u) {
        this.NIF = u.getNIF();
        this.email = u.getEmail();
        this.name = u.getName();
        this.address = u.getAddress();
        this.password = u.getPassword();
    }


    /**
     * Getter for the User's NIF
     *
     * @return    User's NIF
     */
    public int getNIF() {
        return this.NIF;
    }

    /**
     * Getter for the User's Email
     *
     * @return    User's Email
     */
    public String getEmail () {
        return this.email;
    }

    /**
     * Getter for the User's Name
     *
     * @return    User's Name
     */
    public String getName () {
        return this.name;
    }

    /**
     * Getter for the User's Address
     *
     * @return    User's Address
     */
    public String getAddress () {
        return this.address;
    }

    /**
     * Getter for the User's Password (seems not safe, it's private though)
     *
     * @return    User's Password
     */
    private String getPassword () {
        return this.password;
    }

    /**
     * Setter for the User's Email
     *
     * @param  newEmail  New Email to be set for the User
     */
    public void setEmail (String newEmail) {
        this.email = newEmail;
    }

    /**
     * Setter for the User's Name
     *
     * @param  newName  New Name to be set for the User
     */
    public void setName (String newName) {
        this.name = newName;
    }

    /**
     * Setter for the User's Address
     *
     * @param  newAddress  New Address to be set for the User
     */
    public void setAddress (String newAddress) {
        this.address = newAddress;
    }

    /**
     * Setter for the User's Password
     *
     * @param  newPassword  New Password to be set for the User
     */
    public void setPassword (String newPassword) {
        this.password = newPassword;
    }

    /**
      * Better way to login.
    */
    public boolean userMatch(int nif, String pass) {
      return (this.NIF == nif) && (this.password.equals(pass));
    }

    /**
     * Equals method
     *
     * @param  maybeUser  Object to be compared
     *
     * @return    Whether it is equal or not
     */
    public boolean equals (Object maybeUser) {
        if (this == maybeUser)
            return true;

        if ( this == null ||maybeUser.getClass() != this.getClass())
            return false;

        User user = (User) maybeUser;
        return (this.NIF == user.getNIF());
    }

    /**
     * Clone method
     *
     * @return    A clone
     */
    public User clone () {
        return new User(this);
    }

    /**
      * to String
      * @return String all pretty
    */
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("NIF: ");
        str.append(this.NIF);
        str.append(" \n");

        str.append("Email: ");
        str.append(this.email);
        str.append(" \n");

        str.append("Name: ");
        str.append(this.name);
        str.append(" \n");

        str.append("Address: ");
        str.append(this.address);
        str.append(" \n");


        return str.toString();
    }

    public int hashCode() {
      return 31*this.NIF + 19*this.name.hashCode()*3*this.address.hashCode() + this.email.hashCode()*7;
    }
}
