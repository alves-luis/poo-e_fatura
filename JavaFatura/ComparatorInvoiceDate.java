/**
  * Comparator of Invoices by Date (Inverse Chronological Order)
  *
  * @author Luís Alves
  * @version 1.1
*/
import java.util.Comparator;
import java.time.LocalDateTime;

public class ComparatorInvoiceDate implements Comparator<Invoice> {
  public int compare(Invoice i1, Invoice i2) {
    LocalDateTime d1 = i1.getIssueDate();
    LocalDateTime d2 = i2.getIssueDate();

    return d2.compareTo(d1);
  }
}
