
/**
 * Class that represents an Individual in the system
 *
 * @author Zé, Luís e Miguel
 * @version 1.7
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.Objects;

public class Individual extends User {
    /** Maps the NIFs to the actual family member */
    private Map<Integer,Individual> family;
    /** Special fiscalCoefficient */
    private double fiscalCoefficient;
    /** Set of EconomicalFields an Individual can deduct to */
    private Set<EconomicalField> whereCanDeduct;

    /**
    * Empty Constructor
    */
    public Individual() {
        super();
        this.family = new HashMap<Integer,Individual>();
        this.fiscalCoefficient = 1;
        this.whereCanDeduct = new TreeSet<EconomicalField>();
    }

    /**
     * Constructor for objects of class Individual
     */
    public Individual(int myNIF, String myEmail, String myName, String myAddress, String myPassword,
                      Set<Individual> myFamily, double myFiscalCoefficient,
                      Set<EconomicalField> whereICanDeduct) {
      super(myNIF,myEmail,myName,myAddress,myPassword);
      this.family = myFamily.stream().collect(Collectors.toMap(Individual :: getNIF, Individual :: clone));
      this.fiscalCoefficient = myFiscalCoefficient;
      this.whereCanDeduct = whereICanDeduct.stream().map(EconomicalField :: clone).collect(Collectors.toCollection(TreeSet::new));
    }

    public Individual(int myNif, String myEmail, String myName, String myAddress, String myPassword) {
      super(myNif,myEmail,myName,myAddress,myPassword);
      this.family = new HashMap<>();
      this.fiscalCoefficient = 1;
      this.whereCanDeduct = new TreeSet<EconomicalField>();
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

    /**
      * Adds a new family member to the Individual
      * @param newFamilymember Individual that will now be part of the family
    */
    public void addFamilyMember(Individual newFamilymember) {
       this.family.put(newFamilymember.getNIF(),newFamilymember.clone());
    }

    /**
      * Returns a set of EconomicalFields where the Individual can have
      * fiscal deductions
      * @return Set<EconomicalField>
    */
    public Set<EconomicalField> getWhereCanDeduct() {
      return this.whereCanDeduct.stream().map(EconomicalField :: clone).collect(Collectors.toCollection(TreeSet::new));
    }

    /**
      * Set where can deduct
      * @param eco EconomicalField to add to be able to deduct
    */
    public void addWhereCanDeduct(EconomicalField eco) {
      this.whereCanDeduct.add(eco.clone());
    }

    /**
      * Returns a set of Individuals that are part of the family
      * @return Set<Individual>
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


    /**
      * Setter for the fiscal Coefficient
      * @param newFiscalCoefficient
    */
    public void setFiscalCoefficient(double newFiscalCoefficient) {
        this.fiscalCoefficient = newFiscalCoefficient;
    }

    /**
     * Equals method
     *
     * @param  obj Object to be compared
     *
     * @return    Whether it is equal or not
     */
     public boolean equals(Object obj) {
         return super.equals(obj);
     }

    /**
     * Clone method
     *
     * @return    A clone
     */
    public Individual clone () {
        return new Individual(this);
    }

    /**
      * ToString
      * @return String all pretty
    */
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
        str.append(this.getFiscalCoefficient());
        str.append(" \n");

        str.append("Where can deduct: ");
        str.append(this.whereCanDeduct.toString());
        str.append("\n");

        return str.toString();
    }

    public int hashCode() {
      return super.hashCode();
    }
}
