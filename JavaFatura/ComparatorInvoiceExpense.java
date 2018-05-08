/**
  * Comparator of Invoices by ExpenseValue
  *
  * @author Luís Alves
  * @version 1.0
*/
import java.util.Comparator;
import java.time.LocalDateTime;

public class ComparatorInvoiceExpense implements Comparator<Invoice> {
  public int compare(Invoice i1, Invoice i2) {
    double e1 = i1.getExpenseValueAfterTax();
    double e2 = i2.getExpenseValueAfterTax();

    if (e1 == e2) return 0;
    if (e1 > e2) return 1;
    return -1;
  }
}
