/**
 * Class that manages the database of Users.
 *
 * @author Luís Alves
 * @version 1.1
 */

import java.util.Map;
import java.util.HashMap;

/*
  TO-DO:
		* Validar acesso à aplicação (por empresas, individuais ou admins): fazer login/logout, mudar dados, etc;
		* Se empresa:
			* Criar fatura;
			* Obter listagem das faturas emitidas por data de emissão;
			* Obter listagem das faturas emitidas por valor;
			* Obter listagem das faturas de um Individual entre intervalo de datas arbitrário;
			* Obter listagem das faturas de um Individual por valor decrescente de despesa;
			* Indicar o total de faturação de uma empresa;
		* Se Individual:
			* Verificar as despesas emitidas em seu nome;
			* Verificar o montante de dedução fiscal acumulado (individual e do agregado familiar);
			* Corrigir a classificação (EconomicalFields) de uma fatura (deve fazer log);
			* Confimar uma classificação (EconomicalFields) a uma fatura;
		* Se Admin:
			* Gravar o estado em ficheiro;
			* Determinar relação dos 10 contribuintes que mais gastam em todo o sistema;
*/

import java.util.Set;
public class UserManagement {
    // instance variables
    private UserDatabase database; // all the users in the system
    private User current;

    /**
     * Constructor for objects of class UserDatabase
     */
    public UserManagement() {
      this.database = new UserDatabase();
      this.current = new User();
    }

    public boolean userLogin(String email, String password) {
			return false;
		}

    public void addIndividual(String name, String email, String password, String address, int NIF) {
      this.database.addIndividual(new Individual(NIF,email,name,address,password));
    }

    public void addEnterprise(String name, String email, String password, String address, int NIF) {
      this.database.addEnterprise(new Enterprise(NIF,email,name,address,password));
    }

    public void registerInvoice(Invoice i) {
      this.database.addInvoice(i);
    }

    public Set<Individual> getTop10Spenders() {
      return this.database.getTop10Spenders();
    }



}
