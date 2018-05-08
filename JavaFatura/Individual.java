
/**
 * Class that represents an Individual in the system
 *
 * @author Zé, Luís e Miguel
 * @version 1.1
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Individual extends User {
    // instance variables
    private Map<Integer,Individual> family;
    private double fiscalCoefficient;
    private Set<EconomicalFields> whereCanDeduct;
    private IndividualDatabase invoices;

    /**
    * Empty Constructor
    */
    public Individual() {
        super();
        this.family = new HashMap<Integer,Individual>();
        this.fiscalCoefficient = 1;
        this.whereCanDeduct = new HashSet<EconomicalFields>();
        this.invoices = new IndividualDatabase();
    }

    /**
     * Constructor for objects of class Individual
     */
    public Individual(int myNIF, String myEmail, String myName, String myAddress, String myPassword,
                      Set<Individual> myFamily, double myFiscalCoefficient,
                      Set<EconomicalFields> whereICanDeduct) {

      super(myNIF,myEmail,myName,myAddress,myPassword);
      this.family = myFamily.stream().collect(Collectors.toMap(Individual :: getNIF, Individual :: clone));
      this.fiscalCoefficient = myFiscalCoefficient;
      this.whereCanDeduct = whereICanDeduct.stream().map(EconomicalFields :: clone).collect(Collectors.toCollection(HashSet::new));
    }

    public Individual(int myNif, String myEmail, String myName, String myAddress, String myPassword) {
      super(myNif,myEmail,myName,myAddress,myPassword);
      this.family = new HashMap<>();
      this.fiscalCoefficient = 1;
      this.whereCanDeduct = new HashSet<EconomicalFields>();
    }

    /**
    * Copy Constructor
    */
    public Individual(Individual i) {
        super(i);
        this.family = i.getFamily().stream().collect(Collectors.toMap(Individual :: getNIF, Individual :: clone));
        this.fiscalCoefficient = i.getFiscalCoefficient();
        this.whereCanDeduct = i.getWhereCanDeduct();
    }

    // vai à base de dados de cada Individuo em family e soma o total de dedução
    // em cada economicalfield
    public double getTotalFiscalDeductionFromFamily() {
      return 0;
    }

    public void addFamilyMember(Individual newFamilymember) {

    }

    public void removeFamilyMember(int nifOfFamilyMember) {

    }

    /**
      * For each EconomicalField in whereCanDeduct, checks database
      * for invoices of that economicalfield and multiplies the sum of them
      * by the deduction from that EconomicalField.
    */
    public Map<EconomicalFields,Double> getFiscalDeductionPerEconomicalField() {
      return null;
    }

    public double getFiscalDeductionFromEconomicalField(EconomicalFields e) {
      return e.getDeductionValue();
    }

    public Set<EconomicalFields> getWhereCanDeduct() {
      return this.whereCanDeduct.stream().map(EconomicalFields :: clone).collect(Collectors.toSet());
    }

    /**
      * Getter for the Family of an individual
    */
    public Set<Individual> getFamily() {
      return this.family.values().stream().map(Individual :: clone).collect(Collectors.toSet());
    }

    /**
     * Getter for the number of dependents in the family
     *
     * @return    Family dependents
     */
    public int getNumberOfDependents() {
        return this.family.size();
    }

    /**
     * Getter for the fiscal coefficient
     *
     * @return    Fiscal Coefficient
     */
    public double getFiscalCoefficient() {
        return this.fiscalCoefficient;
    }


    public void setFiscalCoefficient(double newFiscalCoefficient) {
        this.fiscalCoefficient = newFiscalCoefficient;
    }

    public void addInvoice(Invoice i) {
      this.invoices.addInvoice(i);
    }
    
    // Returns the sum of all the expenseValueAfterTax of his invoices
    public double getTotalSpent() {
        return 0;
    }


    /**
     * Equals method
     *
     * @param  maybeIndividual  Object to be compared
     *
     * @return    Whether it is equal or not
     */
    public boolean equals (Object maybeIndividual) {
        if (this == maybeIndividual)
            return true;

        if (this == null || maybeIndividual.getClass() != this.getClass())
            return false;

        Individual individual = (Individual) maybeIndividual;
        return super.equals(maybeIndividual);
    }

    /**
     * Clone method
     *
     * @return    A clone
     */
    public Individual clone () {
        return new Individual(this);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(super.toString());

        str.append("Number of dependents: ");
        str.append(this.getNumberOfDependents());
        str.append(" \n");

        str.append("Family NIF's: ");
        str.append(this.family.keySet().toString());
        str.append(" \n");

        str.append("Fiscal Coefficient: ");
        str.append(this.fiscalCoefficient);
        str.append(" \n");

        return str.toString();
    }
}
