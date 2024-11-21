package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;
import java.sql.SQLException;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account insertNewAccount(String username, String password){
        Account accountExists = accountDAO.checkIfUsernameExists(username);
        if (accountExists != null) {
            System.out.println("Username already exists. Please choose a different username.");
            return null; // Return null or throw exception?
        }

        // Create a new account and return the created Account object
        Account newAccount = new Account(username, password);
        return accountDAO.createAccount(newAccount);
    }

    //check with instructor
    public Account realUser(String username, String password) throws SQLException{
        return accountDAO.validateLogin(username, password);
    }
    
    // Get an account by account_id
    public Account searchAccountById(int accountId) {
        return accountDAO.getAccountById(accountId);
    }

    // Get all accounts (for admin or display purposes)
    public List<Account> searchAllAccounts() {
        return accountDAO.getAllAccounts();
    }
}
