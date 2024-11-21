package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;


public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account insertNewAccount(Account account){

        
        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            return null;
        }
        List<Account> a = accountDAO.getAllAccounts();
        for (Account b: a){
            if(b.getAccount_id() == account.getAccount_id()){
                return null;
            }
        }
        return accountDAO.createAccount(account);
    }

    //check with instructor
    public Account realUser(Account account){
        Account user_account = accountDAO.validateLogin(account);
        if(user_account != null){
            return user_account;
        }
        return null;
    }
    
    /* 
    // Get an account by account_id
    public Account searchAccountById(int accountId) {
        return accountDAO.getAccountById(accountId);
    }

    // Get all accounts (for admin or display purposes)
    public List<Account> searchAllAccounts() {
        return accountDAO.getAllAccounts();
    }
        */
}
