
/**
 * This class describes the behavior of the types of Expense of an invoice
 * It also includes some pre-populated types of expenses, and maps their
 * deductions
 *
 * @author Luís Alves
 * @version 1.1
 */

public interface EconomicalFields {
    
    public default String getCategory() {
        return this.getClass().getSimpleName();
    }
    public double getDeductionValue();
    public void setDeductionValue(double value);
    public EconomicalFields clone();

}
