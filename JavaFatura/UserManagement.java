/**
 * This class represents the basic interface between the main class
 * and the databases
 *
 * @author Luís, Miguel e Zé
 * @version 2.3
 */

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class UserManagement implements Serializable {

    /** All the users in the system */
    private UserDatabase userDatabase;
    /** All the invoices in the system */
    private InvoiceDatabase invoiceDatabase;
    /** Current User */
    private User current;

    /**
     * Constructor for objects of class UserDatabase
     */
    public UserManagement() {
      this.userDatabase = new UserDatabase();
      this.invoiceDatabase = new InvoiceDatabase();
      this.current = new User();
    }

    /**
      * Setter for the current user
      * @param u User
    */
    public void setCurrentUser(User u) {
      this.current = u.clone();
    }

    /**
      * Getter for the current user
      * @return User
    */
    public User getCurrentUser() {
      return this.current.clone();
    }

    /**
      * Adds a set of family members to a certain user.
      * Is "placebo", all the effort is done in the userDatabase
      * @param newMembers set of family that will now be part of the user
      * @param memberThatGetsNewFamily user that receives more family
    */
    private void addFamilyMembers(Set<Individual> newMembers, User memberThatGetsNewFamily) {
      this.userDatabase.addFamilyMembers(newMembers,memberThatGetsNewFamily.getNIF());
    }

    /**
      * Returns all the invoices of the current user according to date
      * @return TreeSet, ordered by date
    */
    public Set<Invoice> getAllInvoicesByDateByUser()
      throws UserDoesNotHaveInvoicesException {

        return this.invoiceDatabase.getAllInvoicesByDateByNIF(this.current.getNIF());
    }

    /**
      * Returns all the invoices of the current user according to their value (descending)
      * @return TreeSet, ordered by descending value
    */
    public Set<Invoice> getAllInvoicesByValueByUser()
      throws UserDoesNotHaveInvoicesException {

        return this.invoiceDatabase.getAllInvoicesByValueByNIF(this.current.getNIF());
    }

    /**
      * Returns a set with the available EconomicalFields of a User
      * @param u User
      * @return HashSet
    */
    public Set<EconomicalField> getAvailableEconomicalFieldsOfUser(User u)
      throws WrongInstanceOfUserException {

      TreeSet<EconomicalField> economicalFields = new TreeSet<>();;
        if (u instanceof Enterprise) {
            Enterprise e = (Enterprise) u;
            economicalFields = (TreeSet) e.getEconomicalFields();
        }
        else if (u instanceof Individual) {
            Individual ind = (Individual) u;
            economicalFields = (TreeSet) ind.getWhereCanDeduct();
        }
      else throw new WrongInstanceOfUserException(String.valueOf(u.getNIF()));
      return economicalFields;
    }

    /**
      * Returns all the invoices of a given Enterprise that were issued to an Individual
      * @param individual nif of the individual
      * @return Set of invoices, ordered by value
    */
    public Set<Invoice> getAllInvoicesByIndividualInEnterpriseByValue(Integer individual)
      throws UserDoesNotHaveInvoicesException {

        return this.invoiceDatabase.getAllInvoicesByIndividualInEnterpriseByValue(individual,this.current.getNIF());
    }

    /**
      * Returns all the invoices of a given Enterprise that were issued to an Individual
      * @param individual nif of the individual
      * @return Set of invoices, ordered by date
    */
    public Set<Invoice> getAllInvoicesByIndividualInEnterpriseByDate(Integer individual)
      throws UserDoesNotHaveInvoicesException {

        return this.invoiceDatabase.getAllInvoicesByIndividualInEnterpriseByDate(individual,this.current.getNIF());
    }

    /**
      * Returns all the invoices of a given Enterprise that were issued to an Individual
      * in an interval of dates
      * @param begin begin date
      * @param end end date
      * @param individual nif of individual
      * @return set of invoices
    */
    public Set<Invoice> getInvoicesByIndividualInEnterpriseBetweenDates(LocalDateTime begin,
                        LocalDateTime end, Integer individual)
      throws UserDoesNotHaveInvoicesException {

        return this.invoiceDatabase.getInvoicesByIndividualInEnterpriseBetweenDates(begin,end,individual,this.current.getNIF());
    }

    /**
      * Returns the total revenue of an enterprise in an interval of date. It's the
      * sum of expense values before tax
      * @param begin begin date
      * @param end end date
      * @param enterprise instance of enterprise that wants to get the revenue
      * @return double
    */
    public double getTotalRevenueByDate(LocalDateTime begin, LocalDateTime end,
                  User enterprise) {
      return this.invoiceDatabase.getTotalRevenueByDate(begin,end,enterprise.getNIF());
    }

    /**
      * Returns all the invoices of a certain economicalfield by a given user
      * @param eco EconomicalField
      * @param u User that we want to retrieve the invoices from
      * @return Set of invoices ordered by expense
    */
    private Set<Invoice> getAllInvoicesByEconomicalFieldByUser(EconomicalField eco, User u)
      throws UserDoesNotHaveInvoicesException {

        return this.invoiceDatabase.getAllInvoicesByEconomicalFieldByNIF(eco,u.getNIF());
    }

    /**
      * Returns all the invoices of a certain economicalfield by the current user
      * @param code economicalfield code
    */
    public Set<Invoice> getAllInvoicesByEconomicalFieldByUser(int code)
      throws UserDoesNotHaveInvoicesException, InvalidEconomicalFieldCodeException {

      EconomicalField eco = EconomicalField.getEconomicalFieldByCode(code);

      return this.invoiceDatabase.getAllInvoicesByEconomicalFieldByNIF(eco,this.current.getNIF());
    }

    /**
      * Returns all the invoices of a certain status by the current user
      * @param status status of the invoice
      * @return Set of invoices ordered by expense
    */
    public Set<Invoice> getAllInvoicesByStatusByUser(boolean status)
      throws UserDoesNotHaveInvoicesException {

        return this.invoiceDatabase.getAllInvoicesByStatusByNIF(status, this.current.getNIF());
    }

    /**
      * Method that returns boolean if user credentials match the ones in the database
      * @param nif nif of user
      * @param password alleged password
      * @return 1 if Admin, 2 if Individual, 3 if Enterprise, 4 if failed
    */
    public int userLogin(int nif, String password)
      throws UserDoesNotExistInDatabaseException {
        int result = 4;
        User user = this.getUser(nif);
        if (user.userMatch(nif,password)) {
          this.current = user.clone();
          if (user instanceof Enterprise)
  					result = 3;
  				else if (user instanceof Individual)
  					result = 2;
  				else
  					result = 1;
        }
        return result;
    }

    /**
      * Adds a user to the database
      * @param u User to add
    */
    public void addUser(User u)
      throws InvalidUserParameterException {

        if (!this.userDatabase.isEmailUnique(u.getEmail()))
          throw new InvalidUserParameterException("Email: " + u.getEmail());
        if (!this.userDatabase.isNIFUnique(u.getNIF()))
          throw new InvalidUserParameterException("NIF: " + String.valueOf(u.getNIF()));
        this.userDatabase.addUser(u);
    }

    /**
      * Given a NIF, returns the user
      * @param NIF nif to get the user
      * @return User, or exception if user does not exist
    */
    public User getUser(int NIF)
      throws UserDoesNotExistInDatabaseException {

        return this.userDatabase.getUserByNIF(NIF);
    }

    /**
      * Registers a new Invoice in the system
      * @param issued nif of enterprise who issued an invoice
      * @param receiver nif of individual who payed
      * @param issueDate date of the invoice
      * @param typeOfExpense economicalField of the expense
      * @param valueOfExpense ammount of money the issuer spent
      * @param desc Description of the invoice
      * @return Invoice created, if no exceptions
    */
    private Invoice newInvoice(int issued, int receiver, LocalDateTime issueDate,
                   EconomicalField typeOfExpense, double valueOfExpense, String desc)
      throws UserDoesNotExistInDatabaseException, WrongInstanceOfUserException {

        if (!(this.userDatabase.getUserByNIF(receiver) instanceof Individual)) throw new WrongInstanceOfUserException("Individual");
        if (!(this.userDatabase.getUserByNIF(issued) instanceof Enterprise)) throw new WrongInstanceOfUserException("Enterprise");

        Individual individual = (Individual) this.userDatabase.getUserByNIF(receiver);
        Enterprise enterprise = (Enterprise) this.userDatabase.getUserByNIF(issued);
        boolean stat;

        if (enterprise.getNumberOfEconomicalFields() > 1)
            stat = Invoice.NOT_CONFIRMED;
        else
            stat = Invoice.CONFIRMED;

        Invoice i = new Invoice(enterprise,individual,issueDate,typeOfExpense,valueOfExpense,desc,stat);
        return i;
    }

    /**
      * Registers a new Invoice in the system
      * @param i Invoice
    */
    public void registerInvoice(int receiver, LocalDateTime issueDate,
                   int typeOfExpense, double valueOfExpense, String desc)
      throws UserDoesNotExistInDatabaseException, WrongInstanceOfUserException, InvalidEconomicalFieldCodeException {
        int issued = this.current.getNIF();
        EconomicalField eco = EconomicalField.getEconomicalFieldByCode(typeOfExpense);
        Invoice i = this.newInvoice(issued,receiver,issueDate,eco,valueOfExpense,desc);
        this.invoiceDatabase.addInvoice(i);
    }

    /**
      * Returns all the invoices of a given family, in a map
      * @param family Members of the family
      * @return map, containing as keys the family members, and as values their invoices
    */
    public Map<Individual,Set<Invoice>> getInvoicesByFamily(Set<Individual> family) {
      HashMap<Individual,Set<Invoice>> result = new HashMap<>();
      for(Individual i : family) {
        TreeSet<Invoice> invoices;
        try {
          invoices = (TreeSet) this.invoiceDatabase.getAllInvoicesByDateByNIF(i.getNIF());
        }
        catch (UserDoesNotHaveInvoicesException e) {
          invoices = new TreeSet<>();
        }
        result.put(i.clone(),invoices);
      }
      return result;
    }

    /**
      * Maps the enterprises that issue the most with the deductions they represent
      * It's listed by most deductions first, given the list ordered by most
      * issued value.
      * @param N limiter of enterprises
      * @return Map, keys as deductions to all NIFs who have the same deduction value
    */
    public Map<Double,List<Integer>> getEnterprisesThatIssueTheMost(int N) {
      return this.mapEnterpriseToDeduction(this.getTopNEnterprisesByIssueValue(N));
    }

    /**
      * Returns a list with the NIFs of the Individuals that spend the most
      * The most spender first.
      * @param N limits the top N spenders
      * @return List of NIFs
    */
    public List<Integer> getTopNSpenders(int N) {
      ArrayList<Integer> nifs = (ArrayList) this.invoiceDatabase.getTopSpenders();
      ArrayList<Integer> result = new ArrayList<>();
      User user;
      int max = 0;
      for (Integer i : nifs) {
        if (max == N) break;
        try {
          user = this.getUser(i);
          if (user instanceof Individual) {
            result.add(i);
            max++;
          }
        }
        catch (UserDoesNotExistInDatabaseException e) {
          return result;
        }
      }
      return result;
    }

    /**
      * Returns a list of NIFs of enterprises ordered by value of issued invoices
      * @param N limiter
      * @return List of NIFs,biggest issuevalue first
    */
    public List<Integer> getTopNEnterprisesByIssueValue(int N) {
      ArrayList<Integer> nifs = (ArrayList) this.invoiceDatabase.getTopSpenders();
      ArrayList<Integer> result = new ArrayList<>();
      int max = 0;
      for (Integer i : nifs) {
        if (max == N) break;
        try {
          User user = this.getUser(i);
          if (user instanceof Enterprise) {
            result.add(i);
            max++;
          }
        }
        catch (UserDoesNotExistInDatabaseException e) {
          return result;
        }
      }
      return result;
    }


    /**
      * Given a list of NIFs of enterprises, orders the Nifs according to
      * the deduction value the invoices of the enterprise represents to the end
      * users
      * @param enterprises list ordered by most issued value
      * @return Map, with keys as deductions and values the NIFs that have that deduction value
    */
    public Map<Double,List<Integer>> mapEnterpriseToDeduction(List<Integer> enterprises) {
      TreeMap<Double,List<Integer>> result = new TreeMap<>((k1,k2) -> Double.compare(k2,k1));
      for (Integer nif : enterprises) {
        double deductions = 0;
        try {
          Enterprise enterprise = (Enterprise) this.getUser(nif);
          TreeMap<EconomicalField,Double> mapDeductions = (TreeMap) this.getDeductionsEnterprise(nif);
          for (EconomicalField eco : mapDeductions.keySet()) {
            deductions += mapDeductions.get(eco);
          }
          if (result.get(deductions) == null)
            result.put(deductions,new ArrayList<Integer>());
          result.get(deductions).add(nif);
        }
        catch(UserDoesNotExistInDatabaseException e) {
          deductions = 0;
        }
      }
      return result;
    }

    /**
      * Returns the total spent by a given nif
      * @param nif
      * @return total spent
    */
    public double getTotalSpentByNIF(int nif) {
      return this.invoiceDatabase.getTotalSpentByNIF(nif);
    }

    /**
      * Changes the economicalfield of an invoice
      * @param invoiceToChange original invoice
      * @param code of economicalfield
    */
    public void changeEconomicalFieldOfInvoice(Invoice invoiceToChange,int code)
      throws InvalidEconomicalFieldCodeException {

      EconomicalField eco =  EconomicalField.getEconomicalFieldByCode(code);
      this.invoiceDatabase.changeEconomicalField(invoiceToChange,eco);
    }

    /**
      * Returns the history of invoices of the current user
      * @return a map with keys as invoices, and values as their past status
    */
    public Map<Invoice,List<Invoice>> getInvoiceHistory()
      throws UserHasNoInvoiceHistoryException {

        return this.invoiceDatabase.getInvoiceHistoryFromNIF(this.current.getNIF());
    }

    /**
      * Saves the state of the app to a file
      * @param file name of file
    */
    public void saveObject(String file)
      throws FileNotFoundException, IOException {

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    /**
      * Restores the state of the app given a file to load
      * @param file file name
      * @return new status of the app
    */
    public static UserManagement loadObject(String file)
      throws FileNotFoundException, IOException, ClassNotFoundException {

        FileInputStream fich = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fich);
        UserManagement u = (UserManagement) ois.readObject();
        ois.close();
        return u;
    }

    /**
      * Maps the Individual deduction of the current user according to the
      * economicalField
      * @return Map with said keyset
    */
    public Map<EconomicalField,Double> getDeductionsIndividual() {
      User u = this.current;
      double deductions = 0;
      TreeMap<EconomicalField,Double> result = new TreeMap<>();
      if (!(u instanceof Individual)) return result;
      Individual ind = (Individual) u;
      for(EconomicalField eco : ind.getWhereCanDeduct()) {
        deductions = 0;
        try {
          TreeSet<Invoice> invoices = (TreeSet) this.getAllInvoicesByEconomicalFieldByUser(eco,ind);
          deductions = eco.getFiscalDeductionFromIndividual(invoices);
        }
        catch (UserDoesNotHaveInvoicesException e) {
          deductions = 0;
        }
        result.put(eco.clone(),deductions);
      }
      return result;
    }

    /**
      * Maps the deductions the invoices of an enterprise represent to their
      * customers, according to the EconomicalField
      * @param nif of enterprise
      * @return Map, with keys as the EconomicalFields an Enterprise can issue
      * invoices to, and the value the deduction they represent (sum of deductions of
      * individuals).
    */
    public Map<EconomicalField,Double> getDeductionsEnterprise(Integer nif) {
      double deductions = 0;
      TreeMap<EconomicalField,Double> result = new TreeMap<>();
      try {
        User u = this.getUser(nif);
        if (!(u instanceof Enterprise)) return result;
        Enterprise ent = (Enterprise) u;
        for(EconomicalField eco : ent.getEconomicalFields()) {
          deductions = 0;
          try {
            TreeSet<Invoice> invoices = (TreeSet) this.getAllInvoicesByEconomicalFieldByUser(eco,ent);
            deductions = eco.getFiscalDeductionFromEnterprise(invoices);
          }
          catch(UserDoesNotHaveInvoicesException e) {
            deductions = 0;
          }
          result.put(eco.clone(),deductions);
        }
      }
      catch (UserDoesNotExistInDatabaseException e) {
        return result;
      }

      return result;
    }

    /**
      * Maps the family deduction of the current user according to the economicalField
      * @return Map with said keyset
    */
    public Map<EconomicalField,Double> getDeductionsFamily() {
      User u = this.current;
      double deductions = 0;
      TreeMap<EconomicalField,Double> result = new TreeMap<>();
      if (!(u instanceof Individual)) return result;
      Individual ind = (Individual) u;
      for(EconomicalField eco : ind.getWhereCanDeduct()) {
        HashMap<Individual,Set<Invoice>> invoices = (HashMap) this.getInvoicesByFamily(ind.getFamily());
        deductions = eco.getFiscalDeductionFromFamily(invoices);
        result.put(eco.clone(),deductions);
      }
      return result;
    }


    /**
      * Method to register an Individual in the system
      * @param nif
      * @param email
      * @param name
      * @param address
      * @param password
      * @param family (nifs of family members)
      * @param fiscCoef (fiscal coefficient)
      * @param ecActivities (codes of economicalfields)
    */
    public void registerIndividual(int nif,
          String email, String name, String address, String password,
          Set<Integer> family, double fiscCoef, Set<Integer> ecActivities)
          throws InvalidEconomicalFieldCodeException, UserDoesNotExistInDatabaseException, InvalidUserParameterException {

      Individual ind = new Individual(nif,email,name,address,password);
      int more = 1;
      ind.setFiscalCoefficient(fiscCoef);
      // Adds where it can deduct
      for(Integer code : ecActivities) {
        EconomicalField eco = EconomicalField.getEconomicalFieldByCode(code);
        ind.addWhereCanDeduct(eco);
      }
      // Checks if family exist in the system
      int sizeOfFamily = 0;
      for(Integer i : family) {
        Individual familyMember;
        if (this.getUser(i) instanceof Individual) {
          familyMember = (Individual) this.getUser(i);
          ind.addFamilyMember(familyMember);
          sizeOfFamily++;
        }
      }
      // If family size > 3, it's large.
      if (sizeOfFamily > 3) ind = new LargeFamily(ind);
      // Finally adds the user
      this.addUser(ind);
      // Adds info about family to all family members
      for(Individual familyMember : ind.getFamily()) {
        HashSet<Individual> familyToAdd = (HashSet) ind.getFamily();
        familyToAdd.remove(familyMember);
        familyToAdd.add(ind.clone());
        this.addFamilyMembers(familyToAdd,familyMember);
      }
    }

    /**
      * Registers an enterprise in the system
      * @param nif
      * @param email
      * @param name
      * @param address
      * @param password
      * @param district int value of the district
      * @param ecActivities set of codes of EconomicalFields
    */
    public void registerEnterprise(int nif,
          String email, String name, String address, String password,
          int district, Set<Integer> ecActivities)
          throws InvalidEconomicalFieldCodeException, InvalidUserParameterException {

      Enterprise enterprise = new Enterprise(nif,email,name,address,password);
      // Verifies if it's inland
      if (Districts.isInland(district)) {
        enterprise = new InlandEnterprise(enterprise);
        ((InlandEnterprise)enterprise).setDistrict(district);
      }
      // Adds where it can pass issues to
      for (Integer code : ecActivities) {
        EconomicalField eco = EconomicalField.getEconomicalFieldByCode(code);
        enterprise.addEconomicalField(eco);
      }
      // finally adds
      this.addUser(enterprise);
    }

    public static UserManagement populateData() {
      UserManagement result = new UserManagement();
      try {
        TreeSet<Integer> eco = new TreeSet<>();
        eco.add(1);eco.add(2);eco.add(3);eco.add(4);
        result.registerIndividual(1000,"contribuinte1000@email.com","Iker Casillas","Estádio do Dragão","pass",new TreeSet<>(),1,eco);
        result.registerIndividual(1001,"contribuinte1001@email.com","Joaquim Leitão","Rua da Cruz","pass",new TreeSet<>(),1,eco);
        result.registerIndividual(1002,"contribuinte1002@email.com","Duarte Semedo","Rua do Pessegueiro","pass",new TreeSet<>(),1,eco);
        TreeSet<Integer> family = new TreeSet<>();
        family.add(1000);
        result.registerIndividual(1003,"contribuinte1003@email.com","Ivan Marcano","Estádio do Dragão","pass",family,1,eco);
        family.add(1003);
        result.registerIndividual(1004,"contribuinte1004@email.com","Felipe","Estádio do Dragão","pass",family,1,eco);
        result.registerIndividual(1005,"contribuinte1005@email.com","Adamâncio Pereira","Rua da Oura","pass",new TreeSet<>(),1,eco);
        family.add(1004);
        result.registerIndividual(1006,"contribuinte1006@email.com","Ricardo Pereira","Estádio do Dragão","pass",family,1,eco);
        family.add(1006);
        result.registerIndividual(1007,"contribuinte1007@email.com","Alex Telles","Estádio do Dragão","pass",family,1,eco);
        result.registerIndividual(1008,"contribuinte1008@email.com","Bruno Fereira","Rua das Aves","pass",new TreeSet<>(),1,eco);
        result.registerIndividual(1009,"contribuinte1009@email.com","Carlos Cruz","Rua do Infante","pass",new TreeSet<>(),1,eco);
        result.registerIndividual(1010,"contribuinte1010@email.com","Diogo Dionísio","Rua dos Diamantes","pass",new TreeSet<>(),1,eco);
        family.add(1007);
        result.registerIndividual(1016,"contribuinte1016@email.com","Jorge Nuno","Estádio do Dragão","pass",family,1,eco);

        /**
          * Família numerosa: 1000,1003,1004,1006,1007, 1016
        */

        TreeSet<Integer> deductions = new TreeSet<>();
        deductions.add(1);
        result.registerEnterprise(2000,"empresa2000@email.com","Fruta Lda.","Rua das Frutarias","pass",2,deductions);
        deductions.add(2);
        result.registerEnterprise(2001,"empresa2001@email.com","Bruxo Lda.","Rua das Bruxarias","pass",3,deductions);
        deductions.remove(1);
        result.registerEnterprise(2002,"empresa2002@email.com","Clínica Osório","Rua das Clínicas","pass",4,deductions);
        deductions.add(3);
        result.registerEnterprise(2003,"empresa2003@email.com","Universidade da Vida","Rua das Vida","pass",5,deductions);
        deductions.add(2);
        result.registerEnterprise(2004,"empresa2004@email.com","Supermercado Rodrigues","Rua do Alcaide","pass",6,deductions);

        /**
          * Deduz despesas gerais
        */
        Enterprise e2000 = (Enterprise)result.getUser(2000);
        result.setCurrentUser(e2000);
        result.registerInvoice(1016,LocalDateTime.of(2002,12,13,10,14),1,2,"Maçãs");
        result.registerInvoice(1007,LocalDateTime.of(2003,1,3,9,13),1,5,"Melancia");
        result.registerInvoice(1007,LocalDateTime.of(2003,2,4,10,13),1,3,"Pêssego");
        result.registerInvoice(1006,LocalDateTime.of(2003,3,5,14,15),1,4.6,"Melão");
        result.registerInvoice(1006,LocalDateTime.of(2003,4,7,13,17),1,14.8,"Chouriço");
        result.registerInvoice(1004,LocalDateTime.of(2003,5,6,17,17),1,9.8,"Chouriça");
        result.registerInvoice(1004,LocalDateTime.of(2003,6,8,14,21),1,13.7,"Limões");
        result.registerInvoice(1003,LocalDateTime.of(2003,7,9,19,9),1,1.5,"Morangos");
        result.registerInvoice(1003,LocalDateTime.of(2003,8,10,15,1),1,1.3,"Couve");
        result.registerInvoice(1003,LocalDateTime.of(2003,9,11,15,2),1,13.5,"Alface");

        /*
          * Empresa 2001: Deduz saúde e Gerais
        */
        Enterprise e2001 = (Enterprise)result.getUser(2001);
        result.setCurrentUser(e2001);
        result.registerInvoice(1009,LocalDateTime.of(2012,12,13,10,14),2,2,"Penso para a testa");
        result.registerInvoice(1008,LocalDateTime.of(2013,12,13,10,14),2,3.5,"Penso para a nuca");
        result.registerInvoice(1010,LocalDateTime.of(2012,10,13,10,12),1,14,"Bruxaria Especial");

        /*
          * Empresa 2002: Apenas deduz Saúde
        */
        Enterprise e2002 = (Enterprise)result.getUser(2002);
        result.setCurrentUser(e2002);
        result.registerInvoice(1001,LocalDateTime.of(2016,3,12,9,14),2,20,"Consulta de rotina");
        result.registerInvoice(1002,LocalDateTime.of(2016,4,11,7,12),2,43.5,"Consulta de ortopedia");

        /**
          * Empresa 2003: deduz saúde e educação
        */
        Enterprise e2003 = (Enterprise)result.getUser(2003);
        result.setCurrentUser(e2003);
        result.registerInvoice(1003,LocalDateTime.of(2017,4,11,9,14),2,1.5,"Penso de enfermaria");
        result.registerInvoice(1005,LocalDateTime.of(2017,6,11,7,12),3,1024,"Propinas");

        /**
          * Deduz saúde e educação
        */
        Enterprise e2004 = (Enterprise)result.getUser(2004);
        result.setCurrentUser(e2004);
        result.registerInvoice(1004,LocalDateTime.of(2009,10,10,5,14),2,3.2,"Ben-u-ron");
        result.registerInvoice(1006,LocalDateTime.of(2008,11,11,5,12),3,10.5,"Caixas de cereais");


      }
      catch (Exception e) {
        System.out.println(e.getMessage());
        return result;
      }
      return result;
    }
}
