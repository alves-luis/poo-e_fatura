
/**
 * General
 *
 * @author Luís Alves
 * @version 1.0
 */
public class General implements EconomicalFields {
    // instance variables
    private double deduction;

    /**
     * Constructor for objects of class General
     */
    public General() {
        this.deduction = 0.35;
    }
    
    /**
     * Parameterized constructor for objects of class General
     */
    public General(double ded) {
        this.deduction = ded;
    }
    
    /**
     * Copy constructor for objects of class General
     */
    public General(General g) {
        this.deduction = g.getDeductionValue();
    }
    
    public double getDeductionValue() {
        return this.deduction;
    }

    public void setDeductionValue(double value) {
        this.deduction = value;
    }
    
    public EconomicalFields clone() {
        return new General(this);
    }
}
