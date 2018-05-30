
/**
 * Admin instance of User.
 *
 * @author Luís, Miguel e Zé
 * @version 1.0
 */
public class Admin extends User{

    public Admin() {
      super();
    }

    public Admin(int myNIF, String myEmail, String myName, String myAddress, String myPassword) {
      super(myNIF,myEmail,myName,myAddress,myPassword);
    }

    /**
      * Equals method. Calls User equals (equal if same NIF)
      * @param o Admin
      * @return boolean
    */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
