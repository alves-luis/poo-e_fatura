
/**
 * Class that stores all the users in the system.
 *
 * @author Luís Alves
 * @version 1.1
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.io.Serializable;

public class UserDatabase implements Serializable {
    /** All users in the system */
    private Map<Integer,User> users;
    /** Admin Account */
    private Admin admin;
    /** Random userCount */
    private long userCount;

    /**
     * Constructor for objects of class UserDatabase
     */
    public UserDatabase() {
      this.users = new HashMap<>();
      this.admin = new Admin(666,"admin@admin.com","Admin","Rua do Admin","pass");
      this.userCount = 0;
    }

    /**
      * Getter for the user count
      * @return long
    */
    public long getUserCount() {
      return this.userCount;
    }

    /**
      * Adds a user to the database
      * @param u User
    */
    public void addUser(User u) {
      this.users.put(u.getNIF(),u.clone());
      this.userCount++;
    }

    /**
      * When adding family members, if final family count > 3, then instance
      * the individual whose family has increased to LargeFamily
      * @param family new family members
      * @param nif nif of receiver of family
    */
    public void addFamilyMembers(Set<Individual> family, Integer nif) {
      Individual receiver = (Individual)this.users.get(nif);
      for(Individual i : family) {
        if (!i.equals(receiver))
        receiver.addFamilyMember(i);
      }
      if (receiver.getNumberOfDependents() > 3 && !(receiver instanceof LargeFamily)) {
        LargeFamily newReceiver = new LargeFamily(receiver);
        this.users.remove(nif);
        this.users.put(nif,newReceiver);
      }
    }

    /**
      * Verifies wheter the email already exists in the database
      * @param email
      * @return boolean
    */
    public boolean isEmailUnique(String email) {
        return !(this.users.values()
        .stream()
        .map(i -> i.getEmail())
        .anyMatch(e -> e.equals(email)));
    }

    /**
      * Verifies wheter the NIF already exists in the database
      * @param nif
      * @return boolean
    */
    public boolean isNIFUnique(int NIF) {
        return !(this.users.containsKey(NIF) || this.admin.getNIF() == NIF);
    }

    /**
      * Getter for the user by NIF
      * Throws exception if the user does not exist
      * @param NIF
      * @return User
    */
    public User getUserByNIF(int NIF) throws UserDoesNotExistInDatabaseException {
        if (this.users.containsKey(NIF))
            return users.get(NIF).clone();
        if (this.admin.getNIF() == NIF)
          return this.admin.clone();
        else throw new UserDoesNotExistInDatabaseException(String.valueOf(NIF));
    }

    /**
      * Returns all the NIFs of Enterprises
      * @return Set of nifs
    */
    public List<Integer> getAllEnterpriseNIF() {
      return this.users.values().stream()
      .filter(u -> u instanceof Enterprise)
      .map(u -> u.getNIF())
      .collect(Collectors.toList());
    }



}
