/**
  * Comparator of Invoices by Date (Inverse Chronological Order)
  *
  * @author Luís, Zé e Miguel
  * @version 1.1
*/
import java.util.Comparator;
import java.time.LocalDateTime;
import java.io.Serializable;

public class ComparatorInvoiceDate implements Comparator<Invoice>, Serializable {
  /**
    * Compares two invoices by date
    * @param i1 Invoice 1
    * @param i2 Invoice 2
    * @return <0 if i1 < i2, 0 if i1 == i2 and >0 if i1 > i2
  */
  public int compare(Invoice i1, Invoice i2) {
    LocalDateTime d1 = i1.getIssueDate();
    LocalDateTime d2 = i2.getIssueDate();

    return d1.compareTo(d2);
  }
}
