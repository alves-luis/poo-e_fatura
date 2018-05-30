/**
  * Comparator of Invoices by ExpenseValue
  *
  * @author Miguel, Luís e Zé
  * @version 1.0
*/
import java.util.Comparator;
import java.io.Serializable;

public class ComparatorInvoiceExpense implements Comparator<Invoice>, Serializable {
  /**
    * Compares two invoices by ExpenseValue
    * @param i1 Invoice 1
    * @param i2 Invoice 2
    * @return <0 if i1 < i2, 0 if i1 == i2 and >0 if i1 > i2
  */
  public int compare(Invoice i1, Invoice i2) {
    double e1 = i1.getExpenseValueAfterTax();
    double e2 = i2.getExpenseValueAfterTax();

    if (e1 < e2) return 1;
    if (e1 > e2) return -1;
    return -1;
  }
}
