import java.time.LocalDateTime;
/**
 * Class that represents an Invoice issued by an enterprise.
 *
 * @author Miguel, Luis and Ze
 * @version 1.1
 */
public class Invoice {
    private static boolean CONFIRMED = true;
    private static boolean NOT_CONFIRMED = false;

    // instance variables
    private Enterprise issuer; // identity of the issuer
    private Individual receiver; // identity of the receiver
    private LocalDateTime issueDate; // date of the Invoice Issue
    private EconomicalFields typeOfExpense; // field in which the invoice was issued
    private double expenseValue; // value of the expense
    private String description; // other things about the expense
    private boolean status; // wheter it's confirmed or not;

    private static double salesTax = 0.2; // tax to add to the expenseValue;

    public static void setSalesTax(double tax) {
      salesTax = tax;
    }

    public static double getSalesTax() {
      return salesTax;
    }

    /**
     * Constructor (default) for objects of class Invoice
     */
    public Invoice() {
       this.issuer = new Enterprise();
       this.receiver = new Individual();
       this.issueDate = LocalDateTime.now();
       this.typeOfExpense = new General();
       this.expenseValue = 0;
       this.description = "";
       this.status = false;
    }

    /**
      * Parametrized constructor for Invoices
    */
    public Invoice(Enterprise issued, Individual receiver, LocalDateTime issueDate, EconomicalFields typeOfExpense, double valueOfExpense, String desc, boolean stat) {
      this.issuer = issued.clone();
      this.receiver = receiver.clone();
      this.issueDate = issueDate;
      this.typeOfExpense = typeOfExpense.clone();
      this.expenseValue = valueOfExpense;
      this.description = desc;
      this.status = stat;
    }

    /**
      * Copy constructor for Invoices
    */
    public Invoice(Invoice i) {
      this.issuer = i.getIssuer();
      this.receiver = i.getReceiver();
      this.issueDate = i.getIssueDate();
      this.typeOfExpense = i.getTypeOfExpense();
      this.expenseValue = i.getExpenseValueBeforeTax();
      this.description = i.getDescription();
      this.status = i.getStatus();
    }

    public void setReceiver(Individual i) {
      this.receiver = i.clone();
    }

    public Individual getReceiver() {
      return this.receiver.clone();
    }

    /**
      * Setter for the Invoice's Issuer
    */
    public void setIssuer(Enterprise e) {
      this.issuer = e.clone();
    }

    /**
      * Getter for the Invoice's Issuer
    */
    public Enterprise getIssuer() {
      return this.issuer.clone();
    }

    public Integer getIssuerNIF() {
      return this.issuer.getNIF();
    }

    /**
      * getter for the type of Expense
    */
    public EconomicalFields getTypeOfExpense() {
      return this.typeOfExpense.clone();
    }

    /**
     *  Getter for the Invoice's Issue Date
     *
     * @return    Date of Issue
     */
    public LocalDateTime getIssueDate() {
        return this.issueDate;
    }
    /**
     *  Getter for the Invoice's Value of Expense
     *
     * @return    value (in euros)
     */
    public double getExpenseValueBeforeTax() {
        return this.expenseValue;
    }

    /**
      * Getter for the Invoice's Total Expense Value (after tax)
    */
    public double getExpenseValueAfterTax() {
      return this.expenseValue * (1 + Invoice.getSalesTax());
    }

    /**
     * Getter for the Invoice's Description
     *
     * @return    Invoice's description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for the Invoice's Value of Expense
     *
     * @param value of the expense
     */
    public void setExpenseValue(int value) {
        this.expenseValue = value;
    }

    public boolean getStatus() {
        return this.status;
    }

    /**
      * Clone method
    */

    public Invoice clone() {
      return new Invoice(this);
    }

    public boolean equals(Object i) {

        if (i == this)
            return true;

        if (this == null || this.getClass() != i.getClass())
            return false;

        Invoice inv = (Invoice) i;
        return this.issuer.equals(inv.getIssuer()) &&
               this.receiver.equals(inv.getReceiver()) &&
               this.issueDate.equals(inv.getIssueDate()) &&
               this.typeOfExpense.equals(inv.getTypeOfExpense()) &&
               this.expenseValue == inv.getExpenseValueBeforeTax() &&
               this.description.equals(inv.getDescription()) &&
               this.status == inv.getStatus();
    }

}
