
/**
 * Class that represents Empresas Do Interior
 *
 * @author Luís, Miguel e Zé
 * @version 1.0
 */
public class InlandEnterprise extends Enterprise implements FiscalBenefits {
    private int district;
    private double fiscCoef;

    public InlandEnterprise() {
        super();
        this.fiscCoef = 1.5;
        this.district = 3;
    }
    public InlandEnterprise(Enterprise e) {
        super(e);
        this.fiscCoef = 1.5;
        this.district = 3;
    }

    public InlandEnterprise(Enterprise e, int district) {
      super(e);
      this.fiscCoef = 1.5;
      this.district = district;
    }

    public InlandEnterprise(InlandEnterprise e) {
      super(e);
      this.fiscCoef = e.getFiscalCoefficient();
      this.district = e.getDistrictCode();
    }

    public double getFiscalCoefficient() {
        return this.fiscCoef;
    }

    public void setDistrict(int disc) {
        this.district = disc;
    }

    public String getDistrict() {
        return Districts.values()[this.district].name();
    }

    public int getDistrictCode() {
      return this.district;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString()).append("\n");
      sb.append("District: ").append(this.getDistrict()).append("\n");
      return sb.toString();
    }

    @Override
    public InlandEnterprise clone() {
      return new InlandEnterprise(this);
    }

    @Override
    public boolean equals(Object o) {
      if (o == this)
        return true;
      if (o == null || o.getClass() != this.getClass())
        return false;

      InlandEnterprise  e = (InlandEnterprise) o;
      return this.district == e.getDistrictCode() && this.fiscCoef == e.getFiscalCoefficient()
             && super.equals(o);
    }

    public int hashCode() {
        return super.hashCode();
    }
}
