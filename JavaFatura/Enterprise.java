
/**
 * Class that represents Enterprise entities
 * in the System
 *
 * @author Luís, Miguel e Zé
 * @version 1.3
 */

 import java.util.Set;
 import java.util.HashSet;
 import java.util.TreeSet;
 import java.util.stream.Collectors;
 import java.time.LocalDateTime;
 import java.util.Iterator;

public class Enterprise extends User {
    /** EconomicalFields where an Enterprise can issue Invoices to */
    private Set<EconomicalField> economicalFields;

    /**
     * Constructor (without parameters)
     */
    public Enterprise() {
        super();
        this.economicalFields = new TreeSet<EconomicalField>();
    }

    /**
      * Parameterized constructor
      * @param NIF nif
      * @param email email
      * @param name name
      * @param address address
      * @param pass password
    */
    public Enterprise(int NIF, String email, String name, String address, String pass) {
      super(NIF,email,name,address,pass);
      this.economicalFields = new TreeSet<EconomicalField>();
    }

    /**
      * Copy constructor
      * @param e Enterprise
    */
    public Enterprise(Enterprise e) {
      super(e);
      this.economicalFields = e.getEconomicalFields();
    }

    /**
      * Returns all the EconomicalFields an Enterprise can issue invoices to
      * @return Set of EconomicalFIelds
    */
    public Set<EconomicalField> getEconomicalFields() {
      return this.economicalFields.stream()
      .map(EconomicalField :: clone)
      .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
      * Given a code, returns the EconomicalField refering to that code, if the
      * enterprise can issue Invoices with that EconomicalField
      * If it can't, throws Exception
      * @param code int
      * @return EconomicalField
    */
    public EconomicalField getEconomicalField(int code) throws InvalidEconomicalFieldCodeException {
      for (Iterator<EconomicalField> it = this.economicalFields.iterator(); it.hasNext();) {
        EconomicalField eco = (EconomicalField) it.next();
        if (eco.getCode() == code) return eco.clone();
      }
      throw new InvalidEconomicalFieldCodeException(String.valueOf(code));
    }

    /**
      * Returns the number of EconomicalFields an Enterprise can issue invoices
      * with
      * Useful for determining the status of an invoice
      * @return number of EconomicalFields
    */
    public int getNumberOfEconomicalFields() {
      return this.economicalFields.size();
    }

    /**
      * Adds a new EconomicalField to the Enterprise
      * @param a new EconomicalField
    */
    public void addEconomicalField(EconomicalField a) {
      this.economicalFields.add(a.clone());
    }

    /**
      * Clone method
      * @return Enterprise
    */
    public Enterprise clone() {
      return new Enterprise(this);
    }

    /**
      * Equals method. Calls User equals (equal if same NIF)
      * @param o Enterprise
      * @return boolean
    */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
      return super.hashCode();
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString());
      sb.append("EconomicalFields: ").append(this.economicalFields);
      return sb.toString();
    }
}
