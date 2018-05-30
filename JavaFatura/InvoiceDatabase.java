
/**
 * Base de dados de todas as Faturas
 *
 * @author Miguel, Luís e Zé
 * @version 1.3
 */

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.io.Serializable;
import java.lang.NullPointerException;
import java.util.TreeMap;

public class InvoiceDatabase implements Serializable {

    /** Maps the NIF to their set of Invoices */
    private Map<Integer,Set<Invoice>> invoicesByUser;
    /** Maps an Invoice to its History */
    private Map<Invoice,List<Invoice>> historyOfInvoices;

    /**
     * Default constructor
     */
    public InvoiceDatabase() {
      this.invoicesByUser = new HashMap<>();
      this.historyOfInvoices = new HashMap<>();
    }

    /**
      * Adds an Invoice to the database
      * @param i Invoice to add
    */
    public void addInvoice(Invoice i) throws NullPointerException {
      if (i == null) throw new NullPointerException();
      Set<Invoice> receiverSet = this.invoicesByUser.get(i.getReceiver().getNIF());
      Set<Invoice> issuerSet = this.invoicesByUser.get(i.getIssuer().getNIF());
      if (receiverSet == null) {
        receiverSet = new TreeSet<Invoice>();
        this.invoicesByUser.put(i.getReceiver().getNIF(),receiverSet);
      }
      if (issuerSet == null) {
        issuerSet = new TreeSet<Invoice>();
        this.invoicesByUser.put(i.getIssuer().getNIF(),issuerSet);
      }
      receiverSet.add(i.clone());
      issuerSet.add(i.clone());
    }

    /**
      * Returns all the invoices of a given nif, ordered by date
      * @param nif nif of user
      * @return Set<Invoice>
    */
    public Set<Invoice> getAllInvoicesByDateByNIF(Integer nif) throws UserDoesNotHaveInvoicesException {
      if (this.invoicesByUser.get(nif) == null) throw new UserDoesNotHaveInvoicesException(String.valueOf(nif));
      return invoicesByUser.get(nif).stream()
      .map(i -> i.clone())
      .collect(Collectors.toCollection(() -> new TreeSet(new ComparatorInvoiceDate())));
    }

    /**
      * Returns all the invoices of a given nif, ordered by value
      * @param nif nif of user
      * @return Set<Invoice>
    */
    public Set<Invoice> getAllInvoicesByValueByNIF(Integer nif) throws UserDoesNotHaveInvoicesException {
      if (this.invoicesByUser.get(nif) == null) throw new UserDoesNotHaveInvoicesException(String.valueOf(nif));
      return invoicesByUser.get(nif).stream()
      .map(i -> i.clone())
      .collect(Collectors.toCollection(() -> new TreeSet(new ComparatorInvoiceExpense())));
    }

    /**
      * Returns all the invoices of a given individual issued by a given enterprise by value
      * @param individual nif of individual
      * @param enterprise nif of enterprise
      * @return Set<Invoice> of all the invoices
    */
    public Set<Invoice> getAllInvoicesByIndividualInEnterpriseByValue(Integer individual, Integer enterprise) throws UserDoesNotHaveInvoicesException {
      if (this.invoicesByUser.get(enterprise) == null) throw new UserDoesNotHaveInvoicesException(String.valueOf(enterprise));
      return this.invoicesByUser.get(enterprise).stream()
      .filter(i -> i.getReceiver().getNIF() == individual)
      .map(i -> i.clone())
      .collect(Collectors.toCollection(() -> new TreeSet(new ComparatorInvoiceExpense())));
    }

    /**
      * Returns all the invoices of a given individual issued by a given enterprise by date
      * @param individual nif of individual
      * @param enterprise nif of enterprise
      * @return Set<Invoice> of all the invoices
    */
    public Set<Invoice> getAllInvoicesByIndividualInEnterpriseByDate(Integer individual, Integer enterprise) throws UserDoesNotHaveInvoicesException {
      if (this.invoicesByUser.get(enterprise) == null) throw new UserDoesNotHaveInvoicesException(String.valueOf(enterprise));
      return this.invoicesByUser.get(enterprise).stream()
      .filter(i -> i.getReceiver().getNIF() == individual)
      .map(i -> i.clone())
      .collect(Collectors.toCollection(() -> new TreeSet(new ComparatorInvoiceDate())));
    }

    /**
      * Returns all the invoices of a given individual issued by a given enterprise
      * between an interval of dates
      * @param begin Starting date
      * @param end Ending date
      * @param individual nif of individual
      * @param enterprise nif of enterprise
      * @return Set<Invoice> of all the invoices that match the predicate
    */
    public Set<Invoice> getInvoicesByIndividualInEnterpriseBetweenDates(LocalDateTime begin, LocalDateTime end, Integer individual, Integer enterprise) throws UserDoesNotHaveInvoicesException {
      if (this.invoicesByUser.get(enterprise) == null) throw new UserDoesNotHaveInvoicesException(String.valueOf(enterprise));
      return this.invoicesByUser.get(enterprise).stream()
      .filter(i -> (i.getReceiver().getNIF() == individual) && i.getIssueDate().isAfter(begin) && i.getIssueDate().isBefore(end))
      .map(i -> i.clone())
      .collect(Collectors.toCollection(() -> new TreeSet(new ComparatorInvoiceDate())));
    }


    /**
      * Returns the total revenue of a given enterprise
      * between an interval of dates
      * @param start Starting date
      * @param end Ending date
      * @param enterprise nif of enterprise
      * @return 0 if no invoices, or the sum of the valuesBeforeTax of all the invoices
     */
    public double getTotalRevenueByDate(LocalDateTime start, LocalDateTime end, Integer enterprise) {
      if (this.invoicesByUser.get(enterprise) == null) return 0;
      else
      return this.invoicesByUser.get(enterprise).stream()
      .filter(i -> i.getIssueDate().isAfter(start) && i.getIssueDate().isBefore(end))
      .mapToDouble(i -> i.getExpenseValueBeforeTax())
      .sum();
    }

    /**
      * Returns all the invoices of an EconomicalField by the nif of a user
      * @param eco EconomicalField
      * @param nif nif of user
      * @return Set<Invoice>
    */
    public Set<Invoice> getAllInvoicesByEconomicalFieldByNIF(EconomicalField eco, Integer nif) throws UserDoesNotHaveInvoicesException {
      if (this.invoicesByUser.get(nif) == null) throw new UserDoesNotHaveInvoicesException(nif.toString());
      else
      return this.invoicesByUser.get(nif).stream()
      .filter(i -> i.getTypeOfExpense().equals(eco))
      .map(Invoice::clone)
      .collect(Collectors.toCollection(() -> new TreeSet(new ComparatorInvoiceExpense())));
    }

    /**
      * Returns all the invoices of a given status by the nif of a user
      * @param status status of invoice
      * @param nif nif of user
      * @return Set<Invoice>
    */
    public Set<Invoice> getAllInvoicesByStatusByNIF(boolean status, Integer nif) throws UserDoesNotHaveInvoicesException {
      if (this.invoicesByUser.get(nif) == null) throw new UserDoesNotHaveInvoicesException(nif.toString());
      else
      return this.invoicesByUser.get(nif).stream()
      .filter(i -> i.getStatus() == status)
      .map(Invoice::clone)
      .collect(Collectors.toCollection(() -> new TreeSet(new ComparatorInvoiceExpense())));
    }

    /**
      * Returns the sum of the expense value of the invoices of a given nif
      * @param nif
      * @return total spent
    */
    public double getTotalSpentByNIF(Integer nif) {
      if (this.invoicesByUser.get(nif) == null) return 0;
      else
      return this.invoicesByUser.get(nif).stream()
      .mapToDouble(inv -> inv.getExpenseValueAfterTax())
      .sum();
    }

    /**
      * Returns a list with the top spenders in the system
      * @return list of nifs with the top spenders
    */
    public List<Integer> getTopSpenders() {
      TreeMap<Double,ArrayList<Integer>> mapResult = new TreeMap<>((k1, k2) -> Double.compare(k2,k1));
      for(Integer nif : this.invoicesByUser.keySet()) {
        double spent = this.getTotalSpentByNIF(nif);
        if (mapResult.get(spent) == null)
          mapResult.put(spent,new ArrayList<Integer>());
        mapResult.get(spent).add(nif);
      }
      ArrayList<Integer> result = new ArrayList<>();
      for (Double ded : mapResult.keySet()) {
        ArrayList<Integer> temp = (ArrayList)mapResult.get(ded);
        for(Integer nif : temp)
          result.add(nif);
      }
      return result;
    }

    /**
      * Returns the number of invoices of a given nif
      * @param nif
      * @return number of invoices
    */
    public long getNumberOfInvoices(Integer nif) {
      if (this.invoicesByUser.get(nif) == null) return 0;
      else return this.invoicesByUser.get(nif).stream()
                  .filter(inv -> inv.getIssuer().getNIF() == nif)
                  .count();
    }

    /**
      * Changes an invoice. Therefore it does a lot of clones.
      * @param i original invoice
      * @param newEco new economicalfield of invoice
    */
    public void changeEconomicalField(Invoice i, EconomicalField newEco) {
        Invoice newInvoice = i.clone();
        newInvoice.setTypeOfExpense(newEco);
        newInvoice.setStatus(Invoice.CONFIRMED);
        if (!this.historyOfInvoices.containsKey(i)) {
          this.historyOfInvoices.put(newInvoice.clone(),new ArrayList<Invoice>());
        }
        this.historyOfInvoices.get(newInvoice).add(i.clone());

        this.invoicesByUser.get(i.getReceiver().getNIF()).remove(i);
        this.invoicesByUser.get(i.getIssuer().getNIF()).remove(i);
        this.invoicesByUser.get(i.getReceiver().getNIF()).add(newInvoice.clone());
        this.invoicesByUser.get(i.getIssuer().getNIF()).add(newInvoice.clone());
    }


    /**
      * Returns the history of invoices of a given nif
      * @param nif
      * @return map, current invoice as key, older invoices as value (in list)
    */
    public Map<Invoice,List<Invoice>> getInvoiceHistoryFromNIF(Integer nif)  throws UserHasNoInvoiceHistoryException {
      if (this.invoicesByUser.get(nif) == null) throw new UserHasNoInvoiceHistoryException(String.valueOf(nif));

      HashMap<Invoice,List<Invoice>> result = new HashMap<>();
      for(Invoice inv : this.invoicesByUser.get(nif)) {
        if (this.historyOfInvoices.containsKey(inv)) {
          result.put(inv.clone(),new ArrayList<Invoice>());
          for(Invoice invs : this.historyOfInvoices.get(inv)) {
            result.get(inv).add(invs.clone());
          }
        }
      }
      if (result.size() == 0) throw new UserHasNoInvoiceHistoryException(String.valueOf(nif));
      else return result;
    }


}
