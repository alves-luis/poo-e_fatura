import java.time.LocalDateTime;
/**
 * Class that represents an Invoice issued by an enterprise.
 *
 * @author Miguel, Luis and Ze
 * @version 1.1
 */

import java.io.Serializable;
import java.lang.Comparable;
import java.util.Objects;

public class Invoice implements Serializable, Comparable<Invoice> {
    /** Renaming booleans */
    public final static boolean CONFIRMED = true;
    public final static boolean NOT_CONFIRMED = false;

    /** Identity of the issuer */
    private Enterprise issuer;
    /** Identity of the receiver */
    private Individual receiver;
    /** Date of the Invoice */
    private LocalDateTime issueDate;
    /** Field in which the invoice was issued */
    private EconomicalField typeOfExpense;
    /** Value of the expense */
    private double expenseValue;
    /** Description of the invoice */
    private String description;
    /** Current status */
    private boolean status;

    /** Static variable: salesTax */
    private static double salesTax = 0.2;

    /**
      * Setter for the Sales Tax
      * @param tax new value
    */
    public static void setSalesTax(double tax) {
      salesTax = tax;
    }

    /**
      * Getter for the sales tax
      * @return double
    */
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
       this.status = NOT_CONFIRMED;
    }

    /**
      * Parametrized constructor for Invoices
    */
    public Invoice(Enterprise issued, Individual receiver, LocalDateTime issueDate, EconomicalField typeOfExpense, double valueOfExpense, String desc, boolean stat) {
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

    /**
      * Setter for the Invoice's receiver
      * @param i individual
    */
    public void setReceiver(Individual i) {
      this.receiver = i.clone();
    }

    /**
      * Getter for the invoice's receiver
      * @return Individual
    */
    public Individual getReceiver() {
      return this.receiver.clone();
    }

    /**
      * Setter for the Invoice's Issuer
      * @param e Enterprise
    */
    public void setIssuer(Enterprise e) {
      this.issuer = e.clone();
    }

    /**
      * Setter for the Invoice's EconomicalField
      * @param eco EconomicalField
    */
    public void setTypeOfExpense(EconomicalField eco) {
        this.typeOfExpense = eco.clone();
    }

    /**
      * Getter for the Invoice's Issuer
      * @return Enterprise
    */
    public Enterprise getIssuer() {
      return this.issuer.clone();
    }

    /**
      * getter for the type of Expense
      * @return EconomicalField
    */
    public EconomicalField getTypeOfExpense() {
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
      * @return double
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

    /**
      * Getter for the status of an Invoice
      * @return boolean
    */
    public boolean getStatus() {
        return this.status;
    }

    /**
      * Setter for the status of an Invoice
      * @param st boolean
    */
    public void setStatus(boolean st) {
        this.status = st;
    }

    /**
      * Clone method
      * @return Invoice
    */

    public Invoice clone() {
      return new Invoice(this);
    }

    /**
      * Natural order of Invoices (by date of Issue)
      * @param i Invoice
      * @return int of comparison
    */
    public int compareTo(Invoice i) {
      return this.issueDate.compareTo(i.getIssueDate());
    }

    /**
      * String all fancy
      * @return String
    */
    public String toString() {
      String status = this.status ? "CONFIRMED" : "NOT_CONFIRMED";
      StringBuilder sb = new StringBuilder();
      sb.append("-------------------------------------\n");
      sb.append("---------Fatura Simplificada---------\n");
      sb.append(" Emitida em ").append(this.issueDate.toString()).append("\n");
      sb.append(" Emitida por ").append(this.issuer.getNIF()).append(" - ").append(this.issuer.getName()).append("\n");
      sb.append(" Contribuinte (NIF): ").append(this.receiver.getNIF()).append("\n");
      sb.append(" Contribuinte (Nome): ").append(this.receiver.getName()).append("\n");
      sb.append(" Atividade Económica (Código): ").append(this.typeOfExpense.getCode()).append("\n");
      sb.append(" Custo (s/IVA): ").append(this.expenseValue).append("€\n");
      sb.append(" Custo (c/IVA): ").append(this.getExpenseValueAfterTax()).append("€\n");
      sb.append(" Descrição: ").append(this.description).append("\n");
      sb.append(" Estado: ").append(status).append("\n");
      sb.append("-------------------------------------\n");
      return sb.toString();
    }

    /**
      * Equals
      * @param i Object
      * @return boolean
    */
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

    public int hashCode() {
      return this.issuer.hashCode()*this.receiver.hashCode()*31+this.description.hashCode()*7;
    }

}
