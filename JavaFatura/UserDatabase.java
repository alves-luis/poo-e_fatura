
/**
 * Class that stores all the users in the system.
 *
 * @author Luís Alves
 * @version 1.1
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/*
  TO-DO: Registar um novo User (Contribuinte ou Empresa);
*/
public class UserDatabase {
    // instance variables
    private Map<Integer,Individual> individuals; // all the individuals in the system
    private Map<Integer,Enterprise> enterprises; // all the enterprises in the system
    private long userCount;

    /**
     * Constructor for objects of class UserDatabase
     */
    public UserDatabase() {
      this.individuals = new HashMap<>();
      this.userCount = 0;
    }

    public long getUserCount() {
      return this.userCount;
    }

    public void addIndividual(Individual i) {
      this.individuals.put(i.getNIF(),i.clone());
      this.userCount++;
    }

    public void addEnterprise(Enterprise e) {
      this.enterprises.put(e.getNIF(),e.clone());
      this.userCount++;
    }

    public void addInvoice(Invoice i) {
      int receiverNIF = i.getReceiver().getNIF();
      Individual receiver = this.individuals.get(receiverNIF);
      receiver.addInvoice(i);
      this.individuals.put(receiverNIF,receiver);

      int issuerNIF = i.getIssuer().getNIF();
      Enterprise issuer = this.enterprises.get(issuerNIF);
      issuer.addInvoice(i);
      this.enterprises.put(issuerNIF,issuer);
    }

    public void ChangeInvoiceEconomicalField(Invoice i, EconomicalFields newEco) {

    }

    public Set<Individual> getTop10Spenders() {
      return null;
    }



}
