/**
 * This class describes the behavior of the types of Expense of an invoice
 * It also includes some pre-populated types of expenses, and maps their
 * code
 *
 * @author Miguel, Luís e Zé
 * @version 1.7
 */

import java.io.*;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public abstract class EconomicalField implements Comparable<EconomicalField>, Serializable {
    /** Valor da dedução */
    private double deductionValue;

    /** Maps all the available EconomicalFields */
    private static Map<Integer,EconomicalField> economicalFieldByCode = new HashMap<>();
    static {
      General gen = new General();
      Health health = new Health();
      Education education = new Education();
      NursingHomes homes = new NursingHomes();
      economicalFieldByCode.put(gen.getCode(), gen);
      economicalFieldByCode.put(health.getCode(),health);
      economicalFieldByCode.put(education.getCode(),education);
      economicalFieldByCode.put(homes.getCode(),homes);
    }

    /**
      * Static method that returns the EconomicalField, given a code
      * @param int code
      * @return EconomicalField
    */
    public static EconomicalField getEconomicalFieldByCode(int code) throws InvalidEconomicalFieldCodeException {
      if (!economicalFieldByCode.containsKey(code)) throw new InvalidEconomicalFieldCodeException(String.valueOf(code));
      else return economicalFieldByCode.get(code).clone();
    }

    /**
      * Static method that returns the number of EconomicalFields the app has
      * @return int number of economicalfields
    */
    public static int getNumberOfEconomicalFields() {
      return economicalFieldByCode.size();
    }

    /**
     * Constructor default for objects of class EconomicalField
     */
    public EconomicalField() {
      this.deductionValue = 0;
    }

    /**
      * Constructor with deduction value
      * @param d deduction
    */
    public EconomicalField(double d) {
      this.deductionValue = d;
    }

    /**
      * Copy constructor
      * @param e EconomicalField
    */
    public EconomicalField(EconomicalField e) {
      this.deductionValue = e.getDeductionValue();
    }

    /**
      * Returns the name of the EconomicalField
      * @return String
    */
    public String getCategory() {
      return this.getClass().getSimpleName();
    }

    /**
      * Returns the deductionValue of this EconomicalField
      * @return Double
    */
    public Double getDeductionValue() {
      return this.deductionValue;
    }

    /**
      * Abstract method that should return the code of the economicalField
      * @return int
    */
    public abstract int getCode();

    /**
      * Standard clone
      * @return EconomicalField
    */
    public abstract EconomicalField clone();

    /**
      * Given a set of Invoices from the same individual,
      * returns the amount of fiscalDeduction that this EconomicalField
      * represents to the Individual
      * @param i Set of invoices from the same individual
      * @return Deduction
    */
    public abstract double getFiscalDeductionFromIndividual(Set<Invoice> i);

    /**
      * Given a set of Invoices from the same Enterprise,
      * returns the amount of fiscalDeduction that this EconomicalField
      * represents to all the individuals
      * @param i Set of invoices from the same enterprise
      * @return deduction (without limits, au contraire of getFiscalDeductionFromIndividual)
    */
    public abstract double getFiscalDeductionFromEnterprise(Set<Invoice> i);

    /**
      * Given a Map of Invoices (representing the family members as values and
      * their invoices as keys), returns the amount of fiscalDeduction that
      * this EconomicalField represents to the Family
      * @param s Map
      * @return Deduction
    */
    public abstract double getFiscalDeductionFromFamily(Map<Individual,Set<Invoice>> s);

    /**
      * Standard toString (but fancier)
      * @return formatted String
    */
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Category: ").append(this.getCategory()).append(";\n");
      sb.append("Deduction: ").append(this.deductionValue).append(".\n");
      return sb.toString();
    }

    /**
      * Equals method
      * @param o Object to compare
      * @return boolean
    */
    public abstract boolean equals(Object o);

    /**
      * Natural order of EconomicalFields (by deductionValue)
      * @param e EconomicalField to compare
      * @return int of comparison
    */
    public int compareTo(EconomicalField e) {
      return this.getCode()- e.getCode();
    }
}
