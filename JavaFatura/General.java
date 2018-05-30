
/**
 * General (type of EconomicalField)
 *
 * @author Zé
 * @version 1.2
 */

import java.util.Set;
import java.util.Map;
public class General extends EconomicalField {
    /** Limits the maximum amount of individual deduction */
    private double maxIndividualDeduction;

    /** Code to map the EconomicalField */
    private int code = 1;

    /**
      * Method that returns the code of this EconomicalField
    */
    public int getCode() {
      return this.code;
    }

    /**
     * Constructor for objects of class General
     */
    public General() {
        super(0.35);
        this.maxIndividualDeduction = 250;
    }

    /**
     * Parameterized constructor for objects of class General
     * @param ded deduction to set
     */
    public General(double ded) {
        super(ded);
        this.maxIndividualDeduction = 250;
    }

    /**
     * Copy constructor for objects of class General
     * @param g Other instance of General
     */
    public General(General g) {
        super(g);
        this.maxIndividualDeduction = g.getMaxIndividualDeduction();
    }

    /**
      * Sets the maximum individual deduction
      * @param d new value of max individual deduction
    */
    public void setMaxIndividualDeduction(double d) {
        this.maxIndividualDeduction = d;
    }

    /**
      * Gets the maximum individual deduction
      * @return max Individual deduction
    */
    public double getMaxIndividualDeduction() {
        return this.maxIndividualDeduction;
    }

    /**
      * Clone
      * @return EconomicalField (of instance General)
    */
    @Override
    public General clone() {
        return new General(this);
    }

    /**
      * Returns the fiscal deduction of an individual, given its set of
      * invoices. Takes into account the max individual deduction
      * @param i Set of invoices
      * @return fiscaldeduction
    */
    public double getFiscalDeductionFromIndividual(Set<Invoice> i) {
      double result = getFiscalDeductionFromEnterprise(i);
      return (result > this.maxIndividualDeduction ? this.maxIndividualDeduction : result);
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
      for (Invoice inv : i) {
        if (inv.getTypeOfExpense().equals(this) && (inv.getStatus() == Invoice.CONFIRMED) &&
        inv.getReceiver().getWhereCanDeduct().contains(this)) {
          double tempResult = inv.getExpenseValueAfterTax()*this.getDeductionValue()*inv.getReceiver().getFiscalCoefficient();
          if (inv.getIssuer() instanceof FiscalBenefits) {
            tempResult = tempResult *(((FiscalBenefits) inv.getIssuer()).getFiscalCoefficient());
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
      * It's just the sum of the individual deductions
      * @param s Map
      * @return fiscal deduction
    */
    public double getFiscalDeductionFromFamily(Map<Individual,Set<Invoice>> s) {
        double result = 0;
        for(Individual i : s.keySet()) {
            result += this.getFiscalDeductionFromIndividual(s.get(i));
        }
        return result;
    }

    /**
      * Equals (calls super)
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
        General o = (General) obj;
        return this.getCategory().equals(o.getCategory());
    }
}
