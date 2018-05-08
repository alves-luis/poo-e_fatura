
/**
 * Class that stores all the invoices an Individual has received
 *
 * @author Luís Alves
 * @version 1.1
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/*
  Stores all the info related to the invoices a customer has received
*/
public class IndividualDatabase {
    // instance variables
    private Set<Invoice> invoicesByDate;
    private Map<Boolean,Set<Invoice>> invoicesByStatus;
    private Map<EconomicalFields,Set<Invoice>> invoicesByEconomicalFields;
    private Map<Invoice,List<Invoice>> invoiceHistory; // associa a cada invoice,
    // um history de alterações (Lista por data)

    /**
     * Constructor for objects of class UserDatabase
     */
    public IndividualDatabase() {
        this.invoicesByDate = new HashSet<>();
        this.invoicesByStatus = new HashMap<>();
        this.invoicesByEconomicalFields = new HashMap<>();
        this.invoiceHistory = new HashMap<>();
    }

    public IndividualDatabase(IndividualDatabase e) {
        this.invoicesByDate = e.getInvoicesByDate();
        this.invoicesByStatus = e.getInvoicesByStatus();
        this.invoicesByEconomicalFields = e.getInvoicesFromEconomicalField();
        this.invoiceHistory = e.getAllInvoicesHistory();
    }

    public IndividualDatabase clone() {
      return new IndividualDatabase(this);
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

    // Adicionar ao history a antiga!!
    public void changeEconomicalField(Invoice i) {

    }

    public Set<Invoice> getInvoicesByDate() {
        return null;
    }
    
    public Set<Invoice> getInvoicesByDate(LocalDateTime start, LocalDateTime end) {
        HashSet<Invoice> res = new HashSet<>();
        for(Invoice i: this.invoicesByDate) {
            if(i.getIssueDate().isAfter(start) && i.getIssueDate().isBefore(end)) {
                res.add(i.clone());
            }
        }
        return res;
    }
    
    public Map<Boolean,Set<Invoice>> getInvoicesByStatus() {
        return null;
    }
    
    public Set<Invoice> getInvoicesWhoNeedConfirmation() {
      HashSet<Invoice> res = new HashSet<>();
      for(Invoice i: this.invoicesByStatus.get(false)){
            res.add(i.clone());
      }
      return res;
    }

    public Set<Invoice> getInvoicesFromEconomicalField(EconomicalFields e) {
      HashSet<Invoice> res = new HashSet<>();
      for(Invoice i: this.invoicesByEconomicalFields.get(e)){
            res.add(i.clone());
      }
      return res;
    }
    
    public Map<EconomicalFields,Set<Invoice>> getInvoicesFromEconomicalField() {
        return null;
    }

    /**
      * Soma o ExpenseValueAfterTax de todas as faturas de um determinado
      * economicalfield
    */
    public double getTotalExpenseFromEconomicalField(EconomicalFields e) {
      Set<Invoice> hset = getInvoicesFromEconomicalField(e);
      double val = 0;
      for(Invoice i: hset) {
          val += i.getExpenseValueAfterTax();
      }
      return val;
    }
    
    public Set<Invoice> getInvoicesWhoAreConfirmed() {
      HashSet<Invoice> res = new HashSet<>();
      for(Invoice i: this.invoicesByStatus.get(true)){
            res.add(i.clone());
      }
      return res;
    }

    public Set<Invoice> getAllInvoices() {
      HashSet<Invoice> res = new HashSet<>();
      for(Invoice i: this.invoicesByDate){
            res.add(i.clone());
      }
      return res;
    }

    public List<Invoice> getInvoiceHistory(Invoice i) {
      ArrayList<Invoice> res = new ArrayList<>();
      for(Invoice inv: this.invoiceHistory.get(i)){
          res.add(inv.clone());
      }
      return res;
    }

    public Map<Invoice,List<Invoice>> getAllInvoicesHistory() {
      return null;
    }

}
