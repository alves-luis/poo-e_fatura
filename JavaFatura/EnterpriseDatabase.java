
/**
 * Class that stores all the invoices an Enterprise has issued
 *
 * @author Luís Alves
 * @version 1.1
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.time.LocalDateTime;
import java.util.TreeSet;

/*
  Stores all the info related to the invoices issued by an Enterprise
*/
public class EnterpriseDatabase {
    // instance variables
    private Set<Invoice> invoicesByDate;
    private Set<Invoice> invoicesByValue;
    private Map<Individual,Set<Invoice>> invoicesByCustomerByDate;
    private Map<Individual,Set<Invoice>> invoicesByCustomerByValue;

    /**
     * Constructor for objects of class UserDatabase
     */
    public EnterpriseDatabase() {
        this.invoicesByDate = new TreeSet<Invoice>(new ComparatorInvoiceDate());
        this.invoicesByValue = new TreeSet<Invoice>(new ComparatorInvoiceExpense());
        this.invoicesByCustomerByDate = new HashMap<>();
        this.invoicesByCustomerByValue = new HashMap<>();
    }

    public EnterpriseDatabase(EnterpriseDatabase e) {

    }

    public EnterpriseDatabase clone() {
      return new EnterpriseDatabase(this);
    }

    /**
      * Dada uma Invoice, adicionar à base de dados
    */
    public void addInvoice(Invoice i) {
    }

    /**
      * Dada uma Invoice, remove de todas as bases de dados
    */
    public void removeInvoice(Invoice i) {

    }

    /**
      * Devolve o set de faturas entre determinadas datas
    */
    public Set<Invoice> getInvoicesByDate(LocalDateTime start, LocalDateTime end) {
      return null;
    }

    /**
      * Devolve o set ordenado por data
    */
    public Set<Invoice> getAllInvoicesByDate() {
      return null;
    }

    /**
      * Devolve o set ordenado por valor;
    */
    public Set<Invoice> getAllInvoicesByValue() {
      return null;
    }

    /**
      * Devolve um set de todas as invoices de um indivíduo entre determinadas datas
    */
    public Set<Invoice> getInvoicesByIndividualByDate(Individual i, LocalDateTime start, LocalDateTime end) {
      return null;
    }

    /**
      * Devolve um Set ordenado por valor decrescente de despesa
    */
    public Set<Invoice> getInvoicesByIndividualByValue(Individual i) {
      return null;
    }

    /**
      * devolve o total faturado pela empresa no período (somar o value b4 tax)
      * de todas as faturas nesse período
    */
    public double getTotalRevenueByDate(LocalDateTime start, LocalDateTime end) {
      return 0;
    }

    /**
      * Retorna o total faturado pela empresa;
    */
    public double getTotalValueIssued() {
      return 0;
    }
}
