
/**
 * Class that represents Enterprise entities
 * in the Invoice System.
 *
 * @author Luís Alves
 * @version 1.1
 */

 import java.util.Set;
 import java.util.HashSet;
 import java.util.TreeSet;
 import java.util.stream.Collectors;
 import java.time.LocalDateTime;

public class Enterprise extends User {
    // instance variables
    private Set<EconomicalFields> economicalFields;
    private EnterpriseDatabase database;

    /**
     * Constructor (without parameters)
     */
    public Enterprise() {
        super();
        this.economicalFields = new HashSet<EconomicalFields>();
        this.database = new EnterpriseDatabase();
    }

    public Enterprise(int NIF, String email, String name, String address, String pass) {
      super(NIF,email,name,address,pass);
      this.economicalFields = new HashSet<EconomicalFields>();
      this.database = new EnterpriseDatabase();
    }

    public Enterprise(Enterprise e) {
      super(e);
      this.economicalFields = e.getEconomicalFields();
      this.database = new EnterpriseDatabase(e.getDatabase());
    }

    public Set<EconomicalFields> getEconomicalFields() {
      return this.economicalFields.stream().map(EconomicalFields :: clone).collect(Collectors.toCollection(HashSet::new));
    }

    public EnterpriseDatabase getDatabase() {
      return this.database.clone();
    }

    public void addEconomicalField(EconomicalFields a) {
      this.economicalFields.add(a.clone());
    }

    public void addInvoice(Invoice i) {
      this.database.addInvoice(i);
    }

    public Set<Invoice> getAllInvoicesByDate() {
      return this.database.getAllInvoicesByDate();
    }

    public Set<Invoice> getAllInvoicesByValue() {
      return this.database.getAllInvoicesByValue();
    }

    public Set<Invoice> getInvoicesByIndividualByDate(Individual i, LocalDateTime start, LocalDateTime end) {
      return this.database.getInvoicesByIndividualByDate(i,start,end);
    }

    public Set<Invoice> getInvoicesByIndividualByValue(Individual i) {
      return this.database.getInvoicesByIndividualByValue(i);
    }

    public double getTotalRevenueByDate(LocalDateTime start, LocalDateTime end) {
      return this.database.getTotalRevenueByDate(start,end);
    }

    public Enterprise clone() {
      return new Enterprise(this);
    }
}
