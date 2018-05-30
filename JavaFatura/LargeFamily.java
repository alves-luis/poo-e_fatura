
/**
 * Quite simple implementation of Large Family, as it was already predicted
 * In the making of an Individual
 *
 * @author Zé
 * @version 1.0
 */

import java.lang.StringBuilder;
import java.io.Serializable;

public class LargeFamily extends Individual implements FiscalBenefits, Serializable {

    public LargeFamily() {
      super();
    }

    public LargeFamily(Individual i) {
      super(i);
    }

    public LargeFamily(LargeFamily f) {
      super(f);
      double coef = f.getFiscalCoefficient();
      double initialCoef = coef - this.getNumberOfDependents()*(0.05);
      this.setFiscalCoefficient(initialCoef);
    }

    public double getFiscalCoefficient() {
      double coef = super.getFiscalCoefficient()+this.getNumberOfDependents()*(0.05);
      return coef;
    }

    public LargeFamily clone() {
      return new LargeFamily(this);
    }

    /**
      * Equals method. Calls User equals (equal if same NIF)
      * @param o LargeFamily
      * @return boolean
    */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
      * toString
    */
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString());
      sb.append("Família Grande!!!\n");
      return sb.toString();
    }

    public int hashCode() {
        return super.hashCode();
    }
}
