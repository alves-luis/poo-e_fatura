
/**
 * Health (type of EconomicalField)
 *
 * @author Miguel
 * @version 1.2
 */
import java.util.Set;
import java.util.Map;

public class Health extends EconomicalField {
    private double maxFamilyDeduction;

    /** Code to map the EconomicalField */
    private int code = 2;

    /**
      * Method that returns the code of this EconomicalField
    */
    public int getCode() {
      return this.code;
    }

    /**
     * Constructor for objects of class Health
     */
    public Health() {
      super(0.15);
      this.maxFamilyDeduction = 1000;
    }

    /**
     * Parameterized constructor for objects of class Health
     * @param ded deductionValue
     */
    public Health(double ded) {
      super(ded);
      this.maxFamilyDeduction = 1000;
    }

    /**
     * Copy constructor for objects of class Health
     * @param g Health
     */
    public Health(Health g) {
      super(g);
      this.maxFamilyDeduction = g.getMaxFamilyDeduction();
    }

    /**
      * Implements a maximum family deduction
      * @param d new Max Family deduction
    */
    public void setMaxFamilyDeduction(double d) {
        this.maxFamilyDeduction = d;
    }

    /**
      * Returns the maximum family deduction
      * @return max family deduction
    */
    public double getMaxFamilyDeduction() {
        return this.maxFamilyDeduction;
    }

    /**
      * Clone
      * @return EconomicalField (of instance Health)
    */
    @Override
    public Health clone() {
        return new Health(this);
    }

    /**
      * Returns the fiscal deduction of an individual, given its set of
      * invoices. It's just the sum of the expenses (multiplied by the coefficients)
      * @param i Set of invoices
      * @return fiscaldeduction
    */
    public double getFiscalDeductionFromIndividual(Set<Invoice> i) {
      double result = getFiscalDeductionFromEnterprise(i);
      return (result > this.getMaxFamilyDeduction() ? this.getMaxFamilyDeduction() : result);
    }

    /**
      * Returns the fiscal deduction of an enterprise, given its set of
      * invoices. Does not take into account the max individual deduction
      * @param i Set of invoices
      * @return fiscaldeduction
    */
    public double getFiscalDeductionFromEnterprise(Set<Invoice> i) {
      if (i == null) return 0;
      double result = 0;
      for(Invoice inv : i) {
        if (inv.getTypeOfExpense().equals(this) && (inv.getStatus() == Invoice.CONFIRMED) &&
        inv.getReceiver().getWhereCanDeduct().contains(this)) {
          double tempResult = inv.getExpenseValueAfterTax()*this.getDeductionValue()*inv.getReceiver().getFiscalCoefficient();
          if (inv.getIssuer() instanceof FiscalBenefits) {
            tempResult = tempResult * (((FiscalBenefits) inv.getIssuer()).getFiscalCoefficient());
          }
          result += tempResult;
        }
      }
      return result;
    }

    /**
      * Returns the fiscal deduction from a family of individuals
      * The family members are the keys in the map
      * The values are the set of invoices of each member
      * Takes into account the max family deduction
      * @param s Map
      * @return fiscal deduction
    */
    public double getFiscalDeductionFromFamily(Map<Individual,Set<Invoice>> s) {
        double result = 0;
        for (Individual i : s.keySet()) {
            result += getFiscalDeductionFromIndividual(s.get(i));
        }
        return (result > this.getMaxFamilyDeduction() ? this.getMaxFamilyDeduction() : result);
    }

    /**
      * Equals
      * @param o Object
      * @return boolean
    */
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Health o = (Health) obj;
        return this.getCategory().equals(o.getCategory());
    }

}
