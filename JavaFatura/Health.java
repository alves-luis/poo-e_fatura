
/**
 * Health
 *
 * @author Luís Alves
 * @version 1.0
 */
public class Health implements EconomicalFields {
    // instance variables
    private double deduction;

    /**
     * Constructor for objects of class Health
     */
    public Health() {
        this.deduction = 0.25;
    }
    
    /**
     * Parameterized constructor for objects of class Health
     */
    public Health(double ded) {
        this.deduction = ded;
    }
    
    /**
     * Copy constructor for objects of class Health
     */
    public Health(Health g) {
        this.deduction = g.getDeductionValue();
    }
    
    public double getDeductionValue() {
        return this.deduction;
    }

    public void setDeductionValue(double value) {
        this.deduction = value;
    }
    
    public EconomicalFields clone() {
        return new Health(this);
    }
}
