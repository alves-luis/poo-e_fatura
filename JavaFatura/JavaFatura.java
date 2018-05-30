
/**
 * Main class, that actually has a main method
 *
 * @author Luís, Miguel e Zé
 * @version 1.5
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;
import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.time.DateTimeException;
import java.util.InputMismatchException;
import java.lang.StringBuilder;

public class JavaFatura {


    private static int listDistricts() {
        Districts[] allDistricts = Districts.values();
        for(int i = 0; i < allDistricts.length; i++) {
            System.out.println(i + ") " + allDistricts[i].toString());
        }
        int choice = readInt(0);
				if (choice < 0 || choice >= allDistricts.length) choice = 0;
        return choice;
    }

    /**
        * Read an int (with error catching) from IO;
        * @param defaultRes - default return value;
    */
    private static int readInt(int defaultRes) {
        Scanner sc = new Scanner(System.in);
        int result = defaultRes;
        try {
            result = sc.nextInt();
            sc.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Oops, input inválido: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Oops " + e.getMessage());
        }
        return result;
    }

    /**
        * Read a String (with error catching) from IO;
        * @param defaultRes - default return value;
    */
    private static String readString(String defaultRes) {
        Scanner sc = new Scanner(System.in);
        String result = defaultRes;
        try {
            result = sc.nextLine();
        }
        catch (Exception e) {
            System.out.println("Oops " + e.getMessage());
        }
        return result;
    }

    /**
        * Read a double (with error catching from IO);
        * @param defaultRes - default return value;
    */
    private static double readDouble(double defaultRes) {
        Scanner sc = new Scanner(System.in);
        double result = defaultRes;
        try {
            result = sc.nextDouble();
            sc.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Oops, input inválido " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Oops " + e.getMessage());
        }
        return result;
    }

    private static Set<Integer> chooseEconomicalFieldByCode() {
        TreeSet<Integer> result = new TreeSet<>();
        int code = 1;
        while (code != 0) {
            code = listEconomicalFields();
            try {
                if (EconomicalField.getEconomicalFieldByCode(code) != null)
                result.add(code);
            }
            catch (InvalidEconomicalFieldCodeException e) {
                if (code != 0)
                System.out.println("Código inválido! " + e.getMessage());
            }
        }
        return result;
    }

    private static void registerIndividual(int nif, String email, String name, String address, String password, UserManagement userMan) {
        System.out.println("Insira o coeficiente fiscal:");
        double fiscCoef = readDouble(1);

        System.out.println("Escolha as atividades económicas para as quais pode deduzir (0 para sair):");
        TreeSet<Integer> ecActivities = (TreeSet)chooseEconomicalFieldByCode();

        int more = 1;
        TreeSet<Integer> family = new TreeSet<>();
        // Create family
        while(more != 0) {
            System.out.println("Indique o NIF de familiares (se possuir):");
            System.out.println("Se não possuir, escreva 0");
            more = readInt(0);
            if (more != 0)
                family.add(more);
        }
        try {
            userMan.registerIndividual(nif,email,name,address,password,family,fiscCoef,ecActivities);
            System.out.println("Utilizador registado com sucesso!");
        }
        catch (UserDoesNotExistInDatabaseException e) {
            System.out.println("Membro da família não existe: " + e.getMessage());
            System.out.println("Utilizador não registado!");
        }
        catch (InvalidEconomicalFieldCodeException e) {
            System.out.println("Código inválido! " + e.getMessage());
            System.out.println("Utilizador não registado!");
        }
        catch (InvalidUserParameterException e) {
            System.out.println("Parâmetro inválido: " + e.getMessage());
            System.out.println("Utilizador não registado!");
        }
    }

    private static void registerEnterprise(int nif, String email, String name, String address, String password, UserManagement userMan) {
        System.out.println("Escolha o distrito:");
        int district = listDistricts();
        System.out.println("Escolha as atividades económicas a adicionar à empresa (0 para sair):");
        TreeSet<Integer> ecActivities = (TreeSet) chooseEconomicalFieldByCode();
        try {
            userMan.registerEnterprise(nif,email,name,address,password,district,ecActivities);
            System.out.println("Utilizador registado com sucesso!");
        }
        catch (InvalidUserParameterException e) {
            System.out.println("Parâmetro inválido: " + e.getMessage());
        }
        catch (InvalidEconomicalFieldCodeException e) {
                System.out.println("Código inválido! " + e.getMessage());
        }
    }

    /**
        * Method for registering a User in the system
        * @param userMan the instance of UserManagement running
    */
    private static void registerUser(UserManagement userMan) {
        System.out.println("Deseja adicionar um Contribuinte ou uma Empresa?");
        System.out.println("1) Contribuinte");
        System.out.println("2) Empresa");
        System.out.println("3) Regressar ao menu");
        int choice = readInt(3);
        if (choice < 3 && choice > 0) {
            System.out.println("Indique o NIF:");
            int nif = readInt(0);
            if (nif == 0) return;

            System.out.println("Indique o e-mail:");
            String email = readString("");
            if (email.equals("")) return;

            System.out.println("Indique o nome:");
            String name = readString("");
            if (name.equals("")) return;

            System.out.println("Indique o endereço:");
            String address = readString("");

			System.out.println("Indique uma palavra-passe:");
			String password = readString("");

			switch(choice) {
				case 1: registerIndividual(nif,email,name,address,password,userMan);
								break;
				case 2: registerEnterprise(nif,email,name,address,password,userMan);
								break;
				default: System.out.println("Regressando ao Menu Inicial...");
								 break;
			}
		}
	}

	/**
		* Lists all the available EconomicalFields
		* @return int, meaning the code of a EconomicalField
	*/
	private static int listEconomicalFields() {
		for(int i = 1; i <= EconomicalField.getNumberOfEconomicalFields(); i++) {
			try {
				System.out.println(i + ") " + EconomicalField.getEconomicalFieldByCode(i).getCategory());
			}
			catch (InvalidEconomicalFieldCodeException e) {
				System.out.println("Error in listing economicalFields. Invalid code! " + e.getMessage());
			}
		}
		int ecActivities = readInt(1);
		return ecActivities;
	}

	/**
		* Lists all the available EconomicalFields from a User
		* If Enterprise, the EconomicalFields it can issue invoices to
		* If Individual, the EconomicalFields it can deduct to
		* @param u User (Individual or Enterprise)
		* @param userMan running instance of UserMan
		* @return int, meaning the code of a EconomicalField
	*/
	private static int listEconomicalFields(User u, UserManagement userMan) {
		try {
			TreeSet<EconomicalField> economicalFields = (TreeSet) userMan.getAvailableEconomicalFieldsOfUser(u);
			for (Iterator<EconomicalField> it = economicalFields.iterator(); it.hasNext();) {
				EconomicalField eco = it.next();
				System.out.println(eco.getCode() + ") " + eco.getCategory() + ";");
			}
			int ecActivities = readInt(0);
			if (economicalFields.contains(EconomicalField.getEconomicalFieldByCode(ecActivities))) return ecActivities;
			else return 0;
		}
		catch (WrongInstanceOfUserException e) {
			System.out.println("Erro de utilizador! " +  e.getMessage());
			return 0;
		}
		catch (InvalidEconomicalFieldCodeException e) {
			System.out.println("Código de atividade económica inválido!");
			return 0;
		}
	}

	/**
		* Method that retrieves a user Profile, given its nif
		* @param userMan instance of UserManagement running
	*/
	private static void getUserProfile(UserManagement userMan) {
		System.out.println("Insira o NIF do utilizador que pretende consultar:");
		System.out.println("Prima 0 para regressar ao menu");
		int nif = readInt(0);
		try {
			System.out.println(userMan.getUser(nif).toString());
		}
		catch (UserDoesNotExistInDatabaseException e) {
			System.out.println(String.valueOf(nif) + " não existe na base de dados!");
		}
	}

  /**
    * Prints the top 10 spenders in the system
  */
	private static void getTop10Individuals(UserManagement userMan) {
		ArrayList<Integer> result = (ArrayList) userMan.getTopNSpenders(10);
		for(Integer i : result) {
				System.out.println("NIF (" + i.toString() + ") gastou: " + userMan.getTotalSpentByNIF(i) + "€");
		}
	}

  /**
    * Prints the N enterprises that issue the most and their deductions
  */
	private static void getNMostEnterprises(UserManagement userMan) {
		System.out.println("Indique o número de empresas que pretende obter:");
		int n = readInt(1);
		if (n < 0) n = 1;
		TreeMap<Double,List<Integer>> result = (TreeMap)userMan.getEnterprisesThatIssueTheMost(n);
		result.keySet().stream().forEach(ded -> result.get(ded).stream().forEach(nif -> System.out.println("NIF(" + nif +") que permitiu (" + ded + "€) de dedução")));
	}

	/**
		* Lists the available options as an Admin
		* @return choice in the menu
	*/
	private static int getOptionAdmin() {
		System.out.println("Selecione o que pretende realizar:");
		System.out.println("0) Logout");
		System.out.println("1) Registar utilizador");
		System.out.println("2) Obter top 10 dos contribuintes que mais gastam");
		System.out.println("3) Gravar o estado da aplicação");
		System.out.println("4) Obter relação das N empresas que mais faturam e montante de dedução que as respetivas faturas representam");
		System.out.println("5) Obter perfil de Utilizador");
		int choice = readInt(0);
		return choice;
	}

	/**
		* Loads a file (asking for input from user)
		* @return instance of UserManagement from file, or a blank one if can't load
	*/
	private static UserManagement loadFile() {
		System.out.println("Insira o nome do ficheiro a carregar:");
		String file = readString("");
		UserManagement result = new UserManagement();
		try {
			result = UserManagement.loadObject(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not Found!");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	/**
		* Saves current state into a file
		* @param userMan running instance of UserManagement
	*/
	private static void saveFile(UserManagement userMan) {
		System.out.println("Insira o nome do ficheiro para guardar:");
		String file = readString("");
		try {
			userMan.saveObject(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("Ficheiro não encontrado!");
		}
		catch (Exception e) {
			System.out.println("Oops " + e.getMessage());
		}
	}

	/**
		* Executes the choice of menu action of an Admin
		* @param userMan running instance of UserManagement
	*/
	private static void mainAdmin(UserManagement userMan) {
		int op = 0;
		do {
			op = getOptionAdmin();
			switch(op) {
				case 1: registerUser(userMan);
								break;
				case 2: getTop10Individuals(userMan);
								break;
				case 3: saveFile(userMan);
								break;
				case 4: getNMostEnterprises(userMan);
								break;
				case 5: getUserProfile(userMan);
								break;
				default:
								break;
			}
		} while (op != 0);
	}


	/**
		* Lists all the options of an individual when he logs in
		* @return int
	*/
	private static int getOptionIndividual() {
		System.out.println("Selecione o que pretende realizar:");
		System.out.println("0) Logout");
		System.out.println("1) Consultar faturas");
		System.out.println("2) Corrigir atividade económica de fatura");
		System.out.println("3) Consultar montante de dedução acumulado");
		int choice = readInt(0);
		return choice;
	}

  /**
    * Prints the individual invoices according to the economicalfield
  */
	private static void listIndividualInvoicesByEconomicalField(UserManagement userMan) {
		System.out.println("Escolha a atividade económica para a qual quer obter as faturas:");
		int ecActivities = listEconomicalFields(userMan.getCurrentUser(),userMan);
		try {
			TreeSet<Invoice> result = (TreeSet) userMan.getAllInvoicesByEconomicalFieldByUser(ecActivities);
			result.stream().forEach(i -> System.out.println(i.toString()));
		}
		catch (UserDoesNotHaveInvoicesException e) {
			System.out.println("User não tem faturas associadas! " + e.getMessage());
		}
		catch (InvalidEconomicalFieldCodeException e) {
			System.out.println("Código inválido! " + e.getMessage());
		}
	}

  /**
    * Prints the individual invoices according to their status
  */
	private static void listIndividualInvoicesByStatus(UserManagement userMan) {
		System.out.println("Escolha o estado de faturas que deseja consultar:");
		System.out.println("0) Regressar ao menu inicial");
		System.out.println("1) Confirmadas");
		System.out.println("2) Não confirmadas");
		int choice = readInt(0);
		if (choice == 0) return;
		boolean status = (choice == 2) ? Invoice.NOT_CONFIRMED : Invoice.CONFIRMED;
		TreeSet<Invoice> result = new TreeSet<>();
		try {
			result = (TreeSet) userMan.getAllInvoicesByStatusByUser(status);
			result.stream().forEach(i -> System.out.println(i.toString()));
		}
		catch (UserDoesNotHaveInvoicesException e) {
			System.out.println("User não tem faturas associadas! " + e.getMessage());
		}
	}

  /**
    * Prints the invoice history of a user
  */
	private static void listIndividualInvoiceHistory(UserManagement userMan) {
		System.out.println("Aqui se listam as alterações realizadas a faturas:");
		try {
			HashMap<Invoice,ArrayList<Invoice>> history = (HashMap) userMan.getInvoiceHistory();
			for(Invoice key : history.keySet()) {
				System.out.println("*** Fatura Atual ***");
				System.out.println(key.toString());
				System.out.println("*** Histórico ***");
				for(Invoice value : history.get(key)) {
					System.out.println(value.toString());
					System.out.println("----------------");
				}
			}
		}
		catch (UserHasNoInvoiceHistoryException e) {
			System.out.println("User não tem histórico de alterações! " + e.getMessage());
		}
	}

	/**
		* Lists all the invoices according to a criteria
		* @param userMan running instance of UserManagement
	*/
	private static void listIndividualInvoices(UserManagement userMan) {
		System.out.println("Selecione o critério de listagem:");
		System.out.println("0) Regressar ao menu inicial");
		System.out.println("1) Listar por data");
		System.out.println("2) Lista por atividade económica");
		System.out.println("3) Listar por estado");
		System.out.println("4) Listar histórico");
		int choice = readInt(0);
		switch (choice) {
			case 1: listInvoicesByValue(userMan);
							break;
			case 2: listIndividualInvoicesByEconomicalField(userMan);
							break;
			case 3: listIndividualInvoicesByStatus(userMan);
							break;
			case 4: listIndividualInvoiceHistory(userMan);
							break;
			default:
							break;
		}
	}

	/**
		* Changes the economicalField of an Invoice
		* @param userMan running instance of UserManagement
	*/
	private static void changeEconomicalFieldOfInvoice(UserManagement userMan) {
		System.out.println("Eis as faturas que ainda não foram confirmadas (pressione 0 para cancelar):");
		TreeSet<Invoice> result = new TreeSet<>();

		// Gets the invoices who are not confirmed
		try {
			result = (TreeSet) userMan.getAllInvoicesByStatusByUser(Invoice.NOT_CONFIRMED);
		}
		catch (UserDoesNotHaveInvoicesException e) {
			System.out.println("User não tem faturas associadas! " + e.getMessage());
		}

		// If no invoices, return to menu
		if (result.size() == 0) {
			System.out.println("Não existem faturas pendentes, regressando ao menu...");
			return;
		}

		// Maps all the invoices with an id
		HashMap<Integer,Invoice> mapOfInvoices = new HashMap<>();
		int count = 1;
		for(Invoice inv : result) {
			mapOfInvoices.put(count,inv);
			System.out.println("********* ID(" + count + ") *********\n" + inv.toString());
			count++;
		}
		System.out.println("Insira o ID da fatura que deseja alterar");
		int choice = readInt(0);
		// If invalid choice, leave
		if (choice == 0 || choice > mapOfInvoices.size()) return;
		// Gets the invoice chosen
		Invoice invoiceToChange = mapOfInvoices.get(choice);
		System.out.println("Escolha a atividade económica que deve ficar associada a esta fatura");
		int activityCode = listEconomicalFields(invoiceToChange.getIssuer(),userMan);
		try {
			userMan.changeEconomicalFieldOfInvoice(invoiceToChange,activityCode);
			System.out.println("Atividade alterada com sucesso! Fatura confirmada!");
		}
		catch (InvalidEconomicalFieldCodeException e) {
			System.out.println("Código inválido! Regressando ao menu..." + e.getMessage());
		}

	}

	/**
		* Lists the fiscal deductions from every EconomicalField
		* @param userMan running instance of UserManagement
	*/
	private static void listFiscalDeductions(UserManagement userMan) {
			TreeMap<EconomicalField,Double> deductionsIndividual = (TreeMap) userMan.getDeductionsIndividual();
			TreeMap<EconomicalField,Double> deductionsFamily = (TreeMap) userMan.getDeductionsFamily();
			for (EconomicalField eco : deductionsIndividual.keySet()) {
				StringBuilder sb = new StringBuilder();
				double deductionsInd = deductionsIndividual.get(eco);
				double deductionsFam = deductionsFamily.get(eco);
				double totalDeduction = deductionsInd + deductionsFam;
				sb.append(eco.getCategory()).append("\n");
				sb.append("Dedução Individual: ").append(String.valueOf(deductionsInd)).append("€\n");
				sb.append("Dedução Familiar: ").append(String.valueOf(deductionsFam)).append("€\n");
				sb.append("Dedução Total: ").append(String.valueOf(totalDeduction)).append("€\n");
				System.out.println(sb.toString());
			}
	}

	/**
		* Executes the choice of menu action of an Individual
		* @param userMan running instance of UserManagement
	*/
	private static void mainIndividual(UserManagement userMan) {
		int op = 0;
		do {
			op = getOptionIndividual();
			switch(op) {
				case 1:	listIndividualInvoices(userMan);
								break;
				case 2: changeEconomicalFieldOfInvoice(userMan);
								break;
				case 3: listFiscalDeductions(userMan);
								break;
				default:
								break;
			}
		} while (op != 0);
	}

  /**
    * Lists the options of an Enterprise
  */
	private static int getOptionEnterprise() {
		System.out.println("Selecione o que pretende realizar:");
		System.out.println("0) Logout");
		System.out.println("1) Registar fatura");
		System.out.println("2) Obter listagem de faturas");
		System.out.println("3) Obter o total faturado no período");
		int choice = readInt(0);
		return choice;
	}

  /**
    * Asks for input of a LocalDateTime
  */
	private static LocalDateTime askForDate() {
		LocalDateTime date = LocalDateTime.now();

		System.out.println("Insira o ano:");
		int year = readInt(date.getYear());

		System.out.println("Insira o mês:");
		int month = readInt(date.getMonthValue());

		System.out.println("Insira o dia:");
		int day = readInt(date.getDayOfMonth());

		System.out.println("Insira a hora:");
		int hour = readInt(date.getHour());

		System.out.println("Insira os minutos:");
		int minute = readInt(date.getMinute());

		try {
			date = LocalDateTime.of(year,month,day,hour,minute);
		}
		catch (DateTimeException e) {
			System.out.println(e.getMessage());
			return null;
		}

		return date;
	}

  /**
    * Registers an Invoice
  */
	private static void registerInvoice(UserManagement userMan) {
		System.out.println("Insira o NIF do cliente:");
		int nif = readInt(0);

		LocalDateTime date = askForDate();
		if (date == null) return;

		System.out.println("Escolha a atividade económica associada à fatura:");
		int ecActivities = listEconomicalFields(userMan.getCurrentUser(),userMan);

		System.out.println("Indique o valor da fatura:");
		double value = readDouble(0);

		System.out.println("Indique a descrição da fatura:");
		String desc = readString("");

		try {
			userMan.registerInvoice(nif,date,ecActivities,value,desc);
			System.out.println("Fatura registada com sucesso!");
		}
		catch (UserDoesNotExistInDatabaseException e) {
			System.out.println("Falha na criação da fatura! User não existe!  " + e.getMessage());
		}
		catch (WrongInstanceOfUserException e) {
			System.out.println("Falha na criação da fatura! Contribuinte não válido! " + e.getMessage());
		}
		catch (InvalidEconomicalFieldCodeException e) {
			System.out.println("Falha na criação da fatura! Código de atividade económica inválido! " + e.getMessage());
		}
	}

  /**
    * Prints the invoices of a User by date
  */
	private static void listInvoicesByDate(UserManagement userMan) {
		TreeSet<Invoice> result;
		try {
			result = (TreeSet) userMan.getAllInvoicesByDateByUser();
			result.stream().forEach(i -> System.out.println(i.toString()));
		}
		catch (UserDoesNotHaveInvoicesException e) {
			System.out.println("User não tem faturas associadas! " + e.getMessage());
		}
	}

  /**
    * Prints the invoices of a User by value
  */
	private static void listInvoicesByValue(UserManagement userMan) {
		TreeSet<Invoice> result;
		try {
			result = (TreeSet) userMan.getAllInvoicesByValueByUser();
			result.stream().forEach(i -> System.out.println(i.toString()));
		}
		catch (UserDoesNotHaveInvoicesException e) {
			System.out.println("User não tem faturas associadas! " + e.getMessage());
		}

	}

  /**
    * Prints the invoices of a nif in an enterprise
  */
	private static void listInvoicesByIndividualByDate(UserManagement userMan, Integer nif) {
		TreeSet<Invoice> result;
		try {
			result = (TreeSet) userMan.getAllInvoicesByIndividualInEnterpriseByDate(nif);
			result.stream().forEach(i -> System.out.println(i.toString()));
		}
		catch (UserDoesNotHaveInvoicesException e) {
			System.out.println("User não tem faturas associadas! " + e.getMessage());
		}
	}

  /**
    * Prints the invoices of a nif in an enterprise
  */
	private static void listInvoicesByIndividualByValue(UserManagement userMan, Integer nif) {
		TreeSet<Invoice> result;
		try {
			result = (TreeSet) userMan.getAllInvoicesByIndividualInEnterpriseByValue(nif);
			result.stream().forEach(i -> System.out.println(i.toString()));
		}
		catch (UserDoesNotHaveInvoicesException e) {
			System.out.println("User não tem faturas associadas! " + e.getMessage());
		}
	}

  /**
    * Prints the invoices of a nif in an enterprise in a period of dates
  */
	private static void listInvoicesByIndividualBetweenDates(UserManagement userMan, Integer nif) {
		TreeSet<Invoice> result;
		System.out.println("Insira a data de início:");
		LocalDateTime begin = askForDate();
		System.out.println("Insira a data de fim:");
		LocalDateTime end = askForDate();
		try {
			result = (TreeSet) userMan.getInvoicesByIndividualInEnterpriseBetweenDates(begin,end,nif);
			result.stream().forEach(i -> System.out.println(i.toString()));
		}
		catch (UserDoesNotHaveInvoicesException e) {
			System.out.println("User não tem faturas associadas! " + e.getMessage());
		}
	}

	private static void listInvoicesByIndividual(UserManagement userMan) {
		System.out.println("Insira o contribuinte:");
		int nif = readInt(0);
		System.out.println("Escolha o critério de listagem:");
		System.out.println("0) Regressar ao menu inicial");
		System.out.println("1) Todas as faturas por data de emissão");
		System.out.println("2) Todas as faturas por valor");
		System.out.println("3) Todas as faturas entre datas");
		int choice = readInt(0);
		switch (choice) {
			case 1: listInvoicesByIndividualByDate(userMan,nif);
							break;
			case 2: listInvoicesByIndividualByValue(userMan,nif);
							break;
			case 3: listInvoicesByIndividualBetweenDates(userMan,nif);
							break;
			default: break;
		}
		System.out.println("Regressando ao menu inicial...");
	}

	private static void listInvoices(UserManagement userMan) {
		System.out.println("Escolha o critério de listagem:");
		System.out.println("0) Regressar ao menu inicial");
		System.out.println("1) Todas as faturas por data de emissão");
		System.out.println("2) Todas as faturas por valor");
		System.out.println("3) Faturas ordenadas por contribuinte");
		int choice = readInt(0);
		switch(choice) {
			case 1: listInvoicesByDate(userMan);
							break;
			case 2: listInvoicesByValue(userMan);
							break;
			case 3: listInvoicesByIndividual(userMan);
							break;
			default: break;
		}
	}

	private static void getTotalValueEnterprise(UserManagement userMan) {
		System.out.println("Insira a data de início:");
		LocalDateTime begin = askForDate();
		System.out.println("Insira a data de fim:");
		LocalDateTime end = askForDate();
		double result = userMan.getTotalRevenueByDate(begin,end,userMan.getCurrentUser());
		System.out.println("Total faturado (antes de imposto): " + result);
	}

	private static void mainEnterprise(UserManagement userMan) {
		int op = 0;
		do {
			op = getOptionEnterprise();
			switch(op) {
				case 1: registerInvoice(userMan);
								break;
				case 2: listInvoices(userMan);
								break;
				case 3: getTotalValueEnterprise(userMan);
								break;
				default: break;
			}
		} while (op != 0);
	}

	private static int loginUser(UserManagement userMan) {
		System.out.println("Escolha uma das opções:");
		System.out.println("0) Fechar a aplicação");
		System.out.println("1) Fazer login");
		int result = readInt(0);
		if (result == 0) return 0;

		System.out.println("Insira o NIF:");
		int nif = readInt(0);

		System.out.println("Insira a password:");
		String pass = readString("");

		try {
			result = userMan.userLogin(nif,pass);
		}
		catch (UserDoesNotExistInDatabaseException e) {
			System.out.println("Não existe esse utilizador! " + e.getMessage());
			result = 4;
		}
		if (result != 4)
			System.out.println("Login com sucesso! Bem-vindo!");
		else
			System.out.println("Não foi possível fazer login!");

		return result;
	}


	public static void main(String[] args) {
		UserManagement userMan = loadFile();//UserManagement.populateData();//
		int op = 0;
		do {
			op = loginUser(userMan);
			switch (op) {
				case 1: mainAdmin(userMan);
								break;
				case 2: mainIndividual(userMan);
								break;
				case 3: mainEnterprise(userMan);
								break;
				default: break;
			}
		} while (op != 0);
		System.out.println("Obrigado por usares a nossa app!");
	}
}
